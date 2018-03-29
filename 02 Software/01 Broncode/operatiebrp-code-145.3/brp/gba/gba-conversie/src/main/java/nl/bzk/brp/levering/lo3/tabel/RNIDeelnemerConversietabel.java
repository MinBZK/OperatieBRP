/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RNIDeelnemer;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRNIDeelnemerConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RNIDeelnemerCode;

/**
 * Deze conversietabel mapt een Lo3RNIDeelnemerCode op de corresponderende BrpPartijCode en vice versa.
 */
public final class RNIDeelnemerConversietabel extends AbstractRNIDeelnemerConversietabel {

    /**
     * Maakt een RNIDeelnemerConversietabel object.
     * @param list de lijst met waarden uit de conversietabel.
     */
    public RNIDeelnemerConversietabel(final List<RNIDeelnemer> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link RNIDeelnemer} naar een conversie map van de LO3 waarde {@link Lo3RNIDeelnemerCode}
     * en de BRP waarde {@link BrpPartijCode}.
     */
    private static final class Converter extends AbstractLijstConverter<RNIDeelnemer, Lo3RNIDeelnemerCode, BrpPartijCode> {

        @Override
        protected Lo3RNIDeelnemerCode maakLo3Waarde(final RNIDeelnemer conversieRNIDeelnemer) {
            return new Lo3RNIDeelnemerCode(conversieRNIDeelnemer.getLo3CodeRNIDeelnemer());
        }

        @Override
        protected BrpPartijCode maakBrpWaarde(final RNIDeelnemer conversieRNIDeelnemer) {
            return new BrpPartijCode(conversieRNIDeelnemer.getPartij().getCode());
        }
    }
}
