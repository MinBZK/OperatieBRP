/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * Bepaler die vaststeld wanneer een persoonview geen informatie meer bevat
 * die geleverd mag worden.
 */
public interface LegeBerichtBepaler {

    /**
     * Bepaalt of een persoon geleverd mag worden, dit werkt alleen voor mutatieberichten.
     *
     * @param persoonView              de persoon view om te checken
     * @return {@code true} als de persoon informatie heeft die geleverd mag worden
     */
    boolean magPersoonGeleverdWorden(PersoonHisVolledigView persoonView);

    /**
     * Bepaalt of een betrokken persoon geleverd mag worden, dit werkt alleen voor mutatieberichten.
     *
     * @param persoonView              de persoon view om te checken
     * @return {@code true} als de persoon informatie heeft die geleverd mag worden
     */
    boolean magBetrokkenPersoonGeleverdWorden(PersoonHisVolledigView persoonView);
}
