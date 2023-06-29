package htl.stp.at.userverwaltung.domain.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
@Controller
public class UserRestAdvice {

    @ExceptionHandler(NoSuchUserException.class)
    ApiError handleStudentNotFound(NoSuchUserException ex) {
        return new ApiError(ex.getMessage());
    }
}
