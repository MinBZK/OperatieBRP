/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieSignaleringReisdocumentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;

public class RegistratieSignaleringReisdocumentTest extends
        AbstractRegistratieIndicatieUitvoerderTest<ActieRegistratieSignaleringReisdocumentBericht>
{

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT;
    }

    @Override
    protected AbstractRegistratieIndicatieUitvoerder maakNieuweUitvoerder() {
        return new RegistratieSignaleringReisdocument();
    }

    @Override
    protected ActieRegistratieSignaleringReisdocumentBericht maakNieuwActieBericht() {
        return new ActieRegistratieSignaleringReisdocumentBericht();
    }

    @Override
    protected void voegNieuweIndicatieHisVolledigToe() {
        getPersoon().setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(
                new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl(getPersoon()));
    }

    @Override
    protected void verwijderIndicatie() {
        getPersoon().setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(null);
    }

}
