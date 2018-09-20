/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdellijkeTitelCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De mogelijke adellijke titel van een Persoon.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:51 CET 2013.
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractAdellijkeTitel extends AbstractStatischObjectType {

    @Id
    private Short                iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Code"))
    private AdellijkeTitelCode   code;

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
    protected AbstractAdellijkeTitel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van AdellijkeTitel.
     * @param naamMannelijk naamMannelijk van AdellijkeTitel.
     * @param naamVrouwelijk naamVrouwelijk van AdellijkeTitel.
     */
    protected AbstractAdellijkeTitel(final AdellijkeTitelCode code, final NaamEnumeratiewaarde naamMannelijk,
            final NaamEnumeratiewaarde naamVrouwelijk)
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
    public AdellijkeTitelCode getCode() {
        return code;
    }

    /**
     * Retourneert Naam mannelijk van Adellijke titel.
     *
     * @return Naam mannelijk.
     */
    public NaamEnumeratiewaarde getNaamMannelijk() {
        return naamMannelijk;
    }

    /**
     * Retourneert Naam vrouwelijk van Adellijke titel.
     *
     * @return Naam vrouwelijk.
     */
    public NaamEnumeratiewaarde getNaamVrouwelijk() {
        return naamVrouwelijk;
    }

}
