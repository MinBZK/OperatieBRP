/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;

public class RedenOpschortingBijhoudingConversietabel implements
        Conversietabel<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> {


    private static final Map<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> LO3_NAAR_REDEN_OPSCHORTING =
            Collections.unmodifiableMap(new HashMap<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>() {
                {
                    put(Lo3RedenOpschortingBijhoudingCodeEnum.OVERLIJDEN.asElement(), BrpNadereBijhoudingsaardCode.OVERLEDEN);
                    put(
                            Lo3RedenOpschortingBijhoudingCodeEnum.MINISTERIEEL_BESLUIT.asElement(),
                            BrpNadereBijhoudingsaardCode.BIJZONDERE_STATUS);
                    put(Lo3RedenOpschortingBijhoudingCodeEnum.FOUT.asElement(), BrpNadereBijhoudingsaardCode.FOUT);
                    put(Lo3RedenOpschortingBijhoudingCodeEnum.ONBEKEND.asElement(), BrpNadereBijhoudingsaardCode.ONBEKEND);
                    put(Lo3RedenOpschortingBijhoudingCodeEnum.EMIGRATIE.asElement(), BrpNadereBijhoudingsaardCode.EMIGRATIE);
                    put(Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement(), BrpNadereBijhoudingsaardCode.RECHTSTREEKS_NIET_INGEZETENE);
                }
            });

    @Override
    public BrpNadereBijhoudingsaardCode converteerNaarBrp(final Lo3RedenOpschortingBijhoudingCode input) {
        if (input == null) {
            return BrpNadereBijhoudingsaardCode.ACTUEEL;
        }
        if (!LO3_NAAR_REDEN_OPSCHORTING.containsKey(input)) {
            throw new IllegalArgumentException("Ongeldige code voor LO3 reden opschorting bijhouding: " + input);
        }
        return new BrpNadereBijhoudingsaardCode(LO3_NAAR_REDEN_OPSCHORTING.get(input).getWaarde(), input.getOnderzoek());
    }

    @Override
    public Lo3RedenOpschortingBijhoudingCode converteerNaarLo3(final BrpNadereBijhoudingsaardCode input) {
        return input == null ? null : new Lo3RedenOpschortingBijhoudingCode(input.getWaarde(), input.getOnderzoek());
    }

    @Override
    public boolean valideerLo3(final Lo3RedenOpschortingBijhoudingCode input) {
        return input == null || LO3_NAAR_REDEN_OPSCHORTING.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final BrpNadereBijhoudingsaardCode input) {
        return true;
    }

}
