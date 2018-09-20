/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.statisch.RedenVerliesNLNationaliteit;

/**
 * De standaard groep implementatie van object type persoonnationaliteit.
 */
public abstract class AbstractPersoonNationaliteitStandaardGroepMdl extends AbstractGroep
        implements PersoonNationaliteitStandaardGroepBasis
{

    private RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit;
    private RedenVerliesNLNationaliteit redenVerliesNlNationaliteit;

    public RedenVerkrijgingNLNationaliteit getRedenVerkregenNlNationaliteit() {
        return redenVerkregenNlNationaliteit;
    }

    public RedenVerliesNLNationaliteit getRedenVerliesNlNationaliteit() {
        return redenVerliesNlNationaliteit;
    }
}
