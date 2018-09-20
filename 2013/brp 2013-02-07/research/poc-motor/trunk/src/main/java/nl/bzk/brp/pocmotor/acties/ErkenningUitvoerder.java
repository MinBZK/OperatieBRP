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

@Component
public class ErkenningUitvoerder implements ActieUitvoerder {

    @Inject
    private RelatieDAO relatieDAO;

    @Inject
    private PersoonDAO persoonDAO;

    @Override
    public void voerUit(BRPActie actie, List<RootObject> huidigeSituatie) {
        Relatie nieuweSituatie = (Relatie) actie.getNieuweSituatie();
        Persoon vader = null;
        Persoon kind = null;
        for (Betrokkenheid betrokkenheid : nieuweSituatie.getBetrokkenheden()) {
            if (SoortBetrokkenheid.OUDER == betrokkenheid.getIdentiteit().getRol()) {
                vader = betrokkenheid.getIdentiteit().getBetrokkene();
            } else if (SoortBetrokkenheid.KIND == betrokkenheid.getIdentiteit().getRol()) {
                kind = betrokkenheid.getIdentiteit().getBetrokkene();
            }
        }

        //Voeg vader toe aan bestaande relatie met moeder
        relatieDAO.voegVaderToeAanFamilieRechtelijkeBetrekking(vader, kind);

        //Wijzig geslachtsnaam van kind inclusief historie
        persoonDAO.wijzigGelsachtsNaam(kind, actie);
    }
}
