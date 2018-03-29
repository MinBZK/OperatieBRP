/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.preconditie.lo3;

import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.factory.ConversietabelFactory;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3ToevalligeGebeurtenis;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortAkte;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.logging.LogSeverity;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.conversie.regels.proces.foutmelding.Foutmelding;

/**
 * Preconditie controles voor een toevallige gebeurtenis: aktes.
 *
 * Maakt gebruik van de {@link nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging#log Logging.log} methode.
 */
public final class Lo3ToevalligeGebeurtenisAktesPrecondities {

    private final Lo3ToevalligeGebeurtenisAkteOudersPrecondities akteOudersPrecondities;
    private final Lo3ToevalligeGebeurtenisAkteNaamGeslachtPrecondities akteNaamGeslachtPrecondities;
    private final Lo3ToevalligeGebeurtenisAkteOverlijdenPrecondities akteOverlijdenPrecondities;
    private final Lo3ToevalligeGebeurtenisAkteVerbintenisSluitingPrecondities akteVerbintenisSluitingPrecondities;
    private final Lo3ToevalligeGebeurtenisAkteVerbintenisOntbindingPrecondities akteVerbintenisOntbindingPrecondities;
    private final Lo3ToevalligeGebeurtenisAkteVerbintenisOmzettingPrecondities akteVerbintenisOmzettingPrecondities;

    /**
     * constructor.
     * @param conversieTabelFactory factory
     */
    public Lo3ToevalligeGebeurtenisAktesPrecondities(
            final ConversietabelFactory conversieTabelFactory) {
        akteOudersPrecondities = new Lo3ToevalligeGebeurtenisAkteOudersPrecondities(conversieTabelFactory);
        akteNaamGeslachtPrecondities = new Lo3ToevalligeGebeurtenisAkteNaamGeslachtPrecondities(conversieTabelFactory);
        akteOverlijdenPrecondities = new Lo3ToevalligeGebeurtenisAkteOverlijdenPrecondities(conversieTabelFactory);
        akteVerbintenisSluitingPrecondities = new Lo3ToevalligeGebeurtenisAkteVerbintenisSluitingPrecondities(conversieTabelFactory);
        akteVerbintenisOntbindingPrecondities = new Lo3ToevalligeGebeurtenisAkteVerbintenisOntbindingPrecondities(conversieTabelFactory);
        akteVerbintenisOmzettingPrecondities = new Lo3ToevalligeGebeurtenisAkteVerbintenisOmzettingPrecondities(conversieTabelFactory);

    }

    /**
     * Controleer toevallige gebeurtenis obv de soort akte.
     * @param toevalligeGebeurtenis toevallige gebeurtenis
     */
    public void controleerToevalligeGebeurtenis(final Lo3ToevalligeGebeurtenis toevalligeGebeurtenis) {
        final String nummerAkte = toevalligeGebeurtenis.getNummerAkte() == null ? null : toevalligeGebeurtenis.getNummerAkte().getWaarde();
        final Lo3SoortAkte soortAkte = Lo3SoortAkte.bepaalSoortAkteObvAktenummer(nummerAkte);
        if (soortAkte == null) {
            Foutmelding.logMeldingFout(new Lo3Herkomst(null, -1, -1), LogSeverity.ERROR, SoortMeldingCode.TG012, null);
            return;
        }

        switch (soortAkte) {
            case AKTE_1C:
            case AKTE_1E:
            case AKTE_1J:
            case AKTE_1N:
            case AKTE_1Q:
            case AKTE_1U:
            case AKTE_1V:
                // TODO: Adoptie is nog onduidelijk en zal nog aangepast moeten worden
                akteOudersPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);
                break;
            case AKTE_1H:
                akteNaamGeslachtPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis, false, true, false);
                break;
            case AKTE_1M:
                akteNaamGeslachtPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis, true, false, false);
                break;
            case AKTE_1S:
                akteNaamGeslachtPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis, true, false, true);
                break;
            case AKTE_2A:
            case AKTE_2G:
                akteOverlijdenPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis);
                break;
            case AKTE_3A:
                akteVerbintenisSluitingPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis, Lo3SoortVerbintenisEnum.HUWELIJK);
                break;
            case AKTE_3B:
                akteVerbintenisOntbindingPrecondities.controleerToevalligeGebeurtenis(toevalligeGebeurtenis, Lo3SoortVerbintenisEnum.HUWELIJK);
                break;
            case AKTE_3H:
                akteVerbintenisOmzettingPrecondities.controleerToevalligeGebeurtenis(
                        toevalligeGebeurtenis,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP,
                        Lo3SoortVerbintenisEnum.HUWELIJK);
                break;
            case AKTE_5A:
                akteVerbintenisSluitingPrecondities.controleerToevalligeGebeurtenis(
                        toevalligeGebeurtenis,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP);
                break;
            case AKTE_5B:
                akteVerbintenisOntbindingPrecondities.controleerToevalligeGebeurtenis(
                        toevalligeGebeurtenis,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP);
                break;
            case AKTE_5H:
                akteVerbintenisOmzettingPrecondities.controleerToevalligeGebeurtenis(
                        toevalligeGebeurtenis,
                        Lo3SoortVerbintenisEnum.HUWELIJK,
                        Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP);
                break;
            default:
                // Kan niet voorkomen
                throw new IllegalStateException("Onbekende SoortAkte");
        }
    }
}
