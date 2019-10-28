package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Product;
import com.codegym.model.ProductForm;
import com.codegym.service.CategoryService;
import com.codegym.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    Environment env;

    @ModelAttribute("categories")
    public Iterable<Category> viewCategory() {
        return categoryService.findAll();
    }

    @GetMapping("/products-laptop")
    public ModelAndView listLaptop() {
        Category category = categoryService.findById((long) 3.0);
        Iterable<Product> products = productService.findAllByCategory(category);
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products",products);
        return modelAndView;

    }
    @GetMapping("/create-product")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/create-product")
    public ModelAndView createProduct(@Valid @ModelAttribute ProductForm productForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/product/create");
        }
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Product product = new Product(productForm.getName(), fileName, productForm.getPrice(), productForm.getAmount(), productForm.getDescription(), productForm.getCreateDate(), productForm.getCategory());
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("productForm", new ProductForm());
        modelAndView.addObject("ms", "Create Product successfully!");
        return modelAndView;

    }

    @GetMapping("/products")
    public ModelAndView listProduct() {
        ModelAndView modelAndView = new ModelAndView("/product/list");
        Iterable<Product> products = productService.findAll();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/edit-product/{id}")
    public ModelAndView showEditForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
        if (product == null) {
            return new ModelAndView("/product/error");
        }
        ProductForm productForm = new ProductForm(product.getId(), product.getName(), null, product.getPrice(), product.getAmount(), product.getDescription(), product.getCreateDate(), product.getCategory());
        ModelAndView modelAndView = new ModelAndView("product/edit");
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("product", product);
        return modelAndView;
    }

    @PostMapping("/edit-product")
    public ModelAndView updateProduct(@ModelAttribute ProductForm productForm, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("/product/edit");
        }
        MultipartFile multipartFile = productForm.getImage();
        String fileName = multipartFile.getOriginalFilename();
        String fileUpload = env.getProperty("file_upload").toString();
        try {
            FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Product product = new Product(productForm.getId(), productForm.getName(), fileName, productForm.getPrice(), productForm.getAmount(), productForm.getDescription(), productForm.getCreateDate(), productForm.getCategory());
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("productForm", productForm);
        modelAndView.addObject("product", product);
        modelAndView.addObject("ms", "Create successfully!");
        return modelAndView;
    }

    @GetMapping("/delete-product/{id}")
    public ModelAndView showDeleteForm(@PathVariable("id") Long id) {
        Product product = productService.findById(id);
       ModelAndView modelAndView = new ModelAndView("/product/delete");
       modelAndView.addObject("product",product);
       return modelAndView;
    }
    @PostMapping("/delete-product")
    public String deleteProduct(@ModelAttribute Product product) {
        productService.remove(product.getId());
        return "redirect:products";
    }
}
