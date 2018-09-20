/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVastgesteldNietNederlanderModel;

public class VastgesteldNietNederlanderGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieVastgesteldNietNederlanderModel, PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00006;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_VASTGESTELD_NIET_NEDERLANDER;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieVastgesteldNietNederlanderModel,
            PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieVastgesteldNietNederlanderModel> model,
            final ActieModel actie)
    {
        return new VastgesteldNietNederlanderGroepVerwerker(bericht, (PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieVastgesteldNietNederlanderModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl(getPersoonHisVolledigImpl());
    }


}
