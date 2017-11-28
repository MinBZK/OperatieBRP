/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;

/**
 */
public class Parameters {


    private List<ParameterItem> itemList;

    @XmlElement(name = "item")
    public List<ParameterItem> getItemList() {
        return itemList;
    }

    public void setItemList(final List<ParameterItem> itemList) {
        this.itemList = itemList;
    }
}
