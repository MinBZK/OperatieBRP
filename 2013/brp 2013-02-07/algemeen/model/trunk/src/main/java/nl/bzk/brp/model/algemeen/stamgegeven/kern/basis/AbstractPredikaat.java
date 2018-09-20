/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke predikaat van een Persoon.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractPredikaat extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private PredikaatCode        code;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "NaamMannelijk"))
    private NaamEnumeratiewaarde naamMannelijk;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "NaamVrouwelijk"))
    private NaamEnumeratiewaarde naamVrouwelijk;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPredikaat() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Predikaat.
     * @param naamMannelijk naamMannelijk van Predikaat.
     * @param naamVrouwelijk naamVrouwelijk van Predikaat.
     */
    protected AbstractPredikaat(final PredikaatCode code, final NaamEnumeratiewaarde naamMannelijk,
            final NaamEnumeratiewaarde naamVrouwelijk)
    {
        this.code = code;
        this.naamMannelijk = naamMannelijk;
        this.naamVrouwelijk = naamVrouwelijk;

    }

    /**
     * Retourneert ID van Predikaat.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Predikaat.
     *
     * @return Code.
     */
    public PredikaatCode getCode() {
        return code;
    }

    /**
     * Retourneert Naam mannelijk van Predikaat.
     *
     * @return Naam mannelijk.
     */
    public NaamEnumeratiewaarde getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Retourneert Naam vrouwelijk van Predikaat.
     *
     * @return Naam vrouwelijk.
     */
    public NaamEnumeratiewaarde getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

}
