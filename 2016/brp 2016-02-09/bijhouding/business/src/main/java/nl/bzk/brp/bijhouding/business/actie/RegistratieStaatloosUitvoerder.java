/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.StaatloosGroepVerwerker;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieStaatloosHisVolledigImpl;

/**
 * Registratie uitvoerder voor de indicatie staatloos.
 */
public final class RegistratieStaatloosUitvoerder extends AbstractRegistratieIndicatieUitvoerder
{

    @Override
    protected void verzamelVerwerkingsregels() {
        PersoonIndicatieStaatloosHisVolledigImpl indicatieHisVolledig =
                getPersoonHisVolledig().getIndicatieStaatloos();
        if (indicatieHisVolledig == null) {
            indicatieHisVolledig = new PersoonIndicatieStaatloosHisVolledigImpl(getPersoonHisVolledig());
            getPersoonHisVolledig().setIndicatieStaatloos(indicatieHisVolledig);
        }
        this.voegVerwerkingsregelToe(new StaatloosGroepVerwerker(
                getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
    }

}
