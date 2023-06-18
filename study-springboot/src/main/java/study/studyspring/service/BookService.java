package study.studyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.entity.Book;
import study.studyspring.repository.BookRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book saveBook (Book book) {
        return bookRepository.save(book);
    }

    @Transactional(readOnly = true)
    public Book getOneBook(Long id){
        return bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요."));
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }

    // 함수 종료 -> 트랙잭션 종료 -> 영속화 된 데이터를 DB로 갱신(flush) -> commit -> 더티체킹
    @Transactional
    public Book modifyBook(Long id, Book book){
        Book bookEntity = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요."));
        bookEntity.setTitle(book.getTitle());
        bookEntity.setAuthor(book.getAuthor());
        return bookEntity;
    }

    @Transactional
    public String deleteBook(Long id){
         bookRepository.deleteById(id);
    return "ok";
    }
}
