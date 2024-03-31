package kolesov.maksim.mapping.map.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(
        name = "auth-service",
        url = "${auth-service.url}"
)
public interface AuthService {

    @RequestMapping(method = RequestMethod.GET, value = "/public")
    Response downloadKey();

}
