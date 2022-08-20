package junction.oreo.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import junction.oreo.enums.CategoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    public List<CategoryType> getCategory() {
        return Arrays.stream(CategoryType.values()).collect(Collectors.toList());
    }
}
