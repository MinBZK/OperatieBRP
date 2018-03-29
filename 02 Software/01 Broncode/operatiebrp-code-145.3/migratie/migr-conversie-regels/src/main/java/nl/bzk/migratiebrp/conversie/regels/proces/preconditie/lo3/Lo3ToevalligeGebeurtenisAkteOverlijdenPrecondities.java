/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: akte 2A en 2G.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisAkteOverlijdenPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisAkteOverlijdenPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer toevallige gebeurtenis obv de soort akte.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     */
    public void controleerToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        // Categorie 01: Persoon
        controleerAlleenAktueel(toevalligeGebeurtenis.getPersoon());

        // Categorie 02: Ouder 1
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder1());

        // Categorie 03: Ouder 2
        controleerNietAanwezig(toevalligeGebeurtenis.getOuder2());

        // Categorie 05: Huwelijk/Geregistreerd partnerschap
        controleerNietAanwezig(toevalligeGebeurtenis.getVerbintenis());

        // Categorie 06: Overlijden
        controleerOverlijden(toevalligeGebeurtenis.getOverlijden());
    }

    private void controleerOverlijden(final Lo3Categorie<Lo3OverlijdenInhoud> overlijden) {
        if (overlijden == null) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_06, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG029, null);
        }

        // Groep 08: Overlijden
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 81: Akte
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 82: Document
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 83: Procedure
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 84: Onjuist
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 85: Geldigheid
        // geen aanvullende controles (aanwezigheid is al gecontroleerd)

        // Groep 86: Opneming
        // geen aanvullende controles (afwezigheid is al gecontroleerd)

        // Groep 88: RNI-deelnemer
        // geen aanvullende controles (afwezigheid is al gecontroleerd)
    }

}
