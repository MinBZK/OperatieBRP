/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.statisch;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3AanduidingUitgeslotenKiesrechtEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * Dit is een statische conversie (er wordt geen tabel gebruikt) tussen de LO3 en BRP aanduiding uitgesloten kiesrecht.
 * Aangezien Aanduiding Uitgesloten Kiesrecht in LO3 geen onderzoek kan bevatten, wordt deze ook niet geconverteerd.
 */
public final class AanduidingUitgeslotenKiesrechtConversietabel implements Conversietabel<Lo3AanduidingUitgeslotenKiesrecht, BrpBoolean> {

    @Override
    public BrpBoolean converteerNaarBrp(final Lo3AanduidingUitgeslotenKiesrecht input) {
        if (!Lo3Validatie.isElementGevuld(input)) {
            return null;
        }

        final Lo3AanduidingUitgeslotenKiesrechtEnum code = Lo3AanduidingUitgeslotenKiesrechtEnum.getByCode(input.getWaarde());

        if (Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT == code) {
            return new BrpBoolean(Boolean.TRUE);
        } else {
            throw new IllegalArgumentException("Ongeldige waarde voor lo3 aanduiding uitgelosten kiesrecht: " + input);
        }
    }

    @Override
    public Lo3AanduidingUitgeslotenKiesrecht converteerNaarLo3(final BrpBoolean input) {
        if (BrpValidatie.isAttribuutGevuld(input) && Boolean.TRUE.equals(input.getWaarde())) {
            return new Lo3AanduidingUitgeslotenKiesrecht(Lo3AanduidingUitgeslotenKiesrechtEnum.UITGESLOTEN_KIESRECHT.getCode());
        }
        return null;
    }

    @Override
    public boolean valideerLo3(final Lo3AanduidingUitgeslotenKiesrecht input) {
        return !Lo3Validatie.isElementGevuld(input) || Lo3AanduidingUitgeslotenKiesrechtEnum.getByCode(input.getWaarde()) != null;
    }

    @Override
    public boolean valideerBrp(final BrpBoolean input) {
        // Alle boolean waarden zijn geldig
        return true;
    }

}
