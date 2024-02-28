package com.backend.stayEasy.repository;

import com.backend.stayEasy.entity.PaymentBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentBill, UUID> {
}
