/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.stappen.resultaat;

import java.util.Collections;
import java.util.Set;

/**
 * Verrijkt een getypeerde waarde met resultaatmeldingen.
 * @param <T> het type van de waarde
 */
public final class WaardeMetMeldingen<T> {
    private T                      waarde;
    private Set<ResultaatMelding> meldingen;

    /**
     * Constructor met waarde en meldingen.
     * @param waarde de waarde
     * @param meldingen de meldingen
     */
    public WaardeMetMeldingen(final T waarde, final Set<ResultaatMelding> meldingen) {
        this.waarde = waarde;
        this.meldingen = meldingen;
    }

    /**
     * Constructor met alleen een waarde.
     * @param waarde de waarde
     */
    public WaardeMetMeldingen(final T waarde) {
        this(waarde, Collections.<ResultaatMelding>emptySet());
    }

    /**
     * Constructor voor alleen de melding.
     * @param melding de melding
     */
    public WaardeMetMeldingen(final ResultaatMelding melding) {
        this(null, Collections.singleton(melding));
    }

    public T getWaarde() {
        return waarde;
    }

    public Set<ResultaatMelding> getMeldingen() {
        return meldingen;
    }
}
