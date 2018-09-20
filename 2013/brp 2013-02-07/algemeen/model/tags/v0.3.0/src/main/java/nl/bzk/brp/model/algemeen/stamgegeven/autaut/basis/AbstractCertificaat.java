/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatserial;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.Certificaatsubject;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PubliekeSleutel;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * Het Certificaat zoals bedoeld in de standaard X509.3.
 *
 * Certificaten worden gebruikt voor authenticatiedoeleinden.
 *
 * Van Certificaten nemen we een beperkt aantal gegevens op in deze entiteit:
 * Het betreft het subject van het certificaat, dat uitsluitsel geeft over de eigenaar ervan, en het seriel nummer.
 * Overige gegevens van het certificaat, zoals de publieke sleutel, worden hier niet opgenomen: deze worden in de
 * technische laag gebruikt, en staan op dedicated hardware.
 * RvdP 10 oktober 2011.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractCertificaat extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer            iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Subject"))
    private Certificaatsubject subject;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Serial"))
    private Certificaatserial  serial;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Signature"))
    private PubliekeSleutel    signature;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractCertificaat() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param subject subject van Certificaat.
     * @param serial serial van Certificaat.
     * @param signature signature van Certificaat.
     */
    protected AbstractCertificaat(final Certificaatsubject subject, final Certificaatserial serial,
            final PubliekeSleutel signature)
    {
        this.subject = subject;
        this.serial = serial;
        this.signature = signature;

    }

    /**
     * Retourneert ID van Certificaat.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Subject van Certificaat.
     *
     * @return Subject.
     */
    public Certificaatsubject getSubject() {
        return subject;
    }

    /**
     * Retourneert Serial van Certificaat.
     *
     * @return Serial.
     */
    public Certificaatserial getSerial() {
        return serial;
    }

    /**
     * Retourneert Signature van Certificaat.
     *
     * @return Signature.
     */
    public PubliekeSleutel getSignature() {
        return signature;
    }

}
