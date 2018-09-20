/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.configuratie.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.AdministratieveHandelingModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.BerichtModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.DienstbundelLO3RubriekModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.LeveringsautorisatieModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.PersoonModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.modules.VersieModule;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.GrantedAuthorityMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.OrderMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.PageMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.SortMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.spring.UserDetailsMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.BaseEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ShortEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ShortStamgegevenMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StamgegevenMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StringEnumMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.StringStamgegevenMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.ber.SoortBerichtMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.brm.RegelSoortBerichtMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.brm.RegelsituatieMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieAanduidingInhoudingVermissingReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieAangifteAdreshoudingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieAdellijkeTitelPredikaatMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieRNIDeelnemerMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieRedenBeeindigenNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschapMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieRedenOpnameNationaliteitMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieRedenOpschortingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieSoortNLReisdocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.conv.ConversieVoorvoegselMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.AdellijkeTitelMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.ElementMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.GemeenteMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.LandGebiedMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.PartijMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.PartijRolMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.PredicaatMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.RechtsgrondMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.RegelMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.SoortAdministratieveHandelingMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.SoortDocumentMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.SoortIndicatieMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.SoortPartijMixIn;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.kern.VoorvoegselMixin;
import nl.bzk.brp.beheer.webapp.configuratie.json.stamgegevens.verconv.LO3SoortMeldingMixIn;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.AanduidingMedium;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Functie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortAutorisatiebesluit;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortBevoegdheid;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingssituatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Richting;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortVraagAntwoord;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.RegelSoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.Regeleffect;
import nl.bzk.brp.model.algemeen.stamgegeven.brm.SoortRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BurgerzakenModule;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Naamgebruik;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElementAutorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartijOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRechtsgrond;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.migblok.RedenBlokkering;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3BerichtenBron;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3CategorieMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3Severity;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuder;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMelding;
import nl.bzk.brp.model.beheer.brm.Regelsituatie;
import nl.bzk.brp.model.beheer.conv.ConversieAanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.beheer.conv.ConversieAangifteAdreshouding;
import nl.bzk.brp.model.beheer.conv.ConversieAdellijkeTitelPredikaat;
import nl.bzk.brp.model.beheer.conv.ConversieLO3Rubriek;
import nl.bzk.brp.model.beheer.conv.ConversieRNIDeelnemer;
import nl.bzk.brp.model.beheer.conv.ConversieRedenBeeindigenNationaliteit;
import nl.bzk.brp.model.beheer.conv.ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap;
import nl.bzk.brp.model.beheer.conv.ConversieRedenOpnameNationaliteit;
import nl.bzk.brp.model.beheer.conv.ConversieRedenOpschorting;
import nl.bzk.brp.model.beheer.conv.ConversieSoortNLReisdocument;
import nl.bzk.brp.model.beheer.conv.ConversieVoorvoegsel;
import nl.bzk.brp.model.beheer.kern.AanduidingInhoudingVermissingReisdocument;
import nl.bzk.brp.model.beheer.kern.AanduidingVerblijfsrecht;
import nl.bzk.brp.model.beheer.kern.Aangever;
import nl.bzk.brp.model.beheer.kern.AdellijkeTitel;
import nl.bzk.brp.model.beheer.kern.AutoriteittypeVanAfgifteReisdocument;
import nl.bzk.brp.model.beheer.kern.Gemeente;
import nl.bzk.brp.model.beheer.kern.LandGebied;
import nl.bzk.brp.model.beheer.kern.Nationaliteit;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.PartijRol;
import nl.bzk.brp.model.beheer.kern.Plaats;
import nl.bzk.brp.model.beheer.kern.Predicaat;
import nl.bzk.brp.model.beheer.kern.Rechtsgrond;
import nl.bzk.brp.model.beheer.kern.RedenEindeRelatie;
import nl.bzk.brp.model.beheer.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.beheer.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.beheer.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.beheer.kern.SoortDocument;
import nl.bzk.brp.model.beheer.kern.SoortNederlandsReisdocument;
import nl.bzk.brp.model.beheer.kern.SoortPartij;
import nl.bzk.brp.model.beheer.kern.Voorvoegsel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * JSON Object Mapper configuratie voor Brp objecten.
 */
public final class BrpJsonObjectMapper extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public BrpJsonObjectMapper() {
        configureerMapper();

