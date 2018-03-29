/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.springframework.stereotype.Component;

/**
 * Builder voor grotere groepen. Merk op dat we het hier hebben over Lo3 (gegevens)groepen en niet over BRP groepen (a
 * la categorie).
 */
@Component
public class GgoLo3GegevensgroepenBuilder {
    @Inject
    private GgoLo3ValueConvert lo3ValueConvert;

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param aNummer Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param burgerServiceNummer Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep01(final Map<String, String> voorkomen, final Lo3String aNummer, final Lo3String burgerServiceNummer) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0110, aNummer);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0120, burgerServiceNummer);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param voornamen Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param adellijkeTitelPredikaatCode Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param voorvoegselGeslachtsnaam Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param geslachtsnaam Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep02(
            final Map<String, String> voorkomen,
            final Lo3String voornamen,
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final Lo3String voorvoegselGeslachtsnaam,
            final Lo3String geslachtsnaam) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0210, voornamen);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0220, adellijkeTitelPredikaatCode);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0230, voorvoegselGeslachtsnaam);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0240, geslachtsnaam);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param geboortedatum Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param geboorteGemeenteCode Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param geboorteLandCode Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep03(
            final Map<String, String> voorkomen,
            final Lo3Datum geboortedatum,
            final Lo3GemeenteCode geboorteGemeenteCode,
            final Lo3LandCode geboorteLandCode) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0310, geboortedatum);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0320, geboorteGemeenteCode);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0330, geboorteLandCode);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param geslachtsaanduiding Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep04(final Map<String, String> voorkomen, final Lo3Geslachtsaanduiding geslachtsaanduiding) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0410, geslachtsaanduiding);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param datumSluitingHuwelijkOfAangaanGp Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param lo3GemeenteCode Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param lo3LandCode Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep06(
            final Map<String, String> voorkomen,
            final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
            final Lo3GemeenteCode lo3GemeenteCode,
            final Lo3LandCode lo3LandCode) {
        if (datumSluitingHuwelijkOfAangaanGp != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0610, datumSluitingHuwelijkOfAangaanGp);
        }
        if (lo3GemeenteCode != null && lo3GemeenteCode.getWaarde() != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0620, lo3GemeenteCode);
        }
        if (lo3LandCode != null && lo3LandCode.getWaarde() != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0630, lo3LandCode);
        }
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param datumOntbindingHuwelijkOfGp Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param gemeenteCodeOntbindingHuwelijkOfGp Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param landCodeOntbindingHuwelijkOfGp Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param redenOntbindingHuwelijkOfGpCode Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep07(
            final Map<String, String> voorkomen,
            final Lo3Datum datumOntbindingHuwelijkOfGp,
            final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
            final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0710, datumOntbindingHuwelijkOfGp);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0720, gemeenteCodeOntbindingHuwelijkOfGp);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0730, landCodeOntbindingHuwelijkOfGp);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0740, redenOntbindingHuwelijkOfGpCode);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param gemeenteInschrijving Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param datumInschrijving Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep09(final Map<String, String> voorkomen, final Lo3GemeenteCode gemeenteInschrijving, final Lo3Datum datumInschrijving) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0910, gemeenteInschrijving);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_0920, datumInschrijving);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param functieAdres Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param gemeenteDeel Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param aanvangAdreshouding Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep10(
            final Map<String, String> voorkomen,
            final Lo3FunctieAdres functieAdres,
            final Lo3String gemeenteDeel,
            final Lo3Datum aanvangAdreshouding) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1010, functieAdres);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1020, gemeenteDeel);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1030, aanvangAdreshouding);
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud Verzameling elementen die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep11(final Map<String, String> voorkomen, final Lo3VerblijfplaatsInhoud inhoud) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1110, inhoud.getStraatnaam());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1115, inhoud.getNaamOpenbareRuimte());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1120, inhoud.getHuisnummer());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1130, inhoud.getHuisletter());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1140, inhoud.getHuisnummertoevoeging());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1150, inhoud.getAanduidingHuisnummer());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1160, inhoud.getPostcode());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1170, inhoud.getWoonplaatsnaam());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1180, inhoud.getIdentificatiecodeVerblijfplaats());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1190, inhoud.getIdentificatiecodeNummeraanduiding());
    }

    /**
     * Voeg Lo3 groep01 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param landAdresBuitenland Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param datumVertrekUitNederland Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param adresBuitenland1 Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param adresBuitenland2 Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param adresBuitenland3 Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep13(
            final Map<String, String> voorkomen,
            final Lo3LandCode landAdresBuitenland,
            final Lo3Datum datumVertrekUitNederland,
            final Lo3String adresBuitenland1,
            final Lo3String adresBuitenland2,
            final Lo3String adresBuitenland3) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1310, landAdresBuitenland);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1320, datumVertrekUitNederland);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1330, adresBuitenland1);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1340, adresBuitenland2);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1350, adresBuitenland3);
    }

    /**
     * Voeg Lo3 groep14 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param landVanwaarIngeschreven Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param vestigingInNederland Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep14(final Map<String, String> voorkomen, final Lo3LandCode landVanwaarIngeschreven, final Lo3Datum vestigingInNederland) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1410, landVanwaarIngeschreven);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_1420, vestigingInNederland);
    }

    /**
     * Voeg Lo3 groep35 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param inhoud Elementen die eventueel toegevoegd moeten worden aan voorkomen.
     */
    public final void addGroep35(final Map<String, String> voorkomen, final Lo3ReisdocumentInhoud inhoud) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3510, inhoud.getSoortNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3520, inhoud.getNummerNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3530, inhoud.getDatumUitgifteNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3540, inhoud.getAutoriteitVanAfgifteNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3550, inhoud.getDatumEindeGeldigheidNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3560, inhoud.getDatumInhoudingVermissingNederlandsReisdocument());
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_3570, inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument());
    }

    /**
     * Voeg Lo3 groep67 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param datumOpschortingBijhouding Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param redenOpschortingBijhoudingCode Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep67(
            final Map<String, String> voorkomen,
            final Lo3Datum datumOpschortingBijhouding,
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_6710, datumOpschortingBijhouding);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_6720, redenOpschortingBijhoudingCode);
    }

    /**
     * Voeg Lo3 groep71 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param datumVerificatie Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param omschrijvingVerificatie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep71(final Map<String, String> voorkomen, final Lo3Datum datumVerificatie, final Lo3String omschrijvingVerificatie) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_7110, datumVerificatie);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_7120, omschrijvingVerificatie);
    }

    /**
     * Voeg Lo3 groep80 toe aan het voorkomen.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param versienummer Element die eventueel toegevoegd moet worden aan voorkomen.
     * @param datumtijdstempel Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addGroep80(final Map<String, String> voorkomen, final Lo3Integer versienummer, final Lo3Datumtijdstempel datumtijdstempel) {
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8010, versienummer);
        lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8020, datumtijdstempel);
    }

    /**
     * Voeg Lo3 Historie elementen toe aan het voorkomen. Groep 84, 85 en 86.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param historie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addHistorie(final Map<String, String> voorkomen, final Lo3Historie historie) {
        if (historie != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8410, historie.getIndicatieOnjuist());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8510, historie.getIngangsdatumGeldigheid());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8610, historie.getDatumVanOpneming());
        }
    }

    /**
     * Voeg Lo3 documentatie elementen toe aan het voorkomen. Groep 81 en 82.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param documentatie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addDocumentatie(final Map<String, String> voorkomen, final Lo3Documentatie documentatie) {
        if (documentatie != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8110, documentatie.getGemeenteAkte());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8120, documentatie.getNummerAkte());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8210, documentatie.getGemeenteDocument());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8220, documentatie.getDatumDocument());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8230, documentatie.getBeschrijvingDocument());
        }
    }

    /**
     * Voeg Lo3 documentatie rni elementen toe aan het voorkomen. Groep 88. Aparte methodes ivm volgorde elementnummers.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param documentatie Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addRni(final Map<String, String> voorkomen, final Lo3Documentatie documentatie) {
        if (documentatie != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8810, documentatie.getRniDeelnemerCode());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8820, documentatie.getOmschrijvingVerdrag());
        }
    }

    /**
     * Voeg Lo3 Onderzoek elementen toe aan het voorkomen. Groep 83.
     * @param voorkomen Voorkomen waar eventuele elementen aan toegevoegd moeten worden.
     * @param onderzoek Element die eventueel toegevoegd moet worden aan voorkomen.
     */
    public final void addOnderzoek(final Map<String, String> voorkomen, final Lo3Onderzoek onderzoek) {
        if (onderzoek != null) {
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8310, onderzoek.getAanduidingGegevensInOnderzoek());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8320, onderzoek.getDatumIngangOnderzoek());
            lo3ValueConvert.addElement(voorkomen, Lo3ElementEnum.ELEMENT_8330, onderzoek.getDatumEindeOnderzoek());
        }
    }
}
