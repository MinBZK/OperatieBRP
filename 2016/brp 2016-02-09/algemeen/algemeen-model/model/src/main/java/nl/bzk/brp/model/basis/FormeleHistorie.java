/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;


/**
 * Interface methodes van een groep met formele historie.
 */
public interface FormeleHistorie {

    /**
     * Retourneert (een kopie van) de timestamp van registratie.
     *
     * @return (een kopie) van de timestamp van registratie.
     */
    DatumTijdAttribuut getTijdstipRegistratie();

    /**
     * Retourneert (een kopie van) de timestamp van vervallen.
     *
     * @return (een kopie) van de timestamp van vervallen.
     */
    DatumTijdAttribuut getDatumTijdVerval();

}
