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

import nl.bzk.brp.toegangsbewaking.test.model.Functieadres;


public class FunctieadresEditor extends PropertyEditorSupport {

    private final Map<Integer, Functieadres> mogelijkeFuncties;
    
    public FunctieadresEditor(final List<Functieadres> functies) {
        mogelijkeFuncties = new HashMap<Integer, Functieadres>();
        for (Functieadres functie : functies) {
            mogelijkeFuncties.put((int) functie.getId(), functie);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkeFuncties.get(Integer.parseInt(text)));
    }
 
    @Override
    public String getAsText() {
        Functieadres functie = (Functieadres) getValue();
        if (functie == null) {
            return null;
        } else {
            return Integer.toString(functie.getId());
        }
    }

}
