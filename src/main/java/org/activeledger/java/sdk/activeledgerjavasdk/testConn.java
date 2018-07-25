package org.activeledger.java.sdk.activeledgerjavasdk;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class testConn {

	public static void main(String[] args) throws
     NoSuchAlgorithmException, IOException, InvalidKeySpecException
{
 createRsaPublicKey(
 "-----BEGIN PRIVATE KEY-----\r\n" + 
 "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCoUro7O/26m3bt\r\n" + 
 "p2yMjVn/YyKdP9WQ4HgaSFt6MjFCoO935DSskEakELUugkJoeiqpikU0ZGGVkwuB\r\n" + 
 "FEzrjRefYcwhEj+RiO8B+yuCy5TYWMasZqqJ/HTLImaCXnMBmbWyhUhcepLgBmrW\r\n" + 
 "fIsG/PCLflr8EKhULRH/xgsP4ZX88TGCpYTPBOpWi4kM65g3zoXX+qyMVhbWMTu/\r\n" + 
 "EAw8oYs99eLYq7kfDfPdAJapZpEwporg62pz2BR5M6XlbC17apkZOH2RtvKz6vj7\r\n" + 
 "rvjSp1gzJWFsV7jcK9Ek88RwCdpwPN9nHMmlsyJ7nbjBMPC18il/mm74FhZUgipB\r\n" + 
 "x2xXZIjPAgMBAAECggEBAKaiF0kyr5G//DFzUGuoN5MzIf5IRnWBMoTaYu0KqWeL\r\n" + 
 "iOXInjjDL5uWCrd7LqDdaESk5LPkOBLqGjZz0V5TNDKNj5ahyRqbU/lDHKUEQ1sW\r\n" + 
 "L8BWdrfZnAnJCXBAblwEPjL37VXX5nUvBS9G5h9J4hxGP25QU0PZxnrOJP9Y1LIs\r\n" + 
 "uV2VGeZ63QO/blfMyGR/a+eb0LlswCu1Lx+ohtsraMK452n6ZzL34WdGcQlIgX5G\r\n" + 
 "picwXo4NwlTCYLIFNGZjXqw+goVCQG0OejNC9e4hr5h2VOWtYQilccmamfES5XB0\r\n" + 
 "uk1icmSIN6EfQxZmmGSoJD2fUBfuiJqbHDSTMITt3UkCgYEA7n4GeH7H9AFIpMFM\r\n" + 
 "tmoSLwDA8PV6zz8wVnHyrN3wnu/8RoCQnQPG0qn2tLRc2VMos1yl8gbObX5eoyPA\r\n" + 
 "MLCXpbuMu4/CkLLAxRPSpeWXg92075mPxlX7WxsL5nq/AnXCxvEqKUWvw2zACKYV\r\n" + 
 "9ZHu+gKGHiGnFZHIjQzR77g5j+sCgYEAtK4EUbvRSoKvBLOOf5BU8BFwaZYqBxgF\r\n" + 
 "p68jSgpA9QY4zF98Oy6oyndKMrSLqZQLM+B3jzskSPH7l1CIuuMXdiGnjHC12tj9\r\n" + 
 "MSIBYtnW4XP12NQgHxqSy/lnIAIg8vJWCSiQRzIqGEbfiKIj2hz/poTLkGlYaT68\r\n" + 
 "KO3bniMQFa0CgYEA4yvUSg6ptDlM0vL1eYSMEY2sO1n5Q1QzXQRpXlSCEUMTkKyC\r\n" + 
 "E643Qw922iY7kx9tU3xa7bGiDBYlW0yAk2CC8vE1WqvTbzV1/cOmETQjIQ6cfOZP\r\n" + 
 "V2MrbFrU+RPOwi8d+5ZYzLt+cq4yNYrOu/3oHuksi6MOnrMgz4p4ptlaq0UCgYBd\r\n" + 
 "J/O2KH5KSoLui52HC+WfOX6aEgBtU+dmg5hQTmUbcC/Mv9aPtQj2CvdImWoA7wGv\r\n" + 
 "p6Y9RSL6MTmk1LMKmJe6IrP+ZwZ8ZbB1qrDIRMTTzTQOraA6KTtSy65uBGr0mBnf\r\n" + 
 "8vggnS6zAaezDNYrWRQ+yO0j8gzRhrUA7VJ0gGOEqQKBgHos44RTleAEVr+6qGNN\r\n" + 
 "JFxkAfYt9jTxR1Mdc/LhWRRWQCfHzpSk+NK6a8h4cjzafJt3oJpIIHQcN4Y0Q71I\r\n" + 
 "vlz/x84NA7ivjsL+LemhLuJ2sBLo71r3kBjIXZkzEGTp1V9BfgT1WvVjDBhj/tm2\r\n" + 
 "vRhZU+SWv14IIHzzZ49qghgG\r\n" + 
 "-----END PRIVATE KEY-----"
 );
}

static PublicKey createRsaPublicKey(String keystr) throws
     IOException, NoSuchAlgorithmException, InvalidKeySpecException
{
 try (StringReader reader = new StringReader(keystr);
         PemReader pemReader = new PemReader(reader))
 {
	// bytes = Org.BouncyCastle.Pkcs.PrivateKeyInfoFactory.CreatePrivateKeyInfo(keys.Private).ParsePrivateKey().GetDerEncoded();
 }
return null;
}
}
