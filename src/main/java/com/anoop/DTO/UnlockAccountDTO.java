package com.anoop.DTO;

import lombok.Data;

@Data
public class UnlockAccountDTO {
    private String email;
    private String temporaryPwd;
    private String newPwd;
    private String comfirmPwd;
}
