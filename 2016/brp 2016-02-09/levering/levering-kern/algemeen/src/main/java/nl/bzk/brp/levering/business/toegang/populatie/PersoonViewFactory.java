/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.populatie;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;

/**
 * De persoon view die views maakt op personen.
 */
public interface PersoonViewFactory {

    /**
     * Maakt een view op een persoon his volledig op basis van de in/uit populatie en administratieve handeling.
     *
     * @param persoonHisVolledig       De persoon his volledig.
     * @param leveringAutorisatie      De leveringAutorisatie.
     * @param populatie                De in/uit populatie.
     * @param administratieveHandeling De administratieve handeling.
     * @return De persoon his volledig view.
     */
    PersoonHisVolledigView maakView(final PersoonHisVolledig persoonHisVolledig,
        final Leveringinformatie leveringAutorisatie, final Populatie populatie,
        final AdministratieveHandelingModel administratieveHandeling);

    /**
     * Maakt een view op een persoon his volledig op basis van de in/uit populatie en administratieve handeling.
     *
     * @param persoonHisVolledig       De persoon his volledig.
     * @return De persoon his volledig view.
     */
    PersoonHisVolledigView maakLegeView(final PersoonHisVolledig persoonHisVolledig);

    /**
     * Maakt een view op een persoon his volledig op basis van de in/uit populatie en administratieve handeling.
     *
     * @param persoonHisVolledigView   De persoon his volledig view.
     * @param leveringAutorisatie      De leveringAutorisatie.
     * @param populatie                De in/uit populatie.
     * @param administratieveHandeling De administratieve handeling.
     * @return De persoon his volledig view.
     */
    PersoonHisVolledigView voegPredikatenToe(PersoonHisVolledigView persoonHisVolledigView,
                                    Leveringinformatie leveringAutorisatie,
                                    Populatie populatie,
                                    AdministratieveHandelingModel administratieveHandeling);

    /**
     * Maakt een materiele historie view op een persoon, tbv. synchronisatie (VolledigBericht).
     *
     * @param persoonHisVolledig       De persoon his volledig
     * @param leveringAutorisatie      de leveringAutorisatie
     * @param administratieveHandeling De administratieve handeling
     * @param peilMoment               het peil moment
     * @return De persoon his volledig view.
     */
    PersoonHisVolledigView maakMaterieleHistorieView(PersoonHisVolledig persoonHisVolledig,
                                                     Leveringinformatie leveringAutorisatie,
                                                     AdministratieveHandelingModel administratieveHandeling,
                                                     DatumAttribuut peilMoment);

}
