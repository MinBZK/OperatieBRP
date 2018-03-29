/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actionhandler;

import java.util.Map;

import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;

import org.dom4j.tree.DefaultElement;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Action handler om uitgaande berichten, waarop een atnwoord wordt verwacht, te versturen.
 */
public final class EsbActionHandler implements ActionHandler {

    /**
     * Attribuut naam waarop het JBPM proces id wordt gezet.
     */
    public static final String PROCESS_ID_ATTRIBUTE = "jbpm.process.id";
    /**
     * Attribuut naam waarop het JBPM token id wordt gezet.
     */
    public static final String TOKEN_ID_ATTRIBUTE = "jbpm.token.id";
    /**
     * Attribuut naam waarop het JBPM node id wordt gezet.
     */
    public static final String NODE_ID_ATTRIBUTE = "jbpm.node.id";

    private static final long serialVersionUID = 1L;

    /**
     * The ESB Service Name, specified in the processdefinition.xml.
     */
    private String esbServiceName;
    /**
     * XML element to specify the mapping of variables from jBPM to ESB.
     */
    private DefaultElement bpmToEsbVars;
    /**
     * XML element to specify the mapping of variables from jBPM to ESB.
     */
    private DefaultElement esbToBpmVars;

    /**
     * Geef de ESB service naam.
     * @return de ESB service naam
     */
    public String getEsbServiceName() {
        return esbServiceName;
    }

    /**
     * Zet de ESB service naam.
     * @param esbServiceName de te zetten ESB service naam.
     */
    public void setEsbServiceName(final String esbServiceName) {
        this.esbServiceName = esbServiceName;
    }

    /**
     * Geef de BPM naar ESB variabelen.
     * @return de BPM naar ESB variabelen.
     */
    public DefaultElement getBpmToEsbVars() {
        return bpmToEsbVars;
    }

    /**
     * Zet de BPM naar ESB variabelen.
     * @param bpmToEsbVars de te zetten BPM naar ESB variabelen.
     */
    public void setBpmToEsbVars(final DefaultElement bpmToEsbVars) {
        this.bpmToEsbVars = bpmToEsbVars;
    }

    /**
     * Geef de ESB naar BPM variabelen.
     * @return de ESB naar BPM variabelen.
     */
    public DefaultElement getEsbToBpmVars() {
        return esbToBpmVars;
    }

    /**
     * Zet de ESB naar BPM variabelen.
     * @param esbToBpmVars de te zetten ESB naar BPM variabelen.
     */
    public void setEsbToBpmVars(final DefaultElement esbToBpmVars) {
        this.esbToBpmVars = esbToBpmVars;
    }

    @Override
    public void execute(final ExecutionContext executionContext) {
        // Map message
        final Map<String, Object> attributes = EsbHandlerUtil.mapAttributesFromJbpmToEsb(bpmToEsbVars, executionContext);
        final Object messageContent = attributes.remove(EsbHandlerUtil.BODY_CONTENT_VARIABLE_NAME);

        // Map default attributes
        attributes.put(PROCESS_ID_ATTRIBUTE, executionContext.getProcessInstance().getId());

        final Token token = executionContext.getToken();
        attributes.put(TOKEN_ID_ATTRIBUTE, token.getId());
        if (token.getNode() != null) {
            attributes.put(NODE_ID_ATTRIBUTE, token.getNode().getId());
        }

        // Call outbound handler
        final SpringService springService = (SpringService) executionContext.getJbpmContext().getServices().getService(SpringServiceFactory.SERVICE_NAME);
        final OutboundHandler outboundHandler = springService.getBean(OutboundHandler.class);
        outboundHandler.handleMessage(esbServiceName, messageContent == null ? null : messageContent.toString(), attributes);
    }
}
