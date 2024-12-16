package com.caju.authorizer.util;

import com.caju.authorizer.model.MerchantMapping;
import com.caju.authorizer.model.Transaction;
import com.caju.authorizer.repository.MerchantMappingRepository;

import java.util.Optional;

public class TransactionUtils {

    public static String getCategoryByMcc(String mcc) {
        switch (mcc) {
            case "5411":
            case "5412":
                return "FOOD";
            case "5811":
            case "5812":
                return "MEAL";
            default:
                return "CASH";
        }
    }

    public static String getCategoryByMerchant(Transaction transaction, MerchantMappingRepository merchantMappingRepository) {
        Optional<MerchantMapping> mapping = merchantMappingRepository.findById(transaction.getMerchant());
        return mapping.map(MerchantMapping::getMcc).orElseGet(() -> getCategoryByMcc(transaction.getMcc()));
    }
}
