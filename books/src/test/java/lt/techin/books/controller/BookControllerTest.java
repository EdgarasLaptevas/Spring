package lt.techin.books.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lt.techin.books.model.Book;
import lt.techin.books.model.BookDetails;
import lt.techin.books.model.Category;
import lt.techin.books.model.Review;
import lt.techin.books.security.SecurityConfig;
import lt.techin.books.service.BookService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = BookController.class)
@Import(SecurityConfig.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    // Happy path test
    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    void getBooks_whenFindAll_thenReturnAllAnd200() throws Exception {

        // given
        Book book1 = new Book("Food for Thought: Essays and Ruminations",
                false, "https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png",
                new BigDecimal("50.21"), "Comedy", "Alton Brown",
                List.of(),
                List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
                new BookDetails("978-1668064214", 304, "English"));

        Book book2 = new Book("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century",
                false, "https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png",
                new BigDecimal("30.30"), "Drama",
                "Ian Mortimer",
                List.of(new Review("Great book. An introduction to Medieval England"), new Review("I don't think you can write a WORSE book!!!")),
                List.of(new Category("History"), new Category("Nonfiction")),
                new BookDetails("978-1439112908", 368, "English"));

        List<Book> books = List.of(book1, book2);

        // Paduodami duomenys į mūsų „duomenų bazę“ - tas bus grąžinama metodo
        BDDMockito.given(bookService.findAllBooks()).willReturn(books);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))

                // then
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))

                // Check first book's regular fields
                // Testuojame ar id laukas egzistuoja; žinoma, jis nėra inkrementuojamas, nes nėra
                // sąsajos su duomenų baze
                .andExpect(MockMvcResultMatchers.jsonPath("[0].id").exists())
                .andExpect(jsonPath("[0].title").value("Food for Thought: Essays and Ruminations"))
                .andExpect(jsonPath("[0].reserved").value(false))
                .andExpect(jsonPath("[0].cover").value("https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png"))
                .andExpect(jsonPath("[0].price").value("50.21"))
                .andExpect(jsonPath("[0].category").value("Comedy"))
                .andExpect(jsonPath("[0].author").value("Alton Brown"))


                // Check first book's reviews
                .andExpect(jsonPath("[0].reviews").isArray())
                .andExpect(jsonPath("[0].reviews", Matchers.hasSize(0)))

                // Check first book's categories
                .andExpect(jsonPath("[0].categories").isArray())
                .andExpect(jsonPath("[0].categories", Matchers.hasSize(2)))
                .andExpect(jsonPath("[0].categories.[0].id").exists())
                .andExpect(jsonPath("[0].categories.[0].name").value("Biographies & Memoirs"))
                .andExpect(jsonPath("[0].categories.[1].id").exists())
                .andExpect(jsonPath("[0].categories.[1].name").value("Arts & Literature"))

                // Check first book's bookDetail
                .andExpect(jsonPath("[0].bookDetails.id").exists())
                .andExpect(jsonPath("[0].bookDetails.isbn").value("978-1668064214"))
                .andExpect(jsonPath("[0].bookDetails.pageCount").value(304))
                .andExpect(jsonPath("[0].bookDetails.language").value("English"));

        // TODO: patikrinti antros knygos laukus...

        // Užtikriname, jog findAllBooks buvo kviestat tik vieną kartą
        Mockito.verify(bookService, times(1)).findAllBooks();
    }

    // Unhappy path
    @Test
    void getBooks_whenFindAllUnauthenticated_thenRespond401() throws Exception {
        // given
        Book book1 = new Book("Food for Thought: Essays and Ruminations",
                false, "https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png",
                new BigDecimal("50.21"), "Comedy", " Alton Brown",
                List.of(),
                List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
                new BookDetails("978-1668064214", 304, "English"));

        Book book2 = new Book("The Time Traveller's Guide to Medieval England: A Handbook for Visitors to the Fourteenth Century",
                false, "https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png",
                new BigDecimal("30.30"), "Drama",
                "Ian Mortimer",
                List.of(new Review("Great book. An introduction to Medieval England"), new Review("I don't think you can write a WORSE book!!!")),
                List.of(new Category("History"), new Category("Nonfiction")),
                new BookDetails("978-1439112908", 368, "English"));


        List<Book> books = List.of(book1, book2);

        given(bookService.findAllBooks()).willReturn(books);

        // when
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                // then
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").doesNotExist());

        // Patikrinti, ar findAllBooks NEBUVO kviestas
        Mockito.verify(bookService, times(0)).findAllBooks();
    }

    // TODO: unhappy path tests: getBooks - what if user is authenticated, but the returned list
    //  is empty?

    //unhappy
    @Test
    @WithMockUser(authorities = "SCOPE_ROLE_USER")
    void getBooks_whenUserGetBook_thenReturnEmptyListAnd200() throws Exception {
        //given
        List<Book> books = List.of();

        given(bookService.findAllBooks()).willReturn(books);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/books"))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", Matchers.hasSize(0)));

        Mockito.verify(bookService, times(1)).findAllBooks();
    }

    // Happy path
    @Test
    @WithMockUser
    void addBook_whenUserAddBook_thenReturnAnd201() throws Exception {
        // given
        Book book = new Book("Foodforthoughtessaysandruminations", false,
                "https://png.pngtree.com/png-clipart/20230307/original/pngtree-realistic-web-url-icon-background-png-image_8975518.png",
                new BigDecimal("25.31"), "Comedy",
                "Altonbrown",
                List.of(),
                List.of(new Category("Biographies & Memoirs"), new Category("Arts & Literature")),
                new BookDetails("978-1668064214", 304, "English"));

        BDDMockito.given(bookService.saveBook(ArgumentMatchers.any(Book.class))).willReturn(book);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book)))
                // then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("title").value("Foodforthoughtessaysandruminations"))
                .andExpect(jsonPath("author").value("Altonbrown"))


                // Check first book's reviews
                .andExpect(jsonPath("reviews").isArray())
                .andExpect(jsonPath("reviews", Matchers.hasSize(0)))
