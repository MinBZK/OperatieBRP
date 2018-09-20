/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;


public class ObjectClass extends ObjectInterface {

    private List<Field>       fields       = new ArrayList<Field>();
    private List<Identifier>  interfaces   = new ArrayList<Identifier>();
    private List<Identifier>  annotations   = new ArrayList<Identifier>();

    private List<Constructor> constructors = new ArrayList<Constructor>();

    public ObjectClass(final String naam, final String documentatie) {
        super(naam, documentatie);
        bInterface = false;
    }

    public void setExtendsFrom(final Character soortInhoud) {
        if (soortInhoud.equals('D')) {
            super.setExtendsFrom("AbstractDynamischObjectType");
        } else if (soortInhoud.equals('S')) {
            super.setExtendsFrom("AbstractStatischObjectType");
        } else {
            super.setExtendsFrom(null);
        }
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    public boolean addConstructor(final Constructor constructor) {
        return constructors.add(constructor);
    }

    public boolean addAttribuut(final Field field) {
        return fields.add(field);
    }

    public List<Identifier> getInterfaces() {
        return interfaces;
    }

    public boolean addInterface(final String interfaceDefinition) {
        return interfaces.add(new Identifier(interfaceDefinition));
    }

    public List<Identifier> getAnnotations() {
        return annotations;
    }

    public boolean addAnnotation(final String annotation) {
        return interfaces.add(new Identifier(annotation));
    }

}
