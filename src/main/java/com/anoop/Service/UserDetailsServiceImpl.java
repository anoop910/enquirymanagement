package com.anoop.Service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anoop.DTO.LoginDTO;
import com.anoop.DTO.SignupDTO;
import com.anoop.DTO.UnlockAccountDTO;
import com.anoop.Entity.UserDetailsEntity;
import com.anoop.Repository.UserDetailsRepo;
import com.anoop.Utility.EmailUtils;
import com.anoop.Utility.PasswordUtils;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Autowired
    private HttpSession session;

    @Override
    @Transactional
    public Boolean signup(SignupDTO data) {

        UserDetailsEntity userDetailsEntity = userDetailsRepo.findByEmail(data.getEmail());
        if (userDetailsEntity != null) {
            return false;
        }


        UserDetailsEntity entity = new UserDetailsEntity();
        BeanUtils.copyProperties(data, entity);

        String tempPwd = PasswordUtils.PasswordGenerator();

        entity.setAccStatus("LOCKED");
        entity.setPassword(tempPwd);

        userDetailsRepo.save(entity);

        String toAddress = data.getEmail();
        String subject = "Ashok IT - Temporary Password to Unlock Your Account";

        StringBuilder body = new StringBuilder();
        body.append("<h2>The below temporary password can be used to unlock your account.</h2>");
        body.append("<p><strong>Temporary Password:</strong></p>");
        body.append("<input type='text' value='" + tempPwd
                + "' readonly style='padding:8px;font-size:16px;border:1px solid #ccc;border-radius:4px;width:200px;'>");
        body.append("<p><a href=\"http://localhost:8080/unlock?email=" + toAddress
                + "\">Click Here to Unlock Your Account</a></p>");

        Boolean isSent = emailUtils.sendEmailForTempPassword(toAddress, subject, body.toString());

        if (isSent) {
            return true;
        }

        return false;
    }

    @Override
    public Boolean login(LoginDTO data) {
        String email = data.getEmail();
        String password = data.getPassword();
        UserDetailsEntity userDetailsEntity = userDetailsRepo.findByEmailAndPassword(email, password);
        if (userDetailsEntity == null) {
            return false;
        }
        if (userDetailsEntity.getEmail().equals(email) && userDetailsEntity.getPassword().equals(password) && !userDetailsEntity.getAccStatus().equals("LOCKED")) {
            userDetailsEntity.setAccStatus("LOGIN");
            userDetailsRepo.save(userDetailsEntity);
            session.setAttribute("userId", userDetailsEntity.getId());
            return true;
        }
        return false;

    }

    @Override
    public String unlockAccount(UnlockAccountDTO data) {

        UserDetailsEntity userDetailsEntity = userDetailsRepo.findByEmail(data.getEmail());

        if (data.getNewPwd().equals(data.getComfirmPwd())) {
            if (userDetailsEntity.getPassword().equals(data.getTemporaryPwd())) {
                userDetailsEntity.setAccStatus("UNLOCKED");
                userDetailsEntity.setPassword(data.getNewPwd());
                userDetailsRepo.save(userDetailsEntity);
            } else {
                return "Invalid Temporary Password";
            }

        } else {
            return "New Password and Comfirm Password should be same";
        }

        return "success";
    }

    @Override
    public Boolean forgotPwd(String email) {
      UserDetailsEntity userDetailsEntity = userDetailsRepo.findByEmail(email);
      if (userDetailsEntity == null || userDetailsEntity.getAccStatus().equals("LOCKED")) {
        return false;
      }
      String  pass = userDetailsEntity.getPassword();
      String subject = "Ashok IT | Password Recover";
      String body = "<h5> Your Password is : </h5>" + pass;
        emailUtils.sendEmailForTempPassword(email, subject, body);
       return true;
    }

    @Override
    public Boolean logout() {
        session.invalidate();
        System.out.println("Logout Method calling");
        return true;
    }
    

}
