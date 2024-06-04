package com.example.bookingsystem.controller;

import lombok.RequiredArgsConstructor;
import com.example.bookingsystem.dtos.ContractCustomerDto;
import com.example.bookingsystem.dtos.DetailedContractCustomerDto;
import com.example.bookingsystem.services.ContractCustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
    @RequiredArgsConstructor
    public class ContractCustomerController {

        private final ContractCustomerService contractCustomerService;

        @GetMapping("/contractCustomers")
        public String contractCustomers(Model model,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(defaultValue = "companyName") String sortBy,
                                        @RequestParam(defaultValue = "asc") String direction,
                                        @RequestParam(required = false) String search) {

            Page<ContractCustomerDto> contractCustomersPage = contractCustomerService.getContractCustomers(page, size, sortBy, direction, search);

            model.addAttribute("contractCustomers", contractCustomersPage.getContent());
            model.addAttribute("currentPage", contractCustomersPage.getNumber());
            model.addAttribute("totalPages", contractCustomersPage.getTotalPages());
            model.addAttribute("emptyListMessage", "Inga företagskunder hittades");
            return "contractCustomers";
        }


        @GetMapping("/allDetails")
    public String getCustomerDetails(Long customerId, RedirectAttributes redirectAttributes) {

        DetailedContractCustomerDto customer = contractCustomerService.getCustomerById(customerId);

        redirectAttributes.addFlashAttribute("customerAllinfo", customer);

        return "redirect:/customerDetails";
    }
    @RequestMapping("/customerDetails")
    public String customerDetails(){
        return "customerDetails";

    }
        @GetMapping("/customers")
        public ResponseEntity<Page<ContractCustomerDto>> getCustomers(
                @RequestParam(required = false) String search,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "10") int size,
                @RequestParam(defaultValue = "companyName") String sortBy,
                @RequestParam(defaultValue = "asc") String direction) {

            Sort.Direction sortDirection = Sort.Direction.fromString(direction);
            Pageable pageable = PageRequest.of(page, size, sortDirection, sortBy);
            Page<ContractCustomerDto> customers = contractCustomerService.getCustomers(search, pageable);
            return ResponseEntity.ok(customers);
        }
}

