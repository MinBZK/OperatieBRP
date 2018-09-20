/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.bedrijfsregels.impl.afstamming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.ActieBedrijfsRegel;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.RelatieRepository;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Actie;
import nl.bzk.brp.model.objecttype.logisch.Relatie;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Soortmelding;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ouders mogen niet aan elkaar verwant zijn. Verwantschap gedefinieërd in BRBY0001
 *
 * @brp.bedrijfsregel BRBY0134
 */
public class BRBY0134 implements ActieBedrijfsRegel<Relatie> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BRBY0134.class);

    @Inject
    PersoonRepository persoonRepository;

    @Inject
    private RelatieRepository relatieRepository;

    @Override
    public String getCode() {
        return "BRBY0134";
    }

    @Override
    public List<Melding> executeer(final Relatie huidigeSituatie, final Relatie nieuweSituatie, final Actie actie) {
        List<Melding> meldingen = new ArrayList<Melding>();

        Set<BetrokkenheidBericht> ouderBetrokkenheden = ((RelatieBericht) nieuweSituatie).getOuderBetrokkenheden();
        // per (XSD) definitie, kind moet 1 of 2 ouders hebben, alleen als er twee ouders zijn kan er verwantschap zijn
        if (ouderBetrokkenheden.size() == 2) {
            // converteer naar gesorteerd list.
            List<BetrokkenheidBericht> gesorteerdeOuderBerichten = new ArrayList<BetrokkenheidBericht>(2);
            Iterator<BetrokkenheidBericht> it = ouderBetrokkenheden.iterator();
            BetrokkenheidBericht ob1 = it.next();
            BetrokkenheidBericht ob2 = it.next();
            gesorteerdeOuderBerichten.add(ob1);
            if (ob1.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().getWaarde()
                .compareTo(ob2.getBetrokkene().getIdentificatienummers().getBurgerservicenummer().getWaarde()) < 0)
            {
                gesorteerdeOuderBerichten.add(ob2);
            } else {
                gesorteerdeOuderBerichten.add(0, ob2);
            }

            List<PersoonModel> personen = new ArrayList<PersoonModel>(2);
            for (BetrokkenheidBericht betrokkenheidBericht : ouderBetrokkenheden) {
                final PersoonModel persoonModel = persoonRepository.findByBurgerservicenummer(
                        betrokkenheidBericht.getBetrokkene().getIdentificatienummers()
                                .getBurgerservicenummer());
                if (persoonModel == null) {
                    LOGGER.error("Onbekend burgerservicenummer ",
                                 betrokkenheidBericht.getBetrokkene().getIdentificatienummers()
                                         .getBurgerservicenummer());
                } else {
                    personen.add(persoonModel);
                }
            }

            if (personen.size() == 2 &&
                    relatieRepository.isVerwant(personen.get(0).getId(), personen.get(1).getId()))
            {
                meldingen.add(new Melding(Soortmelding.OVERRULEBAAR, MeldingCode.BRBY0134,
                                          gesorteerdeOuderBerichten.get(0), "ouder"));
            }
        }
        return meldingen;
    }
}

