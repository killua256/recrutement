package com.recrutement.modules.base;

import org.mapstruct.BeanMapping;
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

    E toEntity(D dto);
    @BeanMapping(ignoreByDefault = false)
    D toDto(E entity);

    List<E> toEntityList(Collection<D> dtoList);

    List<D> toDtoList(Collection<E> entityList);

    Set<E> toEntitySet(Collection<D> dtoList);

    Set<D> toDtoSet(Collection<E> entityList);

    Page<D> toPageDto(Page<E> entityPage);

    @Named("partialUpdate")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget E entity, D dto);
}
