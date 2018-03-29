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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonBuitenlandsPersoonsnummerHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map buitenlands persoonsnummers van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpBuitenlandsPersoonsnummerMapper {

    private final BrpBuitenlandsPersoonsnummerInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper inhoud mapper
     */
    @Inject
    public BrpBuitenlandsPersoonsnummerMapper(final BrpBuitenlandsPersoonsnummerInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map de BRP database nationaliteiten naar BRP conversiemodel nationaliteiten.
     * @param persoonBuitenlandsPersoonsnummerSet set aan buitenlands persoonsnummers
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return lijst van buitenlands persoonsnummer
     */
    public List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> map(
            final Set<PersoonBuitenlandsPersoonsnummer> persoonBuitenlandsPersoonsnummerSet,
            final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonBuitenlandsPersoonsnummerSet == null) {
            return null;
        }

        final List<BrpStapel<BrpBuitenlandsPersoonsnummerInhoud>> result = new ArrayList<>();

        for (final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer : persoonBuitenlandsPersoonsnummerSet) {
            final BrpStapel<BrpBuitenlandsPersoonsnummerInhoud> brpBuitenlandsPersoonsnummerStapel =
                    mapper.map(persoonBuitenlandsPersoonsnummer.getPersoonBuitenlandsPersoonsnummerHistorieSet(), brpOnderzoekMapper);
            if (brpBuitenlandsPersoonsnummerStapel != null) {
                result.add(brpBuitenlandsPersoonsnummerStapel);
            }
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpBuitenlandsPersoonsnummerInhoudMapper
            extends AbstractBrpMapper<PersoonBuitenlandsPersoonsnummerHistorie, BrpBuitenlandsPersoonsnummerInhoud> {
        @Override
        protected BrpBuitenlandsPersoonsnummerInhoud mapInhoud(
                final PersoonBuitenlandsPersoonsnummerHistorie historie,
                final BrpOnderzoekMapper brpOnderzoekMapper) {

            final PersoonBuitenlandsPersoonsnummer persoonBuitenlandsPersoonsnummer = historie.getPersoonBuitenlandsPersoonsnummer();
            Lo3Onderzoek onderzoekOpNummer =
                    brpOnderzoekMapper.bepaalOnderzoek(persoonBuitenlandsPersoonsnummer, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_NUMMER, true);
            onderzoekOpNummer =
                    onderzoekOpNummer == null ? null : brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD, true);
            Lo3Onderzoek onderzoekOpAutoriteitVanAfgifte =
                    brpOnderzoekMapper.bepaalOnderzoek(
                            persoonBuitenlandsPersoonsnummer,
                            Element.PERSOON_BUITENLANDSPERSOONSNUMMER_AUTORITEITVANAFGIFTECODE,
                            true);
            onderzoekOpAutoriteitVanAfgifte =
                    onderzoekOpAutoriteitVanAfgifte == null ? null
                            : brpOnderzoekMapper.bepaalOnderzoek(
                                    historie,
                                    Element.PERSOON_BUITENLANDSPERSOONSNUMMER_STANDAARD,
                                    true);
            final BrpString nummer = BrpMapperUtil.mapBrpString(persoonBuitenlandsPersoonsnummer.getNummer(), onderzoekOpNummer);
            final BrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer autoriteitVanAfgifteBuitenlandsPersoonsnummer =
                    BrpMapperUtil.mapBrpAutoriteitVanAfgifteBuitenlandsPersoonsnummer(
                            persoonBuitenlandsPersoonsnummer.getAutoriteitAfgifteBuitenlandsPersoonsnummer(),
                            onderzoekOpAutoriteitVanAfgifte);
            return new BrpBuitenlandsPersoonsnummerInhoud(nummer, autoriteitVanAfgifteBuitenlandsPersoonsnummer);
        }
    }
}
