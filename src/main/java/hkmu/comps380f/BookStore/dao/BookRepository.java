package hkmu.comps380f.BookStore.dao;

import hkmu.comps380f.BookStore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}

