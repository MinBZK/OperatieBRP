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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;

/**
 * De mogelijke adellijke titel van een Persoon.
 *
 *
 *
 */
@Entity(name = "beheer.AdellijkeTitel")
@Table(schema = "Kern", name = "AdellijkeTitel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class AdellijkeTitel {

    @Id
    @SequenceGenerator(name = "ADELLIJKETITEL", sequenceName = "Kern.seq_AdellijkeTitel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ADELLIJKETITEL")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AdellijkeTitelCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private AdellijkeTitelCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamMannelijk"))
    private NaamEnumeratiewaardeAttribuut naamMannelijk;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamVrouwelijk"))
    private NaamEnumeratiewaardeAttribuut naamVrouwelijk;

    /**
     * Retourneert ID van Adellijke titel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Adellijke titel.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AdellijkeTitelCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam mannelijk van Adellijke titel.
     *
     * @return Naam mannelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Retourneert Naam vrouwelijk van Adellijke titel.
     *
     * @return Naam vrouwelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    /**
     * Zet ID van Adellijke titel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Adellijke titel.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final AdellijkeTitelCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam mannelijk van Adellijke titel.
     *
     * @param pNaamMannelijk Naam mannelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaamMannelijk(final NaamEnumeratiewaardeAttribuut pNaamMannelijk) {
        this.naamMannelijk = pNaamMannelijk;
    }

    /**
     * Zet Naam vrouwelijk van Adellijke titel.
     *
     * @param pNaamVrouwelijk Naam vrouwelijk.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaamVrouwelijk(final NaamEnumeratiewaardeAttribuut pNaamVrouwelijk) {
        this.naamVrouwelijk = pNaamVrouwelijk;
    }

}
