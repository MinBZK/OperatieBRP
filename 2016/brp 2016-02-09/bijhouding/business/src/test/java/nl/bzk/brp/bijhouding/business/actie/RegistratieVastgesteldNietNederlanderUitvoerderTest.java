/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;

public class RegistratieVastgesteldNietNederlanderUitvoerderTest
        extends AbstractRegistratieIndicatieUitvoerderTest<ActieRegistratieVerstrekkingsbeperkingBericht>
{

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER;
    }

    @Override
    protected AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder() {
        return new RegistratieVastgesteldNietNederlanderUitvoerder();
    }

    @Override
    protected ActieRegistratieVerstrekkingsbeperkingBericht maakNieuwActieBericht() {
        return new ActieRegistratieVerstrekkingsbeperkingBericht();
    }

    @Override
    protected void voegNieuweIndicatieHisVolledigToe() {
        getPersoon().setIndicatieVastgesteldNietNederlander(new PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl(getPersoon()));
    }

    @Override
    protected void verwijderIndicatie() {
        getPersoon().setIndicatieVastgesteldNietNederlander(null);
    }

}
