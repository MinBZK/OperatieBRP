/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.ber;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Systeem naam.
 */
@Embeddable
public class SysteemNaamAttribuut extends AbstractSysteemNaamAttribuut {

    /**
     * Systeemnaam BRP.
     */
    public static final SysteemNaamAttribuut BRP = new SysteemNaamAttribuut("BRP");

    /**
     * Lege private constructor voor SysteemNaamAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private SysteemNaamAttribuut() {
        super();
    }

    /**
     * Constructor voor SysteemNaamAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public SysteemNaamAttribuut(final String waarde) {
        super(waarde);
    }

}
