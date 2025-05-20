package online.tufactura.api.infrastructure.adapters.client.arca;

import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;

public class LoginCMS {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static byte[] sign(byte[] traBytes, KeyStore keystore, String alias, char[] password) throws Exception {
        PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, password);
        X509Certificate cert = (X509Certificate) keystore.getCertificate(alias);

        Collection<X509Certificate> certList = new ArrayList<>();
        certList.add(cert);

        CMSSignedDataGenerator generator = new CMSSignedDataGenerator();
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(privateKey);
        generator.addSignerInfoGenerator(
                new JcaSignerInfoGeneratorBuilder(new JcaDigestCalculatorProviderBuilder().build())
                        .build(contentSigner, cert));
        generator.addCertificates(new org.bouncycastle.cert.jcajce.JcaCertStore(certList));

        ByteArrayInputStream dataStream = new ByteArrayInputStream(traBytes);
        CMSSignedData signedData = generator.generate(new org.bouncycastle.cms.CMSProcessableByteArray(traBytes), true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(signedData.getEncoded());
        return baos.toByteArray();
    }
}
