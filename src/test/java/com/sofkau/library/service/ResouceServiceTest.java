package com.sofkau.library.service;

import com.sofkau.library.dto.ResourceDto;
import com.sofkau.library.model.Resource;
import com.sofkau.library.repository.IResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ResouceServiceTest {

    @MockBean
    private IResourceRepository iResourceRepository;

    @Autowired
    private ResourceService service;


    @Test
    @DisplayName("Test findAll resources")
    void getAllTest() {
        Resource resource = new Resource();
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Resource resource2 = new Resource();
        resource2.setTitle("JavaScript");
        resource2.setDescription("Description 2");
        resource2.setTheme("history");
        resource2.setType("magazine");

        Mockito.when(iResourceRepository.findAll()).thenReturn(List.of(resource, resource2));

        var result = service.getAll();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(resource.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(resource.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(resource.getTheme(), result.get(0).getTheme());
        Assertions.assertEquals(resource.getType(), result.get(0).getType());
        Assertions.assertEquals(resource2.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(resource2.getDescription(), result.get(1).getDescription());
        Assertions.assertEquals(resource2.getTheme(), result.get(1).getTheme());
        Assertions.assertEquals(resource2.getType(), result.get(1).getType());
    }

    @Test
    @DisplayName("Test findById resources")
    void getByIdTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");


        Mockito.when(iResourceRepository.findById("xxx")).thenReturn(Optional.of(resource));

        var result = service.getById("xxx");
        Assertions.assertEquals(resource.getId(), result.getId());
        Assertions.assertEquals(resource.getTitle(), result.getTitle());
        Assertions.assertEquals(resource.getDescription(), result.getDescription());
        Assertions.assertEquals(resource.getTheme(), result.getTheme());
        Assertions.assertEquals(resource.getType(), result.getType());
    }

    @Test
    @DisplayName("Test create resources")
    void createResourceTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setTitle("React");
        resourceDto.setDescription("Description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");


        Mockito.when(iResourceRepository.save(Mockito.any())).thenReturn(resource);

        var result = service.create(resourceDto);

        Assertions.assertNotNull(result, "not null");
        Assertions.assertEquals(resourceDto.getTitle(), result.getTitle());
        Assertions.assertEquals(resourceDto.getDescription(), result.getDescription());
        Assertions.assertEquals(resourceDto.getTheme(), result.getTheme());
        Assertions.assertEquals(resourceDto.getType(), result.getType());
    }

    @Test
    @DisplayName("Test update resources")
    void updateResourceTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId("xxx");
        resourceDto.setTitle("React");
        resourceDto.setDescription("Description");
        resourceDto.setTheme("education");
        resourceDto.setType("book");

        Mockito.when(iResourceRepository.save(Mockito.any())).thenReturn(resource);

        var result = service.create(resourceDto);

        Assertions.assertEquals(resourceDto.getId(), result.getId());
        Assertions.assertEquals(resourceDto.getTitle(), result.getTitle());
        Assertions.assertEquals(resourceDto.getDescription(), result.getDescription());
        Assertions.assertEquals(resourceDto.getTheme(), result.getTheme());
        Assertions.assertEquals(resourceDto.getType(), result.getType());
    }

    @Test
    @DisplayName("Test getAllByQueries ")
    void getAllByQueriesTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Resource resource2 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Resource resource3 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Mockito.when(iResourceRepository.findAllByQueries("book","education")).thenReturn(List.of(resource,resource2, resource3));

        var result = service.getAllByQueries("book", "education");

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(resource.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(resource.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(resource.getTheme(), result.get(0).getTheme());
        Assertions.assertEquals(resource.getType(), result.get(0).getType());
        Assertions.assertEquals(resource2.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(resource2.getDescription(), result.get(1).getDescription());
        Assertions.assertEquals(resource2.getTheme(), result.get(1).getTheme());
        Assertions.assertEquals(resource2.getType(), result.get(1).getType());
        Assertions.assertEquals(resource3.getTitle(), result.get(2).getTitle());
        Assertions.assertEquals(resource3.getDescription(), result.get(2).getDescription());
        Assertions.assertEquals(resource3.getTheme(), result.get(2).getTheme());
        Assertions.assertEquals(resource3.getType(), result.get(2).getType());
    }


    @Test
    @DisplayName("Test getAllByQueries when type is blank")
    void getAllByQueriesWhenTypeIsBlankTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("magazine");

        Resource resource2 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Resource resource3 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Mockito.when(iResourceRepository.findResourcesByTheme("education")).thenReturn(List.of(resource,resource2, resource3));

        var result = service.getAllByQueries("", "education");

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(resource.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(resource.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(resource.getTheme(), result.get(0).getTheme());
        Assertions.assertEquals(resource.getType(), result.get(0).getType());
        Assertions.assertEquals(resource2.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(resource2.getDescription(), result.get(1).getDescription());
        Assertions.assertEquals(resource2.getTheme(), result.get(1).getTheme());
        Assertions.assertEquals(resource2.getType(), result.get(1).getType());
        Assertions.assertEquals(resource3.getTitle(), result.get(2).getTitle());
        Assertions.assertEquals(resource3.getDescription(), result.get(2).getDescription());
        Assertions.assertEquals(resource3.getTheme(), result.get(2).getTheme());
        Assertions.assertEquals(resource3.getType(), result.get(2).getType());
    }

    @Test
    @DisplayName("Test getAllByQueries when theme is blank")
    void getAllByQueriesWhenThemeIsBlankTest() {
        Resource resource = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("history");
        resource.setType("book");

        Resource resource2 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Resource resource3 = new Resource();
        resource.setId("xxx");
        resource.setTitle("React");
        resource.setDescription("Description");
        resource.setTheme("education");
        resource.setType("book");

        Mockito.when(iResourceRepository.findResourcesByType("book")).thenReturn(List.of(resource,resource2, resource3));

        var result = service.getAllByQueries("book", "");

        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(resource.getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(resource.getDescription(), result.get(0).getDescription());
        Assertions.assertEquals(resource.getTheme(), result.get(0).getTheme());
        Assertions.assertEquals(resource.getType(), result.get(0).getType());
        Assertions.assertEquals(resource2.getTitle(), result.get(1).getTitle());
        Assertions.assertEquals(resource2.getDescription(), result.get(1).getDescription());
        Assertions.assertEquals(resource2.getTheme(), result.get(1).getTheme());
        Assertions.assertEquals(resource2.getType(), result.get(1).getType());
        Assertions.assertEquals(resource3.getTitle(), result.get(2).getTitle());
        Assertions.assertEquals(resource3.getDescription(), result.get(2).getDescription());
        Assertions.assertEquals(resource3.getTheme(), result.get(2).getTheme());
        Assertions.assertEquals(resource3.getType(), result.get(2).getType());
    }


}
