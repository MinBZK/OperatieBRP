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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenWijzigingVerblijfCodeAttribuut;

/**
 * De mogelijke reden voor het wijzigen van verblijf.
 *
 * De waarde onbekend (?) is bestemd voor conversie-doeleinden. In de BRP-bijhoudingen dient vulling van de waarde
 * voorkomen te worden.
 *
 *
 *
 */
@Entity(name = "beheer.RedenWijzigingVerblijf")
@Table(schema = "Kern", name = "RdnWijzVerblijf")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class RedenWijzigingVerblijf {

    @Id
    @SequenceGenerator(name = "REDENWIJZIGINGVERBLIJF", sequenceName = "Kern.seq_RdnWijzVerblijf")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "REDENWIJZIGINGVERBLIJF")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = RedenWijzigingVerblijfCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private RedenWijzigingVerblijfCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    /**
     * Retourneert ID van Reden wijziging verblijf.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Reden wijziging verblijf.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenWijzigingVerblijfCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Reden wijziging verblijf.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Zet ID van Reden wijziging verblijf.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Reden wijziging verblijf.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final RedenWijzigingVerblijfCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Reden wijziging verblijf.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

}
