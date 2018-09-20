/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt een geslachtsnaamcomponent.
 */
@Component
public final class GeslachtsnaamcomponentMapper extends
        AbstractMaterieelMapper<PersoonGeslachtsnaamcomponentHisVolledig, HisPersoonGeslachtsnaamcomponentModel, BrpGeslachtsnaamcomponentInhoud>
{
    /**
     * Constructor.
     */
    public GeslachtsnaamcomponentMapper() {
        super(ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonGeslachtsnaamcomponentModel> getHistorieIterable(
        final PersoonGeslachtsnaamcomponentHisVolledig persoonGeslachtsnaamcomponentHisVolledig)
    {
        return persoonGeslachtsnaamcomponentHisVolledig.getPersoonGeslachtsnaamcomponentHistorie();
    }

    @Override
    public BrpGeslachtsnaamcomponentInhoud mapInhoud(final HisPersoonGeslachtsnaamcomponentModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpGeslachtsnaamcomponentInhoud(
            BrpMapperUtil.mapBrpString(
                historie.getVoorvoegsel(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL, true)),
            BrpMapperUtil.mapBrpCharacter(
                historie.getScheidingsteken(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN, true)),
            BrpMapperUtil.mapBrpString(
                historie.getStam(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_STAM, true)),
            BrpMapperUtil.mapBrpPredicaatCode(
                historie.getPredicaat(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE, true)),
            BrpMapperUtil.mapBrpAdellijkeTitelCode(
                historie.getAdellijkeTitel(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE, true)),
            BrpMapperUtil.mapBrpInteger(historie.getPersoonGeslachtsnaamcomponent().getVolgnummer(), null));
    }
}
