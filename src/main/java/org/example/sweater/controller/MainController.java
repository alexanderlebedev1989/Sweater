package org.example.sweater.controller;

import lombok.AllArgsConstructor;
import org.example.sweater.domain.Message;
import org.example.sweater.domain.User;
import org.example.sweater.repo.MessageRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
public class MainController {

    private final MessageRepo repo;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String tag,
                       Model model) {
        Iterable<Message> messages;

        if (StringUtils.hasLength(tag)) {
            messages = repo.findByTag(tag);
        } else {
            messages = repo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("tag", tag);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Model model) {
        Message message = new Message(text, tag, user);
        repo.save(message);

        Iterable<Message> messages = repo.findAll();
        model.addAttribute("messages", messages);
        return "main";
    }
}
