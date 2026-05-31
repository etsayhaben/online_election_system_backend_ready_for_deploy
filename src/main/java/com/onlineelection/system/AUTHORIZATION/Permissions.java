package com.onlineelection.system.AUTHORIZATION;

public enum Permissions {
    ADMIN_READ("read"),
    ADMIN_WRITE("write"),
    ADMIN_DELETE("delete"),
    ADMIN_UPDATE("update");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}