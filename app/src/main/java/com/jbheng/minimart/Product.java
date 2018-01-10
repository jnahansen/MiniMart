package com.jbheng.minimart;

/**
 * The {@link Product} class.
 * <p>Defines the attributes for a product.</p>
 */
class Product {

    private final String name;
    private final String description;
    private final String price;
    private final String category;
    private final String imageName;

    public Product(String name, String description, String price, String category,
                   String imageName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getImageName() {
        return imageName;
    }
}
