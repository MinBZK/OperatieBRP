/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingvolledig;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.AbstractPersoonIndicatieGroepVerwerker;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel;

/**
 * VR00021.
 * Verwerkingsregel voor de volledige verstrekkingsbeperkingvolledig indicatie.
 *
 * @brp.bedrijfsregel VR00021
 */
public class VolledigeVerstrekkingsbeperkingGroepVerwerker extends AbstractPersoonIndicatieGroepVerwerker<
        HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel,
        PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl>
{

    /**
     * Forwarding constructor.
     *
     * @param persoonIndicatieBericht the persoon indicatie bericht
     * @param persoonIndicatieModel   the persoon indicatie model
     * @param actie                   de actie
     */
    public VolledigeVerstrekkingsbeperkingGroepVerwerker(
            final PersoonIndicatieBericht persoonIndicatieBericht,
            final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledigImpl persoonIndicatieModel,
            final ActieModel actie)
    {
        super(persoonIndicatieBericht, persoonIndicatieModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00021;
    }

    @Override
    protected final HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel maakHisRecord() {
        return new HisPersoonIndicatieVolledigeVerstrekkingsbeperkingModel(getModel(),
                getBericht().getStandaard(), getBericht().getStandaard(), getActie());
    }

    @Override
    public final void verzamelAfleidingsregels() {
        // Een volledige verstrekkingsbeperking is reden om alle specifieke verstrekkingsbeperkingen te beeindigen.
        this.voegAfleidingsregelToe(new BeeindigingSpecifiekeVerstrekkingsbeperkingenAfleiding(
                getModel().getPersoon(), getActie()));
    }

}
