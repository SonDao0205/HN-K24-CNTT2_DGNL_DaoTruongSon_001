package com.dgnl.artwork.service;

import com.dgnl.artwork.model.Category;
import com.dgnl.artwork.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public boolean save(Category category) {
        if(category != null) {
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    public Category getCategoryById(Long categoryId) {
        for(Category category : categoryRepository.findAll()) {
            if(category.getId() == categoryId) {
                return category;
            }
        }
        return null;
    }

    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Page<Category> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        return categoryRepository.findAll(pageable);
    }
}
