/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;


/**
 * Classes die de formele historie ondersteunen.
 */
public interface FormeleHistorieModel extends FormeleHistorie {

    /**
     * Zet de datum tijd registratie.
     *
     * @param datumTijd DatumTijd
     */
    void setDatumTijdRegistratie(DatumTijdAttribuut datumTijd);

    /**
     * Zet de datum tijd verval.
     *
     * @param datumTijd DatumTijd
     */
    void setDatumTijdVerval(DatumTijdAttribuut datumTijd);
}
