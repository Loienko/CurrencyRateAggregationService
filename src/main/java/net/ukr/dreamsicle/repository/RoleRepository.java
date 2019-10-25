package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.user.Role;
import net.ukr.dreamsicle.model.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface that extends {@link JpaRepository} for class {@link Role}.
 *
 * @author yurii.loienko
 * @version 1.0
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleUser);
}
