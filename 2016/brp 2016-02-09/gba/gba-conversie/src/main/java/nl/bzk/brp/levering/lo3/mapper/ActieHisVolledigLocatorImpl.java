/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Standaard implementatie.
 */
public final class ActieHisVolledigLocatorImpl implements ActieHisVolledigLocator {

    private final Map<Long, ActieHisVolledig> acties;

    /**
     * Constructor.
     *
     * @param persoonHisVolledig persoon volledig
     */
    public ActieHisVolledigLocatorImpl(final PersoonHisVolledig persoonHisVolledig) {
        acties = new HashMap<>();
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : persoonHisVolledig.getAdministratieveHandelingen()) {
            for (final ActieHisVolledig actie : administratieveHandeling.getActies()) {
                acties.put(actie.getID(), actie);
            }
        }
    }

    @Override
    public ActieHisVolledig locate(final ActieModel actieModel) {
        return acties.get(actieModel.getID());
    }

}
