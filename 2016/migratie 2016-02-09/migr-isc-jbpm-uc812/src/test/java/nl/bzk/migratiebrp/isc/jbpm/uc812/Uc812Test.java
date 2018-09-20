/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.uc812;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc811Bericht;
import nl.bzk.migratiebrp.bericht.model.isc.impl.Uc812Bericht;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Test de happy flow.
 */
public class Uc812Test extends AbstractUc812Test {

    @BeforeClass
    public static void outputTestIscBerichten() {
        // Output de unittests als migr-test-isc flow.
        // setOutputBerichten("D:\\mGBA\\work\\test-isc");
    }

    @Test
    public void happyFlow() throws Exception {
        final String aNummer = "1607306145";
        final String doelGemeente = "0599";

        final String bulkSynchronisatievraag = doelGemeente + "," + aNummer + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);

        // Start
        startProcess(uc812Bericht);

        Assert.assertTrue(processEnded());
    }

    @Test
    public void happyFlowMeerdere() throws Exception {
        final List<Long> aNummerLijst = new ArrayList<>();
        aNummerLijst.add(Long.valueOf("1607306145"));
        aNummerLijst.add(Long.valueOf("1718206305"));
        final List<String> gemeenteLijst = new ArrayList<>();
        gemeenteLijst.add("0599");
        gemeenteLijst.add("0717");

        final String bulkSynchronisatievraag =
                gemeenteLijst.get(0) + "," + aNummerLijst.get(0) + "\n" + gemeenteLijst.get(1) + "," + aNummerLijst.get(1) + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);

        // Start
        startProcess(uc812Bericht);

        Assert.assertTrue(processEnded());

        controleerSubProcessen(aNummerLijst, gemeenteLijst);
    }

    @Test
    public void badFlow() throws Exception {
        final String aNummer = "1607306140";
        final String doelGemeente = "0599";

        final String bulkSynchronisatievraag = doelGemeente + "," + aNummer + ",testtekst\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);

        // Start
        startProcess(uc812Bericht);

        signalHumanTask("end");

        Assert.assertTrue(processEnded());
    }

    @Test
    public void badFlowMeerdere() throws Exception {
        final String aNummer = "1607306145";
        final String aNummer2 = "1718206305";
        final String aNummerOngeldigeLengte = "123123123";
        final String aNummerOngeldig = "1231231235";
        final String aNummerNull = null;
        final String aNummerNietAlleenCijfers = "123test234";
        final String negatiefANummerOngeldig = "-123123124";
        final String doelGemeente = "0599";
        final String doelGemeenteOngeldigeLengte = "600";
        final String doelGemeenteNonGba = "0699";
        final String doelGemeenteNietBestaand = "0600";

        final String bulkSynchronisatievraag =
                doelGemeente
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummer2
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelGemeenteNonGba
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeenteNonGba
                        + ","
                        + aNummer2
                        + "\n"
                        + doelGemeenteNietBestaand
                        + ","
                        + aNummerOngeldig
                        + "\n"
                        + doelGemeente
                        + ","
                        + negatiefANummerOngeldig
                        + "\n"
                        + doelGemeenteOngeldigeLengte
                        + ","
                        + "\n"
                        + doelGemeenteOngeldigeLengte
                        + ","
                        + aNummer
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerOngeldigeLengte
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerNull
                        + "\n"
                        + doelGemeente
                        + ","
                        + aNummerNietAlleenCijfers
                        + "\n";

        final Uc812Bericht uc812Bericht = new Uc812Bericht();
        uc812Bericht.setBulkSynchronisatievraag(bulkSynchronisatievraag);

        // Start
        startProcess(uc812Bericht);

        signalHumanTask("end");

        Assert.assertTrue(processEnded());
    }

    private void controleerSubProcessen(final List<Long> aNummerLijst, final List<String> gemeenteLijst) {
        final List<Long> uc811BerichtIds = new ArrayList<>();

        // Ophalen 'beeindigd' proces en controleren dat er meerdere subprocessen
        // met de juiste input berichten zijn aangemaakt.
        executeInJbpmContext(new JbpmWorker() {

            @Override
            @SuppressWarnings("unchecked")
            public void execute(final JbpmContext context, final Long processInstanceId) {
                final List<Token> tokenLijst = context.getProcessInstance(processInstanceId).findAllTokens();

                final ProcessDefinition uc811ProcessDefinition = context.getGraphSession().findLatestProcessDefinition("uc811");

                final List<ProcessInstance> uc811SubProcessen = context.getGraphSession().findProcessInstances(uc811ProcessDefinition.getId());

                for (final ProcessInstance uc811SubProces : uc811SubProcessen) {
                    if (uc811SubProces.hasEnded() && tokenLijst.contains(uc811SubProces.getSuperProcessToken())) {
                        uc811BerichtIds.add((Long) uc811SubProces.getContextInstance().getVariable("input"));
                    }
                }
            }
        });

        Assert.assertEquals(aNummerLijst.size(), uc811BerichtIds.size());

        final List<Long> uc811ANummers = new ArrayList<>();
        final List<String> uc811Gemeentes = new ArrayList<>();
        for (final Long uc811BerichtId : uc811BerichtIds) {

            final Uc811Bericht uc811Bericht = (Uc811Bericht) leesBericht(uc811BerichtId);
            uc811Gemeentes.add(uc811Bericht.getGemeenteCode());
            uc811ANummers.add(uc811Bericht.getAnummer());
        }
        Assert.assertTrue(gemeenteLijst.containsAll(uc811Gemeentes));
        Assert.assertTrue(aNummerLijst.containsAll(uc811ANummers));
    }

}
