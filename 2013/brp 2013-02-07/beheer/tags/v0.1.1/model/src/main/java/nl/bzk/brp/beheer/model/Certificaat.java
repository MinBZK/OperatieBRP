/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * Het Certificaat zoals bedoeld in de standaard X509.3. Certificaten worden gebruikt voor authenticatiedoeleinden.
 *
 * Van Certificaten nemen we een beperkt aantal gegevens op in deze entiteit:
 * Het betreft het subject van het certificaat, dat uitsluitsel geeft over de eigenaar ervan, en het seriel nummer.
 * Overige gegevens van het certificaat, zoals de publieke sleutel, worden hier niet opgenomen: deze worden in de
 * technische laag gebruikt, en staan op dedicated hardware.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Certificaat", schema = "AutAut")
@Access(AccessType.FIELD)
public class Certificaat implements Serializable {

    @SequenceGenerator(name = "CERTIFICAAT_SEQUENCE_GENERATOR", sequenceName = "autaut.seq_certificaat")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CERTIFICAAT_SEQUENCE_GENERATOR")
    private Long       id;
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "subject")
    private String     subject;
    @Column(name = "serial")
    private BigInteger serial;
    @Column(name = "signature")
    private String     signature;

    /**
     * No-arg constructor voor JPA.
     */
    public Certificaat() {
    }

    public Long getId() {
        return id;
    }

    /**
     * Het subject van het Certificaat, zoals bedoeld in de X.509.3 standaard voor certificaten.
     *
     * @return het subject van het Certificaat, zoals bedoeld in de X.509.3 standaard voor certificaten.
     */
    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    /**
     * Het serial nummer van het Certificaat, zoals bedoeld in de X.509.3 standaard voor certificaten.
     *
     * @return het serial nummer van het Certificaat.
     */
    public BigInteger getSerial() {
        return serial;
    }

    public void setSerial(final BigInteger serial) {
        this.serial = serial;
    }

    /**
     * Methode voor het omzetten van een hexadecimale string naar een bytearray.
     *
     * @param signature de hexadecimal string die moet worden omgezet.
     * @return de byte array omgezet vanuit de string.
     * @throws DecoderException indien er een fout optreedt bij het omzetten.
     */
    public static byte[] signatureFromStringToByteArray(final String signature) throws DecoderException {
        return Hex.decodeHex(signature.toCharArray());
    }

    /**
     * Methode voor het omzetten van een bytearray naar een hexadecimale string.
     *
     * @param signature de bytearray die moet worden omgezet.
     * @return de hexadecimale string op basis van de bytearray.
     * @throws DecoderException indien er een fout optreedt bij het omzetten.
     */
    public static String signatureFromStringToByteArray(final byte[] signature) throws DecoderException {
        return Hex.encodeHexString(signature);
    }

    /**
     * Retourneert de signature van het certificaat als byte array. De signature wordt intern bijgehouden als
     * hexadecimale string, maar extern als bytearray.
     *
     * @return de signature van het certificaat als byte array.
     * @throws DecoderException indien er een fout optreedt bij het omzetten van de signature.
     */
    public byte[] getSignature() throws DecoderException {
        return signatureFromStringToByteArray(signature);
    }

    /**
     * Zet de signature van het certificaat als byte array. De signature wordt intern bijgehouden als
     * hexadecimale string, maar extern als bytearray.
     *
     * @param signature de signature van het certificaat als byte array.
     * @throws DecoderException indien er een fout optreedt bij het omzetten van de signature.
     */
    public void setSignature(final byte[] signature) throws DecoderException {
        this.signature = signatureFromStringToByteArray(signature);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id).append("subject", subject)
                .toString();
    }

}
