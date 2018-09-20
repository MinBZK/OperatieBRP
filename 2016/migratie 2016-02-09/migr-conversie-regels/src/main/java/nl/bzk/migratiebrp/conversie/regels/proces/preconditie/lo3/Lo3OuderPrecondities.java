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
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;

import org.springframework.stereotype.Component;

/**
 * Preconditie controles voor categorie 02/03: Ouder 1/2.
 * 
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
@Component
public final class Lo3OuderPrecondities extends AbstractLo3Precondities {

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     * @param persoonAnummer
     *            anummer van de persoon zelf (01.01.10)
     */
    public void controleerStapel(final Lo3Stapel<Lo3OuderInhoud> stapel, final Lo3Long persoonAnummer) {
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
        controleerPreconditie113(stapel, persoonAnummer);

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        final boolean groep1Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP01, inhoud);
        final boolean groep2Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP02, inhoud);
        final boolean groep3Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP03, inhoud);
        final boolean groep4Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP04, inhoud);
        final boolean groep62Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP62, inhoud);

        // Groep 02: Naam
        if (!groep2Aanwezig) {

            controleerJuridischGeenOuder(herkomst, categorie);
            // Groep 01: Identificatienummers
            if (groep1Aanwezig || groep3Aanwezig || groep4Aanwezig || groep62Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE049, null);
            }

        } else {
            controleerGroep02Naam(
                inhoud.getVoornamen(),
                inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoorvoegselGeslachtsnaam(),
                inhoud.getGeslachtsnaam(),
                herkomst,
                false);

            // Groep 01: Identificatienummers
            if (groep1Aanwezig) {
                controleerGroep01Identificatienummers(
                    inhoud.getaNummer(),
                    inhoud.getBurgerservicenummer(),
                    historie.getIngangsdatumGeldigheid(),
                    herkomst,
                    false);
            }

            // Groep 03: Geboorte
            if (groep3Aanwezig) {
                controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(), inhoud.getGeboorteLandCode(), herkomst);
            }

            // Groep 04: Geslachtsaanduiding
            if (groep4Aanwezig) {
                controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
            }

            // Groep 62: Familierechtelijke betrekking
            if (!groep62Aanwezig) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.PRE069, null);
            } else {
                controleerGroep62FamilierechtelijkeBetrekking(inhoud.getFamilierechtelijkeBetrekking(), herkomst);
            }

            controleerOnbekendeOuder(herkomst, categorie);
            controleerZwakkeAdoptie(herkomst, categorie);
        }
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB021)
    private void controleerZwakkeAdoptie(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3Documentatie docs = categorie.getDocumentatie();
        if (docs.isDocument() && docs.getBeschrijvingDocument() != null) {
            final String zwakHaagsAdoptieverdrag = "akte (zwak) Haags adoptieverdrag".toLowerCase();
            final String zwakConflictenrechtAdoptie = "akte (zwak) Wet conflictenrecht adoptie".toLowerCase();

            final String docBeschrijving = Lo3String.unwrap(docs.getBeschrijvingDocument()).toLowerCase();
            if (docBeschrijving.startsWith(zwakHaagsAdoptieverdrag) || docBeschrijving.startsWith(zwakConflictenrechtAdoptie)) {
                Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB021, null);
            }
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB013)
    private void controleerOnbekendeOuder(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final Lo3Historie historie = categorie.getHistorie();
        final boolean groep81Aanwezig = isAkteAanwezig(documentatie);
        final boolean groep82Aanwezig = isDocumentAanwezig(documentatie);
        final boolean groep85Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, historie);
        final boolean groep86Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP86, historie);

        if ((groep81Aanwezig || groep82Aanwezig) && groep85Aanwezig && groep86Aanwezig && inhoud.isOnbekendeOuder()) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB013, null);
        }
    }

    @BijzondereSituatie(SoortMeldingCode.BIJZ_CONV_LB014)
    private void controleerJuridischGeenOuder(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final Lo3Historie historie = categorie.getHistorie();
        final boolean groep81Aanwezig = isAkteAanwezig(documentatie);
        final boolean groep82Aanwezig = isDocumentAanwezig(documentatie);
        final boolean groep85Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP85, historie);
        final boolean groep86Aanwezig = Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP86, historie);

        if ((groep81Aanwezig || groep82Aanwezig) && groep85Aanwezig && groep86Aanwezig && inhoud.isJurischeGeenOuder()) {
            Foutmelding.logMeldingFout(herkomst, LogSeverity.INFO, SoortMeldingCode.BIJZ_CONV_LB014, null);
        }
    }

    private void controleerGroep62FamilierechtelijkeBetrekking(final Lo3Datum familierechtelijkeBetrekking, final Lo3Herkomst herkomst) {
        controleerAanwezig(
            familierechtelijkeBetrekking,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.WARNING, SoortMeldingCode.STRUC_VERPLICHT, Lo3ElementEnum.ELEMENT_6210));
        controleerDatum(
            familierechtelijkeBetrekking,
            Foutmelding.maakMeldingFout(herkomst, LogSeverity.ERROR, SoortMeldingCode.STRUC_DATUM, Lo3ElementEnum.ELEMENT_6210));
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    /**
     * Indicatie ouder 1 of 2.
     */
    protected enum Ouder {
        /** Ouder 1. */
        OUDER_1("02: Ouder 1"),
        /** Ouder 2. */
        OUDER_2("03: Ouder 2");
        private final String ouder;

        /**
         * Ouder.
         * 
         * @param ouder
         *            omschrijving
         */
        Ouder(final String ouder) {
            this.ouder = ouder;
        }

        @Override
        public String toString() {
            return ouder;
        }
    }
}
