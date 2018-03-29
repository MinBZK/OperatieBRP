/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonIndicatieHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.AbstractBrpIndicatieGroepInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBehandeldAlsNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnverwerktDocumentAanwezigIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVastgesteldNietNederlanderIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerstrekkingsbeperkingIndicatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Een mapper voor een BRP indicatie die een van de Indicatie stapels uit het migratie model mapped op PersoonIndicatie.
 * @param <T> het type indicatie die hier gemapped wordt
 */
public final class PersoonIndicatieMapper<T extends AbstractBrpIndicatieGroepInhoud>
        extends AbstractHistorieMapperStrategie<T, PersoonIndicatieHistorie, PersoonIndicatie> {

    private static final Map<Class<? extends AbstractBrpIndicatieGroepInhoud>,
            Function<Class<? extends AbstractBrpIndicatieGroepInhoud>, SoortIndicatie>> soortIndicatieMap =
            new HashMap<>();

    private final Map<SoortIndicatie, BiConsumer<T, PersoonIndicatieHistorie>> soortIndicatieOnderzoekMapperMap = new EnumMap<>(SoortIndicatie.class);

    static {
        soortIndicatieMap.put(BrpBehandeldAlsNederlanderIndicatieInhoud.class, value -> SoortIndicatie.BEHANDELD_ALS_NEDERLANDER);
        soortIndicatieMap.put(BrpSignaleringMetBetrekkingTotVerstrekkenReisdocumentInhoud.class,
                value -> SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT);
        soortIndicatieMap.put(BrpDerdeHeeftGezagIndicatieInhoud.class, value -> SoortIndicatie.DERDE_HEEFT_GEZAG);
        soortIndicatieMap.put(BrpOnderCurateleIndicatieInhoud.class, value -> SoortIndicatie.ONDER_CURATELE);
        soortIndicatieMap.put(BrpStaatloosIndicatieInhoud.class, value -> SoortIndicatie.STAATLOOS);
        soortIndicatieMap.put(BrpVerstrekkingsbeperkingIndicatieInhoud.class, value -> SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING);
        soortIndicatieMap.put(BrpVastgesteldNietNederlanderIndicatieInhoud.class, value -> SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER);
        soortIndicatieMap.put(BrpBijzondereVerblijfsrechtelijkePositieIndicatieInhoud.class, value -> SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE);
        soortIndicatieMap.put(BrpOnverwerktDocumentAanwezigIndicatieInhoud.class, value -> SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG);
    }

    /**
     * Maakt een PersoonIndicatieMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonIndicatieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);

        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_BEHANDELDALSNEDERLANDER_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.SIGNALERING_MET_BETREKKING_TOT_VERSTREKKEN_REISDOCUMENT,
                (groepInhoud, result) -> getOnderzoekMapper().mapOnderzoek(
                        result,
                        groepInhoud.getIndicatie(),
                        Element.PERSOON_INDICATIE_SIGNALERINGMETBETREKKINGTOTVERSTREKKENREISDOCUMENT_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.DERDE_HEEFT_GEZAG, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_DERDEHEEFTGEZAG_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.ONDER_CURATELE,
                (groepInhoud, result) -> getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_ONDERCURATELE_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.STAATLOOS,
                (groepInhoud, result) -> getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_STAATLOOS_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.VOLLEDIGE_VERSTREKKINGSBEPERKING, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_VOLLEDIGEVERSTREKKINGSBEPERKING_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.VASTGESTELD_NIET_NEDERLANDER, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_VASTGESTELDNIETNEDERLANDER_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_BIJZONDEREVERBLIJFSRECHTELIJKEPOSITIE_WAARDE));
        soortIndicatieOnderzoekMapperMap.put(SoortIndicatie.ONVERWERKT_DOCUMENT_AANWEZIG, (groepInhoud, result) -> getOnderzoekMapper()
                .mapOnderzoek(result, groepInhoud.getIndicatie(), Element.PERSOON_INDICATIE_ONVERWERKTDOCUMENTAANWEZIG_WAARDE));

    }

    /**
     * Converteer AbstractBrpIndicatieGroepInhoud class naar SortIndicate.
     * @param brpIndicatieClass class
     * @return SortIndicatie
     */
    static SoortIndicatie mapBrpClassOpIndicatie(final Class<? extends AbstractBrpIndicatieGroepInhoud> brpIndicatieClass) {
        return soortIndicatieMap.getOrDefault(brpIndicatieClass, PersoonIndicatieMapper::onbekendeSoort).apply(brpIndicatieClass);
    }

    private static SoortIndicatie onbekendeSoort(final Class<? extends AbstractBrpIndicatieGroepInhoud> brpIndicatieClass) {
        throw new IllegalStateException("Onbekende indicatie stapel: " + brpIndicatieClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonIndicatieHistorie historie, final PersoonIndicatie entiteit) {
        entiteit.addPersoonIndicatieHistorie(historie);
        entiteit.setMigratieRedenBeeindigenNationaliteit(historie.getMigratieRedenBeeindigenNationaliteit());
        entiteit.setMigratieRedenOpnameNationaliteit(historie.getMigratieRedenOpnameNationaliteit());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonIndicatieHistorie mapHistorischeGroep(final T groepInhoud, final PersoonIndicatie entiteit) {
        final PersoonIndicatieHistorie result = new PersoonIndicatieHistorie(entiteit, Boolean.TRUE.equals(groepInhoud.heeftIndicatie()));

        final BrpString redenVerkrijgingNlCode = groepInhoud.getMigratieRedenOpnameNationaliteit();
        if (redenVerkrijgingNlCode != null) {
            result.setMigratieRedenOpnameNationaliteit(redenVerkrijgingNlCode.getWaarde());
        }
        final BrpString redenVerliesNlcode = groepInhoud.getMigratieRedenBeeindigingNationaliteit();
        if (redenVerliesNlcode != null) {
            result.setMigratieRedenBeeindigenNationaliteit(redenVerliesNlcode.getWaarde());
        }
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getMigratieRedenOpnameNationaliteit(),
                Element.PERSOON_INDICATIE_MIGRATIEREDENOPNAMENATIONALITEIT);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getMigratieRedenBeeindigingNationaliteit(),
                Element.PERSOON_INDICATIE_MIGRATIEREDENBEEINDIGENNATIONALITEIT);

        soortIndicatieOnderzoekMapperMap.getOrDefault(entiteit.getSoortIndicatie(), this::verwerkOnbekendeSoortIndicatie).accept(groepInhoud, result);

        return result;
    }

    private void verwerkOnbekendeSoortIndicatie(final T groepInhoud, final PersoonIndicatieHistorie result) {
        throw new IllegalStateException("Onbekend soort indicatie");
    }
}
