/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP indicatie geheim geconverteerd. Er
 * wordt geen onderzoek geconverteerd aangezien cat07 geen onderzoek kan bevatten in LO3.
 */
public final class IndicatieGeheimConversietabel implements Conversietabel<Lo3IndicatieGeheimCode, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3IndicatieGeheimCode input) {
        if (!Lo3Validatie.isElementGevuld(input)) {
            return null;
        }

        return new BrpBoolean(!input.getIntegerWaarde().equals(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.getCode()));
    }

    @Override
    public Lo3IndicatieGeheimCode converteerNaarLo3(final BrpBoolean input) {
        final Lo3IndicatieGeheimCode geheim =
                new Lo3IndicatieGeheimCode(
                        Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN_EN_NIET_AAN_KERKEN.getCode());

        final Lo3IndicatieGeheimCode nietGeheim = new Lo3IndicatieGeheimCode(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.getCode());

        return input != null && Boolean.TRUE.equals(input.getWaarde()) ? geheim : nietGeheim;
    }

    @Override
    public boolean valideerLo3(final Lo3IndicatieGeheimCode input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3IndicatieGeheimCodeEnum.getByCode(input.getIntegerWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
