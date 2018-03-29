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
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingInhoudingOfVermissingReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpReisdocumentAutoriteitVanAfgifteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortNederlandsReisdocumentCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map reisdocumenten van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpReisdocumentMapper {

    private final BrpReisdocumentInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper inhoud mapper
     */
    @Inject
    public BrpReisdocumentMapper(final BrpReisdocumentInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map BRP database reisdocumenten naar BRP conversiemodel reisdocumenten.
     * @param persoonReisdocumentSet database reisdocumenten
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return conversiemodel reisdocumenten
     */
    public List<BrpStapel<BrpReisdocumentInhoud>> map(final Set<PersoonReisdocument> persoonReisdocumentSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonReisdocumentSet == null) {
            return null;
        }

        final List<BrpStapel<BrpReisdocumentInhoud>> result = new ArrayList<>();

        for (final PersoonReisdocument persoonReisdocument : persoonReisdocumentSet) {
            final BrpStapel<BrpReisdocumentInhoud> brpReisdocumentStapel =
                    mapper.map(persoonReisdocument.getPersoonReisdocumentHistorieSet(), brpOnderzoekMapper);
            if (brpReisdocumentStapel != null) {
                result.add(brpReisdocumentStapel);
            }
        }

        return result;
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpReisdocumentInhoudMapper extends AbstractBrpMapper<PersoonReisdocumentHistorie, BrpReisdocumentInhoud> {

        @Override
        protected BrpReisdocumentInhoud mapInhoud(final PersoonReisdocumentHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
            final BrpSoortNederlandsReisdocumentCode soort;
            soort =
                    BrpMapperUtil.mapBrpReisdocumentSoort(
                            historie.getPersoonReisdocument().getSoortNederlandsReisdocument(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie.getPersoonReisdocument(), Element.PERSOON_REISDOCUMENT_SOORTCODE, true));
            final BrpString nummer;
            nummer = BrpString.wrap(historie.getNummer(), brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_NUMMER, true));

            final BrpDatum datumIngangDocument;
            datumIngangDocument =
                    BrpMapperUtil.mapDatum(
                            historie.getDatumIngangDocument(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT, true));
            final BrpDatum datumUitgifte;
            datumUitgifte =
                    BrpMapperUtil.mapDatum(
                            historie.getDatumUitgifte(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE, true));
            final BrpReisdocumentAutoriteitVanAfgifteCode autoriteitVanAfgifte;
            autoriteitVanAfgifte =
                    BrpMapperUtil.mapBrpAutoriteitVanAfgifte(
                            historie.getAutoriteitVanAfgifte(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE, true));
            final BrpDatum datumEindeDocument;
            datumEindeDocument =
                    BrpMapperUtil.mapDatum(
                            historie.getDatumEindeDocument(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT, true));
            final BrpDatum datumInhoudingOfVermissing;
            datumInhoudingOfVermissing =
                    BrpMapperUtil.mapDatum(
                            historie.getDatumInhoudingOfVermissing(),
                            brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING, true));
            final BrpAanduidingInhoudingOfVermissingReisdocumentCode aanduidingInhoudingOfVermissing;
            final AanduidingInhoudingOfVermissingReisdocument aanduidingInhoudingOfVermissingReisdocument;
            aanduidingInhoudingOfVermissingReisdocument = historie.getAanduidingInhoudingOfVermissingReisdocument();
            final Lo3Onderzoek onderzoekAanduidingInhoudingOfVermissing =
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE, true);
            aanduidingInhoudingOfVermissing =
                    BrpMapperUtil.mapBrpAanduidingInhoudingOfVermissingReisdocument(
                            aanduidingInhoudingOfVermissingReisdocument,
                            onderzoekAanduidingInhoudingOfVermissing);

            return new BrpReisdocumentInhoud(
                    soort,
                    nummer,
                    datumIngangDocument,
                    datumUitgifte,
                    autoriteitVanAfgifte,
                    datumEindeDocument,
                    datumInhoudingOfVermissing,
                    aanduidingInhoudingOfVermissing);
        }
    }

}
