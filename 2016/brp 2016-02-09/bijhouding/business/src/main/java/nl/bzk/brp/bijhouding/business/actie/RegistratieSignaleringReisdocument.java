/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.signaleringverstrekkenreisdocument.SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerker;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl;

/**
 * Uitvoerder voor actie registratieBelemmeringVerstrekkingReisdocument.
 */
public final class RegistratieSignaleringReisdocument extends AbstractRegistratieIndicatieUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        final PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl indicatieHisVolledig;
        if (getPersoonHisVolledig().getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument() != null) {
            indicatieHisVolledig = getPersoonHisVolledig().getIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument();
        } else {
            indicatieHisVolledig =
                new PersoonIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocumentHisVolledigImpl(getPersoonHisVolledig());
            getPersoonHisVolledig().setIndicatieSignaleringMetBetrekkingTotVerstrekkenReisdocument(indicatieHisVolledig);
        }

        this.voegVerwerkingsregelToe(new SignaleringMetBetrekkingTotVerstrekkenReisdocumentGroepVerwerker(
                getPersoonIndicatieBericht(), indicatieHisVolledig, getActieModel()));
    }
}
