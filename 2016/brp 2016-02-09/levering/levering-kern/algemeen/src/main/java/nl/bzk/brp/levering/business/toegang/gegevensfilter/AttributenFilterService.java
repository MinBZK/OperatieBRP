/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;


/**
 * De interface van de service die de mag geleverd worden vlaggen zet op de attributen.
 */
public interface AttributenFilterService {

    /**
     * De methode die voor een de attributen die getoond mogen worden voor een dienst op 'magGeleverdWorden' zet
     * voor een lijst van persoon his volledig views.
     *
     * @param persoonHisVolledigViews De lijst van persoon his volledig views.
     * @param dienst              Het dienst.
     * @param rol                     De rol van de leveringautorisatie
     * @return De lijst van geraakte attributen.
     * @throws ExpressieExceptie de expressie exceptie
     */
    List<Attribuut> zetMagGeleverdWordenVlaggen(List<PersoonHisVolledigView> persoonHisVolledigViews, Dienst dienst, Rol rol)
            throws ExpressieExceptie;

    /**
     * De methode die voor een de attributen die getoond mogen worden voor een dienst op 'magGeleverdWorden' zet
     * voor een lijst van persoon his volledig views.
     *
     * @param persoonHisVolledigViews De lijst van persoon his volledig views.
     * @param dienst              Het dienst.
     * @param rol                     De rol van de leveringautorisatie
     * @param persoonAttributenMap De mapping van persoon naar key expressie naar attributen
     * @return De lijst van geraakte attributen.
     * @throws ExpressieExceptie de expressie exceptie
     */
    List<Attribuut> zetMagGeleverdWordenVlaggen(List<PersoonHisVolledigView> persoonHisVolledigViews, Dienst dienst, Rol rol,
                                                Map<Integer, Map<String, List<Attribuut>>> persoonAttributenMap) throws ExpressieExceptie;

    /**
     * De methode die voor een de attributen die getoond mogen worden voor een dienst op 'magGeleverdWorden' zet
     * voor een lijst van persoon his volledig views.
     *
     * @param persoonHisVolledigView De persoon his volledig view.
     * @param dienst             Het dienst.
     * @param rol                    de rol van de leveringautorisatie
     * @return De lijst van geraakte attributen.
     * @throws ExpressieExceptie de expressie exceptie
     */
    List<Attribuut> zetMagGeleverdWordenVlaggen(PersoonHisVolledigView persoonHisVolledigView, Dienst dienst, Rol rol)
            throws ExpressieExceptie;

    /**
     * Zet de magGeleverdWorden vlaggen weer naar de originele waarde: false.
     *
     * @param attributen De attributen die ge-reset moeten worden.
     */
    void resetMagGeleverdWordenVlaggen(List<Attribuut> attributen);

}
