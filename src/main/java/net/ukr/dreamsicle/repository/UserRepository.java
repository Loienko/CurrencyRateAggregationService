package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.user.StatusType;
import net.ukr.dreamsicle.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link User}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select u from User u where u.id = :id and u.status = :statusType")
    Optional<User> findByIdAndStatus(Long id, StatusType statusType);

    @Query(value = "select u from User u where u.status = :statusType")
    Page<User> findAllByStatus(Pageable pageable, StatusType statusType);

}
