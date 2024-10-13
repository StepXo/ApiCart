package com.emazon.ApiCart.Infrastructure.ExceptionHandler;

import com.emazon.ApiCart.Domain.Exeptions.ItemNotFoundException;
import com.emazon.ApiCart.Domain.Exeptions.QuantityIsNotEnough;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
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
            String errorMessage = errorBody.path(InfraConstants.MESSAGE).asText();
            if (errorMessage.contains(InfraConstants.ITEM_NOT_FOUND)) {
                return new ItemNotFoundException();
            } else if (errorMessage.contains(InfraConstants.QUANTITY_IS_NOT_ENOUGH)) {
                System.out.println(errorMessage);
                return new QuantityIsNotEnough(errorMessage);
            } else {
                return new BadRequestException();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
