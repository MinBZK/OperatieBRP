/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.huwelijkgeregistreerdpartnerschap.acties.registratieaanvanghuwelijkpartnerschap;

import javax.inject.Named;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;


/**
 * Minimumleeftijd partner (overig).
 *
 * @brp.bedrijfsregel BRBY0402
 */
@Named("BRBY0402")
public class BRBY0402 extends AbstractMinimaleLeeftijdsRegelVerbintenis {

    private static final Integer MINIMALE_LEEFTIJD = 15;

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0402;
    }

    @Override
    protected Integer getMinimaleLeeftijd() {
        return MINIMALE_LEEFTIJD;
    }
}
