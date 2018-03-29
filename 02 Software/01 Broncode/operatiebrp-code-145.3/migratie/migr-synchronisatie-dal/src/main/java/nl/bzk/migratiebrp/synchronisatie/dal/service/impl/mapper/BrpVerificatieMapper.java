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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerificatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map verificaties van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpVerificatieMapper {

    private final BrpVerificatieInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper inhoud mapper
     */
    @Inject
    public BrpVerificatieMapper(final BrpVerificatieInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map BRP database verificaties naar BRP conversiemodel verificaties.
     * @param persoonVerificatieSet database reisdocumenten
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return conversiemodel reisdocumenten
     */
    public List<BrpStapel<BrpVerificatieInhoud>> map(final Set<PersoonVerificatie> persoonVerificatieSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonVerificatieSet == null) {
            return null;
        }

        final List<BrpStapel<BrpVerificatieInhoud>> result = new ArrayList<>();

        for (final PersoonVerificatie persoonVerificatie : persoonVerificatieSet) {
            final BrpStapel<BrpVerificatieInhoud> brpVerificatieStapel =
                    mapper.map(persoonVerificatie.getPersoonVerificatieHistorieSet(), brpOnderzoekMapper);
            if (brpVerificatieStapel != null) {
                result.add(brpVerificatieStapel);
            }
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpVerificatieInhoudMapper extends AbstractBrpMapper<PersoonVerificatieHistorie, BrpVerificatieInhoud> {

        @Override
        protected BrpVerificatieInhoud mapInhoud(final PersoonVerificatieHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
            final BrpPartijCode partij =
                    BrpMapperUtil.mapBrpPartijCode(
                            historie.getPersoonVerificatie().getPartij(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERIFICATIE_PARTIJCODE, true));
            final BrpString soort =
                    BrpMapperUtil.mapBrpString(
                            historie.getPersoonVerificatie().getSoortVerificatie(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERIFICATIE_SOORT, true));
            final BrpDatum datum =
                    BrpMapperUtil.mapDatum(historie.getDatum(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_VERIFICATIE_DATUM, true));

            return new BrpVerificatieInhoud(partij, soort, datum);
        }
    }

}
