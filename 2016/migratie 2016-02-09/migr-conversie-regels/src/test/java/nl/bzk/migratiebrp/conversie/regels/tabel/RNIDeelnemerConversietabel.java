/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.tabel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.Conversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;

public class RNIDeelnemerConversietabel implements Conversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> {

    private static final Map<Lo3RNIDeelnemerCode, BrpPartijCode> RNI_DEELNEMER_MAP =
            Collections.unmodifiableMap(new HashMap<Lo3RNIDeelnemerCode, BrpPartijCode>() {
                private static final long serialVersionUID = 1L;

                {
                    put(new Lo3RNIDeelnemerCode("0000"), new BrpPartijCode(0));
                    put(new Lo3RNIDeelnemerCode("0101"), new BrpPartijCode(250001));
                    put(new Lo3RNIDeelnemerCode("9999"), new BrpPartijCode(999999));
                }
            });

    private static final Map<BrpPartijCode, Lo3RNIDeelnemerCode> RNI_DEELNEMER_MAP_INVERSE;
    static {
        final Map<BrpPartijCode, Lo3RNIDeelnemerCode> buildingMap = new HashMap<>();
        for (final Map.Entry<Lo3RNIDeelnemerCode, BrpPartijCode> entry : RNI_DEELNEMER_MAP.entrySet()) {
            buildingMap.put(entry.getValue(), entry.getKey());
        }
        RNI_DEELNEMER_MAP_INVERSE = Collections.unmodifiableMap(buildingMap);
    }

    @Override
    public BrpPartijCode converteerNaarBrp(final Lo3RNIDeelnemerCode input) {
        final Lo3RNIDeelnemerCode inputZonderOnderzoek = new Lo3RNIDeelnemerCode(input.getWaarde(), null);

        if (!valideerLo3(inputZonderOnderzoek)) {
            throw new IllegalArgumentException("Ongeldige code voor LO3 rni deelnemer: " + input);
        }
        final BrpPartijCode brpPartijCodeZonderOnderzoek = RNI_DEELNEMER_MAP.get(inputZonderOnderzoek);
        return new BrpPartijCode(brpPartijCodeZonderOnderzoek.getWaarde(), input.getOnderzoek());
    }

    @Override
    public Lo3RNIDeelnemerCode converteerNaarLo3(final BrpPartijCode input) {
        final BrpPartijCode brpPartijCodeZonderOnderzoek = new BrpPartijCode(input.getWaarde(), null);
        if (!valideerBrp(brpPartijCodeZonderOnderzoek)) {
            throw new IllegalArgumentException("Ongeldige code voor BRP Partij: " + input);
        }
        final Lo3RNIDeelnemerCode lo3RNIDeelnemerCodeZonderOnderzoek = RNI_DEELNEMER_MAP_INVERSE.get(brpPartijCodeZonderOnderzoek);
        return new Lo3RNIDeelnemerCode(lo3RNIDeelnemerCodeZonderOnderzoek.getWaarde(), input.getOnderzoek());
    }

    @Override
    public boolean valideerLo3(final Lo3RNIDeelnemerCode input) {
        return input == null || RNI_DEELNEMER_MAP.containsKey(input);
    }

    @Override
    public boolean valideerBrp(final BrpPartijCode input) {
        return input == null || RNI_DEELNEMER_MAP.containsValue(input);
    }

}
