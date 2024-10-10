package com.emazon.ApiCart.Infrastructure.Adapters.Feign;



import com.emazon.ApiCart.Application.Response.UserResponse;
import com.emazon.ApiCart.Infrastructure.Utils.InfraConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientProperties;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = InfraConstants.USER_API, url = "${user.api.url}",configuration = FeignClientProperties.FeignClientConfiguration.class)
public interface IUserFeign {

    @GetMapping(value = InfraConstants.USER_PATH,consumes = {MediaType.APPLICATION_JSON_VALUE},produces = {MediaType.APPLICATION_JSON_VALUE})
    UserResponse getUserById(@PathVariable String id);

}
