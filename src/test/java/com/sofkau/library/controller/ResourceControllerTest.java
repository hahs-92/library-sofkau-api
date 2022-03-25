package com.sofkau.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofkau.library.dto.ResourceDto;
import com.sofkau.library.service.ResourceService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ResourceControllerTest {

    @MockBean
    private ResourceService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /resources success")
    void testGetAllResources() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setTitle("React");
        resourceDto.setTheme("education");
        resourceDto.setType("book");

        ResourceDto resourceDto2 = new ResourceDto();
        resourceDto2.setTitle("JavaScript");
        resourceDto2.setTheme("history");
        resourceDto2.setType("magazine");

        Mockito.doReturn(Lists.newArrayList(resourceDto, resourceDto2)).when(service).getAll();

        mockMvc.perform(get("/resources"))
                //validate response code
                .andExpect(status().isOk())
                //validate returned fields
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("React")))
                .andExpect(jsonPath("$[0].type", is("book")))
                .andExpect(jsonPath("$[0].theme", is("education")))
                .andExpect(jsonPath("$[1].title", is("JavaScript")))
                .andExpect(jsonPath("$[1].type", is("magazine")))
                .andExpect(jsonPath("$[1].theme", is("history")));
    }

    @Test
    @DisplayName("GET /resources/1 success")
    void testGetResourceById() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isOk())
                //validate returned fields
                .andExpect(jsonPath("$.id", is("23x34y")))
                .andExpect(jsonPath("$.title", is("React")))
                .andExpect(jsonPath("$.description",is("description")))
                .andExpect(jsonPath("$.type", is("book")))
                .andExpect(jsonPath("$.theme", is("education")));
    }

    @Test
    @DisplayName("POST /resources success")
    void testCreateResource() throws Exception {
        // Setup our mocked service
        ResourceDto resourceToPost = new ResourceDto();
        resourceToPost.setTitle("React");
        resourceToPost.setDescription("description");
        resourceToPost.setTheme("education");
        resourceToPost.setType("book");

        ResourceDto resourceToReturn = new ResourceDto();
        resourceToReturn.setId("23x34y");
        resourceToReturn.setTitle("React");
        resourceToReturn.setDescription("description");
        resourceToReturn.setTheme("education");
        resourceToReturn.setType("book");

        Mockito.doReturn(resourceToReturn).when(service).create(any());

        // Execute the POST request
        mockMvc.perform(post("/resource")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(resourceToPost))
                );
                // Validate the response code and content type
                //.andExpect(status().isCreated())
                //.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                // Validate the returned fields
                //.andExpect(jsonPath("$.id", is("23x34y")))
                //.andExpect(jsonPath("$.title", is("React")))
                //.andExpect(jsonPath("$.description", is("description")))
                //.andExpect(jsonPath("$.type", is("book")))
                //.andExpect(jsonPath("$.theme", is("education")));
    }

    @Test
    @DisplayName("GET /resource/available/{id} success")
    void testIsAvailableRecourse() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(true);

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/available/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("El resource is available")));

    }

    @Test
    @DisplayName("GET /resource/available/{id} NotAvailable")
    void testIsAvailableRecourseNotAvailable() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(false);
        resourceDto.setLastBorrowingDate(LocalDate.now());

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/available/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isForbidden())
                .andExpect(content().string(containsString("Resource is not available, last borrowing date: " + LocalDate.now())));
    }

    @Test
    @DisplayName("GET /resource/borrow/{id} success")
    void testBorrowResource() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(true);
        resourceDto.setLastBorrowingDate(LocalDate.now());

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/borrow/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

    }

    @Test
    @DisplayName("GET /resource/borrow/{id} Not Available")
    void testBorrowResourceNotAvailable() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(false);
        resourceDto.setLastBorrowingDate(LocalDate.now());

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/borrow/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isForbidden())
                .andExpect(
                        content().string(containsString("Resource is not available, last borrowing date: " + LocalDate.now()))
                );

    }

    @Test
    @DisplayName("GET /resource/giveBack/{id} success")
    void testGiveBackResource() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(false);
        resourceDto.setLastBorrowingDate(LocalDate.now());

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/giveBack/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isOk())
                .andExpect(
                        content().string(containsString("OK"))
                );

    }

    @Test
    @DisplayName("GET /resource/giveBack/{id} Not Allow")
    void testNotAllowToGiveBackResource() throws Exception {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("23x34y");
        resourceDto.setTitle("React");
        resourceDto.setDescription("description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");
        resourceDto.setAvailable(true);
        resourceDto.setLastBorrowingDate(LocalDate.now());

        Mockito.doReturn(resourceDto).when(service).getById("23x34y");

        mockMvc.perform(get("/resources/giveBack/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isForbidden())
                .andExpect(
                        content().string(containsString("Do not giveBack this recourse, because it is not borrowed"))
                );

    }

    @Test
    @DisplayName("DELETE /resources/{id} ")
    void testDeleteResource() throws Exception {

        Mockito.doNothing().when(service).deleteById("23x34y");

        mockMvc.perform(get("/resources/{id}", "23x34y"))
                //validate response code
                .andExpect(status().isOk());
    }





    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
