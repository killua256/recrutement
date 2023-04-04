package com.recrutement.modules.base;

import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface BaseMapper<D, E> {

    @Named(value = "toEntity")
    E toEntity(D dto);
    @BeanMapping(ignoreByDefault = false)
    @Named(value = "toDto")
    D toDto(E entity);

    @IterableMapping(qualifiedByName = "toEntity")
    List<E> toEntityList(Collection<D> dtoList);

    @IterableMapping(qualifiedByName = "toDto")
    List<D> toDtoList(Collection<E> entityList);

    @IterableMapping(qualifiedByName = "toEntity")
    Set<E> toEntitySet(Collection<D> dtoList);

    @IterableMapping(qualifiedByName = "toDto")
    Set<D> toDtoSet(Collection<E> entityList);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
