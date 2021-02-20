package test.task.lamanosova.model;

public class CategoryModel {
    private String name;
    private String dir;


    public CategoryModel(String name, String hash) {
        this.name = name;
        this.dir = hash;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

}
