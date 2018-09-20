/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bevraging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.operationeel.basis.AbstractBetrokkenheidModel;
import nl.bzk.brp.web.AbstractAntwoordBericht;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Abstracte antwoord bericht voor BRP bevragingsberichten. */
public class BevragingAntwoordBericht extends AbstractAntwoordBericht<OpvragenPersoonResultaat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BevragingAntwoordBericht.class);

    private List<PersoonModel> personen = null;

    /**
     * Constructor.
     *
     * @param berichtResultaat Het resultaat uit de back-end.
     */
    public BevragingAntwoordBericht(final OpvragenPersoonResultaat berichtResultaat) {
        super(berichtResultaat);
        if (berichtResultaat.getGevondenPersonen() != null && !berichtResultaat.getGevondenPersonen().isEmpty()) {
            personen = new ArrayList<PersoonModel>();
            for (final PersoonModel gevondenPersoon : berichtResultaat.getGevondenPersonen()) {
                personen.add(gevondenPersoon);
            }
        }

        if (personen != null) {
            for (PersoonModel persoon : personen) {
                bewerkViewOpPersoonVoorBinding(persoon);
            }
        }
    }

    /**
     * Let op, de Persoon die in dit antwoord bericht zit bevat dubbele informatie die ervoor kan zorgen dat de
     * databinding niet goed werkt. De persoon kent mogelijk een partner betrokkenheid, deze partner betrokkenheid kent
     * een relatie met weer 2 betrokkenheden. Van deze 2 betrokkenheden is er een hetzelfde als de directe
     * betrokkenheid op de persoon in dit antwoord bericht. Hier kan de databinding niet tegen. Wat we in deze functie
     * doen is de betrokkenheid die aan de relatie hangt verwijderen. We passen dus de View op de persoon aan voor
     * correcte presentatie in het xml bericht.
     *
     * @param persoon De te bewerken persoon.
     */
    private void bewerkViewOpPersoonVoorBinding(final PersoonModel persoon) {
        if (persoon.getBetrokkenheden() != null) {
            BetrokkenheidModel teVerwijderenBetr = null;
            for (BetrokkenheidModel directeBetrokkenheid : persoon.getPartnerBetrokkenHeden()) {
                RelatieModel relatie = directeBetrokkenheid.getRelatie();
                for (BetrokkenheidModel indirecteBetrokkenheid : relatie.getBetrokkenheden()) {
                    if (indirecteBetrokkenheid.getId() != null
                        && indirecteBetrokkenheid.getId().equals(directeBetrokkenheid.getId()))
                    {
                        teVerwijderenBetr = indirecteBetrokkenheid;
                        continue;
                    }
                }
                if (teVerwijderenBetr != null) {
                    RelatieModel relatieKopie = new RelatieModel(relatie);
                    for (BetrokkenheidModel betrokkenheid : relatie.getBetrokkenheden()) {
                        if (betrokkenheid != teVerwijderenBetr) {
                            relatieKopie.getBetrokkenheden().add(betrokkenheid);
                        }
                    }
                    teVerwijderenBetr = null;

                    // De relatie wordt vervangen door een copy met daarin de aangepaste betrokkenheden (en dus
                    // eventueel een betrokkenheid verwijderd. We kunnen niet de betrokkenheid uit de originele
                    // relatie verwijderen, daar mogelijk naar dezelfde relatie wordt verwezen vanuit een andere
                    // persoon die is gevonden. Bijvoorbeeld als er wordt gezocht op personen op hetzelfde adres,
                    // dan zullen twee getrouwde mensen die op hetzelfde adres wonen beide naar de zelfde relatie
                    // verwijzen. Als we dan voor persoon 1 zijn eigen betrokkenheid uit de relatie halen en dan
                    // voor persoon 2 ook zijn eigen betrokkenheid uit de relatie verwijderen, houden we een lege
                    // relatie over en niet zoals bedoeld twee relaties met in de een de betrokkenheid naar de ander
                    // en in de ander de betrokkenheid naar de een. Vandaar de copy, zodat beide personen naar een
                    // andere relatie verwijzen met ieder een andere betrokkenheid.
                    try {
                        // TODO: Onderstaande zonder reflectie uitvoeren!
                        Field fld = AbstractBetrokkenheidModel.class.getDeclaredField("relatie");
                        fld.setAccessible(true);
                        fld.set(directeBetrokkenheid, relatieKopie);
                    } catch (IllegalAccessException e) {
                        LOGGER.error("Systeem fout bij het zetten van een relatie via reflectie.", e);
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        LOGGER.error("Systeem fout bij het zetten van een relatie via reflectie.", e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public List<PersoonModel> getPersonen() {
        return personen;
    }
}
