/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractBijzondereVerblijfsrechtelijkePositie;


/**
 * Categorisatie van de bijzondere verblijfsrechtelijke positie.
 *
 * Een bijzondere verblijfsrechtelijke positie heeft tot gevolg dat de vigerende vreemdelingenwet niet van toepassing
 * is.
 *
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "BVP")
public class BijzondereVerblijfsrechtelijkePositie extends AbstractBijzondereVerblijfsrechtelijkePositie {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected BijzondereVerblijfsrechtelijkePositie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param naam naam van BijzondereVerblijfsrechtelijkePositie.
     * @param omschrijving omschrijving van BijzondereVerblijfsrechtelijkePositie.
     */
    protected BijzondereVerblijfsrechtelijkePositie(final NaamEnumeratiewaarde naam,
            final OmschrijvingEnumeratiewaarde omschrijving)
    {
        super(naam, omschrijving);
    }

}
