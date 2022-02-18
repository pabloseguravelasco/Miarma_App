package com.salesianostriana.dam.miarma.errors;

import com.salesianostriana.dam.miarma.errors.exception.ListEntityNotFoundException;
import com.salesianostriana.dam.miarma.errors.exception.SingleEntityNotFoundException;
import com.salesianostriana.dam.miarma.errors.models.ApiError;
import com.salesianostriana.dam.miarma.errors.models.ApiSubError;
import com.salesianostriana.dam.miarma.errors.models.ApiValidationSubError;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return buildApiError(ex, request);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {


        List<ApiSubError> subErrorList = new ArrayList<>();

        ex.getAllErrors().forEach(error -> {

            if (error instanceof FieldError) {
                FieldError fieldError = (FieldError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(fieldError.getObjectName())
                                .campo(fieldError.getField())
                                .valorRechazado(fieldError.getRejectedValue())
                                .mensaje(fieldError.getDefaultMessage())
                                .build()
                );
            }
            else
            {
                ObjectError objectError = (ObjectError) error;

                subErrorList.add(
                        ApiValidationSubError.builder()
                                .objeto(objectError.getObjectName())
                                .mensaje(objectError.getDefaultMessage())
                                .build()
                );
            }


        });



        return buildApiErrorWithSubError(HttpStatus.BAD_REQUEST, "Errores varios en la validación",
                request, subErrorList.isEmpty() ? null : subErrorList
        );
    }

    private ResponseEntity<Object> buildApiErrorWithSubError(HttpStatus estado, String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(estado)
                .body(new ApiError(estado, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildApiError(ex, request);
    }


    private ResponseEntity<Object> buildApiError(Exception ex, WebRequest request) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI()));

    }

    private ResponseEntity<Object> buildApiError(String mensaje, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(HttpStatus.NOT_FOUND, mensaje, ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }

    private ResponseEntity<?> buildApiError(HttpStatus estado, ConstraintViolationException unoError, WebRequest request, List<ApiSubError> subErrores) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError( estado,unoError.getMessage(), ((ServletWebRequest) request).getRequest().getRequestURI(), subErrores));

    }
    @ExceptionHandler(ListEntityNotFoundException.class)
    public ResponseEntity<Object> handleListEntityNotFoundException(ListEntityNotFoundException listaError){

        ApiError apiError = ApiError.builder()
                .estado(HttpStatus.NOT_FOUND)
                .mensaje(listaError.getMessage())
                .codigo(HttpStatus.NOT_FOUND.value())
                .ruta("EN TÉRMINOS DE RUTA, NO TENEMOS RUTA ¯|_( ͡❛ ͜ʖ ͡❛)_|¯")
                .build();

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(SingleEntityNotFoundException.class)
    public ResponseEntity<Object> handleSingleEntityNotFoundException(SingleEntityNotFoundException unoError){

        ApiError apiError = ApiError.builder()
                .estado(HttpStatus.NOT_FOUND)
                .mensaje(unoError.getMessage())
                .codigo(HttpStatus.NOT_FOUND.value())
                .ruta("EN TÉRMINOS DE RUTA, NO TENEMOS RUTA ¯|_( ͡❛ ͜ʖ ͡❛)_|¯")
                .build();

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);

    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException unoError, WebRequest request){

        return buildApiError(HttpStatus.BAD_REQUEST,unoError,request,
                unoError.getConstraintViolations().stream().map(error ->
                        ApiValidationSubError.builder()
                                .objeto(error.getRootBeanClass().getSimpleName())
                                .campo(((PathImpl)error.getPropertyPath()).getLeafNode().asString())
                                .valorRechazado(error.getInvalidValue())
                                .mensaje(error.getMessage())
                                .build()).collect(Collectors.toList())
        );

    }


}