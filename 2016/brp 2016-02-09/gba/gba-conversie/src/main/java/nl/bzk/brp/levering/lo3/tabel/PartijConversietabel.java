/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.text.DecimalFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractPartijConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;

/**
 * Conversietabel voor LO3 naar BRP codering en vice versa.
 */
public final class PartijConversietabel extends AbstractPartijConversietabel {

    /**
     * Maakt een PartijConversietabel object.
     *
     * @param collection de lijst met brp gemeenten
     */
    public PartijConversietabel(final Collection<Gemeente> collection) {
        super(new Converter().converteer(collection));
    }

    /**
     * Converteer de lijst van {@link Gemeente} naar een conversie map van de LO3 waarde {@link Lo3GemeenteCode} en de
     * BRP waarde {@link BrpPartijCode}.
     */
    private static final class Converter extends AbstractLijstConverter<Gemeente, Lo3GemeenteCode, BrpPartijCode> {

        private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");

        @Override
        protected Lo3GemeenteCode maakLo3Waarde(final Gemeente gemeente) {
            return new Lo3GemeenteCode(GEMEENTE_CODE_FORMAT.format(gemeente.getCode().getWaarde()));
        }

        @Override
        protected BrpPartijCode maakBrpWaarde(final Gemeente gemeente) {
            return new BrpPartijCode(gemeente.getPartij().getCode().getWaarde());
        }

        @Override
        protected void toevoegenStatischeVertalingen(final List<Entry<Lo3GemeenteCode, BrpPartijCode>> resultaat) {
            resultaat.add(new SimpleEntry<Lo3GemeenteCode, BrpPartijCode>(Lo3GemeenteCode.RNI, BrpPartijCode.MINISTER));

        }

    }
}
