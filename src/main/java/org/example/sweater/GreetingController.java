package org.example.sweater;

import lombok.AllArgsConstructor;
import org.example.sweater.domain.Message;
import org.example.sweater.repo.MessageRepo;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@AllArgsConstructor
public class GreetingController {

    private final MessageRepo repo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<Message> messages = repo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        Message message = new Message(text, tag);
        repo.save(message);

        Iterable<Message> messages = repo.findAll();
        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String tag, Map<String, Object> model) {
        Iterable<Message> messages;
        if (StringUtils.hasLength(tag)) {
            messages = repo.findByTag(tag);
        } else {
            messages = repo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }
}
