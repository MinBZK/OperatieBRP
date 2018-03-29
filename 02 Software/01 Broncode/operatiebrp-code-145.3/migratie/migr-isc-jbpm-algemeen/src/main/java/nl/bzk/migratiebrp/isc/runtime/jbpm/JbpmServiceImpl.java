/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.Bericht;
import nl.bzk.migratiebrp.bericht.model.brp.BrpBericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.AbstractOngeldigLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.OngeldigeSyntaxBericht;
import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.EsbActionHandler;
import nl.bzk.migratiebrp.isc.jbpm.common.actionhandler.EsbHandlerUtil;
import nl.bzk.migratiebrp.isc.jbpm.common.jsf.FoutafhandelingPaden;
import org.dom4j.tree.DefaultElement;
import org.jbpm.JbpmConfiguration;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.instantiation.Delegation;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 * JBPM Service implementatie.
 */
@Service
public final class JbpmServiceImpl implements JbpmService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String FOUTAFHANDELING_ATTRIBUTE_FUNCTIONELE_STAP = "functioneleStap";
    private static final String FOUTAFHANDELING_ATTRIBUTE_BRON_GEMEENTE = "bronPartijCode";
    private static final String FOUTAFHANDELING_ATTRIBUTE_DOEL_GEMEENTE = "doelPartijCode";
    private static final String FOUTAFHANDELING_ATTRIBUTE_FOUT = "fout";
    private static final String FOUTAFHANDELING_ATTRIBUTE_FOUTMELDING = "foutmelding";
    private static final String FOUTAFHANDELING_ATTRIBUTE_INDICATIE_BEHEERDER = "indicatieBeheerder";
    private static final String FOUTAFHANDELING_ATTRIBUTE_RESTART = "restart";
    private static final String FOUTAFHANDELING_ATTRIBUTE_FOUTPADEN = "foutafhandelingPaden";
    private static final String FOUTAFHANDELING_ATTRIBUTE_LO3_BERICHT = "lo3Bericht";
    private static final String FOUTAFHANDELING_ATTRIBUTE_BRP_BERICHT = "brpBericht";
    private static final String FOUTAFHANDELING_ATTRIBUTE_OVERIG_BERICHT = "overigBericht";
    private static final String FOUTAFHANDELING_ATTRIBUTE_INDICATIE_CYCLUS_FOUT = "indicatieCyclusFout";
    private static final String FOUTAFHANDELING_PAD_END = "end";
    private static final String FOUTAFHANDELING_PAD_END_ZONDER_PF = "endWithoutPf";
    private static final String FOUTAFHANDELING_PAD_END_DESC = "Afsluiten";
    private static final String FOUTAFHANDELING_PAD_OPNIEUW = "Opnieuw antwoord versturen";

    private JbpmInvoker jbpmInvoker;

    /**
     * Zet de JBPM invoker.
     * @param jbpmInvoker De te zetten invoker.
     */
    @Required
    public void setJbpmInvoker(final JbpmInvoker jbpmInvoker) {
        this.jbpmInvoker = jbpmInvoker;
    }

    @Override
    public long startProces(final String procesNaam, final Long berichtId) {
        return jbpmInvoker.executeInContext(jbpmContext -> {
            final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate(procesNaam);
            final ContextInstance contextInstance = processInstance.getContextInstance();
            contextInstance.setVariable("input", berichtId);

            // Synchrone start
            processInstance.signal();
            return processInstance.getId();
        });
    }

    @Override
    public long startFoutmeldingProces(
            final Bericht bericht,
            final Long berichtId,
            final String originator,
            final String recipient,
            final String fout,
            final String foutmelding,
            final boolean indicatieBeheerder,
            final boolean geenPfVoorL3o) {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FUNCTIONELE_STAP, "esb.verwerken");
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUT, fout);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUTMELDING, foutmelding);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_INDICATIE_BEHEERDER, indicatieBeheerder);

        if (bericht instanceof Lo3Bericht) {
            attributes.put(FOUTAFHANDELING_ATTRIBUTE_LO3_BERICHT, berichtId);

            if (bericht instanceof OngeldigeSyntaxBericht || bericht instanceof AbstractOngeldigLo3Bericht) {
                attributes.put(FOUTAFHANDELING_ATTRIBUTE_INDICATIE_CYCLUS_FOUT, Boolean.FALSE);
            } else {
                attributes.put(FOUTAFHANDELING_ATTRIBUTE_INDICATIE_CYCLUS_FOUT, Boolean.TRUE);
            }
        } else if (bericht instanceof BrpBericht) {
            attributes.put(FOUTAFHANDELING_ATTRIBUTE_BRP_BERICHT, berichtId);
        } else {
            attributes.put(FOUTAFHANDELING_ATTRIBUTE_OVERIG_BERICHT, berichtId);
        }

        if (!indicatieBeheerder) {
            attributes.put(FOUTAFHANDELING_ATTRIBUTE_RESTART, FOUTAFHANDELING_PAD_END);
        }

        final FoutafhandelingPaden paden = new FoutafhandelingPaden();
        if (bericht instanceof Lo3Bericht) {
            final String berichtType = bericht.getBerichtType();
            if ("delr".equalsIgnoreCase(berichtType)) {
                paden.put(FOUTAFHANDELING_PAD_END_DESC, FOUTAFHANDELING_PAD_END_DESC, false, false);
                paden.put(FOUTAFHANDELING_PAD_OPNIEUW, FOUTAFHANDELING_PAD_OPNIEUW, false, false);
            } else if (berichtType.toLowerCase().startsWith("pf") || geenPfVoorL3o) {
                paden.put(FOUTAFHANDELING_PAD_END, FOUTAFHANDELING_PAD_END_DESC, false, false);
            } else {
                paden.put(FOUTAFHANDELING_PAD_END, "Afsluiten (met Pf)", true, false);
                paden.put(FOUTAFHANDELING_PAD_END_ZONDER_PF, "Afsluiten (zonder Pf)", false, false);
            }

        } else {
            paden.put(FOUTAFHANDELING_PAD_END, FOUTAFHANDELING_PAD_END_DESC, false, false);

        }

        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUTPADEN, paden);

        // Gemeente
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_BRON_GEMEENTE, originator);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_DOEL_GEMEENTE, recipient);

        return invokeProces(attributes, "foutafhandeling");
    }

    @Override
    public long startHerhaalProces(
            final Bericht bericht,
            final Long oorspronkelijkProcesBerichtId,
            final String originator,
            final String recipient,
            final String fout,
            final String foutmelding,
            final boolean indicatieBeheerder) {
        final Map<String, Object> attributes = new HashMap<>();
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FUNCTIONELE_STAP, "esb.verwerken");
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUT, fout);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUTMELDING, foutmelding);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_INDICATIE_BEHEERDER, indicatieBeheerder);

        attributes.put(FOUTAFHANDELING_ATTRIBUTE_LO3_BERICHT, oorspronkelijkProcesBerichtId);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_INDICATIE_CYCLUS_FOUT, Boolean.TRUE);

        if (!indicatieBeheerder) {
            attributes.put(FOUTAFHANDELING_ATTRIBUTE_RESTART, FOUTAFHANDELING_PAD_OPNIEUW);
        }

        final FoutafhandelingPaden paden = new FoutafhandelingPaden();
        paden.put(FOUTAFHANDELING_PAD_END_DESC, "Afsluiten", false, false);
        paden.put(FOUTAFHANDELING_PAD_OPNIEUW, "Opnieuw antwoord versturen", false, false);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_FOUTPADEN, paden);

        // Gemeente
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_BRON_GEMEENTE, originator);
        attributes.put(FOUTAFHANDELING_ATTRIBUTE_DOEL_GEMEENTE, recipient);

        return invokeProces(attributes, "herhaal");
    }

    private long invokeProces(final Map<String, Object> attributes, final String procesnaam) {
        return jbpmInvoker.executeInContext(jbpmContext -> {
            final ProcessInstance processInstance = jbpmContext.newProcessInstanceForUpdate(procesnaam);
            final ContextInstance contextInstance = processInstance.getContextInstance();
            for (final Map.Entry<String, Object> attribute : attributes.entrySet()) {
                contextInstance.setVariable(attribute.getKey(), attribute.getValue());
            }

            processInstance.signal();
            return processInstance.getId();
        });
    }

    @Override
    public boolean vervolgProces(final long processInstanceId, final long tokenId, final long nodeId, final Map<String, Object> attributes) {
        LOGGER.info("Proces vervolgen (processInstanceId={}, tokenId={}, nodeId={}", processInstanceId, tokenId, nodeId);
        return jbpmInvoker.executeInContext(jbpmContext -> {
            // Controle
            final Token token = jbpmContext.getTokenForUpdate(tokenId);

            if (token == null || token.getNode().getId() != nodeId || token.getProcessInstance().getId() != processInstanceId) {
                LOGGER.info("Proces bevindt zich in niet in de verwachte node. Proces wordt niet vervolgd.");
                return Boolean.FALSE;
            }

            final Thread currentThread = Thread.currentThread();
            final ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            mapVariabelen(attributes, token, currentThread, contextClassLoader);

            LOGGER.info("Signaleren token");
            token.signal();
            LOGGER.info("Proces vervolgd");
            return true;
        });
    }

    private void mapVariabelen(Map<String, Object> attributes, Token token, Thread currentThread, ClassLoader contextClassLoader) {
        try {
            // Set context class loader correctly for delegation class
            final ClassLoader processClassLoader = JbpmConfiguration.getProcessClassLoader(token.getProcessInstance().getProcessDefinition());
            currentThread.setContextClassLoader(processClassLoader);

            // Map variables
            final EsbActionHandler handler = bepaalEsbActionHandler(token.getNode().getAction());
            if (handler != null) {
                final DefaultElement esbToBpmVars = handler.getEsbToBpmVars();
                final Map<String, Object> variables = EsbHandlerUtil.mapAttributesFromEsbToJbpm(esbToBpmVars, attributes);
                for (final Map.Entry<String, Object> variable : variables.entrySet()) {
                    LOGGER.info("Mapping variable {} -> {}", variable.getKey(), variable.getValue());
                    token.getProcessInstance().getContextInstance().setVariable(variable.getKey(), variable.getValue(), token);
                }
            }
        } finally {
            currentThread.setContextClassLoader(contextClassLoader);
        }
    }

    private EsbActionHandler bepaalEsbActionHandler(final Action jbpmAction) {
        EsbActionHandler esbActionHandler = null;
        if (jbpmAction != null) {
            final Delegation actionDelegation = jbpmAction.getActionDelegation();
            if (actionDelegation != null && actionDelegation.getInstance() instanceof EsbActionHandler) {
                esbActionHandler = (EsbActionHandler) actionDelegation.getInstance();
            }
        }
        return esbActionHandler;
    }
}
