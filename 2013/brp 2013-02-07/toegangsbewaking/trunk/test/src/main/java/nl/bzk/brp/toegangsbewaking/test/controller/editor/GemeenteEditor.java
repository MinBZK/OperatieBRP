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

import nl.bzk.brp.toegangsbewaking.test.model.Gem;


public class GemeenteEditor extends PropertyEditorSupport {

    private final Map<Integer, Gem> mogelijkeGemeentes;
    
    public GemeenteEditor(final List<Gem> gemeentes) {
        mogelijkeGemeentes = new HashMap<Integer, Gem>();
        for (Gem gem : gemeentes) {
            mogelijkeGemeentes.put(gem.getId(), gem);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkeGemeentes.get(Integer.parseInt(text)));
    }
 
    @Override
    public String getAsText() {
        Gem gem = (Gem) getValue();
        if (gem == null) {
            return null;
        } else {
            return Integer.toString(gem.getId());
        }
    }

}
