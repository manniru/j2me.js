package com.sun.midp.publickeystore;

import gnu.testlet.TestHarness;
import gnu.testlet.Testlet;

import java.util.*;
import java.io.IOException;
import javax.microedition.pki.CertificateException;
import com.sun.midp.pki.*;
import com.sun.midp.security.*;
import com.sun.midp.main.Configuration;

public class TestWebPublicKeyStore implements Testlet {
    public int getExpectedPass() { return 3; }
    public int getExpectedFail() { return 0; }
    public int getExpectedKnownFail() { return 0; }
    static final byte[] GoDaddySecureCertificationAuthorityDER = hexToBytes("308204de308203c6a00302010202020301300d06092a864886f70d01010505003063310b30090603550406130255533121301f060355040a131854686520476f2044616464792047726f75702c20496e632e3131302f060355040b1328476f20446164647920436c61737320322043657274696669636174696f6e20417574686f72697479301e170d3036313131363031353433375a170d3236313131363031353433375a3081ca310b30090603550406130255533110300e060355040813074172697a6f6e61311330110603550407130a53636f74747364616c65311a3018060355040a1311476f44616464792e636f6d2c20496e632e31333031060355040b132a687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f72793130302e06035504031327476f204461646479205365637572652043657274696669636174696f6e20417574686f726974793111300f06035504051308303739363932383730820122300d06092a864886f70d01010105000382010f003082010a0282010100c42dd5158c9c264cec3235eb5fb859015aa66181593b7063abe3dc3dc72ab8c933d379e43aed3c3023848eb33014b6b287c33d9554049edf99dd0b251e21de65297e35a8a954ebf6f73239d4265595adeffbfe5886d79ef4008d8c2a0cbd4204cea73f04f6ee80f2aaef52a16966dabe1aad5dda2c66ea1a6bbbe51a514a002f48c79875d8b929c8eef8666d0a9cb3f3fc787ca2f8a3f2b5c3f3b97a91c1a7e6252e9ca8ed12656e6af6124453703095c39c2b582b3d08744af2be51b0bf87d04c27586bb535c59daf1731f80b8feead813605890898cf3aaf2587c049eaa7fd67f7458e97cc1439e23685b57e1a37fd16f671119a743016fe1394a33f840d4f0203010001a38201323082012e301d0603551d0e04160414fdac6132936c45d6e2ee855f9abae7769968cce7301f0603551d23041830168014d2c4b0d291d44c1171b361cb3da1fedda86ad4e330120603551d130101ff040830060101ff020100303306082b0601050507010104273025302306082b060105050730018617687474703a2f2f6f6373702e676f64616464792e636f6d30460603551d1f043f303d303ba039a0378635687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f72792f6764726f6f742e63726c304b0603551d200444304230400604551d20003038303606082b06010505070201162a687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f7279300e0603551d0f0101ff040403020106300d06092a864886f70d01010505000382010100d286c0ecbdf9a1b667ee660ba2063a04508e1572ac4a749553cb37cb4449ef07906b33d996f09456a51330053c8532217bc9c70aa824a490de46d32523140367c210d66f0f5d7b7acc9fc5582ac1c49e21a85af3aca446f39ee463cb2f90a4292901d9722c29df370127bc4fee68d3218fc0b3e4f509edd210aa53b4bef0cc590bd63b961c952449dfceecfda7489114450e3a366fda45b345a241c9d4d7444e3eb97476d5a213552cc687a3b599ac0684877f7506fcbf144c0ecc6ec4df3db71271f4e8f15140222849e01d4b87a834cc06a2dd125ad186366403356f6f776eebf28550985eab0353ad9123631f169ccdb9b205633ae1f4681b1705359553ee");

