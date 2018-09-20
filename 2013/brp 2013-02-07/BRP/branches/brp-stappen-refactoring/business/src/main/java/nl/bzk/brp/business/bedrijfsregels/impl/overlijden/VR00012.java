/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.overlijden;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.constanten.BrpConstanten;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenOpschorting;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.GeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapBericht;
import nl.bzk.brp.model.bericht.kern.HuwelijkGeregistreerdPartnerschapStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PartnerBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.logisch.kern.Actie;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.GeregistreerdPartnerschap;
import nl.bzk.brp.model.logisch.kern.Huwelijk;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.BetrokkenheidModel;
import nl.bzk.brp.model.operationeel.kern.HuwelijkGeregistreerdPartnerschapModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.RelatieModel;
import nl.bzk.brp.model.validatie.Melding;


/**
 * VR00012: Verwerken Groep Overlijden.
 *
 * @brp.bedrijfsregel VR00012
 */
public class VR00012 implements ActieBedrijfsRegel<Persoon> {

    @Inject
    private ReferentieDataRepository referentieDataRepository;

    @Override
    public String getCode() {
        return "VR00012";
    }

    @Override
    public List<Melding> executeer(final Persoon huidigeSituatie, final Persoon nieuweSituatie, final Actie actie) {
        verrijkPersoonBerichtMetOverlijdenInformatie(nieuweSituatie);
        verrijkPersoonBerichtMetRelatieVerbrekingen(huidigeSituatie, nieuweSituatie);
        return new ArrayList<Melding>();
    }

    /**
     * Voegt relatie betrokkenheid veranderingen toe.
     *
     * @param huidigeSituatie PersoonModel
     * @param nieuweSituatie PersoonBericht
     */
    private void verrijkPersoonBerichtMetRelatieVerbrekingen(final Persoon huidigeSituatie,
        final Persoon nieuweSituatie)
    {
        // BOLIE: als neiuwe sitatie is null (op een of ander manier is dit fout gegaan), doe niets !!!.
        //
        if (null != nieuweSituatie) {
            PersoonBericht persoonBericht = (PersoonBericht) nieuweSituatie;

            if (((PersoonModel) huidigeSituatie).heeftBetrokkenheden()) {
                for (Betrokkenheid betrokkenheid : huidigeSituatie.getBetrokkenheden()) {
                    RelatieModel relatieModel = (RelatieModel) betrokkenheid.getRelatie();
                    if ((relatieModel.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP
                        || relatieModel.getSoort() == SoortRelatie.HUWELIJK)
                        && ((HuwelijkGeregistreerdPartnerschapModel) relatieModel).getStandaard().getDatumEinde() == null)
                    {
                        // deze relatie moet veranderd worden, voeg toe aan nieuweSituatie
                        BetrokkenheidBericht betrokkenheidBericht =
                            kopieerModelNaarBericht((BetrokkenheidModel) betrokkenheid,
                                (PersoonBericht) nieuweSituatie);
                        if (persoonBericht.getBetrokkenheden() == null) {
                            persoonBericht.setBetrokkenheden(new ArrayList<BetrokkenheidBericht>());
                        }
                        persoonBericht.getBetrokkenheden().add(betrokkenheidBericht);
                    }
                }
            }

        }
    }

    /**
     * Voert de werkelijke afleiding uit voor opschorting.
     *
     * @param persoon de persoon waarvoor de afleiding geldt.
     */
    private void verrijkPersoonBerichtMetOverlijdenInformatie(final Persoon persoon) {
        // TODO tijdelijk wordt land overlijden altijd op NL gezet zonder te kijken naar Bronnen, het is op dit moment
        // nog niet duidelijk hoe bronnen behandeld moet worden.
        // 2-11-2012: Bevestigd met Jeroen.
        if (persoon.getOverlijden().getLandOverlijden() == null) {
            ((PersoonBericht) persoon).getOverlijden().setLandOverlijdenCode("" + BrpConstanten.NL_LAND_CODE.getWaarde());
            ((PersoonBericht) persoon).getOverlijden()
                                      .setLandOverlijden(
                                          referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        }

        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschortingBijhouding(RedenOpschorting.OVERLIJDEN);

        ((PersoonBericht) persoon).setOpschorting(opschorting);
    }


    /**
     * .
     * @param betrokkenheidModel .
     * @param persoonBericht .
     * @return .
     */
    private BetrokkenheidBericht kopieerModelNaarBericht(final BetrokkenheidModel betrokkenheidModel,
        final PersoonBericht persoonBericht)
    {
        // creër de nieuwe gegevens gebaseerd op de oude gegevens.
        HuwelijkGeregistreerdPartnerschapStandaardGroepBericht relatieStandaardGroepBericht =
            new HuwelijkGeregistreerdPartnerschapStandaardGroepBericht(
                ((HuwelijkGeregistreerdPartnerschapModel) betrokkenheidModel.getRelatie()).getStandaard());

        relatieStandaardGroepBericht.setDatumEinde(persoonBericht.getOverlijden().getDatumOverlijden());
        relatieStandaardGroepBericht.setRedenEinde(
            referentieDataRepository.vindRedenBeeindigingRelatieOpCode(
                BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE));
        relatieStandaardGroepBericht.setGemeenteEinde(persoonBericht.getOverlijden().getGemeenteOverlijden());
        relatieStandaardGroepBericht.setGemeenteEindeCode(persoonBericht.getOverlijden().getGemeenteOverlijdenCode());
        relatieStandaardGroepBericht.setWoonplaatsEinde(persoonBericht.getOverlijden().getWoonplaatsOverlijden());
        relatieStandaardGroepBericht
            .setWoonplaatsEindeCode(persoonBericht.getOverlijden().getWoonplaatsOverlijdenCode());
        relatieStandaardGroepBericht.setLandEinde(persoonBericht.getOverlijden().getLandOverlijden());
        relatieStandaardGroepBericht.setLandEindeCode(persoonBericht.getOverlijden().getLandOverlijdenCode());
        relatieStandaardGroepBericht
            .setBuitenlandsePlaatsEinde(persoonBericht.getOverlijden().getBuitenlandsePlaatsOverlijden());
        relatieStandaardGroepBericht
            .setBuitenlandseRegioEinde(persoonBericht.getOverlijden().getBuitenlandseRegioOverlijden());
        if (null != persoonBericht.getOverlijden().getOmschrijvingLocatieOverlijden()) {
            relatieStandaardGroepBericht.setOmschrijvingLocatieEinde(persoonBericht
                .getOverlijden().getOmschrijvingLocatieOverlijden());
        }

        // hang de gegevens in de relatie
        HuwelijkGeregistreerdPartnerschapBericht relatieBericht = null;
        if (betrokkenheidModel.getRelatie() instanceof Huwelijk) {
            relatieBericht = new HuwelijkBericht();
        } else if (betrokkenheidModel.getRelatie() instanceof GeregistreerdPartnerschap) {
            relatieBericht = new GeregistreerdPartnerschapBericht();
        } else {
            throw new IllegalArgumentException("relatieBerich is van verkeerde type: "
                + betrokkenheidModel.getRelatie().getClass().getName());
        }
        relatieBericht.setStandaard(relatieStandaardGroepBericht);
        relatieBericht.setModelID(betrokkenheidModel.getRelatie().getID());

        // hang de relatie in de betrokkenheden.
        PartnerBericht betrokkenheidBericht = new PartnerBericht();
        betrokkenheidBericht.setPersoon(persoonBericht);
        betrokkenheidBericht.setRelatie(relatieBericht);

        return betrokkenheidBericht;
    }
}
