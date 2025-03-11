package com.example.gamja.repository;

import com.example.gamja.dto.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void UserRepositoryTest_제대로_연결_되었나(){
        UserEntity user=new UserEntity();
        user.setEmail("test@gmail.com");
        user.setAddress("seoul");
        user.setNickname("test");
        user.setStatus(UserStatus.ACTIVE);
        user.setCertificationCode("aaaa-aaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //when
        UserEntity savedUser=userRepository.save(user);

        //then
        assertThat(savedUser.getId()).isEqualTo(user.getId()).isNotNull();

    }

    @Test
    void findByIdAndStatus_로_유저데이터를_찾아오기(){
        UserEntity user=new UserEntity();
        user.setId(4L);
        user.setEmail("test@gmail.com");
        user.setAddress("seoul");
        user.setNickname("test");
        user.setStatus(UserStatus.ACTIVE);
        user.setCertificationCode("aaaa-aaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //when
        userRepository.save(user);
        Optional<UserEntity> findByUser=userRepository.findByIdAndStatus(4,UserStatus.ACTIVE);

        //then
        assertThat(findByUser.isPresent()).isTrue();

    }

    @Test
    void findByIdAndStatus_로_유저데이터_없으면_에러(){
        UserEntity user=new UserEntity();
        user.setId(4L);
        user.setEmail("test@gmail.com");
        user.setAddress("seoul");
        user.setNickname("test");
        user.setStatus(UserStatus.ACTIVE);
        user.setCertificationCode("aaaa-aaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //when
        userRepository.save(user);
        Optional<UserEntity> findByUser=userRepository.findByIdAndStatus(4,UserStatus.PENDING);

        //then
        assertThat(findByUser.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus_이메일로_조회(){
        UserEntity user=new UserEntity();
        user.setId(4L);
        user.setEmail("test@gmail.com");
        user.setAddress("seoul");
        user.setNickname("test");
        user.setStatus(UserStatus.ACTIVE);
        user.setCertificationCode("aaaa-aaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

        //when
        userRepository.save(user);
        Optional<UserEntity> findByEmailUser=userRepository.findByEmailAndStatus("test@gmail.com",UserStatus.ACTIVE);

        //then
        assertThat(findByEmailUser.isPresent()).isTrue();
    }
}
