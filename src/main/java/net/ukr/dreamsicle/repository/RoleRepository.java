package net.ukr.dreamsicle.repository;

import net.ukr.dreamsicle.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleUser);
}
