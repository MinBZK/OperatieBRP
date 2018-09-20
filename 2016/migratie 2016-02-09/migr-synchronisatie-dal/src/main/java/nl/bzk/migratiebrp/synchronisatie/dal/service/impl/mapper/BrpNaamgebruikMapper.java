/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonNaamgebruikHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map naamgebruik van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpNaamgebruikMapper extends AbstractBrpMapper<PersoonNaamgebruikHistorie, BrpNaamgebruikInhoud> {

    @Override
    protected BrpNaamgebruikInhoud mapInhoud(final PersoonNaamgebruikHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
        final BrpNaamgebruikCode naamgebruik =
                BrpMapperUtil.mapBrpNaamgebruikCode(
                    historie.getNaamgebruik(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_CODE, true));
        final BrpBoolean indAfgeleid =
                BrpMapperUtil.mapBrpBoolean(
                    historie.getIndicatieNaamgebruikAfgeleid(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID, true));
        final BrpPredicaatCode predicaat;
        predicaat =
                BrpMapperUtil.mapBrpPredicaatCode(
                    historie.getPredicaat(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE, true));
        final BrpAdellijkeTitelCode adellijkeTitel;
        adellijkeTitel =
                BrpMapperUtil.mapBrpAdellijkeTitelCode(
                    historie.getAdellijkeTitel(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE, true));
        final BrpString voornamen =
                BrpString.wrap(
                    historie.getVoornamenNaamgebruik(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_VOORNAMEN, true));
        final BrpString voorvoegsel =
                BrpString.wrap(
                    historie.getVoorvoegselNaamgebruik(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL, true));
        final BrpCharacter scheidingsteken =
                BrpCharacter.wrap(
                    historie.getScheidingstekenNaamgebruik(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN, true));
        final BrpString geslachtsnaamstam =
                BrpString.wrap(
                    historie.getGeslachtsnaamstamNaamgebruik(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM, true));

        return new BrpNaamgebruikInhoud(naamgebruik, indAfgeleid, predicaat, adellijkeTitel, voornamen, voorvoegsel, scheidingsteken, geslachtsnaamstam);
    }
}
