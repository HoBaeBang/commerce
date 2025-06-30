package com.aslan.cmc.products.exception;

import com.aslan.cmc.common.error.ErrorResponseDto;
import com.aslan.cmc.orders.exception.OrderNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleProductNotFoundException(ProductNotFoundException e) {
        log.error("상품을 찾을수 없음: {}", e.getMessage());

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("PRODUCT_NOT_FOUND")
                .errorMessage("해당 ID의 상품을 찾을 수 없습니다.")
                .errorData("product_id : " + e.getProductId().toString())
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleOrderNotFoundException(OrderNotFoundException e) {
        log.error("주문을 찾을수 없음: {}", e.getMessage());

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("ORDER_NOT_FOUND")
                .errorMessage("해당 ID의 주문을 찾을 수 없습니다.")
                .errorData("order_id : " + e.getOrderId())
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("잘못된 요청 파라미터: {}", e.getMessage());

        String errorCode = "INVALID_PARAMETER";
        String errorMessage = e.getMessage();
        
        if (e.getMessage().contains("Keyword length")) {
            errorCode = "INVALID_KEYWORD_LENGTH";
            errorMessage = "검색 키워드는 255자를 초과할 수 없습니다.";
        } else if (e.getMessage().contains("최소 가격은 최대 가격보다")) {
            errorCode = "INVALID_PRICE_RANGE";
            errorMessage = "최소 가격은 최대 가격보다 클 수 없습니다.";
        } else if (e.getMessage().contains("상품을 찾을 수 없습니다")) {
            errorCode = "PRODUCT_NOT_FOUND";
            errorMessage = "요청한 상품을 찾을 수 없습니다.";
        } else if (e.getMessage().contains("재고가 부족합니다")) {
            errorCode = "INSUFFICIENT_STOCK";
            errorMessage = "상품의 재고가 부족합니다.";
        }

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .errorData("parameter")
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalStateException(IllegalStateException e) {
        log.error("잘못된 상태: {}", e.getMessage());

        String errorCode = "INVALID_STATE";
        String errorMessage = e.getMessage();
        
        if (e.getMessage().contains("취소할 수 없는 주문 상태")) {
            errorCode = "INVALID_ORDER_STATUS";
            errorMessage = "현재 주문 상태에서는 취소할 수 없습니다.";
        }

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .errorData("order_status")
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        log.error("유효성 검사 실패: {}", e.getMessage());

        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("유효성 검사에 실패했습니다.");

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("VALIDATION_ERROR")
                .errorMessage(errorMessage)
                .errorData("validation")
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        log.error("서버 내부 오류: {}", e.getMessage(), e);

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errorCode("SERVER_ERROR")
                .errorMessage("서버 내부 오류가 발생했습니다. 잠시 후 다시 시도해주세요.")
                .errorData("")
                .build();
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
