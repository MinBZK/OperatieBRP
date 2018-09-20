/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRNIDeelnemerConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.conversietabel.entity.RNIDeelnemer;

/**
 * De conversietabel om 'Lo3 RNI Deelnemer'-code (GBA Tabel 60) te converteren naar de 'BRP Partij'-code en vice versa.
 * 
 */
public final class RNIDeelnemerConversietabel extends AbstractRNIDeelnemerConversietabel {

    private static final DecimalFormat RNI_DEELNEMER_CODE_FORMAT = new DecimalFormat("0000");

    /**
     * Maakt een RNIDeelnemerConversietabel object.
     * 
     * @param rniDeelnemerLijst
     *            de lijst met RNIDeelnemer conversies.
     */
    public RNIDeelnemerConversietabel(final List<RNIDeelnemer> rniDeelnemerLijst) {
        super(converteerRNIDeelnemerLijst(rniDeelnemerLijst));
    }

    private static List<Map.Entry<Lo3RNIDeelnemerCode, BrpPartijCode>> converteerRNIDeelnemerLijst(
        final List<RNIDeelnemer> rniDeelnemerLijst)
    {
        final List<Map.Entry<Lo3RNIDeelnemerCode, BrpPartijCode>> result = new ArrayList<>();
        for (final RNIDeelnemer rniDeelnemer : rniDeelnemerLijst) {
            result.add(new ConversieMapEntry<>(new Lo3RNIDeelnemerCode(
                RNI_DEELNEMER_CODE_FORMAT.format(rniDeelnemer.getLo3CodeRNIDeelnemer())), new BrpPartijCode(rniDeelnemer.getPartij()
                                                                                                                        .getCode())));
        }
        return result;
    }

}
