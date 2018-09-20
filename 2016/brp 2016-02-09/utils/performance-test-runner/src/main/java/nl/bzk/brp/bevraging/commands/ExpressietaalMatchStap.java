/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.app.support.Abonnement;
import nl.bzk.brp.bevraging.app.support.Afnemer;
import nl.bzk.brp.expressietaal.expressies.BRPExpressies;
import nl.bzk.brp.expressietaal.expressies.Expressie;
import nl.bzk.brp.expressietaal.expressies.literals.BooleanLiteralExpressie;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class ExpressietaalMatchStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressietaalMatchStap.class);

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<Afnemer> afnemers = (List) context.get(ContextParameterNames.AFNEMERLIJST);
        List<Persoon> personenLijst = (List) context.get(ContextParameterNames.PERSONENLIJST);

        long totalTime = 0;
        List<BevraagInfo> results = new ArrayList<BevraagInfo>(personenLijst.size());
        for (Afnemer afnemer : afnemers) {
            long startTijdAfnemer = System.currentTimeMillis();
            List<String> evaluatieFouten = verwerkAbonnementen(personenLijst, afnemer);
            long totaleTijdAfnemer = System.currentTimeMillis() - startTijdAfnemer;

            BevraagInfo info = new BevraagInfo(afnemer.getNaam(), (evaluatieFouten.size() == 0 ? "OK" : "FAIL"),
                                               totaleTijdAfnemer);
            results.add(info);

            LOGGER.info("Expressietaal cycles ID '{}' in {} ms.", info.getTaskName(), totaleTijdAfnemer);
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return CONTINUE_PROCESSING;
    }

    /**
     * Verwerk de abonnementen van een afnemer.
     * @param personenLijst de lijst personen die gematched moet worden
     * @param afnemer de afnemer
     * @return een lijst met opgetreden fouten
     */
    private List<String> verwerkAbonnementen(final List<Persoon> personenLijst, final Afnemer afnemer) {
        List<String> evaluatieFouten = new ArrayList<String>();

        for (Abonnement abonnement : afnemer.getAbonnementen()) {

            ParserResultaat parserResultaat =
                    getParserResultaat(abonnement.getExpressie());

            List<Persoon> personenDieMatchen = new ArrayList<Persoon>();

            for (Persoon persoon : personenLijst) {
                try {
                    Expressie evaluatieResultaat = BRPExpressies.evalueer(parserResultaat.getExpressie(), persoon);
                    boolean succes = !evaluatieResultaat.isFout() && (evaluatieResultaat instanceof BooleanLiteralExpressie);

                    if (succes) {
                        if (evaluatieResultaat.getBooleanWaarde()) {
                            personenDieMatchen.add(persoon);
                        }
                    } else {
                        evaluatieFouten.add(evaluatieResultaat.getStringWaarde());
                        //LOGGER.error("Evaluatiefout: " + evaluatieResultaat.getFout());
                    }
                } catch (Exception e) {
                    LOGGER.error("{}", abonnement.getExpressie(), e);
                }
            }

            //LOGGER.info("Resultaat voor leveringsautorisatie {}: {} van de {} personen.", new Object[]{leveringsautorisatie.getOmschrijving(), personenDieMatchen.size(), personenLijst.size()});
        }

        return evaluatieFouten;
    }

    /**
     * Parse een expressie.
     * @param expressie de expressie string om te parsen
     * @return een geparsde expressie, anders wordt er een {@link RuntimeException} gegooid
     */
    private ParserResultaat getParserResultaat(final String expressie) {
        ParserResultaat parserResultaat = BRPExpressies.parse(expressie);
        if (! parserResultaat.succes()) {
            throw new RuntimeException("Failed to parse expression: " + expressie + "\n" + parserResultaat.getFoutmelding());
        }
        return parserResultaat;
    }

}
