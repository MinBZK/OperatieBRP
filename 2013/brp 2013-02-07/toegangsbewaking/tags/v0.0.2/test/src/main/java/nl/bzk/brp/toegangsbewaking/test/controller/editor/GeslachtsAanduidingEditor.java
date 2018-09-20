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

import nl.bzk.brp.toegangsbewaking.test.model.Geslachtsaand;


public class GeslachtsAanduidingEditor extends PropertyEditorSupport {

    private final Map<String, Geslachtsaand> mogelijkeGeslachtsAanduidingen;
    
    public GeslachtsAanduidingEditor(final List<Geslachtsaand> geslachtsAanduidingen) {
        mogelijkeGeslachtsAanduidingen = new HashMap<String, Geslachtsaand>();
        for (Geslachtsaand aand : geslachtsAanduidingen) {
            mogelijkeGeslachtsAanduidingen.put(aand.getCode(), aand);
        }
    }
 
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(mogelijkeGeslachtsAanduidingen.get(text));
    }
 
    @Override
    public String getAsText() {
        Geslachtsaand aand = (Geslachtsaand) getValue();
        if (aand == null) {
            return null;
        } else {
            return aand.getCode();
        }
    }

}
