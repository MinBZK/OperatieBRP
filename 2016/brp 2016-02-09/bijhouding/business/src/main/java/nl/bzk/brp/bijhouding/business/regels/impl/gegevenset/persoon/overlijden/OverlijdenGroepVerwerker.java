/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorOverlijden;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis.BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkGeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.util.PersoonHisVolledigUtil;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonOverlijdenModel;

/**
 * VR00012: Verwerkingsregel van de overlijden groep.
 */
public class OverlijdenGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl> {

    /**
     * Forwarding constructor.
     *
     * @param persoonBericht het persoon bericht
     * @param persoonModel het persoon model
     * @param actie de actie
     */
    public OverlijdenGroepVerwerker(final PersoonBericht persoonBericht, final PersoonHisVolledigImpl persoonModel,
            final ActieModel actie)
    {
        super(persoonBericht, persoonModel, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00012;
    }

    @Override
    public final void verrijkBericht() {
        // Als in de registratie van een Overlijden het attribuut Land/gebied ontbreekt, dan wordt de waarde "Nederland"
        // toegekend.
        if (getBericht().getOverlijden().getLandGebiedOverlijden() == null) {
            getBericht().getOverlijden().setLandGebiedOverlijden(new LandGebiedAttribuut(getReferentieDataRepository().getNederland()));
        }
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonOverlijdenModel hisPersoonOverlijdenModel = new HisPersoonOverlijdenModel(getModel(),
                getBericht().getOverlijden(), getActie());
        getModel().getPersoonOverlijdenHistorie().voegToe(hisPersoonOverlijdenModel);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        final PersoonHisVolledig persoonModel = getModel();
        // VR00015a: Bij registratie van een Overlijden, wordt de groep Bijhouding van de overleden Persoon,
        // afgeleid geactualiseerd.
        this.voegAfleidingsregelToe(new BijhoudingAfleidingDoorOverlijden(persoonModel, getActie()));

        // VR02002a: Beeindigen actuele huwelijken / geregistreerd partnerschappen.
        // (zouden er meerdere kunnen zijn ivm buitenlandse wetgeving)
        final List<HuwelijkGeregistreerdPartnerschapHisVolledig> actueleHGPs =
                PersoonHisVolledigUtil.getActueleHGPs(persoonModel);
        for (final HuwelijkGeregistreerdPartnerschapHisVolledig actueelHGP : actueleHGPs) {
            final HisPersoonOverlijdenModel overlijden = persoonModel.getPersoonOverlijdenHistorie().getActueleRecord();
            this.voegAfleidingsregelToe(new BeeindigHuwelijkGeregistreerdPartnerschapDoorOverlijden(
                    actueelHGP, getActie(), overlijden));
        }
    }

}
