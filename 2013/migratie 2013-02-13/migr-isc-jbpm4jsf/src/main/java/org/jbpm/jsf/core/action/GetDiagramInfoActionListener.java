/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class GetDiagramInfoActionListener implements JbpmActionListener {
    private final ValueExpression processExpression;
    private final ValueExpression targetExpression;

    public GetDiagramInfoActionListener(final ValueExpression processExpression, final ValueExpression targetExpression) {
        this.processExpression = processExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "getDiagramInfo";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object processValue = processExpression.getValue(elContext);
            if (processValue == null) {
                context.setError("Error getting diagram info", "The process value is null");
                return;
            }
            if (!(processValue instanceof ProcessDefinition)) {
                context.setError("Error getting diagram info", "The process value is not of type ProcessDefinition");
            }
            final ProcessDefinition processDefinition = (ProcessDefinition) processValue;
            final FileDefinition fileDefinition = processDefinition.getFileDefinition();
            if (! fileDefinition.hasFile("gpd.xml")) {
                targetExpression.setValue(elContext, null);
                context.selectOutcome("success");
                return;
            }
            Document document = XmlUtil.parseXmlInputStream(fileDefinition.getInputStream("gpd.xml"));
            Element processDiagramElement = document.getDocumentElement();
            final String widthString = processDiagramElement.getAttribute("width");
            final String heightString = processDiagramElement.getAttribute("height");
            final List<DiagramNodeInfo> diagramNodeInfoList = new ArrayList<DiagramNodeInfo>();
            final NodeList nodeNodeList = processDiagramElement.getElementsByTagName("node");
            final int nodeNodeListLength = nodeNodeList.getLength();
            for (int i = 0; i < nodeNodeListLength; i ++) {
                final Node nodeNode = nodeNodeList.item(i);
                if (nodeNode.getParentNode() == processDiagramElement) {
                    final Element nodeElement = (Element) nodeNode;
                    final String nodeName = nodeElement.getAttribute("name");
                    final String nodeXString = nodeElement.getAttribute("x");
                    final String nodeYString = nodeElement.getAttribute("y");
                    final String nodeWidthString = nodeElement.getAttribute("width");
                    final String nodeHeightString = nodeElement.getAttribute("height");
                    final DiagramNodeInfo nodeInfo = new DiagramNodeInfo(
                        nodeName,
                        Integer.parseInt(nodeXString),
                        Integer.parseInt(nodeYString),
                        Integer.parseInt(nodeWidthString),
                        Integer.parseInt(nodeHeightString)
                    );
                    diagramNodeInfoList.add(nodeInfo);
                }
            }
            final DiagramInfo diagramInfo = new DiagramInfo(
                Integer.parseInt(heightString),
                Integer.parseInt(widthString),
                diagramNodeInfoList
            );
            targetExpression.setValue(elContext, diagramInfo);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error getting diagram info", ex);
            return;
        }
    }

    public static final class DiagramInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        private final int width;
        private final int height;
        private final Map<String,DiagramNodeInfo> nodeMap;

        public DiagramInfo(final int height, final int width, final List<DiagramNodeInfo> nodeList) {
            this.height = height;
            this.width = width;
            final LinkedHashMap<String, DiagramNodeInfo> map = new LinkedHashMap<String, DiagramNodeInfo>();
            for (DiagramNodeInfo nodeInfo : nodeList) {
                map.put(nodeInfo.getName(), nodeInfo);
            }
            nodeMap = Collections.unmodifiableMap(map);
        }

        public int getHeight() {
            return height;
        }

        public Map<String, DiagramNodeInfo> getNodeMap() {
            return nodeMap;
        }

        public List<DiagramNodeInfo> getNodes() {
            return Collections.unmodifiableList(new ArrayList<DiagramNodeInfo>(nodeMap.values()));
        }

        public int getWidth() {
            return width;
        }
    }

    public static final class DiagramNodeInfo implements Serializable {
        private static final long serialVersionUID = 1L;

        private final String name;
        private final int x;
        private final int y;
        private final int width;
        private final int height;

        public DiagramNodeInfo(final String name, final int x, final int y, final int width, final int height) {
            this.height = height;
            this.name = name;
            this.width = width;
            this.x = x;
            this.y = y;
        }

        public int getHeight() {
            return height;
        }

        public String getName() {
            return name;
        }

        public int getWidth() {
            return width;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
