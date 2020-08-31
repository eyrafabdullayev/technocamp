package com.ecommerce.form;

import javax.validation.constraints.NotNull;

public class UserAccountForm {

    @NotNull
    private Integer id;
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
    @NotNull
    private String newPasswordAgain;

    public UserAccountForm(@NotNull Integer id, @NotNull String currentPassword, @NotNull String newPassword, @NotNull String newPasswordAgain) {
        this.id = id;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordAgain = newPasswordAgain;
    }

    public Integer getId() {
        return id;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }
}
