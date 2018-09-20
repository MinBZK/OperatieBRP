/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.AntwoordBericht;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * Abstracte antwoord bericht voor BRP bevragingsberichten.
 */
public class BevragingAntwoordBericht extends AntwoordBericht {

    private List<PersoonHisVolledigView> gevondenPersonen;

    /**
     * Standaard constructor die de soort van het bericht zet.
     *
     * @param soort de soort van het bericht.
     */
    protected BevragingAntwoordBericht(final SoortBericht soort) {
        super(soort);
        this.gevondenPersonen = new ArrayList<>();
    }

    public final List<PersoonHisVolledigView> getGevondenPersonen() {
        return gevondenPersonen;
    }

    /**
     * Voeg een persoon aan het antwoord bericht toe.
     *
     * @param persoon de persoon
     */
    public final void voegGevondenPersoonToe(final PersoonHisVolledigView persoon) {
        this.gevondenPersonen.add(persoon);
    }

    /**
     * Geeft aan of er een resultaat is voor deze bevraging. Dat is het geval als er een of meer personen gevonden zijn.
     *
     * @return true als er iets gevonden is
     */
    public final boolean isResultaatGevonden() {
        return getGevondenPersonen().size() > 0;
    }

    /**
     * Geeft aan of het bericht meldingen bevat.
     *
     * @return true als er meldingen zijn, anders false.
     */
    public final boolean heeftMeldingen() {
        return getMeldingen() != null && !getMeldingen().isEmpty();
    }
}
