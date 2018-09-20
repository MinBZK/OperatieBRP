/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonPersoonskaartModel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de persoonskaart.
 */
@Component
public final class PersoonskaartHistorieMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonPersoonskaartModel, BrpPersoonskaartInhoud> {
    /**
     * Constructor.
     */
    public PersoonskaartHistorieMapper() {
        super(ElementEnum.PERSOON_PERSOONSKAART_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_PERSOONSKAART_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonPersoonskaartModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonPersoonskaartHistorie();
    }

    @Override
    public BrpPersoonskaartInhoud mapInhoud(final HisPersoonPersoonskaartModel historie, final OnderzoekMapper onderzoekMapper) {
        final BrpPartijCode gemeentePersoonskaart =
                BrpMapperUtil.mapBrpPartijCode(
                    historie.getGemeentePersoonskaart(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_PERSOONSKAART_PARTIJCODE, true));
        final BrpBoolean indicatieVolledigGeconverteerd =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatiePersoonskaartVolledigGeconverteerd(),
                    onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD, true));
        return new BrpPersoonskaartInhoud(gemeentePersoonskaart, indicatieVolledigGeconverteerd);
    }
}
