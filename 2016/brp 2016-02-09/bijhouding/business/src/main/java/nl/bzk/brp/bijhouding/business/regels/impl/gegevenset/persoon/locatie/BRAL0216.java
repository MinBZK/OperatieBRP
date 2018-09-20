/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.locatie;

import javax.inject.Named;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandsePlaatsAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BuitenlandseRegioAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;

/**
 * Implementatie van regel:
 * Buitenlandse regio mag alleen gevuld zijn als ook buitenlandse plaats gevuld is.
 *
 * @brp.bedrijfsregel BRAL0216
 */
@Named("BRAL0216")
public class BRAL0216 extends AbstractLocatieRegel {

    @Override
    public final Regel getRegel() {
        return Regel.BRAL0216;
    }

    @Override
    protected boolean voldoetAanRegel(final LandGebiedAttribuut land, final GemeenteAttribuut gemeente,
            final NaamEnumeratiewaardeAttribuut plaats, final BuitenlandsePlaatsAttribuut buitenlandsePlaats,
            final BuitenlandseRegioAttribuut buitenlandseRegio,
            final LocatieomschrijvingAttribuut locatieOmschrijving)
    {
        return buitenlandseRegio == null || buitenlandsePlaats != null;
    }

}
