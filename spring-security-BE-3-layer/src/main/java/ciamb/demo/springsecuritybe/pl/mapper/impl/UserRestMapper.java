package ciamb.demo.springsecuritybe.pl.mapper.impl;

import ciamb.demo.springsecuritybe.bll.dto.UserDto;
import ciamb.demo.springsecuritybe.pl.mapper.RestMapper;
import ciamb.demo.springsecuritybe.pl.rest.UserRest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "Spring")
public interface UserRestMapper extends RestMapper<UserDto, UserRest> {
}
