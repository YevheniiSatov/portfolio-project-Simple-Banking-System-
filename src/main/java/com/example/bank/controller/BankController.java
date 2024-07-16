package com.example.bank.controller;

import com.example.bank.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.example.bank.model.*;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * BankController handles web requests related to managing clients, accounts, payments, purchases, and reports.
 */
@Controller
public class BankController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private ReportService reportService;

    private static final Logger logger = Logger.getLogger(BankController.class.getName());

    /**
     * Displays the home page.
     *
     * @return the name of the home page view
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /**
     * Displays the form for adding a new client.
     *
     * @return the name of the add client view
     */
    @GetMapping("/bank/addClient")
    public String showAddClientForm() {
        return "addClient";
    }

    /**
     * Processes the request to add a new client.
     *
     * @param firstName the client's first name
     * @param lastName the client's last name
     * @param address the client's address
     * @param postalCode the client's postal code
     * @param model the model to pass data to the view
     * @return the name of the result view
     */
    @PostMapping("/bank/addClient")
    public String addClient(@RequestParam("firstName") String firstName,
                            @RequestParam("lastName") String lastName,
                            @RequestParam("address") String address,
                            @RequestParam("postalCode") String postalCode,
                            Model model) {
        try {
            Client client = new Client(firstName, lastName, address, postalCode);
            clientService.saveClient(client);
            model.addAttribute("message", "Client added successfully!");
            return "success";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding client: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Displays the form for adding a new account.
     *
     * @param model the model to pass data to the view
     * @return the name of the add account view
     */
    @GetMapping("/bank/addAccount")
    public String showAddAccountForm(Model model) {
        model.addAttribute("clients", clientService.getAllClients());
        return "addAccount";
    }

    /**
     * Processes the request to add a new account.
     *
     * @param accountNumber the account number
     * @param balance the initial account balance
     * @param clientId the client ID to associate with the account
     * @param model the model to pass data to the view
     * @return the name of the result view
     */
    @PostMapping("/bank/addAccount")
    public String addAccount(@RequestParam("accountNumber") String accountNumber,
                             @RequestParam("balance") double balance,
                             @RequestParam("clientId") Long clientId,
                             Model model) {
        try {
            Client client = clientService.getClientById(clientId);
            if (client == null) {
                model.addAttribute("message", "Client not found");
                return "error";
            }
            Account account = new Account(accountNumber, balance, client);
            accountService.saveAccount(account);
            model.addAttribute("message", "Account added successfully!");
            return "success";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding account: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Displays the form for adding a new payment.
     *
     * @param model the model to pass data to the view
     * @return the name of the add payment view
     */
    @GetMapping("/bank/addPayment")
    public String showAddPaymentForm(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "addPayment";
    }

    /**
     * Processes the request to add a new payment.
     *
     * @param paymentType the type of payment
     * @param amount the amount of payment
     * @param accountId the account ID to associate with the payment
     * @param model the model to pass data to the view
     * @return the name of the result view
     */
    @PostMapping("/bank/addPayment")
    public String addPayment(@RequestParam("paymentType") String paymentType,
                             @RequestParam("amount") double amount,
                             @RequestParam("accountId") Long accountId,
                             Model model) {
        try {
            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                model.addAttribute("message", "Account not found");
                return "error";
            }
            Payment payment = new Payment(paymentType, amount, account);
            paymentService.savePayment(payment);
            model.addAttribute("message", "Payment added successfully!");
            return "success";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding payment: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Displays the form for adding a new purchase.
     *
     * @param model the model to pass data to the view
     * @return the name of the add purchase view
     */
    @GetMapping("/bank/addPurchase")
    public String showAddPurchaseForm(Model model) {
        model.addAttribute("accounts", accountService.getAllAccounts());
        return "addPurchase";
    }

    /**
     * Processes the request to add a new purchase.
     *
     * @param accountId the account ID to associate with the purchase
     * @param amount the amount of purchase
     * @param transactionDate the date of transaction
     * @param model the model to pass data to the view
     * @return the name of the result view
     */
    @PostMapping("/bank/addPurchase")
    public String addPurchase(@RequestParam("accountId") Long accountId,
                              @RequestParam("amount") double amount,
                              @RequestParam("transactionDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate transactionDate,
                              Model model) {
        try {
            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                model.addAttribute("message", "Account not found");
                return "error";
            }
            if (account.getBalance() < amount) {
                model.addAttribute("message", "Insufficient funds");
                return "error";
            }

            // Creating a new purchase
            Purchase purchase = new Purchase(account, transactionDate, amount);
            purchaseService.savePurchase(purchase);

            // Updating the account balance
            account.setBalance(account.getBalance() - amount);
            accountService.saveAccount(account);

            model.addAttribute("message", "Purchase added successfully!");
            return "success";
        } catch (Exception e) {
            model.addAttribute("message", "Error adding purchase: " + e.getMessage());
            return "error";
        }
    }

    /**
     * Displays the form for generating a report.
     *
     * @return the name of the generate report view
     */
    @GetMapping("/bank/generateReport")
    public String showGenerateReportForm() {
        return "generateReport";
    }

    /**
     * Processes the request to generate a report for a specified period.
     *
     * @param startDate the start date of the period
     * @param endDate the end date of the period
     * @param model the model to pass data to the view
     * @return the name of the result view
     */
    @PostMapping("/bank/generateReport")
    public String generateReport(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 Model model) {
        try {
            logger.info("Generating report for period: " + startDate + " to " + endDate);
            String reportContent = reportService.generateReport(startDate, endDate);
            logger.info("Generated report content: " + reportContent);
            model.addAttribute("report", reportContent);
            return "report";
        } catch (Exception e) {
            logger.severe("Error generating report: " + e.getMessage());
            model.addAttribute("message", "Error generating report: " + e.getMessage());
            return "error";
        }
    }
}
