package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Model.User;
import com.sefaunal.cineverse.Service.ActorService;
import com.sefaunal.cineverse.Service.UserService;
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
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/content/actor")
public class ActorController {
    private final ActorService actorService;
    private final UserService userService;

    @PostMapping("/save")
    public RedirectView saveActor(@ModelAttribute Actor actor, @RequestParam MultipartFile actorImage) {
        return actorService.saveActor(actor, actorImage);
    }

    @GetMapping("/update")
    public ModelAndView updateActorPage(@RequestParam String ID, Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        Actor actor = actorService.findRecordByID(ID);

        model.addAttribute("user", user);
        model.addAttribute("actor", actor);
        model.addAttribute("updateType", "ACTOR");

        return new ModelAndView("UpdatePage");
    }

    @PostMapping("update")
    public RedirectView updateActorRecord(@ModelAttribute Actor actor, @RequestParam MultipartFile actorImage, @RequestParam String password, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());

        return actorService.updateRecordByID(actor, actorImage, user.getPassword(), password);
    }
}