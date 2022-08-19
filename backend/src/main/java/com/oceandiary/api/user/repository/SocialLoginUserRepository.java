package com.oceandiary.api.user.repository;

import com.oceandiary.api.user.domain.SocialProvider;
import com.oceandiary.api.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import static com.oceandiary.api.user.entity.QUser.user;


@Repository
public class SocialLoginUserRepository {
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public SocialLoginUserRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public User findByProviderAndOauthId(SocialProvider provider, String oauthId) {
        return queryFactory
                .selectFrom(user)
                .where(user.provider.eq(provider), user.oauthId.eq(oauthId), user.deletedAt.isNull())
                .fetchOne();
    }

    public void save(User user) {
        em.persist(user);
    }

}
