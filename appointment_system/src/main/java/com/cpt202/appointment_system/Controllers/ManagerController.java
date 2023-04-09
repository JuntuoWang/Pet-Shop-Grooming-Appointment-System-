package com.cpt202.appointment_system.Controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cpt202.appointment_system.Common.Result;
import com.cpt202.appointment_system.Models.User;
import com.cpt202.appointment_system.Repositories.UserRepo;
import com.cpt202.appointment_system.Services.UserServiceM;

@RestController // a RESTfull API
@RequestMapping("/mm")
public class ManagerController {
    @Autowired
    private UserServiceM userService;

    @GetMapping("/customer-management")  
    public Result<?> getAllCustomers(){
        return userService.listAllCustomers();
    }

    @GetMapping("customer-management/search")
    public Result<?> getCustomerByName(@RequestParam User user){
        return userService.searchCustomerByName(user);
    }

    // @GetMapping("customer-management/customer")
    // public Result<?> viewCustomer(@RequestParam User user){
    //     return userService.viewOneCustomer(user);
    // }

    @GetMapping("/customer-management/list")
    public List<User> getAllAppointment(){
        return userService.getAppointmentList();
    }
}
