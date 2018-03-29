/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: akte 3A en 5A.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisAkteNaamGeslachtPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisAkteNaamGeslachtPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer toevallige gebeurtenis obv de soort akte.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     * @param voornaamWijziging indicatie of voornaam wijziging is toegestaan
     * @param geslachtsnaamWijziging indicatie of geslachtsnaam wijziging is toegestaan
     * @param geslachtsWijziging indicatie of geslachtswijziging (en adellijke titel) is toegestaan
     */
    public void controleerToevalligeGebeurtenis(
            final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis,
            final boolean voornaamWijziging,
            final boolean geslachtsnaamWijziging,
            final boolean geslachtsWijziging) {
        // Categorie 01: Persoon
        controleerNaamGeslacht(toevalligeGebeurtenis.getPersoon(), voornaamWijziging, geslachtsnaamWijziging, geslachtsWijziging);

        // Categorie 02: Ouder 1
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder1());

        // Categorie 03: Ouder 2
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder2());

        // Categorie 05: Huwelijk/Geregistreerd partnerschap
        controleerNietAanwezig(toevalligeGebeurtenis.getVerbintenis());

        // Categorie 06: Overlijden
        controleerNietAanwezig(toevalligeGebeurtenis.getOverlijden());
    }

    private void controleerNaamGeslacht(
            final Lo3Stapel<Lo3PersoonInhoud> persoon,
            final boolean voornaamWijziging,
            final boolean geslachtsnaamWijziging,
            final boolean geslachtsWijziging) {
        if (persoon == null || persoon.isEmpty()) {
            // Aanwezigheid is al gecontroleerd
            return;
        }

        final Lo3PersoonInhoud actueleInhoud = persoon.get(0).getInhoud();
        final Lo3Herkomst actueleHerkomst = persoon.get(0).getLo3Herkomst();

        // Groep 01: Identificatienummers
        // geen aanvullende controles

        // Groep 02: Naam
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 03: Geboorte
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 04: Geslacht
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 20: A-nummerverwijzigingen
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 61: Naamgebruik
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 81: Akte
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 85: Geldigheid
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        if (persoon.size() == 1) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_51, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG042, null);
            return;
        }
        final Lo3PersoonInhoud historischeInhoud = persoon.get(1).getInhoud();

        // Groep 01: Identificatienummers
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 02: Naam (aanwezigheid is al gecontroleerd)
        if (!elementEquals(historischeInhoud.getVoornamen(), actueleInhoud.getVoornamen()) && !voornaamWijziging) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG058, null);
        }
        if (!elementEquals(historischeInhoud.getAdellijkeTitelPredikaatCode(), actueleInhoud.getAdellijkeTitelPredikaatCode()) && !geslachtsWijziging) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG057, null);
        }
        if ((!elementEquals(historischeInhoud.getVoorvoegselGeslachtsnaam(), actueleInhoud.getVoorvoegselGeslachtsnaam())
                || !elementEquals(historischeInhoud.getGeslachtsnaam(), actueleInhoud.getGeslachtsnaam()))
                && !geslachtsnaamWijziging) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG044, null);
        }

        // Groep 03: Geboorte (aanwezigheid is al gecontroleerd)
        if (!elementEquals(historischeInhoud.getGeboortedatum(), actueleInhoud.getGeboortedatum())
                || !elementEquals(historischeInhoud.getGeboorteGemeenteCode(), actueleInhoud.getGeboorteGemeenteCode())
                || !elementEquals(historischeInhoud.getGeboorteLandCode(), actueleInhoud.getGeboorteLandCode())) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG045, null);
        }

        // Groep 04: Geslacht (aanwezigheid is al gecontroleerd)
        if (!elementEquals(historischeInhoud.getGeslachtsaanduiding(), actueleInhoud.getGeslachtsaanduiding()) && !geslachtsWijziging) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG046, null);
        }

        // Groep 20: A-nummerverwijzigingen
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 61: Naamgebruik
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 81: Akte
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 85: Geldigheid
        // geen aanvullende controles (afwezigheid is al gecontroleerd)
    }

}
