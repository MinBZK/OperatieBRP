/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;

/**
 * Handler, die verantwoordelijk is voor het aanmaken van object sleutels. LET
 * OP: De encryptie code is zoveel mogelijk een kopie van de service uit de Java
 * code! (met zoveel mogelijk onnodige delen gestript) Als hier changes nodig
 * zijn, eerst in de Java code doorvoeren en dan pas hier overnemen! NB: Er zijn
 * dus ook geen tests voor deze code, die zitten al aan de Java kant.
 */
public class ObjectSleutelHandler
{

    private static final String CIPHER_ALGORITME = "DES/ECB/PKCS5Padding";
    private static final String ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD = "BRPTango";
    private static final SecretKeySpec secretKeySpec;

    static {
        try {
            secretKeySpec = new SecretKeySpec(ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD.getBytes("UTF-8"), "DES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    };

    public static String genereerObjectSleutelString(final Long persoonId, final int partijCode, long tijdstipVanUitgifte)

    {
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeInt(persoonId.intValue());
            oos.writeLong(tijdstipVanUitgifte);
            oos.writeInt(partijCode);
            oos.close();

            return encrypt(bos.toByteArray());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String encrypt(final byte[] objectSleutelBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
            UnsupportedEncodingException
    {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] rawBytes = cipher.doFinal(objectSleutelBytes);
        return new String(Base64.encodeBase64(rawBytes, false), "UTF-8");
    }
}
