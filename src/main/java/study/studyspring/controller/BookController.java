package study.studyspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.studyspring.entity.Book;
import study.studyspring.service.BookService;

@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookServie;

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody Book book){
        return new ResponseEntity<>(bookServie.saveBook(book), HttpStatus.CREATED);
    }

    @GetMapping("/book")
    public ResponseEntity<?> findALl(){
        return new ResponseEntity<>(bookServie.getAllBook(), HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<?> findbyId(@PathVariable Long id){
        return new ResponseEntity<>(bookServie.getOneBook(id), HttpStatus.OK);
    }

    @PutMapping("book/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book book){
        return new ResponseEntity<>(bookServie.modifyBook(id, book), HttpStatus.OK);
    }

    @DeleteMapping("book/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id, @RequestBody Book book){
        return new ResponseEntity<>(bookServie.deleteBook(id), HttpStatus.OK);
    }
}
