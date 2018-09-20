/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;

/**
 * De bij de verwerking van een LO3 bericht optredende melding.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface LO3MeldingBasis extends BrpObject {

    /**
     * Retourneert LO3 voorkomen van LO3 Melding.
     *
     * @return LO3 voorkomen.
     */
    LO3Voorkomen getLO3Voorkomen();

    /**
     * Retourneert Soort van LO3 Melding.
     *
     * @return Soort.
     */
    LO3SoortMeldingAttribuut getSoort();

    /**
     * Retourneert Log severity van LO3 Melding.
     *
     * @return Log severity.
     */
    LO3SeverityAttribuut getLogSeverity();

    /**
     * Retourneert Groep van LO3 Melding.
     *
     * @return Groep.
     */
    LO3GroepAttribuut getGroep();

    /**
     * Retourneert Rubriek van LO3 Melding.
     *
     * @return Rubriek.
     */
    LO3RubriekExclCategorieEnGroepAttribuut getRubriek();

}
