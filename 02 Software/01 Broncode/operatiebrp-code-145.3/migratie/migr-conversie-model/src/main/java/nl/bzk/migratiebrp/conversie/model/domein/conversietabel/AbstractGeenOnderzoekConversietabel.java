/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.domein.conversietabel;

import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Bevat hulpmethodes voor conversie mapping functionaliteit voor als er geen onderzoek meegeconverteerd moet worden.
 * @param <L> de LO3 waarde
 * @param <B> de BRP waarde
 */
public abstract class AbstractGeenOnderzoekConversietabel<L, B> extends AbstractConversietabel<L, B> {
    /**
     * Maakt een AbstractConversietabel object.
     * @param conversieLijst de lijst met conversies.
     */
    public AbstractGeenOnderzoekConversietabel(final List<Map.Entry<L, B>> conversieLijst) {
        super(conversieLijst);
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekLo3(final L input) {
        return null;
    }

    @Override
    protected final Lo3Onderzoek bepaalOnderzoekBrp(final B input) {
        return null;
    }

    @Override
    protected final L voegOnderzoekToeLo3(final L input, final Lo3Onderzoek onderzoek) {
        return input;
    }

    @Override
    protected final B voegOnderzoekToeBrp(final B input, final Lo3Onderzoek onderzoek) {
        return input;
    }
}
