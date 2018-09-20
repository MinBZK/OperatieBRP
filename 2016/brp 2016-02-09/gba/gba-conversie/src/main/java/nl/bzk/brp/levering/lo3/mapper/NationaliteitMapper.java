/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonNationaliteitHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een nationaliteit.
 */
@Component
public final class NationaliteitMapper
        extends AbstractMaterieelMapper<PersoonNationaliteitHisVolledig, HisPersoonNationaliteitModel, BrpNationaliteitInhoud>
{
    /**
     * Constructor.
     */
    public NationaliteitMapper() {
        super(ElementEnum.PERSOON_NATIONALITEIT_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_NATIONALITEIT_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_NATIONALITEIT_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_NATIONALITEIT_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonNationaliteitModel> getHistorieIterable(final PersoonNationaliteitHisVolledig persoonNationaliteitHisVolledig) {
        return persoonNationaliteitHisVolledig.getPersoonNationaliteitHistorie();
    }

    @Override
    public BrpNationaliteitInhoud mapInhoud(final HisPersoonNationaliteitModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpNationaliteitInhoud(
            BrpMapperUtil.mapBrpNationaliteitCode(
                historie.getPersoonNationaliteit().getNationaliteit(),
                onderzoekMapper.bepaalOnderzoek(historie.getPersoonNationaliteit().getID(), ElementEnum.PERSOON_NATIONALITEIT_NATIONALITEITCODE, true)),
            BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                historie.getRedenVerkrijging(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, true)),
            BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(
                historie.getRedenVerlies(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_REDENVERLIESCODE, true)),
            BrpMapperUtil.mapBrpBoolean(
                historie.getIndicatieBijhoudingBeeindigd(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getMigratieDatumEindeBijhouding(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING, true)),
            BrpMapperUtil.mapBrpString(
                historie.getMigratieRedenOpnameNationaliteit(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT, true)),
            BrpMapperUtil.mapBrpString(
                historie.getMigratieRedenBeeindigenNationaliteit(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT, true)));
    }

    @Override
    protected Integer getStapelNummer(final HisPersoonNationaliteitModel historie) {
        return historie.getPersoonNationaliteit().getID();
    }
}
