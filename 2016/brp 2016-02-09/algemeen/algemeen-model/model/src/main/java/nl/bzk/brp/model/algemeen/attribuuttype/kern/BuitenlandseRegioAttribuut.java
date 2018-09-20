/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Buitenlandse regio.
 */
@Embeddable
public class BuitenlandseRegioAttribuut extends AbstractBuitenlandseRegioAttribuut {

    /**
     * Lege private constructor voor BuitenlandseRegioAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private BuitenlandseRegioAttribuut() {
        super();
    }

    /**
     * Constructor voor BuitenlandseRegioAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public BuitenlandseRegioAttribuut(final String waarde) {
        super(waarde);
    }

}
