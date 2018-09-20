/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.attribuuttype.RedenVerliesCode;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonNationaliteitStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;

/**
 * De standaard groep implementatie van object type persoonnationaliteit.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonNationaliteitStandaardGroepBericht extends AbstractGroepBericht
        implements PersoonNationaliteitStandaardGroepBasis
{

    private RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit;
    private RedenVerkrijgingCode redenVerkregenNlNationaliteitCode;
    private RedenVerliesNLNationaliteit redenVerliesNlNationaliteit;
    private RedenVerliesCode redenVerliesNlNationaliteitCode;

    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkregenNlNationaliteit() {
        return redenVerkregenNlNationaliteit;
    }

    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNlNationaliteit() {
        return redenVerliesNlNationaliteit;
    }

    public RedenVerkrijgingCode getRedenVerkregenNlNationaliteitCode() {
        return redenVerkregenNlNationaliteitCode;
    }

    public RedenVerliesCode getRedenVerliesNlNationaliteitCode() {
        return redenVerliesNlNationaliteitCode;
    }

    public void setRedenVerkregenNlNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkregenNlNationaliteit) {
        this.redenVerkregenNlNationaliteit = redenVerkregenNlNationaliteit;
    }

    public void setRedenVerliesNlNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNlNationaliteit) {
        this.redenVerliesNlNationaliteit = redenVerliesNlNationaliteit;
    }

    public void setRedenVerkregenNlNationaliteitCode(final RedenVerkrijgingCode redenVerkregenNlNationaliteitCode) {
        this.redenVerkregenNlNationaliteitCode = redenVerkregenNlNationaliteitCode;
    }

    public void setRedenVerliesNlNationaliteitNaam(final RedenVerliesCode redenVerliesNlNationaliteitCode) {
        this.redenVerliesNlNationaliteitCode = redenVerliesNlNationaliteitCode;
    }
}
