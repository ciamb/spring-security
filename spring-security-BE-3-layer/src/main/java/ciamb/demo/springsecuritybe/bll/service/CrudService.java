package ciamb.demo.springsecuritybe.bll.service;

import ciamb.demo.springsecuritybe.bll.exception.UserNotFoundException;

import java.util.List;

public interface CrudService<D> {

    D insert(D dto);
    List<D> getAll();
    D getById(Long id) throws UserNotFoundException;
    D update(D dto) throws UserNotFoundException;
    void deleteById(Long id) throws UserNotFoundException;

}
