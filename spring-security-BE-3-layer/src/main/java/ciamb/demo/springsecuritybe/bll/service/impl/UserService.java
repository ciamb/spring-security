package ciamb.demo.springsecuritybe.bll.service.impl;

import ciamb.demo.springsecuritybe.bll.dto.UserDto;
import ciamb.demo.springsecuritybe.bll.exception.UserNotFoundException;
import ciamb.demo.springsecuritybe.bll.mapper.impl.UserDtoMapper;
import ciamb.demo.springsecuritybe.bll.service.CrudService;
import ciamb.demo.springsecuritybe.dal.entity.User;
import ciamb.demo.springsecuritybe.dal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<UserDto> {

    private final UserRepository userRepository;
    private final UserDtoMapper userMapper;

    @Override
    public UserDto insert(UserDto dto) {
        return userMapper.toDto(
                userRepository.save(
                        userMapper.toEntity(dto))
        );
    }

    @Override
    public List<UserDto> getAll() {
        return userMapper.toDtos(
                userRepository.findAll()
        );
    }

    @Override
    public UserDto getById(Long id) throws UserNotFoundException {
        return userMapper.toDto(
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new UserNotFoundException("Utente con id " + id + " non trovato."))
        );
    }

    @Override
    public UserDto update(UserDto dto) throws UserNotFoundException {
        if (userRepository.existsById(dto.getId())) {
            User user =
                    userRepository.save(userMapper.toEntity(dto));

            return userMapper.toDto(user);
        } else throw new UserNotFoundException("Utente con id " + dto.getId() + " non trovato.");
    }

    @Override
    public void deleteById(Long id) throws UserNotFoundException{
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else throw new UserNotFoundException("Utente con id " + id + " non trovato.");
    }
}
