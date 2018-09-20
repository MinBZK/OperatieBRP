/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSamengesteldeNaamInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map samengestelde naam van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpSamengesteldeNaamMapper extends AbstractBrpMapper<PersoonSamengesteldeNaamHistorie, BrpSamengesteldeNaamInhoud> {

    @Override
    protected BrpSamengesteldeNaamInhoud mapInhoud(final PersoonSamengesteldeNaamHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpPredicaatCode predicaat =
                BrpMapperUtil.mapBrpPredicaatCode(
                    historie.getPredicaat(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE, true));
        final BrpString voornamen =
                BrpString.wrap(historie.getVoornamen(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN, true));
        final BrpString voorvoegsel =
                BrpString.wrap(
                    historie.getVoorvoegsel(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL, true));
        final BrpCharacter scheidingsteken =
                BrpCharacter.wrap(
                    historie.getScheidingsteken(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN, true));
        final BrpAdellijkeTitelCode adellijkeTitel;
        adellijkeTitel =
                BrpMapperUtil.mapBrpAdellijkeTitelCode(
                    historie.getAdellijkeTitel(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE, true));
        final BrpString geslachtsnaamstam =
                BrpString.wrap(
                    historie.getGeslachtsnaamstam(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM, true));
        final BrpBoolean indNamenreeks =
                BrpBoolean.wrap(
                    historie.getIndicatieNamenreeks(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS, true));
        final BrpBoolean indAfgeleid =
                BrpBoolean.wrap(
                    historie.getIndicatieAfgeleid(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID, true));

        return new BrpSamengesteldeNaamInhoud(
            predicaat,
            voornamen,
            voorvoegsel,
            scheidingsteken,
            adellijkeTitel,
            geslachtsnaamstam,
            indNamenreeks,
            indAfgeleid);
    }
}
