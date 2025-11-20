package com.anoop.Service;

import com.anoop.DTO.LoginDTO;
import com.anoop.DTO.SignupDTO;
import com.anoop.DTO.UnlockAccountDTO;

public interface UserDetailsService {
    public Boolean signup(SignupDTO data);

    public Boolean login(LoginDTO data);

    public String unlockAccount(UnlockAccountDTO data);

    public Boolean forgotPwd(String email);
    
    public Boolean logout();

}
