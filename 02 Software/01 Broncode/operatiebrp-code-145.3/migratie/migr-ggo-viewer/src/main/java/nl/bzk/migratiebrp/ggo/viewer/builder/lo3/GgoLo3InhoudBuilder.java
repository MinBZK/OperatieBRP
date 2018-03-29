/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.builder.lo3;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.inject.Inject;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import org.springframework.stereotype.Component;

/**
 * De builder die de Lo3Persoonslijst omzet naar het viewer model.
 */
@Component
public class GgoLo3InhoudBuilder {
    @Inject
    private GgoLo3GegevensgroepenBuilder gegevensgroepenBuilder;
    @Inject
    private GgoLo3ValueConvert lo3ValueConvert;

    /**
     * Maak een persoonvoorkomen van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createPersoonInhoud(
            final Lo3PersoonInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> persoon = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep01(persoon, inhoud.getANummer(), inhoud.getBurgerservicenummer());
            gegevensgroepenBuilder.addGroep02(
                    persoon,
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam());
            gegevensgroepenBuilder.addGroep03(persoon, inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode());
            gegevensgroepenBuilder.addGroep04(persoon, inhoud.getGeslachtsaanduiding());
            if (inhoud.getVorigANummer() != null) {
                lo3ValueConvert.addElement(persoon, Lo3ElementEnum.ELEMENT_2010, inhoud.getVorigANummer());
            }
            if (inhoud.getVolgendANummer() != null) {
                lo3ValueConvert.addElement(persoon, Lo3ElementEnum.ELEMENT_2020, inhoud.getVolgendANummer());
            }
            if (inhoud.getAanduidingNaamgebruikCode() != null) {
                lo3ValueConvert.addElement(persoon, Lo3ElementEnum.ELEMENT_6110, inhoud.getAanduidingNaamgebruikCode());
            }
        }
        gegevensgroepenBuilder.addDocumentatie(persoon, documentatie);
        gegevensgroepenBuilder.addOnderzoek(persoon, onderzoek);
        gegevensgroepenBuilder.addHistorie(persoon, historie);
        gegevensgroepenBuilder.addRni(persoon, documentatie);
        return persoon;
    }

    /**
     * Maak een oudervoorkomen van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createOuderInhoud(
            final Lo3OuderInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> ouder = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep01(ouder, inhoud.getaNummer(), inhoud.getBurgerservicenummer());
            gegevensgroepenBuilder.addGroep02(
                    ouder,
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam());
            gegevensgroepenBuilder.addGroep03(ouder, inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode());
            gegevensgroepenBuilder.addGroep04(ouder, inhoud.getGeslachtsaanduiding());
            if (inhoud.getFamilierechtelijkeBetrekking() != null) {
                lo3ValueConvert.addElement(ouder, Lo3ElementEnum.ELEMENT_6210, inhoud.getFamilierechtelijkeBetrekking());
            }

            gegevensgroepenBuilder.addDocumentatie(ouder, documentatie);
            gegevensgroepenBuilder.addOnderzoek(ouder, onderzoek);
            gegevensgroepenBuilder.addHistorie(ouder, historie);
        }
        return ouder;
    }

    /**
     * Maak een nationaliteitvoorkomen van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek.
     * @return Map met de keys en values.
     */
    public final Map<String, String> createNationaliteitInhoud(
            final Lo3NationaliteitInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> nationaliteit = new LinkedHashMap<>();
        if (inhoud != null) {
            if (heeftWaarde(inhoud.getNationaliteitCode())) {
                lo3ValueConvert.addElement(nationaliteit, Lo3ElementEnum.ELEMENT_0510, inhoud.getNationaliteitCode());
            }
            if (heeftWaarde(inhoud.getRedenVerkrijgingNederlandschapCode())) {
                lo3ValueConvert.addElement(nationaliteit, Lo3ElementEnum.ELEMENT_6310, inhoud.getRedenVerkrijgingNederlandschapCode());
            }
            if (heeftWaarde(inhoud.getRedenVerliesNederlandschapCode())) {
                lo3ValueConvert.addElement(nationaliteit, Lo3ElementEnum.ELEMENT_6410, inhoud.getRedenVerliesNederlandschapCode());
            }
            if (heeftWaarde(inhoud.getAanduidingBijzonderNederlandschap())) {
                lo3ValueConvert.addElement(nationaliteit, Lo3ElementEnum.ELEMENT_6510, inhoud.getAanduidingBijzonderNederlandschap());
            }
            if (heeftWaarde(inhoud.getBuitenlandsPersoonsnummer())) {
                lo3ValueConvert.addElement(nationaliteit, Lo3ElementEnum.ELEMENT_7310, inhoud.getBuitenlandsPersoonsnummer());
            }

            gegevensgroepenBuilder.addDocumentatie(nationaliteit, documentatie);
            gegevensgroepenBuilder.addOnderzoek(nationaliteit, onderzoek);
            gegevensgroepenBuilder.addHistorie(nationaliteit, historie);
            gegevensgroepenBuilder.addRni(nationaliteit, documentatie);
        }
        return nationaliteit;
    }

