package com.raahi1.demo.service;

import com.raahi1.demo.dto.LinkedUserDTO;
import com.raahi1.demo.dto.TrustedContactLoginDTO;
import com.raahi1.demo.dto.TrustedContactSignupDTO;
import com.raahi1.demo.models.TrustedContact;
import com.raahi1.demo.repository.TrustedContactRepository;
import com.raahi1.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrustedContactService {

    private final TrustedContactRepository trustedRepo;
    private final UserRepository           userRepo;

    @Autowired
    public TrustedContactService(TrustedContactRepository trustedRepo,
                                  UserRepository userRepo) {
        this.trustedRepo = trustedRepo;
        this.userRepo    = userRepo;
    }

    // ── SIGNUP ────────────────────────────────────────────────────────────
    public TrustedContact signup(TrustedContactSignupDTO dto) {

        if (trustedRepo.existsByMobile(dto.getMobile())) {
            throw new IllegalArgumentException(
                "Mobile number " + dto.getMobile() + " is already registered.");
        }
        if (trustedRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException(
                "Email " + dto.getEmail() + " is already registered.");
        }

        TrustedContact tc = new TrustedContact();
        tc.setFullName(dto.getName());
        tc.setMobile(dto.getMobile());
        tc.setEmail(dto.getEmail());
        // ⚠️ In production, hash with BCrypt: passwordEncoder.encode(dto.getPassword())
        tc.setPassword(dto.getPassword());

        return trustedRepo.save(tc);
    }

    // ── LOGIN ─────────────────────────────────────────────────────────────
    public TrustedContact login(TrustedContactLoginDTO dto) {

        TrustedContact tc = trustedRepo.findByMobile(dto.getMobile())
            .orElseThrow(() -> new IllegalArgumentException(
                "No account found for mobile: " + dto.getMobile()));

        // ⚠️ In production, use: passwordEncoder.matches(dto.getPassword(), tc.getPassword())
        if (!tc.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Incorrect password.");
        }

        return tc;
    }

    // ── GET LINKED RAAHI USERS ────────────────────────────────────────────
    // Returns all Raahi users who listed this mobile as an emergency contact
    public List<LinkedUserDTO> getLinkedUsers(String mobile) {
        return userRepo.findUsersLinkedToContact(mobile);
    }
}
