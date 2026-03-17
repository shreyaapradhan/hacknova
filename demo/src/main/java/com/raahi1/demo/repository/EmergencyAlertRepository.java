package com.raahi1.demo.repository;

import com.raahi1.demo.models.EmergencyAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyAlertRepository extends JpaRepository<EmergencyAlert, Long> {

    // Active alert for a specific user
    Optional<EmergencyAlert> findByUserMobileAndStatus(String mobile, String status);

    /**
     * Get all ACTIVE alerts for users who listed trustedMobile as a contact.
     * Joins with users table to check contact1/contact2/contact3 fields.
     */
    @Query(value =
        "SELECT e.* FROM emergency_alerts e " +
        "JOIN users u ON u.mobile = e.user_mobile " +
        "WHERE e.status = 'ACTIVE' " +
        "AND (u.contact1 = :trustedMobile " +
        "  OR u.contact2 = :trustedMobile " +
        "  OR u.contact3 = :trustedMobile)",
        nativeQuery = true)
    List<EmergencyAlert> findActiveAlertsForTrustedContact(
        @Param("trustedMobile") String trustedMobile);
}
