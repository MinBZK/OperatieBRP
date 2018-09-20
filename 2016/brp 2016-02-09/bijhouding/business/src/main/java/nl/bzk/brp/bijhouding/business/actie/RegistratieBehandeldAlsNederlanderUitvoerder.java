/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander.BehandeldAlsNederlanderGroepVerwerker;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl;

/**
 * Uitvoerder voor registratie behandeld als Nederlander.
 */
public final class RegistratieBehandeldAlsNederlanderUitvoerder extends AbstractRegistratieIndicatieUitvoerder
{

    @Override
    protected void verzamelVerwerkingsregels() {
        final PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl indicatieHisVolledig;
        if (getPersoonHisVolledig().getIndicatieBehandeldAlsNederlander() != null) {
            indicatieHisVolledig = getPersoonHisVolledig().getIndicatieBehandeldAlsNederlander();
        } else {
            indicatieHisVolledig = new PersoonIndicatieBehandeldAlsNederlanderHisVolledigImpl(getPersoonHisVolledig());
            getPersoonHisVolledig().setIndicatieBehandeldAlsNederlander(indicatieHisVolledig);
        }
        this.voegVerwerkingsregelToe(new BehandeldAlsNederlanderGroepVerwerker(
                getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
    }

}
