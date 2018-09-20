/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen.service;

import java.util.Set;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;


/**
 * De interface voor de Niet Relevante Actie Service die wordt gebruikt om relevante acties te bepalen bij het opstellen
 * van leveringberichten.
 */
public interface ToekomstigeActieService {

    /**
     * Bepaalt welke acties niet relevant zijn als we van een persoon de persoonlijst willen construeren tot een
     * bepaalde administratieve handeling.
     * Dit betekent dat handelingen die na admhnd zijn verwerkt uitgesloten moeten worden, van deze handelingen bepalen
     * we de acties.
     *
     * @param admhnd de administratieve handeling tot waar je de persoonlijst wilt reconstrueren.
     * @param persoonHisVolledig de persoonslijst
     * @return Een set met niet relevante acties.
     *
     * @brp.bedrijfsregel VR00097
     */
    @Regels(Regel.VR00097)
    Set<Long> geefToekomstigeActieIdsPlusHuidigeHandeling(AdministratieveHandelingModel admhnd,
            PersoonHisVolledig persoonHisVolledig);

    /**
     * Bepaalt welke acties niet relevant zijn als we van een persoon de persoonlijst willen construeren tot een
     * bepaalde administratieve handeling.
     * Dit betekent dat handelingen die na admhnd zijn verwerkt uitgesloten moeten worden, van deze handelingen bepalen
     * we de acties.
     *
     * @param admhnd de administratieve handeling tot waar je de persoonlijst wilt reconstrueren.
     * @param persoonHisVolledig de persoonslijst
     * @return Een set met niet relevante acties.
     *
     */
    Set<Long> geefToekomstigeActieIds(AdministratieveHandelingModel admhnd, PersoonHisVolledig persoonHisVolledig);
}
