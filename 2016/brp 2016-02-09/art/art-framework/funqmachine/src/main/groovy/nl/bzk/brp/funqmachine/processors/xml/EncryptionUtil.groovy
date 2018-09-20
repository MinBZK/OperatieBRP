package nl.bzk.brp.funqmachine.processors.xml

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64

class EncryptionUtil {
    private final String CIPHER_ALGORITME = 'DES/ECB/PKCS5Padding'
    private final String ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD = 'BRPTango'
    private SecretKeySpec secretKeySpec

    EncryptionUtil() {
        secretKeySpec = new SecretKeySpec(ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD.getBytes('UTF-8'), 'DES')
    }

    public String genereerObjectSleutelString(final int persoonId, final int partijCode, long tijdstipVanUitgifte) {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream()

        ObjectOutputStream oos = new ObjectOutputStream(bos)
        oos.with {
            writeInt(persoonId)
            writeLong(tijdstipVanUitgifte)
            writeInt(partijCode)
            close()
        }

        return encrypt(bos.toByteArray())
    }

    private String encrypt(final byte[] objectSleutelBytes) {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITME)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        byte[] rawBytes = cipher.doFinal(objectSleutelBytes)
        return new String(Base64.encodeBase64(rawBytes, false), 'UTF-8')
    }
}
