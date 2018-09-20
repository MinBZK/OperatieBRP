/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortRelatieCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3SoortVerbintenisEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3SoortVerbintenis;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP soort verbintenis.
 */
public final class SoortVerbintenisConversietabel implements Conversietabel<Lo3SoortVerbintenis, BrpSoortRelatieCode> {

    @Override
    public BrpSoortRelatieCode converteerNaarBrp(final Lo3SoortVerbintenis input) {
        if (input == null) {
            return null;
        }

        final Lo3SoortVerbintenisEnum code = Lo3SoortVerbintenisEnum.getByCode(input.getCode());

        BrpSoortRelatieCode result;
        switch (code) {
            case GEREGISTREERD_PARTNERSCHAP:
                result = BrpSoortRelatieCode.GEREGISTREERD_PARTNERSCHAP;
                break;
            case HUWELIJK:
                result = BrpSoortRelatieCode.HUWELIJK;
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Kan de LO3 soort verbintenis '%s' niet converteren naar BRP", input.toString()));
        }
        return result;
    }

    @Override
    public Lo3SoortVerbintenis converteerNaarLo3(final BrpSoortRelatieCode input) {
        if (input == null) {
            return null;
        }
        Lo3SoortVerbintenis result;
        switch (input) {
            case GEREGISTREERD_PARTNERSCHAP:
                result = new Lo3SoortVerbintenis(Lo3SoortVerbintenisEnum.GEREGISTREERD_PARTNERSCHAP.getCode());
                break;
            case HUWELIJK:
                result = new Lo3SoortVerbintenis(Lo3SoortVerbintenisEnum.HUWELIJK.getCode());
                break;
            default:
                throw new IllegalArgumentException(String.format(
                        "Kan de BRP soort verbintenis '%s' niet converteren naar LO3", input.toString()));
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3SoortVerbintenis input) {
        return input == null || Lo3SoortVerbintenisEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpSoortRelatieCode input) {
        // Enum
        return true;
    }
}
