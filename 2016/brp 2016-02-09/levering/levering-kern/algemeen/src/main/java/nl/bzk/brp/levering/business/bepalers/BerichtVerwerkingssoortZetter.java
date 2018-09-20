/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;

/**
 * Binnen de levering berichten worden verwerkingssoorten gebruikt. Deze geven aan wat er gebeurd is met een groep.
 * Deze klasse voegt deze verwerkingssoorten toe voor alle elementen in het bericht.
 */
public interface BerichtVerwerkingssoortZetter {

    /**
     * Voegt de verwerkingssoorten toe op de elementen van het kennisgevingbericht.
     *
     * @param persoonHisVolledigViews    de persoonviews waarop de elementen moeten worden toegevoegd
     * @param administratieveHandelingId de kennisgeving ten behoeve van het bepalen van de soorten
     */
    void voegVerwerkingssoortenToe(List<PersoonHisVolledigView> persoonHisVolledigViews, Long administratieveHandelingId);

    /**
     * Voegt de verwerkingssoorten toe op de elementen van een persoonView.
     *
     * @param persoonHisVolledigView     de persoonview waarop de elementen moeten worden toegevoegd
     * @param administratieveHandelingId de kennisgeving ten behoeve van het bepalen van de soorten
     */
    void voegVerwerkingssoortenToe(PersoonHisVolledigView persoonHisVolledigView, Long administratieveHandelingId);
}
