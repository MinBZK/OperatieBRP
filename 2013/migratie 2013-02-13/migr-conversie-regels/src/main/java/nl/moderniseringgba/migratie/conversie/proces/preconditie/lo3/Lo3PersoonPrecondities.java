/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.preconditie.lo3;

import nl.moderniseringgba.migratie.BijzondereSituaties;
import nl.moderniseringgba.migratie.Precondities;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 01: persoon.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3PersoonPrecondities extends Lo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3PersoonInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie050(stapel);
        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * 
     * @param categorie
     *            categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
        final Lo3PersoonInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 01: Indentificatienummers
        if (!isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE005,
                    "Groep 01: Identificatienummers moet verplicht voorkomen in categorie 01: Persoon.")

            ;
        } else {
            controleerGroep01Identificatienummers(inhoud.getaNummer(), inhoud.getBurgerservicenummer(),
                    historie.getIngangsdatumGeldigheid(), herkomst, true);
        }

        // Groep 02: Naam
        if (!isGroepAanwezig(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam())) {
            Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE034,
                    "Groep 02: Naam moet verplicht voorkomen in categorie 01: Persoon.");
        } else {
            controleerGroep02Naam(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam(), herkomst, true);
        }

        // Groep 03: Geboorte
        if (!isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                inhoud.getGeboorteLandCode())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 03: Geboorte moet verplicht voorkomen in categorie 01: Persoon.");
        } else {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode(), herkomst);
        }

        // Groep 04: Geslachtsaanduiding
        if (!isGroepAanwezig(inhoud.getGeslachtsaanduiding())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 04: Geslachtsaanduiding moet verplicht voorkomen in categorie 01: Persoon.");
        } else {
            controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        }

        // Groep 20: A-nummerverwijzigingen
        if (isGroepAanwezig(inhoud.getVorigANummer(), inhoud.getVolgendANummer())) {
            controleerGroep20ANummerverwijzingen(inhoud.getVorigANummer(), inhoud.getVolgendANummer(), herkomst);
            controleerVorigAnummer(inhoud, herkomst);
            controleerVolgendAnummer(inhoud, herkomst);
        }

        // Groep 61: Naamgebruik
        if (!isGroepAanwezig(inhoud.getAanduidingNaamgebruikCode())) {
            Foutmelding.logStructuurFout(herkomst, LogSeverity.INFO,
                    "Groep 61: Naamgebruik moet verplicht voorkomen in categorie 01: Persoon.");
        } else {
            controleerGroep61Naamgebruik(inhoud.getAanduidingNaamgebruikCode(), herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    private void controleerVorigAnummer(final Lo3PersoonInhoud inhoud, final Lo3Herkomst herkomst) {
        final Long vorigAnummer = inhoud.getVorigANummer();
        final Long aNummer = inhoud.getaNummer();
        if (vorigAnummer != null) {
            if (vorigAnummer.equals(aNummer)) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB006);
            } else {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB005);
            }
        }
    }

    private void controleerVolgendAnummer(final Lo3PersoonInhoud inhoud, final Lo3Herkomst herkomst) {
        final Long volgendAnummer = inhoud.getVolgendANummer();
        final Long aNummer = inhoud.getaNummer();
        if (volgendAnummer != null) {
            if (volgendAnummer.equals(aNummer)) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB007);
            } else {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB008);
            }
        }
    }

    private void controleerGroep20ANummerverwijzingen(
            final Long vorigANummer,
            final Long volgendANummer,
            final Lo3Herkomst herkomst) {
        controleerAnummer(vorigANummer, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 20.10: Vorig a-nummer voldoet niet aan de inhoudelijke voorwaarden."));
        controleerAnummer(volgendANummer, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 20.20: Volgend a-nummer voldoet niet aan de inhoudelijke voorwaarden."));
    }

    private void controleerGroep61Naamgebruik(
            final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingNaamgebruikCode, Foutmelding.maakStructuurFout(herkomst, LogSeverity.INFO,
                "Element 61.10: Aanduiding naamgebruik is verplicht in groep 61: Naamgebruik."));
        Lo3PreconditieEnumCodeChecks.controleerCode(aanduidingNaamgebruikCode, Foutmelding.maakPreconditieFout(
                herkomst, LogSeverity.ERROR, Precondities.PRE054,
                "Element 61.10: Aanduiding naamgebruik bevat geen geldige waarde."));
    }

}
