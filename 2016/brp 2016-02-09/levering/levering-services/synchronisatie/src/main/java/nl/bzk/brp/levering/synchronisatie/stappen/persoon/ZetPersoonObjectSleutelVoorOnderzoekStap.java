/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import javax.inject.Inject;
import nl.bzk.brp.levering.algemeen.service.StamTabelService;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GegevenInOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.GegevenInOnderzoekHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.OnderzoekHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtVerwerkingStap;

/**
 * Deze stap versleutelt de persoon id object sleutels als persoon in onderzoek is gezet.
 */
public class ZetPersoonObjectSleutelVoorOnderzoekStap extends AbstractBerichtVerwerkingStap<GeefSynchronisatiePersoonBericht,
    SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    @Inject
    private ObjectSleutelService objectSleutelService;

    @Inject
    private StamTabelService stamTabelService;

    @Override
    @Regels(beschrijving = "R1567")
    public final boolean voerStapUit(final GeefSynchronisatiePersoonBericht onderwerp, final SynchronisatieBerichtContext context,
        final SynchronisatieResultaat resultaat)
    {
        //de partij voor de object sleutel generatie
        final Partij partij = context.getLeveringinformatie().getToegangLeveringsautorisatie().getGeautoriseerde().getPartij();
        for (final PersoonHisVolledigView bijgehoudenPersoon : context.getVolledigBericht().getAdministratieveHandeling().getBijgehoudenPersonen()) {
            zetObjectSleutel(partij, bijgehoudenPersoon);
            for (final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView : bijgehoudenPersoon.getBetrokkenheden()) {
                final RelatieHisVolledig relatie = betrokkenheidHisVolledigView.getRelatie();
                loopDoorBetrokkenheden(partij, bijgehoudenPersoon, relatie);
            }
        }
        return DOORGAAN;
    }

    private void loopDoorBetrokkenheden(final Partij partij, final PersoonHisVolledigView bijgehoudenPersoon, final RelatieHisVolledig relatie) {
        if (((RelatieHisVolledigView) relatie).isZichtbaar()) {
            for (final BetrokkenheidHisVolledig betrokkenheidHisVolledig : relatie.getBetrokkenheden()) {
                if (((BetrokkenheidHisVolledigView) betrokkenheidHisVolledig).isZichtbaar()) {
                    final PersoonHisVolledigView persoon = (PersoonHisVolledigView) betrokkenheidHisVolledig.getPersoon();
                    zetObjectSleutel(partij, persoon);
                }
            }
        }
    }

    private void zetObjectSleutel(final Partij partij, final PersoonHisVolledigView bijgehoudenPersoon) {
        if (bijgehoudenPersoon != null && !bijgehoudenPersoon.getOnderzoeken().isEmpty()) {
            String objectSleutel = null;
            for (final PersoonOnderzoekHisVolledig persoonOnderzoekHisVolledig : bijgehoudenPersoon.getOnderzoeken()) {
                final OnderzoekHisVolledigView onderzoekHisVolledigView = (OnderzoekHisVolledigView) persoonOnderzoekHisVolledig.getOnderzoek();
                for (final GegevenInOnderzoekHisVolledig gegevenInOnderzoekHisVolledig : onderzoekHisVolledigView.getGegevensInOnderzoek()) {
                    final GegevenInOnderzoekHisVolledigView gegevenInOnderzoekHisVolledigView = (GegevenInOnderzoekHisVolledigView)
                        gegevenInOnderzoekHisVolledig;
                    final Element elementUitStamtabel = stamTabelService
                        .geefElementById(gegevenInOnderzoekHisVolledigView.getElement().getWaarde().getID());
                    if (elementUitStamtabel.getElementNaam().getWaarde().equals(ElementEnum.PERSOON.getNaam())) {
                        if (objectSleutel == null) {
                            objectSleutel =
                                objectSleutelService.genereerObjectSleutelString(bijgehoudenPersoon.getID(), partij.getCode().getWaarde());
                        }
                        gegevenInOnderzoekHisVolledigView.setEncryptedObjectSleutelGegeven(objectSleutel);
                        bijgehoudenPersoon.setObjectSleutel(objectSleutel);
                    }
                }
            }
        }
    }
}
