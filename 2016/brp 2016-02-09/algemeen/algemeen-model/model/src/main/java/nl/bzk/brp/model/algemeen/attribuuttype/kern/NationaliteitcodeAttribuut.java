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
 * Attribuut wrapper klasse voor Nationaliteitcode.
 */
@Embeddable
public class NationaliteitcodeAttribuut extends AbstractNationaliteitcodeAttribuut {

    private static final int LENGTE_CODE = 4;

    /**
     * Constante voor nederlandse nationaliteit code.
     */
    public static final String NL_NATIONALITEIT_CODE_STRING = "0001";

    /**
     * Constante voor nederlandse nationaliteit code.
     */
    public static final NationaliteitcodeAttribuut NL_NATIONALITEIT_CODE = new NationaliteitcodeAttribuut(
        NL_NATIONALITEIT_CODE_STRING);

    /**
     * Lege private constructor voor NationaliteitcodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private NationaliteitcodeAttribuut() {
        super();
    }

    /**
     * Constructor voor NationaliteitcodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public NationaliteitcodeAttribuut(final Short waarde) {
        super(waarde);
    }

    /**
     * Constructor voor Nationaliteitcode die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    public NationaliteitcodeAttribuut(final String waarde) {
        super(Short.valueOf(waarde));
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
