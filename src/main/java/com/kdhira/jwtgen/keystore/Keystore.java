package com.kdhira.jwtgen.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Keystore {

    private Map<String, PublicKey> publicKeys;
    private Map<String, PrivateKey> privateKeys;

    public Keystore(File keystore, String password) throws KeyStoreException,
            CertificateException, FileNotFoundException, IOException, NoSuchAlgorithmException {
        KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
        store.load(new FileInputStream(keystore), password.toCharArray());

        publicKeys = new HashMap<String, PublicKey>();
        privateKeys = new HashMap<String, PrivateKey>();

        Enumeration<String> aliases = store.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            publicKeys.put(alias, store.getCertificate(alias).getPublicKey());
            try {
                privateKeys.put(alias, (PrivateKey) store.getKey(alias, password.toCharArray()));
            }
            catch (UnrecoverableKeyException | NoSuchAlgorithmException e) {}
        }
    }
    /**
     * @return the publicKeys
     */
    public Map<String, PublicKey> getPublicKeys() {
        return publicKeys;
    }

    /**
     * @return the privateKeys
     */
    public Map<String, PrivateKey> getPrivateKeys() {
        return privateKeys;
    }

    /**
     * @param privateKeys the privateKeys to set
     */
    public void setPrivateKeys(Map<String, PrivateKey> privateKeys) {
        this.privateKeys = privateKeys;
    }

    /**
     * @param publicKeys the publicKeys to set
     */
    public void setPublicKeys(Map<String, PublicKey> publicKeys) {
        this.publicKeys = publicKeys;
    }
}
