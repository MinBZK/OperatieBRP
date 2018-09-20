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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;

/**
 * De mogelijke hoedanigheid waarmee een persoon die aangifte doet van verblijf en adres, van adreswijziging of van
 * emigratie kan staan ten opzichte van de Persoon wiens adres is aangegeven.
 *
 * In veel gevallen is degene wiens adres wijzigt ook degene die de adreswijziging doorgeeft. Maar het is ook mogelijk
 * dat anderen de wijziging doorgeven, bijvoorbeeld een meerderjarig gemachtigde.
 *
 *
 *
 */
@Entity(name = "beheer.Aangever")
@Table(schema = "Kern", name = "Aang")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Aangever {

    @Id
    @SequenceGenerator(name = "AANGEVER", sequenceName = "Kern.seq_Aang")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AANGEVER")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AangeverCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private AangeverCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaardeAttribuut omschrijving;

    /**
     * Retourneert ID van Aangever.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Aangever.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AangeverCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Aangever.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Aangever.
     *
     * @return Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * Zet ID van Aangever.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Aangever.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final AangeverCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Aangever.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Omschrijving van Aangever.
     *
     * @param pOmschrijving Omschrijving.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOmschrijving(final OmschrijvingEnumeratiewaardeAttribuut pOmschrijving) {
        this.omschrijving = pOmschrijving;
    }

}
