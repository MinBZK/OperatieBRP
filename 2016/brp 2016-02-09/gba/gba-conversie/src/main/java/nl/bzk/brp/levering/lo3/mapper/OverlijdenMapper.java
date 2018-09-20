/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt het overlijden.
 */
@Component
public final class OverlijdenMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonOverlijdenModel, BrpOverlijdenInhoud> {
    /**
     * Constructor.
     */
    public OverlijdenMapper() {
        super(ElementEnum.PERSOON_OVERLIJDEN_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_OVERLIJDEN_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonOverlijdenModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonOverlijdenHistorie();
    }

    @Override
    public BrpOverlijdenInhoud mapInhoud(final HisPersoonOverlijdenModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpOverlijdenInhoud(
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_DATUM, true)),
            BrpMapperUtil.mapBrpGemeenteCode(
                historie.getGemeenteOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_GEMEENTECODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getWoonplaatsnaamOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_WOONPLAATSNAAM, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsePlaatsOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandseRegioOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                historie.getLandGebiedOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_LANDGEBIEDCODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getOmschrijvingLocatieOverlijden(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE, true)));
    }
}
