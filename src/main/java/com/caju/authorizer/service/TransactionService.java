package com.caju.authorizer.service;

import com.caju.authorizer.model.Account;
import com.caju.authorizer.model.Transaction;
import com.caju.authorizer.repository.AccountRepository;
import com.caju.authorizer.repository.MerchantMappingRepository;
import com.caju.authorizer.util.TransactionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MerchantMappingRepository merchantMappingRepository;

    public String processTransaction(Transaction transaction) {
        Optional<Account> accountOptional = accountRepository.findById(transaction.getAccountId());
        if (accountOptional.isEmpty()) {
            return "{ \"code\": \"07\" }"; // Conta não encontrada
        }

        Account account = accountOptional.get();
        String category = TransactionUtils.getCategoryByMerchant(transaction, merchantMappingRepository);
        BigDecimal availableBalance = account.getBalance(category);

        if (availableBalance.compareTo(transaction.getTotalAmount()) >= 0) {
            account.setBalance(category, availableBalance.subtract(transaction.getTotalAmount()));
            accountRepository.save(account);
            return "{ \"code\": \"00\" }"; // Transação aprovada
        } else if (account.getCashBalance().compareTo(transaction.getTotalAmount()) >= 0) {
            account.setCashBalance(account.getCashBalance().subtract(transaction.getTotalAmount()));
            accountRepository.save(account);
            return "{ \"code\": \"00\" }"; // Transação aprovada com saldo de CASH
        } else {
            return "{ \"code\": \"51\" }"; // Saldo insuficiente
        }
    }
}
