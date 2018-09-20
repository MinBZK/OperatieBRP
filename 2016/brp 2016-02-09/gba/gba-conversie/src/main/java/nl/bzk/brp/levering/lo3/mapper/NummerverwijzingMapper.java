/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNummerverwijzingModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de nummerverwijzing.
 */
@Component
public final class NummerverwijzingMapper extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonNummerverwijzingModel, BrpNummerverwijzingInhoud> {
    /**
     * Constructor.
     */
    public NummerverwijzingMapper() {
        super(ElementEnum.PERSOON_NUMMERVERWIJZING_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_NUMMERVERWIJZING_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_NUMMERVERWIJZING_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_NUMMERVERWIJZING_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonNummerverwijzingModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonNummerverwijzingHistorie();
    }

    @Override
    public BrpNummerverwijzingInhoud mapInhoud(final HisPersoonNummerverwijzingModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpNummerverwijzingInhoud(
            BrpMapperUtil.mapBrpLong(
                historie.getVorigeAdministratienummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER, true)),
            BrpMapperUtil.mapBrpLong(
                historie.getVolgendeAdministratienummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER, true)),
            BrpMapperUtil.mapBrpInteger(
                historie.getVorigeBurgerservicenummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER, true)),
            BrpMapperUtil.mapBrpInteger(
                historie.getVolgendeBurgerservicenummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER, true)));
    }
}
