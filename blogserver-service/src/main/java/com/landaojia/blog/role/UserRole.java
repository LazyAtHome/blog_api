package com.landaojia.blog.role;

public enum UserRole {

    ADMIN("admin"), EDITOR("editor"), GUEST("guest");

    private final String value;

    public String getValue() {
        return value;
    }

    UserRole(String value) {
        this.value = value;
    }
    
    public static UserRole getRole(String roleName) {
        for (UserRole role : UserRole.values()) {
            if (role.getValue().equals(roleName)) return role;
        }
        return null;
    }
}
