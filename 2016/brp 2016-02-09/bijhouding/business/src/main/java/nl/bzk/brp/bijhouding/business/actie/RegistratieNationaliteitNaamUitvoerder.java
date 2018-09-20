/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.actie;

import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;

/**
 * @TODO, er loopt nog een discussie of dit een andere actie is als RegistratieNationaliteit.
 * @TODO, voorlopig een subclass van gemaakt.
 */
public final class RegistratieNationaliteitNaamUitvoerder extends RegistratieNationaliteitUitvoerder {

    @Override
    protected void verzamelVerwerkingsregels() {
        super.verzamelVerwerkingsregels();

        //Geslachtsnaam en voornaam wijzigingen verwerken:
        this.voegVerwerkingsregelsToe(PersoonGroepVerwerkersUtil.bepaalAlleVerwerkingsregels(
                getBerichtRootObject(), (PersoonHisVolledigImpl) getModelRootObject(), getActieModel()));
    }
}
