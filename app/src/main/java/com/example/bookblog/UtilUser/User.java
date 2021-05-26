package com.example.bookblog.UtilUser;

public class User {
    private String email;
    private String name;
    private String pictureURL;
    private String sub;
    private String grantedScope;


    public User(String email, String name, String pictureURL, String grantedScope , String sub) {
        this.email = email;
        this.name = name;
        this.pictureURL = pictureURL;
        this.grantedScope = grantedScope;
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public String getGrantedScope() {
        return grantedScope;
    }

    public String getName() {
        return name;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public Boolean hasScope(String scope) {
        return grantedScope.contains(scope);
    }

    public String getSub() {
        return sub;
    }

}
