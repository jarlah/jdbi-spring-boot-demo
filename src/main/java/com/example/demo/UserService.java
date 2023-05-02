package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final Jdbi jdbi;

    public UserService(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    @PostConstruct
    public void init() {
        testThatTransactionWorksProperly();
    }

    public void create() {
        UserDao dao = jdbi.onDemand(UserDao.class);
        dao.useTransaction(transactionDao -> {
            transactionDao.createTable();
            transactionDao.insertNamed(1, "Test1");
            transactionDao.insertNamed(2, "Test2");
            transactionDao.insertNamed(3, "Test3");
            throw new DemoException("Something failed");
        });
    }

    public void testThatTransactionWorksProperly() {
        try {
            create();
        } catch (DemoException ignored) {}
        jdbi.withExtension(UserDao.class, userDao -> {
            if (userDao.listUsers().size() != 0) {
                throw new IllegalStateException("User list should be empty");
            }
            return null;
        });
    }
}
