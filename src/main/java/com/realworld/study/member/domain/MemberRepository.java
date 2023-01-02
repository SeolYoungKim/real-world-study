package com.realworld.study.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    Optional<Member> findByEmail(Email email);

    boolean existsByMemberName(String memberName);
}
