/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 * Actie His Volledig locator.
 *
 * Wordt gebruikt omdat acties die bij een historie entiteit bereikbaar zijn van het type ActieModel zijn. Voor de
 * conversie is ActieHisVolledig nodig omdat we de historie van documenten nodig hebben. De ActieHisVolledig objecten
 * zijn te bereiken door uit de persoon de verantwoording (PersoonHisVolledig.getAdministratieveHandelingen()) te halen.
 */
public interface ActieHisVolledigLocator {

    /**
     * Vind de ActieHisVolledig die hoort bij het gegeven actie model.
     *
     * @param actieModel actie
     * @return actie his volledig
     */
    ActieHisVolledig locate(final ActieModel actieModel);
}
