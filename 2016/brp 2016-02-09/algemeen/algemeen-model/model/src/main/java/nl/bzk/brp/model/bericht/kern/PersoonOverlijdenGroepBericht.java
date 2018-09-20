/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.logisch.kern.PersoonOverlijdenGroep;
import nl.bzk.brp.model.validatie.constraint.BRAL0102;


/**
 * 1. De vorm van historie is conform bij geboorte: ondanks dat er een materieel tijdsaspect is (datum overlijden) is er g��n sprake van een 'materieel
 * historiepatroon'. Immers, het is absurd om te stellen dat iemand vorig jaar overleden is in plaats X en dit jaar in plaats Y ;-0 RvdP 9 jan 2012.
 */
public final class PersoonOverlijdenGroepBericht extends AbstractPersoonOverlijdenGroepBericht implements
    PersoonOverlijdenGroep
{

    @Override
    @BRAL0102(dbObject = DatabaseObjectKern.PERSOON__DATUM_OVERLIJDEN)
    public DatumEvtDeelsOnbekendAttribuut getDatumOverlijden() {
        return super.getDatumOverlijden();
    }
}
