package com.chandan.service.impl;

import com.chandan.dto.SalonDTO;
import com.chandan.model.Category;
import com.chandan.repository.CategoryRepository;
import com.chandan.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
@Service
@RequiredArgsConstructor
public class CategoryServiceimpl implements CategoryService {


    private final CategoryRepository categoryRepository;


    @Override
    public Category saveCategory(Category category, SalonDTO salonDTO) {
        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setSalonId(salonDTO.getId());
        newCategory.setImage(category.getImage());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long id) {
        return categoryRepository.findBySalonId(id);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            throw new Exception("category not exist with id" + id);
        }
        return category;
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) throws Exception {

        Category category=getCategoryById(id);
        if (!category.getSalonId().equals(salonId)) {
            throw new Exception("You dont have permission to delete category " + id);
        }
        categoryRepository.deleteById(id);

    }
}