    private boolean heeftWaarde(final Lo3Element lo3Element) {
        return lo3Element != null && lo3Element.getWaarde() != null;
    }

    /**
     * Maak een huwelijkinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createHuwelijkInhoud(
            final Lo3HuwelijkOfGpInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> huwelijk = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep01(huwelijk, inhoud.getaNummer(), inhoud.getBurgerservicenummer());
            gegevensgroepenBuilder.addGroep02(
                    huwelijk,
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam());
            gegevensgroepenBuilder.addGroep03(huwelijk, inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode());
            gegevensgroepenBuilder.addGroep04(huwelijk, inhoud.getGeslachtsaanduiding());
            gegevensgroepenBuilder.addGroep06(
                    huwelijk,
                    inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                    inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                    inhoud.getLandCodeSluitingHuwelijkOfAangaanGp());
            gegevensgroepenBuilder.addGroep07(
                    huwelijk,
                    inhoud.getDatumOntbindingHuwelijkOfGp(),
                    inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                    inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                    inhoud.getRedenOntbindingHuwelijkOfGpCode());

            if (inhoud.getSoortVerbintenis() != null) {
                lo3ValueConvert.addElement(huwelijk, Lo3ElementEnum.ELEMENT_1510, inhoud.getSoortVerbintenis());
            }

            gegevensgroepenBuilder.addDocumentatie(huwelijk, documentatie);
            gegevensgroepenBuilder.addOnderzoek(huwelijk, onderzoek);
            gegevensgroepenBuilder.addHistorie(huwelijk, historie);
        }
        return huwelijk;
    }

    /**
     * Maak een overlijdeninhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createOverlijdenInhoud(
            final Lo3OverlijdenInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> overlijden = new LinkedHashMap<>();
        if (inhoud != null) {
            if (inhoud.getDatum() != null) {
                lo3ValueConvert.addElement(overlijden, Lo3ElementEnum.ELEMENT_0810, inhoud.getDatum());
            }
            if (inhoud.getGemeenteCode() != null) {
                lo3ValueConvert.addElement(overlijden, Lo3ElementEnum.ELEMENT_0820, inhoud.getGemeenteCode());
            }
            if (inhoud.getLandCode() != null) {
                lo3ValueConvert.addElement(overlijden, Lo3ElementEnum.ELEMENT_0830, inhoud.getLandCode());
            }

            gegevensgroepenBuilder.addDocumentatie(overlijden, documentatie);
            gegevensgroepenBuilder.addOnderzoek(overlijden, onderzoek);
            gegevensgroepenBuilder.addHistorie(overlijden, historie);
            gegevensgroepenBuilder.addRni(overlijden, documentatie);
        }
        return overlijden;
    }

    /**
     * Maak een inschrijvinginhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @return Map met de keys en values.
     */
    public final Map<String, String> createInschrijvingInhoud(
            final Lo3InschrijvingInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie) {
        final Map<String, String> inschrijving = new LinkedHashMap<>();
        if (inhoud != null) {
            if (inhoud.getDatumIngangBlokkering() != null) {
                lo3ValueConvert.addElement(inschrijving, Lo3ElementEnum.ELEMENT_6620, inhoud.getDatumIngangBlokkering());
            }
            gegevensgroepenBuilder.addGroep67(inschrijving, inhoud.getDatumOpschortingBijhouding(), inhoud.getRedenOpschortingBijhoudingCode());
            if (inhoud.getDatumEersteInschrijving() != null) {
                lo3ValueConvert.addElement(inschrijving, Lo3ElementEnum.ELEMENT_6810, inhoud.getDatumEersteInschrijving());
            }
            if (inhoud.getGemeentePKCode() != null && inhoud.getGemeentePKCode().getWaarde() != null) {
                lo3ValueConvert.addElement(inschrijving, Lo3ElementEnum.ELEMENT_6910, inhoud.getGemeentePKCode());
            }
            if (inhoud.getIndicatieGeheimCode() != null && inhoud.getIndicatieGeheimCode().getWaarde() != null) {
                lo3ValueConvert.addElement(inschrijving, Lo3ElementEnum.ELEMENT_7010, inhoud.getIndicatieGeheimCode());
            }
            gegevensgroepenBuilder.addGroep71(inschrijving, inhoud.getDatumVerificatie(), inhoud.getOmschrijvingVerificatie());
            gegevensgroepenBuilder.addGroep80(inschrijving, inhoud.getVersienummer(), inhoud.getDatumtijdstempel());

            if (inhoud.getIndicatiePKVolledigGeconverteerdCode() != null && inhoud.getIndicatiePKVolledigGeconverteerdCode().getWaarde() != null) {
                lo3ValueConvert.addElement(inschrijving, Lo3ElementEnum.ELEMENT_8710, inhoud.getIndicatiePKVolledigGeconverteerdCode());
            }
            gegevensgroepenBuilder.addRni(inschrijving, documentatie);
        }
        return inschrijving;
    }

