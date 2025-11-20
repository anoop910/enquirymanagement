package com.anoop.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.anoop.DTO.AddEnquiryDTO;
import com.anoop.DTO.DashboardDTO;
import com.anoop.DTO.ViewEnquiryDTO;
import com.anoop.Entity.CoursesEntity;
import com.anoop.Entity.EnquriryStatusEntity;
import com.anoop.Service.EnquiryService;

import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

    @Autowired
    private HttpSession session;

    @Autowired
    private EnquiryService service;

    /**
     * Handles GET requests for the dashboard page.
     * <p>
     * Retrieves the current user's session and, if authenticated, fetches dashboard
     * data
     * to be displayed on the dashboard view. If the user is not authenticated,
     * redirects
     * to the login page.
     * </p>
     *
     * @param model the model to which dashboard data will be added
     * @return the name of the dashboard view if the user is authenticated,
     *         otherwise a redirect to the login page
     */
    @GetMapping("/dashboard")
    public String dashboardPage(Model model) {
        var userSession = (Integer) session.getAttribute("userId");
        if (userSession != null) {
            DashboardDTO DashboardData = service.getDashboardData(userSession);
            model.addAttribute("data", DashboardData);

            return "dashboard";
        } else {
            model.addAttribute("error", "Please Login");
            return "redirect:/login";
        }

    }

    @GetMapping("/addenquiry")
    public String addEnquiryPage(Model model) {
        model.addAttribute("AddEnquiryDTO", new AddEnquiryDTO());
        var userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            model.addAttribute("error", "Please Login");
            return "redirect:/login";
        }
        List<CoursesEntity> courses = service.getCourses();
        List<EnquriryStatusEntity> status = service.getStatus();
        model.addAttribute("courses", courses);
        model.addAttribute("status", status);

        return "addenquiry";
    }

    @PostMapping("/addenquiry")
    public String handleAddEnquiry(@ModelAttribute("AddEnquiryDTO") AddEnquiryDTO addEnquiryDTO, Model model) {
        var userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        String  succMsg = service.addEnquriy(addEnquiryDTO, userId);
        model.addAttribute("success", succMsg);
        return "addenquiry";
    }

    @GetMapping("/viewenquiry")
    public String viewEnquiryPage(Model model) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            List<ViewEnquiryDTO> viewEnquiryDTOs = service.getEnquiries(userId);
            model.addAttribute("dataes", viewEnquiryDTOs);
            return "vieweqnuiry";
        }
        return "redirect:/login";
    }

    @GetMapping("/course")
    public void getCoursesData() {

    }

}
