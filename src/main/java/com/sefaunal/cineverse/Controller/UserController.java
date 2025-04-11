package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Request.UserRequest;
import com.sefaunal.cineverse.Service.UserService;
import com.sefaunal.cineverse.Utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-04-10
 */
@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ModelAndView accountPage(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());

        model.addAttribute("user", user);
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/photo/update")
    public ModelAndView updatePhoto(@RequestParam MultipartFile profileImage, Model model) {
        User user = userService.updateUserProfileImage(profileImage);

        model.addAttribute("user", user);
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/password/update")
    public ModelAndView updatePassword(@RequestParam String oldPassword, @RequestParam String newPassword, Model model) {
        if (userService.updateUserPassword(oldPassword, newPassword)) {
            model.addAttribute("passwordChanged", true);
        } else {
            model.addAttribute("passwordChangedFail", true);
        }

        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/details/update")
    public ModelAndView updateProfile(@Valid @ModelAttribute UserRequest userRequest, Model model) {
        if  (userService.updateUserProfile(userRequest)) {
            model.addAttribute("profileUpdated", true);
        } else {
            model.addAttribute("profileUpdatedFail", true);
        }
        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        return new ModelAndView("AccountPage");
    }

    @PostMapping("/profile/account/deactivate")
    public ModelAndView deactivateAccount(HttpServletRequest httpServletRequest, @RequestParam String confirmPassword, Model model) {
        if (userService.deactivateUser(httpServletRequest, confirmPassword)) {
            model.addAttribute("user", null);
            return new ModelAndView("MoviesPage");
        }

        model.addAttribute("user", userService.findUserByUsername(CommonUtils.getUserInfo()));
        model.addAttribute("accountDeactivationFail", true);
        return new ModelAndView("AccountPage");
    }
}
