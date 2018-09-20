/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.verconv.LO3SoortAanduidingOuderAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;

/**
 * Interface voor LO3 Aanduiding Ouder.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LO3AanduidingOuderHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Ouder van LO3 Aanduiding Ouder.
     *
     * @return Ouder van LO3 Aanduiding Ouder
     */
    OuderHisVolledig getOuder();

    /**
     * Retourneert Soort van LO3 Aanduiding Ouder.
     *
     * @return Soort van LO3 Aanduiding Ouder
     */
    LO3SoortAanduidingOuderAttribuut getSoort();

}
