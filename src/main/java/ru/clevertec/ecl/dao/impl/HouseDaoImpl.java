package ru.clevertec.ecl.dao.impl;

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
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.dao.UtilDB;
import ru.clevertec.ecl.entity.House;

@Slf4j
@Repository
@RequiredArgsConstructor
public class HouseDaoImpl implements HouseDao {

    private final SessionFactory sessionFactory;

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<House> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(House.class, id));
    }

    @Override
    public long save(House house) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(house);
        Long id = house.getId();
        log.debug("{} is saved with {} id", house, id);
        return id;
    }

    @Override
    public void update(House house) {
        Session session = sessionFactory.getCurrentSession();
        Object merge = session.merge(house);
        session.flush();
        log.info("{} is updated", merge.toString());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update(UtilDB.DELETE_HOUSE, uuid);
    }

    @Override
    public List<House> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<House> criteria = cb.createQuery(House.class);
        Root<House> root = criteria.from(House.class);
        return session.createQuery(criteria.select(root)).getResultList();
    }

}
