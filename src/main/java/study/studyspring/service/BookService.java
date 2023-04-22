package study.studyspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.studyspring.repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
}
