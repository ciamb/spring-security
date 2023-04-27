package ciamb.demo.springsecuritybe.bll.mapper;

import java.util.List;

public interface DtoMapper<E, D> {
    D toDto(E entity);
    E toEntity(D dto);
    List<D> toDtos(List<E> entities);
}
