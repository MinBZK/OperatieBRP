/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: verbintenis.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisVerbintenisPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisVerbintenisPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGroep81Akte(stapel);
        controleerGroep82Document(stapel);
        controleerGroep83Procedure(stapel);
        controleerGroep84Onjuist(stapel);
        controleerGroep85Geldigheid(stapel);
        controleerGroep86Opneming(stapel);

        controleerMaximaalTweeVoorkomens(stapel);

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 01: Identificatienummers
        controleerGroep1(inhoud, historie, herkomst);

        // Groep 02: Naam
        controleerGroep2(inhoud, herkomst);
        // Groep 03: Geboorte
        controleerGroep3(inhoud, herkomst);

        // Groep 04: Geslachtsaanduiding
        controleerGroep4(inhoud, herkomst);

        // Groep 06: Sluiting
        controleerGroep6(inhoud, herkomst);

        // Groep 07: Ontbinding
        controleerGroep7(inhoud, herkomst);

        // Groep 15: Soort verbintenis
        controleerGroep15(inhoud, herkomst);

    }

    private void controleerGroep15(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 15: Soort verbintenis
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP15, inhoud)) {
            controleerGroep15SoortVerbintenis(inhoud.getSoortVerbintenis(), herkomst);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG022, null);
        }
    }

    private void controleerGroep7(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 07: Ontbinding
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, inhoud)) {
            if (herkomst.isLo3ActueelVoorkomen()) {
                controleerGroep07OnbindingHuwelijk(
                        inhoud.getDatumOntbindingHuwelijkOfGp(),
                        inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                        inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                        inhoud.getRedenOntbindingHuwelijkOfGpCode(),
                        herkomst);

                //
                controleerNederland(inhoud.getLandCodeOntbindingHuwelijkOfGp(), herkomst, Lo3ElementEnum.ELEMENT_0730);
            } else {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG021, null);
            }
        }
    }

    private void controleerGroep6(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 06: Sluiting
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, inhoud)) {
            controleerGroep06Huwelijkssluiting(
                    inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                    inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                    inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(),
                    herkomst);

            //
            if (herkomst.isLo3ActueelVoorkomen()) {
                controleerNederland(inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(), herkomst, Lo3ElementEnum.ELEMENT_0630);
            }
        } else {
            if (!herkomst.isLo3ActueelVoorkomen()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG036, null);
            }
        }
    }

    private void controleerGroep4(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 04: Geslachtsaanduiding
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP04, inhoud)) {
            if (!herkomst.isLo3ActueelVoorkomen()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG055, null);
            } else {
                controleerGroep04Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
            }
        } else {
            if (herkomst.isLo3ActueelVoorkomen()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG035, null);
            }
        }
    }

    private void controleerGroep3(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 03: Geboorte
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud)) {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG020, null);
        }
    }

    private void controleerGroep2(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Herkomst herkomst) {
        // Groep 02: Naam
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud)) {
            controleerGroep02Naam(
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam(),
                    herkomst,
                    false);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG019, null);
        }
    }

    private void controleerGroep1(final Lo3HuwelijkOfGpInhoud inhoud, final Lo3Historie historie, final Lo3Herkomst herkomst) {
        // Groep 01: Identificatienummers
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud)) {
            if (!herkomst.isLo3ActueelVoorkomen()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG056, null);
            } else {
                controleerGroep01Identificatienummers(
                        inhoud.getaNummer(),
                        inhoud.getBurgerservicenummer(),
                        historie.getIngangsdatumGeldigheid(),
                        herkomst,
                        false);
            }
        }
    }

}
