/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingOfVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Aangever;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AangifteAdreshouding;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdellijkeTitelPredikaat;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AutoriteittypeVanAfgifteReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Gemeente;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.LandOfGebied;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Nationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Plaats;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Rechtsgrond;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenBeeindigingRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOntbindingHuwelijkPartnerschap;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpnameNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerkrijgingNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenVerliesNLNationaliteit;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenWijzigingVerblijf;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNlReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortPartij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Verblijfsrecht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.VoorvoegselConversie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AanduidingOuder;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Bijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BurgerzakenModule;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.CategorieAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EenheidSelectieInterval;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Geslachtsaanduiding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Koppelvlak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3CategorieMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3Severity;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Naamgebruik;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereBijhoudingsaard;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.RedenBlokkering;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBerichtVrijBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElement;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMigratie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerantwoordingCategorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingswijze;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.GrantedAuthorityMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.OrderMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.PageMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.SortMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.UserDetailsMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdCodeNaamEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdCodeNaamOmschrijvingEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamDatumEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamOmschrijvingEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.AbstractIdNaamSoortBerichtEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.autaut.AbstractProtocolleringsniveauMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ber.AbstractSoortBerichtMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractAanduidingInhoudingVermissingReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractAangifteAdreshoudingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractAdellijkeTitelPredikaatMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractLo3RubriekMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractRNIDeelnemerMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractRedenBeeindigenNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractRedenOntbindingHuwelijkPartnerschapMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractRedenOpnameNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractRedenOpschortingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractSoortNLReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.AbstractVoorvoegselMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractAanduidingInhoudingOfVermissingReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractAangeverMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractAutoriteittypeVanAfgifteReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractElementMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractGemeenteMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractKoppelvlakMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractLandGebiedMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractPartijMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractPlaatsMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractPredicaatMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractRechtsgrondMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractRedenBeeindigingRelatieMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractRedenNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractRedenWijzigingVerblijfMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractRegelMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortAdministratieveHandelingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortDocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortIndicatieMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortMeldingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortNederlandsReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractSoortPersoonMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AbstractVerblijfsrechtMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.migblok.BlokkeringMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.verconv.AbstractLo3SoortMeldingMixIn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * JSON Object Mapper configuratie voor Brp objecten.
 */
