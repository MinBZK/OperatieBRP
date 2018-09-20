/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder.OuderschapGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.util.RelatieHisVolledigUtil;

/**
 * Actie uitvoerder voor adoptie, erkenning, vaderschap en actualisering afstamming.
 */
public final class ActualiseringAfstammingUitvoerder extends AbstractActieUitvoerder<
    FamilierechtelijkeBetrekkingBericht,
    FamilierechtelijkeBetrekkingHisVolledig>

{
    @Override
    protected void verzamelVerwerkingsregels() {
        verwerkOuders();
        verwerkKind();
    }

    /**
     * Verwerk de informatie voor het nieuwe kind.
     */
    private void verwerkKind() {
        final PersoonBericht kindUitBericht = getBerichtRootObject().getKindBetrokkenheid().getPersoon();
        final PersoonHisVolledigImpl kindHisVolledig = (PersoonHisVolledigImpl)
            getContext().zoekHisVolledigRootObject(kindUitBericht);
        voegVerwerkingsregelsToe(PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(
            kindUitBericht, kindHisVolledig, getActieModel(), null));
    }

    /**
     * Voeg eventuele nieuwe ouders toe aan de familierechtelijke betrekking en verwerk de ouders.
     */
    private void verwerkOuders() {
        final FamilierechtelijkeBetrekkingHisVolledigImpl afstamming =
            (FamilierechtelijkeBetrekkingHisVolledigImpl) getModelRootObject();

        // Voeg de ouders toe als betrokkenheden
        for (final OuderBericht ouderBetrokkenheid : getBerichtRootObject().getOuderBetrokkenheden()) {
            final PersoonHisVolledigImpl persoonHisVolledig = (PersoonHisVolledigImpl)
                getContext().zoekHisVolledigRootObject(ouderBetrokkenheid.getPersoon());

            final OuderHisVolledigImpl ouderHisVolledig;
            if (RelatieHisVolledigUtil.heeftBetrokkenheidNaarRelatie(persoonHisVolledig, afstamming)) {
                ouderHisVolledig = (OuderHisVolledigImpl) RelatieHisVolledigUtil.bepaalBetrokkenheidVanPersoonInRelatie(persoonHisVolledig, afstamming);

            } else {
                ouderHisVolledig = new OuderHisVolledigImpl(afstamming, persoonHisVolledig);
                persoonHisVolledig.getBetrokkenheden().add(ouderHisVolledig);
                afstamming.getBetrokkenheden().add(ouderHisVolledig);
            }

            // Voeg een verwerkingsregel toe voor elke in het bericht aanwezige en dus (opnieuw) geregistreerde
            // ouder betrokkenheid.
            voegVerwerkingsregelToe(
                new OuderschapGroepVerwerker(ouderBetrokkenheid, ouderHisVolledig, getActieModel()));
        }
    }

    @Override
    protected FamilierechtelijkeBetrekkingHisVolledig maakNieuwRootObjectHisVolledig() {
        return new FamilierechtelijkeBetrekkingHisVolledigImpl();
    }
}