        toevoegenApplicatieMixIns();
        toevoegenAutAutMixIns();
        toevoegenBerMixIns();
        toevoegenBrmMixIns();
        toevoegenConvMixIns();
        toevoegenKernMixInsDeelEen();
        toevoegenKernMixInsDeelTwee();
        toevoegenMigblokMixIns();
        toevoegenVerconvMixIns();

        // Beperken serializatie van persoon voor zoeken
//        addMixInAnnotations(AbstractPersoonModel.class, AbstractPersoonModelMixIn.class);

        // custom serializers / deserializer
        registerModule(new LeveringsautorisatieModule());
        registerModule(new DienstbundelLO3RubriekModule());
        registerModule(new AdministratieveHandelingModule());
        registerModule(new BerichtModule());
        registerModule(new VersieModule());
        registerModule(new PersoonModule());
    }

    private void toevoegenApplicatieMixIns() {
        // Serializeren van Page
        addMixInAnnotations(Page.class, PageMixIn.class);
        addMixInAnnotations(Sort.class, SortMixIn.class);
        addMixInAnnotations(Order.class, OrderMixIn.class);

        // Serializeren van UserDetails
        addMixInAnnotations(UserDetails.class, UserDetailsMixIn.class);
        addMixInAnnotations(GrantedAuthority.class, GrantedAuthorityMixIn.class);
    }

    private void toevoegenAutAutMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (autaut)
        addMixInAnnotations(AanduidingMedium.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortDienst.class, StringEnumMixIn.class);
        addMixInAnnotations(EffectAfnemerindicaties.class, StringEnumMixIn.class);
        addMixInAnnotations(Functie.class, StringEnumMixIn.class);
        addMixInAnnotations(Stelsel.class, StringEnumMixIn.class);
        addMixInAnnotations(Protocolleringsniveau.class, ShortEnumMixIn.class);
        addMixInAnnotations(SoortAutorisatiebesluit.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortBevoegdheid.class, StringEnumMixIn.class);
    }

    private void toevoegenBrmMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (autaut)
        addMixInAnnotations(Regelsituatie.class, RegelsituatieMixIn.class);
        addMixInAnnotations(RegelSoortBericht.class, RegelSoortBerichtMixIn.class);
        addMixInAnnotations(Regeleffect.class, BaseEnumMixIn.class);
        addMixInAnnotations(SoortRegel.class, BaseEnumMixIn.class);
    }

    private void toevoegenBerMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (ber)
        addMixInAnnotations(Bijhoudingsresultaat.class, StringEnumMixIn.class);
        addMixInAnnotations(Bijhoudingssituatie.class, StringEnumMixIn.class);
        addMixInAnnotations(Historievorm.class, StringEnumMixIn.class);
        addMixInAnnotations(Richting.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortBericht.class, SoortBerichtMixIn.class);
        addMixInAnnotations(SoortMelding.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortSynchronisatie.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortVraagAntwoord.class, StringEnumMixIn.class);
        addMixInAnnotations(Verwerkingsresultaat.class, StringEnumMixIn.class);
        addMixInAnnotations(Verwerkingswijze.class, StringEnumMixIn.class);
    }

    private void toevoegenConvMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (conv)
        addMixInAnnotations(ConversieAanduidingInhoudingVermissingReisdocument.class, ConversieAanduidingInhoudingVermissingReisdocumentMixIn.class);
        addMixInAnnotations(ConversieAangifteAdreshouding.class, ConversieAangifteAdreshoudingMixIn.class);
        addMixInAnnotations(ConversieAdellijkeTitelPredikaat.class, ConversieAdellijkeTitelPredikaatMixIn.class);
        addMixInAnnotations(ConversieRedenBeeindigenNationaliteit.class, ConversieRedenBeeindigenNationaliteitMixIn.class);
        addMixInAnnotations(
                ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschap.class, ConversieRedenOntbindingHuwelijkGeregistreerdPartnerschapMixIn.class);
        addMixInAnnotations(ConversieRedenOpnameNationaliteit.class, ConversieRedenOpnameNationaliteitMixIn.class);
        addMixInAnnotations(ConversieRedenOpschorting.class, ConversieRedenOpschortingMixIn.class);
        addMixInAnnotations(ConversieRNIDeelnemer.class, ConversieRNIDeelnemerMixIn.class);
        addMixInAnnotations(ConversieSoortNLReisdocument.class, ConversieSoortNLReisdocumentMixIn.class);
        addMixInAnnotations(ConversieVoorvoegsel.class, ConversieVoorvoegselMixIn.class);
        addMixInAnnotations(ConversieLO3Rubriek.class, StamgegevenMixIn.class);
    }

    private void toevoegenKernMixInsDeelEen() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (kern)
        addMixInAnnotations(AanduidingInhoudingVermissingReisdocument.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(AanduidingVerblijfsrecht.class, ShortStamgegevenMixIn.class);
        addMixInAnnotations(Aangever.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(AdellijkeTitel.class, AdellijkeTitelMixIn.class);
        addMixInAnnotations(AutoriteittypeVanAfgifteReisdocument.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(Bijhoudingsaard.class, StringEnumMixIn.class);
        addMixInAnnotations(BurgerzakenModule.class, StringEnumMixIn.class);
        addMixInAnnotations(CategorieAdministratieveHandeling.class, StringEnumMixIn.class);
        addMixInAnnotations(Element.class, ElementMixIn.class);
        addMixInAnnotations(FunctieAdres.class, StringEnumMixIn.class);
        addMixInAnnotations(Gemeente.class, GemeenteMixIn.class);
        addMixInAnnotations(Geslachtsaanduiding.class, StringEnumMixIn.class);
        addMixInAnnotations(LandGebied.class, LandGebiedMixIn.class);
        addMixInAnnotations(Naamgebruik.class, StringEnumMixIn.class);
        addMixInAnnotations(NadereBijhoudingsaard.class, StringEnumMixIn.class);
        addMixInAnnotations(Nationaliteit.class, ShortStamgegevenMixIn.class);
        addMixInAnnotations(Partij.class, PartijMixIn.class);
        addMixInAnnotations(PartijRol.class, PartijRolMixIn.class);
        addMixInAnnotations(Plaats.class, ShortStamgegevenMixIn.class);
        addMixInAnnotations(Predicaat.class, PredicaatMixIn.class);
        addMixInAnnotations(Rechtsgrond.class, RechtsgrondMixIn.class);
        addMixInAnnotations(RedenEindeRelatie.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(RedenVerkrijgingNLNationaliteit.class, ShortStamgegevenMixIn.class);
        addMixInAnnotations(RedenVerliesNLNationaliteit.class, ShortStamgegevenMixIn.class);
        addMixInAnnotations(RedenWijzigingVerblijf.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(Regel.class, RegelMixIn.class);
        addMixInAnnotations(SoortPartij.class, SoortPartijMixIn.class);
        addMixInAnnotations(Voorvoegsel.class, VoorvoegselMixin.class);
    }

    private void toevoegenKernMixInsDeelTwee() {
        addMixInAnnotations(Rol.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortActie.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortAdministratieveHandeling.class, SoortAdministratieveHandelingMixIn.class);
        addMixInAnnotations(SoortBetrokkenheid.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortDocument.class, SoortDocumentMixIn.class);
        addMixInAnnotations(SoortElement.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortElementAutorisatie.class, BaseEnumMixIn.class);
        addMixInAnnotations(SoortIndicatie.class, SoortIndicatieMixIn.class);
        addMixInAnnotations(SoortMigratie.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortNederlandsReisdocument.class, StringStamgegevenMixIn.class);
        addMixInAnnotations(SoortPartijOnderzoek.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortPersoon.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortPersoonOnderzoek.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortRechtsgrond.class, StringEnumMixIn.class);
        addMixInAnnotations(SoortRelatie.class, StringEnumMixIn.class);
        addMixInAnnotations(StatusOnderzoek.class, StringEnumMixIn.class);
        addMixInAnnotations(StatusTerugmelding.class, StringEnumMixIn.class);
    }

    private void toevoegenMigblokMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (migblok)
        addMixInAnnotations(RedenBlokkering.class, StringEnumMixIn.class);
    }

    private void toevoegenVerconvMixIns() {
        // Uitbreiden serializatie van stamgegevens voor onderhoud (verconv)
        addMixInAnnotations(LO3BerichtenBron.class, StringEnumMixIn.class);
        addMixInAnnotations(LO3CategorieMelding.class, StringEnumMixIn.class);
        addMixInAnnotations(LO3Severity.class, StringEnumMixIn.class);
        addMixInAnnotations(LO3SoortAanduidingOuder.class, BaseEnumMixIn.class);
        addMixInAnnotations(LO3SoortMelding.class, LO3SoortMeldingMixIn.class);
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
