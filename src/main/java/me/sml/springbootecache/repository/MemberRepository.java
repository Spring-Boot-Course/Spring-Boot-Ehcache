package me.sml.springbootecache.repository;

import me.sml.springbootecache.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository {
    Member findByNameNoCache(String name);

    Member findByNameCache(String name);

    void refresh(String name);
}
