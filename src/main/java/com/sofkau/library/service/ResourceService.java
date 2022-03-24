package com.sofkau.library.service;

import com.sofkau.library.dto.ResourceDto;
import com.sofkau.library.mapper.ResourceMapper;
import com.sofkau.library.model.Resource;
import com.sofkau.library.repository.IResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private IResourceRepository iResourceRepository;
    private ResourceMapper mapper  = new ResourceMapper();

    public List<ResourceDto> getAll() {
        List<Resource> resources = iResourceRepository.findAll();
        return mapper.fromCollectionList(resources);
    }

    public ResourceDto getById(String id) {
        Resource resource = iResourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Resource not found"));
        return mapper.fromCollection(resource);
    }

    public ResourceDto create(ResourceDto dto) {
        Resource resource = mapper.fromDTO(dto);
        return  mapper.fromCollection(iResourceRepository.save(resource));
    }

    public ResourceDto update(ResourceDto dto) {
        Resource resource = mapper.fromDTO(dto);
        iResourceRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException(("Resource not found")));
        return mapper.fromCollection(iResourceRepository.save(resource));
    }

    public void deleteById(String id) {
        iResourceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(("Resource not found")));

        iResourceRepository.deleteById(id);
    }

    public List<ResourceDto> getAllByQueries(String type, String theme) {
        if(!type.isBlank() && !theme.isBlank()) {
            List<Resource> resources = iResourceRepository.findAllByQueries(type, theme);
            return mapper.fromCollectionList(resources);
        }

        if(!type.isBlank()) {
            List<Resource> resources = iResourceRepository.findResourcesByType(type);
            return mapper.fromCollectionList(resources);
        }

        if(!theme.isBlank()) {
            List<Resource> resources = iResourceRepository.findResourcesByTheme(theme);
            return mapper.fromCollectionList(resources);
        }

        List<Resource> resources = iResourceRepository.findAll();
        return mapper.fromCollectionList(resources);
    }

}