@Component
public final class BrpJsonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    private static final Map<Class<?>, Class<?>> APPLICATIE_MIXINS = ImmutableMap.of(
            Page.class, PageMixIn.class,
            Sort.class, SortMixIn.class,
            Order.class, OrderMixIn.class,
            UserDetails.class, UserDetailsMixIn.class,
            GrantedAuthority.class, GrantedAuthorityMixIn.class
    );

    private static final Map<Class<?>, Class<?>> AUTAUT_MIXINS = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(EenheidSelectieInterval.class, AbstractIdNaamEnumMixIn.class)
            .put(EffectAfnemerindicaties.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(LeverwijzeSelectie.class, AbstractIdNaamEnumMixIn.class)
            .put(Protocolleringsniveau.class, AbstractProtocolleringsniveauMixIn.class)
            .put(SoortDienst.class, AbstractIdNaamEnumMixIn.class)
            .put(SoortSelectie.class, AbstractIdNaamEnumMixIn.class)
            .put(Stelsel.class, AbstractIdNaamEnumMixIn.class)
            .build();

    private static final Map<Class<?>, Class<?>> BER_MIXINS = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(BijhoudingResultaat.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(BijhoudingSituatie.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(Richting.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(SoortBericht.class, AbstractSoortBerichtMixIn.class)
            .put(SoortSynchronisatie.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(Verantwoording.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(VerantwoordingCategorie.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(VerwerkingsResultaat.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(Verwerkingswijze.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .build();

    private static final Map<Class<?>, Class<?>> BEH_MIXINS = ImmutableMap.of(
            SoortBerichtVrijBericht.class, AbstractIdNaamSoortBerichtEnumMixIn.class);

    private static final Map<Class<?>, Class<?>> CONV_MIXINS = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(AanduidingInhoudingVermissingReisdocument.class, AbstractAanduidingInhoudingVermissingReisdocumentMixIn.class)
            .put(AangifteAdreshouding.class, AbstractAangifteAdreshoudingMixIn.class)
            .put(AdellijkeTitelPredikaat.class, AbstractAdellijkeTitelPredikaatMixIn.class)
            .put(RedenBeeindigingNationaliteit.class, AbstractRedenBeeindigenNationaliteitMixIn.class)
            .put(RedenOntbindingHuwelijkPartnerschap.class, AbstractRedenOntbindingHuwelijkPartnerschapMixIn.class)
            .put(RedenOpnameNationaliteit.class, AbstractRedenOpnameNationaliteitMixIn.class)
            .put(RedenOpschorting.class, AbstractRedenOpschortingMixIn.class)
            .put(RNIDeelnemer.class, AbstractRNIDeelnemerMixIn.class)
            .put(SoortNlReisdocument.class, AbstractSoortNLReisdocumentMixIn.class)
            .put(VoorvoegselConversie.class, AbstractVoorvoegselMixIn.class)
            .put(Lo3Rubriek.class, AbstractLo3RubriekMixIn.class)
            .build();

    private static final Map<Class<?>, Class<?>> KERN_MIXINS = ImmutableMap.<Class<?>, Class<?>>builder()
            .put(AanduidingInhoudingOfVermissingReisdocument.class, AbstractAanduidingInhoudingOfVermissingReisdocumentMixIn.class)
            .put(Aangever.class, AbstractAangeverMixIn.class)
            .put(AdellijkeTitel.class, AbstractPredicaatMixIn.class)
            .put(AutoriteittypeVanAfgifteReisdocument.class, AbstractAutoriteittypeVanAfgifteReisdocumentMixIn.class)
            .put(Bijhoudingsaard.class, AbstractIdCodeNaamOmschrijvingEnumMixIn.class)
            .put(BurgerzakenModule.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(CategorieAdministratieveHandeling.class, AbstractIdNaamEnumMixIn.class)
            .put(Element.class, AbstractElementMixIn.class)
            .put(Gemeente.class, AbstractGemeenteMixIn.class)
            .put(Geslachtsaanduiding.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(HistorieVorm.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(Koppelvlak.class, AbstractKoppelvlakMixIn.class)
            .put(LandOfGebied.class, AbstractLandGebiedMixIn.class)
            .put(Naamgebruik.class, AbstractIdCodeNaamOmschrijvingEnumMixIn.class)
            .put(NadereBijhoudingsaard.class, AbstractIdCodeNaamOmschrijvingEnumMixIn.class)
            .put(Nationaliteit.class, AbstractNationaliteitMixIn.class)
            .put(Partij.class, AbstractPartijMixIn.class)
            .put(Plaats.class, AbstractPlaatsMixIn.class)
            .put(Predicaat.class, AbstractPredicaatMixIn.class)
            .put(Rechtsgrond.class, AbstractRechtsgrondMixIn.class)
            .put(RedenBeeindigingRelatie.class, AbstractRedenBeeindigingRelatieMixIn.class)
            .put(RedenVerkrijgingNLNationaliteit.class, AbstractRedenNationaliteitMixIn.class)
            .put(RedenVerliesNLNationaliteit.class, AbstractRedenNationaliteitMixIn.class)
            .put(RedenWijzigingVerblijf.class, AbstractRedenWijzigingVerblijfMixIn.class)
            .put(Regel.class, AbstractRegelMixIn.class)
            .put(Rol.class, AbstractIdNaamDatumEnumMixIn.class)
            .put(SoortActie.class, AbstractIdNaamEnumMixIn.class)
            .put(SoortAdres.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(SoortAdministratieveHandeling.class, AbstractSoortAdministratieveHandelingMixIn.class)
            .put(SoortBetrokkenheid.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(SoortDocument.class, AbstractSoortDocumentMixIn.class)
            .put(SoortElement.class, AbstractIdNaamEnumMixIn.class)
            .put(SoortElementAutorisatie.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(SoortIndicatie.class, AbstractSoortIndicatieMixIn.class)
            .put(SoortMelding.class, AbstractSoortMeldingMixIn.class)
            .put(SoortMigratie.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(SoortNederlandsReisdocument.class, AbstractSoortNederlandsReisdocumentMixIn.class)
            .put(SoortPartij.class, AbstractIdNaamDatumEnumMixIn.class)
            .put(SoortVrijBericht.class, AbstractIdNaamDatumEnumMixIn.class)
            .put(SoortPersoon.class, AbstractSoortPersoonMixIn.class)
            .put(SoortRelatie.class, AbstractIdCodeNaamEnumMixIn.class)
            .put(StatusOnderzoek.class, AbstractIdNaamOmschrijvingEnumMixIn.class)
            .put(Verblijfsrecht.class, AbstractVerblijfsrechtMixIn.class)
            .build();

    private static final Map<Class<?>, Class<?>> MIGBLOK_MIXINS = ImmutableMap.of(
            Blokkering.class, BlokkeringMixIn.class,
            RedenBlokkering.class, AbstractIdNaamEnumMixIn.class
    );

    private static final Map<Class<?>, Class<?>> VERCONV_MIXINS = ImmutableMap.of(
            Lo3BerichtenBron.class, AbstractIdNaamEnumMixIn.class,
            Lo3CategorieMelding.class, AbstractIdNaamEnumMixIn.class,
            Lo3Severity.class, AbstractIdNaamEnumMixIn.class,
            AanduidingOuder.class, AbstractEnumMixIn.class,
            Lo3SoortMelding.class, AbstractLo3SoortMeldingMixIn.class
    );

    /**
     * Constructor.
     * @param modules Modules
     */
    @Inject
    public BrpJsonObjectMapper(final Module[] modules) {
        configureerMapper();

        ImmutableMap.<Class<?>, Class<?>>builder()
                .putAll(APPLICATIE_MIXINS)
                .putAll(AUTAUT_MIXINS)
                .putAll(BER_MIXINS)
                .putAll(BEH_MIXINS)
                .putAll(CONV_MIXINS)
                .putAll(KERN_MIXINS)
                .putAll(MIGBLOK_MIXINS)
                .putAll(VERCONV_MIXINS)
                .build()
                .forEach(this::addMixIn);

        // custom serializers / deserializer
        registerModules(modules);
    }

    private void configureerMapper() {
        // Configuratie
        this.disable(MapperFeature.AUTO_DETECT_CREATORS);
        this.disable(MapperFeature.AUTO_DETECT_FIELDS);
        this.disable(MapperFeature.AUTO_DETECT_GETTERS);
        this.disable(MapperFeature.AUTO_DETECT_IS_GETTERS);
        this.disable(MapperFeature.AUTO_DETECT_SETTERS);

        // Default velden niet als JSON exposen (expliciet annoteren!)
        setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NONE);
        this.enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        this.enable(MapperFeature.CAN_OVERRIDE_ACCESS_MODIFIERS);

        // serialization
        this.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        this.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);

        // deserialization
        this.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }
}
