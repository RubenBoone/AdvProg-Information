package advporg.einformation.advproginformation;

import advporg.einformation.advproginformation.model.Monument;
import advporg.einformation.advproginformation.repository.MonumentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MonumentControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MonumentRepository monumentRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void whenGetMonuments_thenReturnJsonMonuments() throws Exception {
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);
        Monument monument2 = new Monument("2",  "FR18890331","Eiffel tower", "FR", "1889",4);

        List<Monument> monumentList = new ArrayList<>();
        monumentList.add(monument1);
        monumentList.add(monument2);

        given(monumentRepository.findAll()).willReturn(monumentList);

        mockMvc.perform(get("/monuments"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
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
                .andExpect(jsonPath("$[1].score", is(4.0)));
    }

    @Test
    public void givenMonuCode_whenGetMonumentByMonuCode_thenReturnJsonMonument() throws Exception {
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);

        given(monumentRepository.findMonumentByMonuCode("BE19580318")).willReturn(monument1);

        mockMvc.perform(get("/monuments/{monuCode}","BE19580318"))
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
    public void givenMonument_whenGetMonumentByMonuCode_thenReturnJsonMonuments() throws Exception {
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);

        List<Monument> monumentList = new ArrayList<>();
        monumentList.add(monument1);

        given(monumentRepository.findMonumentByBuildYear("1958")).willReturn(monumentList);

        mockMvc.perform(get("/monuments/buildyear/{year}","1958"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].monuCode", is("BE19580318")))
                .andExpect(jsonPath("$[0].name", is("Atomium")))
                .andExpect(jsonPath("$[0].location", is("BE")))
                .andExpect(jsonPath("$[0].buildYear", is("1958")))
                .andExpect(jsonPath("$[0].score", is(3.5)));
    }

    @Test
    public void whenGetMonumentsByNew_thenReturnJsonMonuments() throws Exception {
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);
        Monument monument2 = new Monument("2",  "FR18890331","Eiffel tower", "FR", "1889",4);
        Monument monument3 = new Monument("3", "US18860901","Statue of Liberty", "US", "1886",3.8);

        List<Monument> monumentList = new ArrayList<>();
        monumentList.add(monument1);
        monumentList.add(monument2);
        monumentList.add(monument3);

        given(monumentRepository.findAllByOrderByBuildYearDesc()).willReturn(monumentList);

        mockMvc.perform(get("/monuments/new"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
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
    public void whenGetMonumentsByOld_thenReturnJsonMonuments() throws Exception {
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);
        Monument monument2 = new Monument("2",  "FR18890331","Eiffel tower", "FR", "1889",4);
        Monument monument3 = new Monument("3", "US18860901","Statue of Liberty", "US", "1886",3.8);

        List<Monument> monumentList = new ArrayList<>();
        monumentList.add(monument3);
        monumentList.add(monument2);
        monumentList.add(monument1);

        given(monumentRepository.findAllByOrderByBuildYearAsc()).willReturn(monumentList);

        mockMvc.perform(get("/monuments/old"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[2].id", is("1")))
                .andExpect(jsonPath("$[2].monuCode", is("BE19580318")))
                .andExpect(jsonPath("$[2].name", is("Atomium")))
                .andExpect(jsonPath("$[2].location", is("BE")))
                .andExpect(jsonPath("$[2].buildYear", is("1958")))
                .andExpect(jsonPath("$[2].score", is(3.5)))

                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[1].monuCode", is("FR18890331")))
                .andExpect(jsonPath("$[1].name", is("Eiffel tower")))
                .andExpect(jsonPath("$[1].location", is("FR")))
                .andExpect(jsonPath("$[1].buildYear", is("1889")))
                .andExpect(jsonPath("$[1].score", is(4.0)))

                .andExpect(jsonPath("$[0].id", is("3")))
                .andExpect(jsonPath("$[0].monuCode", is("US18860901")))
                .andExpect(jsonPath("$[0].name", is("Statue of Liberty")))
                .andExpect(jsonPath("$[0].location", is("US")))
                .andExpect(jsonPath("$[0].buildYear", is("1886")))
                .andExpect(jsonPath("$[0].score", is(3.8)));
    }

    @Test
    public void whenPostMonument_thenReturnJsonMonument() throws Exception{
        Monument monument1 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);

        mockMvc.perform(post("/monuments")
                        .content(mapper.writeValueAsString(monument1))
                        .contentType(MediaType.APPLICATION_JSON))
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
    public void givenMonument_whenPutMonument_thenReturnJsonMonument() throws Exception{

        Monument monument1 = new Monument("1",  "BE19580318","Eiffel tower", "FR", "1889",4);

        given(monumentRepository.findMonumentByMonuCode("BE19580318")).willReturn(monument1);

        Monument monument2 = new Monument("1", "BE19580318", "Atomium", "BE", "1958", 3.5);

        mockMvc.perform(put("/monuments")
                        .content(mapper.writeValueAsString(monument2))
                        .contentType(MediaType.APPLICATION_JSON))
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
    public void givenMonument_whenDeleteMonument_thenStatusOk() throws Exception{
        Monument monument1 = new Monument("1",  "BE19580318","Eiffel tower", "FR", "1889",4);

        given(monumentRepository.findMonumentByMonuCode("BE19580318")).willReturn(monument1);

        mockMvc.perform(delete("/monuments/{monuCode}","BE19580318")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoMonument_whenDeleteMonument_thenStatusNotFound() throws Exception{
        given(monumentRepository.findMonumentByMonuCode("IkBestaNiet")).willReturn(null);

        mockMvc.perform(delete("/monuments/{monuCode}","IkBestaNiet")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
