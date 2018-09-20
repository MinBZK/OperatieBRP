/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.AdresGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;

/**
 * Uitvoerder voor correctie adres.
 */
public class CorrectieAdresUitvoerder extends AbstractActieUitvoerder<PersoonBericht,
        PersoonHisVolledig>
{

    @Override
    protected void verzamelVerwerkingsregels() {
        // Aanname: er is op dit moment maar 1 adres.
        final PersoonAdresBericht adresBericht = getBerichtRootObject().getAdressen().iterator().next();
        final PersoonAdresHisVolledigImpl adresModel =
                (PersoonAdresHisVolledigImpl) getModelRootObject().getAdressen().iterator().next();

        //VR000013
        voegVerwerkingsregelToe(new AdresGroepVerwerker(adresBericht, adresModel, getActieModel()));
    }
}
