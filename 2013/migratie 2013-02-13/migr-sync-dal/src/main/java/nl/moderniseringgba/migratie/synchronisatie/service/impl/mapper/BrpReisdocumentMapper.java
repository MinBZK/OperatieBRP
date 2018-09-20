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
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonReisdocumentHistorie;

import org.springframework.stereotype.Component;

/**
 * Map reisdocumenten van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpReisdocumentMapper {

    @Inject
    private BrpReisdocumentInhoudMapper mapper;

    /**
     * Map BRP database reisdocumenten naar BRP conversiemodel reisdocumenten.
     * 
     * @param persoonReisdocumentSet
     *            database reisdocumenten
     * @return conversiemodel reisdocumenten
     */
    public List<BrpStapel<BrpReisdocumentInhoud>> map(final Set<PersoonReisdocument> persoonReisdocumentSet) {
        if (persoonReisdocumentSet == null) {
            return null;
        }

        final List<BrpStapel<BrpReisdocumentInhoud>> result = new ArrayList<BrpStapel<BrpReisdocumentInhoud>>();

        for (final PersoonReisdocument persoonReisdocument : persoonReisdocumentSet) {
            result.add(mapper.map(persoonReisdocument.getPersoonReisdocumentHistorieSet()));
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpReisdocumentInhoudMapper extends
            BrpMapper<PersoonReisdocumentHistorie, BrpReisdocumentInhoud> {

        @Override
        protected BrpReisdocumentInhoud mapInhoud(final PersoonReisdocumentHistorie historie) {
            // @formatter:off
            return new BrpReisdocumentInhoud(
                    BrpMapperUtil.mapBrpReisdocumentSoort(
                            historie.getPersoonReisdocument().getSoortNederlandsReisdocument()), 
                    historie.getNummer(), 
                    BrpMapperUtil.mapDatum(historie.getDatumIngang()),
                    BrpMapperUtil.mapDatum(historie.getDatumUitgifte()), 
                    BrpMapperUtil.mapBrpAutoriteitVanAfgifte(historie.getAutoriteitVanAfgifteReisdocument()), 
                    BrpMapperUtil.mapDatum(historie.getDatumVoorzieneEindeGeldigheid()), 
                    BrpMapperUtil.mapDatum(historie.getDatumInhoudingVermissing()), 
                    BrpMapperUtil.mapBrpReisdocumentRedenOntbreken(historie.getRedenVervallenReisdocument()), 
                    BrpMapperUtil.mapInteger(historie.getLengteHouder())
                );
            // @formatter:on
        }
    }

}
