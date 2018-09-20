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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingInhoudingVermissingReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * De (mogelijke) reden van het definitief aan het verkeer onttrokken zijn van het Nederlands reisdocument.
 *
 *
 *
 */
@Entity(name = "beheer.AanduidingInhoudingVermissingReisdocument")
@Table(schema = "Kern", name = "AandInhingVermissingReisdoc")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class AanduidingInhoudingVermissingReisdocument {

    @Id
    @SequenceGenerator(name = "AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT", sequenceName = "Kern.seq_AandInhingVermissingReisdoc")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "AANDUIDINGINHOUDINGVERMISSINGREISDOCUMENT")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AanduidingInhoudingVermissingReisdocumentCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private AanduidingInhoudingVermissingReisdocumentCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    /**
     * Retourneert ID van Aanduiding inhouding/vermissing reisdocument.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Aanduiding inhouding/vermissing reisdocument.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AanduidingInhoudingVermissingReisdocumentCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Aanduiding inhouding/vermissing reisdocument.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Zet ID van Aanduiding inhouding/vermissing reisdocument.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Aanduiding inhouding/vermissing reisdocument.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final AanduidingInhoudingVermissingReisdocumentCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Aanduiding inhouding/vermissing reisdocument.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

}
