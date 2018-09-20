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
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.groep.bericht.PersoonOpschortingGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Betrokkenheid;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenOpschorting;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;
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
     * @param nieuweSituatie  PersoonBericht
     */
    private void verrijkPersoonBerichtMetRelatieVerbrekingen(final Persoon huidigeSituatie,
                                                             final Persoon nieuweSituatie)
    {
        PersoonBericht persoonBericht = (PersoonBericht) nieuweSituatie;

        if (((PersoonModel) huidigeSituatie).heeftBetrokkenheden()) {
            for (Betrokkenheid betrokkenheid : huidigeSituatie.getBetrokkenheden()) {
                RelatieModel relatieModel = (RelatieModel) betrokkenheid.getRelatie();
                if ((relatieModel.getSoort() == SoortRelatie.GEREGISTREERD_PARTNERSCHAP ||
                        relatieModel.getSoort() == SoortRelatie.HUWELIJK) &&
                        relatieModel.getGegevens().getDatumEinde() == null)
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
            ((PersoonBericht) persoon).getOverlijden().setLandOverlijdenCode(BrpConstanten.NL_LAND_CODE);
            ((PersoonBericht) persoon).getOverlijden()
                    .setLandOverlijden(referentieDataRepository.vindLandOpCode(BrpConstanten.NL_LAND_CODE));
        }

        PersoonOpschortingGroepBericht opschorting = new PersoonOpschortingGroepBericht();
        opschorting.setRedenOpschorting(RedenOpschorting.OVERLIJDEN);

        ((PersoonBericht) persoon).setOpschorting(opschorting);
    }


    private BetrokkenheidBericht kopieerModelNaarBericht(final BetrokkenheidModel betrokkenheidModel,
                                                         final PersoonBericht persoonBericht)
    {
        // creër de nieuwe gegevens gebaseerd op de oude gegevens.
        RelatieStandaardGroepBericht relatieStandaardGroepBericht =
                new RelatieStandaardGroepBericht(betrokkenheidModel.getRelatie().getGegevens());

        relatieStandaardGroepBericht.setDatumEinde(persoonBericht.getOverlijden().getDatumOverlijden());
        relatieStandaardGroepBericht.setRedenBeeindigingRelatie(
                referentieDataRepository.vindRedenBeeindigingRelatieOpCode(
                    BrpConstanten.REDEN_BEEINDIGING_RELATIE_OVERLIJDEN_CODE));
        relatieStandaardGroepBericht.setGemeenteEinde(persoonBericht.getOverlijden().getOverlijdenGemeente());
        relatieStandaardGroepBericht.setGemeenteEindeCode(persoonBericht.getOverlijden().getGemeenteOverlijdenCode());
        relatieStandaardGroepBericht.setWoonPlaatsEinde(persoonBericht.getOverlijden().getWoonplaatsOverlijden());
        relatieStandaardGroepBericht
                .setWoonPlaatsEindeCode(persoonBericht.getOverlijden().getWoonplaatsOverlijdenCode());
        relatieStandaardGroepBericht.setLandEinde(persoonBericht.getOverlijden().getLandOverlijden());
        relatieStandaardGroepBericht.setLandEindeCode(persoonBericht.getOverlijden().getLandOverlijdenCode());
        relatieStandaardGroepBericht
                .setBuitenlandsePlaatsEinde(persoonBericht.getOverlijden().getBuitenlandsePlaatsOverlijden());
        relatieStandaardGroepBericht
                .setBuitenlandseRegioEinde(persoonBericht.getOverlijden().getBuitenlandseRegioOverlijden());
        if (null != persoonBericht.getOverlijden().getOmschrijvingLocatieOverlijden()) {
            relatieStandaardGroepBericht.setOmschrijvingLocatieEinde(
                    new Omschrijving(persoonBericht.getOverlijden().getOmschrijvingLocatieOverlijden().getWaarde()));
        }

        // hang de gegevens in de relatie
        RelatieBericht relatieBericht = new RelatieBericht();
        relatieBericht.setSoort(betrokkenheidModel.getRelatie().getSoort());
        relatieBericht.setGegevens(relatieStandaardGroepBericht);
        relatieBericht.setId(betrokkenheidModel.getRelatie().getId());

        // hang de relatie in de betrokkenheden.
        BetrokkenheidBericht betrokkenheidBericht = new BetrokkenheidBericht();
        betrokkenheidBericht.setBetrokkene(persoonBericht);
        betrokkenheidBericht.setRelatie(relatieBericht);
        betrokkenheidBericht.setRol(betrokkenheidModel.getRol());

        return betrokkenheidBericht;
    }
}
