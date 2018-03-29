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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonNationaliteitHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerkrijgingNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenVerliesNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map nationaliteiten van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpNationaliteitMapper {

    private final BrpNationaliteitInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper inhoud mapper
     */
    @Inject
    public BrpNationaliteitMapper(final BrpNationaliteitInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map de BRP database nationaliteiten naar BRP conversiemodel nationaliteiten.
     * @param persoonNationaliteitSet set aan nationaliteiten
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return lijst van nationaliteiten
     */
    public List<BrpStapel<BrpNationaliteitInhoud>> map(final Set<PersoonNationaliteit> persoonNationaliteitSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonNationaliteitSet == null) {
            return null;
        }

        final List<BrpStapel<BrpNationaliteitInhoud>> result = new ArrayList<>();

        for (final PersoonNationaliteit persoonNationaliteit : persoonNationaliteitSet) {
            final BrpStapel<BrpNationaliteitInhoud> brpNationaliteitStapel =
                    mapper.map(persoonNationaliteit.getPersoonNationaliteitHistorieSet(), brpOnderzoekMapper);
            if (brpNationaliteitStapel != null) {
                result.add(brpNationaliteitStapel);
            }
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpNationaliteitInhoudMapper extends AbstractBrpMapper<PersoonNationaliteitHistorie, BrpNationaliteitInhoud> {

        @Override
        protected BrpNationaliteitInhoud mapInhoud(final PersoonNationaliteitHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {

            Lo3Onderzoek onderzoekOpCode =
                    brpOnderzoekMapper.bepaalOnderzoek(historie.getPersoonNationaliteit(), Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE, true);
            onderzoekOpCode = onderzoekOpCode == null ? null : brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_STANDAARD, true);

            final BrpNationaliteitCode code = BrpMapperUtil.mapBrpNationaliteitCode(historie.getPersoonNationaliteit().getNationaliteit(), onderzoekOpCode);
            final BrpRedenVerkrijgingNederlandschapCode redenVerkrijging =
                    BrpMapperUtil.mapBrpRedenVerkrijgingNederlandschapCode(
                            historie.getRedenVerkrijgingNLNationaliteit(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE, true));
            final BrpRedenVerliesNederlandschapCode redenVerlies =
                    BrpMapperUtil.mapBrpRedenVerliesNederlanderschapCode(
                            historie.getRedenVerliesNLNationaliteit(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE, true));
            final BrpBoolean eindeBijhouding =
                    BrpMapperUtil.mapBrpBoolean(
                            historie.getIndicatieBijhoudingBeeindigd(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD, true));
            final BrpDatum migratieDatum =
                    BrpMapperUtil.mapDatum(
                            historie.getMigratieDatumEindeBijhouding(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING, true));
            final BrpString migratieRedenOpnameNationaliteit =
                    BrpMapperUtil.mapBrpString(
                            historie.getMigratieRedenOpnameNationaliteit(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT, true));
            final BrpString migratieRedenBeeindigingNationaliteit =
                    BrpMapperUtil.mapBrpString(
                            historie.getMigratieRedenBeeindigenNationaliteit(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT, true));
            return new BrpNationaliteitInhoud(
                    code,
                    redenVerkrijging,
                    redenVerlies,
                    eindeBijhouding,
                    migratieDatum,
                    migratieRedenOpnameNationaliteit,
                    migratieRedenBeeindigingNationaliteit);
        }
    }
}
