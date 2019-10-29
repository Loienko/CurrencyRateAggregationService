package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.userDetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link UserDetails}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    Optional<UserDetails> findUserDetailsByUserId(Long id);
}