package com.example.gamja.repository;

import com.example.gamja.dto.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/user-repository-test-data.sql")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void findByIdAndStatus_로_유저데이터를_찾아오기(){
        Optional<UserEntity> findByUser = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);
        assertThat(findByUser.isPresent()).isFalse();
    }

    @Test
    void findByIdAndStatus_로_유저데이터_없으면_에러(){
        Optional<UserEntity> findByUser = userRepository.findByIdAndStatus(1, UserStatus.PENDING);
        assertThat(findByUser.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_이메일로_조회(){
        Optional<UserEntity> findByEmailUser = userRepository.findByEmailAndStatus("test1@gmail.com", UserStatus.ACTIVE);
        assertThat(findByEmailUser.isEmpty()).isFalse();
    }
}