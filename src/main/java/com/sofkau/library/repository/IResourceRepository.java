package com.sofkau.library.repository;

import com.sofkau.library.model.Resource;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface IResourceRepository extends MongoRepository<Resource, String> {
    List<Resource> findByType(String type);
    List<Resource> findByTheme(String theme);
    List<Resource> findByTitle(String title);
}
