/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.ber;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

/**
 * Attribuut wrapper klasse voor BerichtdataLax (XSD).
 *
 */
@Embeddable
@Access(value = AccessType.PROPERTY)
public class BerichtdataLaxXSDAttribuut extends AbstractBerichtdataLaxXSDAttribuut {

    /**
     * Lege private constructor voor BerichtdataLaxXSDAttribuut, om te voorkomen dat wrappers zonder waarde worden
     * geïnstantieerd.
     *
     */
    private BerichtdataLaxXSDAttribuut() {
        super();
    }

    /**
     * Constructor voor BerichtdataLaxXSDAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public BerichtdataLaxXSDAttribuut(final String waarde) {
        super(waarde);
    }

}
