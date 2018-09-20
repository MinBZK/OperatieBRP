/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonOverlijdenGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er
 * g��n sprake van een 'materieel historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden
 * is in plaats X en dit jaar in plaats Y ;-0
 * RvdP 9 jan 2012.
 *
 *
 *
 */
public class PersoonOverlijdenGroepBericht extends AbstractPersoonOverlijdenGroepBericht implements
        PersoonOverlijdenGroep
{
    @Override
    @nl.bzk.brp.model.validatie.constraint.Datum
    public Datum getDatumOverlijden() {
        return super.getDatumOverlijden();
    }
}
