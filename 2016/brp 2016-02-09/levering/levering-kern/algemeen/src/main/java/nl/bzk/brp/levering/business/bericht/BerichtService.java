/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import java.util.List;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;

/**
 * Interface voor de service die methoden biedt die betrekking hebben op het type van berichten.
 */
public interface BerichtService {
    /**
     * Verwijdert personen uit het bericht die "leeg" zijn, dat wil zeggen er zijn
     * geen valide groepen om te leveren.
     * brp.bedrijfsregel VR00089
     *
     * @param bericht het bericht om te filteren
     */
    void filterLegePersonen(SynchronisatieBericht bericht);

    /**
     * Voegt verwerkingssoorten toe aan het bericht.
     *
     * @param persoonHisVolledigViews    Het bericht om verwerkingssoorten aan toe te voegen
     * @param administratieveHandelingId De id van de administratieve handeling.
     */
    void voegVerwerkingssoortenToe(List<PersoonHisVolledigView> persoonHisVolledigViews, Long administratieveHandelingId);

    /**
     * Verwijdert geplaatste waardes van de velden <code>verwerkingssoort</code>.
     *
     * @param persoonHisVolledigViews De personen waarop de verwerkingssoorten verwijderd moeten worden.
     */
    void verwijderVerwerkingssoortenUitPersonen(List<PersoonHisVolledigView> persoonHisVolledigViews);
}
