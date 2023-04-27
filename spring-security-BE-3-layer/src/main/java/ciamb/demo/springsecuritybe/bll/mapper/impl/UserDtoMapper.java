package ciamb.demo.springsecuritybe.bll.mapper.impl;

import ciamb.demo.springsecuritybe.bll.dto.UserDto;
import ciamb.demo.springsecuritybe.bll.mapper.DtoMapper;
import ciamb.demo.springsecuritybe.dal.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserDtoMapper extends DtoMapper<User, UserDto> {
}
