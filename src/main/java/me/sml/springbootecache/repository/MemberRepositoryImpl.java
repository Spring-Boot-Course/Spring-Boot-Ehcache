package me.sml.springbootecache.repository;

import lombok.extern.slf4j.Slf4j;
import me.sml.springbootecache.domain.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository{

    Logger logger = LoggerFactory.getLogger(MemberRepositoryImpl.class);
    @Override
    public Member findByNameNoCache(String name) {
        slowQuery(2000);
        return new Member(0, name+"@gmail.com", name);
    }

    @Override
    @Cacheable(value = "findMemberCache", key = "#name")
    public Member findByNameCache(String name) {
        slowQuery(2000);
        return new Member(0, name+"@gmail.com", name);
    }

    @Override
    @CacheEvict(value = "findMemberCache", key = "#name")
    public void refresh(String name) {
        logger.info(name + "의 캐시 제거");
    }

    private void slowQuery(long seconds){
        try {
            Thread.sleep(seconds);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