    static final byte[] GoDaddyDotComDER = hexToBytes("308205d6308204bea00302010202074afcc0e2e233aa300d06092a864886f70d01010505003081ca310b30090603550406130255533110300e060355040813074172697a6f6e61311330110603550407130a53636f74747364616c65311a3018060355040a1311476f44616464792e636f6d2c20496e632e31333031060355040b132a687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f72793130302e06035504031327476f204461646479205365637572652043657274696669636174696f6e20417574686f726974793111300f060355040513083037393639323837301e170d3134303432383134343633345a170d3135313230363137333732345a3081cd31133011060b2b0601040182373c0201031302555331183016060b2b0601040182373c02010213074172697a6f6e61311d301b060355040f131450726976617465204f7267616e697a6174696f6e311430120603550405130b522d313732343733332d36310b30090603550406130255533110300e060355040813074172697a6f6e61311330110603550407130a53636f74747364616c6531193017060355040a1310476f44616464792e636f6d2c204c4c43311830160603550403130f7777772e676f64616464792e636f6d30820122300d06092a864886f70d01010105000382010f003082010a0282010100c5b14c056beb6b77ab00ff5e9352ef6c7c4564e5cf0a14ac038ab4635dcf4fa9b0cbb62a3222722759af530e17b4a0e8aa9f28cf2d828f12f020fb6c7fc8d945943fbaba873f9892e02510f71312775cf26ccc0696d272d3d774e7e4a580d399f13a22b34ec12fe14ad74e220b3f155b360b59a61f380046bcaa65ca6426c2e8a5f50f5f8f50fce0a1cee4b9cedecc8742d7e107bb57388485d9bfc08a87bd4ab815ce753be4618c86e20cc01c53ba36e02a093c0ddd958c4abda53468320b12fb41437dc3fbf3aa1737d56b7eb9012611cdcf51ad788ffda2198ffac5722ba15b2994d533ff51daa757784bd5853f94a0f185da5e9e49cf6b13d0cf801fd9df0203010001a38201ba308201b6300f0603551d130101ff04053003010100301d0603551d250416301406082b0601050507030106082b06010505070302300e0603551d0f0101ff0404030205a030330603551d1f042c302a3028a026a0248622687474703a2f2f63726c2e676f64616464792e636f6d2f676473332d39322e63726c30530603551d20044c304a3048060b6086480186fd6d010717033039303706082b06010505070201162b687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f72792f30818006082b0601050507010104743072302406082b060105050730018618687474703a2f2f6f6373702e676f64616464792e636f6d2f304a06082b06010505073002863e687474703a2f2f6365727469666963617465732e676f64616464792e636f6d2f7265706f7369746f72792f67645f696e7465726d6564696174652e637274301f0603551d23041830168014fdac6132936c45d6e2ee855f9abae7769968cce730270603551d110420301e820f7777772e676f64616464792e636f6d820b676f64616464792e636f6d301d0603551d0e0416041447b5046b1a8403da6902bb833995879edeb63779300d06092a864886f70d01010505000382010100bb60237e7a48f6d1c86011b1cea9f2fd3eec7215e1c105e4f261ebe11cdbdb8a82d5faa3f1a622931ef1ee81fa8101e096b2f6582ef84b534fc3d00790eefc955d3f53e4122c8c22b212f8512c51a41a6bfe0a54f9feddab539764f359f3eae21640b72166038705cb7d26864763f036699a36fcca5d1c4fdcf5b370561d469c792fc38ae383efbb3fb0382a390defe1d248dc1c9d82e4feb7413852e899d3582f0a343ff0bf83025a5510d352914d5f43caf7e05b5e93640ec2d71f912c74fdbed6584e175257c0ff27f22f89a17760766dcb76e9a553601a7b7653a5810e7fe0fff429d9df54c4dc11e72a55e79914dd0421dcb303e03e908f94093bde49bb");

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                                  + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public void test(TestHarness th) {
        WebPublicKeyStore cs = WebPublicKeyStore.getTrustedKeyStore();
        th.check(cs.numberOfKeys() >= 1);

        PublicKeyInfo keyInfo = cs.getKey(0);
        th.check(keyInfo.getOwner().length() > 0);
        keyInfo = cs.getKey(1);
        th.check(keyInfo.getOwner().length() > 0);

        X509Certificate[] certificates = cs.getCertificates("C=US;O=The Go Daddy Group, Inc.;OU=Go Daddy Class 2 Certification Authority");
        X509Certificate rootCert = certificates[0];

        try {
            rootCert.verify(rootCert.getPublicKey());
        } catch (CertificateException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
        }

        X509Certificate middleCert, endCert;
        try {
            middleCert = X509Certificate.generateCertificate(GoDaddySecureCertificationAuthorityDER, 0, GoDaddySecureCertificationAuthorityDER.length);
            endCert = X509Certificate.generateCertificate(GoDaddyDotComDER, 0, GoDaddyDotComDER.length);
        } catch (IOException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
            return;
        }

        try {
            endCert.verify(middleCert.getPublicKey());
        } catch (CertificateException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
        }

        try {
            middleCert.verify(rootCert.getPublicKey());
        } catch (CertificateException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
        }

        try {
            Vector certs = new Vector();
            certs.addElement(endCert);
            certs.addElement(middleCert);
            X509Certificate.verifyChain(certs, -1, -1, cs);
        } catch (CertificateException e) {
            th.fail("Unexpected exception: " + e);
            e.printStackTrace();
        }

        try {
            middleCert.verify(endCert.getPublicKey());
            th.fail("Verification shouldn't succeed");
        } catch (CertificateException e) {
        }

        try {
            rootCert.verify(middleCert.getPublicKey());
            th.fail("Verification shouldn't succeed");
        } catch (CertificateException e) {
        }

        try {
            rootCert.verify(endCert.getPublicKey());
            th.fail("Verification shouldn't succeed");
        } catch (CertificateException e) {
        }

        try {
            endCert.verify(rootCert.getPublicKey());
            th.fail("Verification shouldn't succeed");
        } catch (CertificateException e) {
        }
    }
}