//

                // Check first book's categories
                .andExpect(jsonPath("categories").isArray())
                .andExpect(jsonPath("categories", Matchers.hasSize(2)))
                .andExpect(jsonPath("categories.[0].id").exists())
                .andExpect(jsonPath("categories.[0].name").value("Biographies & Memoirs"))
                .andExpect(jsonPath("categories.[1].id").exists())
                .andExpect(jsonPath("categories.[1].name").value("Arts & Literature"))

                // Check first book's bookDetail
                .andExpect(jsonPath("bookDetails.id").exists())
                .andExpect(jsonPath("bookDetails.isbn").value("978-1668064214"))
                .andExpect(jsonPath("bookDetails.pageCount").value(304))
                .andExpect(jsonPath("bookDetails.language").value("English"));


        Mockito.verify(bookService, times(1)).saveBook(ArgumentMatchers.any(Book.class));
    }

    //unhappy
    @Test
    @WithMockUser
    void addBook_whenAdminAddBookThatDoNotMatchValidation_thenReturn400() throws Exception {
        //given
        String invalidBookTitle = "{ \"title\" : \"\"}";

        //when
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidBookTitle))
                //then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("title").value("size must be between 2 and 120"));

    }

    // TODO: unhappy path methods: addBook - what happens if user is Admin, but title does not
    //  match validation (can be many tests)
    // TODO: What happens if unauthenticated user tries to call this endpoint?
    @Test
    void getEndPoint_whenUnauthenticatedGetEndPoint_thenReturn401() throws Exception {
        //when
        mockMvc.perform(MockMvcRequestBuilders.head("/api/books"))
                //then
                .andExpect(status().isUnauthorized());
    }
}

