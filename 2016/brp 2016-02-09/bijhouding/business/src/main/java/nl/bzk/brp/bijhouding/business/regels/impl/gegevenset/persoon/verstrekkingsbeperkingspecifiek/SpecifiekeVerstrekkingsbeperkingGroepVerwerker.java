/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonVerstrekkingsbeperkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVerstrekkingsbeperkingModel;


/**
 * VR00034.
 * Verwerkingsregel voor de specifieke verstrekkingsbeperking.
 *
 * @brp.bedrijfsregel VR00034
 */
public class SpecifiekeVerstrekkingsbeperkingGroepVerwerker extends
        AbstractVerwerkingsregel<PersoonVerstrekkingsbeperkingBericht, PersoonVerstrekkingsbeperkingHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public SpecifiekeVerstrekkingsbeperkingGroepVerwerker(
            final PersoonVerstrekkingsbeperkingBericht bericht,
            final PersoonVerstrekkingsbeperkingHisVolledigImpl model, final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00034;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final PersoonVerstrekkingsbeperkingHisVolledigImpl persoonVerstrekkingsbeperkingHisVolledigImpl = getModel();

        final HisPersoonVerstrekkingsbeperkingModel hisVerstrekkingsbeperking =
            new HisPersoonVerstrekkingsbeperkingModel(persoonVerstrekkingsbeperkingHisVolledigImpl,
                    getActie());

        persoonVerstrekkingsbeperkingHisVolledigImpl.getPersoonVerstrekkingsbeperkingHistorie()
                .voegToe(hisVerstrekkingsbeperking);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        // Een specifieke verstrekkingsbeperking is reden om een volledige verstrekkingsbeperkingen te beeindigen.
        this.voegAfleidingsregelToe(new BeeindigingVolledigeVerstrekkingsbeperkingAfleiding(
                getModel().getPersoon(), getActie()));
    }

}
