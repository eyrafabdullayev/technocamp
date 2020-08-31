package com.ecommerce.security;

public enum ApplicationUserPermission {
    STORE_ADD("store:add"),
    STORE_UPDATE("store:update"),
    STORE_REMOVE("store:remove");

    private final String permission;

    ApplicationUserPermission(String permission){
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
