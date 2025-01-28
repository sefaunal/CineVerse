package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.UserService;
import com.sefaunal.cineverse.Utils.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-01-28
 */
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    private static final Integer REQUEST_SIZE = 50;

    @GetMapping("/panel")
    public ModelAndView adminListPanel(@RequestParam(defaultValue = "1") int page,
                                       Principal principal,
                                       Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<User> usersPage = userService.findByRoleWithPageable("ADMIN", pageable);

        model.addAttribute("adminList", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());

        model.addAttribute("user", user);
        return new ModelAndView("panel/AdminList");
    }

    @GetMapping("/panel/users")
    public ModelAndView userListPanel(@RequestParam(defaultValue = "1") int page,
                                      Principal principal,
                                      Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<User> usersPage = userService.findByRoleWithPageable("USER", pageable);

        model.addAttribute("userList", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());
        model.addAttribute("totalItems", usersPage.getTotalElements());

        model.addAttribute("user", user);
        return new ModelAndView("panel/UserList");
    }

    @GetMapping("/panel/banned")
    public ModelAndView bannedUserListPanel(@RequestParam(defaultValue = "1") int page,
                                            Principal principal,
                                            Model model) {
        User user = userService.findUserByUsername(principal.getName());

        Pageable pageable = PageRequest.of(page - 1, REQUEST_SIZE);
        Page<User> usersPage = userService.findByRoleWithPageable("BANNED", pageable);

        model.addAttribute("bannedList", usersPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", usersPage.getTotalPages());

        model.addAttribute("user", user);
        return new ModelAndView("panel/BannedList");
    }

    @GetMapping("/panel/config/grant")
    public RedirectView grantAdminPrivileges(@RequestParam String ID) {
        User user = userService.findUserByID(ID);

        if (user.getUsername().equals(CommonUtils.getUserInfo())) {
            throw new AccessDeniedException("You Are Not Authorized!");
        }

        userService.grantAdminPrivileges(user);

        return new RedirectView("/admin/panel");
    }

    @GetMapping("/panel/config/revoke")
    public RedirectView revokeAdminPrivileges(@RequestParam String ID) {
        User user = userService.findUserByID(ID);

        if (user.getUsername().equals(CommonUtils.getUserInfo())) {
            throw new AccessDeniedException("You Are Not Authorized!");
        }

        userService.revokeAdminPrivileges(user);

        return new RedirectView("/admin/panel");
    }

    @GetMapping("/panel/config/ban")
    public RedirectView banUser(@RequestParam String ID) {
        User user = userService.findUserByID(ID);

        if (user.getUsername().equals(CommonUtils.getUserInfo())) {
            throw new AccessDeniedException("You Are Not Authorized!");
        }

        userService.banUser(user);

        return new RedirectView("/admin/panel/banned");
    }

    @GetMapping("/panel/config/unban")
    public RedirectView unbanUser(@RequestParam String ID) {
        User user = userService.findUserByID(ID);

        if (user.getUsername().equals(CommonUtils.getUserInfo())) {
            throw new AccessDeniedException("You Are Not Authorized!");
        }

        userService.unbanUser(user);

        return new RedirectView("/admin/panel/users");
    }
}
