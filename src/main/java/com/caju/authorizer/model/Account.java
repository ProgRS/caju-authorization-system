package com.caju.authorizer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Account {
    @Id
    private String id;
    private BigDecimal foodBalance;
    private BigDecimal mealBalance;
    private BigDecimal cashBalance;

    public BigDecimal getBalance(String category) {
        switch (category) {
            case "FOOD":
                return foodBalance;
            case "MEAL":
                return mealBalance;
            case "CASH":
                return cashBalance;
            default:
                return BigDecimal.ZERO;
        }
    }

    public void setBalance(String category, BigDecimal newBalance) {
        switch (category) {
            case "FOOD":
                foodBalance = newBalance;
                break;
            case "MEAL":
                mealBalance = newBalance;
                break;
            case "CASH":
                cashBalance = newBalance;
                break;
        }
    }
}
