/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bevraging;

import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.AbstractIdentificerendeGroep;

/**
 * De logische groep in het xml bericht die opties voor de vraag kunnen bevatten.
 *
 */
public class VraagOpties extends AbstractIdentificerendeGroep {
    // gemoved van AbstractVraag.
    private Ja historieMaterieel;
    private Ja historieFormeel;
    private Burgerservicenummer aanschouwer;

    public Ja getHistorieMaterieel() {
        return historieMaterieel;
    }

    public void setHistorieMaterieel(final Ja historieMaterieel) {
        this.historieMaterieel = historieMaterieel;
    }

    public Ja getHistorieFormeel() {
        return historieFormeel;
    }

    public void setHistorieFormeel(final Ja historieFormeel) {
        this.historieFormeel = historieFormeel;
    }

    public Burgerservicenummer getAanschouwer() {
        return aanschouwer;
    }

    public void setAanschouwer(final Burgerservicenummer aanschouwer) {
        this.aanschouwer = aanschouwer;
    }

}
