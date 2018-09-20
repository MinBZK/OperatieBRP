/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp;

import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.BepaalGeslachtsnaamComponenten;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.BepaalSluitingen;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.BepaalVervallenOuders;
import nl.moderniseringgba.migratie.conversie.proces.lo3naarbrp.attributen.BepaalVoornamen;
import nl.moderniseringgba.migratie.conversie.validatie.InputValidationException;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Sommige gegevens worden afgeleid na de inhoudelijke en historische conversie.
 * 
 * Dit zijn de BRP voornamen en geslachtsnaam component stapels, die worden afgeleid aan de hand van de samengestelde
 * naam stapel.
 */
@Component
public class Lo3BepalenAfgeleideGegevens {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BepaalGeslachtsnaamComponenten bepaalGeslachtsnaamComponenten;
    @Inject
    private BepaalVoornamen bepaalVoornamen;
    @Inject
    private BepaalSluitingen bepaalSluitingen;
    @Inject
    private BepaalVervallenOuders bepaalVervallenOuders;

    /**
     * Bepaal de afgeleide gegevens voor een BRP persoonslijst.
     * 
     * @param persoonslijst
     *            de te verwerken BRP persoonslijst
     * @return de BRP persoonlijst aangevuld met de afgeleide gegevens
     * @throws InputValidationException
     *             wanneer de nieuwe brp lijst niet gemaakt kon worden
     */
    public final BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) throws InputValidationException {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        // Geslachtsnaam componenten
        LOG.debug("bepaal geslachtsnaam componenten");
        builder.geslachtsnaamcomponentStapels(bepaalGeslachtsnaamComponenten.bepaal(persoonslijst
                .getSamengesteldeNaamStapel()));
        // Voornamen
        LOG.debug("bepaal voornamen");
        builder.voornaamStapels(bepaalVoornamen.bepaal(persoonslijst.getSamengesteldeNaamStapel()));

        // Huwelijkssluitingen registreren bij ontbindingen
        LOG.debug("bepaal huwelijk sluitingen bij ontbindingen");
        List<BrpRelatie> relaties = bepaalSluitingen.bepaal(persoonslijst.getRelaties());

        // Vervallen ouder records in betrokkenheden
        LOG.debug("bepaal vervallen ouder records bij familierechtelijke betrekkingen");
        relaties = bepaalVervallenOuders.bepaal(relaties);

        builder.relaties(relaties);

        LOG.debug("build persoonslijst");
        return builder.build();
    }
}
