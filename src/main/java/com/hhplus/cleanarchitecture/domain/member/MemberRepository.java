package com.hhplus.cleanarchitecture.domain.member;

import org.springframework.data.jpa.repository.*;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
