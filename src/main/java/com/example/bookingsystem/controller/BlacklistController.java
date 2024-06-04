package com.example.bookingsystem.controller;

import com.example.bookingsystem.services.impl.BlackListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;

@Controller
public class BlacklistController {

    @RequestMapping("blacklist")
    public String blacklist() {
        return "blacklist";
    }

    @Autowired
    private BlackListServiceImpl blackListService;

    @PostMapping("/addEmail")
    public String addEmailToBlacklist(@RequestParam("email") String email, Model model) throws URISyntaxException {

        blackListService.addEmailToBlackList(email);
        model.addAttribute("message", "Email added to blacklist successfully.");

        return "blacklist.html";
    }

    @PostMapping("/removeEmail")
    public String removeEmailFromBlacklist(@RequestParam("email") String email, Model model) throws URISyntaxException {
        blackListService.removeEmailFromBlackList(email, true);
        model.addAttribute("message", "Email removed from blacklist successfully.");
        return "blacklist.html";
    }
}
