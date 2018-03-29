/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonMigratieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortMigratieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map immigratie van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpMigratieMapper extends AbstractBrpMapper<PersoonMigratieHistorie, BrpMigratieInhoud> {

    @Override
    protected BrpMigratieInhoud mapInhoud(final PersoonMigratieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpSoortMigratieCode soortMigratieCode =
                new BrpSoortMigratieCode(historie.getSoortMigratie().getCode(), brpOnderzoekMapper.bepaalOnderzoek(
                        historie,
                        Element.PERSOON_MIGRATIE_SOORTCODE,
                        true));
        final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode;
        final Lo3Onderzoek onderzoekRedenWijzigingVerblijfCode =
                brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE, true);
        redenWijzigingVerblijfCode =
                BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(historie.getRedenWijzigingMigratie(), onderzoekRedenWijzigingVerblijfCode);
        final BrpAangeverCode aangeverCode;
        aangeverCode =
                BrpMapperUtil.mapBrpAangeverCode(
                        historie.getAangeverMigratie(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_AANGEVERCODE, true));
        final BrpLandOfGebiedCode landOfGebied;
        landOfGebied =
                BrpMapperUtil.mapBrpLandOfGebiedCode(
                        historie.getLandOfGebied(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_LANDGEBIEDCODE, true));
        final BrpString buitenlandsAdresRegel1 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel1(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1, true));
        final BrpString buitenlandsAdresRegel2 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel2(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2, true));
        final BrpString buitenlandsAdresRegel3 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel3(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3, true));
        final BrpString buitenlandsAdresRegel4 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel4(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4, true));
        final BrpString buitenlandsAdresRegel5 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel5(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5, true));
        final BrpString buitenlandsAdresRegel6 =
                BrpString.wrap(
                        historie.getBuitenlandsAdresRegel6(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6, true));

        return new BrpMigratieInhoud(
                soortMigratieCode,
                redenWijzigingVerblijfCode,
                aangeverCode,
                landOfGebied,
                buitenlandsAdresRegel1,
                buitenlandsAdresRegel2,
                buitenlandsAdresRegel3,
                buitenlandsAdresRegel4,
                buitenlandsAdresRegel5,
                buitenlandsAdresRegel6);
    }

}
