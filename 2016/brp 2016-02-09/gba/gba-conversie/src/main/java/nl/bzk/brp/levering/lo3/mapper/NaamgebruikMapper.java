/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNaamgebruikModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt het naamgebruik.
 */
@Component
public final class NaamgebruikMapper extends AbstractFormeelMapper<PersoonHisVolledig, HisPersoonNaamgebruikModel, BrpNaamgebruikInhoud> {
    /**
     * Constructor.
     */
    public NaamgebruikMapper() {
        super(ElementEnum.PERSOON_NAAMGEBRUIK_TIJDSTIPREGISTRATIE, ElementEnum.PERSOON_NAAMGEBRUIK_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonNaamgebruikModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonNaamgebruikHistorie();
    }

    @Override
    public BrpNaamgebruikInhoud mapInhoud(final HisPersoonNaamgebruikModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpNaamgebruikInhoud(
            BrpMapperUtil.mapBrpNaamgebruikCode(
                historie.getNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_CODE, true)),
            BrpMapperUtil.mapBrpBoolean(
                historie.getIndicatieNaamgebruikAfgeleid(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID, true)),
            BrpMapperUtil.mapBrpPredicaatCode(
                historie.getPredicaatNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_PREDICAATCODE, true)),
            BrpMapperUtil.mapBrpAdellijkeTitelCode(
                historie.getAdellijkeTitelNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getVoornamenNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_VOORNAMEN, true)),
            BrpMapperUtil.mapBrpString(
                historie.getVoorvoegselNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_VOORVOEGSEL, true)),
            BrpMapperUtil.mapBrpCharacter(
                historie.getScheidingstekenNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN, true)),
            BrpMapperUtil.mapBrpString(
                historie.getGeslachtsnaamstamNaamgebruik(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM, true)));

    }

}
