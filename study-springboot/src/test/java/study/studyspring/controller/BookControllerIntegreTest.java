package study.studyspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;
import study.studyspring.entity.Book;
import study.studyspring.repository.BookRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegreTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {
        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();
        //entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT = 1").executeUpdate();
    }

    @Test
    @WithMockUser(username = "admin")
    public void saveTest() throws Exception {
        // given 테스트 준비
        Book book = new Book(null, "TestTitle", "TestAuthor");
        String content = new ObjectMapper().writeValueAsString(book);

        // when 테스트 실행
        ResultActions resultActions = mockMvc.perform(post("/book")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then 검증
        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("TestTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void getAllTest() throws Exception {
        //given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "TestTitle", "TestAuthor"));
        books.add(new Book(null, "HelloTitle", "HelloAuthor"));
        books.add(new Book(null, "SpringTitle", "SpringAuthor"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[2].title").value("SpringTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void findByIdTest() throws Exception {
        //given
        Long id = 1L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "TestTitle", "TestAuthor"));
        books.add(new Book(null, "HelloTitle", "HelloAuthor"));
        books.add(new Book(null, "SpringTitle", "SpringAuthor"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("TestTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void modifyTest() throws Exception {
        //given
        Long id = 3L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "TestTitle", "TestAuthor"));
        books.add(new Book(null, "HelloTitle", "HelloAuthor"));
        books.add(new Book(null, "SpringTitle", "SpringAuthor"));
        bookRepository.saveAll(books);

        Book book = new Book(null, "BookTitle", "BookAuthor");
        String content = new ObjectMapper().writeValueAsString(book);

        //when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.title").value("BookTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void deleteTest() throws Exception {
        //given
        Long id = 3L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "TestTitle", "TestAuthor"));
        books.add(new Book(null, "HelloTitle", "HelloAuthor"));
        books.add(new Book(null, "SpringTitle", "SpringAuthor"));
        bookRepository.saveAll(books);

        //when
        ResultActions resultAction = mockMvc.perform(delete("/book/{id}", id)
                .with(csrf())
                .accept(MediaType.TEXT_PLAIN));

        //then
        resultAction
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestReult = resultAction.andReturn();
        String result = requestReult.getResponse().getContentAsString();

        assertEquals("ok",result);
    }
}
