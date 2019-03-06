package com.kdhira.jwtgen.rest;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.kdhira.jwtgen.config.JWTGenConfig;
import com.kdhira.jwtgen.json.Error;
import com.kdhira.jwtgen.json.JWTInfo;
import com.kdhira.jwtgen.json.JWTToken;
import com.kdhira.jwtgen.json.JWTInfo.JWTIssueRequest;
import com.kdhira.jwtgen.json.JWTToken.JWTVerifyRequest;
import com.kdhira.jwtgen.keystore.Keystore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/jwtgen/jwt")
public class JWTGeneratorController {

    @Autowired
    JWTGenConfig config;

    private static final String KEYSTORE = "jwtgen.jks";

    @PostMapping(value = "/issue", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> generateJWT(@RequestBody final JWTIssueRequest request) {

        try {
            String signKey = Optional.ofNullable(request.getSignKey()).orElseThrow(() -> new Exception("signKey required"));
            Long ttl = Optional.ofNullable(request.getTTL()).orElse(86400000L);
            String keystoreName = Optional.ofNullable(request.getKeystore()).orElse(KEYSTORE);

            String jwt = createToken(request.getPayload(), getPrivateKey(keystoreName, request.getPassword(), signKey), ttl);
            JWTToken jwtToken = new JWTToken();
            jwtToken.setJwt(jwt);
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(e.getMessage()));
        }
    }

    @PostMapping(value = "/verify", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> generateJWT(@RequestBody final JWTVerifyRequest request) {

        try {
            String signKey = Optional.ofNullable(request.getSignKey()).orElseThrow(() -> new Exception("signKey required"));
            String keystoreName = Optional.ofNullable(request.getKeystore()).orElse(KEYSTORE);

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(getPublicKey(keystoreName, request.getPassword(), signKey)).parseClaimsJws(request.getJwt());
            if (claimsJws != null) {
                JWTInfo jwtInfo = new JWTInfo();
                jwtInfo.setPayload(buildPayload(claimsJws));
                return ResponseEntity.ok(jwtInfo);
            }
            throw new Exception("Invalid key or password");
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Error(e.getMessage()));
        }
    }



    private Map<String, String> buildPayload(Jws<Claims> claimsJws) {
        Map<String, String> payload = new HashMap<String, String>();
        claimsJws.getBody().entrySet().forEach(e -> payload.put(e.getKey(), (String)e.getValue().toString()));
        return payload;
    }

    private String createToken(Map<String, String> jwtPayload, PrivateKey privateKey, Long ttl) throws Exception {
        Map<String, Object> payload = new HashMap<String, Object>(jwtPayload);
        Long current = System.currentTimeMillis();

        String t;
        return Jwts.builder()
                .setClaims(payload)
                .setIssuedAt((t = jwtPayload.get("iat")) != null ? new Date(Long.valueOf(t)) : new Date(current))
                .setExpiration((t = jwtPayload.get("exp")) != null ? new Date(Long.valueOf(t)) : new Date(current + ttl))
                .setIssuer((t = jwtPayload.get("iss")) != null ? t : "JWTGen")
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    private PrivateKey getPrivateKey(String keystoreName, String password, String keyAlias) throws Exception {
        // return new Keystore(new File(KEYSTORE), password).getPrivateKeys().get(keyAlias);
        return getKeystore(keystoreName, password).getPrivateKeys().get(keyAlias);
    }

    private PublicKey getPublicKey(String keystoreName, String password, String keyAlias) throws Exception {
        return getKeystore(keystoreName, password).getPublicKeys().get(keyAlias);
    }

    private Keystore getKeystore(String keystoreName, String password) throws Exception {
        return new Keystore(new File(config.getKeystoreLocation() + "/" + keystoreName), password);
    }
}
