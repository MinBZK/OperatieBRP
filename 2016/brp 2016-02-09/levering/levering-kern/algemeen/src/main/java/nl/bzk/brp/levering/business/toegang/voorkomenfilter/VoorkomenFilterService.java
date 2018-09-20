/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;

/**
 * Filter voor historie en verantwoording-elementen van groepen.
 */
public interface VoorkomenFilterService {

    /**
     * Zet de mag geleverd worden op basis van de verantwoordingsinfo en/of historie die geleverd mag worden.
     *
     * @param persoonHisVolledigView De persoon view.
     * @param dienst                 De dienst
     * @throws ExpressieExceptie de expressie exceptie
     */
    void voerVoorkomenFilterUit(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst) throws ExpressieExceptie;

    /**
     * Zet de mag geleverd worden op basis van de verantwoordingsinfo en/of historie die geleverd mag worden.
     * <p/>
     * Mutatielevering voorkomenfilter stap pad. Nog te doen: omleggen vanuit bevraging en synchronisatie
     *
     * @param persoonHisVolledigView De persoon view.
     * @param dienst                 De dienst
     * @param expressieAttributenMap de map met key expressie naar attributen
     * @throws ExpressieExceptie de expressie exceptie
     */
    void voerVoorkomenFilterUit(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst, Map<String, List<Attribuut>>
        expressieAttributenMap)
        throws ExpressieExceptie;


    /**
     * Voer het groepen filter uit zonder formele voorkomens eruit te filteren. Dit betekend dat de formele voorkomens altijd in het bericht komen,
     * ongeacht authorisatie.
     *
     * @param persoonHisVolledigView de persoon waarop de filtering moet worden toegepast
     * @param dienst                 de dienst van de afnemer
     * @throws ExpressieExceptie de expressie exceptie
     */
    void voerVoorkomenFilterUitVoorMutatieLevering(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst)
        throws ExpressieExceptie;

    /**
     * Voer het groepen filter uit zonder formele voorkomens eruit te filteren. Dit betekend dat de formele voorkomens altijd in het bericht komen,
     * ongeacht authorisatie.
     * <p/>
     * Mutatielevering voorkomenfilter stap pad. Nog te doen: omleggen vanuit bevraging en synchronisatie
     *
     * @param persoonHisVolledigView de persoon waarop de filtering moet worden toegepast
     * @param dienst                 de dienst van de afnemer
     * @param expressieAttributenMap de map met key expressie naar attributen
     * @throws ExpressieExceptie de expressie exceptie
     */
    void voerVoorkomenFilterUitVoorMutatieLevering(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst, Map<String,
        List<Attribuut>> expressieAttributenMap) throws ExpressieExceptie;
}
