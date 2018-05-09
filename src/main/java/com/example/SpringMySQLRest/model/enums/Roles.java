package com.example.SpringMySQLRest.model.enums;

/**
 * @author David
 */
public enum Roles {
USER("USER"),ADMIN("ADMIN"),MASTER("MASTER");
private String roleName;

    private Roles(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }


}
