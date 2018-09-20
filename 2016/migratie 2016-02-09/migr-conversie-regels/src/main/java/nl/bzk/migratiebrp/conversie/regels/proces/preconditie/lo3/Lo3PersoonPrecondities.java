/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.BijzondereSituatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 01: persoon.
 * 
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3PersoonPrecondities extends AbstractLo3Precondities {

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
        controleerGeldigheidDatumActueel(stapel);
        controleerOnderzoek(stapel);

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
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 01: Indentificatienummers
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE005, null);
        } else {
            controleerGroep01Identificatienummers(
                inhoud.getANummer(),
                inhoud.getBurgerservicenummer(),
                historie.getIngangsdatumGeldigheid(),
                herkomst,
                true);
        }

        // Groep 02: Naam
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE034, null);
        } else {
            controleerGroep02Naam(
                inhoud.getVoornamen(),
                inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoorvoegselGeslachtsnaam(),
                inhoud.getGeslachtsnaam(),
                herkomst,
                true);
            controleerBijzondereSituatieLB035(
                inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getGeslachtsaanduiding(),
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB035, null));
        }

        // Groep 03: Geboorte
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud)) {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
        }

        // Groep 04: Geslachtsaanduiding
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP04, inhoud)) {
            controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        }

        // Groep 20: A-nummerverwijzigingen
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP20, inhoud)) {
            controleerGroep20ANummerverwijzingen(inhoud.getVorigANummer(), inhoud.getVolgendANummer(), herkomst);
            controleerVorigAnummer(inhoud, herkomst);
            controleerVolgendAnummer(inhoud, herkomst);
        }

        // Groep 61: Naamgebruik
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP61, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB040, null);
        } else {
            controleerGroep61Naamgebruik(inhoud.getAanduidingNaamgebruikCode(), herkomst);
        }

        // Groep 88: RNI-deelnemer
        if (isRNIDeelnemerAanwezig(documentatie)) {
            controleerGroep88RNIDeelnemer(documentatie.getRniDeelnemerCode(), herkomst);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    @BijzondereSituatie({SoortMeldingCode.BIJZ_CONV_LB005, SoortMeldingCode.BIJZ_CONV_LB006 })
    private void controleerVorigAnummer(final Lo3PersoonInhoud inhoud, final Lo3Herkomst herkomst) {
        final Long vorigAnummer = Lo3Long.unwrap(inhoud.getVorigANummer());
        final Long aNummer = Lo3Long.unwrap(inhoud.getANummer());
        if (vorigAnummer != null) {
            if (vorigAnummer.equals(aNummer)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB006, null);
            } else {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB005, null);
            }
        }
    }

    @BijzondereSituatie({SoortMeldingCode.BIJZ_CONV_LB007, SoortMeldingCode.BIJZ_CONV_LB008 })
    private void controleerVolgendAnummer(final Lo3PersoonInhoud inhoud, final Lo3Herkomst herkomst) {
        final Long volgendAnummer = Lo3Long.unwrap(inhoud.getVolgendANummer());
        final Long aNummer = Lo3Long.unwrap(inhoud.getANummer());
        if (volgendAnummer != null) {
            if (volgendAnummer.equals(aNummer)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB007, null);
            } else {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB008, null);
            }
        }
    }

    private void controleerGroep20ANummerverwijzingen(final Lo3Long vorigANummer, final Lo3Long volgendANummer, final Lo3Herkomst herkomst) {
        controleerAnummer(
            vorigANummer,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_2010));
        controleerAnummer(
            volgendANummer,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_IDENTIFICATIE, Lo3ElementEnum.ELEMENT_2020));
    }

    private void controleerGroep61Naamgebruik(final Lo3AanduidingNaamgebruikCode aanduidingNaamgebruikCode, final Lo3Herkomst herkomst) {
        controleerAanwezig(aanduidingNaamgebruikCode, Foutmelding.maakMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB040, null));
        if (Validatie.isElementGevuld(aanduidingNaamgebruikCode)) {
            Lo3PreconditieEnumCodeChecks.controleerCode(
                aanduidingNaamgebruikCode,
                Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE054, Lo3ElementEnum.ELEMENT_6110));
        }
    }
}
