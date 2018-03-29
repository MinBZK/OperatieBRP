/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.Stelsel;
import nl.bzk.migratiebrp.isc.jbpm.common.berichten.BerichtenDao;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringActionHandler;
import nl.bzk.migratiebrp.register.client.PartijService;
import nl.bzk.migratiebrp.util.common.AnummerUtil;
import org.springframework.stereotype.Component;

/**
 * Deze actionhandler bepaalt of het ingelezen bulk bestand valide is.
 */
@Component("uc812ValideerBulkBestandAction")
public final class ValideerBulkBestandAction implements SpringAction {

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOUT = "3b. Fout";

    private static final String NIEUWE_REGEL_MET_ENTER = "\r\n";
    private static final String NIEUWE_REGEL = "\n";
    private static final String KOMMA = ",";
    private static final Integer LENGTE_GEMEENTE_CODE = 4;
    private static final Integer LENGTE_ANUMMER = 10;
    private static final String MELDING_FOUT_REGEL = "Fout in regel ";
    private static final String INPUT_PARAMETER_KEY = "input";
    private static final Integer MAXIMALE_INDEX_FOUTMELDING = 3996;

    private final BerichtenDao berichtenDao;
    private final PartijService partijService;

    /**
     * Constructor.
     * @param berichtenDao berichten dao
     * @param partijService de partij service
     */
    @Inject
    public ValideerBulkBestandAction(final BerichtenDao berichtenDao, final PartijService partijService) {
        this.berichtenDao = berichtenDao;
        this.partijService = partijService;
    }

    @Override
    public Map<String, Object> execute(final Map<String, Object> parameters) {
        LOG.info("execute(parameters={})", parameters);

        final Uc812Bericht uc812Bericht = (Uc812Bericht) berichtenDao.leesBericht((Long) parameters.get(INPUT_PARAMETER_KEY));

        final PartijRegister partijRegister = partijService.geefRegister();

        final List<String> foutRegels = valideerInhoudBulkBestand(uc812Bericht.getBulkSynchronisatievraag(), partijRegister);
        if (!foutRegels.isEmpty()) {

            final Map<String, Object> result = new HashMap<>();

            final StringBuilder foutmelding = new StringBuilder();
            for (final String foutRegel : foutRegels) {
                foutmelding.append(foutRegel).append(".\n");
            }

            final String foutmeldingString;
            if (foutmelding.length() < MAXIMALE_INDEX_FOUTMELDING) {
                foutmeldingString = foutmelding.toString();
            } else {
                foutmeldingString = foutmelding.toString().substring(0, MAXIMALE_INDEX_FOUTMELDING) + "...";
            }

            result.put("foutafhandelingFoutmelding", foutmeldingString);
            result.put(SpringActionHandler.TRANSITION_RESULT, FOUT);

            LOG.debug("result: {}", result);
            return result;
        }

        return null;
    }

    private List<String> valideerInhoudBulkBestand(final String teformatterenTekst, final PartijRegister partijRegister) {

        final List<String> foutRegels = new ArrayList<>();

        final String teformatterenTekstGeNormaliseerd = teformatterenTekst.replaceAll(NIEUWE_REGEL_MET_ENTER, NIEUWE_REGEL);
        final String[] regels = teformatterenTekstGeNormaliseerd.split(NIEUWE_REGEL);
        LOG.info("Aantal regels: " + regels.length);

        int huidigeRegel = 1;
        for (final String regel : regels) {

            final String[] regelGesplit = regel.split(KOMMA);

            final int verwachtAantalKolommen = 2;
            if (regelGesplit.length != verwachtAantalKolommen) {
                foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Het aantal kolommen is niet 2. ");
            } else {
                final String gemeente = regelGesplit[0];
                if (LENGTE_GEMEENTE_CODE != gemeente.length()) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": De lengte van de gemeente code is niet " + LENGTE_GEMEENTE_CODE);
                } else if (!bepaalGbaGemeenten(partijRegister, gemeente)) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Gemeente met code " + gemeente + " is geen GBA-gemeente");
                }
                final String aNummer = regelGesplit[1];
                if (LENGTE_ANUMMER != aNummer.length()) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": De lengte van het a-nummer is niet " + LENGTE_ANUMMER);
                } else if (!AnummerUtil.isAnummerValide(aNummer)) {
                    foutRegels.add(MELDING_FOUT_REGEL + huidigeRegel + ": Het a-nummer " + aNummer + " is geen geldig a-nummer");
                }
            }

            huidigeRegel++;
        }

        return foutRegels;
    }

    private boolean bepaalGbaGemeenten(final PartijRegister partijRegister, final String ingelezenGemeente) {
        final Partij partij = partijRegister.zoekPartijOpGemeenteCode(ingelezenGemeente);
        return partij != null && partij.isBijhouder() && Stelsel.GBA == partij.getStelsel();

    }

}
