/**
 * UserController handles user authentication and account management operations.
 * <p>
 * This controller provides endpoints for:
 * <ul>
 *   <li>Login and logout</li>
 *   <li>User signup</li>
 *   <li>Account unlocking</li>
 *   <li>Forgot password functionality</li>
 * </ul>
 * It interacts with the {@link UserDetailsService} to perform business logic and manages session state.
 * </p>
 *
 * Endpoints:
 * <ul>
 *   <li><b>GET /login</b>: Display login page</li>
 *   <li><b>POST /login</b>: Handle login form submission</li>
 *   <li><b>GET /signup</b>: Display signup page</li>
 *   <li><b>POST /signup</b>: Handle signup form submission</li>
 *   <li><b>GET /unlock</b>: Display unlock account page</li>
 *   <li><b>POST /unlock</b>: Handle unlock account form submission</li>
 *   <li><b>GET /forgotPwd</b>: Display forgot password page</li>
 *   <li><b>POST /forgotPwd</b>: Handle forgot password form submission</li>
 *   <li><b>GET /logout</b>: Invalidate session and logout user</li>
 * </ul>
 *
 * Model attributes used:
 * <ul>
 *   <li>LoginDTO</li>
 *   <li>SignupDTO</li>
 *   <li>UnlockAccountDTO</li>
 * </ul>
 *
 * Error and success messages are set in the model for user feedback.
 *
 * Dependencies:
 * <ul>
 *   <li>{@link UserDetailsService} for user-related operations</li>
 *   <li>{@link HttpSession} for session management</li>
 * </ul>
 */
package com.anoop.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.anoop.DTO.LoginDTO;
import com.anoop.DTO.SignupDTO;
import com.anoop.DTO.UnlockAccountDTO;
import com.anoop.Service.UserDetailsService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private HttpSession session;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("LoginDTO", new LoginDTO());
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@ModelAttribute("LoginDTO") LoginDTO data, Model model) {
        Boolean isLogin = userDetailsService.login(data);
        if (isLogin) {
            return "redirect:/dashboard";
        } else {
            model.addAttribute("error", "Invalid Credential");
            return "login";
        }

    }

    @PostMapping("/signup")
    public String handleSignup(@ModelAttribute("signupDTO") SignupDTO data, Model model) {
        Boolean isTrue = userDetailsService.signup(data);

        if (isTrue) {
            model.addAttribute("success", "Check your Email Id, Sended temporary Password for Unlock your Account");
        } else {
            model.addAttribute("error", "Use Different Email Id, It Email Id Already have an Account");
        }

        return "signup";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("signupDTO", new SignupDTO());
        return "signup";
    }

    @GetMapping("/unlock")
    public String unlockPage(@RequestParam String email, Model model) {

        model.addAttribute("email", email);
        model.addAttribute("UnlockAccountDTO", new UnlockAccountDTO());

        return "unlock";
    }

    @PostMapping("/unlock")
    public String unlock(@ModelAttribute("UnlockAccountDTO") UnlockAccountDTO data, Model model) {
        System.out.println(data.getEmail());
        String status = userDetailsService.unlockAccount(data);
        if (status.equals("success")) {
            model.addAttribute("success", "Your Account is successfully unlock, Please Log in.");
        } else {
            model.addAttribute("error", status);
            model.addAttribute("email", data.getEmail());
        }
        return "unlock";
    }

    @GetMapping("/forgotPwd")
    public String forgotPwdPage() {
        return "forgotPwd";
    }

    @PostMapping("/forgotPwd")
    public String handleForgotPassword(@RequestParam String email, Model model) {
        Boolean isForgot = userDetailsService.forgotPwd(email);
        if (isForgot) {
            model.addAttribute("success", "check your email, Recover your password");
        }else{
            model.addAttribute("error", "Invalid email Id, Please try again with valid email Id");
        }
        return "forgotPwd";
    }

    @GetMapping("/logout")
    public String logout() {
       session.invalidate();
        return "index";
    }

}
