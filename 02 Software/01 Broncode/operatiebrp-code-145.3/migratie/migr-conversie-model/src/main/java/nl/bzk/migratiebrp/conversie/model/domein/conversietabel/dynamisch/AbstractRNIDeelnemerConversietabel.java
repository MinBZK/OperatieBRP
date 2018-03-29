/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpValidatie;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.AbstractAttribuutConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;

/**
 * De conversietabel om 'Lo3 RNI Deelnemer'-code (GBA Tabel 60) te converteren naar de 'BRP Partij'-code en vice versa.
 */
public abstract class AbstractRNIDeelnemerConversietabel extends AbstractAttribuutConversietabel<Lo3RNIDeelnemerCode, BrpPartijCode> {

    /**
     * Maakt een RNIDeelnemerConversietabel object.
     * @param conversieLijst de lijst met RNIDeelnemer conversies.
     */
    public AbstractRNIDeelnemerConversietabel(final List<Entry<Lo3RNIDeelnemerCode, BrpPartijCode>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3RNIDeelnemerCode voegOnderzoekToeLo3(final Lo3RNIDeelnemerCode input, final Lo3Onderzoek onderzoek) {
        final Lo3RNIDeelnemerCode resultaat;
        if (Lo3Validatie.isElementGevuld(input)) {
            resultaat = new Lo3RNIDeelnemerCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new Lo3RNIDeelnemerCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }

    @Override
    protected final BrpPartijCode voegOnderzoekToeBrp(final BrpPartijCode input, final Lo3Onderzoek onderzoek) {
        final BrpPartijCode resultaat;
        if (BrpValidatie.isAttribuutGevuld(input)) {
            resultaat = new BrpPartijCode(input.getWaarde(), onderzoek);
        } else {
            if (onderzoek != null) {
                resultaat = new BrpPartijCode(null, onderzoek);
            } else {
                resultaat = null;
            }
        }

        return resultaat;
    }
}
