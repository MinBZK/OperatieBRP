/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.naamgebruik.NaamgebruikGroepVerwerker;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * Verwerken Groep Aanschrijving.
 */
public class RegistratieNaamgebruikUitvoerder extends AbstractActieUitvoerder<PersoonBericht, PersoonHisVolledigImpl> {

    @Override
    protected void verzamelVerwerkingsregels() {
        voegVerwerkingsregelToe(new NaamgebruikGroepVerwerker(getBerichtRootObject(), getModelRootObject(), getActieModel()));
    }
}
