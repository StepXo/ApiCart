package com.emazon.ApiCart.Infrastructure.ExeptionHanlder;

import com.emazon.ApiCart.Domain.Exeptions.ItemNotFoundException;
import com.emazon.ApiCart.Domain.Exeptions.QuantityIsNotEnough;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class ControllerErrorDecoder implements ErrorDecoder {
    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            JsonNode errorBody = objectMapper.readTree(response.body().asInputStream());
            String errorMessage = errorBody.path("Message").asText();
            if (errorMessage.contains("No item was found with that name")) {
                return new ItemNotFoundException();
            } else if (errorMessage.contains("The quantity is not available")) {
                return new QuantityIsNotEnough();
            } else {
                return new BadRequestException();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
