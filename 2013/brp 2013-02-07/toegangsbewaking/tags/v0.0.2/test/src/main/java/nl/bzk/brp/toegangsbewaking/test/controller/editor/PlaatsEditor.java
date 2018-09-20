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

import nl.bzk.brp.toegangsbewaking.test.model.Plaats;


public class PlaatsEditor extends PropertyEditorSupport {

    private final Map<Integer, Plaats> mogelijkePlaatsen;
    
    public PlaatsEditor(final List<Plaats> plaatsen) {
        mogelijkePlaatsen = new HashMap<Integer, Plaats>();
        for (Plaats plaats : plaatsen) {
            mogelijkePlaatsen.put(plaats.getId(), plaats);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkePlaatsen.get(Integer.parseInt(text)));
    }
 
    @Override
    public String getAsText() {
        Plaats plaats = (Plaats) getValue();
        if (plaats == null) {
            return null;
        } else {
            return Integer.toString(plaats.getId());
        }
    }

}
