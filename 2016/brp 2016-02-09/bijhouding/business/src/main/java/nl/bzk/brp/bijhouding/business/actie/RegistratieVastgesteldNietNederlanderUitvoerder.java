/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander.VastgesteldNietNederlanderGroepVerwerker;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl;

/**
 * Uitvoerder voor indicatie vastgesteld niet nederlander.
 */
public final class RegistratieVastgesteldNietNederlanderUitvoerder extends AbstractRegistratieIndicatieUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        final PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl indicatieHisVolledig;
        if (getPersoonHisVolledig().getIndicatieVastgesteldNietNederlander() != null) {
            indicatieHisVolledig = getPersoonHisVolledig().getIndicatieVastgesteldNietNederlander();
        } else {
            indicatieHisVolledig = new PersoonIndicatieVastgesteldNietNederlanderHisVolledigImpl(
                getPersoonHisVolledig());
            getPersoonHisVolledig().setIndicatieVastgesteldNietNederlander(indicatieHisVolledig);
        }

        this.voegVerwerkingsregelToe(new VastgesteldNietNederlanderGroepVerwerker(
                getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
    }
}
