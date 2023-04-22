package com.cydeo.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper<D,E> {

    private final ModelMapper modelMapper;
    private final Class<D> dtoClass;
    private final Class<E> entityClass;


    public Mapper(ModelMapper modelMapper, Class<D> dtoClass, Class<E> entityClass) {
        this.modelMapper = modelMapper;
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    public E convertToEntity(D dto){
        return modelMapper.map(dto,entityClass);
    }

    public D convertToDto(E entity){
        return modelMapper.map(entity, dtoClass);
    }

}
