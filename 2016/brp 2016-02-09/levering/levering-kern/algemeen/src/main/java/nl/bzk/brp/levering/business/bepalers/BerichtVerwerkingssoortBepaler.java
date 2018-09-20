/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;

/**
 * Deze klasse bepaald de verwerkingsoort voor een kennisgevings bericht.
 */
public interface BerichtVerwerkingssoortBepaler {

    /**
     * Bepaalt de verwerkingssoort voor elementen binnen de kennisgevingberichten.
     *
     * @param formeleOfMaterieleHistorie Een formele of materiele historie element waarvan de verwerkingssoort bepaalt moet worden.
     * @param administratieveHandelingId De id van de administratieve handeling waar het formele of informele historie element onder valt.
     * @return De verwerkingsoort die in het kennisgevingbericht gebruikt zal worden.
     */
    Verwerkingssoort bepaalVerwerkingssoort(HistorieEntiteit formeleOfMaterieleHistorie, Long administratieveHandelingId);

    /**
     * Bepaalt de verwerkingssoort op het persoon element en op de onderliggende afgeleid administratief.
     *
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @param persoonHisVolledigView     De persoon his volledig view.
     * @return De verwerkingssoort.
     */
    Verwerkingssoort bepaalVerwerkingssoortPersoon(Long administratieveHandelingId, PersoonHisVolledigView persoonHisVolledigView);

    /**
     * Bepaalt de verwerkingssoort op het betrokken persoon element en op de onderliggende afgeleid administratief.
     *
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @param persoonHisVolledigView     De persoon his volledig view.
     * @return De verwerkingssoort.
     */
    Verwerkingssoort bepaalVerwerkingssoortBetrokkenPersoon(Long administratieveHandelingId, PersoonHisVolledigView persoonHisVolledigView);

    /**
     * Bepaalt de verwerkingssoort voor een betrokken kind.
     *
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @param persoonHisVolledigView     De persoon his volledig delta view.
     * @return de verwerkingssoort voor deze persoon
     */
    Verwerkingssoort bepaalVerwerkingssoortBetrokkenKind(final Long administratieveHandelingId, PersoonHisVolledigView persoonHisVolledigView);

    /**
     * Bepaalt de verwerkingssoort.
     *
     * @param relatieHisVolledigView     De relatie his volledig view.
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @return de verwerkingssoort voor deze relatie.
     */
    Verwerkingssoort bepaalVerwerkingssoortVoorRelaties(RelatieHisVolledigView relatieHisVolledigView, Long administratieveHandelingId);
    /**
     * Bepaalt de verwerkingssoort.
     *
     * @param betrokkenheidHisVolledigView De betrokkenheid his volledig view.
     * @param administratieveHandelingId   De id van de huidige administratieve handeling.
     * @return de verwerkingssoort voor deze relatie.
     */
    Verwerkingssoort bepaalVerwerkingssoortVoorBetrokkenheden(BetrokkenheidHisVolledigView betrokkenheidHisVolledigView, Long administratieveHandelingId);
}
