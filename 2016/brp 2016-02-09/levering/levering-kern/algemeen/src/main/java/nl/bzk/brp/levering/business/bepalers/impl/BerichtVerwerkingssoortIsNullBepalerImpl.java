/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortBepaler;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import org.springframework.stereotype.Component;

/**
 * Deze klasse is een implementatie van de bericht verwerkingssoort bepaler.
 * Deze geeft altijd <code>null</code> als verwerkingssoort.
 */
@Component("nullVerwerkingssoortBepaler")
public class BerichtVerwerkingssoortIsNullBepalerImpl implements BerichtVerwerkingssoortBepaler {

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoort(final HistorieEntiteit formeleOfMaterieleHistorie, final Long administratieveHandelingId)
    {
        return null;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortPersoon(final Long administratieveHandelingIdKennisgeving,
        final PersoonHisVolledigView persoonHisVolledig)
    {
        return null;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortBetrokkenPersoon(final Long administratieveHandelingIdKennisgeving,
        final PersoonHisVolledigView persoonHisVolledig)
    {
        return null;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortBetrokkenKind(final Long administratieveHandelingIdKennisgeving,
        final PersoonHisVolledigView persoonHisVolledig)
    {
        return null;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortVoorRelaties(final RelatieHisVolledigView relatieHisVolledigView,
        final Long administratieveHandelingId)
    {
        return null;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortVoorBetrokkenheden(final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView,
        final Long administratieveHandelingId)
    {
        return null;
    }

}
