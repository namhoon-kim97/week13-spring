package week13.board.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;
import week13.board.dto.ApiResponse;
import week13.board.dto.ErrorDto;
import week13.board.exception.CustomException;

import java.io.IOException;

public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException ex) {
            setErrorResponse(HttpStatus.BAD_REQUEST, response, ex);
        }
    }

    public void setErrorResponse(HttpStatus status, HttpServletResponse response, CustomException ex) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json; charset=UTF-8");

        ApiResponse<ErrorDto> apiResponse = new ApiResponse<>(status.value(), "토큰이 유효하지 않습니다.", new ErrorDto(status, "토큰이 유효하지 않습니다."));

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
