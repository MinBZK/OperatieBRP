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
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Documentatie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.logging.LogSeverity;

/**
 * Preconditie controles voor categorie 02/03: Ouder 1/2.
 * 
 * Maakt gebruik van de {@link nl.moderniseringgba.migratie.conversie.proces.logging.Logging#log Logging.log} methode.
 */
public class Lo3OuderPrecondities extends Lo3Precondities {

    private static final String PUNT = ".";

    private final Ouder ouder;

    /**
     * Constructor.
     * 
     * @param ouder
     *            ouder 1 of 2
     */
    protected Lo3OuderPrecondities(final Ouder ouder) {
        this.ouder = ouder;
    }

    /**
     * Controleer alle stapels.
     * 
     * @param stapels
     *            stapels
     */
    public final void controleerStapels(final List<Lo3Stapel<Lo3OuderInhoud>> stapels) {
        if (stapels == null) {
            return;
        }

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            controleerStapel(stapel);
        }
    }

    /**
     * Controleer precondities op stapel niveau.
     * 
     * @param stapel
     *            stapel
     */
    public final void controleerStapel(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return;
        }

        controleerDocumentOfAkte(stapel);
        controleerGeldigheidAanwezig(stapel);
        controleerOpnemingAanwezig(stapel);
        controleerOnjuist(stapel);

        controleerPreconditie055(stapel);

        for (final Lo3Categorie<Lo3OuderInhoud> categorie : stapel) {
            controleerCategorie(categorie);
        }
    }

    private void controleerCategorie(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Historie historie = categorie.getHistorie();

        final Lo3Herkomst herkomst = categorie.getLo3Herkomst();

        // Groep 02: Naam
        if (!isGroepAanwezig(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam())) {

            controleerJuridischGeenOuder(herkomst, categorie);
            // Groep 01: Identificatienummers
            if (isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer())) {

                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE049,
                        "Groep 01: Identificatienummers mag niet voorkomen als groep 02: Naam niet voorkomt in"
                                + " categorie " + ouder + PUNT);
            }

            // Groep 03: Geboorte
            if (isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode())) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE049,
                        "Groep 03: Geboorte mag niet voorkomen als groep 02: Naam niet voorkomt in categorie "
                                + ouder + PUNT);
            }

            // Groep 04: Geslachtsaanduiding
            if (isGroepAanwezig(inhoud.getGeslachtsaanduiding())) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE049,
                        "Groep 04: Geslachtsaanduiding mag niet voorkomen als groep 02: Naam niet voorkomt in "
                                + "categorie " + ouder + PUNT);
            }

            // Groep 62: Familierechtelijke betrekking
            if (isGroepAanwezig(inhoud.getFamilierechtelijkeBetrekking())) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE049,
                        "Groep 62: Familierechtelijke betrekking mag niet voorkomen als groep 02: Naam"
                                + " niet voorkomt in categorie " + ouder + PUNT);
            }

        } else {
            controleerGroep02Naam(inhoud.getVoornamen(), inhoud.getAdellijkeTitelPredikaatCode(),
                    inhoud.getVoorvoegselGeslachtsnaam(), inhoud.getGeslachtsnaam(), herkomst, false);

            // Groep 01: Identificatienummers
            if (isGroepAanwezig(inhoud.getaNummer(), inhoud.getBurgerservicenummer())) {
                controleerGroep01Identificatienummers(inhoud.getaNummer(), inhoud.getBurgerservicenummer(),
                        historie.getIngangsdatumGeldigheid(), herkomst, false);
            }

            // Groep 03: Geboorte
            if (isGroepAanwezig(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                    inhoud.getGeboorteLandCode())) {
                controleerGroep03Geboorte(inhoud.getGeboortedatum(), inhoud.getGeboorteGemeenteCode(),
                        inhoud.getGeboorteLandCode(), herkomst);
            }

            // Groep 04: Geslachtsaanduiding
            if (isGroepAanwezig(inhoud.getGeslachtsaanduiding())) {
                controleerGroep4Geslachtsaanduiding(inhoud.getGeslachtsaanduiding(), herkomst);
            }

            // Groep 62: Familierechtelijke betrekking
            if (!isGroepAanwezig(inhoud.getFamilierechtelijkeBetrekking())) {
                Foutmelding.logPreconditieFout(herkomst, LogSeverity.ERROR, Precondities.PRE069,
                        "Groep 62: Familierechtelijke betrekking moet voorkomen als groep 02: Naam"
                                + " voorkomt in categorie " + ouder + PUNT);
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

    private void controleerZwakkeAdoptie(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3Documentatie docs = categorie.getDocumentatie();
        if (docs.isDocument()) {
            final String zwakHaagsAdoptieverdrag = "akte (zwak) Haags adoptieverdrag".toLowerCase();
            final String zwakConflictenrechtAdoptie = "akte (zwak) Wet conflictenrecht adoptie".toLowerCase();

            final String docBeschrijving = docs.getBeschrijvingDocument().toLowerCase();
            if (docBeschrijving.startsWith(zwakHaagsAdoptieverdrag)
                    || docBeschrijving.startsWith(zwakConflictenrechtAdoptie)) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB021);
            }
        }
    }

    private void controleerOnbekendeOuder(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final boolean groep81Aanwezig = isAkteAanwezig(documentatie, herkomst);
        final boolean groep82Aanwezig = isDocumentAanwezig(documentatie, herkomst);
        final boolean groep85Aanwezig = getIngangsDatumGeldigheid(categorie) != null;
        final boolean groep86Aanwezig = getDatumOpneming(categorie) != null;

        if ((groep81Aanwezig || groep82Aanwezig) && groep85Aanwezig && groep86Aanwezig) {
            if (inhoud.isOnbekendeOuder()) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB013);
            }
        }
    }

    private void
            controleerJuridischGeenOuder(final Lo3Herkomst herkomst, final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();
        final Lo3Documentatie documentatie = categorie.getDocumentatie();
        final boolean groep81Aanwezig = isAkteAanwezig(documentatie, herkomst);
        final boolean groep82Aanwezig = isDocumentAanwezig(documentatie, herkomst);
        final boolean groep85Aanwezig = getIngangsDatumGeldigheid(categorie) != null;
        final boolean groep86Aanwezig = getDatumOpneming(categorie) != null;

        if ((groep81Aanwezig || groep82Aanwezig) && groep85Aanwezig && groep86Aanwezig) {
            if (inhoud.isJurischeGeenOuder()) {
                Foutmelding.logBijzondereSituatieFout(herkomst, BijzondereSituaties.BIJZ_CONV_LB014);
            }
        }
    }

    private void controleerGroep62FamilierechtelijkeBetrekking(
            final Lo3Datum familierechtelijkeBetrekking,
            final Lo3Herkomst herkomst) {
        controleerAanwezig(familierechtelijkeBetrekking, Foutmelding.maakStructuurFout(herkomst, LogSeverity.WARNING,
                "Element 62.10: Datum " + "familierechtelijke betrekking is verplicht in groep 62: "
                        + "Familierechtelijke betrekking."));
        controleerDatum(familierechtelijkeBetrekking, Foutmelding.maakStructuurFout(herkomst, LogSeverity.ERROR,
                "Element 62.10: Datum familierechtelijke betrekking bevat geen geldige datum."));
    }

    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */
    /* ************************************************************************************************************ */

    /**
     * Indicatie ouder 1 of 2.
     */
    protected static enum Ouder {
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
