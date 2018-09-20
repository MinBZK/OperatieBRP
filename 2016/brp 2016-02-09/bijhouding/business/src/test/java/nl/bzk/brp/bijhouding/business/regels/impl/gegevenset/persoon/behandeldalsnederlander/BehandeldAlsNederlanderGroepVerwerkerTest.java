/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerkerTest;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieBehandeldAlsNederlanderModel;

public class BehandeldAlsNederlanderGroepVerwerkerTest extends AbstractPersoonIndicatieGroepVerwerkerTest<
        HisPersoonIndicatieBehandeldAlsNederlanderModel, PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl>
{

    @Override
    protected Regel getVerwachteRegel() {
        return Regel.VR00007;
    }

    @Override
    protected SoortIndicatie getSoortIndicatie() {
        return SoortIndicatie.INDICATIE_BEHANDELD_ALS_NEDERLANDER;
    }

    @Override
    protected AbstractPersoonIndicatieGroepVerwerker<HisPersoonIndicatieBehandeldAlsNederlanderModel,
            PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl> maakNieuweVerwerker(
            final PersoonIndicatieBericht bericht,
            final PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieBehandeldAlsNederlanderModel> model,
            final ActieModel actie)
    {
        return new BehandeldAlsNederlanderGroepVerwerker(bericht, (PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl) model, actie);
    }

    @Override
    protected PersoonIndicatieHisVolledigImpl<HisPersoonIndicatieBehandeldAlsNederlanderModel> maakPersoonIndicatieHisVolledigImpl() {
        return new PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl(getPersoonHisVolledigImpl());
    }

}
