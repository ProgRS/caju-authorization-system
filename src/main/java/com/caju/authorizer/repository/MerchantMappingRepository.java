package com.caju.authorizer.repository;

import com.caju.authorizer.model.MerchantMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantMappingRepository extends JpaRepository<MerchantMapping, String> {
}
