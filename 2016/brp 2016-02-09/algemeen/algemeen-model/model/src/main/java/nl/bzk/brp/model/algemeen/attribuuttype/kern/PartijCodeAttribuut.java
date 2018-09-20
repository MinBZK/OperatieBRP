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
 * Attribuut wrapper klasse voor Partij code.
 */
@Embeddable
public class PartijCodeAttribuut extends AbstractPartijCodeAttribuut {

    /**
     * Constante voor partij code Minister.
     */
    public static final PartijCodeAttribuut MINISTER        = new PartijCodeAttribuut(199901);
    /**
     * Constante voor partij code BRP Voorziening.
     */
    public static final PartijCodeAttribuut BRP_VOORZIENING = new PartijCodeAttribuut(199903);

    private static final int LENGTE_CODE = 6;


    /**
     * Lege private constructor voor PartijCodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private PartijCodeAttribuut() {
        super();
    }

    /**
     * Constructor voor PartijCodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public PartijCodeAttribuut(final Integer waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        String resultaat;
        if (getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, LENGTE_CODE, '0');
        }
        return resultaat;
    }
}
