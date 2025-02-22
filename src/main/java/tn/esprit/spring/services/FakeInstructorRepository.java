package tn.esprit.spring.services;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.IInstructorRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class FakeInstructorRepository implements IInstructorRepository {

    //Les faux repositories (Fake Repositories) sont utilisés uniquement pour les tests unitaires,
    // lorsque l'on veut tester le service sans dépendre d'une vraie base de données
    //sans utilisee mockitoo  (Junit c tt)
    //si tu veux faire un test JUnit pur sans utiliser Mockito, tu dois créer un faux repository (Fake Repository). Ce faux repository est une implémentation simple de l'interface du repository,
    // mais sans accès à une base de données réelle. Il agit comme une base en mémoire.
    private Map<Long, Instructor> instructorDB = new HashMap<>();

    @Override
    public Instructor save(Instructor instructor) {
        instructorDB.put(instructor.getNumInstructor(), instructor);
        return instructor;
    }

    @Override
    public List<Instructor> findAll() {
        return null;
    }

    @Override
    public List<Instructor> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Instructor> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Instructor> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Instructor entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Instructor> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends Instructor> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Instructor> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Instructor> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Instructor> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Instructor> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Instructor getOne(Long aLong) {
        return null;
    }

    @Override
    public Instructor getById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Instructor> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Instructor> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Instructor> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Instructor> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Instructor> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Instructor> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Instructor, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}