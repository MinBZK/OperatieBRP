/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractVoorvoegsel;


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
@Entity
@Table(schema = "Kern", name = "Voorvoegsel2")
public class Voorvoegsel extends AbstractVoorvoegsel {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Voorvoegsel() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param voorvoegsel voorvoegsel van Voorvoegsel.
     * @param scheidingsteken scheidingsteken van Voorvoegsel.
     * @param lO3Voorvoegsel lO3Voorvoegsel van Voorvoegsel.
     */
    protected Voorvoegsel(final NaamEnumeratiewaarde voorvoegsel, final Scheidingsteken scheidingsteken,
            final NaamEnumeratiewaarde lO3Voorvoegsel)
    {
        super(voorvoegsel, scheidingsteken, lO3Voorvoegsel);
    }

}
