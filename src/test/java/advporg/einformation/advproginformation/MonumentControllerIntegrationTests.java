package advporg.einformation.advproginformation;

import advporg.einformation.advproginformation.model.Monument;
import advporg.einformation.advproginformation.repository.MonumentRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MonumentControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MonumentRepository monumentRepository;

    private Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);
    private Monument monument2 = new Monument("2", "FR18890331", "Eiffel tower", "FR", "1889", 4);
    private Monument monument3 = new Monument("3", "US18860901", "Statue of Liberty", "US", "1886", 3.8);
    private Monument monumentToDelete = new Monument("999", "deleteMe", "test", "test", "0", 0);

    @BeforeEach
    public void beforeAllTests() {
        monumentRepository.deleteAll();
        monumentRepository.save(monument1);
        monumentRepository.save(monument2);
        monumentRepository.save(monument3);
        monumentRepository.save(monumentToDelete);
    }

    @AfterEach
    public void afterAllTests() {
        monumentRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetMonuments_thenReturnJsonMonuments() throws Exception {

        mockMvc.perform(get("/monuments"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].monuCode", is("BE19580318")))
                .andExpect(jsonPath("$[0].name", is("Atomium")))
                .andExpect(jsonPath("$[0].location", is("BE")))
                .andExpect(jsonPath("$[0].buildYear", is("1958")))
                .andExpect(jsonPath("$[0].score", is(3.5)))

                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].monuCode", is("FR18890331")))
                .andExpect(jsonPath("$[1].name", is("Eiffel tower")))
                .andExpect(jsonPath("$[1].location", is("FR")))
                .andExpect(jsonPath("$[1].buildYear", is("1889")))
                .andExpect(jsonPath("$[1].score", is(4.0)))

                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].monuCode", is("US18860901")))
                .andExpect(jsonPath("$[2].name", is("Statue of Liberty")))
                .andExpect(jsonPath("$[2].location", is("US")))
                .andExpect(jsonPath("$[2].buildYear", is("1886")))
                .andExpect(jsonPath("$[2].score", is(3.8)));
    }

    @Test
    public void givenMonument_whenGetMonumentByMonuCode_thenReturnJsonMonument() throws Exception {

        mockMvc.perform(get("/monuments/{monuCode}", "BE19580318"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.monuCode", is("BE19580318")))
                .andExpect(jsonPath("$.name", is("Atomium")))
                .andExpect(jsonPath("$.location", is("BE")))
                .andExpect(jsonPath("$.buildYear", is("1958")))
                .andExpect(jsonPath("$.score", is(3.5)));
    }

    @Test
    public void givenYear_whenGetMonumentByBuildYear_thenReturnJsonMonuments() throws Exception {

        mockMvc.perform(get("/monuments/buildyear/{year}", "1886"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))

                .andExpect(jsonPath("$[0].id", is("3")))
                .andExpect(jsonPath("$[0].monuCode", is("US18860901")))
                .andExpect(jsonPath("$[0].name", is("Statue of Liberty")))
                .andExpect(jsonPath("$[0].location", is("US")))
                .andExpect(jsonPath("$[0].buildYear", is("1886")))
                .andExpect(jsonPath("$[0].score", is(3.8)));
    }

    @Test
    public void whenGetMonumentsByOld_thenReturnJsonMonuments() throws Exception {

        mockMvc.perform(get("/monuments/old"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[3].id", is("1")))
                .andExpect(jsonPath("$[3].monuCode", is("BE19580318")))
                .andExpect(jsonPath("$[3].name", is("Atomium")))
                .andExpect(jsonPath("$[3].location", is("BE")))
                .andExpect(jsonPath("$[3].buildYear", is("1958")))
                .andExpect(jsonPath("$[3].score", is(3.5)))

                .andExpect(jsonPath("$[2].id", is("2")))
                .andExpect(jsonPath("$[2].monuCode", is("FR18890331")))
                .andExpect(jsonPath("$[2].name", is("Eiffel tower")))
                .andExpect(jsonPath("$[2].location", is("FR")))
                .andExpect(jsonPath("$[2].buildYear", is("1889")))
                .andExpect(jsonPath("$[2].score", is(4.0)))

                .andExpect(jsonPath("$[1].id", is("3")))
                .andExpect(jsonPath("$[1].monuCode", is("US18860901")))
                .andExpect(jsonPath("$[1].name", is("Statue of Liberty")))
                .andExpect(jsonPath("$[1].location", is("US")))
                .andExpect(jsonPath("$[1].buildYear", is("1886")))
                .andExpect(jsonPath("$[1].score", is(3.8)));
    }

    @Test
    public void whenGetMonumentsByNew_thenReturnJsonMonuments() throws Exception {

        mockMvc.perform(get("/monuments/new"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))

                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].monuCode", is("BE19580318")))
                .andExpect(jsonPath("$[0].name", is("Atomium")))
                .andExpect(jsonPath("$[0].location", is("BE")))
                .andExpect(jsonPath("$[0].buildYear", is("1958")))
                .andExpect(jsonPath("$[0].score", is(3.5)))

                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].monuCode", is("FR18890331")))
                .andExpect(jsonPath("$[1].name", is("Eiffel tower")))
                .andExpect(jsonPath("$[1].location", is("FR")))
                .andExpect(jsonPath("$[1].buildYear", is("1889")))
                .andExpect(jsonPath("$[1].score", is(4.0)))

                .andExpect(jsonPath("$[2].id", is("3")))
                .andExpect(jsonPath("$[2].monuCode", is("US18860901")))
                .andExpect(jsonPath("$[2].name", is("Statue of Liberty")))
                .andExpect(jsonPath("$[2].location", is("US")))
                .andExpect(jsonPath("$[2].buildYear", is("1886")))
                .andExpect(jsonPath("$[2].score", is(3.8)));
    }

    @Test
    public void whenPostMonument_thenReturnJsonMonument() throws Exception {

        Monument newMonument = new Monument("4", "0123456", "test monument", "BE", "2022", 5.0);

        mockMvc.perform(post("/monuments")
                        .content(mapper.writeValueAsString(newMonument))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("4")))
                .andExpect(jsonPath("$.monuCode", is("0123456")))
                .andExpect(jsonPath("$.name", is("test monument")))
                .andExpect(jsonPath("$.location", is("BE")))
                .andExpect(jsonPath("$.buildYear", is("2022")))
                .andExpect(jsonPath("$.score", is(5.0)));
    }

    @Test
    public void givenMonument_whenPutMonument_thenReturnJsonMonument() throws Exception {

        Monument updatedMonument = new Monument("1", "BE19580318", "test monument", "BE", "2022", 5.0);

        mockMvc.perform(put("/monuments")
                        .content(mapper.writeValueAsString(updatedMonument))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.monuCode", is("BE19580318")))
                .andExpect(jsonPath("$.name", is("test monument")))
                .andExpect(jsonPath("$.location", is("BE")))
                .andExpect(jsonPath("$.buildYear", is("2022")))
                .andExpect(jsonPath("$.score", is(5.0)));
    }

    @Test
    public void givenMonument_whenDeleteMonument_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/monuments/{monuCode}", "deleteMe")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoMonument_whenDeleteMonument_thenStatusNotFound() throws Exception {

        mockMvc.perform(delete("/monuments/{monuCode}", "notFound")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
