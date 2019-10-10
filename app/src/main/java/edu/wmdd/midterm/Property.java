package edu.wmdd.midterm;

public class Property {

    public String name;
    public int id;
//    private String image_url;

    public Property() {

    }

    public Property(String name, int id, String image_url) {
        this.name = name;
        this.id = id;
//        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getImage_url() {
//        return image_url;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

//    public void setImage_url(String image_url) {
//        this.image_url = image_url;
//    }
}