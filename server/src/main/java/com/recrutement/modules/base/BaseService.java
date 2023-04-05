package com.recrutement.modules.base;

import com.querydsl.core.types.Predicate;
import com.recrutement.dtos.BaseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public abstract class BaseService<Type extends BaseEntity, TypeDto extends BaseDTO, TypeFullDto extends BaseDTO> {

    protected abstract Class<Type> getType();
    protected abstract BaseRepository<Type> getRepository();
    protected abstract BaseMapper<TypeDto, Type> getMapper();
    protected abstract BaseMapper<TypeFullDto, Type> getFullMapper();

    private EntityNotFoundException elementNotFoundHandler(Long id) {
        return new EntityNotFoundException("item of type "
                + getType().getName() +" with id "
                + id +" not found");
    }

    private TypeDto update(Type type,TypeDto dto){
        getMapper().partialUpdate(type, dto);
        type = getRepository().save(type);
        return getMapper().toDto(type);
    }

    public TypeDto save(TypeDto dto) {
        try{
            Type type = getRepository().findById(dto.getId()).orElse(getType().getDeclaredConstructor().newInstance());
            return update(type, dto);
        }catch(NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public TypeDto update(TypeDto dto){
        Type type = getRepository().findById(dto.getId()).orElseThrow(()->
                elementNotFoundHandler(dto.getId()));
        return update(type, dto);
    }


    public List<TypeDto> getAll(){
        return getMapper().toDtoList(getRepository().findAll());
    }

    public Set<TypeDto> getAllDistinct(){
        return new HashSet<>(getMapper()
                .toDtoList(getRepository()
                        .findAll()));
    }

    public Set<TypeDto> getAllDistinct(Predicate where) {
        Iterator<Type> entitiesIterator = getRepository().findAll(where).iterator();
        List<Type> entitiesList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(entitiesIterator, Spliterator.ORDERED), true)
                .collect(Collectors.toList());
        return new HashSet<>(getMapper().toDtoList(entitiesList));
    }

    public TypeDto getOne(long id){
        return getMapper().toDto(getRepository().findById(id).orElseThrow(() -> elementNotFoundHandler(id)));
    }

    public Page<TypeDto> list(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return getRepository().findAll(pageable).map(item -> getMapper().toDto(item));
    }

    public void delete(Long id){
        getRepository().deleteById(id);
    }

    public void softDelete(Long id) {
        getRepository().findById(id)
                .orElseThrow(() -> elementNotFoundHandler(id));

        getRepository().deleteById(id);
    }

    public boolean exists(long id){
        return getRepository().existsById(id);
    }
}
