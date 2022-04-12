package com.accountbook.domain.repository.user;

import com.accountbook.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * UserRepository
 *
 * @author donggun
 * @since 2021/11/23
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    // 회원 정보 조회
    Optional<User> findById(String userId);

    // 회원 정보 조회 by token
    User findByToken(String token);

    @Modifying(clearAutomatically = true)
    @Query("update User u set u.expireDate = :expireDate where u.token = :token")
    int updateExpireDateByToken(@Param("token") String token, @Param("expireDate") LocalDateTime expireDate);

    // 이름, 이메일 기반 사용자 정보 조회 -> 아이디 찾기
    Optional<User> findByNameAndEmail(String name, String email);

    // 아이디, 이메일 기반 사용자 정보 조회 -> 패스워드 찾기
    Optional<User> findByIdAndEmail(String userId, String email);

    // 사용자 정보 삭제
    void deleteById(String userId);
}
