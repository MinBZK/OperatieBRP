/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util.conversie.zoekvraag.xml;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
public class In0Root {

    private List<InElement> elementList;

    @XmlElement(name = "in0")
    public List<InElement> getElementList() {
        return elementList;
    }

    public void setElementList(final List<InElement> elementList) {
        this.elementList = elementList;
    }
}
