/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;

/**
 * Verwerkingsregel van de identificatienummers groep.
 * Verwerkingsregel VR00025.
 */
public class IdentificatienummersGroepVerwerker extends
        AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public IdentificatienummersGroepVerwerker(final PersoonBericht bericht, final PersoonHisVolledigImpl model,
                                              final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00025;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonIdentificatienummersModel identificatienummers = new HisPersoonIdentificatienummersModel(getModel(),
                getBericht().getIdentificatienummers(), getBericht().getIdentificatienummers(), getActie());
        getModel().getPersoonIdentificatienummersHistorie().voegToe(identificatienummers);
    }
}
