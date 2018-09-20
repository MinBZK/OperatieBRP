/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import org.springframework.stereotype.Service;

/**
 * Implementatie van de BetrokkenheidMagLeverenBepalerService. brp.bedrijfsregel VR00121
 */
@Regels(Regel.VR00121)
@Service
public class BetrokkenheidMagLeverenBepalerServiceImpl implements BetrokkenheidMagLeverenBepalerService {

    @Inject
    private LegeBerichtBepaler legeBerichtBepaler;

    @Inject
    private ElementEnumBepaler elementEnumBepaler;

    @Override
    public final void bepaalMagLeveren(final PersoonHisVolledigView persoonHisVolledigView,
        final Dienst dienst, final boolean isMutatieLevering)
    {
        final Set<ElementEnum> objectAutorisaties =
            dienst.getDienstbundel().geefGeautoriseerdeGerelateerdeObjectTypen();

        for (final BetrokkenheidHisVolledigView betrokkenheid : persoonHisVolledigView.getBetrokkenheden()) {

            final RelatieHisVolledigView relatie = (RelatieHisVolledigView) betrokkenheid.getRelatie();
            final Set<? extends BetrokkenheidHisVolledig> relatieBetrokkenheden = relatie.getBetrokkenheden();

            // kan optreden bij kind met ontbrekende ouders.
            if (relatieBetrokkenheden.isEmpty()) {
                if (!isMutatieLevering && (objectAutorisaties.contains(ElementEnum.GERELATEERDEOUDER_PERSOON)
                    || objectAutorisaties.contains(ElementEnum.GERELATEERDEOUDER)))
                {
                    relatie.setMagLeveren(true);
                } else {
                    relatie.setMagLeveren(!relatie.getAlleHistorieRecords().isEmpty());
                }
                betrokkenheid.setMagLeveren(relatie.isMagLeveren());
            }

            for (final BetrokkenheidHisVolledig betrokkenPersoonBetrokkenheid : relatieBetrokkenheden) {

                final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheidView =
                    (BetrokkenheidHisVolledigView) betrokkenPersoonBetrokkenheid;
                final PersoonHisVolledigView betrokkenPersoon =
                    (PersoonHisVolledigView) betrokkenPersoonBetrokkenheid.getPersoon();
                if (betrokkenPersoon != null && betrokkenPersoon.getID().equals(persoonHisVolledigView.getID())) {
                    continue;
                }

                if (isMutatieLevering) {
                    zetVlaggenVoorMutatielevering(betrokkenheid, relatie, betrokkenPersoonBetrokkenheidView,
                        betrokkenPersoon);
                } else {
                    zetVlaggenVoorVolledigBericht(objectAutorisaties, betrokkenheid, relatie,
                        betrokkenPersoonBetrokkenheidView, betrokkenPersoon);
                }
            }
        }
    }

    private void zetVlaggenVoorVolledigBericht(final Set<ElementEnum> objectAutorisaties,
        final BetrokkenheidHisVolledigView betrokkenheid, final RelatieHisVolledigView relatie,
        final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheidView,
        final PersoonHisVolledigView betrokkenPersoon)
    {
        // bepaal autorisatie op betrokken persoon
        final ElementEnum betrokkenPersoonObjectType =
            elementEnumBepaler.bepaalBetrokkenPersoonObjectType(betrokkenPersoonBetrokkenheidView);
        final boolean betrokkenPersoonObjectTypeAutorisatie =
            betrokkenPersoonObjectType != null && objectAutorisaties.contains(betrokkenPersoonObjectType);

        if (betrokkenPersoon != null) {
            betrokkenPersoon.setMagLeveren(betrokkenPersoonObjectTypeAutorisatie);
        }

        // bepaal autorisatie op betrokken betrokkenheid
        final ElementEnum betrokkenBetrokkenheidObjectType =
            elementEnumBepaler.bepaalBetrokkenBetrokkenheidObjectType(betrokkenPersoonBetrokkenheidView);
        final boolean betrokkenheidMagGeleverdWorden =
            betrokkenBetrokkenheidObjectType != null && objectAutorisaties.contains(betrokkenBetrokkenheidObjectType);
        betrokkenPersoonBetrokkenheidView.setMagLeveren(betrokkenheidMagGeleverdWorden
            || betrokkenPersoonObjectTypeAutorisatie);

        // bepaal autorisatie op relatie
        if (!relatie.isMagLeveren()) {
            final ElementEnum relatieObjectType = elementEnumBepaler.bepaalRelatieObjectType(relatie);
            final boolean relatieObjectTypeAutorisatie =
                relatieObjectType != null && objectAutorisaties.contains(relatieObjectType);
            relatie.setMagLeveren(betrokkenPersoonBetrokkenheidView.isMagLeveren() || relatieObjectTypeAutorisatie);
        }

        betrokkenheid.setMagLeveren(relatie.isMagLeveren());
    }

    private void zetVlaggenVoorMutatielevering(final BetrokkenheidHisVolledigView betrokkenheid,
        final RelatieHisVolledigView relatie, final BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheidView,
        final PersoonHisVolledigView betrokkenPersoon)
    {
        final boolean betrokkenPersoonHeeftRecords =
            betrokkenPersoon != null && legeBerichtBepaler.magBetrokkenPersoonGeleverdWorden(betrokkenPersoon);
        final boolean betrokkenPersoonBetrokkenheidHeeftRecords =
            !betrokkenPersoonBetrokkenheidView.getAlleHistorieRecords().isEmpty();
        final boolean relatieHeeftRecords = !relatie.getAlleHistorieRecords().isEmpty();
        final boolean betrokkenPersoonMagGeleverdWorden =
            betrokkenPersoonHeeftRecords || betrokkenPersoonBetrokkenheidHeeftRecords || relatieHeeftRecords;
        if (betrokkenPersoon != null) {
            betrokkenPersoon.setMagLeveren(betrokkenPersoonMagGeleverdWorden);
        }
        betrokkenPersoonBetrokkenheidView.setMagLeveren(betrokkenPersoonMagGeleverdWorden);

        if (!relatie.isMagLeveren()) {
            relatie.setMagLeveren(betrokkenPersoonMagGeleverdWorden);
        }

        if (!betrokkenheid.isMagLeveren()) {
            betrokkenheid.setMagLeveren(relatie.isMagLeveren());
        }
    }
}
