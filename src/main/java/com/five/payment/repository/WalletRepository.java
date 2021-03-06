package com.five.payment.repository;

import com.five.payment.model.Wallet;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by msi on 2017/6/6.
 */
public interface WalletRepository extends JpaRepository<Wallet, Integer> {
    List<Wallet> findByUserId(int userId);
    Wallet findOne(Integer id);
}
