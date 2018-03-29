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
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: akte 2A en 2G.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisAkteOudersPrecondities extends AbstractLo3ToevalligeGebeurtenisPrecondities {

    /**
     * Constructor.
     * @param conversieTabelFactory {@link ConversietabelFactory}
     */
    public Lo3ToevalligeGebeurtenisAkteOudersPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        super(conversieTabelFactory);
    }

    /**
     * Controleer toevallige gebeurtenis obv de soort akte.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     */
    public void controleerToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        // Categorie 01: Persoon
        controleerNaamWijziging(toevalligeGebeurtenis.getPersoon());

        // Categorie 02/03: Ouder 1/2
        // controleerOuders(toevalligeGebeurtenis.getOuder1(), toevalligeGebeurtenis.getOuder2())

        // Categorie 05: Huwelijk/Geregistreerd partnerschap
        controleerNietAanwezig(toevalligeGebeurtenis.getVerbintenis());

        // Categorie 06: Overlijden
        controleerNietAanwezig(toevalligeGebeurtenis.getOverlijden());
    }

    private void controleerNaamWijziging(final Lo3Stapel<Lo3PersoonInhoud> persoon) {
        if (persoon != null && persoon.size() > 1) {
            // Naamswijziging
            final Lo3PersoonInhoud actueleInhoud = persoon.get(0).getInhoud();
            final Lo3Herkomst actueleHerkomst = persoon.get(0).getLo3Herkomst();
            final Lo3PersoonInhoud historischeInhoud = persoon.get(1).getInhoud();

            // Groep 02: Naam (aanwezigheid is al gecontroleerd)
            if (!elementEquals(historischeInhoud.getVoornamen(), actueleInhoud.getVoornamen())) {
                Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG061, null);
            }

            // Groep 03: Geboorte (aanwezigheid is al gecontroleerd)
            if (!elementEquals(historischeInhoud.getGeboortedatum(), actueleInhoud.getGeboortedatum())
                    || !elementEquals(historischeInhoud.getGeboorteGemeenteCode(), actueleInhoud.getGeboorteGemeenteCode())
                    || !elementEquals(historischeInhoud.getGeboorteLandCode(), actueleInhoud.getGeboorteLandCode())) {
                Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG061, null);
            }

            // Groep 04: Geslacht (aanwezigheid is al gecontroleerd)
            if (!elementEquals(historischeInhoud.getGeslachtsaanduiding(), actueleInhoud.getGeslachtsaanduiding())) {
                Foutmelding.logMeldingFout(actueleHerkomst, LogSeverity.ERROR, SoortMeldingCode.TG061, null);
            }

        }
    }
}
