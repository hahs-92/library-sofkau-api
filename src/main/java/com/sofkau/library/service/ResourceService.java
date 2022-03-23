package com.sofkau.library.service;

import com.sofkau.library.repository.IResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceService {
    @Autowired
    private IResourceRepository iResourceRepository;

}
