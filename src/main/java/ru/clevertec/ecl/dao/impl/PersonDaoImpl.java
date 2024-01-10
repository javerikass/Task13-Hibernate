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
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.dao.UtilDB;
import ru.clevertec.ecl.entity.Person;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    private final SessionFactory sessionFactory;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Person> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(Person.class, id));
    }

    @Override
    public long save(Person person) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(person);
        Long id = person.getId();
        log.debug("{} is saved with {} id", person, id);
        return id;
    }

    @Override
    public void update(Person person) {
        Session session = sessionFactory.getCurrentSession();
        Object merge = session.merge(person);
        session.flush();
        log.info("{} is updated", merge.toString());
    }

    @Override
    public void delete(UUID uuid) {
        jdbcTemplate.update(UtilDB.DELETE_PERSON, uuid);
    }

    @Override
    public List<Person> findAll() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Person> criteria = cb.createQuery(Person.class);
        Root<Person> root = criteria.from(Person.class);
        return session.createQuery(criteria.select(root)).getResultList();
    }

}
