/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.basis.PersoonNationaliteitStandaardGroepBasis;


/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materi�le historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, ��k omdat dit van oudsher gebeurde vanuit de
 * GBA.
 * RvdP 17 jan 2012.
 *
 *
 *
 */
public abstract class AbstractPersoonNationaliteitStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonNationaliteitStandaardGroepBasis
{

    private String                          redenVerkrijgingCode;
    private RedenVerkrijgingNLNationaliteit redenVerkrijging;
    private String                          redenVerliesCode;
    private RedenVerliesNLNationaliteit     redenVerlies;

    /**
     * Retourneert Reden verkrijging van Standaard.
     *
     * @return Reden verkrijging.
     */
    public String getRedenVerkrijgingCode() {
        return redenVerkrijgingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * Retourneert Reden verlies van Standaard.
     *
     * @return Reden verlies.
     */
    public String getRedenVerliesCode() {
        return redenVerliesCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteit getRedenVerlies() {
        return redenVerlies;
    }

    /**
     * Zet Reden verkrijging van Standaard.
     *
     * @param redenVerkrijgingCode Reden verkrijging.
     */
    public void setRedenVerkrijgingCode(final String redenVerkrijgingCode) {
        this.redenVerkrijgingCode = redenVerkrijgingCode;
    }

    /**
     * Zet Reden verkrijging van Standaard.
     *
     * @param redenVerkrijging Reden verkrijging.
     */
    public void setRedenVerkrijging(final RedenVerkrijgingNLNationaliteit redenVerkrijging) {
        this.redenVerkrijging = redenVerkrijging;
    }

    /**
     * Zet Reden verlies van Standaard.
     *
     * @param redenVerliesCode Reden verlies.
     */
    public void setRedenVerliesCode(final String redenVerliesCode) {
        this.redenVerliesCode = redenVerliesCode;
    }

    /**
     * Zet Reden verlies van Standaard.
     *
     * @param redenVerlies Reden verlies.
     */
    public void setRedenVerlies(final RedenVerliesNLNationaliteit redenVerlies) {
        this.redenVerlies = redenVerlies;
    }

}
