package com.example.demo;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import java.util.List;

public interface UserDao extends Transactional<UserDao> {
    @SqlUpdate("CREATE TABLE \"user\" (id INTEGER PRIMARY KEY, \"name\" VARCHAR)")
    void createTable();

    @SqlUpdate("INSERT INTO \"user\" (id, \"name\") VALUES (:id, :name)")
    void insertNamed(@Bind("id") int id, @Bind("name") String name);

    @SqlQuery("SELECT * FROM \"user\" ORDER BY \"name\"")
    @RegisterBeanMapper(User.class)
    List<User> listUsers();
}
