package com.example.gamja.service;

import com.example.gamja.dto.UserCreateDto;
import com.example.gamja.dto.UserStatus;
import com.example.gamja.dto.UserUpdateDto;
import com.example.gamja.exception.ResourceNotFoundException;
import com.example.gamja.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql("/sql/user-service-test-data.sql")
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockitoBean
    private JavaMailSender mailSender;


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

        assertThatThrownBy(()->{
            UserEntity user=userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }
    @Test
    void getById_ACTIVE_상태만_가져온다(){
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

    @Test
    void Create_유저_생성(){

        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("test10@gmail.com")
                .address("seoul")
                .nickname("test")
                .build();

        BDDMockito.doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        UserEntity user=userService.create(userCreateDto);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
    }

    @Test
    void Create_유저_정보_수정(){

        UserUpdateDto userUpdateDtoe = UserUpdateDto.builder()
                .address("mont")
                .nickname("kkk")
                .build();

        userService.update(85, userUpdateDtoe);

        UserEntity user=userService.getById(85);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("mont");
        assertThat(user.getNickname()).isEqualTo("kkk");
    }


}
