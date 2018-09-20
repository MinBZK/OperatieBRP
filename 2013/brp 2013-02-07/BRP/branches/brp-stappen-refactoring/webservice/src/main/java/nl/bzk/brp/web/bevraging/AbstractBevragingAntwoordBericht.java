/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.util.List;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.web.AbstractAntwoordBericht;

/** Abstracte antwoord bericht voor BRP bevragingsberichten. */
public abstract class AbstractBevragingAntwoordBericht extends AbstractAntwoordBericht {

    private List<PersoonModel> personen = null;

    protected AbstractBevragingAntwoordBericht(final SoortBericht soort) {
        super(soort);
    }

    public List<PersoonModel> getPersonen() {
        return personen;
    }

    public void setPersonen(final List<PersoonModel> personen) {
        this.personen = personen;
    }
}
