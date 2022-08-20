package junction.oreo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import junction.oreo.entity.User;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.exception.CustomException;
import junction.oreo.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

/**
 * Bean 네임을 지정해줘야
 * org.springframework.security.provisioning.InMemoryUserDetailsManager#loadUserByUsername(java.lang.String)에서
 * 현재 파일(=CustomUserDetailsService)에 정의되어 있는 loadUserByUsername() 메소드를 사용하게 된다.
 *
 * Bean 네임을 지정하지 않으면
 * InMemoryUserDetailsManager에 있는 loadUserByUsername() 메소드를 사용한다.
 */
@Component("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) {
        /**
         * 명시적으로 사용처가 보이지 않지만
         * org.springframework.security.authentication.dao.DaoAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
         * 에서 loadUserByUsername()를 사용한다.
         * 그러므로 해당 파일이 존재하지 않으면 에러가 발생한다.
         */
        return userRepository.findOneWithAuthoritiesByUsername(username).map(user -> createUser(username, user))
                             .orElseThrow(() -> new CustomException(CodeEnum.NOT_IDENTITY_VERIFIED_USER));
    }

    private org.springframework.security.core.userdetails.User createUser(String username, User user) {
        if (!user.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }

        List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream().map(
                authority -> new SimpleGrantedAuthority(authority.getAuthorityName())).collect(
                Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                                                                      grantedAuthorities);
    }
}
