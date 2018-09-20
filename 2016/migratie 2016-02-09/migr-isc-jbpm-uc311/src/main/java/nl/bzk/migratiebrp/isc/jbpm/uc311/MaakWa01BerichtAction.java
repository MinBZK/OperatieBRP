/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc311;

import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Wa01Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AnummerWijzigingNotificatie;
import nl.bzk.migratiebrp.bericht.model.sync.impl.LeesUitBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3String;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.graph.exe.ExecutionContext;
import org.springframework.stereotype.Component;

/**
 * Maak een Wa01 bericht obv input, gelezen pl en doel gemeente.
 */
@Component("uc311MaakWa01BerichtAction")
public final class MaakWa01BerichtAction implements SpringAction {

    private static final Integer MAX_GBA_HERHALING = 9;
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BerichtenDao berichtenDao;

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final AnummerWijzigingNotificatie input = (AnummerWijzigingNotificatie) berichtenDao.leesBericht((Long) parameters.get("input"));
        final Long leesUitBrpAntwoordBerichtId = (Long) parameters.get("leesUitBrpAntwoordBericht");
        final LeesUitBrpAntwoordBericht leesUitBrpAntwoordBericht = (LeesUitBrpAntwoordBericht) berichtenDao.leesBericht(leesUitBrpAntwoordBerichtId);
        final Lo3Persoonslijst persoonslijst = leesUitBrpAntwoordBericht.getLo3Persoonslijst();
        final Lo3PersoonInhoud persoon = persoonslijst.getPersoonStapel().getLaatsteElement().getInhoud();

        // Maak wa01 Bericht
        final Wa01Bericht wa01Bericht = new Wa01Bericht();
        wa01Bericht.setBronGemeente(input.getBronGemeente());

        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        final String doelGemeente = (String) executionContext.getVariable(BepaalGemeentenAction.DOEL_GEMEENTE);

        wa01Bericht.setDoelGemeente(doelGemeente);

        wa01Bericht.setNieuwANummer(input.getNieuwAnummer());
        wa01Bericht.setOudANummer(input.getOudAnummer());
        wa01Bericht.setDatumGeldigheid(new Lo3Datum(input.getDatumIngangGeldigheid()));

        wa01Bericht.setVoornamen(Lo3String.unwrap(persoon.getVoornamen()));
        wa01Bericht.setAdellijkeTitelPredikaatCode(persoon.getAdellijkeTitelPredikaatCode());
        wa01Bericht.setVoorvoegselGeslachtsnaam(Lo3String.unwrap(persoon.getVoorvoegselGeslachtsnaam()));
        wa01Bericht.setGeslachtsnaam(Lo3String.unwrap(persoon.getGeslachtsnaam()));

        wa01Bericht.setGeboortedatum(persoon.getGeboortedatum());
        wa01Bericht.setGeboorteGemeenteCode(persoon.getGeboorteGemeenteCode());
        wa01Bericht.setGeboorteLandCode(persoon.getGeboorteLandCode());

        // Herhaling
        final Object herhaling = executionContext.getVariable(BepaalGemeentenAction.WA01_HERHALING);
        if (herhaling != null) {
            wa01Bericht.setHeader(Lo3HeaderVeld.HERHALING, getHerhaling(herhaling));

            // Herhaal bericht moet zelfde message id hebben
            final Lo3Bericht orgineel = (Lo3Bericht) berichtenDao.leesBericht((Long) executionContext.getVariable(BepaalGemeentenAction.WA01_BERICHT));
            wa01Bericht.setMessageId(orgineel.getMessageId());
        }

        executionContext.getContextInstance().setVariable(
            BepaalGemeentenAction.WA01_BERICHT,
            berichtenDao.bewaarBericht(wa01Bericht),
            executionContext.getToken());
        return null;
    }

    private String getHerhaling(final Object herhaling) {
        Integer herhalingInteger = Integer.valueOf(herhaling.toString());
        if (herhalingInteger > MAX_GBA_HERHALING) {
            herhalingInteger = MAX_GBA_HERHALING;
            LOG.warn("wa01Herhaling '{}' is groter dat toegestaan in GBA Herhaalteller."
                     + " Maximaal 1 positie. Herhaalteller op het Wa01 bericht zal op {} blijven staan.", herhaling, MAX_GBA_HERHALING);
        }
        return String.valueOf(herhalingInteger);
    }
}
