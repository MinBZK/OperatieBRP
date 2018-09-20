/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;

/**
 * VR00015 Verwerken Groep Bijhouding.
 */
public class BijhoudingGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model   het model object
     * @param actie   de actie
     */
    public BijhoudingGroepVerwerker(final PersoonBericht bericht, final PersoonHisVolledigImpl model, final ActieModel actie) {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00015;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonBijhoudingModel hisPersoonBijhoudingModel =
            new HisPersoonBijhoudingModel(getModel(), getBericht().getBijhouding(), getBericht().getBijhouding(), getActie());
        getModel().getPersoonBijhoudingHistorie().voegToe(hisPersoonBijhoudingModel);
    }

    @Override
    public void verzamelAfleidingsregels() {
        // TODO Niet in scope geweest:
        // Na actualisering van de NadereBijhoudingsaard (groep Bijhouding) naar "Emigratie", wordt zo nodig de deelname aan DeelnameEUverkiezingen
        // afgeleid gestopt.
    }
}
