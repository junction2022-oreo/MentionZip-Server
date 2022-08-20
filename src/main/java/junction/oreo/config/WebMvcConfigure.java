package junction.oreo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import junction.oreo.repositories.MemberRepository;
import junction.oreo.resolver.MidResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfigure implements WebMvcConfigurer {

    private final MemberRepository memberRepository;


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(midResolver());
    }

    @Bean
    public MidResolver midResolver() {
        return new MidResolver(memberRepository);
    }
}
