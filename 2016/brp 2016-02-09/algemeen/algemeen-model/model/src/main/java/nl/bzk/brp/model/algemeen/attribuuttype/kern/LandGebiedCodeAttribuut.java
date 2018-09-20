/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.Embeddable;


/**
 * Attribuut wrapper klasse voor Land/gebied code.
 */
@Embeddable
public class LandGebiedCodeAttribuut extends AbstractLandGebiedCodeAttribuut {

    /**
     * Constante voor nederlandse land code.
     */
    public static final Short                   NL_LAND_CODE_SHORT       = 6030;
    /**
     * Constante voor onbekende land code.
     */
    public static final Short                   ONBEKEND_LAND_CODE_SHORT = 0;
    /**
     * Constante voor nederlandse land code.
     */
    public static final String                  NL_LAND_CODE_STRING      = "6030";
    /**
     * Constante voor Nederlandse land/gebied code.
     */
    public static final LandGebiedCodeAttribuut NEDERLAND             = new LandGebiedCodeAttribuut(NL_LAND_CODE_SHORT);
    /**
     * Constante voor onbekende land/gebied code.
     */
    public static final LandGebiedCodeAttribuut ONBEKEND       = new LandGebiedCodeAttribuut(ONBEKEND_LAND_CODE_SHORT);

    /**
     * Lege private constructor voor LandGebiedCodeAttribuut, om te voorkomen dat wrappers zonder waarde worden geïnstantieerd.
     */
    private LandGebiedCodeAttribuut() {
        super();
    }

    /**
     * Constructor voor LandGebiedCodeAttribuut die de waarde toekent.
     *
     * @param waarde De waarde van het attribuut.
     */
    @JsonCreator
    public LandGebiedCodeAttribuut(final Short waarde) {
        super(waarde);
    }

}
