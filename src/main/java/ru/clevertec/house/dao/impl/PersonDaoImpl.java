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
import ru.clevertec.house.dao.PersonDao;
import ru.clevertec.house.dao.UtilDB;
import ru.clevertec.house.entity.Person;
import ru.clevertec.house.entity.Person_;
import ru.clevertec.house.entity.Sex;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    private boolean isTriggerCreated = false;

    @Override
    public Optional<Person> findById(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);

        criteria.multiselect(
                root.get(Person_.uuid),
                root.get(Person_.name),
                root.get(Person_.surname),
                root.get(Person_.sex),
                root.get(Person_.passportSeries),
                root.get(Person_.passportNumber),
                root.get(Person_.createDate),
                root.get(Person_.updateDate))
            .where(cb.equal(root.get(Person_.uuid), uuid));

        Person result = session.createQuery(criteria).uniqueResult();
        return Optional.ofNullable(result);
    }

    @Override
    public List<Person> findAllResidentsByHouse(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();

        return session.createQuery(UtilDB.FIND_ALL_RESIDENTS_BY_HOUSE, Person.class)
            .setParameter("uuid", uuid)
            .getResultList();
    }

    @Override
    public long save(Person person) {
        Session session = sessionFactory.getCurrentSession();

        session.persist(person);
        Long id = person.getId();
        log.info("{} is saved with {} id", person, id);
        return id;
    }

    @Override
    public void update(Person person) {
        if (!isTriggerCreated) {
            createEntityUpdateTrigger();
        }
        Session session = sessionFactory.getCurrentSession();

        session.createMutationQuery(UtilDB.UPDATE_PERSON)
            .setParameter("uuid", person.getUuid())
            .setParameter("name", person.getName())
            .setParameter("surname", person.getSurname())
            .setParameter("sex", person.getSex())
            .setParameter("passportNumber", person.getPassportNumber())
            .setParameter("passportSeries", person.getPassportSeries()).executeUpdate();

        log.info("{} is updated", person);
    }

    /**
     * Метод создания триггера для обновления поля update_date (В import.sql почему-то не хочет
     * работать)
     */
    public void createEntityUpdateTrigger() {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.createNativeMutationQuery(UtilDB.UPDATE_FUNCTION).executeUpdate();
        currentSession.createNativeMutationQuery(UtilDB.CREATE_TRIGGER).executeUpdate();
        isTriggerCreated = true;
    }

    @Override
    public void delete(UUID uuid) {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery(UtilDB.DELETE_PERSON)
            .setParameter("uuid", uuid).executeUpdate();
    }

    @Override
    public List<Person> findAll(int page, int pageSize) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);

        criteria.orderBy(cb.asc(root.get("id")));
        int startIndex = (page - 1) * pageSize;
        criteria.select(root);

        return session.createQuery(criteria)
            .setFirstResult(startIndex)
            .setMaxResults(pageSize)
            .getResultList();
    }

    @Override
    public List<Person> searchPersons(String text) {
        text = "%" + text + "%";

        return jdbcTemplate.query(UtilDB.SEARCH_PEOPLE, new Object[]{text, text, text, text},
            (rs, rowNum) -> new Person(
                UUID.fromString(rs.getString("uuid")),
                rs.getString("name"),
                rs.getString("surname"),
                Sex.valueOf(rs.getString("sex")),
                rs.getString("passport_series"),
                rs.getString("passport_number"),
                rs.getTimestamp("create_date").toLocalDateTime(),
                rs.getTimestamp("update_date").toLocalDateTime()));
    }


}
