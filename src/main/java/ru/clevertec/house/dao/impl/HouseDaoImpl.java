package ru.clevertec.house.dao.impl;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.clevertec.house.dao.HouseDao;
import ru.clevertec.house.dao.UtilDB;
import ru.clevertec.house.entity.House;
import ru.clevertec.house.entity.House_;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HouseDaoImpl implements HouseDao {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<House> findById(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<House> criteria = cb.createQuery(House.class);
        Root<House> root = criteria.from(House.class);

        criteria.multiselect(
                root.get(House_.id),
                root.get(House_.uuid),
                root.get(House_.area),
                root.get(House_.country),
                root.get(House_.city),
                root.get(House_.street),
                root.get(House_.number),
                root.get(House_.createDate))
            .where(cb.equal(root.get(House_.uuid), uuid));

        return Optional.ofNullable(session.createQuery(criteria).uniqueResult());
    }

    @Override
    public long save(House house) {
        Session session = sessionFactory.getCurrentSession();

        session.persist(house);
        Long id = house.getId();
        log.info("{} is saved with {} id", house, id);
        return id;
    }

    @Override
    public void update(House house) {
        Session session = sessionFactory.getCurrentSession();

        session.createMutationQuery(UtilDB.UPDATE_HOUSE)
            .setParameter("uuid", house.getUuid())
            .setParameter("number", house.getNumber())
            .setParameter("street", house.getStreet())
            .setParameter("city", house.getCity())
            .setParameter("country", house.getCountry())
            .setParameter("area", house.getArea()).executeUpdate();

        log.info("{} is updated", house);
    }

    @Override
    public void delete(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();

        session.createQuery(UtilDB.DELETE_HOUSE, House.class)
            .setParameter("uuid", uuid).executeUpdate();
    }

    @Override
    public List<House> findAll(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<House> criteria = cb.createQuery(House.class);
        Root<House> root = criteria.from(House.class);

        criteria.orderBy(cb.asc(root.get(House_.id)));

        int startIndex = (page - 1) * pageSize;

        criteria.select(root);
        return session.createQuery(criteria)
            .setFirstResult(startIndex)
            .setMaxResults(pageSize)
            .getResultList();
    }

    @Override
    public List<House> findOwnedHousesByPerson(UUID uuid) {
        Session currentSession = sessionFactory.getCurrentSession();

        return currentSession.createQuery(UtilDB.FIND_OWNED_HOUSES_BY_PERSON, House.class)
            .setParameter("uuid", uuid).getResultList();
    }

    public List<House> searchHouses(String text) {
        text = "%" + text + "%";

        return jdbcTemplate.query(UtilDB.SEARCH_HOUSES, new Object[]{text, text, text, text, text},
            (rs, rowNum) -> new House(
                rs.getLong("id"),
                UUID.fromString(rs.getString("uuid")),
                rs.getString("area"),
                rs.getString("country"),
                rs.getString("city"),
                rs.getString("street"),
                rs.getString("number"),
                rs.getTimestamp("create_date").toLocalDateTime()));
    }

}
