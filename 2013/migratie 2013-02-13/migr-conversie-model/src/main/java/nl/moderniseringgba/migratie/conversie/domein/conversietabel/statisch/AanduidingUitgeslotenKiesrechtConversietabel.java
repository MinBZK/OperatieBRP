/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.domein.conversietabel.statisch;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP aanduiding uitgesloten kiesrecht.
 */
public final class AanduidingUitgeslotenKiesrechtConversietabel implements
        Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, Boolean> {

    @Override
    public Boolean converteerNaarBrp(final Lo3AanduidingUitgeslotenKiesrecht input) {
        if (input == null) {
            return null;
        }

        final Lo3AanduidingUitgeslotenKiesrechtEnum code =
                Lo3AanduidingUitgeslotenKiesrechtEnum.getByCode(input.getCode());

        switch (code) {
            case UITGESLOTEN_KIESRECHT:
                return Boolean.TRUE;
            default:
                throw new IllegalArgumentException("Ongeldige waarde voor lo3 aanduiding uitgelosten kiesrecht: "
                        + input);
        }
    }

    @Override
    public Lo3AanduidingUitgeslotenKiesrecht converteerNaarLo3(final Boolean input) {
        return Boolean.TRUE.equals(input) ? new Lo3AanduidingUitgeslotenKiesrecht(
                Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.getCode()) : null;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingUitgeslotenKiesrecht input) {
        return input == null || Lo3AanduidingUitgeslotenKiesrechtEnum.getByCode(input.getCode()) != null;
    }

    @Override
    public boolean valideerBrp(final Boolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
