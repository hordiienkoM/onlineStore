package com.hordiienko.onlinestore.mapper;


import com.hordiienko.onlinestore.dto.PageableDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Mapper
public interface PageableMapper {
    default Pageable toPageableWithSort(PageableDTO pageableDTO){
        return PageRequest.of(pageableDTO.getPage(), pageableDTO.getSize()).withSort(
                Sort.by(pageableDTO.getSortField())
        );
    }
}
