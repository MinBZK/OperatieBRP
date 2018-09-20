/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

/**
 * Basis controles.
 */
public abstract class Lo3Precondities extends Lo3PreconditieCodeChecks {

    private static final Lo3Datum INGANG_BSN = new Lo3Datum(20071126);

    private static final int MAXIMUM_MAAND_WAARDE = 12;
    private static final String MAXIMAAL_200 = "MAXIMAAL-200";
    private static final String DATUM_FORMAT = "%08d";
    private static final String ONBEKEND_JAAR = "0000";
    private static final String ONBEKENDE_MAAND_OF_DAG = "00";
    private static final int A_NUMMER_LENGTE = 10;

    /**
     * Bepaal of een groep aanwezig is.
     * 
     * @param elementen
     *            elementen die tesamen de groep vormen
     * @return true, als minimaal 1 element niet null is, anders false
     */
    protected final boolean isGroepAanwezig(final Object... elementen) {
        for (final Object element : elementen) {
            if (element != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controleer de coumentatie.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerDocumentOfAkte(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            controleerDocumentOfAkte(categorie.getDocumentatie(), categorie.getLo3Herkomst());
        }
    }

    /**
     * Geeft aan of akte aanwezig is.
     * 
     * @param documentatie
     *            de documentatie die mogelijk een akte (groep81) bevat
     * @param herkomst
     *            herkomst
     * @return true als er akte is opgenomen
     */
    protected final boolean isAkteAanwezig(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        return isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte());
    }

    /**
     * Geeft aan of document aanwezig is.
     * 
     * @param documentatie
     *            de documentatie die mogelijk een document (groep 82) bevat
     * @param herkomst
     *            herkomst
     * @return true als er akte is opgenomen
     */
    protected final boolean isDocumentAanwezig(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        return isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument());
    }

