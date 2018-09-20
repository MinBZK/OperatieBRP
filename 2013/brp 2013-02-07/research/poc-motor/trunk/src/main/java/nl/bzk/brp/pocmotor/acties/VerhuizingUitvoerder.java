/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.acties;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.VerhuizingDAO;
import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import org.springframework.stereotype.Component;

@Component
public class VerhuizingUitvoerder implements ActieUitvoerder {

    @Inject
    private VerhuizingDAO verhuizingDAO;

    @Override
    public void voerUit(final BRPActie actie, final List<RootObject> huidigeSituatie) {
        final Persoon pers = (Persoon) actie.getNieuweSituatie();
        final PersoonAdres nieuwAdres = pers.getAdressen().iterator().next();

        Persoon huidigePersoon = (Persoon) huidigeSituatie.get(0);

        verhuizingDAO.persisteerVerhuizing(huidigePersoon, nieuwAdres, actie.getDatumAanvangGeldigheid());
    }
}
