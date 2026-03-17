package com.raahi1.demo.repository;

import com.raahi1.demo.models.TrustedContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrustedContactRepository extends JpaRepository<TrustedContact, Long> {

    boolean existsByMobile(String mobile);
    boolean existsByEmail(String email);

    Optional<TrustedContact> findByMobile(String mobile);
}
