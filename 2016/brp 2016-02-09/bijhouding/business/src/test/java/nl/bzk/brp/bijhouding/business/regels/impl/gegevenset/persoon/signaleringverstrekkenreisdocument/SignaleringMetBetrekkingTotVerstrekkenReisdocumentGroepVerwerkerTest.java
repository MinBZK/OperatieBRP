/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.signaleringverstrekkenreisdocument;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel;

public class SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel,
        PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00019;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel,
            PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel> model,
            final ActieModel actie)
    {
        return new SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerker(bericht,
                      (PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl(getPersoonHisVolledigImpl());
    }

}
