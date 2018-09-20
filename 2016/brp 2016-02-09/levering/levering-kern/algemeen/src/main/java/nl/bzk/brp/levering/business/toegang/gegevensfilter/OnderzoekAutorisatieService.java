/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;

/**
 * Service voor het autoriseren van onderzoeken.
 */
public interface OnderzoekAutorisatieService {

    /**
     * Autoriseer onderzoeken binnen een persoon.
     *
     * @param persoonHisVolledigView de persoon.
     * @param persoonOnderzoekenMap  de mapping tussen persoonsgegevens en attributen.
     * @return lijst met geraakte attributen
     */
    List<Attribuut> autoriseerOnderzoeken(PersoonHisVolledigView persoonHisVolledigView,
                                          Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap);
}
