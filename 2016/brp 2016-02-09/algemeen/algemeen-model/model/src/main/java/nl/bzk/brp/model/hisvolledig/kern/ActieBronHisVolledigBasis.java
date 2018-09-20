/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RechtsgrondAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelPeriode;

/**
 * Interface voor Actie \ Bron.
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigInterfaceModelGenerator")
public interface ActieBronHisVolledigBasis extends ModelPeriode, ModelIdentificeerbaar<Long>, BrpObject {

    /**
     * Retourneert Actie van Actie \ Bron.
     *
     * @return Actie van Actie \ Bron
     */
    ActieHisVolledig getActie();

    /**
     * Retourneert Document van Actie \ Bron.
     *
     * @return Document van Actie \ Bron
     */
    DocumentHisVolledig getDocument();

    /**
     * Retourneert Rechtsgrond van Actie \ Bron.
     *
     * @return Rechtsgrond van Actie \ Bron
     */
    RechtsgrondAttribuut getRechtsgrond();

    /**
     * Retourneert Rechtsgrondomschrijving van Actie \ Bron.
     *
     * @return Rechtsgrondomschrijving van Actie \ Bron
     */
    OmschrijvingEnumeratiewaardeAttribuut getRechtsgrondomschrijving();

}
