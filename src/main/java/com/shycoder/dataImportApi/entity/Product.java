package com.shycoder.dataImportApi.entity;

import javax.persistence.*;

@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private Long ean;
    private String name;
    private String h1;
    private String secondName;
    private String brand;
    private String author;
    private String otherCategory;
    private String mainCategory;
    private String attributeCategories;
    private String imageUrl;
    private String pageLink;
    private String metaTitle;
    private String metaKeywords;
    private String metaDescription;
    private String description;
    private String canonicalLink;
    private Boolean noIndexFlag;
    private String isbn;
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getEan() {
        return ean;
    }

    public void setEan(Long ean) {
        this.ean = ean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getH1() {
        return h1;
    }

    public void setH1(String h1) {
        this.h1 = h1;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getOtherCategory() {
        return otherCategory;
    }

    public void setOtherCategory(String otherCategory) {
        this.otherCategory = otherCategory;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getAttributeCategories() {
        return attributeCategories;
    }

    public void setAttributeCategories(String attributeCategories) {
        this.attributeCategories = attributeCategories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPageLink() {
        return pageLink;
    }

    public void setPageLink(String pageLink) {
        this.pageLink = pageLink;
    }

    public String getMetaTitle() {
        return metaTitle;
    }

    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }

    public String getMetaKeywords() {
        return metaKeywords;
    }

    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }

    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCanonicalLink() {
        return canonicalLink;
    }

    public void setCanonicalLink(String canonicalLink) {
        this.canonicalLink = canonicalLink;
    }

    public Boolean getNoIndexFlag() {
        return noIndexFlag;
    }

    public void setNoIndexFlag(Boolean noIndexFlag) {
        this.noIndexFlag = noIndexFlag;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", ean=" + ean +
                ", name='" + name + '\'' +
                ", h1='" + h1 + '\'' +
                ", secondName='" + secondName + '\'' +
                ", brand='" + brand + '\'' +
                ", author='" + author + '\'' +
                ", otherCategory='" + otherCategory + '\'' +
                ", mainCategory='" + mainCategory + '\'' +
                ", attributeCategories='" + attributeCategories + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", pageLink='" + pageLink + '\'' +
                ", metaTitle='" + metaTitle + '\'' +
                ", metaKeywords='" + metaKeywords + '\'' +
                ", metaDescription='" + metaDescription + '\'' +
                ", description='" + description + '\'' +
                ", canonicalLink='" + canonicalLink + '\'' +
                ", noIndexFlag=" + noIndexFlag +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
