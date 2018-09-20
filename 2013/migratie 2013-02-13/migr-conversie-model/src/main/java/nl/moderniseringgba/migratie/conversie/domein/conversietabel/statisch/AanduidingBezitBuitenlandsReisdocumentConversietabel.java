/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingBezitBuitenlandsReisdocumentEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingBezitBuitenlandsReisdocument;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP code.
 */
public final class AanduidingBezitBuitenlandsReisdocumentConversietabel implements
        Conversietabel<Lo3AanduidingBezitBuitenlandsReisdocument, Boolean> {

    @Override
    public Boolean converteerNaarBrp(final Lo3AanduidingBezitBuitenlandsReisdocument input) {
        return Lo3AanduidingBezitBuitenlandsReisdocumentEnum.AANDUIDING.getCode().equals(
                input == null ? null : input.getCode()) ? Boolean.TRUE : null;
    }

    @Override
    public Lo3AanduidingBezitBuitenlandsReisdocument converteerNaarLo3(final Boolean input) {
        return Boolean.TRUE.equals(input) ? new Lo3AanduidingBezitBuitenlandsReisdocument(
                Lo3AanduidingBezitBuitenlandsReisdocumentEnum.AANDUIDING.getCode()) : null;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingBezitBuitenlandsReisdocument input) {
        return input == null || Lo3AanduidingBezitBuitenlandsReisdocumentEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final Boolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
