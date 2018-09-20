/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import nl.bzk.brp.model.basis.AbstractAttribuut;


/**
 * Attribuut wrapper klasse voor Reden opschorting.
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
public class RedenOpschortingAttribuut extends AbstractAttribuut<RedenOpschorting> {

    /**
     * Lege private constructor voor RedenOpschortingAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private RedenOpschortingAttribuut() {
        super(null);
    }

    /**
     * Constructor voor RedenOpschortingAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    public RedenOpschortingAttribuut(final RedenOpschorting waarde) {
        super(waarde);
    }

    /**
     * Retourneert de waarde van het attribuut. Bevat de specifieke configuratie voor het soort gewrapte object.
     *
     * @return de waarde van het attribuut
     */
    @Override
    @JsonProperty
    @Enumerated
    public RedenOpschorting getWaarde() {
        return super.getWaarde();
    }

}
