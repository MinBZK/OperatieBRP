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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponent;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonGeslachtsnaamcomponentHistorie;

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
     * @return lijst geslachtsnaam componenten
     */
    public List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> map(
            final Set<PersoonGeslachtsnaamcomponent> persoonGeslachtsnaamcomponentSet) {
        if (persoonGeslachtsnaamcomponentSet == null) {
            return null;
        }

        final List<BrpStapel<BrpGeslachtsnaamcomponentInhoud>> result =
                new ArrayList<BrpStapel<BrpGeslachtsnaamcomponentInhoud>>();

        for (final PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent : persoonGeslachtsnaamcomponentSet) {
            result.add(mapper.map(persoonGeslachtsnaamcomponent.getPersoonGeslachtsnaamcomponentHistorieSet()));
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpGeslachtsnaamcomponentInhoudMapper extends
            BrpMapper<PersoonGeslachtsnaamcomponentHistorie, BrpGeslachtsnaamcomponentInhoud> {

        @Override
        protected BrpGeslachtsnaamcomponentInhoud mapInhoud(final PersoonGeslachtsnaamcomponentHistorie historie) {
            // @formatter:off
            return new BrpGeslachtsnaamcomponentInhoud(
                    historie.getVoorvoegsel(), 
                    historie.getScheidingsteken(), 
                    historie.getNaam(), 
                    BrpMapperUtil.mapBrpPredikaatCode(historie.getPredikaat()), 
                    BrpMapperUtil.mapBrpAdellijkeTitelCode(historie.getAdellijkeTitel()), 
                    historie.getPersoonGeslachtsnaamcomponent().getVolgnummer()
                );
            // @formatter:on
        }
    }
}
