/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Datum (evt. deels onbekend).
 */
@Embeddable
public class DatumEvtDeelsOnbekendAttribuut extends AbstractDatumEvtDeelsOnbekendAttribuut {

    /**
     * Leeftijd constante, omdat die vaak gebruikt wordt.
     */
    public static final Integer                        ZESTIEN_JAAR       = 16;
    /**
     * Leeftijd constante, omdat die vaak gebruikt wordt.
     */
    public static final Integer                        ACHTTIEN_JAAR       = 18;
    /**
     * 18 jaar als datum.
     */
    public static final DatumEvtDeelsOnbekendAttribuut ACHTTIEN_JAAR_DATUM = new DatumEvtDeelsOnbekendAttribuut(ACHTTIEN_JAAR * 1000 * 10 * 10);

    /**
     * alleen omdat dit vaak gebruikt wordt.
     */
    public static final Integer                        ZEVEN_JAAR       = 7;
    /**
     * 7 jaar als datum.
     */
    public static final DatumEvtDeelsOnbekendAttribuut ZEVEN_JAAR_DATUM = new DatumEvtDeelsOnbekendAttribuut(ZEVEN_JAAR * 1000 * 10 * 10);



    /**
     * Lege private constructor voor DatumEvtDeelsOnbekendAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private DatumEvtDeelsOnbekendAttribuut() {
        super();
    }

    /**
     * Constructor voor DatumEvtDeelsOnbekendAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public DatumEvtDeelsOnbekendAttribuut(final Integer waarde) {
        super(waarde);
    }

    /**
     * Constructor voor DatumEvtDeelsOnbekendAttribuut op basis van een geheel bekende datum.
     *
     * @param volledigeDatum Een volledige datum.
     */
    public DatumEvtDeelsOnbekendAttribuut(final DatumAttribuut volledigeDatum) {
        super(volledigeDatum.getWaarde());
    }

}
