/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;

import org.springframework.stereotype.Component;

/**
 * Mapt de migratie.
 */
@Component
public final class MigratieMapper extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonMigratieModel, BrpMigratieInhoud> {
    /**
     * Constructor.
     */
    public MigratieMapper() {
        super(ElementEnum.PERSOON_MIGRATIE_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_MIGRATIE_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_MIGRATIE_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_MIGRATIE_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonMigratieModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        return persoonHisVolledig.getPersoonMigratieHistorie();
    }

    @Override
    public BrpMigratieInhoud mapInhoud(final HisPersoonMigratieModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpMigratieInhoud(
            BrpMapperUtil.mapBrpSoortMigratieCode(
                historie.getSoortMigratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_SOORTCODE, true)),
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(
                historie.getRedenWijzigingMigratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_REDENWIJZIGINGCODE, true)),
            BrpMapperUtil.mapBrpAangeverCode(
                historie.getAangeverMigratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_AANGEVERCODE, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                historie.getLandGebiedMigratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_LANDGEBIEDCODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel1Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel2Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel3Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel4Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel5Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel6Migratie(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6, true)));
    }
}
