/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpVoornaamInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaam;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonVoornaamHistorie;

import org.springframework.stereotype.Component;

/**
 * Map voornaam van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpVoornaamMapper {

    @Inject
    private BrpVoornaamInhoudMapper mapper;

    /**
     * Map de database voornamen naar conversiemodel voornamen.
     * 
     * @param persoonVoornaamSet
     *            database voornamen
     * @return conversiemodel voornamen
     */
    public List<BrpStapel<BrpVoornaamInhoud>> map(final Set<PersoonVoornaam> persoonVoornaamSet) {
        if (persoonVoornaamSet == null) {
            return null;
        }

        final List<BrpStapel<BrpVoornaamInhoud>> result = new ArrayList<BrpStapel<BrpVoornaamInhoud>>();

        for (final PersoonVoornaam persoonVoornaam : persoonVoornaamSet) {
            result.add(mapper.map(persoonVoornaam.getPersoonVoornaamHistorieSet()));
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpVoornaamInhoudMapper extends BrpMapper<PersoonVoornaamHistorie, BrpVoornaamInhoud> {

        @Override
        protected BrpVoornaamInhoud mapInhoud(final PersoonVoornaamHistorie historie) {
            // @formatter:off
            return new BrpVoornaamInhoud(
                    historie.getNaam(),
                    historie.getPersoonVoornaam().getVolgnummer()
                );
            // @formatter:on
        }
    }

}
