/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De mogelijke adellijke titel van een Persoon.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractAdellijkeTitel implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = AdellijkeTitelCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    @JsonProperty
    private AdellijkeTitelCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamMannelijk"))
    private NaamEnumeratiewaardeAttribuut naamMannelijk;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaamVrouwelijk"))
    private NaamEnumeratiewaardeAttribuut naamVrouwelijk;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractAdellijkeTitel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AdellijkeTitel.
     * @param naamMannelijk naamMannelijk van AdellijkeTitel.
     * @param naamVrouwelijk naamVrouwelijk van AdellijkeTitel.
     */
    protected AbstractAdellijkeTitel(
        final AdellijkeTitelCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naamMannelijk,
        final NaamEnumeratiewaardeAttribuut naamVrouwelijk)
    {
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;

    }

    /**
     * Retourneert ID van Adellijke titel.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Adellijke titel.
     *
     * @return Code.
     */
    public final AdellijkeTitelCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam mannelijk van Adellijke titel.
     *
     * @return Naam mannelijk.
     */
    public final NaamEnumeratiewaardeAttribuut getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Retourneert Naam vrouwelijk van Adellijke titel.
     *
     * @return Naam vrouwelijk.
     */
    public final NaamEnumeratiewaardeAttribuut getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.ADELLIJKETITEL;
    }

}
