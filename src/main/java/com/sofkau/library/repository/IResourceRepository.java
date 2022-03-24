package com.sofkau.library.repository;

import com.sofkau.library.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


import java.util.List;

public interface IResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findAll();
    @Query(value = "{ type: ?0 }")
    List<Resource> findResourcesByType(String type);
    @Query(value = "{ theme: ?0 }")
    List<Resource> findResourcesByTheme(String theme);
    @Query(value = "{ type: ?0, theme: ?1}")
    List<Resource> findAllByQueries(String type, String theme);
}
