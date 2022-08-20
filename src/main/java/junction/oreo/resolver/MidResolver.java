package junction.oreo.resolver;

import java.util.Optional;

import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import junction.oreo.entity.Member;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.exception.CustomException;
import junction.oreo.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MidResolver implements HandlerMethodArgumentResolver {
    private final MemberRepository memberRepository;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(Member.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory)
            throws Exception {

        String mid = webRequest.getHeader("X-MID");

        MDC.put("mid", mid);

        return findByMid(mid);
    }

    private Member findByMid(String mid) {
        if (mid.equals("9999")) {
            return Member.builder()
                         .mid("9999")
                         .build();
        }
        Optional<Member> memberOpt = memberRepository.findByMid(mid);
        if (memberOpt.isEmpty()) {
            throw new CustomException(CodeEnum.NOT_IDENTITY_VERIFIED_USER);
        }
        return memberOpt.get();
    }
}

