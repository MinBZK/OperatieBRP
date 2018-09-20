/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.ist;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.basis.VolgnummerBevattend;

/**
 * Interface voor Stapel voorkomen.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface StapelVoorkomenHisVolledigBasis extends ModelPeriode, VolgnummerBevattend, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert Stapel van Stapel voorkomen.
     *
     * @return Stapel van Stapel voorkomen
     */
    StapelHisVolledig getStapel();

    /**
     * Retourneert Volgnummer van Stapel voorkomen.
     *
     * @return Volgnummer van Stapel voorkomen
     */
    VolgnummerAttribuut getVolgnummer();

}
