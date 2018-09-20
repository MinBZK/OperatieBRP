/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import java.util.List;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.Preconditie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapels;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieOnjuist;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 05: Huwelijk.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3HuwelijkPrecondities extends AbstractLo3Precondities {

    /**
     * Controleer alle stapels.
     *
     * @param stapels
     *            stapels
     * @param persoonAnummer
     *            anummer van de persoon zelf (01.01.10)
     */
    public void controleerStapels(final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels, final Lo3Long persoonAnummer) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : stapels) {
            controleerStapel(stapel, persoonAnummer);
        }
    }

    private void controleerStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel, final Lo3Long persoonAnummer) {
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
        controleerVoorkomensOnderling(stapel);

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer preconditie voorkomens onderling of gegevens gelijk zijn e.d.
     *
     * @param stapel
     *            De stapel die gecontroleerd moet worden.
     */
    private void controleerVoorkomensOnderling(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {

        // Sorteer stapel op 85.10
        Lo3Stapels.sorteerCategorieenLg01(stapel.getCategorieen());

        Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigeCategorie = null;
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : stapel) {
            final Lo3IndicatieOnjuist onjuist = categorie.getHistorie().getIndicatieOnjuist();

            // Juiste voorkomen?
            if (Validatie.isElementGevuld(onjuist)) {
                continue;
            }

            if (vorigeCategorie != null
                && Validatie.isElementGevuld(vorigeCategorie.getInhoud().getSoortVerbintenis())
                && Validatie.isElementGevuld(categorie.getInhoud().getSoortVerbintenis())
                && AbstractLo3Element.equalsWaarde(categorie.getInhoud().getSoortVerbintenis(), vorigeCategorie.getInhoud().getSoortVerbintenis()))
            {
                controleerPreconditie074(vorigeCategorie, categorie);
                controleerPreconditie075(vorigeCategorie, categorie);
            }
            // vorigeCategorie wordt de huidige voor de volgende controle
            vorigeCategorie = categorie;
        }
    }

    /**
     * NIET alle elementen in groep 7 gelijk aan vorige voorkomen? en 15.10 gelijk aan vorige voorkomen en Groep 7
     * aanwezig??
     *
     * @param vorigeCategorie
     *            Vorige categorie.
     * @param categorie
     *            Categorie.
     */
    private void controleerPreconditie075(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigeCategorie, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final boolean groep07AanwezigHuidige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, categorie.getInhoud());
        final boolean groep07AanwezigVorige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, vorigeCategorie.getInhoud());

        if (groep07AanwezigHuidige && groep07AanwezigVorige && !isGroep7Gelijk(vorigeCategorie, categorie)) {
            // Inhoud van groep 7 is niet gelijk, preconditie fout 075
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.INFO, SoortMeldingCode.PRE075, null);
        }
    }

    /**
     * NIET alle elementen in groep 6 gelijk aan vorige voorkomen? en 15.10 gelijk aan vorige voorkomen en Groep 6
     * aanwezig??
     *
     * @param vorigeCategorie
     *            Vorige categorie.
     * @param categorie
     *            Categorie.
     */
    private void controleerPreconditie074(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> vorigeCategorie, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final boolean groep06AanwezigHuidige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, categorie.getInhoud());
        final boolean groep06AanwezigVorige = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, vorigeCategorie.getInhoud());

        if (groep06AanwezigHuidige && groep06AanwezigVorige && !isGroep6Gelijk(vorigeCategorie, categorie)) {
            // Inhoud van groep 6 is niet gelijk, preconditie fout 074
            Foutmelding.logMeldingFout(categorie.getLo3Herkomst(), LogSeverity.INFO, SoortMeldingCode.PRE074, null);
        }
    }

    /**
     * Controleer of groep6 in de meegegeven categorieen inhoudelijk gelijk is.
     *
     * @param categorie1
     *            Eerste categorie.
     * @param categorie2
     *            Tweede categorie
     * @return True als groep 6 inhoudelijk gelijk is.
     */
    private boolean isGroep6Gelijk(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie1, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie2) {
        return AbstractLo3Element.equalsWaarde(
            categorie2.getInhoud().getDatumSluitingHuwelijkOfAangaanGp(),
            categorie1.getInhoud().getDatumSluitingHuwelijkOfAangaanGp())
               && AbstractLo3Element.equalsWaarde(
                   categorie2.getInhoud().getGemeenteCodeSluitingHuwelijkOfAangaanGp(),
                   categorie1.getInhoud().getGemeenteCodeSluitingHuwelijkOfAangaanGp())
               && AbstractLo3Element.equalsWaarde(
                   categorie2.getInhoud().getLandCodeSluitingHuwelijkOfAangaanGp(),
                   categorie1.getInhoud().getLandCodeSluitingHuwelijkOfAangaanGp());
    }

    /**
     * Controleer of groep7 in de meegegeven categorieen inhoudelijk gelijk is.
     *
     * @param categorie1
     *            Eerste categorie.
     * @param categorie2
     *            Tweede categorie
     * @return True als groep 7 inhoudelijk gelijk is.
     */
    private boolean isGroep7Gelijk(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie1, final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie2) {
        return AbstractLo3Element.equalsWaarde(categorie2.getInhoud().getDatumOntbindingHuwelijkOfGp(), categorie1.getInhoud()
                                                                                                                  .getDatumOntbindingHuwelijkOfGp())
               && AbstractLo3Element.equalsWaarde(
                   categorie2.getInhoud().getGemeenteCodeOntbindingHuwelijkOfGp(),
                   categorie1.getInhoud().getGemeenteCodeOntbindingHuwelijkOfGp())
               && AbstractLo3Element.equalsWaarde(
                   categorie2.getInhoud().getLandCodeOntbindingHuwelijkOfGp(),
                   categorie1.getInhoud().getLandCodeOntbindingHuwelijkOfGp())
               && AbstractLo3Element.equalsWaarde(
                   categorie2.getInhoud().getRedenOntbindingHuwelijkOfGpCode(),
                   categorie1.getInhoud().getRedenOntbindingHuwelijkOfGpCode());
    }

    /**
     * Controleer precondities op categorie niveau.
     *
     * @param categorie
     *            categorie
     */
    /* Cyclomatic Complexity: Code is duidelijker als alle checks in 1 methode staan */
    private void controleerCategorie(final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();
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
            controleerHuwerlijkOfGpInhoud(inhoud, groep01Aanwezig, groep02Aanwezig, groep03Aanwezig, groep04Aanwezig, groep15Aanwezig, historie, herkomst);
        }

        if (groep15Aanwezig) {
            controleerGroep15SoortVerbintenis(inhoud.getSoortVerbintenis(), herkomst);
        }
    }

    private void controleerHuwerlijkOfGpInhoud(
        final Lo3HuwelijkOfGpInhoud inhoud,
        final boolean groep01Aanwezig,
        final boolean groep02Aanwezig,
        final boolean groep03Aanwezig,
        final boolean groep04Aanwezig,
        final boolean groep15Aanwezig,
        final Lo3Historie historie,
        final Lo3Herkomst herkomst)
    {
        // Groep 01: Identificatienummers
        if (groep01Aanwezig) {
            controleerGroep01Identificatienummers(
                inhoud.getaNummer(),
                inhoud.getBurgerservicenummer(),
                historie.getIngangsdatumGeldigheid(),
                herkomst,
                false);
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
            controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        }

        // Groep 15: Soort verbintenis
        if (!groep15Aanwezig) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE018, null);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB003)
    @Preconditie({SoortMeldingCode.PRE024, SoortMeldingCode.PRE027, SoortMeldingCode.PRE088 })
    private void controleerGroep06Huwelijkssluiting(
        final Lo3Datum datumSluitingHuwelijkOfAangaanGp,
        final Lo3GemeenteCode gemeenteCodeSluitingHuwelijkOfAangaanGp,
        final Lo3LandCode landCodeSluitingHuwelijkOfAangaanGp,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(datumSluitingHuwelijkOfAangaanGp, Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE088, null));
        controleerDatum(
            datumSluitingHuwelijkOfAangaanGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0610));

        controleerAanwezig(
            gemeenteCodeSluitingHuwelijkOfAangaanGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0620));

        controleerAanwezig(
            landCodeSluitingHuwelijkOfAangaanGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE024, Lo3ElementEnum.ELEMENT_0630));
        controleerCode(
            landCodeSluitingHuwelijkOfAangaanGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE024, Lo3ElementEnum.ELEMENT_0630));

        if (landCodeSluitingHuwelijkOfAangaanGp != null && landCodeSluitingHuwelijkOfAangaanGp.isOnbekend()) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB003, null);
        }

        controleerNederlandseGemeente(
            landCodeSluitingHuwelijkOfAangaanGp,
            gemeenteCodeSluitingHuwelijkOfAangaanGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE027, Lo3ElementEnum.ELEMENT_0630));
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB004)
    @Preconditie({SoortMeldingCode.PRE028, SoortMeldingCode.PRE029, SoortMeldingCode.PRE054 })
    private void controleerGroep07OnbindingHuwelijk(
        final Lo3Datum datumOntbindingHuwelijkOfGp,
        final Lo3GemeenteCode gemeenteCodeOntbindingHuwelijkOfGp,
        final Lo3LandCode landCodeOntbindingHuwelijkOfGp,
        final Lo3RedenOntbindingHuwelijkOfGpCode redenOntbindingHuwelijkOfGpCode,
        final Lo3Herkomst herkomst)
    {
        controleerAanwezig(
            datumOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0710));
        controleerDatum(
            datumOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_0710));

        controleerAanwezig(
            gemeenteCodeOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_0720));

        controleerAanwezig(
            landCodeOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE028, Lo3ElementEnum.ELEMENT_0730));
        controleerCode(
            landCodeOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE028, Lo3ElementEnum.ELEMENT_0730));

        if (landCodeOntbindingHuwelijkOfGp != null && landCodeOntbindingHuwelijkOfGp.isOnbekend()) {
            Foutmelding.logMeldingFoutInfo(herkomst, SoortMeldingCode.BIJZ_CONV_LB004, null);
        }

        controleerNederlandseGemeente(
            landCodeOntbindingHuwelijkOfGp,
            gemeenteCodeOntbindingHuwelijkOfGp,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE029, Lo3ElementEnum.ELEMENT_0730));

        controleerCode(
            redenOntbindingHuwelijkOfGpCode,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_0740));
    }

    private void controleerGroep15SoortVerbintenis(final Lo3SoortVerbintenis soortVerbintenis, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            soortVerbintenis,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_1510));

        if (!Lo3SoortVerbintenisEnum.isPartnerschap(soortVerbintenis) && !Lo3SoortVerbintenisEnum.isHuwelijk(soortVerbintenis)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE042, null);
        }
    }
}
