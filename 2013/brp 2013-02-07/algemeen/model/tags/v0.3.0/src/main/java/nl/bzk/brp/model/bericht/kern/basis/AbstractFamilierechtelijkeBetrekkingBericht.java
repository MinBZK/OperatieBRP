/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.logisch.kern.basis.FamilierechtelijkeBetrekkingBasis;


/**
 * De familierechtelijke betrekking tussen het Kind enerzijds, en zijn/haar ouders anderzijds.
 *
 * De familierechtelijke betrekking "is" van het Kind. Adoptie, erkenning, dan wel het terugdraaien van een adoptie of
 * erkenning heeft g��n invloed op de familierechtelijke betrekking zelf: het blijft ��n en dezelfde Relatie.
 *
 *
 *
 */
public abstract class AbstractFamilierechtelijkeBetrekkingBericht extends RelatieBericht implements
        FamilierechtelijkeBetrekkingBasis
{

    /**
     * Constructor die het discriminator attribuut zet of doorgeeft.
     *
     */
    public AbstractFamilierechtelijkeBetrekkingBericht() {
        super(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
    }

}
