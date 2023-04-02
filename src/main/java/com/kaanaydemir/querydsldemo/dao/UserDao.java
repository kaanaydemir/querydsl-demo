package com.kaanaydemir.querydsldemo.dao;

import com.kaanaydemir.querydsldemo.entity.QUser;
import com.kaanaydemir.querydsldemo.entity.User;
import com.querydsl.core.group.GroupBy;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    public User findByLogin(String login) {
        final JPAQuery<User> query = new JPAQuery<>(entityManager);
        final QUser user = QUser.user;
        return query.from(user)
                .where(user.login.eq(login))
                .fetchOne();
    }

    public List<User> findByName(String firstName) {
        final JPAQuery<User> query = new JPAQuery<>(entityManager);
        final QUser user = QUser.user;
        return query.from(user)
                .where(user.firstName.eq(firstName))
                .fetch();
    }

    public Integer findyMaxAge() {
        final JPAQuery<User> query = new JPAQuery<>(entityManager);
        final QUser user = QUser.user;

        return query.from(user)
                .select(user.age.max())
                .fetchOne();
    }

    public Map<String,Integer> findMaxAgeByName() {
        final JPAQuery<User> query = new JPAQuery<>(entityManager);
        final QUser user = QUser.user;

        return query.from(user).transform(GroupBy.groupBy(user.firstName).as(user.age.max()));
    }

}
