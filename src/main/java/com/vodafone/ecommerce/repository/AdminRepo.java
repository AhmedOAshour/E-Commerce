package com.vodafone.ecommerce.repository;

import com.vodafone.ecommerce.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin, Long> {
}
