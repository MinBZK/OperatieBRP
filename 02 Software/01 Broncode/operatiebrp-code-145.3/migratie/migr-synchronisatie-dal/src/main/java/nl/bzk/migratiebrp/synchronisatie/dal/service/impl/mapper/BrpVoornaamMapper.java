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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaam;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVoornaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map voornaam van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpVoornaamMapper {

    private final BrpVoornaamInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper inhoud mapper
     */
    @Inject
    public BrpVoornaamMapper(final BrpVoornaamInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map de database voornamen naar conversiemodel voornamen.
     * @param persoonVoornaamSet database voornamen
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return conversiemodel voornamen
     */
    public List<BrpStapel<BrpVoornaamInhoud>> map(final Set<PersoonVoornaam> persoonVoornaamSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonVoornaamSet == null) {
            return null;
        }

        final List<BrpStapel<BrpVoornaamInhoud>> result = new ArrayList<>();

        for (final PersoonVoornaam persoonVoornaam : persoonVoornaamSet) {
            final BrpStapel<BrpVoornaamInhoud> stapel = mapper.map(persoonVoornaam.getPersoonVoornaamHistorieSet(), brpOnderzoekMapper);
            if (stapel != null) {
                result.add(stapel);
            }
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpVoornaamInhoudMapper extends AbstractBrpMapper<PersoonVoornaamHistorie, BrpVoornaamInhoud> {

        @Override
        protected BrpVoornaamInhoud mapInhoud(final PersoonVoornaamHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
            final BrpString naam = BrpString.wrap(historie.getNaam(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VOORNAAM_NAAM, true));
            final BrpInteger volgnummer =
                    BrpInteger.wrap(
                            historie.getPersoonVoornaam().getVolgnummer(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VOORNAAM_VOLGNUMMER, true));

            return new BrpVoornaamInhoud(naam, volgnummer);

        }
    }

}
