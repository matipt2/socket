package com.githubprojects.reactiveSockets.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

@Getter
@Setter
@Builder
public class JwtToken {
    private Map<String, Object> header;
    private Map<Object, Object> payload;
    private static final String HMAC_ALGO = "HmacSHA256";

    public String buildJwt(String secret) throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        ObjectMapper objectMapper = new ObjectMapper();

        header = Map.of("alg", "HS256", "typ", "JWT");
        String headerJson = objectMapper.writeValueAsString(header);
        String payloadJson = objectMapper.writeValueAsString(payload);

        String encodedClaims = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes());
        String encodedPayload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes());
        String unsignedToken = encodedClaims+"."+encodedPayload;

        String signatureBase64 = provideBase64Signature(unsignedToken, secret);

        return unsignedToken + "." + signatureBase64;
    }

    public String provideBase64Signature(String unsignedToken, String secret) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(HMAC_ALGO);
        SecretKeySpec keySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_ALGO);
        mac.init(keySpec);
        byte[] rawHmac = mac.doFinal(unsignedToken.getBytes(StandardCharsets.UTF_8));

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(rawHmac);

    }
}
