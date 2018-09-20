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

import nl.bzk.brp.toegangsbewaking.test.model.Land;


public class LandEditor extends PropertyEditorSupport {

    private final Map<Integer, Land> mogelijkeLanden;
    
    public LandEditor(final List<Land> landen) {
        mogelijkeLanden = new HashMap<Integer, Land>();
        for (Land land : landen) {
            mogelijkeLanden.put(land.getId(), land);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkeLanden.get(Integer.parseInt(text)));
    }
 
    @Override
    public String getAsText() {
        Land land = (Land) getValue();
        if (land == null) {
            return null;
        } else {
            return Integer.toString(land.getId());
        }
    }

}
