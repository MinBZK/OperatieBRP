/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.taskformlayout.renderer;

import org.jboss.gravel.common.renderer.RendererBase;
import org.jboss.gravel.common.renderer.Element;
import org.jbpm.jsf.taskformlayout.ui.UIDataArea;
import org.jbpm.jsf.taskformlayout.ui.UIDataCell;
import org.jbpm.jsf.taskformlayout.ui.UIDataForm;
import org.jbpm.jsf.taskformlayout.ui.UIDataSection;

import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;

import java.io.IOException;
import java.util.Iterator;

/**
 *
 */
public final class DataFormRenderer extends RendererBase {

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        // delegate to encodeChildren
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        if (! component.isRendered()) {
            return;
        }

        UIDataForm dataForm = ((UIDataForm)component);
        final int columns = dataForm.getColumns();
        final int colspan = columns * 2;

        final Element<UIDataForm> table = writeElement(context, "table", dataForm);
        table.writeId();
        table.addClass(dataForm.getStyleClass()).writeClass();
        table.addStyle(dataForm.getStyle()).writeStyle();

        for (int i = 0; i < columns; i ++) {
            final Element<UIDataForm> colgroup = table.writeElement("colgroup");
            colgroup.writeAttribute("span", 2);
            colgroup.close();
        }

        final UIComponent dataFormHeader = dataForm.getFacet("header");
        final UIComponent dataFormFooter = dataForm.getFacet("footer");

        if (dataFormHeader != null) {
            final Element<UIComponent> thead = table.writeElement("thead", dataFormHeader);
            thead.addClass(dataForm.getHeaderClass()).writeClass();
            thead.addStyle(dataForm.getHeaderStyle()).writeClass();
            final Element<UIComponent> tr = thead.writeElement("tr");
            final Element<UIComponent> th = tr.writeElement("th");
            th.writeAttribute("colspan", colspan);
            th.doEncode();
            th.close();
            tr.close();
            thead.close();
        }
        if (dataFormFooter != null) {
            final Element<UIComponent> tfoot = table.writeElement("tfoot", dataFormFooter);
            tfoot.addClass(dataForm.getFooterClass()).writeClass();
            tfoot.addStyle(dataForm.getFooterStyle()).writeClass();
            final Element<UIComponent> tr = tfoot.writeElement("tr");
            final Element<UIComponent> th = tr.writeElement("th");
            th.writeAttribute("colspan", colspan);
            th.doEncode();
            th.close();
            tr.close();
            tfoot.close();
        }

        final Iterator<UIDataSection> sectionIter = dataForm.getChildrenOfType(UIDataSection.class);
        boolean sections = false;
        while (sectionIter.hasNext()) {
            sections = true;
            final UIDataSection section = sectionIter.next();
            final Element<UIDataSection> tbody = table.writeElement("tbody", section);

            final UIComponent sectionHeader = section.getFacet("header");
            final UIComponent sectionFooter = section.getFacet("footer");

            if (sectionHeader != null) {
                final Element<UIComponent> tr = tbody.writeElement("tr", sectionHeader);
                final Element<UIComponent> th = tr.writeElement("th");
                th.writeAttribute("scope", "rowgroup");
                th.writeAttribute("colspan", colspan);
                th.doEncode();
                th.close();
                tr.close();
            }

            if (section instanceof UIDataArea) {
                final Element<UIDataSection> tr = tbody.writeElement("tr");
                final Element<UIDataSection> td = tr.writeElement("td");
                td.writeAttribute("colspan", colspan);
                td.doEncode();
                td.close();
                tr.close();
            } else {
                encodeCells(tbody, section.getChildrenOfType(UIDataCell.class), columns);
            }

            if (sectionFooter != null) {
                final Element<UIComponent> tr = tbody.writeElement("tr", sectionFooter);
                final Element<UIComponent> th = tr.writeElement("th");
                th.writeAttribute("colspan", colspan);
                th.doEncode();
                th.close();
                tr.close();
            }
            tbody.close();
        }
        if (! sections) {
            final Element<UIDataForm> tbody = table.writeElement("tbody");
            encodeCells(tbody, dataForm.getChildrenOfType(UIDataCell.class), columns);
            tbody.close();
        }
        table.close();
    }

    public <T extends UIComponent> void encodeCells(final Element<T> tbody, Iterator<UIDataCell> cellIterator, final int columns) throws IOException {
        int column = 0;

        Element<T> tr = null;
        while (cellIterator.hasNext()) {
            final UIDataCell dataCell = cellIterator.next();
            if (column == 0) {
                tr = tbody.writeElement("tr");
            }

            final UIComponent headerFacet = dataCell.getFacet("header");
            if (headerFacet != null) {
                Element<UIComponent> th = tr.writeElement("th", headerFacet);
                th.writeAttribute("scope", "colgroup");
                th.addClass(dataCell.getHeaderClass()).writeClass();
                th.addStyle(dataCell.getHeaderStyle()).writeStyle();
                th.doEncode();
                th.close();
            } else {
                tr.writeElement("th").close();
            }

            final Element<UIDataCell> td = tr.writeElement("td", dataCell);
            td.writeId();
            td.addClass(dataCell.getStyleClass()).writeClass();
            td.addStyle(dataCell.getStyle()).writeStyle();
            td.doEncode();
            td.close();

            column = (column + 1) % columns;
            if (column == 0) {
                tr.close();
            }
        }

        if (column > 0) {
            while (column < columns) {
                tr.writeElement("th").close();
                tr.writeElement("td").close();
            }
            tr.close();
        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // delegate to encodeChildren
    }

    public boolean getRendersChildren() {
        return true;
    }
}
