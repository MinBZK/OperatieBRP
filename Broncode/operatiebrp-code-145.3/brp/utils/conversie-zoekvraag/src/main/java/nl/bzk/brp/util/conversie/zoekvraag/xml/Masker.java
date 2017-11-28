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
public class Masker {

    private List<String> itemList;

    @XmlElement(name = "item")
    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(final List<String> itemList) {
        this.itemList = itemList;
    }
}
