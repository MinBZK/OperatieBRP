/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.generiekmodel.hismapper;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.poc.generiekmodel.BrpAttribuut;
import nl.bzk.brp.poc.generiekmodel.BrpGroep;
import nl.bzk.brp.poc.generiekmodel.BrpGroepVoorkomen;
import nl.bzk.brp.poc.generiekmodel.BrpObject;
import nl.bzk.brp.poc.generiekmodel.ModelVisitor;
import nl.bzk.brp.poc.generiekmodel.bmr.ElementObjectType;

/**
 * Created by dennis on 2/5/16.
 */
class ModelViz implements ModelVisitor {


    private int indent;

    @Override
    public void visit(final BrpObject ot) {

        System.out.printf("%s Object [%d] %s %n", geefIndent(), ot.getObjectsleutel(), ot.getElement());
        for (BrpGroep groep : ot.getGroepen()) {
            indent++;
            groep.visit(this);
            indent--;
        }
        for (Map.Entry<ElementObjectType, Set<BrpObject>> entry : ot.getObjecten().entrySet()) {
            final Set<BrpObject> objectenMetGelijkType = entry.getValue();
            indent++;
            for (BrpObject brpObject : objectenMetGelijkType) {
                brpObject.visit(this);
            }
            indent--;
        }

    }

    @Override
    public void visit(final BrpGroep g) {
        System.out.printf("%s BrpGroep %s %n", geefIndent(), g.getElement());

        for (BrpGroepVoorkomen o : g.getVoorkomens()) {
            o.visit(this);
        }
    }

    @Override
    public void visit(final BrpGroepVoorkomen voorkomen) {
        indent++;
        System.out.printf("%s Voorkomen [id=%d] %n", geefIndent(), voorkomen.getVoorkomensleutel());
        indent--;
        //printAttributen(voorkomen);
    }

    @Override
    public void visit(final BrpAttribuut a) {
        System.out.printf(", %s=%s", a.getElement(), a.getWaarde());
    }

    private void printAttributen(final BrpGroepVoorkomen voorkomen) {
        System.out.printf("[id=%d", voorkomen.getVoorkomensleutel());
        final Collection<BrpAttribuut> values = voorkomen.getAttributen().values();
        for (BrpAttribuut value : values) {
            value.visit(this);
        }
        System.out.println("]");
    }

    private String geefIndent() {
        String indentString = "";

        for (int i = 0 ; i < indent ; i++) {
            indentString += "\t";
        }
        return indentString;

    }
}
