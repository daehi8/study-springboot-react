package study.studyspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.studyspring.entity.Book;

public interface BookRepository extends JpaRepository<Book,Long> {
}
