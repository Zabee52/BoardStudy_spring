package com.sparta.assignment.service;

import com.sparta.assignment.dto.SignUpRequestDto;
import com.sparta.assignment.models.User;
import com.sparta.assignment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    /*
        - 닉네임은 `최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)`로 구성하기
        - 비밀번호는 `최소 4자 이상이며, 닉네임과 같은 값이 포함된 경우 회원가입에 실패`로 만들기
        - 비밀번호 확인은 비밀번호와 정확하게 일치하기
        - 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "중복된 닉네임입니다." 라는 에러메세지를 프론트엔드에서 보여주기
        - 회원가입 버튼을 누르고 에러메세지가 발생하지 않는다면 `로그인 페이지`로 이동시키기
     */
    public String signUp(SignUpRequestDto signUpRequestDto) {
        // 회원가입 작업
        // return message
        String nickname = signUpRequestDto.getNickname();

        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        // 유효성 검사
        if (!isNicknameValid(nickname)) {
            throw new IllegalArgumentException("닉네임은 3자 이상이어야 하며, 대소문자와 숫자로만 구성되어야 합니다.");
        }

        String password = signUpRequestDto.getPassword();
        if (!isPasswordValid(password, nickname)) {
            throw new IllegalArgumentException("비밀번호는 4자 이상이어야 하며, 닉네임이 포함되면 안 됩니다.");
        }

        String passwordCheck = signUpRequestDto.getPasswordCheck();
        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인란의 값이 다릅니다.");
        }

        // 패스워드 암호화
        String encryptedPassword = passwordEncoder.encode(password);

        User user = new User(nickname, encryptedPassword);
        userRepository.save(user);

        return "회원가입 완료";
    }


    private boolean isNicknameValid(String nickname) {
        boolean isNicknameValid = true;

        // 닉네임은 최소 3자 이상
        if (nickname.length() >= 3) {
            // 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
            // regexp 사용해도 되지만 알고리즘 문제 풀듯이 풀어봤다.
            char[] chars = nickname.toCharArray();
            for (char c : chars) {
                if (!((c >= 'a' && c <= 'z') ||
                        (c >= 'A' && c <= 'Z') ||
                        (c >= '0' && c <= '9'))) {
                    isNicknameValid = false;
                    break;
                }
            }
        } else {
            isNicknameValid = false;
        }

        return isNicknameValid;
    }

    private boolean isPasswordValid(String password, String nickname) {
        boolean isPasswordValid = true;

        // 비밀번호는 최소 4자 이상
        if (password.length() >= 4) {
            // 닉네임과 같은 값이 포함된 경우 회원가입에 실패
            if (password.contains(nickname)) {
                isPasswordValid = false;
            }
        } else {
            isPasswordValid = false;
        }

        return isPasswordValid;
    }
}
