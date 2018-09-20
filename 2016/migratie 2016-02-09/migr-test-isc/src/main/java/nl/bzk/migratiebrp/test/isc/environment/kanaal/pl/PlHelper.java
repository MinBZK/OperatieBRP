/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.pl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.serialize.PersoonslijstEncoder;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;

/**
 * Check PL Helper.
 */
public final class PlHelper {

    @Inject
    private BrpDalService brpDalService;

    private final Map<String, Long> checkMap = new HashMap<>();

    /**
     * Registreer een administratie nummer om later te checken.
     *
     * @param berichtReferentie
     *            bericht referentie
     * @param administratieNummer
     *            administratie nummer
     */
    public void checkPl(final String berichtReferentie, final long administratieNummer) {
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
     *
     * @param bericht
     *            bericht
     * @return resultaat bericht (met gelezen pl)
     * @throws KanaalException
     *             bij fouten
     */
    public Bericht checkPlResult(final Bericht bericht) throws KanaalException {
        if (!checkMap.containsKey(bericht.getCorrelatieReferentie())) {
            throw new KanaalException("check_pl_result resultaat verwachting niet gecorreleerd aan check_pl bericht");
        }

        final long administratieNummer = checkMap.get(bericht.getCorrelatieReferentie());
        String result;
        try {
            result = readPl(administratieNummer);
        } catch (final IOException e) {
            throw new KanaalException("Kon persoon niet lezen", e);
        }

        final Bericht ontvangenBericht = new Bericht();
        ontvangenBericht.setInhoud(result);

        return ontvangenBericht;
    }

    private String readPl(final long administratieNummer) throws IOException {
        final BrpPersoonslijst brpPersoonslijst = brpDalService.bevraagPersoonslijst(administratieNummer);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PersoonslijstEncoder.encodePersoonslijst(brpPersoonslijst, baos);
            return baos.toString();
        }
    }
}
