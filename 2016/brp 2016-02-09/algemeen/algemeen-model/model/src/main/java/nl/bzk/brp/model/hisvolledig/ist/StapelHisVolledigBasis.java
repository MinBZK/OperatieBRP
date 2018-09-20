/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.ist;

import java.util.Set;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ist.LO3CategorieAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;
import nl.bzk.brp.model.basis.VolgnummerBevattend;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Interface voor Stapel.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface StapelHisVolledigBasis extends ModelPeriode, VolgnummerBevattend, ModelIdentificeerbaar<Integer>, BrpObject {

    /**
     * Retourneert Persoon van Stapel.
     *
     * @return Persoon van Stapel
     */
    PersoonHisVolledig getPersoon();

    /**
     * Retourneert Categorie van Stapel.
     *
     * @return Categorie van Stapel
     */
    LO3CategorieAttribuut getCategorie();

    /**
     * Retourneert Volgnummer van Stapel.
     *
     * @return Volgnummer van Stapel
     */
    VolgnummerAttribuut getVolgnummer();

    /**
     * Retourneert lijst van Stapel \ Relatie.
     *
     * @return lijst van Stapel \ Relatie
     */
    Set<? extends StapelRelatieHisVolledig> getStapelRelaties();

    /**
     * Retourneert lijst van Stapel voorkomen.
     *
     * @return lijst van Stapel voorkomen
     */
    Set<? extends StapelVoorkomenHisVolledig> getStapelVoorkomens();

}
