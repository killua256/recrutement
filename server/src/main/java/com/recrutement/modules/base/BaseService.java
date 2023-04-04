package com.recrutement.modules.base;

import com.google.common.collect.Sets;
import com.recrutement.DTOs.BaseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.client.HttpServerErrorException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RequiredArgsConstructor
public abstract class BaseService<Type extends BaseEntity, TypeDto extends BaseDTO> {

    protected abstract Class<Type> getType();
    protected abstract BaseRepository<Type> getRepository();
    protected abstract BaseMapper<TypeDto, Type> getMapper();

    private NoSuchElementException elementNotFoundHandler(Long id) {
        return new NoSuchElementException("item of type "
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
        return Sets.newHashSet(getMapper()
                .toDtoList(getRepository()
                        .findAll()));
    }

    public TypeDto getOne(long id){
        return getMapper().toDto(getRepository().findById(id).orElseThrow(() -> elementNotFoundHandler(id)));
    }

    public Page<TypeDto> list(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return getMapper().toPageDto(getRepository().findAll(pageable));
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
