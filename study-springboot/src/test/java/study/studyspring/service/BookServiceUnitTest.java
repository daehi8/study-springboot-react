package study.studyspring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.studyspring.entity.Book;
import study.studyspring.repository.BookRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void saveTest() {
        //given
        Book book = new Book();
        book.setTitle("책제목");
        book.setAuthor("책저자");

        //stub
        when(bookRepository.save(book)).thenReturn(book);

        //test
        Book bookEntity = bookService.saveBook(book);

        //then
        assertEquals(bookEntity, book);
    }
}
