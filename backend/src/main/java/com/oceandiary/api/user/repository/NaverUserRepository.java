package com.oceandiary.api.user.repository;

import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.oceandiary.api.user.entity.QUser.user;


@Repository
public class NaverUserRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public NaverUserRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public User findByProviderAndOauthId(SocialProvider provider, String oauthId) {
        return queryFactory
                .selectFrom(user)
                .where(user.provider.eq(provider), user.oauthId.eq(oauthId))
                .fetchOne();
    }

    public void save(User user) {
        em.persist(user);
    }

}
