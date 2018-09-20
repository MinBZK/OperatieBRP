/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Verwerker voor de standaard groep van huwelijk / geregistreerd partnerschap.
 */
public class FamilierechtelijkeBetrekkingVerwerker extends
        AbstractVerwerkingsregel<FamilierechtelijkeBetrekkingBericht, FamilierechtelijkeBetrekkingHisVolledigImpl>
{
    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public FamilierechtelijkeBetrekkingVerwerker(final FamilierechtelijkeBetrekkingBericht bericht,
                                                 final FamilierechtelijkeBetrekkingHisVolledigImpl model,
                                                 final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR02002;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisRelatieModel hgpGroep = new HisRelatieModel(getModel(), getBericht().getStandaard(), getActie());
        getModel().getRelatieHistorie().voegToe(hgpGroep);
    }
}
