package com.recrutement.modules.base;

import com.google.common.collect.Sets;
import com.recrutement.DTOs.BaseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

public abstract class BaseService<Type extends BaseEntity, TypeDto extends BaseDTO> {

    private Class<Type> TypeClass;
    protected abstract BaseRepository<Type> getRepository();
    protected abstract BaseMapper<TypeDto, Type> getMapper();

    private NullPointerException elementNotFoundHandler(Long id) {
        return new NullPointerException("item of type "
                + TypeClass.getName() +" with id "
                + id +" not found");
    }

    private TypeDto update(Type type,TypeDto dto){
        getMapper().partialUpdate(type, dto);
        type = getRepository().save(type);
        return getMapper().toDto(type);
    }

    protected TypeDto save(TypeDto dto) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Type type = getRepository().findById(dto.getId()).orElse(TypeClass.getDeclaredConstructor().newInstance());
        return update(type, dto);
    }

    protected TypeDto update(long id, TypeDto dto){
        Type type = getRepository().findById(dto.getId()).orElseThrow(()->
                elementNotFoundHandler(dto.getId()));
        return update(type, dto);
    }

    protected List<TypeDto> getAll(){
        return getMapper().toDtoList(getRepository().findAll());
    }

    protected Set<TypeDto> getAllDistinct(){
        return Sets.newHashSet(getMapper()
                .toDtoList(getRepository()
                        .findAll()));
    }

    protected TypeDto getOne(long id){
        return getMapper().toDto(getRepository().findById(id).orElseThrow(() -> elementNotFoundHandler(id)));
    }

    protected Page<TypeDto> list(int pageNumber, int pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return getMapper().toPageDto(getRepository().findAll(pageable));
    }

    protected void delete(Long id){
        getRepository().deleteById(id);
    }

    protected void softDelete(Long id) {
        getRepository().findById(id)
                .orElseThrow(() -> elementNotFoundHandler(id));

        getRepository().deleteById(id);
    }
}
