package hkmu.comps380f.BookStore.dao;

import hkmu.comps380f.BookStore.model.BookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookUserRepository extends JpaRepository<BookUser, String> {
}
