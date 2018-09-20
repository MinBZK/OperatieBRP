/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP soort verbintenis.
 */
public final class SoortVerbintenisConversietabel implements Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> {

    @Override
    public BrpSoortRelatieCode converteerNaarBrp(final Lo3SoortVerbintenis input) {
        if (input == null) {
            return null;
        }

        final Lo3Onderzoek onderzoek = input.getOnderzoek();

        String waarde = null;
        if (input.isInhoudelijkGevuld()) {
            final Lo3SoortVerbintenisEnum code = Lo3SoortVerbintenisEnum.getByCode(input.getWaarde());

            switch (code) {
                case GEREGISTREERD_PARTNERSCHAP:
                    waarde = BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getWaarde();
                    break;
                case HUWELIJK:
                    waarde = BrpSoortRelatieCode.HUWELIJK.getWaarde();
                    break;
                default:
                    throw new IllegalArgumentException(String.format("Kan de LO3 soort verbintenis '%s' niet converteren naar BRP", input.toString()));
            }
        }
        return waarde == null && onderzoek == null ? null : new BrpSoortRelatieCode(waarde, onderzoek);
    }

    @Override
    public Lo3SoortVerbintenis converteerNaarLo3(final BrpSoortRelatieCode input) {
        if (input == null) {
            return null;
        }
        final Lo3SoortVerbintenis result;
        final Lo3Onderzoek onderzoek = input.getOnderzoek();

        if (input.isInhoudelijkGevuld()) {
            final String waarde = input.getWaarde();
            if (BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP.getWaarde().equals(waarde)) {
                result = new Lo3SoortVerbintenis(Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.getCode(), onderzoek);

            } else if (BrpSoortRelatieCode.HUWELIJK.getWaarde().equals(waarde)) {
                result = new Lo3SoortVerbintenis(Lo3SoortVerbintenisEnum.HUWELIJK.getCode(), onderzoek);
            } else {
                throw new IllegalArgumentException(String.format("Kan de BRP soort verbintenis '%s' niet converteren naar LO3", input.toString()));
            }
        } else {
            result = onderzoek == null ? null : new Lo3SoortVerbintenis(null, onderzoek);
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3SoortVerbintenis input) {
        return !Validatie.isElementGevuld(input) || Lo3SoortVerbintenisEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpSoortRelatieCode input) {
        return true;
    }
}
