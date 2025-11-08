package com.example.online_banking;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"balance", "transactions"})
public class DashboardController {

    // Initialize default balance
    @ModelAttribute("balance")
    public Double balance() {
        return 15230.75;
    }

    // Initialize default transactions
    @ModelAttribute("transactions")
    public List<String> transactions() {
        List<String> list = new ArrayList<>();
        list.add("Deposit - ₹10,000");
        list.add("ATM Withdrawal - ₹1,500");
        list.add("Online Transfer - ₹2,000");
        list.add("Bill Payment - ₹320.75");
        list.add("Salary Credit - ₹5,000");
        return list;
    }

    // ✅ Dashboard page
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "dashboard";
    }

    // ✅ Deposit money handler
    @PostMapping("/deposit")
    public String deposit(@RequestParam double amount,
                          @ModelAttribute("balance") Double balance,
                          @ModelAttribute("transactions") List<String> transactions,
                          Model model,
                          Principal principal) {

        String message;

        if (amount <= 0) {
            message = "⚠️ Invalid deposit amount: ₹" + amount;
            transactions.add(message);
        } else {
            balance += amount;
            message = "✅ Deposit successful! New Balance: ₹" + String.format("%.2f", balance);
            transactions.add("Deposit - ₹" + amount);
        }

        model.addAttribute("balance", balance);
        model.addAttribute("transactions", transactions);
        model.addAttribute("username", principal.getName());
        model.addAttribute("message", message);

        return "dashboard";
    }

    // ✅ Withdraw money handler
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam double amount,
                           @ModelAttribute("balance") Double balance,
                           @ModelAttribute("transactions") List<String> transactions,
                           Model model,
                           Principal principal) {

        String message;

        if (amount <= 0) {
            message = "⚠️ Invalid withdrawal amount: ₹" + amount;
            transactions.add(message);
        } else if (balance >= amount) {
            balance -= amount;
            message = "💸 Withdrawal successful! Remaining Balance: ₹" + String.format("%.2f", balance);
            transactions.add("Withdrawal - ₹" + amount);
        } else {
            message = "❌ Withdrawal failed! Insufficient funds. Current Balance: ₹" + String.format("%.2f", balance);
            transactions.add(message);
        }

        model.addAttribute("balance", balance);
        model.addAttribute("transactions", transactions);
        model.addAttribute("username", principal.getName());
        model.addAttribute("message", message);

        return "dashboard";
    }
}
