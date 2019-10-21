package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findUserDetailsByUserId(Long id);
}