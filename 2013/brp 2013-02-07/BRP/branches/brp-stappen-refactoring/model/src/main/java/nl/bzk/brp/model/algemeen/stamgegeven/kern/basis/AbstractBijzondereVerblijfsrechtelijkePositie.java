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

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * Categorisatie van de bijzondere verblijfsrechtelijke positie.
 *
 * Een bijzondere verblijfsrechtelijke positie heeft tot gevolg dat de vigerende vreemdelingenwet niet van toepassing
 * is.
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
public abstract class AbstractBijzondereVerblijfsrechtelijkePositie extends AbstractStatischObjectType {

    @Id
    private Byte                         iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Naam"))
    private NaamEnumeratiewaarde         naam;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Oms"))
    private OmschrijvingEnumeratiewaarde omschrijving;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractBijzondereVerblijfsrechtelijkePositie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van BijzondereVerblijfsrechtelijkePositie.
     * @param omschrijving omschrijving van BijzondereVerblijfsrechtelijkePositie.
     */
    protected AbstractBijzondereVerblijfsrechtelijkePositie(final NaamEnumeratiewaarde naam,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        this.naam = naam;
        this.omschrijving = omschrijving;

    }

    /**
     * Retourneert ID van Bijzondere verblijfsrechtelijke positie.
     *
     * @return ID.
     */
    protected Byte getID() {
        return iD;
    }

    /**
     * Retourneert Naam van Bijzondere verblijfsrechtelijke positie.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaarde getNaam() {
        return naam;
    }

    /**
     * Retourneert Omschrijving van Bijzondere verblijfsrechtelijke positie.
     *
     * @return Omschrijving.
     */
    public OmschrijvingEnumeratiewaarde getOmschrijving() {
        return omschrijving;
    }

}
