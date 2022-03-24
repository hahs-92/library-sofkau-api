package com.sofkau.library.mapper;

import com.sofkau.library.dto.ResourceDto;
import com.sofkau.library.model.Resource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ResourceMapper {

    public Resource fromDTO(ResourceDto dto) {
        Resource resource = new Resource();
        resource.setId(dto.getId());
        resource.setTitle(dto.getTitle());
        resource.setDescription(dto.getDescription());
        resource.setType(dto.getType());
        resource.setTheme(dto.getTheme());

        return resource;
    }

    public ResourceDto fromCollection(Resource resource) {
        ResourceDto resourceDto = new ResourceDto();
        resourceDto.setId(resource.getId());
        resourceDto.setTitle(resource.getTitle());
        resourceDto.setDescription(resource.getDescription());
        resourceDto.setType(resource.getType());
        resourceDto.setTheme(resource.getTheme());

        return resourceDto;
    }


    public List<ResourceDto> fromCollectionList(List<Resource> list) {
        if(list == null) {
            return  null;
        }

        List<ResourceDto> resourceDtoList = new ArrayList<>(list.size());
        Iterator listTracks = list.iterator();

        while(listTracks.hasNext()) {
            Resource resource = (Resource) listTracks.next();
            resourceDtoList.add(fromCollection(resource));
        }

        return resourceDtoList;
    }
}
