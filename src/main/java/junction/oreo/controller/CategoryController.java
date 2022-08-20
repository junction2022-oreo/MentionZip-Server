package junction.oreo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import junction.oreo.enums.response.CodeEnum;
import junction.oreo.response.CommonResponse;
import junction.oreo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Category 조회",
            parameters = {
                    @Parameter(in = ParameterIn.HEADER,
                            name = "X-MID",
                            description = "mid",
                            required = true,
                            schema = @Schema(type = "string"))
            }
    )
    @GetMapping
    public CommonResponse getCategory() {
        return CommonResponse.builder()
                             .returnCode(CodeEnum.SUCCESS.getCode())
                             .info(categoryService.getCategory())
                             .build();
    }
}
