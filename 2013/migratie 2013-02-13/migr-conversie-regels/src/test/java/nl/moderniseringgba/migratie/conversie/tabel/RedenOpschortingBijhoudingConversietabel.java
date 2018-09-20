/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.domein.conversietabel.Conversietabel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpRedenOpschortingBijhoudingCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;

public class RedenOpschortingBijhoudingConversietabel implements
        Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode> {

    @SuppressWarnings("serial")
    private static final Map<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode> LO3_NAAR_REDEN_OPSCHORTING =
            Collections
                    .unmodifiableMap(new HashMap<Lo3RedenOpschortingBijhoudingCode, BrpRedenOpschortingBijhoudingCode>() {
                        {
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement(),
                                    BrpRedenOpschortingBijhoudingCode.OVERLIJDEN);
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement(),
                                    BrpRedenOpschortingBijhoudingCode.MINISTERIEEL_BESLUIT);
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement(),
                                    BrpRedenOpschortingBijhoudingCode.FOUT);
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(),
                                    BrpRedenOpschortingBijhoudingCode.ONBEKEND);
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(), null);
                            put(Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement(), null);
                        }
                    });

    @Override
    public BrpRedenOpschortingBijhoudingCode converteerNaarBrp(final Lo3RedenOpschortingBijhoudingCode input) {
        if (input == null) {
            return null;
        }
        if (!LO3_NAAR_REDEN_OPSCHORTING.containsKey(input)) {
            throw new IllegalArgumentException("Ongeldige code voor LO3 reden opschortign bijhouding: " + input);
        }
        return LO3_NAAR_REDEN_OPSCHORTING.get(input);
    }

    @Override
    public Lo3RedenOpschortingBijhoudingCode converteerNaarLo3(final BrpRedenOpschortingBijhoudingCode input) {
        return input == null ? null : new Lo3RedenOpschortingBijhoudingCode(input.getCode());
    }

    @Override
    public boolean valideerLo3(final Lo3RedenOpschortingBijhoudingCode input) {
        return input == null || LO3_NAAR_REDEN_OPSCHORTING.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final BrpRedenOpschortingBijhoudingCode input) {
        return true;
    }

}
