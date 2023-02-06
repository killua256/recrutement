package com.recrutement.modules.base;

import org.springframework.data.domain.Page;

import java.util.List;

public interface IBaseService<Type, TypeDto> {

    TypeDto save(TypeDto dto);
    TypeDto update(long id, TypeDto dto);
    List<TypeDto> getAll();
    TypeDto getOne(long id);
    Page<TypeDto> list(int pageNumber, int pageSize);
    void remove(Long id);
}
