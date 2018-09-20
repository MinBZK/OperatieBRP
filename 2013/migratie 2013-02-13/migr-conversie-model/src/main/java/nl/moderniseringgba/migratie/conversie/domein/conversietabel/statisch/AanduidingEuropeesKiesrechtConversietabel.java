/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP geslachtsaanduiding.
 */
public final class AanduidingEuropeesKiesrechtConversietabel implements
        Conversietabel<Lo3AanduidingEuropeesKiesrecht, Boolean> {

    @Override
    public Boolean converteerNaarBrp(final Lo3AanduidingEuropeesKiesrecht input) {
        if (input == null) {
            return null;
        }

        final Lo3AanduidingEuropeesKiesrechtEnum code = Lo3AanduidingEuropeesKiesrechtEnum.getByCode(input.getCode());

        final Boolean result;
        switch (code) {
            case UITGESLOTEN:
                result = Boolean.FALSE;
                break;
            case ONTVANGT_OPROEP:
                result = Boolean.TRUE;
                break;
            default:
                throw new IllegalArgumentException("Ongeldige waarde voor lo3 aanduiding europees kiesrecht: "
                        + input);
        }
        return result;
    }

    @Override
    public Lo3AanduidingEuropeesKiesrecht converteerNaarLo3(final Boolean input) {
        return input == null ? null : Boolean.TRUE.equals(input) ? new Lo3AanduidingEuropeesKiesrecht(
                Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.getCode()) : new Lo3AanduidingEuropeesKiesrecht(
                Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingEuropeesKiesrecht input) {
        return input == null || Lo3AanduidingEuropeesKiesrechtEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final Boolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
