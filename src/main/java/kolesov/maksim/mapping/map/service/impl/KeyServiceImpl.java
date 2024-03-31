package kolesov.maksim.mapping.map.service.impl;

import feign.Response;
import kolesov.maksim.mapping.map.client.AuthService;
import kolesov.maksim.mapping.map.exception.ServiceRuntimeException;
import kolesov.maksim.mapping.map.service.KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeyServiceImpl implements KeyService {

    private final AuthService authService;

    private static PublicKey publicKey = null;

    @Override
    public PublicKey getKey() {
        if (publicKey != null) {
            return publicKey;
        }

        try {
            return updateKey(authService);
        } catch (Exception e) {
            log.error("Failed to load public key", e);
            throw new ServiceRuntimeException(e);
        }
    }

    private static synchronized PublicKey updateKey(AuthService authService) throws Exception {
        try (Response response = authService.downloadKey()) {
            Response.Body body = response.body();
            InputStream inputStream = body.asInputStream();
            X509EncodedKeySpec spec = new X509EncodedKeySpec(inputStream.readAllBytes());
            KeyFactory factory = KeyFactory.getInstance("RSA");

            publicKey = factory.generatePublic(spec);
            return publicKey;
        }
    }

}
