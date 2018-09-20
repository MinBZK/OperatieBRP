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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import org.hibernate.annotations.Immutable;

/**
 * De in de BRP en GBA onderkende voorvoegsels, die voor de geslachtsnaam worden geplaatst bij aanschrijving.
 *
 * Ten behoeve van de migratieperiode onderkend de BRP een vaste lijst met voorvoegsels, deze worden gebruikt in de
 * conversie tussen GBA_V en de BRP, en ten behoeve van een bedrijfsregel.
 *
 * De voorvoegseltabel is toegevoegd ten behoeve van onder andere de bedrijfsregel; er wordt NIET naar verwezen (in die
 * zin is het géén echte stamtabel); dat is omdat voorzien is dat op den duur de limitatie tot bepaalde voorvoegsels
 * wordt losgelaten.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractVoorvoegsel implements SynchroniseerbaarStamgegeven, ElementIdentificeerbaar {

    @Id
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = VoorvoegselAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Voorvoegsel"))
    @JsonProperty
    private VoorvoegselAttribuut voorvoegsel;

    @Embedded
    @AttributeOverride(name = ScheidingstekenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Scheidingsteken"))
    @JsonProperty
    private ScheidingstekenAttribuut scheidingsteken;

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
     */
    protected AbstractVoorvoegsel(final VoorvoegselAttribuut voorvoegsel, final ScheidingstekenAttribuut scheidingsteken) {
        this.voorvoegsel = voorvoegsel;
        this.scheidingsteken = scheidingsteken;

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
    public final VoorvoegselAttribuut getVoorvoegsel() {
        return voorvoegsel;
    }

    /**
     * Retourneert Scheidingsteken van Voorvoegsel.
     *
     * @return Scheidingsteken.
     */
    public final ScheidingstekenAttribuut getScheidingsteken() {
        return scheidingsteken;
    }

    /**
     * Retourneert het Element behorende bij dit objecttype.
     *
     * @return Element enum instantie behorende bij dit objecttype.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.VOORVOEGSEL;
    }

}
