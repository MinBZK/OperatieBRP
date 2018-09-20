/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.verconv;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor LO3 Voorkomen.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface LO3VoorkomenHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert LO3 Bericht van LO3 Voorkomen.
     *
     * @return LO3 Bericht van LO3 Voorkomen
     */
    LO3BerichtHisVolledig getLO3Bericht();

    /**
     * Retourneert LO3 categorie van LO3 Voorkomen.
     *
     * @return LO3 categorie van LO3 Voorkomen
     */
    LO3CategorieAttribuut getLO3Categorie();

    /**
     * Retourneert LO3 stapelvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 stapelvolgnummer van LO3 Voorkomen
     */
    VolgnummerAttribuut getLO3Stapelvolgnummer();

    /**
     * Retourneert LO3 voorkomenvolgnummer van LO3 Voorkomen.
     *
     * @return LO3 voorkomenvolgnummer van LO3 Voorkomen
     */
    VolgnummerAttribuut getLO3Voorkomenvolgnummer();

}
