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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteit;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonNationaliteitHistorie;

import org.springframework.stereotype.Component;

/**
 * Map nationaliteiten van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpNationaliteitMapper {

    @Inject
    private BrpNationaliteitInhoudMapper mapper;

    /**
     * Map de BRP database nationaliteiten naar BRP conversiemodel nationaliteiten.
     * 
     * @param persoonNationaliteitSet
     *            set aan nationaliteiten
     * @return lijst van nationaliteiten
     */
    public List<BrpStapel<BrpNationaliteitInhoud>> map(final Set<PersoonNationaliteit> persoonNationaliteitSet) {
        if (persoonNationaliteitSet == null) {
            return null;
        }

        final List<BrpStapel<BrpNationaliteitInhoud>> result = new ArrayList<BrpStapel<BrpNationaliteitInhoud>>();

        for (final PersoonNationaliteit persoonNationaliteit : persoonNationaliteitSet) {
            result.add(mapper.map(persoonNationaliteit.getPersoonNationaliteitHistorieSet()));
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpNationaliteitInhoudMapper extends
            BrpMapper<PersoonNationaliteitHistorie, BrpNationaliteitInhoud> {

        @Override
        protected BrpNationaliteitInhoud mapInhoud(final PersoonNationaliteitHistorie historie) {
            // @formatter:off
            return new BrpNationaliteitInhoud(
                    BrpMapperUtil.mapBrpNationaliteitCode(historie.getPersoonNationaliteit().getNationaliteit()), 
                    BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                            historie.getRedenVerkrijgingNLNationaliteit()), 
                    BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(historie.getRedenVerliesNLNationaliteit())
                );
            // @formatter:on
        }
    }
}
