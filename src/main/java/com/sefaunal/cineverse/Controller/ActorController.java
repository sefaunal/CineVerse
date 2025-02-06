package com.sefaunal.cineverse.Controller;

import com.sefaunal.cineverse.Model.Actor;
import com.sefaunal.cineverse.Service.ActorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Instant;

/**
 * @author github.com/sefaunal
 * @since 2025-02-06
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/content/actor")
public class ActorController {
    private final ActorService actorService;

    @PostMapping("/save")
    public RedirectView saveActor(@ModelAttribute Actor actor) {
        actorService.saveActor(actor);

        return new RedirectView("/content/panel/actors");
    }
}
