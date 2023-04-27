package ciamb.demo.springsecuritybe.pl.mapper;

import java.util.List;

public interface RestMapper<D, R> {
    R toRest (D dto);
    D toDto (R rest);
    List<R> toRests (List<D> dtos);
}
