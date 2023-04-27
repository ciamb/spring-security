package ciamb.demo.springsecuritybe.bll.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor public class UserNotFoundException extends Exception{
    private String message;

}
