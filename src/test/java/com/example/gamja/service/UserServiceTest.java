package com.example.gamja.service;

import com.example.gamja.exception.ResourceNotFoundException;
import com.example.gamja.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/user-service-test-data.sql")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail_ACTIVE_상태만_가져온다(){
        //given
        String email = "test3@gmail.com";

        //when
        UserEntity user=userService.getByEmail(email);

        //then
        assertThat(user.getNickname()).isEqualTo("test");
    }

    @Test
    void getByEmail_PENDING_은_가져_올_수_없다(){
        //given
        String email = "test4@gmail.com";

        //when

        //then
        assertThatThrownBy(()->{
            UserEntity user=userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
    @Test
    void getById_ACTIVE_상태만_가져온다(){
        //given

        //when
        UserEntity user=userService.getById(85);

        //then
        assertThat(user.getNickname()).isEqualTo("test");
    }

    @Test
    void getById_PENDING_은_가져_올_수_없다(){
        //then
        assertThatThrownBy(()->{
            UserEntity user=userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

}
