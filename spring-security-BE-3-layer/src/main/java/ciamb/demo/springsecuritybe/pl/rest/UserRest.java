package ciamb.demo.springsecuritybe.pl.rest;

import ciamb.demo.springsecuritybe.dal.entity.Role;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Set;
@Data
public class UserRest {
    private Long id;
    @NotBlank private String username;
    @NotBlank private String email;
    @NotBlank private String password;
    private Set<Role> role;
}