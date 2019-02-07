package com.kdhira.jwtgen.json;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class JWTInfo {

    private Map<String, String> payload;

    /**
     * @return the payload
     */
    public Map<String, String> getPayload() {
        return payload;
    }

    /**
     * @param payload the payload to set
     */
    public void setPayload(Map<String, String> payload) {
        this.payload = payload;
    }

    public static class JWTIssueRequest extends JWTInfo {

        private String signKey;
        private String password;
        private Long ttl;

        /**
         * @return the ttl
         */
        public Long getTTL() {
            return ttl;
        }

        /**
         * @param ttl the ttl to set
         */
        public void setTTL(Long ttl) {
            this.ttl = ttl;
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

    }
}
