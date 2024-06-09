package week13.board.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ErrorDto {
    private HttpStatus httpStatus;
    private String errorMessage;
}