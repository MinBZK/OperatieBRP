/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.verbintenis;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkGeregistreerdPartnerschapHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisRelatieModel;

/**
 * Verwerker voor de standaard groep van huwelijk / geregistreerd partnerschap.
 */
public class HuwelijkGeregistreerdPartnerschapVerwerker extends
        AbstractVerwerkingsregel<HuwelijkGeregistreerdPartnerschapBericht, HuwelijkGeregistreerdPartnerschapHisVolledigImpl>
{
    /**
     * Forwarding constructor.
     *
     * @param bericht het bericht object
     * @param model het model object
     * @param actie de actie
     */
    public HuwelijkGeregistreerdPartnerschapVerwerker(final HuwelijkGeregistreerdPartnerschapBericht bericht,
            final HuwelijkGeregistreerdPartnerschapHisVolledigImpl model,
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
        final HisRelatieModel hgpGroep;

        if (this.getActie().getSoort().getWaarde() == SoortActie.REGISTRATIE_EINDE_HUWELIJK_GEREGISTREERD_PARTNERSCHAP) {
            hgpGroep = maakHgpModelVoorBeeindiging();
        } else {
            // Neem de groep over zoals in het bericht aanwezig.
            hgpGroep = new HisRelatieModel(getModel(), getBericht().getStandaard(), getActie());
        }

        getModel().getRelatieHistorie().voegToe(hgpGroep);
    }

    /**
     * Maakt een His Huwelijk Geregistreerd Partnerschap Model voor registratie einde huwelijk / geregistreerd
     * partnerschap.
     *
     * @return his huwelijk geregistreerd partnerschap model
     */
    private HisRelatieModel maakHgpModelVoorBeeindiging() {
        final HisRelatieModel actueleHGP = getModel().getRelatieHistorie().getActueleRecord();
        final RelatieStandaardGroepBericht berichtHGP = getBericht().getStandaard();

        return new HisRelatieModel(getModel(),
                             // Aanvang info van meest recente record.
                             actueleHGP.getDatumAanvang(),
                             actueleHGP.getGemeenteAanvang(), actueleHGP.getWoonplaatsnaamAanvang(),
                             actueleHGP.getBuitenlandsePlaatsAanvang(), actueleHGP.getBuitenlandseRegioAanvang(),
                             actueleHGP.getOmschrijvingLocatieAanvang(), actueleHGP.getLandGebiedAanvang(),
                             // Einde info van bericht.
                             berichtHGP.getRedenEinde(), berichtHGP.getDatumEinde(),
                             berichtHGP.getGemeenteEinde(), berichtHGP.getWoonplaatsnaamEinde(),
                             berichtHGP.getBuitenlandsePlaatsEinde(), berichtHGP.getBuitenlandseRegioEinde(),
                             berichtHGP.getOmschrijvingLocatieEinde(), berichtHGP.getLandGebiedEinde(), getActie());
    }

    @Override
    public final void verzamelAfleidingsregels() {
        final SoortAdministratieveHandeling soortAdministratieveHandeling = getActie().getAdministratieveHandeling().getSoort().getWaarde();
        if (SoortAdministratieveHandeling.OMZETTING_GEREGISTREERD_PARTNERSCHAP_IN_HUWELIJK == soortAdministratieveHandeling) {
            voegAfleidingsregelToe(new OmzettingPartnerschapInHuwelijkAfleiding(getModel(), getActie()));
        }
    }

}
