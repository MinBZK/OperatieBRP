/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.acties;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.pocmotor.dal.PersoonDAO;
import nl.bzk.brp.pocmotor.dal.RelatieDAO;
import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.BRPActie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import org.springframework.stereotype.Component;

/**
 * Actie uitvoerder voor het uitvoeren van een eerste inschrijving.
 */
@Component
public class EersteInschrijvingUitvoerder implements ActieUitvoerder {

    @Inject
    private PersoonDAO persoonDAO;

    @Inject
    private RelatieDAO relatieDAO;

    @Override
    public void voerUit(final BRPActie actie, final List<RootObject> huidigeSituatie) {
        final Relatie geboorteRelatie = (Relatie) actie.getNieuweSituatie();

        //Haal kind uit relatie.
        Persoon kind = null;
        for (Betrokkenheid betrokkenheid : geboorteRelatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.KIND == betrokkenheid.getIdentiteit().getRol()) {
                kind = betrokkenheid.getIdentiteit().getBetrokkene();
            }
        }

        //Persisteer kind via DAL laag
        persoonDAO.persisteerNieuwIngeschreve(kind);
        //Haal ouders uit relatie
        relatieDAO.persisteerFamilieRechtelijkeBetrekking(geboorteRelatie);

    }
}
