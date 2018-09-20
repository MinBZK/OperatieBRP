/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.toegangsbewaking.test.controller.editor;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bzk.brp.toegangsbewaking.test.model.Verantwoordelijke;


public class VerantwoordelijkeEditor extends PropertyEditorSupport {

    private final Map<Integer, Verantwoordelijke> mogelijkeVerantwoordelijken;
    
    public VerantwoordelijkeEditor(final List<Verantwoordelijke> verantwoordelijken) {
        mogelijkeVerantwoordelijken = new HashMap<Integer, Verantwoordelijke>();
        for (Verantwoordelijke verantw : verantwoordelijken) {
            mogelijkeVerantwoordelijken.put((int) verantw.getId(), verantw);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkeVerantwoordelijken.get(Integer.parseInt(text)));
    }
 
    @Override
    public String getAsText() {
        Verantwoordelijke verantw = (Verantwoordelijke) getValue();
        if (verantw == null) {
            return null;
        } else {
            return Integer.toString(verantw.getId());
        }
    }

}
