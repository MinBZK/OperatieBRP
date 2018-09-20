/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Ja \ Nee.
 */
@Embeddable
public class JaNeeAttribuut extends AbstractJaNeeAttribuut {

    /**
     * Constante voor de waarde JA = JaNeeAttribuut(true).
     */
    public static final JaNeeAttribuut JA  = new JaNeeAttribuut(true);
    /**
     * Constante voor de waarde NEE = JaNeeAttribuut(false).
     */
    public static final JaNeeAttribuut NEE = new JaNeeAttribuut(false);

    /**
     * Lege private constructor voor JaNeeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private JaNeeAttribuut() {
        super();
    }

    /**
     * Constructor voor JaNeeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public JaNeeAttribuut(final Boolean waarde) {
        super(waarde);
    }

}
