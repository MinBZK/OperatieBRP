/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieStaatloosModel;

public class StaatloosGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieStaatloosModel, PersoonIndicatieStaatloosHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00005;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_STAATLOOS;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieStaatloosModel,
            PersoonIndicatieStaatloosHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieStaatloosModel> model,
            final ActieModel actie)
    {
        return new StaatloosGroepVerwerker(bericht, (PersoonIndicatieStaatloosHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieStaatloosModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieStaatloosHisVolledigImpl(getPersoonHisVolledigImpl());
    }

}
