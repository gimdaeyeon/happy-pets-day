package com.example.happypetsday.security;

import com.example.happypetsday.dto.UserDto;
import com.example.happypetsday.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// UserDetailsService
// 컨테이너에 등록한다.
// 시큐리티 설정에서 loginProcessingUrl(url) 을 설정해 놓았기에
// 로그인시 위 url 로 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어 있는
// loadUserByUsername() 가 실행되고
// 인증성공하면 결과를 UserDetails 로 리턴
@Service
@RequiredArgsConstructor
public class PrincipalDetailService implements UserDetailsService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // DB 조회
        UserDto user = userService.findUserInfoByUserId(username);

        // 해당 username 의 User 가 DB 에 있었다면
        // UserDetails 를 생성하여 리턴
        if(user != null){
            System.out.println(BCrypt.hashpw("aaa", BCrypt.gensalt(10)));

//            passwordEncoder.

            PrincipalDetails userDetails = new PrincipalDetails(user);
            userDetails.setUserService(userService);
            System.out.println(userDetails.getUser().getUserPassword());
            return userDetails;
        }

        // 해당 username 이 User 가 없다면?
        // UsernameNotFoundException 을 throw 해주어야 한다
        throw new UsernameNotFoundException(username);
        // ※ 주의! 여기서 null 리턴하면 예외 발생
    }
}









