/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;

public class CurateleGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieOnderCurateleModel, PersoonIndicatieOnderCurateleHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00017;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_ONDER_CURATELE;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieOnderCurateleModel,
            PersoonIndicatieOnderCurateleHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieOnderCurateleModel> model,
            final ActieModel actie)
    {
        return new CurateleGroepVerwerker(bericht, (PersoonIndicatieOnderCurateleHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieOnderCurateleModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieOnderCurateleHisVolledigImpl(getPersoonHisVolledigImpl());
    }

}
