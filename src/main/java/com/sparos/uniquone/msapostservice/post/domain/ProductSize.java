package com.sparos.uniquone.msapostservice.post.domain;

public enum ProductSize {
    S("Small"),
    M("Medium"),
    L("Lage"),
    XL("XLage"),
    FR("Free");
    String size;

    ProductSize(String size) {
        this.size = size;
    }

    public String value() {
        return size;
    }
}
