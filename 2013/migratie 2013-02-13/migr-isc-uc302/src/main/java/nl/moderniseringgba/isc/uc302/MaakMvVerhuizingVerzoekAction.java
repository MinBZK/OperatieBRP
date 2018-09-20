/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc302;

import java.util.HashMap;
import java.util.Map;

import nl.moderniseringgba.isc.esb.message.brp.impl.MvVerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.brp.impl.VerhuizingVerzoekBericht;
import nl.moderniseringgba.isc.jbpm.spring.SpringAction;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Maak een deblokkering bericht (obv blokkeringBericht).
 */
@Component("uc302MaakMvVerhuizingVerzoekAction")
public final class MaakMvVerhuizingVerzoekAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);
        final VerhuizingVerzoekBericht input = (VerhuizingVerzoekBericht) parameters.get("input");

        final MvVerhuizingVerzoekBericht mvVerhuizingVerzoek = new MvVerhuizingVerzoekBericht();
        mvVerhuizingVerzoek.setAfzender(input.getAfzender());
        mvVerhuizingVerzoek.setANummer(input.getANummer());
        mvVerhuizingVerzoek.setBsn(input.getBsn());
        mvVerhuizingVerzoek.setHuidigeGemeente(input.getHuidigeGemeente());
        mvVerhuizingVerzoek.setNieuweGemeente(input.getNieuweGemeente());
        mvVerhuizingVerzoek.setDatumInschrijving(input.getDatumInschrijving());
        mvVerhuizingVerzoek.setAdresseerbaarObject(input.getAdresseerbaarObject());
        mvVerhuizingVerzoek.setIdNummerAanduiding(input.getIdNummerAanduiding());
        mvVerhuizingVerzoek.setNaamOpenbareRuimte(input.getNaamOpenbareRuimte());
        mvVerhuizingVerzoek.setAfgekorteNaamOpenbareRuimte(input.getAfgekorteNaamOpenbareRuimte());
        mvVerhuizingVerzoek.setHuisnummer(input.getHuisnummer());
        mvVerhuizingVerzoek.setHuisletter(input.getHuisletter());
        mvVerhuizingVerzoek.setHuisnummerToevoeging(input.getHuisnummerToevoeging());
        mvVerhuizingVerzoek.setPostcode(input.getPostcode());
        mvVerhuizingVerzoek.setWoonplaats(input.getWoonplaats());
        mvVerhuizingVerzoek.setLocatieTovAdres(input.getLocatieTovAdres());
        mvVerhuizingVerzoek.setLocatieOmschrijving(input.getLocatieOmschrijving());

        mvVerhuizingVerzoek.setLo3Gemeente(input.getLo3Gemeente());
        mvVerhuizingVerzoek.setBrpGemeente(input.getBrpGemeente());

        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("mvVerhuizingVerzoek", mvVerhuizingVerzoek);
        return result;
    }
}
