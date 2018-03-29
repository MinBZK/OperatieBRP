/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.tabel;

import java.util.List;
import java.util.Map.Entry;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.RedenOpschorting;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpNadereBijhoudingsaardCode;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.ConversieMapEntry;
import nl.bzk.migratiebrp.conversie.model.domein.conversietabel.dynamisch.AbstractRedenOpschortingConversietabel;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3RedenOpschortingBijhoudingCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;

/**
 * Deze conversietabel map een Lo3RedenOpschortingBijhoudingCode op de corresponderen BrpRedenOpschortingBijhoudingCode
 * vice versa.
 */
public final class RedenOpschortingConversietabel extends AbstractRedenOpschortingConversietabel {

    /**
     * Maakt een RedenOpschortingConversietabel object.
     * @param list de lijst met waarden uit de conversietabel.
     */
    public RedenOpschortingConversietabel(final List<RedenOpschorting> list) {
        super(new Converter().converteer(list));
    }

    /**
     * Converteer de lijst van {@link RedenOpschorting} naar een conversie map van de LO3 waarde
     * {@link Lo3RedenOpschortingBijhoudingCode} en de BRP waarde {@link BrpNadereBijhoudingsaardCode}.
     */
    private static final class Converter
            extends AbstractLijstConverter<RedenOpschorting, Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode> {
        @Override
        protected void toevoegenStatischeVertalingen(final List<Entry<Lo3RedenOpschortingBijhoudingCode, BrpNadereBijhoudingsaardCode>> resultaat) {
            resultaat.add(new ConversieMapEntry<>(Lo3RedenOpschortingBijhoudingCodeEnum.RNI.asElement(), (BrpNadereBijhoudingsaardCode) null));
        }

        @Override
        protected Lo3RedenOpschortingBijhoudingCode maakLo3Waarde(final RedenOpschorting redenOpschorting) {
            return new Lo3RedenOpschortingBijhoudingCode(Character.toString(redenOpschorting.getLo3OmschrijvingRedenOpschorting()));
        }

        @Override
        protected BrpNadereBijhoudingsaardCode maakBrpWaarde(final RedenOpschorting redenOpschorting) {
            return new BrpNadereBijhoudingsaardCode(redenOpschorting.getRedenOpschorting().getCode());
        }
    }
}
