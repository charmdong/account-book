package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 Repo
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    // 회원 정보 조회
    Optional<User> findById(String userId);

    // 이름, 이메일 기반 사용자 정보 조회 -> 아이디 찾기
    Optional<User> findByNameAndEmail(String name, String email);

    // 아이디, 이메일 기반 사용자 정보 조회 -> 패스워드 찾기
    Optional<User> findByIdAndEmail(String userId, String email);

    // 사용자 정보 삭제
    int deleteById(String userId);
}
