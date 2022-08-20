package junction.oreo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import junction.oreo.jwt.JwtAccessDeniedHandler;
import junction.oreo.jwt.JwtAuthenticationEntryPoint;
import junction.oreo.jwt.JwtSecurityConfig;
import junction.oreo.jwt.TokenProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(
            TokenProvider tokenProvider,
            CorsFilter corsFilter,
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            JwtAccessDeniedHandler jwtAccessDeniedHandler
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // token 방식을 사용하므로 csrf를 disable로 설정
                .csrf().disable()

                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
//                .antMatchers("/favicon.ico").permitAll()

                /**
                 * permitAll()으로 허용한 Path에 대해서는
                 * 401 응답코드가 발생하지 않고
                 * Spring Security가 없는 거 처럼 동작한다.
                 */
//                .antMatchers("/auth/**").permitAll() // "auth/**"로 하면 정상적으로 동작하지 않으니 반드시 앞에 "/"가 있어야함
//                .antMatchers("/api/login").permitAll()
//                .antMatchers("/api/signup").permitAll()
                .antMatchers("/**").permitAll()
//                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        return httpSecurity.build();
    }

    /**
     * 아래 코드처럼 설정하고 실행시키면 다음과 같은 로그가 출력된다.
     * You are asking Spring Security to ignore Ant [pattern='/open/**'].
     * This is not recommended -- please use permitAll via HttpSecurity#authorizeHttpRequests instead.
     */
//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring()
//                .antMatchers("api/signup");
//    }

}
