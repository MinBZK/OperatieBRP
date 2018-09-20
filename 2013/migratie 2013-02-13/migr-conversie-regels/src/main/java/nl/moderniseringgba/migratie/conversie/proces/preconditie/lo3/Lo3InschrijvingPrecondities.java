/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 07: Inschrijving.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3InschrijvingPrecondities extends Lo3Precondities {

    private static final int VERSIENUMMER_MIN = 0;
    private static final int VERSIENUMMER_MAX = 9999;

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3InschrijvingInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        if (stapel.size() >= 2) {
            for (int i = 1; i < stapel.size(); i++) {
                Foutmelding.logStructuurFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR,
                        "Categorie 07: Inschrijving mag alleen actuele categorie voorkomens hebben.");
            }
        }

        for (final Lo3Categorie<Lo3InschrijvingInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3InschrijvingInhoud> categorie) {
        final Lo3InschrijvingInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 66: Blokkering - geen controles
        if (isGroepAanwezig(inhoud.getDatumIngangBlokkering())) {
            controleerGroep66Blokkering(inhoud.getDatumIngangBlokkering(), herkomst);
        }

        // Groep 67: Opschorting
        if (isGroepAanwezig(inhoud.getDatumOpschortingBijhouding(), inhoud.getRedenOpschortingBijhoudingCode())) {
            controleerGroep67Opschorting(inhoud.getDatumOpschortingBijhouding(),
                    inhoud.getRedenOpschortingBijhoudingCode(), herkomst);
        }

        // Groep 68: Opname
        if (!isGroepAanwezig(inhoud.getDatumEersteInschrijving())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE037,
                    "Groep 68: Opname moet verplicht voorkomen in categorie 07: Inschrijving.");
        } else {
            controleerGroep68Opname(inhoud.getDatumEersteInschrijving(), herkomst);
        }

        // Groep 69: Gemeente PK - geen controles
        if (isGroepAanwezig(inhoud.getGemeentePKCode())) {
            controleer69GemeentePK(inhoud.getGemeentePKCode(), herkomst);
        }

        // Groep 70: Geheim - geen controles
        if (!isGroepAanwezig(inhoud.getIndicatieGeheimCode())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 70: Geheim moet verplicht voorkomen in categorie 07: Inschrijving.");
        } else {
            controleerGroep70Geheim(inhoud.getIndicatieGeheimCode(), herkomst);
        }

        // Groep 80: Synchroniciteit
        if (!isGroepAanwezig(inhoud.getVersienummer(), inhoud.getDatumtijdstempel())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE037,
                    "Groep 80: Synchroniciteit moet verplicht voorkomen in categorie 07: Inschrijving.");
        } else {
            controleerGroep80Synchroniciteit(inhoud.getVersienummer(), inhoud.getDatumtijdstempel(), herkomst);
        }

        // Groep 87: PK-conversie - geen controles
        if (isGroepAanwezig(inhoud.getIndicatiePKVolledigGeconverteerdCode())) {
            controleerGroep87PKConversie(inhoud.getIndicatiePKVolledigGeconverteerdCode(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        controleerDocumentatie(documentatie, herkomst);

        controleerHistorie(categorie, herkomst);
    }

    private void controleerDocumentatie(final Lo3Documentatie documentatie, final Lo3Herkomst herkomst) {
        if (isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 07: Inschrijving.");
            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 82: Document mag niet voorkomen in categorie 07: Inschrijving.");
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }
    }

    private void controleerHistorie(final Lo3Categorie<Lo3InschrijvingInhoud> categorie, final Lo3Herkomst herkomst) {
        final Lo3Historie historie = categorie.getHistorie();
        if (!historie.isNullHistorie()) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 84: Onjuist en Groep 85: Geldigheid en Groep 86: Opneming mogen niet voorkomen in "
                            + "categorie 07: Inschrijving.");
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep66Blokkering(final Lo3Datum datumIngangBlokkering, final Lo3Herkomst herkomst) {
        controleerAanwezig(datumIngangBlokkering, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 66.20: Datum ingang blokkering is verplicht in groep 66: Blokkering."));
        controleerDatum(datumIngangBlokkering, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 66.20: Datum ingang blokkering bevat geen geldige datum."));

    }

    private void controleerGroep67Opschorting(
            final Lo3Datum datumOpschortingBijhouding,
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(datumOpschortingBijhouding, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 67.10: Datum opschorting bijhouding moet verplicht voorkomen in " + "groep 67: "
                        + "Opschorting."));
        controleerDatum(datumOpschortingBijhouding, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 67.10: Datum opschorting bijhouding bevat geen geldige datum."));

        controleerAanwezig(redenOpschortingBijhoudingCode,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 67.20: Reden opschorting bijhouding moet verplicht voorkomen in groep 67:"
                                + " Opschorting."));
        Lo3PreconditieEnumCodeChecks.controleerCode(redenOpschortingBijhoudingCode, Foutmelding.maakPreconditieFout(
                herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 67.20: Reden opschorting bijhouding bevat geen geldige waarde."));
        controleerBijhoudingCode(herkomst, redenOpschortingBijhoudingCode);
    }

    private void controleerBijhoudingCode(
            final Lo3Herkomst herkomst,
            final Lo3RedenOpschortingBijhoudingCode redenOpschortingBijhoudingCode) {
        if (redenOpschortingBijhoudingCode != null) {
            if (redenOpschortingBijhoudingCode.isOnbekend()) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB010);
            }
            if (redenOpschortingBijhoudingCode.isFout()) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB011);
            }
        }
    }

    private void controleerGroep68Opname(final Lo3Datum datumEersteInschrijving, final Lo3Herkomst herkomst) {
        controleerAanwezig(datumEersteInschrijving, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE037, "Element 68.10: Datum eerste inschrijving is verplicht in groep 68: Opname."));
        controleerDatum(datumEersteInschrijving, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 68.10: Datum eerste inschrijving bevat geen geldige datum."));
    }

    private void controleer69GemeentePK(final Lo3GemeenteCode gemeentePKCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(gemeentePKCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 69.10: Gemeente PK is verplicht in groep 69: Gemeente PK."));
        controleerCode(gemeentePKCode, false, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 69.10: Gemeente PK bevat geen geldige waarde."));
    }

    private void
            controleerGroep70Geheim(final Lo3IndicatieGeheimCode indicatieGeheimCode, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks
                .controleerCode(indicatieGeheimCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                        Precondities.PRE054, "Element 70.10: Indicatie geheim bevat geen geldige waarde."));
        // CHECKSTYLE:OFF magic-number voor '6'.
        if (indicatieGeheimCode.getCode() >= 1 && indicatieGeheimCode.getCode() <= 6) {
            // CHECKSTYLE:ON
            Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB009);
        }
    }

    private void controleerGroep80Synchroniciteit(
            final Integer versienummer,
            final Lo3Datumtijdstempel datumtijdstempel,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(versienummer, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE037,
                "Element 80.10: Versienummer moet verplicht voorkomen in groep 80: Synchroniciteit."));
        controleerVersienummer(versienummer, "Element 80.10: Versienummer bevat geen geldige waarde.", herkomst);
        controleerAanwezig(datumtijdstempel, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE038, "Element 80.20: Datumtijdstempel moet verplicht voorkomen in groep 80: "
                        + "Synchroniciteit."));
        controleerDatumtijdstempel(datumtijdstempel, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE038, "Element 80.20: Datumtijdstempel bevat geen geldige waarde."));
    }

    private void controleerGroep87PKConversie(
            final Lo3IndicatiePKVolledigGeconverteerdCode indicatiePKVolledigGeconverteerdCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(indicatiePKVolledigGeconverteerdCode, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO,
                "Element 87.10: Volledig geconverteerd moet verplicht voorkomen in groep 87: PK-Conversie."));
        Lo3PreconditieEnumCodeChecks.controleerCode(indicatiePKVolledigGeconverteerdCode, Foutmelding
                .maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 87.10: Volledig geconverteerd bevat geen geldige waarde."));
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerVersienummer(
            final Integer versienummer,
            final String foutmelding,
            final Lo3Herkomst herkomst) {
        if (versienummer == null) {
            return;
        }

        if (versienummer < VERSIENUMMER_MIN || versienummer > VERSIENUMMER_MAX) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.WARNING, foutmelding);
        }
    }

    private void
            controleerDatumtijdstempel(final Lo3Datumtijdstempel datumtijdstempel, final Foutmelding foutmelding) {
        if (datumtijdstempel == null) {
            return;
        }

        final String formatted = String.format("%017d", datumtijdstempel.getDatum());

        // CHECKSTYLE:OFF - Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen
        if (formatted.length() != 17) {
            // CHECKSTYLE:ON
            foutmelding.log();
        } else if ("00000000000000000".equals(formatted)) {
            foutmelding.log();
        } else {
            // CHECKSTYLE:OFF - Magic numbers: Worden alleen gebruikt om een locaal gedefinieerd datum format te parsen
            final int jaar = Integer.valueOf(formatted.substring(0, 4));
            final int maand = Integer.valueOf(formatted.substring(4, 6)) - 1;
            final int dag = Integer.valueOf(formatted.substring(6, 8));
            final int uur = Integer.valueOf(formatted.substring(8, 10));
            final int minuut = Integer.valueOf(formatted.substring(10, 12));
            final int seconde = Integer.valueOf(formatted.substring(12, 14));
            // CHECKSTYLE:ON

            final Calendar cal = new GregorianCalendar();
            cal.set(Calendar.YEAR, jaar);
            cal.set(Calendar.MONTH, maand);
            cal.set(Calendar.DAY_OF_MONTH, dag);
            cal.set(Calendar.HOUR_OF_DAY, uur);
            cal.set(Calendar.MINUTE, minuut);
            cal.set(Calendar.SECOND, seconde);
            // milliseconden hoeven niet gecheckt te worden omdat alle 1000 mogelijke
            // waarden geldig zijn.

            // CHECKSTYLE:OFF - Boolean Expression Complexity: Is niet complex, alleen lang
            if (cal.get(Calendar.YEAR) != jaar || cal.get(Calendar.MONTH) != maand
                    || cal.get(Calendar.DAY_OF_MONTH) != dag || cal.get(Calendar.HOUR_OF_DAY) != uur
                    || cal.get(Calendar.MINUTE) != minuut || cal.get(Calendar.SECOND) != seconde) {
                // CHECKSTYLE:ON
                foutmelding.log();
            }
        }
    }
}
