/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.overlijden.OverlijdenGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Actie uitvoerder voor registratie overlijden.
 */
public final class RegistratieOverlijdenUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledigImpl> {

    @Override
    protected void verzamelVerwerkingsregels() {
        this.voegVerwerkingsregelToe(new OverlijdenGroepVerwerker(getBerichtRootObject(), getModelRootObject(), getActieModel()));
    }

}
