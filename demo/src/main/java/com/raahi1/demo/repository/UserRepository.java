package com.raahi1.demo.repository;

import com.raahi1.demo.dto.LinkedUserDTO;
import com.raahi1.demo.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByMobile(String mobile);

    Optional<User> findByMobile(String mobile);

    /**
     * Finds all Raahi users who listed :mobile as one of their
     * three emergency contacts.
     * Returns only safe fields via LinkedUserDTO projection.
     */
    @Query("SELECT new com.raahi1.demo.dto.LinkedUserDTO(" +
           "u.id, u.fullName, u.mobile, u.state, u.city) " +
           "FROM User u " +
           "WHERE u.contact1 = :mobile " +
           "   OR u.contact2 = :mobile " +
           "   OR u.contact3 = :mobile")
    List<LinkedUserDTO> findUsersLinkedToContact(@Param("mobile") String mobile);
}
