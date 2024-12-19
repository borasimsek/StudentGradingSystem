package com.example.sgs.model;

import java.util.List;
/**
 * Represents an Admin in the system.
 * Extends the abstract User class.
 */
public class Admin extends User {

    @Override
    public String getRole() {
        return "Admin";
    }
    private String adminId;
    private List<String> permissions;

    // Constructor kısmı
    public Admin(String id, String name, String email, String password,
                    String adminId, List<String> permissions) {
        super(id, name, email, password);
        this.adminId = adminId;
        this.permissions = permissions;
    }

    // Getters and Setters
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
