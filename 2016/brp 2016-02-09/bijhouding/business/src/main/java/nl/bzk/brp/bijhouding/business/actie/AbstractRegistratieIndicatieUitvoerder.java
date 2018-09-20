/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Abstracte tussen-klasse uitvoerder voor de registratie van indicaties.
 * Bevat de logica om de persoon indicatie uit het bericht te halen.
 *
 */
public abstract class AbstractRegistratieIndicatieUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Haal (de enige) persoon indicatie uit het bericht.
     *
     * @return de persoon indicatie bericht
     */
    protected final PersoonIndicatieBericht getPersoonIndicatieBericht() {
        return getBerichtRootObject().getIndicaties().iterator().next();
    }

    protected final PersoonHisVolledigImpl getPersoonHisVolledig() {
        return getModelRootObject();
    }

}
