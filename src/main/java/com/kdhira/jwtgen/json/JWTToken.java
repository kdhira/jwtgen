package com.kdhira.jwtgen.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class JWTToken {

    private String jwt;

    /**
     * @return the jwt
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * @param jwt the jwt to set
     */
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public static class JWTVerifyRequest extends JWTToken {

        private String keystore;
        private String signKey;
        private String password;

        /**
         * @return the keystore
         */
        public String getKeystore() {
            return keystore;
        }

        /**
         * @param keystore the keystore to set
         */
        public void setKeystore(String keystore) {
            this.keystore = keystore;
        }

        /**
         * @return the password
         */
        public String getPassword() {
            return password;
        }

        /**
         * @param password the password to set
         */
        public void setPassword(String password) {
            this.password = password;
        }

        /**
         * @return the signKey
         */
        public String getSignKey() {
            return signKey;
        }

        /**
         * @param signKey the signKey to set
         */
        public void setSignKey(String signKey) {
            this.signKey = signKey;
        }

    }

}
