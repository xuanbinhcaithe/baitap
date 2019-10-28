package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.ManyToOne;
import javax.validation.Valid;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/create-category")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        return modelAndView;
    }
    @PostMapping("/create-category")
    public ModelAndView createCategory(@Valid @ModelAttribute Category category , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/category/create");
            return modelAndView;
        }
        categoryService.save(category);
        ModelAndView modelAndView = new ModelAndView("/category/create");
        modelAndView.addObject("category",new Category());
        modelAndView.addObject("ms","create category successfully!");
        return modelAndView;
    }

    @GetMapping("/categories")
    public ModelAndView listCategory() {
        Iterable<Category> categories = categoryService.findAll();
        ModelAndView modelAndView = new ModelAndView("/category/list");
        modelAndView.addObject("categories",categories);
        return modelAndView;
    }
    @GetMapping("/edit-category/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null){
            ModelAndView modelAndView = new ModelAndView("/category/error");
            return modelAndView;
        }
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        modelAndView.addObject("category",category);
        return modelAndView;
    }
    @PostMapping("/edit-category")
    public ModelAndView updateCategory(@Valid @ModelAttribute Category category,BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/category/edit");
        }
        ModelAndView modelAndView = new ModelAndView("/category/edit");
        categoryService.save(category);
        modelAndView.addObject("category", category);
        modelAndView.addObject("ms","update category successfully!");
        return modelAndView;
    }
    @GetMapping("/delete-category/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return new ModelAndView("/category/error");
        }
        ModelAndView modelAndView = new ModelAndView("/category/delete");
        modelAndView.addObject("category",category);
       return modelAndView;
    }
    @PostMapping("/delete-category")
    public String deleteCategory(@ModelAttribute Category category) {
        categoryService.remove(category.getId());
        return "redirect:categories";
    }

}
