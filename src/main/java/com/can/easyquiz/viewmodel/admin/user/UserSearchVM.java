package com.can.easyquiz.viewmodel.admin.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchVM {

    @NotBlank
    private String name;
}
