# JWTGen

Simple RESTful application to issue and verify RSA-signed JWTs

## Usage

The keystore `jwtgen.jks` in the current directory will be used.

### POST `/jwt/issue`

Headers:
- Accepts: application/json
- Content-type: application/json

Request body example:
```json
{
    "payload" : {
        "sub": "Kevin.Hira",
        "iss": "JWTGen"
    },
    "signKey": "mykey",
    "password": "keystorepassword",
    "ttl": 86400000
}
```

Response body example:
```json
{
    "jwt": "<jwt token>"
}
```

### POST `/jwt/verify`

Headers:
- Accepts: application/json
- Content-type: application/json

Request body example:
```json
{
    "jwt": "<jwt token>",
    "signKey": "mykey",
    "password": "keystorepassword"
}
```

Response body example:
```json
{
    "payload": {
        "sub": "Kevin.Hira",
        "iss": "JWTGen",
        "exp": "1549654292",
        "iat": "1549567892"
    }
}
```

## Notes

Currenly not defensive/descriptive about errors/bad requests etc. To do for future