    private void controleerDocumentOfAkte(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        final boolean akteAanwezig = isAkteAanwezig(documentatie, herkomst);
        final boolean documentAanwezig = isDocumentAanwezig(documentatie, herkomst);

        if (akteAanwezig) {
            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (documentAanwezig) {
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }
        // Als beide aanwezig zijn, dan preconditie 020 loggen
        if (akteAanwezig && documentAanwezig) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE020,
                    Precondities.PRE020.getOmschrijving());
        }
    }

    /**
     * Controleer dat groep 85: Geldigheid aanwezig is in alle categorieen van een stapel.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerGeldigheidAanwezig(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Datum ingangsDatumGeldigheid = getIngangsDatumGeldigheid(categorie);
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            controleerAanwezig(ingangsDatumGeldigheid, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                    Precondities.PRE030, "Groep 85: Geldigheid moet verplicht voorkomen."));
            controleerDatum(ingangsDatumGeldigheid, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                    "Element 85.10: Ingangsdatum geldigheid bevat geen geldige datum."));
        }
    }

    /**
     * Geeft de datum geldigheid (85.10) terug als deze aanwezig is.
     * 
     * @param <T>
     *            categorie type
     * @param categorie
     *            de categorie waar de 85.10 uit gehaald wordt.
     * @return de datum geldigheid of null
     */
    protected final <T extends Lo3CategorieInhoud> Lo3Datum
            getIngangsDatumGeldigheid(final Lo3Categorie<T> categorie) {
        final Lo3Historie historie = categorie.getHistorie();
        return historie == null ? null : historie.getIngangsdatumGeldigheid();
    }

    /**
     * Controleer dat groep 86: Opname aanwezig is in alle categorieen van een stapel.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerOpnemingAanwezig(final Lo3Stapel<T> stapel) {
        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Datum datumVanOpneming = getDatumOpneming(categorie);
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            controleerAanwezig(datumVanOpneming, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                    Precondities.PRE031, "Groep 86: Opneming moet verplicht voorkomen."));
            controleerDatum(datumVanOpneming, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                    "Element 86.10: Datum van opneming bevat geen geldige datum."));
            controleerDatumNietOnbekend(datumVanOpneming,
                    Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE031,
                            "Element 86.10: Datum van opneming mag geen (gedeeltelijk) onbekende geldige datum "
                                    + "bevatten."));

        }
    }

    /**
     * Geeft de datum opneming (86.10) terug als deze aanwezig is.
     * 
     * @param <T>
     *            categorie type
     * @param categorie
     *            de categorie waar de 86.10 uit gehaald wordt.
     * @return de datum opneming of null
     */
    protected final <T extends Lo3CategorieInhoud> Lo3Datum getDatumOpneming(final Lo3Categorie<T> categorie) {
        final Lo3Historie historie = categorie.getHistorie();
        return historie == null ? null : historie.getDatumVanOpneming();
    }

    /**
     * Controleer dat groep 84: Onjuist *niet* aanwezig is in de actuele categorieen van een stapel.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerOnjuist(final Lo3Stapel<T> stapel) {
        for (int i = 0; i < stapel.size(); i++) {
            final Lo3Categorie<T> categorie = stapel.get(i);

            final Lo3Historie historie = categorie.getHistorie();
            final Lo3IndicatieOnjuist onjuist = historie == null ? null : historie.getIndicatieOnjuist();
            final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

            if (i == stapel.size() - 1) {
                if (onjuist != null) {
                    Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE055,
                            "Groep 84: Onjuist mag niet voorkomen in een actuele categorie.");
                }
            }

            Lo3PreconditieEnumCodeChecks.controleerCode(onjuist,
                    Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                            "Element 84.10: Indicatie onjuist" + " bevat geen geldige waarde."));
            if (onjuist != null && "S".equals(onjuist.getCode())) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB022);
            }
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    /**
     * Controleer groep 01 identificatienummers.
     * 
     * @param aNummer
     *            Een A-Nummer.
     * @param burgerservicenummer
     *            Een BurgerServiceNummer.
     * @param ingangsDatumGeldigheid
     *            Een ingangsdatum geldigheid.
     * @param herkomst
     *            Een herkomst.
     * @param categorie01
     *            Is dit voor categorie 01?
     */
    protected final void controleerGroep01Identificatienummers(
            final Long aNummer,
            final Long burgerservicenummer,
            final Lo3Datum ingangsDatumGeldigheid,
            final Lo3Herkomst herkomst,
            final boolean categorie01) {
        final String foutOmschrijvingAnummerAfwezig =
                "Element 01.10: A-nummer is verplicht in groep 01: Identificatienummers.";
        controleerAanwezig(
                aNummer,
                categorie01 ? Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE005,
                        foutOmschrijvingAnummerAfwezig) : Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        foutOmschrijvingAnummerAfwezig));
        controleerAnummer(aNummer, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 01.10: A-nummer voldoet niet aan de inhoudelijke voorwaarden."));

        if (ingangsDatumGeldigheid != null && INGANG_BSN.compareTo(ingangsDatumGeldigheid) <= 0) {
            controleerAanwezig(burgerservicenummer, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                    "Element 01.20: Burgerservicenummer is verplicht (indien de ingangsdatum geldigheid op of na "
                            + "26-11-2007 ligt) in groep 01: Identificatienummers."));
        }

        controleerBsn(burgerservicenummer, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 01.20: Burgerservicenummer voldoet niet aan de inhoudelijke voorwaarden."));
    }

    /**
     * Controleer groep 02 Naam.
     * 
     * @param voornamen
     *            voornamen
     * @param adellijkeTitelPredikaatCode
     *            adelijke titel predikaat code
     * @param voorvoegsel
     *            voorvoegsel
     * @param geslachtsnaam
     *            geslachtsnaam
     * @param herkomst
     *            herkomst
     * @param categorie01
     *            Is dit voor categorie 01?
     */
    protected final void controleerGroep02Naam(
            final String voornamen,
            final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode,
            final String voorvoegsel,
            final String geslachtsnaam,
            final Lo3Herkomst herkomst,
            final boolean categorie01) {
        controleerMaximumLengte(voornamen, Lo3ElementEnum.ELEMENT_0210, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR, MAXIMAAL_200, "Element 02.10: Voornamen mag niet langer dan 200 tekens zijn."));
        controleerCode(adellijkeTitelPredikaatCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 02.20: Adellijke titel/predikaat bevat geen geldige waarde."));
        controleerMaximumLengte(voorvoegsel, Lo3ElementEnum.ELEMENT_0230, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR, "MAXIMAAL-10", "Element 02.30: Voorvoegsel mag niet langer dan 10 tekens zijn."));
        controleerCode(voorvoegsel, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 02.30: Voorvoegsel bevat geen geldige waarde."));

        controleerAanwezig(geslachtsnaam, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                categorie01 ? Precondities.PRE034 : Precondities.PRE064,
                "Element 02.40: Geslachtnaam is verplicht in groep 02: Naam."));
        controleerMaximumLengte(geslachtsnaam, Lo3ElementEnum.ELEMENT_0240,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING, MAXIMAAL_200,
                        "Element 02.40: Geslachtnaam mag niet langer dan 200 tekens zijn."));
    }

    /**
     * Controleer groep 03 Geboorte.
     * 
     * @param geboortedatum
     *            geboortedatum
     * @param geboorteGemeenteCode
     *            geboortegemeente code
     * @param geboorteLandCode
     *            geboorteland code
     * @param herkomst
     *            herkomst
     */
    protected final void controleerGroep03Geboorte(
            final Lo3Datum geboortedatum,
            final Lo3GemeenteCode geboorteGemeenteCode,
            final Lo3LandCode geboorteLandCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(geboortedatum, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE007, "Element 03.10: Geboortedatum is verplicht in groep 03: Geboorte."));
        controleerDatum(geboortedatum, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 03.10: Geboortedatum bevat geen geldige datum."));

        controleerAanwezig(geboorteGemeenteCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 03.20: Geboorteplaats is verplicht in groep 03: Geboorte."));

        controleerAanwezig(geboorteLandCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE007, "Element 03.30: Geboorteland is verplicht in groep 03: Geboorte."));
        controleerCode(geboorteLandCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE008, "Element 03.30: Geboorteland bevat geen geldige waarde."));

        if (geboorteLandCode != null && geboorteLandCode.isOnbekend()) {
            Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB001);
        }

        controleerNederlandseGemeente(geboorteLandCode, geboorteGemeenteCode,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE025,
                        "Als element 03.30: Geboorteland Nederland bevat, moet element 03.20: Geboorteplaats een "
                                + "Nederlandse gemeente bevatten."));
    }

    /**
     * Controleer groep 04 Geslachtsaanduiding.
     * 
     * @param geslachtsaanduiding
     *            geslachtsaanduiding
     * @param herkomst
     *            herkomst
     */
    protected final void controleerGroep4Geslachtsaanduiding(
            final Lo3Geslachtsaanduiding geslachtsaanduiding,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(geslachtsaanduiding, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 04.10: Geslachtsaanduiding is verplicht in groep 04: Geslachtsaanduiding."));
        Lo3PreconditieEnumCodeChecks.controleerCode(geslachtsaanduiding, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE054,
                "Element 04.10: Geslachtsaanduiding bevat geen geldige waarde."));
    }

    /**
     * Controleer groep 81 Akte.
     * 
     * @param gemeenteAkte
     *            gemeente akte
     * @param nummerAkte
     *            nummer akte
     * @param herkomst
     *            herkomst
     */
    protected final void controleerGroep81Akte(
            final Lo3GemeenteCode gemeenteAkte,
            final String nummerAkte,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(gemeenteAkte, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 81.10: Registergemeente akte moet verplicht voorkomen in groep 81: Akte."));
        controleerCode(gemeenteAkte, false, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 81.10: Registergemeente akte bevat een ongeldige waarde."));

        controleerAanwezig(nummerAkte, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 81.20: Aktenummer moet verplicht voorkomen in groep 81: Akte"));

        controleerMinimumLengte(nummerAkte, Lo3ElementEnum.ELEMENT_8120, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO, "Element 81.20: Aktenummer moet minimaal 7 lang zijn."));

        controleerMaximumLengte(nummerAkte, Lo3ElementEnum.ELEMENT_8120, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR, "MAXIMAAL-7", "Element 81.20: Aktenummer mag maximaal 7 lang zijn."));

    }

    /**
     * Controleert alle precondities voor Groep 82 Document.
     * 
     * @param gemeenteDocument
     *            de gemeentecode van/op het document
     * @param datumDocument
     *            datum van het document
     * @param beschrijvingDocument
     *            beschrijving van het document
     * @param herkomst
     *            herkomst
     */
    protected final void controleerGroep82Document(
            final Lo3GemeenteCode gemeenteDocument,
            final Lo3Datum datumDocument,
            final String beschrijvingDocument,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(gemeenteDocument, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 82.10: Gemeente document moet verplicht voorkomen in groep 82: Document."));
        controleerCode(gemeenteDocument, false, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 82.10: Gemeente document bevat geen geldige waarde."));

        controleerDatum(datumDocument, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 82.20: Datum document bevat geen geldige datum."));
        controleerDatumNietOnbekend(datumDocument, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE067,
                "Element 82.20: Datum document maggeen (gedeeltelijjk) onbekende datum bevatten."), true);

        controleerAanwezig(beschrijvingDocument, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 82.30: Beschrijving document moet verplicht voorkomen in groep 82: Document"));

        controleerMaximumLengte(beschrijvingDocument, Lo3ElementEnum.ELEMENT_8230, Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.ERROR, "MAXIMAAL-40",
                "Element 82.30: Beschrijving document mag maximaal 40 lang zijn."));
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    /**
     * Controleer preconditie 050.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerPreconditie050(final Lo3Stapel<T> stapel) {
        Lo3Datum oudsteGevuldeDatum = null;

        for (final Lo3Categorie<T> categorie : stapel) {
            final Lo3Datum datum = categorie.getHistorie().getDatumVanOpneming();

            if (!categorie.getInhoud().isLeeg()) {
                if (oudsteGevuldeDatum == null || datum != null && oudsteGevuldeDatum.compareTo(datum) > 0) {
                    oudsteGevuldeDatum = datum;
                }
            }
        }

        for (final Lo3Categorie<T> categorie : stapel) {
            if (categorie.getInhoud().isLeeg()) {
                final Lo3Datum datum = categorie.getHistorie().getDatumVanOpneming();

                if (oudsteGevuldeDatum == null || datum.compareTo(oudsteGevuldeDatum) < 0) {
                    Foutmelding.logPreconditieFout(categorie.getLo3Herkomst(), LogSeverity.ERROR,
                            Precondities.PRE050, "Als er een lege categorie voorkomt op een stapel, "
                                    + "dan moet er ook een gevulde categorie "
                                    + "zijn met een element 86.10: Datum van opneming die hetzelfde of ouder is.");
                }
            }
        }
    }

    /**
     * Controleer preconditie 055.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerPreconditie055(final Lo3Stapel<T> stapel) {
        Lo3Categorie<T> meestRecenteVoorkomen = null;
        for (final Lo3Categorie<T> voorkomen : stapel) {
            if (meestRecenteVoorkomen == null) {
                meestRecenteVoorkomen = voorkomen;
                continue;
            }
            final Lo3Datum datumVanOpnemingMeestRecenteVoorkomen =
                    meestRecenteVoorkomen.getHistorie().getDatumVanOpneming() != null ? meestRecenteVoorkomen
                            .getHistorie().getDatumVanOpneming() : Lo3Datum.NULL_DATUM;
            final Lo3Datum datumVanOpnemingVoorkomen =
                    voorkomen.getHistorie().getDatumVanOpneming() != null ? voorkomen.getHistorie()
                            .getDatumVanOpneming() : Lo3Datum.NULL_DATUM;
            final boolean voorkomenIsRecenter =
                    datumVanOpnemingMeestRecenteVoorkomen.compareTo(datumVanOpnemingVoorkomen) < 0;
            final boolean voorkomenEvenRecent =
                    datumVanOpnemingMeestRecenteVoorkomen.compareTo(datumVanOpnemingVoorkomen) == 0;

            if (voorkomenIsRecenter || voorkomenEvenRecent && meestRecenteVoorkomen.getHistorie().isOnjuist()) {
                meestRecenteVoorkomen = voorkomen;
            }
        }

        if (meestRecenteVoorkomen.getHistorie().isOnjuist()) {
            Foutmelding.logPreconditieFout(meestRecenteVoorkomen.getLo3Herkomst(), LogSeverity.ERROR,
                    Precondities.PRE055,
                    "De categorie-rij met de meest recente 86.10 Datum van opneming mag niet onjuist zijn.");
        }
    }

    /**
     * Controleer preconditie 056.
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            stapel
     */
    protected final <T extends Lo3CategorieInhoud> void controleerPreconditie056(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> legeRijen = verzamelLegeRijen(stapel);

        for (final Lo3Categorie<T> legeRij : legeRijen) {
            final Lo3Datum legeOpneming = legeRij.getHistorie().getDatumVanOpneming();
            final Lo3Datum legeGeldig = legeRij.getHistorie().getIngangsdatumGeldigheid();

            boolean ok = false;

            for (final Lo3Categorie<T> categorie : stapel) {
                final boolean gevuld = !categorie.getInhoud().isLeeg();
                final boolean onjuist = categorie.getHistorie().isOnjuist();
                final Lo3Datum geldig = categorie.getHistorie().getIngangsdatumGeldigheid();
                final Lo3Datum opneming = categorie.getHistorie().getDatumVanOpneming();

                if (gevuld && onjuist) {
                    if (geldig != null && geldig.equals(legeGeldig) && opneming.compareTo(legeOpneming) <= 0) {
                        ok = true;
                        break;
                    }
                }
            }

            if (!ok) {
                Foutmelding.logPreconditieFout(legeRij.getLo3Herkomst(), LogSeverity.ERROR, Precondities.PRE056,
                        "Als er een lege categorie-rij is, moet de datum geldigheid van die " + "categorie-rij ook "
                                + "voorkomen in een gevulde, onjuiste categorie-rij die een eerdere of gelijke "
                                + "datum van opneming heeft.");
            }
        }
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Categorie<T>> verzamelLegeRijen(final Lo3Stapel<T> stapel) {
        final List<Lo3Categorie<T>> legeRijen = new ArrayList<Lo3Categorie<T>>();

        for (final Lo3Categorie<T> categorie : stapel) {
            if (categorie.getInhoud().isLeeg()) {
                legeRijen.add(categorie);
            }
        }
        return legeRijen;
    }

    /**
     * Als er een gevuld juist categorie voorkomen is, dan mogen er geen leeg juist voorkomens zijn.<br>
     * Dit geldt voor de volgende categorieën:
     * <UL>
     * <LI>categorie 05/55 Huwelijk/geregistreerd partnerschap</LI>
     * <LI>categorie 06/56 Overlijden</LI>
     * </UL>
     * 
     * @param <T>
     *            categorie type
     * @param stapel
     *            de stapel die gecontroleerd wordt
     */
    protected final <T extends Lo3CategorieInhoud> void controleerPreconditie068(final Lo3Stapel<T> stapel) {
        boolean heeftStapelGevuldeJuisteRijen = false;
        boolean heeftStapelLegeJuisteRijen = false;

        for (final Lo3Categorie<T> rij : stapel) {
            if (rij.getHistorie().isOnjuist()) {
                continue;
            }
            if (!heeftStapelGevuldeJuisteRijen && !rij.getInhoud().isLeeg()) {
                heeftStapelGevuldeJuisteRijen = true;
            } else if (!heeftStapelLegeJuisteRijen && rij.getInhoud().isLeeg()) {
                heeftStapelLegeJuisteRijen = true;
            }
            if (heeftStapelGevuldeJuisteRijen && heeftStapelLegeJuisteRijen) {
                Foutmelding.logPreconditieFout(rij.getLo3Herkomst(), LogSeverity.WARNING, Precondities.PRE068,
                        "Als er een gevuld juist categorie voorkomen is,"
                                + " dan mogen er geen leeg juist voorkomens zijn.");

                break;
            }
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    /**
     * Controleer of het element voorkomt en log een fout indien niet.
     * 
     * @param elementWaarde
     *            de waarde van het element dat gecontroleerd wordt op aanwezigheid.
     * @param foutmelding
     *            de foutmelding om te loggen als het element niet aanwezig is.
     */
    protected final void controleerAanwezig(final Object elementWaarde, final Foutmelding foutmelding) {
        if (!isAanwezig(elementWaarde)) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of het A-Nummer geldig is en log een fout indien niet. Een afwezig (null) A-Nummer wordt als geldig
     * beschouwd.
     * 
     * @param aNummer
     *            het A-Nummer om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als het A-Nummer niet geldig is.
     */
    protected final void controleerAnummer(final Long aNummer, final Foutmelding foutmelding) {
        if (aNummer == null) {
            return;
        }

        if (!isGeldigAnummer(aNummer)) {
            foutmelding.log();
        }
    }

    /**
     * Geeft aan of het element voorkomt.
     * 
     * @param elementWaarde
     *            de waarde van het element dat gecontroleerd wordt op aanwezigheid
     * @return true als het element voorkomt
     */
    protected final boolean isAanwezig(final Object elementWaarde) {
        boolean aanwezig = elementWaarde != null;
        // Lege string wordt gezien als niet aanwezig/opgenomen op de PL
        if (aanwezig && elementWaarde instanceof String) {
            aanwezig = !((String) elementWaarde).trim().isEmpty();
        }
        return aanwezig;
    }

    private boolean isGeldigAnummer(final Long aNummer) {
        if (!checkAnummerInhoudEnLengte(aNummer)) {
            return false;
        }

        final String asString = String.valueOf(aNummer);

        final byte[] asBytes = new byte[A_NUMMER_LENGTE];
        for (int i = 0; i < A_NUMMER_LENGTE; i++) {
            asBytes[i] = Byte.valueOf(asString.substring(i, i + 1));
        }

        boolean checksumsOk = true;

        int som = 0;
        int multiplySom = 0;

        for (int i = 0; i < A_NUMMER_LENGTE; i++) {
            if (i > 0 && asBytes[i] == asBytes[i - 1]) {
                // 2 opeenvolgende cijfers zijn ongelijk
                checksumsOk = false;
            }
            // a0+a1+...+a9 is deelbaar door 11; rest 0 of 5
            som += asBytes[i];

            // (1*a0)+(2*a1)+(4*a2)+...+(512*a9) is deelbaar door 11
            multiplySom += Math.pow(2, i) * asBytes[i];

        }

        // CHECKSTYLE:OFF - Magic numbers: Vaste waarden in het algoritme
        if (som % 11 != 0 && som % 11 != 5) {
            // CHECKSTYLE:ON
            checksumsOk = false;
        }

        // CHECKSTYLE:OFF - Magic numbers: Vaste waarde in het algoritme
        if (multiplySom % 11 != 0) {
            // CHECKSTYLE:ON
            checksumsOk = false;
        }

        return checksumsOk;
    }

    private boolean checkAnummerInhoudEnLengte(final Long aNummer) {
        boolean heeftInhoudEnJuisteLengte = true;
        final String asString = String.valueOf(aNummer);

        if (aNummer == null || aNummer < 0) {
            heeftInhoudEnJuisteLengte = false;
        }
        if (asString.length() != A_NUMMER_LENGTE) {
            heeftInhoudEnJuisteLengte = false;
        }

        return heeftInhoudEnJuisteLengte;
    }

    /**
     * Controleer of het BurgerServiceNummer geldig is en log een fout indien niet. Een afwezig (null)
     * BurgerServiceNummer wordt als geldig beschouwd.
     * 
     * @param burgerservicenummer
     *            het BurgerServiceNummer om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als het BurgerServiceNummer niet geldig is.
     */
    protected final void controleerBsn(final Long burgerservicenummer, final Foutmelding foutmelding) {
        if (burgerservicenummer == null) {
            return;
        }
        if (!isGeldigBurgerservicenummer(burgerservicenummer)) {
            foutmelding.log();
        }

    }

    private boolean isGeldigBurgerservicenummer(final Long burgerservicenummer) {
        boolean heeftInhoudEnJuisteLengte = true;

        if (burgerservicenummer == null || burgerservicenummer < 0) {
            heeftInhoudEnJuisteLengte = false;
        }

        final String asString = String.format("%09d", burgerservicenummer);
        final int bsnLengte = Lo3ElementEnum.ELEMENT_0120.getMaximumLengte();
        if (asString.length() > bsnLengte) {
            heeftInhoudEnJuisteLengte = false;
        }

        if (!heeftInhoudEnJuisteLengte) {
            return false;
        }

        final byte[] asBytes = new byte[bsnLengte];
        for (int i = 0; i < bsnLengte; i++) {
            asBytes[i] = Byte.valueOf(asString.substring(i, i + 1));
        }

        int som = 0;

        for (int i = 0; i < bsnLengte; i++) {
            // (9*s0)+(8*s1)+(7*s2)+...+(2*s7)-(1*s8) is deelbaar door 11.
            if (i != bsnLengte - 1) {
                som += (bsnLengte - i) * asBytes[i];
            } else {
                som -= (bsnLengte - i) * asBytes[i];
            }
        }

        // CHECKSTYLE:OFF - Magic numbers: Vaste waarde in het algoritme
        return som % 11 == 0;
        // CHECKSTYLE:ON
    }

    /**
     * Controleer of de element waarde de minimale lengte voor het element type heeft, en log een foutmelding indien
     * niet. Een afwezige (null) elementwaarde wordt als geldig beschouwd.
     * 
     * @param elementWaarde
     *            De element waarde om te controleren.
     * @param elementType
     *            Het type element.
     * @param foutmelding
     *            De foutmeling om te logging als het element te kort is.
     */
    protected final void controleerMinimumLengte(
            final String elementWaarde,
            final Lo3ElementEnum elementType,
            final Foutmelding foutmelding) {
        if (elementWaarde == null || "".equals(elementWaarde)) {
            return;
        }

        if (elementWaarde.length() < elementType.getMinimumLengte()) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de element waarde binnen de maximale lengte voor het element type valt, en log een foutmelding
     * indien niet. Een afwezige (null) elementwaarde wordt als geldig beschouwd.
     * 
     * @param elementWaarde
     *            De element waarde om te controleren.
     * @param elementType
     *            Het type element.
     * @param foutmelding
     *            De foutmeling om te logging als het element te lang is.
     */
    protected final void controleerMaximumLengte(
            final String elementWaarde,
            final Lo3ElementEnum elementType,
            final Foutmelding foutmelding) {
        if (!isAanwezig(elementWaarde)) {
            return;
        }

        if (elementWaarde.length() > elementType.getMaximumLengte()) {
            foutmelding.log();
        }
    }

    /**
     * Controleer of de datum een geldige waarde heeft en log een fout indien niet. Een afwezige (null) datum wordt als
     * geldig beschouwd.
     * 
     * @param datum
     *            de datum om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de datum niet geldig is.
     */
    // CHECKSTYLE:OFF - Cyclomatic Complexity: Code is duidelijker als alle checks in 1 methode staan
    protected final void controleerDatum(final Lo3Datum datum, final Foutmelding foutmelding) {
        // CHECKSTYLE:ON
        if (datum == null) {
            return;
        }
        final String formatted = String.format(DATUM_FORMAT, datum.getDatum());

        // CHECKSTYLE:OFF - Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen
        if (formatted.length() != 8) {
            // CHECKSTYLE:ON
            foutmelding.log();
        } else {

            // CHECKSTYLE:OFF - Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen
            final String jaar = formatted.substring(0, 4);
            final String maand = formatted.substring(4, 6);
            final String dag = formatted.substring(6, 8);
            // CHECKSTYLE:ON

            final boolean onbekendJaar = ONBEKEND_JAAR.equals(jaar);
            final boolean onbekendMaand = ONBEKENDE_MAAND_OF_DAG.equals(maand);
            final boolean onbekendDag = ONBEKENDE_MAAND_OF_DAG.equals(dag);

            if (onbekendJaar) {
                if (!onbekendMaand || !onbekendDag) {
                    foutmelding.log();
                }
            } else {
                final int jaarWaarde = Integer.valueOf(jaar);
                final int maandWaarde = Integer.valueOf(maand);
                final int dagWaarde = Integer.valueOf(dag);

                final Calendar cacMaand = new GregorianCalendar();
                cacMaand.set(Calendar.YEAR, jaarWaarde);
                cacMaand.set(Calendar.MONTH, maandWaarde - 1);

                if (onbekendMaand && !onbekendDag) {
                    foutmelding.log();
                } else if (!onbekendMaand && maandWaarde > MAXIMUM_MAAND_WAARDE) {
                    foutmelding.log();
                } else if (!onbekendMaand && !onbekendDag
                        && dagWaarde > cacMaand.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    foutmelding.log();
                }
            }
        }
    }

    /**
     * Controleer of de datum een geldige en volledig bekende waarde heeft en log een fout indien niet. Een afwezige
     * (null) datum wordt als geldig beschouwd.
     * 
     * @param datum
     *            de datum om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de datum niet geldig is.
     */
    protected final void controleerDatumNietOnbekend(final Lo3Datum datum, final Foutmelding foutmelding) {
        controleerDatumNietOnbekend(datum, foutmelding, false);
    }

    /**
     * Controleer of de datum een geldige en bekende waarde heeft en log een fout indien niet. Een afwezige (null) datum
     * wordt als geldig beschouwd.
     * 
     * @param datum
     *            de datum om te controleren.
     * @param foutmelding
     *            de foutmelding om te loggen als de datum niet geldig is.
     * @param magVolledigOnbekendZijn
     *            indien true dan wordt een volledig onbekende datum als geldig beschouwd.
     */
    protected final void controleerDatumNietOnbekend(
            final Lo3Datum datum,
            final Foutmelding foutmelding,
            final boolean magVolledigOnbekendZijn) {
        if (datum == null) {
            return;
        }
        final String formatted = String.format(DATUM_FORMAT, datum.getDatum());

        // CHECKSTYLE:OFF - Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen
        if (formatted.length() == 8) {
            final String jaar = formatted.substring(0, 4);
            final String maand = formatted.substring(4, 6);
            final String dag = formatted.substring(6, 8);
            // CHECKSTYLE:ON

            final boolean onbekendJaar = ONBEKEND_JAAR.equals(jaar);
            final boolean onbekendMaand = ONBEKENDE_MAAND_OF_DAG.equals(maand);
            final boolean onbekendDag = ONBEKENDE_MAAND_OF_DAG.equals(dag);

            if (onbekendJaar || onbekendMaand || onbekendDag) {
                if (!(onbekendDag && onbekendMaand && onbekendJaar && magVolledigOnbekendZijn)) {
                    foutmelding.log();
                }
            }
        }
    }
}
