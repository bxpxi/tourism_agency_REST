package model;

public interface Entity<T> {
    T getId();
    void setId(T id);
}