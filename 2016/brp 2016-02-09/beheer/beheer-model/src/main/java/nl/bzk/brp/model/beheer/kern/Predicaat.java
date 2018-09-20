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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredicaatCodeAttribuut;

/**
 * De mogelijke predicaat van een Persoon.
 *
 *
 *
 */
@Entity(name = "beheer.Predicaat")
@Table(schema = "Kern", name = "Predicaat")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Predicaat {

    @Id
    @SequenceGenerator(name = "PREDICAAT", sequenceName = "Kern.seq_Predicaat")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PREDICAAT")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = PredicaatCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private PredicaatCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamMannelijk"))
    private NaamEnumeratiewaardeAttribuut naamMannelijk;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamVrouwelijk"))
    private NaamEnumeratiewaardeAttribuut naamVrouwelijk;

    /**
     * Retourneert ID van Predicaat.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Predicaat.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PredicaatCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam mannelijk van Predicaat.
     *
     * @return Naam mannelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Retourneert Naam vrouwelijk van Predicaat.
     *
     * @return Naam vrouwelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    /**
     * Zet ID van Predicaat.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Predicaat.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final PredicaatCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam mannelijk van Predicaat.
     *
     * @param pNaamMannelijk Naam mannelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaamMannelijk(final NaamEnumeratiewaardeAttribuut pNaamMannelijk) {
        this.naamMannelijk = pNaamMannelijk;
    }

    /**
     * Zet Naam vrouwelijk van Predicaat.
     *
     * @param pNaamVrouwelijk Naam vrouwelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaamVrouwelijk(final NaamEnumeratiewaardeAttribuut pNaamVrouwelijk) {
        this.naamVrouwelijk = pNaamVrouwelijk;
    }

}
