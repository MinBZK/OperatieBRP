/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;
import org.apache.commons.lang.StringUtils;


/**
 * Attribuut wrapper klasse voor Reden verlies code.
 */
@Embeddable
public class RedenVerliesCodeAttribuut extends AbstractRedenVerliesCodeAttribuut {

    /**
     * De maximale lengte van de code.
     */
    public static final int MAX_LENGTE = 3;

    /**
     * Lege private constructor voor RedenVerliesCodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private RedenVerliesCodeAttribuut() {
        super();
    }

    /**
     * Constructor voor RedenVerliesCodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public RedenVerliesCodeAttribuut(final Short waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        String resultaat;
        if (getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, MAX_LENGTE, '0');
        }
        return resultaat;
    }
}
