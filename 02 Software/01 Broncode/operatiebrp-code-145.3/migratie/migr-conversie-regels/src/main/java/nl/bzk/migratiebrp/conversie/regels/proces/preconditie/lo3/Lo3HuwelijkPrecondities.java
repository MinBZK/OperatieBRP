/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapels;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;
import nl.bzk.migratiebrp.conversie.regels.proces.util.Lo3VerbintenisUtil;

/**
 * Preconditie controles voor categorie 05: Huwelijk.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3HuwelijkPrecondities extends AbstractLo3Precondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3HuwelijkPrecondities(final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer alle stapels.
     * @param stapels stapels
     * @param persoonAnummer anummer van de persoon zelf (01.01.10)
     */
    void controleerStapels(final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels, final Lo3String persoonAnummer) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : stapels) {
            controleerStapel(stapel, persoonAnummer);
        }
    }

    private void controleerStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel, final Lo3String persoonAnummer) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

        controleerPreconditie055(stapel);
        controleerPreconditie056(stapel);
        controleerPreconditie113(stapel, persoonAnummer);
        splitsEnControleerVoorkomens(stapel);

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer preconditie voorkomens onderling of gegevens gelijk zijn e.d.
     * @param stapel De stapel die gecontroleerd moet worden.
     */
    private void splitsEnControleerVoorkomens(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        // Sorteer stapel op 85.10
        Lo3Stapels.sorteerCategorieenLg01(stapel.getCategorieen());
        Lo3VerbintenisUtil.splitsEnControleerVerbintenissen(stapel);
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep01Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud);
        final boolean groep02Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud);
        final boolean groep03Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud);
        final boolean groep04Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP04, inhoud);
        final boolean groep06Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, inhoud);
        final boolean groep07Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, inhoud);
        final boolean groep15Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP15, inhoud);

        if ((groep01Aanwezig || groep03Aanwezig || groep04Aanwezig) && !groep02Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE039, null);
        }

        if (groep06Aanwezig && groep07Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE040, null);
        }

        // Groep 06: Huwelijkssluiting
        if (groep06Aanwezig) {
            controleerGroep06Huwelijkssluiting(
                    inhoud.getDatumSluitingHuwelijkOfAangaanGp(),
                    inhoud.getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                    inhoud.getLandCodeSluitingHuwelijkOfAangaanGp(),
                    herkomst);
        }

        // Groep 07: Ontbinding huwelijk
        if (groep07Aanwezig) {
            controleerGroep07OnbindingHuwelijk(
                    inhoud.getDatumOntbindingHuwelijkOfGp(),
                    inhoud.getGemeenteCodeOntbindingHuwelijkOfGp(),
                    inhoud.getLandCodeOntbindingHuwelijkOfGp(),
                    inhoud.getRedenOntbindingHuwelijkOfGpCode(),
                    herkomst);
        }

        if (groep06Aanwezig || groep07Aanwezig) {
            controleerHuwelijkOfGpInhoud(categorie, groep01Aanwezig, groep02Aanwezig, groep03Aanwezig, groep04Aanwezig, groep15Aanwezig);
        }

        if (groep15Aanwezig) {
            controleerGroep15SoortVerbintenis(inhoud.getSoortVerbintenis(), herkomst);
        }
    }

    private void controleerHuwelijkOfGpInhoud(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie,
            final boolean groep01Aanwezig,
            final boolean groep02Aanwezig,
            final boolean groep03Aanwezig,
            final boolean groep04Aanwezig,
            final boolean groep15Aanwezig) {
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 01: Identificatienummers
        if (groep01Aanwezig) {
            controleerGroep01Identificatienummers(inhoud.getaNummer(), inhoud.getBurgerservicenummer(), historie.getIngangsdatumGeldigheid(), herkomst, false);
        }

        // Groep 02: Naam
        if (!groep02Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE041, null);
        } else {
            controleerGroep02Naam(
                    inhoud.getVoornamen(),
                    inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(),
                    inhoud.getGeslachtsnaam(),
                    herkomst,
                    false);
        }

        // Groep 03: Geboorte
        if (!groep03Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0310);
        } else {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
        }

        // Groep 04: Geslachtsaanduiding
        if (groep04Aanwezig) {
            controleerGroep04Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        }

        // Groep 15: Soort verbintenis
        if (!groep15Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE018, null);
        }
    }

}
