package com.sparta.assignment.service;

import com.sparta.assignment.dto.SignUpRequestDto;
import com.sparta.assignment.models.User;
import com.sparta.assignment.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;

    String nickname;
    String password;
    String passwordCheck;

    @BeforeEach
    void setup() {
        nickname = "test";
        password = "1234";
        passwordCheck = "1234";
    }

    public String signUp(SignUpRequestDto signUpRequestDto) {
        // 회원가입 작업
        // return message
        String nickname = signUpRequestDto.getNickname();
        String password = signUpRequestDto.getPassword();

        // 닉네임 중복여부 검사
        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }
        // 유효성 검사
        nicknameValidCheck(nickname);
        passwordValidCheck(password, nickname);

        String passwordCheck = signUpRequestDto.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인란의 값이 다릅니다.");
        }

        return "회원가입 완료";
    }

    private void nicknameValidCheck(String nickname) {
        // 닉네임은 최소 3자 이상
        if (nickname.length() >= 3) {
            // 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
            // regexp 사용해도 되지만 알고리즘 문제 풀듯이 풀어봤다.
            char[] chars = nickname.toCharArray();
            for (char c : chars) {
                if (!((c >= 'a' && c <= 'z') ||
                        (c >= 'A' && c <= 'Z') ||
                        (c >= '0' && c <= '9'))) {
                    throw new IllegalArgumentException("닉네임은 대소문자와 숫자로만 구성되어야 합니다.");
                }
            }
        } else {
            throw new IllegalArgumentException("닉네임은 3자 이상이어야 합니다.");
        }
    }

    private void passwordValidCheck(String password, String nickname) {
        // 비밀번호는 최소 4자 이상
        if (password.length() >= 4) {
            // 닉네임과 같은 값이 포함된 경우 회원가입에 실패
            if (password.contains(nickname)) {
                throw new IllegalArgumentException("비밀번호에 닉네임이 포함되면 안 됩니다.");
            }
        } else {
            throw new IllegalArgumentException("비밀번호는 4자 이상이어야 합니다.");
        }
    }

    @Test
    @DisplayName("Complete case")
    void nickname_test() {
        // given
        SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

        // when
        String result = signUp(signUpRequestDto);

        // then
        assertEquals("회원가입 완료", result);
    }

    @Nested
    @DisplayName("Nickname Invalid case")
    class NicknameInvalidCase {
        @Test
        @DisplayName("Nickname already have")
        void alreadyNickname() {
            // given
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);
            User user = new User(nickname, password);
            when(userRepository.findByNickname(nickname)).thenReturn(Optional.of(user));

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("중복된 닉네임입니다.", exception.getMessage());
        }

        @Test
        @DisplayName("Nickname length < 3")
        void shortNickname() {
            // given
            nickname = "te";
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("닉네임은 3자 이상이어야 합니다.", exception.getMessage());
        }

        @Test
        @DisplayName("Not only Alphabet and Number")
        void weirdNickname() {
            // given
            nickname = "test@";
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("닉네임은 대소문자와 숫자로만 구성되어야 합니다.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Password Invalid case")
    class PasswordInvalidCase {
        @Test
        @DisplayName("Password length < 4")
        void shortPassword() {
            // given
            password = "123";
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("비밀번호는 4자 이상이어야 합니다.", exception.getMessage());
        }

        @Test
        @DisplayName("Password in nickname")
        void passwordInNickname() {
            // given
            password = "test1234";
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("비밀번호에 닉네임이 포함되면 안 됩니다.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("PasswordCheck Invalid case")
    class PasswordCheckInvalidCase {
        @Test
        @DisplayName("Password and PasswordCheck is not equal")
        void passwordCheckIsNotEqual() {
            // given
            passwordCheck = "123";
            SignUpRequestDto signUpRequestDto = new SignUpRequestDto(nickname, password, passwordCheck);

            // when
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                signUp(signUpRequestDto);
            });

            // then
            assertEquals("비밀번호와 비밀번호 확인란의 값이 다릅니다.", exception.getMessage());
        }
    }
}