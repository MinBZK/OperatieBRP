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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;

/**
 * Categorisatie van Documenten.
 *
 *
 *
 *
 */
@Entity(name = "beheer.SoortDocument")
@Table(schema = "Kern", name = "SrtDoc")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class SoortDocument {

    @Id
    @SequenceGenerator(name = "SOORTDOCUMENT", sequenceName = "Kern.seq_SrtDoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SOORTDOCUMENT")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rangorde"))
    private VolgnummerAttribuut rangorde;

    /**
     * Retourneert ID van Soort document.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Soort document.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Soort document.
     *
     * @return Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Retourneert Rangorde van Soort document.
     *
     * @return Rangorde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public VolgnummerAttribuut getRangorde() {
        return rangorde;
    }

    /**
     * Zet ID van Soort document.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Naam van Soort document.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Omschrijving van Soort document.
     *
     * @param pOmschrijving Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOmschrijving(final OmschrijvingEnumeratiewaardeAttribuut pOmschrijving) {
        this.omschrijving = pOmschrijving;
    }

    /**
     * Zet Rangorde van Soort document.
     *
     * @param pRangorde Rangorde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRangorde(final VolgnummerAttribuut pRangorde) {
        this.rangorde = pRangorde;
    }

}
