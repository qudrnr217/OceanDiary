package com.oceandiary.api;

import com.oceandiary.api.category.entity.Category;
import com.oceandiary.api.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final CategoryRepository categoryRepository;


    @Override
    public void run(String... args) throws Exception {
        if (categoryRepository.findAll().isEmpty()) {
            List<Category> categoryList = new ArrayList<Category>();
            String[] categoryIds = new String[] {"OCEAN", "LIBRARY", "CAFE", "FESTIVAL", "HOME"};
            String[] categoryNames = new String[] {"바다", "도서관", "카페", "축제", "집"};
            for (int i = 0; i < 5; i++) {
                Category category = Category.builder().id(categoryIds[i]).name(categoryNames[i]).build();
                categoryList.add(category);
            }
            categoryRepository.saveAll(categoryList);
        }
    }
}
