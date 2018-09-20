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
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieFout;
import nl.bzk.brp.expressietaal.parser.syntaxtree.EvaluatieResultaat;
import nl.bzk.brp.model.logisch.kern.Persoon;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExpressietaalMatchStap extends AbstractAsynchroonStap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExpressietaalMatchStap.class);

    @Override
    public boolean doExecute(final Context context) throws Exception {
        List<Afnemer> afnemers = (List) context.get(ContextParameterNames.AFNEMERLIJST);
        List<Persoon> personenLijst = (List) context.get(ContextParameterNames.PERSONENLIJST);

        long totalTime = 0;
        List<BevraagInfo> results = new ArrayList<BevraagInfo>(personenLijst.size());
        for(Afnemer afnemer : afnemers){
            long startTijdAfnemer = System.currentTimeMillis();
            List<EvaluatieFout> evaluatieFouten = verwerkAbonnementen(personenLijst, afnemer);
            long totaleTijdAfnemer = System.currentTimeMillis() - startTijdAfnemer;

            BevraagInfo info = new BevraagInfo(afnemer.getNaam(), (evaluatieFouten.size() == 0 ? "OK" : "FAIL"), totaleTijdAfnemer);
            results.add(info);

            LOGGER.info("Expressietaal cycles ID '{}' in {} ms.", info.getTaskName(), totaleTijdAfnemer);
        }

        // opslaan resultaten
        context.put(ContextParameterNames.TASK_INFO_LIJST, results);

        return false;
    }

    private List<EvaluatieFout> verwerkAbonnementen(final List<Persoon> personenLijst, final Afnemer afnemer)
    {
        List<EvaluatieFout> evaluatieFouten = new ArrayList<EvaluatieFout>();

        for(Abonnement abonnement : afnemer.getAbonnementen()){

            ParserResultaat parserResultaat =
                    getParserResultaat(abonnement.getExpressie());

            List<Persoon> personenDieMatchen = new ArrayList<Persoon>();

            for(Persoon persoon : personenLijst){
                EvaluatieResultaat evaluatieResultaat = BRPExpressies.evalueer(parserResultaat.getExpressie(), persoon);
                boolean succes = evaluatieResultaat.succes() && evaluatieResultaat.isBooleanWaarde();

                if(succes){
                    if(evaluatieResultaat.getBooleanWaarde()){
                        personenDieMatchen.add(persoon);
                    }
                } else {
                    evaluatieFouten.add(evaluatieResultaat.getFout());
                    //LOGGER.error("Evaluatiefout: " + evaluatieResultaat.getFout());
                }
            }

            //LOGGER.info("Resultaat voor abonnement {}: {} van de {} personen.", new Object[]{abonnement.getOmschrijving(), personenDieMatchen.size(), personenLijst.size()});
        }

        return evaluatieFouten;
    }

    private ParserResultaat getParserResultaat(final String expressie) {
        ParserResultaat parserResultaat = BRPExpressies.parse(expressie);
        if(parserResultaat.getFout() != null){
            throw new RuntimeException("Failed to parse expression: " + expressie);
        }
        return parserResultaat;
    }

}
