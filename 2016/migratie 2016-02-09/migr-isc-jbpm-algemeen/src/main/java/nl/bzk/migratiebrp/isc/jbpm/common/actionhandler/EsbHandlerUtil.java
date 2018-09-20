/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.actionhandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Esb handler util.
 */
public final class EsbHandlerUtil {

    /** XML element for mapping (in JBPM processdefinition). */
    public static final String MAPPING_TAG = "mapping";
    /** XML element for esb (in JBPM processdefinition). */
    public static final String ESB_VARNAME_TAG = "esb";
    /** XML element for bpm (in JBPM processdefinition). */
    public static final String BPM_VARNAME_TAG = "bpm";
    /** XML value for body content (in JBPM processdefinition). */
    public static final String BODY_CONTENT_VARIABLE_NAME = "BODY_CONTENT";

    private EsbHandlerUtil() {
        // Niet instantieerbaar
    }

    /**
     * Map attributes from the execution context (JBPM) to the ESB map.
     *
     * @param bpmToEsbVars
     *            configuration
     * @param executionContext
     *            execution context (JBPM variables with values)
     * @return values mapped to esb variables
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> mapAttributesFromJbpmToEsb(final DefaultElement bpmToEsbVars, final ExecutionContext executionContext) {
        final Map<String, Object> result = new HashMap<>();
        final List<Element> mappings = bpmToEsbVars.elements(MAPPING_TAG);
        for (final Element mapping : mappings) {
            final String esbVariable = mapping.attributeValue(ESB_VARNAME_TAG);
            final String bpmVariable = mapping.attributeValue(BPM_VARNAME_TAG);

            result.put(esbVariable, executionContext.getContextInstance().getVariable(bpmVariable, executionContext.getToken()));
        }
        return result;
    }

    /**
     * Map attribute from the ESB map to the JBPM map.
     *
     * @param esbToBpmVars
     *            configuration
     * @param attributes
     *            ESB attributes (with values)
     * @return vlues mapped to JBPM variables
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> mapAttributesFromEsbToJbpm(final DefaultElement esbToBpmVars, final Map<String, Object> attributes) {
        final Map<String, Object> result = new HashMap<>();
        final List<Element> mappings = esbToBpmVars.elements(MAPPING_TAG);
        for (final Element mapping : mappings) {
            final String esbVariable = mapping.attributeValue(ESB_VARNAME_TAG);
            final String bpmVariable = mapping.attributeValue(BPM_VARNAME_TAG);

            result.put(bpmVariable, attributes.get(esbVariable));
        }
        return result;
    }
}
