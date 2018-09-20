/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;


/**
 * Classes die de formele historie ondersteunen.
 *
 */
public interface FormeleHistorie {

    /**
     * Retourneert (een kopie van) de timestamp van registratie.
     *
     * @return (een kopie) van de timestamp van registratie.
     */
    DatumTijd getDatumTijdRegistratie();

    /**
     * Zet de datum tijd registratie.
     *
     * @param datumTijd DatumTijd
     */
    void setDatumTijdRegistratie(DatumTijd datumTijd);

    /**
     * Retourneert (een kopie van) de timestamp van vervallen.
     *
     * @return (een kopie) van de timestamp van vervallen.
     */
    DatumTijd getDatumTijdVerval();

    /**
     * Zet de datum tijd verval.
     *
     * @param datumTijd DatumTijd
     */
    void setDatumTijdVerval(DatumTijd datumTijd);

    /**
     * Retourneert de actie.
     *
     * @return actie.
     */
    ActieModel getActieInhoud();

    /**
     * Zet de actie.
     *
     * @param actie ActieMdl
     */
    void setActieInhoud(ActieModel actie);

    /**
     * Retourneert de actieVerval.
     *
     * @return actie
     */
    ActieModel getActieVerval();

    /**
     * Zet de actie voor verval.
     *
     * @param actie ActieMdl
     */
    void setActieVerval(ActieModel actie);
}
