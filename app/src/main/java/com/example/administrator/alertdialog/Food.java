package com.example.administrator.alertdialog;


class Food {

    private String name;
    private int price;
    private String fav;

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public Food(String name) {
        super();
        this.name = name;
    }
    public Food(String name,String fav) {
        super();
        this.name = name;
        this.fav = fav;
    }

    public Food(String name, int price) {
        super();
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