    /**
     * Maak een verblijfinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createVerblijfplaatsInhoud(
            final Lo3VerblijfplaatsInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> verblijfplaats = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep09(verblijfplaats, inhoud.getGemeenteInschrijving(), inhoud.getDatumInschrijving());
            gegevensgroepenBuilder.addGroep10(verblijfplaats, inhoud.getFunctieAdres(), inhoud.getGemeenteDeel(), inhoud.getAanvangAdreshouding());
            gegevensgroepenBuilder.addGroep11(verblijfplaats, inhoud);

            if (inhoud.getLocatieBeschrijving() != null) {
                lo3ValueConvert.addElement(verblijfplaats, Lo3ElementEnum.ELEMENT_1210, inhoud.getLocatieBeschrijving());
            }
            gegevensgroepenBuilder.addGroep13(
                    verblijfplaats,
                    inhoud.getLandAdresBuitenland(),
                    inhoud.getDatumVertrekUitNederland(),
                    inhoud.getAdresBuitenland1(),
                    inhoud.getAdresBuitenland2(),
                    inhoud.getAdresBuitenland3());
            gegevensgroepenBuilder.addGroep14(verblijfplaats, inhoud.getLandVanwaarIngeschreven(), inhoud.getVestigingInNederland());
            if (inhoud.getAangifteAdreshouding() != null && inhoud.getAangifteAdreshouding().getWaarde() != null) {
                lo3ValueConvert.addElement(verblijfplaats, Lo3ElementEnum.ELEMENT_7210, inhoud.getAangifteAdreshouding());
            }
            if (inhoud.getIndicatieDocument() != null && inhoud.getIndicatieDocument().getWaarde() != null) {
                lo3ValueConvert.addElement(verblijfplaats, Lo3ElementEnum.ELEMENT_7510, inhoud.getIndicatieDocument());
            }

            gegevensgroepenBuilder.addDocumentatie(verblijfplaats, documentatie);
            gegevensgroepenBuilder.addOnderzoek(verblijfplaats, onderzoek);
            gegevensgroepenBuilder.addHistorie(verblijfplaats, historie);
            gegevensgroepenBuilder.addRni(verblijfplaats, documentatie);
        }
        return verblijfplaats;
    }

    /**
     * Maak een kindinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createKindInhoud(
            final Lo3KindInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> kind = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep01(kind, inhoud.getaNummer(), inhoud.getBurgerservicenummer());
            gegevensgroepenBuilder.addGroep02(
                    kind,
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam());
            gegevensgroepenBuilder.addGroep03(kind, inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode());

            gegevensgroepenBuilder.addDocumentatie(kind, documentatie);
            gegevensgroepenBuilder.addOnderzoek(kind, onderzoek);
            gegevensgroepenBuilder.addHistorie(kind, historie);
        }
        return kind;
    }

    /**
     * Maak een verblijfstitelinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createVerblijfstitelInhoud(
            final Lo3VerblijfstitelInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> verblijfstitel = new LinkedHashMap<>();
        if (inhoud != null) {
            if (inhoud.getAanduidingVerblijfstitelCode() != null && inhoud.getAanduidingVerblijfstitelCode().getWaarde() != null) {
                lo3ValueConvert.addElement(verblijfstitel, Lo3ElementEnum.ELEMENT_3910, inhoud.getAanduidingVerblijfstitelCode());
            }
            if (inhoud.getDatumEindeVerblijfstitel() != null) {
                lo3ValueConvert.addElement(verblijfstitel, Lo3ElementEnum.ELEMENT_3920, inhoud.getDatumEindeVerblijfstitel());
            }
            if (inhoud.getDatumAanvangVerblijfstitel() != null) {
                lo3ValueConvert.addElement(verblijfstitel, Lo3ElementEnum.ELEMENT_3930, inhoud.getDatumAanvangVerblijfstitel());
            }

            gegevensgroepenBuilder.addOnderzoek(verblijfstitel, onderzoek);
            gegevensgroepenBuilder.addHistorie(verblijfstitel, historie);
        }
        return verblijfstitel;
    }

    /**
     * Maak een gezagsverhoudinginhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createGezagsverhoudingInhoud(
            final Lo3GezagsverhoudingInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> gezagsverhouding = new LinkedHashMap<>();
        if (inhoud != null) {
            if (inhoud.getIndicatieGezagMinderjarige() != null && inhoud.getIndicatieGezagMinderjarige().getWaarde() != null) {
                lo3ValueConvert.addElement(gezagsverhouding, Lo3ElementEnum.ELEMENT_3210, inhoud.getIndicatieGezagMinderjarige());
            }
            if (inhoud.getIndicatieCurateleregister() != null && inhoud.getIndicatieCurateleregister().getWaarde() != null) {
                lo3ValueConvert.addElement(gezagsverhouding, Lo3ElementEnum.ELEMENT_3310, inhoud.getIndicatieCurateleregister());
            }

            gegevensgroepenBuilder.addDocumentatie(gezagsverhouding, documentatie);
            gegevensgroepenBuilder.addOnderzoek(gezagsverhouding, onderzoek);
            gegevensgroepenBuilder.addHistorie(gezagsverhouding, historie);
        }
        return gezagsverhouding;
    }

    /**
     * Maak een reisdocumentinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @param onderzoek Lo3 onderzoek
     * @return Map met de keys en values.
     */
    public final Map<String, String> createReisdocumentInhoud(
            final Lo3ReisdocumentInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie,
            final Lo3Onderzoek onderzoek) {
        final Map<String, String> reisdocument = new LinkedHashMap<>();
        if (inhoud != null) {
            gegevensgroepenBuilder.addGroep35(reisdocument, inhoud);
            if (inhoud.getSignalering() != null && inhoud.getSignalering().getWaarde() != null) {
                lo3ValueConvert.addElement(reisdocument, Lo3ElementEnum.ELEMENT_3610, inhoud.getSignalering());
            }
            gegevensgroepenBuilder.addDocumentatie(reisdocument, documentatie);
            gegevensgroepenBuilder.addOnderzoek(reisdocument, onderzoek);
            gegevensgroepenBuilder.addHistorie(reisdocument, historie);
        }
        return reisdocument;
    }

