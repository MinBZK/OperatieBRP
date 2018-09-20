/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.generator.model.dal;

import java.util.Map;
import java.util.TreeMap;

import nl.bzk.brp.ecore.bmr.Type;


public class JavaDomeinModel extends JavaPackage {

    private Map<Integer, AbstractType> types   = new TreeMap<Integer, AbstractType>();
    private Map<String, JavaPackage>   schemas = new TreeMap<String, JavaPackage>();

    public JavaDomeinModel(final String naam) {
        super(naam);
    }

    public JavaPackage getSchema(final String naam) {
        return schemas.get(naam);
    }

    public AbstractType getType(final Type type) {
        return types.get(type.getId());
    }

    public void putSchema(final String naam, final JavaPackage schema) {
        schemas.put(naam, schema);
    }

    public void putType(final Type type, final AbstractType abstractType) {
        types.put(type.getId(), abstractType);
    }

}
