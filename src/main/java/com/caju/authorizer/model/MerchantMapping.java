package com.caju.authorizer.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class MerchantMapping {
    @Id
    private String merchantName;
    private String mcc;
}
