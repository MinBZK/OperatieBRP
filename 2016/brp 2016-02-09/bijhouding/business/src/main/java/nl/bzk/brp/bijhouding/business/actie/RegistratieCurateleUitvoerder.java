/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.curatele.CurateleGroepVerwerker;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;


/**
 * Uitvoerder voor indicatie onder curatele.
 */
public final class RegistratieCurateleUitvoerder extends AbstractRegistratieIndicatieUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        final PersoonIndicatieOnderCurateleHisVolledigImpl indicatieHisVolledig;
        if (getPersoonHisVolledig().getIndicatieOnderCuratele() != null) {
            indicatieHisVolledig = getPersoonHisVolledig().getIndicatieOnderCuratele();
        } else {
            indicatieHisVolledig = new PersoonIndicatieOnderCurateleHisVolledigImpl(getPersoonHisVolledig());
            getPersoonHisVolledig().setIndicatieOnderCuratele(indicatieHisVolledig);
        }
        this.voegVerwerkingsregelToe(new CurateleGroepVerwerker(
                getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
    }
}
