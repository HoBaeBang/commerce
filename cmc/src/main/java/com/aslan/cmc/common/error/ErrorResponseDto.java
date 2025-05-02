package com.aslan.cmc.common.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "에러 응답")
public class ErrorResponseDto {
    private String errorCode;
    private String errorMessage;
    private String errorData;
}
