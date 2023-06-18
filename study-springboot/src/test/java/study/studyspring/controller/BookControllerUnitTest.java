package study.studyspring.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;
import study.studyspring.entity.Book;
import study.studyspring.jwt.TokenProvider;
import study.studyspring.service.BookService;
import study.studyspring.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private BookService bookService;

    @MockBean
    private TokenProvider tokenProvider;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin")
    public void saveTest() throws Exception {
        // given 테스트 준비
        Book book = new Book(null, "TestTitle", "TestAuthor");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.saveBook(book)).thenReturn(new Book(1L, "TestTitle", "TestAuthor"));

        // when 테스트 실행
        ResultActions resultActions = mockMvc.perform(post("/book").with(csrf())
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
        books.add(new Book(1L, "TestTitle", "TestAuthor"));
        books.add(new Book(2L, "HelloTitle", "HelloAuthor"));
        when(bookService.getAllBook()).thenReturn(books);

        //when
        ResultActions resultActions = mockMvc.perform(get("/book")
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("TestTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void findByIdTest() throws Exception {
        //given
        Long id = 1L;
        when(bookService.getOneBook(id)).thenReturn(new Book(1L,"JavaTest","JavaAuthor"));

        //when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}",id)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("JavaTest"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void modifyTest() throws Exception {
        //given
        Long id = 1L;
        Book book = new Book(null, "BookTitle", "BookAuthor");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.modifyBook(id,book)).thenReturn(new Book(1L,"BookTitle","BookAuthor"));

        //when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}",id)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("BookTitle"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "admin")
    public void deleteTest() throws Exception {
        //given
        Long id = 1L;
        when(bookService.deleteBook(id)).thenReturn("ok");

        //when
        ResultActions resultAction = mockMvc.perform(delete("/book/{id}",id)
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
