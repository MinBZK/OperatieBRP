/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.groep.Lo3GroepUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: akte 3B en 5B.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisAkteVerbintenisOntbindingPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisAkteVerbintenisOntbindingPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer toevallige gebeurtenis obv de soort akte.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     * @param soortVerbintenis soort verbintenis
     */
    public void controleerToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis, final Lo3SoortVerbintenisEnum soortVerbintenis) {
        // Categorie 01: Persoon
        controleerAlleenAktueel(toevalligeGebeurtenis.getPersoon());

        // Categorie 02: Ouder 1
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder1());

        // Categorie 03: Ouder 2
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder2());

        // Categorie 05: Huwelijk/Geregistreerd partnerschap
        controleerVerbintenis(toevalligeGebeurtenis.getVerbintenis(), soortVerbintenis);

        // Categorie 06: Overlijden
        controleerNietAanwezig(toevalligeGebeurtenis.getOverlijden());
    }

    private void controleerVerbintenis(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> verbintenis, final Lo3SoortVerbintenisEnum soortVerbintenis) {
        if (verbintenis == null || verbintenis.isEmpty()) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_05, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG031, null);
            return;
        }

        final Lo3HuwelijkOfGpInhoud actueleInhoud = verbintenis.get(0).getInhoud();
        final Lo3Herkomst actueleHerkomst = verbintenis.get(0).getLo3Herkomst();

        // Groep 01: Identificatienummers
        // geen aanvullende controles

        // Groep 02: Naam
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 03: Geboorte
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 04: Geslacht
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 06: Sluiting
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP06, actueleInhoud)) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG038, null);
        }

        // Groep 07: Ontbinding
        if (!Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP07, actueleInhoud)) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG039, null);
        }

        // Groep 15: Soort verbintenis (aanwezigheid is al gecontroleerd)
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP15, actueleInhoud)
                && !soortVerbintenis.asElement().equalsWaarde(actueleInhoud.getSoortVerbintenis())) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG034, Lo3ElementEnum.ELEMENT_1510);
        }

        // Groep 81: Akte
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 85: Geldigheid
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        if (verbintenis.size() == 1) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_55, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG037, null);
            return;
        }
        final Lo3HuwelijkOfGpInhoud historischeInhoud = verbintenis.get(1).getInhoud();
        final Lo3Herkomst historischeHerkomst = verbintenis.get(1).getLo3Herkomst();

        // Groep 01: Identificatienummers
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 02: Naam (aanwezigheid is al gecontroleerd)
        if (!elementEquals(historischeInhoud.getVoornamen(), actueleInhoud.getVoornamen())
                || !elementEquals(historischeInhoud.getAdellijkeTitelPredikaatCode(), actueleInhoud.getAdellijkeTitelPredikaatCode())
                || !elementEquals(historischeInhoud.getVoorvoegselGeslachtsnaam(), actueleInhoud.getVoorvoegselGeslachtsnaam())
                || !elementEquals(historischeInhoud.getGeslachtsnaam(), actueleInhoud.getGeslachtsnaam())) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG040, null);
        }

        // Groep 03: Geboorte (aanwezigheid is al gecontroleerd)
        if (!elementEquals(historischeInhoud.getGeboortedatum(), actueleInhoud.getGeboortedatum())
                || !elementEquals(historischeInhoud.getGeboorteGemeenteCode(), actueleInhoud.getGeboorteGemeenteCode())
                || !elementEquals(historischeInhoud.getGeboorteLandCode(), actueleInhoud.getGeboorteLandCode())) {
            Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG041, null);
        }

        // Groep 04: Geslacht
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 06: Sluiting
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 07: Ontbinding
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 15: Soort verbintenis (aanwezigheid is al gecontroleerd)
        if (Lo3GroepUtil.isGroepAanwezig(Lo3GroepEnum.GROEP15, historischeInhoud)
                && !soortVerbintenis.asElement().equalsWaarde(historischeInhoud.getSoortVerbintenis())) {
            Foutmelding.logMeldingFout(historischeHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG034, Lo3ElementEnum.ELEMENT_1510);
        }

        // Groep 81: Akte
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 85: Geldigheid
        // geen aanvullende controles (afwezigheid is al gecontroleerd)
    }

}
