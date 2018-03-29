/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.pl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import nl.bzk.algemeenbrp.util.xml.exception.XmlException;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.MigratieXml;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;

/**
 * Check PL Helper.
 */
public final class PlHelper {

    @Inject
    private BrpPersoonslijstService persoonslijstService;

    private final Map<String, String> checkMap = new HashMap<>();

    /**
     * Registreer een administratie nummer om later te checken.
     * @param berichtReferentie bericht referentie
     * @param administratieNummer administratie nummer
     */
    public void checkPl(final String berichtReferentie, final String administratieNummer) {
        checkMap.put(berichtReferentie, administratieNummer);
    }

    /**
     * Schoon de geregistreerde administratie nummers op.
     */
    public void clear() {
        checkMap.clear();
    }

    /**
     * Lees de PL voor het opgeslagen administratienummer (via bericht correlatie).
     * @param bericht bericht
     * @return resultaat bericht (met gelezen pl)
     * @throws KanaalException bij fouten
     */
    public Bericht checkPlResult(final Bericht bericht) throws KanaalException {
        if (!checkMap.containsKey(bericht.getCorrelatieReferentie())) {
            throw new KanaalException("check_pl_result resultaat verwachting niet gecorreleerd aan check_pl bericht");
        }

        final String administratieNummer = checkMap.get(bericht.getCorrelatieReferentie());
        final String result;
        try {
            result = readPl(administratieNummer);
        } catch (final
        IOException
                | XmlException e) {
            throw new KanaalException("Kon persoon niet lezen", e);
        }

        final Bericht ontvangenBericht = new Bericht();
        ontvangenBericht.setInhoud(result);

        return ontvangenBericht;
    }

    private String readPl(final String administratieNummer) throws IOException, XmlException {
        final BrpPersoonslijst brpPersoonslijst = persoonslijstService.bevraagPersoonslijst(administratieNummer);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            try (Writer writer = new OutputStreamWriter(baos)) {
                MigratieXml.encode(brpPersoonslijst, writer);
            }
            return baos.toString();
        }
    }
}
