package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Role;
import net.ukr.dreamsicle.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType roleUser);
}