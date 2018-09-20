/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3IndicatieGeheimCode;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP indicatie geheim geconverteerd.
 */
public final class IndicatieGeheimConversietabel implements Conversietabel<Lo3IndicatieGeheimCode, Boolean> {

    @Override
    public Boolean converteerNaarBrp(final Lo3IndicatieGeheimCode input) {
        if (input == null) {
            return null;
        }

        return input.getCode().equals(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.getCode()) ? Boolean.FALSE
                : Boolean.TRUE;

    }

    @Override
    public Lo3IndicatieGeheimCode converteerNaarLo3(final Boolean input) {
        final Lo3IndicatieGeheimCode geheim =
                Lo3IndicatieGeheimCodeEnum.NIET_TER_UITVOERING_VAN_VOORSCHRIFT_EN_NIET_AAN_VRIJE_DERDEN_EN_NIET_AAN_KERKEN
                        .asElement();

        final Lo3IndicatieGeheimCode nietGeheim = Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement();

        return Boolean.TRUE.equals(input) ? geheim : nietGeheim;
    }

    @Override
    public boolean valideerLo3(final Lo3IndicatieGeheimCode input) {
        return input == null || Lo3IndicatieGeheimCodeEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final Boolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
