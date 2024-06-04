package com.example.bookingsystem.controller;

import com.example.bookingsystem.modell.EmailTemplate;
import com.example.bookingsystem.repo.EmailTemplateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
public class EmailTemplateController {

    @Autowired
    private EmailTemplateRepo emailTemplateRepo;

    @GetMapping("/emailTemplate")
    public String showTemplateForm(Model model) {
        List<EmailTemplate> templates = emailTemplateRepo.findAll();
        EmailTemplate template;

        if (!templates.isEmpty()) {
            template = templates.get(0);
        } else {
            template = new EmailTemplate();
            template.setBody("");
            template.setSubject("");
        }

        model.addAttribute("template", template);
        return "emailTemplate";
    }

    @PostMapping("/emailTemplateUpdate")
    public String updateTemplate(@ModelAttribute EmailTemplate template, RedirectAttributes redirectAttributes) {

        List<EmailTemplate> templates = emailTemplateRepo.findAll();

        if (!templates.isEmpty()) {

            EmailTemplate existingTemplate = templates.get(0);
            existingTemplate.setSubject(template.getSubject());
            existingTemplate.setBody(template.getBody());
            emailTemplateRepo.save(existingTemplate);
        } else {
            emailTemplateRepo.save(template);
        }
        redirectAttributes.addFlashAttribute("updateSuccess", "Mallen är nu uppdaterad.");
        return "redirect:/emailTemplate";
    }
}
