/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;

/**
 * VR00016: Verwerkingsregel van de deelname EU verkiezingen groep.
 */
public class DeelnameEUVerkiezingenGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param persoonBericht het persoon bericht
     * @param persoonModel het persoon model
     * @param actie de actie
     */
    public DeelnameEUVerkiezingenGroepVerwerker(final PersoonBericht persoonBericht, final PersoonHisVolledigImpl persoonModel, final ActieModel actie) {
        super(persoonBericht, persoonModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00016;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonDeelnameEUVerkiezingenModel hisPersoonEUVerkiezingenModel =
                new HisPersoonDeelnameEUVerkiezingenModel(getModel(), getBericht().getDeelnameEUVerkiezingen(), getActie());
        getModel().getPersoonDeelnameEUVerkiezingenHistorie().voegToe(hisPersoonEUVerkiezingenModel);
    }
}
