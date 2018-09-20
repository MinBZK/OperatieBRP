/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.expressietaal.expressies.functies;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Representeert de functie PARTNERSCHAPPEN(P). De functie geeft de partnerschappen van persoon P in een lijst terug.
 */
public final class FunctiePARTNERSCHAPPEN extends AbstractRelatieFunctie {
    @Override
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.GEREGISTREERDPARTNERSCHAP;
    }

    @Override
    protected SoortRelatie getSoortRelatie() {
        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
    }
}
