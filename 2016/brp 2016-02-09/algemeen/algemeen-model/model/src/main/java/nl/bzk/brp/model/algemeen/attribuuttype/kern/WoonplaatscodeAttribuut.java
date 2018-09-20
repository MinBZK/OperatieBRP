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
 * Attribuut wrapper klasse voor Woonplaatscode.
 */
@Embeddable
public class WoonplaatscodeAttribuut extends AbstractWoonplaatscodeAttribuut {

    private static final int LENGTE_CODE = 4;

    /**
     * Lege private constructor voor WoonplaatscodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private WoonplaatscodeAttribuut() {
        super();
    }

    /**
     * Constructor voor WoonplaatscodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public WoonplaatscodeAttribuut(final Short waarde) {
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

    /**
     * Functie verseist voor Jibx maar verder niet gebruikt.
     *
     * @param waarde String
     */
    protected void setCode(final String waarde) {
        throw new IllegalStateException("Mag niet aangeroepen worden vanuit Jibx; dit is alleen maar een out methode.");
    }
}
