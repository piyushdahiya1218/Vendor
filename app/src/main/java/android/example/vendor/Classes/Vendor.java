package android.example.vendor.Classes;

import java.util.ArrayList;

public class Vendor {

    String businessname, username, phonenumber, producttype;

    public Vendor() {
    }

    public Vendor(String businessname, String username, String phonenumber, String producttype) {
        this.businessname = businessname;
        this.username = username;
        this.phonenumber = phonenumber;
        this.producttype = producttype;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
    }

}
