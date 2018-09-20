/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBijhoudingsverantwoordelijkheidInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Sommige gegevens worden in de materiele historie opgeslagen, maar worden in de gegevens set als inhoud behandeld.
 */
@Component
public class BrpBepalenGegevensSet {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Converteer.
     * 
     * @param persoonslijst
     *            persoonslijst
     * @return geconverteerde persoonslijst
     */
    public final BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        // Geboorte in relaties
        // Relatie groepn in relaties
        builder.relaties(verwerkRelaties(persoonslijst.getRelaties()));

        // Opschorting
        builder.opschortingStapel(verwerkOpschortingStapel(persoonslijst.getOpschortingStapel()));

        // Opschorting
        builder.bijhoudingsverantwoordelijkheidStapel(verwerkBijhoudingsverantwoordelijkheidStapel(persoonslijst
                .getBijhoudingsverantwoordelijkheidStapel()));

        LOG.debug("build persoonslijst");
        return builder.build();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> verwerkBijhoudingsverantwoordelijkheidStapel(
            final BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }

        final List<BrpGroep<BrpBijhoudingsverantwoordelijkheidInhoud>> groepen =
                new ArrayList<BrpGroep<BrpBijhoudingsverantwoordelijkheidInhoud>>();

        for (final BrpGroep<BrpBijhoudingsverantwoordelijkheidInhoud> groep : stapel.getGroepen()) {
            final BrpBijhoudingsverantwoordelijkheidInhoud inhoud =
                    new BrpBijhoudingsverantwoordelijkheidInhoud(groep.getInhoud().getVerantwoordelijkeCode(), groep
                            .getHistorie().getDatumAanvangGeldigheid());
            groepen.add(new BrpGroep<BrpBijhoudingsverantwoordelijkheidInhoud>(inhoud, groep.getHistorie(), groep
                    .getActieInhoud(), groep.getActieVerval(), groep.getActieGeldigheid()));
        }

        return new BrpStapel<BrpBijhoudingsverantwoordelijkheidInhoud>(groepen);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private BrpStapel<BrpOpschortingInhoud> verwerkOpschortingStapel(final BrpStapel<BrpOpschortingInhoud> stapel) {
        if (stapel == null || stapel.isEmpty()) {
            return stapel;
        }

        final List<BrpGroep<BrpOpschortingInhoud>> groepen = new ArrayList<BrpGroep<BrpOpschortingInhoud>>();

        for (final BrpGroep<BrpOpschortingInhoud> groep : stapel.getGroepen()) {
            final BrpOpschortingInhoud inhoud =
                    new BrpOpschortingInhoud(groep.getHistorie().getDatumAanvangGeldigheid(), groep.getInhoud()
                            .getRedenOpschortingBijhoudingCode());
            groepen.add(new BrpGroep<BrpOpschortingInhoud>(inhoud, groep.getHistorie(), groep.getActieInhoud(), groep
                    .getActieVerval(), groep.getActieGeldigheid()));
        }

        return new BrpStapel<BrpOpschortingInhoud>(groepen);
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private static List<BrpRelatie> verwerkRelaties(final List<BrpRelatie> relaties) {
        if (relaties == null) {
            return null;
        }

        final List<BrpRelatie> result = new ArrayList<BrpRelatie>();
        for (final BrpRelatie relatie : relaties) {
            result.add(verwerkRelatie(relatie));
        }

        return result;
    }

    private static BrpRelatie verwerkRelatie(final BrpRelatie relatie) {
        final List<BrpBetrokkenheid> verwerkteBetrokkenheden = new ArrayList<BrpBetrokkenheid>();
        for (final BrpBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
            verwerkteBetrokkenheden.add(verwerkBetrokkenheid(betrokkenheid));
        }

        return new BrpRelatie(relatie.getSoortRelatieCode(), relatie.getRolCode(), verwerkteBetrokkenheden,
                relatie.getRelatieStapel());
    }

    private static BrpBetrokkenheid verwerkBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {

        return new BrpBetrokkenheid(betrokkenheid.getRol(), betrokkenheid.getIdentificatienummersStapel(),
                betrokkenheid.getGeslachtsaanduidingStapel(), betrokkenheid.getGeboorteStapel(),
                betrokkenheid.getOuderlijkGezagStapel(), betrokkenheid.getSamengesteldeNaamStapel(),
                verwerkOuderStapel(betrokkenheid.getOuderStapel()));
    }

    private static BrpStapel<BrpOuderInhoud> verwerkOuderStapel(final BrpStapel<BrpOuderInhoud> ouderStapel) {
        if (ouderStapel == null) {
            return null;
        }

        final List<BrpGroep<BrpOuderInhoud>> result = new ArrayList<BrpGroep<BrpOuderInhoud>>();

        for (final BrpGroep<BrpOuderInhoud> groep : ouderStapel) {
            final BrpOuderInhoud inhoud =
                    new BrpOuderInhoud(groep.getInhoud().getHeeftIndicatie(), groep.getHistorie()
                            .getDatumAanvangGeldigheid());

            result.add(new BrpGroep<BrpOuderInhoud>(inhoud, groep.getHistorie(), groep.getActieInhoud(), groep
                    .getActieVerval(), groep.getActieGeldigheid()));
        }

        return new BrpStapel<BrpOuderInhoud>(result);

    }
}
