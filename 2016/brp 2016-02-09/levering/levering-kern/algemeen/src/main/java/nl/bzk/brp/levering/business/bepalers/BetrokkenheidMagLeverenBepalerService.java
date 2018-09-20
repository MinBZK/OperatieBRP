/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;

/**
 * Interface voor het zetten van lever vlaggen op Betrokkenheid, Relatie en Persoon objecten.
 * Deze vlaggen worden later uitgelezen bij het bevragen van de view door de JibX bindings voor het bepalen
 * of een betrokkenheid, relatie of betrokken persoon geleverd wordt.
 *
 * @see nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView#getBetrokkenhedenVoorLeveren()
 * @see nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView#getBetrokkenhedenVoorLeveren()
 */
public interface BetrokkenheidMagLeverenBepalerService {

    /**
     * Bepaalt voor mutatie- en volledigberichtleveringen welke objecten geleverd mogen worden.
     * Wanneer bepaald wordt de magLeveren vlag van het object op true gezet.
     * @param persoonHisVolledigView de persoon view
     * @param dienst de dienst
     * @param isMutatieLevering indicatie of het een mutiatielevering betreft
     */
    void bepaalMagLeveren(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst, boolean isMutatieLevering);
}
