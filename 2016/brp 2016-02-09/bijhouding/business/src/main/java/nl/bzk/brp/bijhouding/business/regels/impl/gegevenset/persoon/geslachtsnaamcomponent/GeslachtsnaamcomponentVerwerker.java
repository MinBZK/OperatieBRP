/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamAfleiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;

/**
 * Verwerker voor de standaard groep van Geslachtsnaamcomponent.
 * VR00002.
 */
public class GeslachtsnaamcomponentVerwerker extends AbstractVerwerkingsregel<PersoonGeslachtsnaamcomponentBericht,
        PersoonGeslachtsnaamcomponentHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public GeslachtsnaamcomponentVerwerker(final PersoonGeslachtsnaamcomponentBericht bericht,
                                           final PersoonGeslachtsnaamcomponentHisVolledigImpl model,
                                           final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00002;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonGeslachtsnaamcomponentModel model = new HisPersoonGeslachtsnaamcomponentModel(getModel(),
                getBericht().getStandaard(), getBericht().getStandaard(), getActie());
        getModel().getPersoonGeslachtsnaamcomponentHistorie().voegToe(model);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        this.voegAfleidingsregelToe(new SamengesteldeNaamAfleiding(getModel().getPersoon(), getActie()));
    }
}
