package com.codegym.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "ten khong duoc de trong")
    private String name;
    private String image;
    private Double price;
    private Long amount;
    private String description;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Product() {
    }

    public Product(@NotEmpty(message = "ten khong duoc de trong") String name, String image, Double price, Long amount, String description, Date createDate, Category category) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.createDate = createDate;
        this.category = category;
    }

    public Product(Long id, @NotEmpty(message = "ten khong duoc de trong") String name, String image, Double price, Long amount, String description, Date createDate, Category category) {
        this.name = name;
        this.image = image;
        this.price = price;
        this.amount = amount;
        this.description = description;
        this.createDate = createDate;
        this.category = category;
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
