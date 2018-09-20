/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAdellijkeTitelCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPredicaatCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map geslachtnaamcomponent van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpGeslachtsnaamcomponentMapper {

    @Inject
    private BrpGeslachtsnaamcomponentInhoudMapper mapper;

    /**
     * Map de geslachtsnaam componenten.
     * 
     * @param persoonGeslachtsnaamcomponentSet
     *            set aan geslachtnaam componenten
     * @param brpOnderzoekMapper
     *            De mapper voor onderzoeken
     * @return lijst geslachtsnaam componenten
     */
    public List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> map(
        final Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet,
        final BrpOnderzoekMapper brpOnderzoekMapper)
    {
        if (persoonGeslachtsnaamcomponentSet == null) {
            return null;
        }

        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> result = new ArrayList<>();

        for (final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : persoonGeslachtsnaamcomponentSet) {
            result.add(mapper.map(persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet(), brpOnderzoekMapper));
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpGeslachtsnaamcomponentInhoudMapper extends
            AbstractBrpMapper<PersoonGeslachtsnaamcomponentHistorie, BrpGeslachtsnaamcomponentInhoud>
    {

        @Override
        protected BrpGeslachtsnaamcomponentInhoud mapInhoud(
            final PersoonGeslachtsnaamcomponentHistorie historie,
            final BrpOnderzoekMapper brpOnderzoekMapper)
        {
            final BrpString voorvoegsel =
                    BrpString.wrap(
                        historie.getVoorvoegsel(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL, true));
            final BrpCharacter scheidingsteken =
                    BrpCharacter.wrap(
                        historie.getScheidingsteken(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN, true));
            final BrpString stam =
                    BrpString.wrap(historie.getStam(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM, true));
            final BrpPredicaatCode predicaat =
                    BrpMapperUtil.mapBrpPredicaatCode(
                        historie.getPredicaat(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE, true));
            final BrpAdellijkeTitelCode adellijkeTitel;
            adellijkeTitel =
                    BrpMapperUtil.mapBrpAdellijkeTitelCode(
                        historie.getAdellijkeTitel(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE, true));
            final BrpInteger volgnummer =
                    BrpInteger.wrap(
                        historie.getPersoonGeslachtsnaamcomponent().getVolgnummer(),
                        brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER, true));

            return new BrpGeslachtsnaamcomponentInhoud(voorvoegsel, scheidingsteken, stam, predicaat, adellijkeTitel, volgnummer);
        }
    }
}
