package hkmu.comps380f.BookStore.dao;

import hkmu.comps380f.BookStore.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
