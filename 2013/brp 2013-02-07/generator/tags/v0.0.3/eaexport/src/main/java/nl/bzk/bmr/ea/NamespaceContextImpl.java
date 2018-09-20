/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.bmr.ea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;


public class NamespaceContextImpl implements NamespaceContext {

    private static final String XML        = "http://www.w3.org/2000/xmlns/";
    private static final String XMI        = "http://schema.omg.org/spec/XMI/2.1";
    private static final String UML        = "http://schema.omg.org/spec/UML/2.1";

    private Map<String, String> namespaces = new HashMap<String, String>();
    {
        namespaces.put("xmlns", XML);
        namespaces.put("xmi", XMI);
        namespaces.put("uml", UML);
    }

    @Override
    public String getNamespaceURI(final String prefix) {
        return namespaces.get(prefix);
    }

    @Override
    public String getPrefix(final String namespaceURI) {
        for (Map.Entry<String, String> entry : namespaces.entrySet()) {
            if (entry.getValue().equals(namespaceURI)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(final String namespaceURI) {
        List<String> prefixes = new ArrayList<String>();
        for (Map.Entry<String, String> entry : namespaces.entrySet()) {
            if (entry.getValue().equals(namespaceURI)) {
                prefixes.add(entry.getKey());
            }
        }
        return prefixes.iterator();
    }
}
