/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingEuropeesKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP aanduiding europees kiesrecht.
 * Aangezien Aanduiding Europees Kiesrecht in LO3 geen onderzoek kan bevatten, wordt deze ook niet geconverteerd.
 */
public final class AanduidingEuropeesKiesrechtConversietabel implements Conversietabel<Lo3AanduidingEuropeesKiesrecht, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3AanduidingEuropeesKiesrecht input) {
        if (!Validatie.isElementGevuld(input)) {
            return null;
        }

        final Lo3AanduidingEuropeesKiesrechtEnum code = Lo3AanduidingEuropeesKiesrechtEnum.getByCode(input.getIntegerWaarde());

        final Boolean result;
        switch (code) {
            case UITGESLOTEN:
                result = Boolean.FALSE;
                break;
            case ONTVANGT_OPROEP:
                result = Boolean.TRUE;
                break;
            default:
                throw new IllegalArgumentException("Ongeldige waarde voor lo3 aanduiding europees kiesrecht: " + input);
        }
        return new BrpBoolean(result);
    }

    @Override
    public Lo3AanduidingEuropeesKiesrecht converteerNaarLo3(final BrpBoolean input) {
        Lo3AanduidingEuropeesKiesrecht result = null;
        if (input != null) {
            if (Boolean.TRUE.equals(input.getWaarde())) {
                result = new Lo3AanduidingEuropeesKiesrecht(Lo3AanduidingEuropeesKiesrechtEnum.ONTVANGT_OPROEP.getCode());
            } else {
                result = new Lo3AanduidingEuropeesKiesrecht(Lo3AanduidingEuropeesKiesrechtEnum.UITGESLOTEN.getCode());
            }
        }
        return result;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingEuropeesKiesrecht input) {
        return !Validatie.isElementGevuld(input) || Lo3AanduidingEuropeesKiesrechtEnum.getByCode(input.getIntegerWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
