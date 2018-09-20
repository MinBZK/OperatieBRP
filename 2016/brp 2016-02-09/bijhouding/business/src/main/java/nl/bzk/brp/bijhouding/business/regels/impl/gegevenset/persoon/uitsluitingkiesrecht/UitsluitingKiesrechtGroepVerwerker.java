/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.uitsluitingkiesrecht;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonUitsluitingKiesrechtModel;

/**
 * VR00022: Verwerkingsregel van de uitsluiting kiesrecht groep.
 */
public class UitsluitingKiesrechtGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param persoonBericht het persoon bericht
     * @param persoonModel het persoon model
     * @param actie de actie
     */
    public UitsluitingKiesrechtGroepVerwerker(final PersoonBericht persoonBericht,
            final PersoonHisVolledigImpl persoonModel, final ActieModel actie)
    {
        super(persoonBericht, persoonModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00022;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonUitsluitingKiesrechtModel hisPersoonUitsluitingKiesrechtModel =
                new HisPersoonUitsluitingKiesrechtModel(getModel(), getBericht().getUitsluitingKiesrecht(), getActie());
        getModel().getPersoonUitsluitingKiesrechtHistorie().voegToe(hisPersoonUitsluitingKiesrechtModel);
    }
}
