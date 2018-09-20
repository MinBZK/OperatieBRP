/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc307;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.generated.GeslachtsaanduidingCodeS;
import nl.moderniseringgba.isc.esb.message.brp.impl.ZoekPersoonVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een zoekPersoonBericht om een persoon buiten de bijhoudingsgemeente te zoeken.
 */
@Component("uc307MaakZoekPersoonBuitenGemeenteBerichtAction")
public final class MaakZoekPersoonBuitenGemeenteBerichtAction implements SpringAction {

    /**
     * Naam van het ZoekPersoonBuitenGemeenteBericht.
     */
    public static final String ZOEK_PERSOON_BUITEN_GEMEENTE_BERICHT = "zoekPersoonBuitenGemeenteBericht";

    /**
     * Variabele voor logging.
     */
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Tb01Bericht tb01Bericht = (Tb01Bericht) parameters.get("input");

        final Lo3OuderInhoud moeder = UC307Utils.actueleMoederGegevens(tb01Bericht.getLo3Persoonslijst());

        final ZoekPersoonVerzoekBericht zoekPersoonBericht = new ZoekPersoonVerzoekBericht();
        zoekPersoonBericht.setBijhoudingsGemeente(new BrpGemeenteCode(new BigDecimal(tb01Bericht.getDoelGemeente())));
        if (moeder.getGeboortedatum() != null) {
            zoekPersoonBericht.setGeboorteDatum(moeder.getGeboortedatum().converteerNaarBrpDatum());
        }
        zoekPersoonBericht.setGeslachtsaanduiding(GeslachtsaanduidingCodeS.valueOf(moeder.getGeslachtsaanduiding()
                .getCode()));
        zoekPersoonBericht.setGeslachtsnaam(moeder.getGeslachtsnaam());
        zoekPersoonBericht.setVoornamen(moeder.getVoornamen());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put(ZOEK_PERSOON_BUITEN_GEMEENTE_BERICHT, zoekPersoonBericht);
        return result;
    }

}
