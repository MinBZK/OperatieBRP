/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp;

import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpRelatie;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.BepaalGeslachtsnaamComponenten;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.BepaalVervallenOuders;
import nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen.BepaalVoornamen;

/**
 * Sommige gegevens worden afgeleid na de inhoudelijke en historische conversie.
 *
 * Dit zijn de BRP voornamen en geslachtsnaam component stapels, die worden afgeleid aan de hand van de samengestelde
 * naam stapel.
 */
public class Lo3BepalenAfgeleideGegevens {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final BepaalVoornamen bepaalVoornamen;

    private final BepaalGeslachtsnaamComponenten bepaalGeslachtsnaamComponenten;
    private final BepaalVervallenOuders bepaalVervallenOuders;

    /**
     * constructor creates new.
     * @param bepaalVoornamen
     */
    public Lo3BepalenAfgeleideGegevens(final BepaalVoornamen bepaalVoornamen) {
        bepaalGeslachtsnaamComponenten = new BepaalGeslachtsnaamComponenten();
        bepaalVervallenOuders = new BepaalVervallenOuders();
        this.bepaalVoornamen = bepaalVoornamen;
    }

    /**
     * Bepaal de afgeleide gegevens voor een BRP persoonslijst.
     * @param persoonslijst de te verwerken BRP persoonslijst
     * @return de BRP persoonlijst aangevuld met de afgeleide gegevens
     */
    public final BrpPersoonslijst converteer(final BrpPersoonslijst persoonslijst) {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})", persoonslijst.getActueelAdministratienummer());
        final BrpPersoonslijstBuilder builder = new BrpPersoonslijstBuilder(persoonslijst);

        // Geslachtsnaam componenten
        LOG.debug("bepaal geslachtsnaam componenten");
        if (persoonslijst.getSamengesteldeNaamStapel() != null) {
            builder.geslachtsnaamcomponentStapels(bepaalGeslachtsnaamComponenten.bepaal(persoonslijst.getSamengesteldeNaamStapel()));
        }

        // Voornamen
        LOG.debug("bepaal voornamen");
        if (persoonslijst.getSamengesteldeNaamStapel() != null) {
            builder.voornaamStapels(bepaalVoornamen.bepaal(persoonslijst.getSamengesteldeNaamStapel()));
        }

        // Vervallen ouder records in betrokkenheden
        LOG.debug("bepaal vervallen ouder records bij familierechtelijke betrekkingen");
        final List<BrpRelatie> relaties = bepaalVervallenOuders.bepaal(persoonslijst.getRelaties());

        builder.relaties(relaties);

        LOG.debug("build persoonslijst");
        return builder.build();
    }
}
