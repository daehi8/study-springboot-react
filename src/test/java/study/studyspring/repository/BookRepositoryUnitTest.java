package study.studyspring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.entity.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@DataJpaTest
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void saveTest() {
        //given
        Book book = new Book(null, "책제목", "책저자");

        //when
        Book bookEntity = bookRepository.save(book);

        //then
        assertEquals("책제목", bookEntity.getTitle());
    }
}
