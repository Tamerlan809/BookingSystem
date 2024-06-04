package com.example.bookingsystem.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.dtos.DetailedKundDto;
import com.example.bookingsystem.services.KundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class KundController {

    private final KundService kundService;


    @RequestMapping("customer")
    public String handleCustomer(Model model) {
        List<DetailedKundDto> kunder = kundService.getAllKunder();
        model.addAttribute("kund", new DetailedKundDto());
        model.addAttribute("kunder", kunder);
        model.addAttribute("pageTitle", "Alla befintliga kunder");
        model.addAttribute("tableHeadings", Arrays.asList("Namn", "Email", "Telefon", "Personnummer"));
        model.addAttribute("emptyListMessage", "Inga kunder hittades");
        return "handleCustomer.html";
    }

    @PostMapping("customerCreated")
    public String create(@Valid @ModelAttribute("kund") DetailedKundDto kund, BindingResult result,
                         Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            List<String> errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            model.addAttribute("errors", errors);
            model.addAttribute("kund", kund);
            model.addAttribute("kunder", kundService.getAllKunder());
            model.addAttribute("pageTitle", "Alla befintliga kunder");
            return "handleCustomer.html";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Kunden har lagts till!");
        kundService.addKund(kund);
        return "redirect:/customer";
    }

    @PostMapping("modifyCustomer")
    public String modifyCustomer(@Valid DetailedKundDto kund,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            redirectAttributes.addFlashAttribute("errors", errors);
            redirectAttributes.addFlashAttribute("kund", kund);
            return "redirect:/customer";
        }

        kundService.modifyKund(kund);
        redirectAttributes.addFlashAttribute("successMessage", "Kund informationen har uppdaterats!");

        return "redirect:/customer";
    }


    @RequestMapping(path ="/customer/deleteById/{id}")
    public String deleteByIdCheck(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        String message = kundService.deleteCustomerCheck(id);

        if (message.equals("koppling")) {
            redirectAttributes.addFlashAttribute("kundFinnsBokad", "Kunden är kopplad till en bokning");
            return "redirect:/customer";
        }
        else {
            kundService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("KundenTogsBort", "Kunden har blivit borttagen");
            return "redirect:/customer";
        }


    }



}
