package io.fiscalito.api.infrastructure.adapters.client.arca;


import org.bouncycastle.util.io.pem.PemReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Reader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;

public class PemUtils {

    private PemUtils() {}

    public static PrivateKey loadPrivateKey(String filePath) throws Exception {
        try (Reader reader = new FileReader(filePath);
             PemReader pemReader = new PemReader(reader)) {

            byte[] content = pemReader.readPemObject().getContent();
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(keySpec);
        }
    }

    public static X509Certificate loadCertificate(String filePath) throws Exception {
        try (InputStream is = new FileInputStream(filePath)) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            return (X509Certificate) cf.generateCertificate(is);
        }
    }
}
