package com.phonebookertask;

import com.phonebookertask.model.Book;
import com.phonebookertask.model.PhoneModel;
import com.phonebookertask.repository.BookRepository;
import com.phonebookertask.repository.PhoneModelRepository;
import com.phonebookertask.rest.request.PhoneBookRequest;
import com.phonebookertask.rest.response.ErrorResponse;
import com.phonebookertask.rest.response.SuccessResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhoneBookerTaskApplicationTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PhoneModelRepository phoneModelRepository;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
    }

    @Test
    void testBook_success() {
        var model = getModel();
        String username = "test";
        var bookRequest = new PhoneBookRequest(username, model.getId());

        var response = this.restTemplate.postForEntity(baseURL() + "/api/book", bookRequest, SuccessResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().message()).isEqualTo("Phone booked");
        var savedBook = bookRepository.findFirstByUsernameAndPhoneModelId(username, model.getId());
        assertThat(savedBook).isNotNull();
    }

    @Test
    void testBook_invalidParams() {
        var bookRequest = new PhoneBookRequest("", 0);

        var response = this.restTemplate.postForEntity(baseURL() + "/api/book", bookRequest, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().error()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        var expectedErrors = Arrays.asList("Username is empty", "Model ID not set");
        Collections.sort(expectedErrors);
        var actualErrors = response.getBody().errors();
        Collections.sort(actualErrors);
        assertThat(actualErrors).isEqualTo(expectedErrors);
        assertThat(bookRepository.count()).isEqualTo(0L);
    }

    @Test
    void testReturn_success() {
        String username = "test";
        var model = getModel();
        var book = new Book();
        book.setPhoneModel(model);
        book.setUsername(username);
        book.setBookDate(Instant.now());
        bookRepository.save(book);

        var bookRequest = new PhoneBookRequest(username, model.getId());

        var response = this.restTemplate.postForEntity(baseURL() + "/api/book/return", bookRequest, SuccessResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().message()).isEqualTo("Phone returned");
        assertThat(bookRepository.count()).isEqualTo(0L);
    }

    @Test
    void testReturn_successIdempotency() {
        var bookRequest = new PhoneBookRequest("test", 111);

        var response = this.restTemplate.postForEntity(baseURL() + "/api/book/return", bookRequest, SuccessResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().message()).isEqualTo("Phone returned");
    }

    @Test
    void testReturn_invalidParams() {
        var returnRequest = new PhoneBookRequest("", 0);

        var response = this.restTemplate.postForEntity(baseURL() + "/api/book/return", returnRequest, ErrorResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().error()).isEqualTo(HttpStatus.BAD_REQUEST.getReasonPhrase());
        var expectedErrors = Arrays.asList("Username is empty", "Model ID not set");
        Collections.sort(expectedErrors);
        var actualErrors = response.getBody().errors();
        Collections.sort(actualErrors);
        assertThat(actualErrors).isEqualTo(expectedErrors);
        assertThat(bookRepository.count()).isEqualTo(0L);
    }

    private PhoneModel getModel() {
        return phoneModelRepository.findAll().iterator().next();
    }

    private String baseURL() {
        return "http://localhost:" + port;
    }
}
