/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractLandcode;

import org.apache.commons.lang.StringUtils;



/**
 *
 *
 */
@Embeddable
public class Landcode extends AbstractLandcode {

    private static final int LENGTE_CODE = 4;

    /**
     * Lege (value-object) constructor voor Landcode, niet gedeclareerd als public om te voorkomen dat objecten zonder
     * waarde
     * worden geïnstantieerd.
     *
     */
    private Landcode() {
        super();
    }

    /**
     * Constructor voor Landcode die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public Landcode(final Short waarde) {
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
     * @param waarde String
     */
    protected void setCode(final String waarde) {
        throw new RuntimeException("Mag niet aangeroepen worden vanuit Jibx; dit is alleen maar een out methode.");
    }
}
