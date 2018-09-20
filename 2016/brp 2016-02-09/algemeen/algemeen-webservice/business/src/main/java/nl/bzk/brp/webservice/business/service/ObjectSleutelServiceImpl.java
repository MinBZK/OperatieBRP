/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Service die een technische sleutel vertaalt naar een database id.
 */
@Service
public class ObjectSleutelServiceImpl implements ObjectSleutelService {

    private static final Logger LOGGER                               = LoggerFactory
                                                                             .getLogger(ObjectSleutelServiceImpl.class);

    private static final String CIPHER_ALGORITME                     = "DES/ECB/PKCS5Padding";
    private static final String ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD = "BRPTango";
    private static final long   MAX_GELDIGHEID_OBJECTSLEUTEL         = 24 * 60 * 60 * 1000;
    private SecretKeySpec       secretKeySpec;

    /**
     * Constructor voor de objectsleutel-service.
     */
    public ObjectSleutelServiceImpl() {
        try {
            secretKeySpec = new SecretKeySpec(ENCRYPT_DECRYPT_OBJECTSLEUTEL_PASSWD.getBytes("UTF-8"), "DES");
        } catch (final UnsupportedEncodingException e) {
            LOGGER.error("Kan encoding UTF-8 niet lezen van sleutel", e);
        }
    }

    @Override
    public String genereerObjectSleutelString(final int persoonId, final int partijCode) {
        final Long tijdstipVanUitgifte = new Date().getTime();
        final ObjectSleutel objectSleutel = new ObjectSleutel(persoonId, tijdstipVanUitgifte, partijCode);
        final byte[] bytes;
        try {
            bytes = objectSleutel.serialize();
            return encrypt(bytes);
        } catch (IOException | IllegalBlockSizeException | InvalidKeyException | BadPaddingException
                | NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            throw new IllegalStateException("Generatie ObjectSleutel mislukt.", e);
        }
    }

    @Override
    public Integer bepaalPersoonId(final String objectSleutelString, final int partijCode) throws OngeldigeObjectSleutelExceptie {
        final byte[] decryptedObjectSleutelBytes;
        try {
            decryptedObjectSleutelBytes = decrypt(objectSleutelString);
            final ObjectSleutel os = ObjectSleutel.deserialize(decryptedObjectSleutelBytes);
            valideerObjectSleutel(os, partijCode);
            return os.getPersoonId();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException
            | IllegalBlockSizeException | IOException e)
        {
            throw new ObjectSleutelVersleutelingFoutExceptie("Fout opgetreden tijdens ontcijferen van de sleutel.", e);
        }
    }

    /**
     * Valideert de objectsleutel. De objectsleutel is 24 uur geldig en mag enkel gebruikt worden door de partij
     * die de objectsleutel heeft aangevraagd.
     *
     * @param os de objectsleutel.
     * @param partijCode de partij die de objectsleutel tracht te gebruiken.
     * @throws OngeldigeObjectSleutelExceptie indien de sleutel ongeldig is, een specifieke
     */
    private void valideerObjectSleutel(final ObjectSleutel os, final int partijCode)
            throws OngeldigeObjectSleutelExceptie
    {
        final Long nu = new Date().getTime();
        if (nu - os.getTijdstipVanUitgifte() > MAX_GELDIGHEID_OBJECTSLEUTEL) {
            throw new ObjectSleutelVerlopenExceptie("Sleutel is niet meer geldig, aantal millis oud: "
                + (nu - os.getTijdstipVanUitgifte()));
        }

        if (partijCode != os.getPartijCode()) {
            throw new ObjectSleutelPartijInvalideExceptie("Ongeldige partij code: " + os.getPartijCode()
                + ", verwachte code: " + partijCode);
        }
    }

    /**
     * Encrypt de objectsleutel bytes zodat de informatie hierin niet te achterhalen is voor clients.
     *
     * @param objectSleutelBytes de geserialiseerde objectsleutel.
     * @throws NoSuchPaddingException bij een onbekende padding methode
     * @throws NoSuchAlgorithmException bij decryptie algoritme niet beschikbaar op de JVM
     * @throws InvalidKeyException als de sleutel invalide is
     * @throws BadPaddingException als de padding van de sleutel niet klopt
     * @throws IllegalBlockSizeException als de block size niet klopt
     * @return een encoded string die de encrypted bytes voorstelt.
     */
    private String encrypt(final byte[] objectSleutelBytes) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        // Encrypt:
        final byte[] rawBytes = cipher.doFinal(objectSleutelBytes);
        // Encode:
        return Base64.encodeBase64String(rawBytes);
    }

    /**
     * Decrypt de objectsleutel string naar de oorsponkelijke bytes voor het object.
     *
     * @param encryptedObjectSleutel encoded en encrypted objectsleutel string.
     * @throws NoSuchPaddingException bij een onbekende padding methode
     * @throws NoSuchAlgorithmException bij decryptie algoritme niet beschikbaar op de JVM
     * @throws InvalidKeyException als de sleutel invalide is
     * @throws BadPaddingException als de padding van de sleutel niet klopt
     * @throws IllegalBlockSizeException als de block size niet klopt
     * @return de bytes voor de objectSleutel instantie.
     */
    private byte[] decrypt(final String encryptedObjectSleutel) throws NoSuchPaddingException,
            NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        final Cipher cipher = Cipher.getInstance(CIPHER_ALGORITME);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        // Decode:
        final byte[] rawBytes = Base64.decodeBase64(encryptedObjectSleutel);
        // Decrypt:
        return cipher.doFinal(rawBytes);
    }
}
