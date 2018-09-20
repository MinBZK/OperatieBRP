/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import java.util.List;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 05: Huwelijk.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3HuwelijkPrecondities extends Lo3Precondities {

    /**
     * Controleer alle stapels.
     * 
     * @param stapels
     *            stapels
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie055(stapel);
        controleerPreconditie056(stapel);
        controleerPreconditie068(stapel);

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    // CHECKSTYLE:OFF - Cyclomatic Complexity: Code is duidelijker als alle checks in 1 methode staan
    private void controleerCategorie(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        // CHECKSTLE:ON
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep01Aanwezig = isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer());
        final boolean groep02Aanwezig =
                isGroepAanwezig(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                        inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam());
        final boolean groep03Aanwezig =
                isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                        inhoud.getGeboorteLandCode());
        final boolean groep04Aanwezig = isGroepAanwezig(inhoud.getGeslachtsaanduiding());

        final boolean groep06aanwezig =
                isGroepAanwezig(inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                        inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                        inhoud.getLandCodeSluitingHuwelijkOfAangaanGp());
        final boolean groep07aanwezig =
                isGroepAanwezig(inhoud.getDatumOntbindingHuwelijkOfGp(),
                        inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(), inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                        inhoud.getRedenOntbindingHuwelijkOfGpCode());

        if ((groep01Aanwezig || groep03Aanwezig || groep04Aanwezig) && !groep02Aanwezig) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE039,
                    "Als groep 01: Identificatienummers of groep 03: Geboorte of groep 04: " + "Geslachtsaanduiding "
                            + "voorkomt, moet groep 02: Naam verplicht voorkomen in categorie 05: " + "Huwelijk.")

            ;

        }

        if (groep06aanwezig && groep07aanwezig) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE040,
                    "Groep 06: Huwelijkssluiting en groep 07: Ontbinding huwelijk mogen niet tegelijkertijd "
                            + "voorkomen in categorie 05: Huwelijk");

        }

        // Groep 06: Huwelijkssluiting
        if (groep06aanwezig) {
            controleerGroep06Huwelijkssluiting(inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                    inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                    inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(), herkomst);
        }

        // Groep 07: Ontbinding huwelijk
        if (groep07aanwezig) {
            controleerGroep07OnbindingHuwelijk(inhoud.getDatumOntbindingHuwelijkOfGp(),
                    inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(), inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                    inhoud.getRedenOntbindingHuwelijkOfGpCode(), herkomst);
        }

        if (groep06aanwezig || groep07aanwezig) {
            controleerHuwerlijkOfGpInhoud(inhoud, groep01Aanwezig, groep02Aanwezig, groep03Aanwezig, groep04Aanwezig,
                    historie, herkomst);
        }
    }

    private void controleerHuwerlijkOfGpInhoud(
            final Lo3HuwelijkOfGpInhoud inhoud,
            final boolean groep01Aanwezig,
            final boolean groep02Aanwezig,
            final boolean groep03Aanwezig,
            final boolean groep04Aanwezig,
            final Lo3Historie historie,
            final Lo3Herkomst herkomst) {
        // Groep 01: Identificatienummers
        if (groep01Aanwezig) {
            controleerGroep01Identificatienummers(inhoud.getaNummer(), inhoud.getBurgerservicenummer(),
                    historie.getIngangsdatumGeldigheid(), herkomst, false);
        }

        // Groep 02: Naam
        if (!groep02Aanwezig) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE041,
                    "Als groep 06: Huwelijksluiting of groep 07: Ontbinding voorkomt dan moet groep 02: "
                            + "Naam voorkomen in categorie 05: Huwelijk.");
        } else {
            controleerGroep02Naam(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam(), herkomst, false);
        }

        // Groep 03: Geboorte
        if (!groep03Aanwezig) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Als groep 06: Huwelijksluiting of groep 07: Ontbinding voorkomt dan moet groep" + " 03: "
                            + "Geboorte voorkomen in categorie 05: Huwelijk.");
        } else {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode(), herkomst);
        }

        // Groep 04: Geslachtsaanduiding
        if (groep04Aanwezig) {
            controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        }

        // Groep 15: Soort verbintenis
        if (!isGroepAanwezig(inhoud.getSoortVerbintenis())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE018,
                    "Groep 15: Soort verbintenis moet verplicht voorkomen als groep 06: Huwelijkssluiting "
                            + "of groep 07: Ontbinding huwelijk voorkomt in categorie 05: Huwelijk.");
        } else {
            controleerGroep15SoortVerbintenis(inhoud.getSoortVerbintenis(), herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerGroep06Huwelijkssluiting(
            final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
            final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp,
            final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(datumSluitingHuwelijkOfAangaanGp, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO, "Element 06.10: Datum sluiting is verplicht in groep 06: Huwelijkssluiting."));
        controleerDatum(datumSluitingHuwelijkOfAangaanGp, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 06.10: Datum sluiting bevat geen geldige datum."));

        controleerAanwezig(gemeenteCodeSluitingHuwelijkOfAangaanGp, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO, "Element 06.20: Plaats sluiting is verplicht in groep 06: Huwelijkssluiting."));

        controleerAanwezig(landCodeSluitingHuwelijkOfAangaanGp, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE024,
                "Element 06.30: Land sluiting is verplicht in groep 06: Huwelijkssluiting."));
        controleerCode(landCodeSluitingHuwelijkOfAangaanGp, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE024, "Element 06.30: Land sluiting bevat geen geldige waarde."));

        if (landCodeSluitingHuwelijkOfAangaanGp != null && landCodeSluitingHuwelijkOfAangaanGp.isOnbekend()) {
            Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB003);
        }

        controleerNederlandseGemeente(landCodeSluitingHuwelijkOfAangaanGp, gemeenteCodeSluitingHuwelijkOfAangaanGp,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE027,
                        "Als element 06.30: Land Nederland bevat, moet element 06.20: Plaats sluiting een Nederlandse "
                                + "gemeente bevatten."));
    }

    private void controleerGroep07OnbindingHuwelijk(
            final Lo3Datum datumOntbindingHuwelijkOfGp,
            final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
            final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
            final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(datumOntbindingHuwelijkOfGp, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 07.10: Datum ontbinding is verplicht in groep 07: Ontbinding " + "huwelijk."));
        controleerDatum(datumOntbindingHuwelijkOfGp, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 07.10: Datum ontbinding bevat geen geldige datum."));

        controleerAanwezig(gemeenteCodeOntbindingHuwelijkOfGp, Foutmelding.maakStructuurFout(herkomst,
                LogSeverity.INFO, "Element 07.20: Plaats ontbinding is verplicht in groep 07: Ontbinding huwelijk."));

        controleerAanwezig(landCodeOntbindingHuwelijkOfGp, Foutmelding.maakPreconditieFout(herkomst,
                LogSeverity.ERROR, Precondities.PRE028,
                "Element 07.30: Land ontbinding is verplicht in groep 07: Ontbinding huwelijk."));
        controleerCode(landCodeOntbindingHuwelijkOfGp, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE028, "Element 07.30: Land ontbinding bevat geen geldige code."));

        if (landCodeOntbindingHuwelijkOfGp != null && landCodeOntbindingHuwelijkOfGp.isOnbekend()) {
            Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB004);
        }

        controleerNederlandseGemeente(landCodeOntbindingHuwelijkOfGp, gemeenteCodeOntbindingHuwelijkOfGp,
                Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE029,
                        "Als element 07.30: Land Nederland bevat, moet element 07.20: Plaats ontbinding een "
                                + "Nederlandse gemeente bevatten."));

        controleerCode(redenOntbindingHuwelijkOfGpCode, Foutmelding.maakPreconditieFout(herkomst, LogSeverity.ERROR,
                Precondities.PRE054, "Element 07.40: Reden ontbinding bevat geen geldige waarde."));
    }

    private void controleerGroep15SoortVerbintenis(
            final Lo3SoortVerbintenis soortVerbintenis,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(soortVerbintenis, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 15.10: Soort verbintenis is verplicht in groep 15: Soort verbintenis."));
        Lo3PreconditieEnumCodeChecks.controleerCode(soortVerbintenis, Foutmelding
                .maakPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE054,
                        "Element 15.10: Soort verbintenis bevat geen geldige waarde."));

        if (!(Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.equalsElement(soortVerbintenis) || Lo3SoortVerbintenisEnum.HUWELIJK
                .equalsElement(soortVerbintenis))) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE042,
                    "Alleen de waarden 'H'(uwelijk) en 'P'(artnerschap) zijn toegestaan in element 15.10: "
                            + "Soort verbintenis.");
        }
    }
}
