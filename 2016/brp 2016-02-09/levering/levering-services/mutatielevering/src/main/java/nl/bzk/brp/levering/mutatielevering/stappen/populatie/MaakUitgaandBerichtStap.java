/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.stappen.populatie.AbstractAfnemerVerwerkingStap;
import nl.bzk.brp.levering.mutatielevering.excepties.StapExceptie;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.SynchronisatieBericht;

import org.jibx.runtime.JiBXException;
import org.perf4j.aop.Profiled;
import org.springframework.beans.factory.annotation.Qualifier;


/**
 * Deze klasse creeert het uitgaande (max) bericht dat naar de afnemers gaat.
 */
public class MaakUitgaandBerichtStap extends AbstractAfnemerVerwerkingStap {

    /**
     * De Constante LOGGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    @Qualifier("levMarshallService")
    private MarshallService     marshallService;

    @Override
    @Profiled(tag = "MaakUitgaandBerichtStap", logFailuresSeparately = true, level = "DEBUG")
    public final boolean voerStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        context.setUitgaandePlatteTekstBerichten(new ArrayList<String>());

        final List<SynchronisatieBericht> leveringBerichten = context.getLeveringBerichten();
        for (final SynchronisatieBericht leveringBericht : leveringBerichten) {
            final List<Integer> teLeverenPersoonsIds = getTeLeverenPersoonIds(leveringBericht);
            leveringBericht.getAdministratieveHandeling().setBijgehoudenPersonenPredikaat(new PersoonIdPredikaat(teLeverenPersoonsIds));

            final String xmlBericht = bouwXmlString(leveringBericht);
            LOGGER.debug("Uitgaand xmlString gebouwd voor administratieve handeling [{}]", context.getAdministratieveHandeling().getID());
            context.getUitgaandePlatteTekstBerichten().add(xmlBericht);
        }
        return DOORGAAN;
    }

    /**
     * Geeft te leveren persoon ids voor type bericht.
     *
     * @param leveringBericht levering bericht
     * @return te leveren persoon ids van het bericht type
     */
    private List<Integer> getTeLeverenPersoonIds(final SynchronisatieBericht leveringBericht) {
        final List<Integer> teLeverenPersoonsIds = new ArrayList<>();
        final List<PersoonHisVolledigView> bijgehoudenPersonen = leveringBericht.getAdministratieveHandeling().getBijgehoudenPersonen();

        for (final PersoonHisVolledigView bijgehoudenPersoon : bijgehoudenPersonen) {
            teLeverenPersoonsIds.add(bijgehoudenPersoon.getID());
        }

        return teLeverenPersoonsIds;
    }

    @Override
    public final void voerNabewerkingStapUit(final LeveringautorisatieStappenOnderwerp onderwerp, final LeveringsautorisatieVerwerkingContext context,
        final LeveringautorisatieVerwerkingResultaat resultaat)
    {
        verwijderBijgehoudenPersonenPredikaatVoorVolgendeIteraties(context);
    }

    /**
     * Verwijdert het predikaat van de berichten, zodat deze niet voor volgende iteraties (leveringsautorisaties) gebruikt kan
     * worden.
     *
     * @param context De stappencontext.
     */
    private void verwijderBijgehoudenPersonenPredikaatVoorVolgendeIteraties(final LeveringsautorisatieVerwerkingContext context) {
        final List<SynchronisatieBericht> leveringBerichten = context.getLeveringBerichten();
        for (final SynchronisatieBericht leveringBericht : leveringBerichten) {
            leveringBericht.getAdministratieveHandeling().setBijgehoudenPersonenPredikaat(null);
        }
    }

    /**
     * Maakt een xml string voor een kennisgevingBericht bericht.
     *
     * @param leveringBericht Het bericht.
     * @return De xml als string.
     */
    private String bouwXmlString(final SynchronisatieBericht leveringBericht) {
        try {
            return marshallService.maakBericht(leveringBericht);
        } catch (final JiBXException e) {
            throw new StapExceptie(e);
        }
    }
}
