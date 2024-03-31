package kolesov.maksim.mapping.map.service.impl;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import kolesov.maksim.mapping.map.service.JwtService;
import kolesov.maksim.mapping.map.service.KeyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final KeyService keyService;

    @Value("${auth-service.issuer}")
    private String issuer;

    @Override
    public boolean verify(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) keyService.getKey());

            if (Instant.now().isAfter(signedJWT.getJWTClaimsSet().getExpirationTime().toInstant())) {
                return false;
            }

            return signedJWT.verify(verifier);
        } catch (ServiceException | ParseException | JOSEException e) {
            log.error("Failed to verify token", e);
            return false;
        }
    }

    @Override
    public String getSub(String token) {
        if (!verify(token)) {
            return null;
        }

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            log.error("Failed to load subject from token", e);
            return null;
        }
    }

    @Override
    public boolean isAccess(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return "access".equals(signedJWT.getJWTClaimsSet().getClaim("typ"));
        } catch (ParseException e) {
            log.error("Failed to load type from token", e);
            return false;
        }
    }

}
