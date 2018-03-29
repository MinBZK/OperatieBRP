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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: persoon.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisPersoonPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisPersoonPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer precondities op stapel niveau.
     * @param stapel stapel
     */
    public void controleerStapel(final Lo3Stapel<Lo3PersoonInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerGroep82Document(stapel);
        controleerGroep83Procedure(stapel);
        controleerGroep84Onjuist(stapel);
        controleerGroep86Opneming(stapel);
        controleerGroep88RNIDeelnemer(stapel);

        controleerMaximaalTweeVoorkomens(stapel);

        if (stapel.size() == 1) {
            // Groep 81: Akte
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP81, stapel.get(0).getDocumentatie())) {
                Foutmelding.logMeldingFout(stapel.get(0).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG047, null);
            }
            // Groep 85: Geldigheid
            if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, stapel.get(0).getHistorie())) {
                Foutmelding.logMeldingFout(stapel.get(0).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG049, null);
            }
        } else {
            // Groep 81: Akte
            if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP81, stapel.get(0).getDocumentatie())) {
                Foutmelding.logMeldingFout(stapel.get(0).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG048, null);
            }
            // Groep 85: Geldigheid
            if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, stapel.get(0).getHistorie())) {
                Foutmelding.logMeldingFout(stapel.get(0).getLo3Herkomst(), LogSeverity.ERROR, SoortMeldingCode.TG050, null);
            }
        }

        for (final Lo3Categorie<Lo3PersoonInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    /**
     * Controleer precondities op categorie niveau.
     * @param categorie categorie
     */
    private void controleerCategorie(final Lo3Categorie<Lo3PersoonInhoud> categorie) {
        final Lo3PersoonInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();
        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 01: Indentificatienummers
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud)) {
            controleerGroep01Identificatienummers(
                    inhoud.getANummer(),
                    inhoud.getBurgerservicenummer(),
                    historie.getIngangsdatumGeldigheid(),
                    herkomst,
                    true);
        } else {
            if (herkomst.isLo3ActueelVoorkomen()) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG051, null);
            }
        }

        // Groep 02: Naam
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud)) {
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
                    Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.BIJZ_CONV_LB035, null));
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG052, null);
        }

        // Groep 03: Geboorte
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud)) {
            controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG053, null);
        }

        // Groep 04: Geslachtsaanduiding
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP04, inhoud)) {
            controleerGroep04Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
        } else {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG054, null);
        }

        // Groep 20: A-nummerverwijzigingen
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP20, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG001, null);
        }

        // Groep 61: Naamgebruik
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP61, inhoud)) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.TG002, null);
        }
    }

}
