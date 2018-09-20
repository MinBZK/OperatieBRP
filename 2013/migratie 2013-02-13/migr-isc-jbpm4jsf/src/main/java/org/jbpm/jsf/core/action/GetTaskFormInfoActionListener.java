/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.io.InputStream;

/**
 *
 */
public final class GetTaskFormInfoActionListener implements JbpmActionListener {
    private final ValueExpression processExpression;
    private final ValueExpression targetExpression;

    public GetTaskFormInfoActionListener(final ValueExpression processExpression, final ValueExpression targetExpression) {
        this.processExpression = processExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "getTaskFormInfo";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object processValue = processExpression.getValue(elContext);
            if (processValue == null) {
                context.setError("Error reading form information", "The process value is null");
                return;
            }
            if (!(processValue instanceof ProcessDefinition)) {
                context.setError("Error reading form information", "The process value is not of type ProcessDefinition");
            }
            final ProcessDefinition processDefinition = (ProcessDefinition) processValue;
            final FileDefinition fileDefinition = processDefinition.getFileDefinition();
            if (! fileDefinition.hasFile("forms.xml")) {
                targetExpression.setValue(elContext, Collections.emptyMap());
                context.selectOutcome("success");
                return;
            }
            final InputStream inputStream = fileDefinition.getInputStream("forms.xml");
            if (inputStream == null) {
                targetExpression.setValue(elContext, Collections.emptyMap());
                context.selectOutcome("success");
                return;
            }
            final Map<String, String> targetMap = new HashMap<String, String>();
            final Document document = XmlUtil.parseXmlInputStream(inputStream);
            final Element documentElement = document.getDocumentElement();
            final NodeList nodeList = documentElement.getElementsByTagName("form");
            final int length = nodeList.getLength();
            for (int i = 0; i < length; i ++) {
                final Element element = (Element) nodeList.item(i);
                final String itemTaskName = element.getAttribute("task");
                final String itemFormName = element.getAttribute("form");
                if (itemTaskName == null || itemFormName == null) {
                    continue;
                }
                targetMap.put(itemTaskName, itemFormName);
            }
            targetExpression.setValue(elContext, Collections.unmodifiableMap(targetMap));
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error reading form information", ex);
            return;
        }
    }
}