    /**
     * Maak een kiesrechtinhoud van de meegegeven gegevens.
     * @param inhoud Lo3 categorie inhoud.
     * @param historie Lo3 historie.
     * @param documentatie Lo3 documentatie.
     * @return Map met de keys en values.
     */
    public final Map<String, String> createKiesrechtInhoud(
            final Lo3KiesrechtInhoud inhoud,
            final Lo3Historie historie,
            final Lo3Documentatie documentatie) {
        final Map<String, String> kiesrecht = new LinkedHashMap<>();
        if (inhoud != null) {
            if (inhoud.getAanduidingEuropeesKiesrecht() != null) {
                lo3ValueConvert.addElement(kiesrecht, Lo3ElementEnum.ELEMENT_3110, inhoud.getAanduidingEuropeesKiesrecht());
            }
            if (inhoud.getDatumEuropeesKiesrecht() != null) {
                lo3ValueConvert.addElement(kiesrecht, Lo3ElementEnum.ELEMENT_3120, inhoud.getDatumEuropeesKiesrecht());
            }
            if (inhoud.getEinddatumUitsluitingEuropeesKiesrecht() != null) {
                lo3ValueConvert.addElement(kiesrecht, Lo3ElementEnum.ELEMENT_3130, inhoud.getEinddatumUitsluitingEuropeesKiesrecht());
            }
            if (inhoud.getAanduidingUitgeslotenKiesrecht() != null && inhoud.getAanduidingUitgeslotenKiesrecht().getWaarde() != null) {
                lo3ValueConvert.addElement(kiesrecht, Lo3ElementEnum.ELEMENT_3810, inhoud.getAanduidingUitgeslotenKiesrecht());
            }
            if (inhoud.getEinddatumUitsluitingKiesrecht() != null) {
                lo3ValueConvert.addElement(kiesrecht, Lo3ElementEnum.ELEMENT_3820, inhoud.getEinddatumUitsluitingKiesrecht());
            }

            gegevensgroepenBuilder.addDocumentatie(kiesrecht, documentatie);
        }
        return kiesrecht;
    }

}
