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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;


/**
 * De in de BRP en GBA onderkende voorvoegsels, die voor de geslachtsnaam worden geplaatst bij aanschrijving.
 *
 * Ten behoeve van de migratieperiode onderkend de BRP een vaste lijst met voorvoegsels, deze worden gebruikt in de
 * conversie tussen GBA_V en de BRP, en ten behoeve van een bedrijfsregel.
 *
 * De voorvoegseltabel is toegevoegd ten behoeve van onder andere de bedrijfsregel; er wordt NIET naar verwezen (in die
 * zin is het g��n echte stamtabel); dat is omdat voorzien is dat op den duur de limitatie tot bepaalde voorvoegsels
 * wordt losgelaten. RvdP 12 november 2012.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractVoorvoegsel extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Short                iD;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Voorvoegsel"))
    private NaamEnumeratiewaarde voorvoegsel;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Scheidingsteken"))
    private Scheidingsteken      scheidingsteken;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LO3Voorvoegsel"))
    private NaamEnumeratiewaarde lO3Voorvoegsel;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractVoorvoegsel() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param voorvoegsel voorvoegsel van Voorvoegsel.
     * @param scheidingsteken scheidingsteken van Voorvoegsel.
     * @param lO3Voorvoegsel lO3Voorvoegsel van Voorvoegsel.
     */
    protected AbstractVoorvoegsel(final NaamEnumeratiewaarde voorvoegsel, final Scheidingsteken scheidingsteken,
            final NaamEnumeratiewaarde lO3Voorvoegsel)
    {
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;
        this.lO3Voorvoegsel = lO3Voorvoegsel;

    }

    /**
     * Retourneert ID van Voorvoegsel.
     *
     * @return ID.
     */
    protected Short getID() {
        return iD;
    }

    /**
     * Retourneert Voorvoegsel van Voorvoegsel.
     *
     * @return Voorvoegsel.
     */
    public NaamEnumeratiewaarde getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van Voorvoegsel.
     *
     * @return Scheidingsteken.
     */
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Retourneert LO3Voorvoegsel van Voorvoegsel.
     *
     * @return LO3Voorvoegsel.
     */
    public NaamEnumeratiewaarde getLO3Voorvoegsel() {
        return lO3Voorvoegsel;
    }

}
