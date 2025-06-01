package repository;

import model.Entity;

public interface IRepository<ID, E extends Entity<ID>> {
    E findById(ID id);
    Iterable<E> findAll();
    E save(E entity);
    void delete(ID id);
    void update(E entity);
}