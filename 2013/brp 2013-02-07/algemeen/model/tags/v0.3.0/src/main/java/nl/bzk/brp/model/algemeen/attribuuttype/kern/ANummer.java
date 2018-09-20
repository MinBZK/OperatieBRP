/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractANummer;

import org.apache.commons.lang.StringUtils;


/**
 *
 *
 */
@Embeddable
public class ANummer extends AbstractANummer {

    private static final int MAX_LENGTH = 10;

    /**
     * Lege (value-object) constructor voor ANummer, niet gedeclareerd als public om te voorkomen dat objecten zonder
     * waarde
     * worden geïnstantieerd.
     *
     */
    private ANummer() {
        super();
    }

    /**
     * Constructor voor ANummer die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public ANummer(final Long waarde) {
        super(waarde);
    }

    @Override
    public String toString() {
        String resultaat;
        if (this.getWaarde() == null) {
            resultaat = StringUtils.EMPTY;
        } else {
            resultaat = this.getWaarde().toString();
            resultaat = StringUtils.leftPad(resultaat, MAX_LENGTH, '0');
        }
        return resultaat;
    }

}
