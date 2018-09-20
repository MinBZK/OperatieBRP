/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3GroepAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3RubriekExclCategorieEnGroepAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SeverityAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortMeldingAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor LO3 Melding.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LO3MeldingHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert LO3 voorkomen van LO3 Melding.
     *
     * @return LO3 voorkomen van LO3 Melding
     */
    LO3VoorkomenHisVolledig getLO3Voorkomen();

    /**
     * Retourneert Soort van LO3 Melding.
     *
     * @return Soort van LO3 Melding
     */
    LO3SoortMeldingAttribuut getSoort();

    /**
     * Retourneert Log severity van LO3 Melding.
     *
     * @return Log severity van LO3 Melding
     */
    LO3SeverityAttribuut getLogSeverity();

    /**
     * Retourneert Groep van LO3 Melding.
     *
     * @return Groep van LO3 Melding
     */
    LO3GroepAttribuut getGroep();

    /**
     * Retourneert Rubriek van LO3 Melding.
     *
     * @return Rubriek van LO3 Melding
     */
    LO3RubriekExclCategorieEnGroepAttribuut getRubriek();

}
