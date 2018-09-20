/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.List;

import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LengteHouder;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Signalering;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 12: Reisdocument.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3ReisdocumentPrecondities extends Lo3Precondities {

    /**
     * Controleer alle stapels.
     * 
     * @param stapels
     *            stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3ReisdocumentInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3ReisdocumentInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3ReisdocumentInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        if (stapel.size() >= 2) {
            for (int i = 1; i < stapel.size(); i++) {
                Foutmelding.logStructuurFout(stapel.get(i).getLo3Herkomst(), LogSeverity.ERROR,
                        "Categorie 12: Reisdocument mag alleen actuele categorie voorkomens hebben.");
            }
        }

        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);

        for (final Lo3Categorie<Lo3ReisdocumentInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3ReisdocumentInhoud> categorie) {
        final Lo3ReisdocumentInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep35aanwezig =
                isGroepAanwezig(inhoud.getSoortNederlandsReisdocument(), inhoud.getNummerNederlandsReisdocument(),
                        inhoud.getDatumUitgifteNederlandsReisdocument(),
                        inhoud.getAutoriteitVanAfgifteNederlandsReisdocument(),
                        inhoud.getDatumEindeGeldigheidNederlandsReisdocument(),
                        inhoud.getDatumInhoudingVermissingNederlandsReisdocument(),
                        inhoud.getAanduidingInhoudingVermissingNederlandsReisdocument(), inhoud.getLengteHouder());
        final boolean groep36aanwezig = isGroepAanwezig(inhoud.getSignalering());
        final boolean groep37aanwezig = isGroepAanwezig(inhoud.getAanduidingBezitBuitenlandsReisdocument());

        controleerAantalGroepen(groep35aanwezig, groep36aanwezig, groep37aanwezig, herkomst);

        // Groep 35: Nederlands reisdocument
        if (groep35aanwezig) {
            controleerGroep35NederlandsReisdocument(
                    inhoud.getSoortNederlandsReisdocument(),
                    inhoud.getNummerNederlandsReisdocument(),
                    inhoud.getAutoriteitVanAfgifteNederlandsReisdocument(),
                    inhoud.getLengteHouder(),
                    new ReisdocumentDatumsGeldigheid(inhoud.getDatumUitgifteNederlandsReisdocument(), inhoud
                            .getDatumEindeGeldigheidNederlandsReisdocument()),
                    new InhoudingVermissingReisdocument(inhoud

                    .getDatumInhoudingVermissingNederlandsReisdocument(), inhoud
                            .getAanduidingInhoudingVermissingNederlandsReisdocument()), herkomst);
        }

        // Groep 36: Signalering
        if (groep36aanwezig) {
            controleerGroep36Signalering(inhoud.getSignalering(), herkomst);
        }

        // Groep 37: Buitenlands reisdocument
        if (groep37aanwezig) {
            controleerGroep37BuitenlandsReisdocument(inhoud.getAanduidingBezitBuitenlandsReisdocument(), herkomst);
        }

        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        if (isGroepAanwezig(documentatie.getGemeenteAkte(), documentatie.getNummerAkte())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 81: Akte mag niet voorkomen in categorie 12: Reisdocument.");
            controleerGroep81Akte(documentatie.getGemeenteAkte(), documentatie.getNummerAkte(), herkomst);
        }
        if (!isGroepAanwezig(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                documentatie.getBeschrijvingDocument())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 82: Document moet verplicht voorkomen in categorie 12: Reisdocument");
        } else {
            controleerGroep82Document(documentatie.getGemeenteDocument(), documentatie.getDatumDocument(),
                    documentatie.getBeschrijvingDocument(), herkomst);
        }
    }

    private void controleerAantalGroepen(
            final boolean groep35aanwezig,
            final boolean groep36aanwezig,
            final boolean groep37aanwezig,
            final Lo3Herkomst herkomst) {
        final int aantalGevuldeGroepen =
                (groep35aanwezig ? 1 : 0) + (groep36aanwezig ? 1 : 0) + (groep37aanwezig ? 1 : 0);
        if (aantalGevuldeGroepen != 1) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE035,
                    "Groep 35: Nederlands reisdocument of groep 36: Signalering of groep 37: Buitenlands "
                            + "reisdocument moet verplicht voorkomen in categorie 12: Reisdocument.");
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep35NederlandsReisdocument(
            final Lo3SoortNederlandsReisdocument soortNederlandsReisdocument,
            final String nummerNederlandsReisdocument,
            final Lo3AutoriteitVanAfgifteNederlandsReisdocument autoriteitVanAfgifteNederlandsReisdocument,
            final Lo3LengteHouder lengteHouder,
            final ReisdocumentDatumsGeldigheid reisdocumentDatumsGeldigheid,
            final InhoudingVermissingReisdocument inhoudingVermissingReisdocument,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(soortNederlandsReisdocument,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE011,
                        "Element 35.10: Soort Nederlands reisdocument moet verplicht voorkomen in" + " groep 35:"
                                + " Reisdocument."));
        controleerCode(soortNederlandsReisdocument, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 35.10: Soort Nederlands reisdocument bevat geen geldige waarde."));

        controleerAanwezig(nummerNederlandsReisdocument,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE011,
                        "Element 35.20: Nummer Nederlands reisdocument moet verplicht voorkomen in groep 35: "
                                + "Reisdocument."));

        controleerMinimumLengte(nummerNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3520,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                        "Element 35.20: Nummer Nederlands reisdocument moet minimaal 9 lang zijn in groep "
                                + "35: Reisdocument."));

        controleerMaximumLengte(nummerNederlandsReisdocument, Lo3ElementEnum.ELEMENT_3520,
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR, "MAXIMAAL-9",
                        "Element 35.20: Nummer Nederlands reisdocument mag maximaal 9 lang zijn in groep"
                                + " 35: Reisdocument."));

        controleerAanwezig(reisdocumentDatumsGeldigheid.getDatumUitgifte(),
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE011,
                        "Element 35.30: Datum uitgifte Nederlands reisdocument moet verplicht voorkomen in "
                                + "groep 35: Reisdocument."));
        controleerDatum(reisdocumentDatumsGeldigheid.getDatumUitgifte(),
                Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                        "Element 35.30: Datum uitgifte Nederlands reisdocument bevat een ongeldige datum."));

        controleerAanwezig(autoriteitVanAfgifteNederlandsReisdocument,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE011,
                        "Element 35.40: Autoriteit van afgifte Nederlands reisdocument moet verplicht voorkomen "
                                + "in groep 35: Reisdocument."));
        controleerCode(autoriteitVanAfgifteNederlandsReisdocument,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 35.40: Autoriteit van afgifte Nederlands reisdocument bevat een"
                                + " ongeldige waarde."));

        controleerAanwezig(reisdocumentDatumsGeldigheid.getDatumEindeGeldigheid(),
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE011,
                        "Element 35.50: Datum einde geldigheid Nederlands reisdocument moet verplicht voorkomen in"
                                + " groep 35: Reisdocument."));
        controleerDatum(reisdocumentDatumsGeldigheid.getDatumEindeGeldigheid(), Foutmelding.maakStructuurFout(
                herkomst, LogSeverity.ERROR,
                "Element 35.50: Datum einde geldigheid Nederlands reisdocument bevat een ongeldige datum."));

        if (inhoudingVermissingReisdocument.getDatum() != null) {
            controleerAanwezig(inhoudingVermissingReisdocument.getAanduiding(),
                    Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                            "Als element 35.60: Datum inhouding voorkomt, dan moet element 35.70: "
                                    + "Aanduiding inhouding verplicht voorkomen in groep 35: Reisdocument."));
        }
        controleerDatum(inhoudingVermissingReisdocument.getDatum(), Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.ERROR, "Element 35.60: Datum inhouding verplicht bevat een ongeldige datum."));

        if (inhoudingVermissingReisdocument.getAanduiding() != null) {
            controleerAanwezig(inhoudingVermissingReisdocument.getDatum(),
                    Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                            "Als element 35.70: Aanduiding inhouding voorkomt, dan moet element 35.60: "
                                    + "Datum inhouding verplicht voorkomen in groep 35: Reisdocument."));
        }
        Lo3PreconditieEnumCodeChecks.controleerCode(inhoudingVermissingReisdocument.getAanduiding(), Foutmelding
                .maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 35.70: Aanduiding inhouding bevat een ongeldige waarde."));

        controleerAanwezig(lengteHouder, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 35.80: Lengte houder moet verplicht voorkomen in groep 35: Reisdocument."));
    }

    private void controleerGroep36Signalering(final Lo3Signalering signalering, final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(signalering, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE054, "Element 36.10: Signalering bevat een ongeldige waarde."));
    }

    private void controleerGroep37BuitenlandsReisdocument(
            final Lo3AanduidingBezitBuitenlandsReisdocument aanduidingBezitBuitenlandsReisdocument,
            final Lo3Herkomst herkomst) {
        Lo3PreconditieEnumCodeChecks.controleerCode(aanduidingBezitBuitenlandsReisdocument,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 37.10: Aanduiding bezit buitenlands reisdocument bevat een " + "ongeldige waarde."));
    }

    /**
     * Gegevens over inhouding of vermissing van het reisdocument.
     */
    private static final class InhoudingVermissingReisdocument {
        private final Lo3Datum datum;
        private final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduiding;

        private InhoudingVermissingReisdocument(
                final Lo3Datum datum,
                final Lo3AanduidingInhoudingVermissingNederlandsReisdocument aanduiding) {
            this.datum = datum;
            this.aanduiding = aanduiding;
        }

        private Lo3Datum getDatum() {
            return datum;
        }

        private Lo3AanduidingInhoudingVermissingNederlandsReisdocument getAanduiding() {
            return aanduiding;
        }
    }

    /**
     * Geeft aan tussen welke datums het reisdocument geldig is.
     */
    private static final class ReisdocumentDatumsGeldigheid {
        private final Lo3Datum datumUitgifte;
        private final Lo3Datum datumEindeGeldigheid;

        private ReisdocumentDatumsGeldigheid(final Lo3Datum datumUitgifte, final Lo3Datum datumEindeGeldigheid) {
            this.datumUitgifte = datumUitgifte;
            this.datumEindeGeldigheid = datumEindeGeldigheid;
        }

        private Lo3Datum getDatumUitgifte() {
            return datumUitgifte;
        }

        private Lo3Datum getDatumEindeGeldigheid() {
            return datumEindeGeldigheid;
        }
    }
}
