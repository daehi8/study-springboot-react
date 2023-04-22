package study.studyspring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    public ResponseEntity<?> findALl(){
        return new ResponseEntity<String>("ok", HttpStatus.OK);
    }
}
