package com.dgnl.artwork.controller;

import com.dgnl.artwork.model.Category;
import com.dgnl.artwork.service.CategoryService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public String listProduct(@RequestParam(defaultValue = "1") int page,
                              Model model) {
        Page<Category> categories = categoryService.findPaginated(page, 5);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("categories", categories.getContent());

        return "categoryList";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("category", new Category());
        return "categoryAdd";
    }

    @PostMapping("/add")
    public String addProduct(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "categoryAdd";
        }
        categoryService.save(category);
        return "redirect:/category/list";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id,Model model) {
        Category findCategory = categoryService.getCategoryById(id);
        if(findCategory == null) {
            return "redirect:/category/list";
        }
        model.addAttribute("category", categoryService.getCategoryById(id));
        return "categoryAdd";
    }

    @PostMapping("/edit")
    public String editProduct(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "categoryAdd";
        }
        categoryService.save(category);
        return "redirect:/category/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/category/list";
    }
}
