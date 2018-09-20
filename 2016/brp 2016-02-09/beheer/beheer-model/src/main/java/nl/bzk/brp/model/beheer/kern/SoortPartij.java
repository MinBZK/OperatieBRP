/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * Typering van Partij.
 *
 * De Soort partij is vrijelijk indeelbaar door de beheerder. De Soort heeft geen invloed op de werking van de BRP.
 *
 * De beheerder is vrij om Partijen in te delen naar een Soort. Voorheen was de Soort partij een statisch stamgegeven en
 * suggereerde de indeling een betekenis. De (Partij \ )Rol is bedoeld om de Partij een, voor het systeem,
 * betekenisvolle indeling te geven.
 *
 *
 *
 */
@Entity(name = "beheer.SoortPartij")
@Table(schema = "Kern", name = "SrtPartij")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SoortPartij {

    @Id
    @SequenceGenerator(name = "SOORTPARTIJ", sequenceName = "Kern.seq_SrtPartij")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SOORTPARTIJ")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvGel"))
    private DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeGel"))
    private DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid;

    /**
     * Retourneert ID van Soort partij.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Soort partij.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum aanvang geldigheid van Soort partij.
     *
     * @return Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Retourneert Datum einde geldigheid van Soort partij.
     *
     * @return Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEindeGeldigheid() {
        return datumEindeGeldigheid;
    }

    /**
     * Zet ID van Soort partij.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Naam van Soort partij.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum aanvang geldigheid van Soort partij.
     *
     * @param pDatumAanvangGeldigheid Datum aanvang geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumAanvangGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = pDatumAanvangGeldigheid;
    }

    /**
     * Zet Datum einde geldigheid van Soort partij.
     *
     * @param pDatumEindeGeldigheid Datum einde geldigheid.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEindeGeldigheid(final DatumEvtDeelsOnbekendAttribuut pDatumEindeGeldigheid) {
        this.datumEindeGeldigheid = pDatumEindeGeldigheid;
    }

}
