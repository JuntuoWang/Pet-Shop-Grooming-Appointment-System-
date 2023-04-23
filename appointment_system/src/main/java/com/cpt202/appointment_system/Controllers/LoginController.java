package com.cpt202.appointment_system.Controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import com.cpt202.appointment_system.Common.Result;
import com.cpt202.appointment_system.Models.User;
import com.cpt202.appointment_system.Repositories.UserRepo;
import com.cpt202.appointment_system.Services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/appointment-system")

public class LoginController {
    @Autowired
    private LoginService loginService;
    // @Autowired
    // private AuthenticationManager authenticationManager;
    
    // @GetMapping("")
    // public String showForm() {
    //     return "signup";
    // }

    //登录登录登录
    @PostMapping("")
    public String loginUser(@RequestParam("uname") String username,
                            @RequestParam("upwd") String password,
                            Model model,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        if(loginService.loginUser(username, password)==0){
            // 添加用户信息到 session
            session.setAttribute("user", username);

            redirectAttributes.addFlashAttribute("message", "登录成功");
            return "redirect:/appointment-system";
        } else if(loginService.loginUser(username, password)==1){
            session.setAttribute("user", username);

            redirectAttributes.addFlashAttribute("message", "管理员登录成功");
            return "redirect:/manager";
        }
        
        else {
            redirectAttributes.addFlashAttribute("error", "登录失败");
            return "redirect:/appointment-system";
        }
    }

    // 注册注册注册
    @PostMapping("/reg")
    public String registerUser(@RequestParam("rename") String username,
                               @RequestParam("reemail") String email,
                               @RequestParam("repass") String password,
                               @RequestParam("repass2") String password2,
                               @RequestParam("phone") String phone,
                               Model model,
                               RedirectAttributes redirectAttributes,
                               HttpSession session) {
        // User user = new User(username, password,);
        User user=new User(null, username, password, 0, null, null, null, phone, email, 0);
    if(password!=password2){
        redirectAttributes.addFlashAttribute("error", "注册失败:两次密码输入不一样");
        return "redirect:/appointment-system";

    }
        if(loginService.registerUser(user)==1){
            redirectAttributes.addFlashAttribute("error", "注册失败:用户名已被注册或格式不符");
            return "redirect:/appointment-system";
        }
        
        else if(loginService.registerUser(user)==2){
            redirectAttributes.addFlashAttribute("error", "注册失败:邮箱已被注册");
            return "redirect:/appointment-system";
        }
        
        else{

            session.setAttribute("user", username);
            redirectAttributes.addFlashAttribute("message", "注册成功：已为您自动登录");
            
            return "redirect:/appointment-system";

        }
    }

    //登出
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 从会话中移除用户信息
        session.removeAttribute("user");

        // 重定向到登录页面
        return "redirect:/appointment-system";
    }



//前端检查用户名和email的api接口


    @GetMapping("/check-unique")
    public ResponseEntity<?> checkUnique(@RequestParam("value") String value) {
        boolean isUnique = loginService.checkUnique(value);

        if (isUnique) {
            return ResponseEntity.ok().body("unique");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate");
        }
    }
    @GetMapping("/check-uniqueem")
    public ResponseEntity<?> checkUniqueem(@RequestParam("value") String value) {
        boolean isUnique = loginService.checkUniqueEmail(value);

        if (isUnique) {
            return ResponseEntity.ok().body("unique");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate");
        }
    }
}



