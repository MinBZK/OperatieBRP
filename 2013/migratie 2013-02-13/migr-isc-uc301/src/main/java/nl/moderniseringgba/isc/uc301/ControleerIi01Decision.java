/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc301;

import java.util.Map;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.lo3.impl.Ii01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringDecision;
import nl.moderniseringgba.isc.migratie.service.GemeenteService;
import nl.moderniseringgba.isc.migratie.service.Stelsel;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Controleer het Ii01-bericht.
 */
@Component("uc301ControleerIi01Decision")
public final class ControleerIi01Decision implements SpringDecision {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT_VERVOLG_PAD_NIET_GBA_GEMEENTE = "2b. fout (nieuwe bijhouder is BRP)";
    private static final String FOUT_VERVOLG_PAD_ZOEKCRITERIA_FOUT = "2c. fout (zoekcriteria voldoen niet aan eisen)";

    @Inject
    private GemeenteService gemeenteService;

    public void setGemeenteService(final GemeenteService gemeenteService) {
        this.gemeenteService = gemeenteService;
    }

    @Override
    public String execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Ii01Bericht ii01Bericht = (Ii01Bericht) parameters.get("input");

        // Controle: nieuwe bijhouder mag niet een BRP gemeente zijn.
        final String controleStelselGemeentes =
                controleerStelselGemeente(ii01Bericht.getBronGemeente(), ii01Bericht.getDoelGemeente());

        final String result;
        if (controleStelselGemeentes != null) {
            result = controleStelselGemeentes;
        } else {
            // Controle: zoekcriteria moeten aan eisen voldoen
            final String controleZoekCriteria = controleerZoekCriteria(ii01Bericht);

            if (controleZoekCriteria != null) {
                result = controleZoekCriteria;
            } else {
                result = null;
            }
        }

        return result;
    }

    /**
     * Controleert of de doelgemeente een BRP gemeente is en de brongemeente een LO3 gemeente.
     * 
     * @param bronGemeente
     *            De brongemeente uit het bericht.
     * @param doelGemeente
     *            De doelgemeente uit het bericht.
     * @return Null indien de doelgemeente een LO3 gemeente is en de brongemeente een BRP gemeente is, de status 2c in
     *         het geval hier niet aan voldaan is.
     */
    private String controleerStelselGemeente(final String bronGemeente, final String doelGemeente) {

        final Stelsel stelselBronGemeente = gemeenteService.geefStelselVoorGemeente(Integer.valueOf(bronGemeente));
        final Stelsel stelselDoelGemeente = gemeenteService.geefStelselVoorGemeente(Integer.valueOf(doelGemeente));

        if (!Stelsel.BRP.equals(stelselDoelGemeente) || !Stelsel.GBA.equals(stelselBronGemeente)) {
            return FOUT_VERVOLG_PAD_NIET_GBA_GEMEENTE;
        }

        return null;
    }

    private String controleerZoekCriteria(final Ii01Bericht ii01Bericht) {
        final String bsn = ii01Bericht.get(Lo3CategorieEnum.PERSOON, Lo3ElementEnum.BURGERSERVICENUMMER);

        if (bsn == null || "".equals(bsn)) {
            return FOUT_VERVOLG_PAD_ZOEKCRITERIA_FOUT;
        }

        return null;
    }

}
