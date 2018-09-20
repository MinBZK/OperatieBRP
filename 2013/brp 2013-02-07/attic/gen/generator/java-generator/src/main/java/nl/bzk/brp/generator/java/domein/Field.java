/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

/**
 *
 */
package nl.bzk.brp.generator.java.domein;

import java.util.ArrayList;
import java.util.List;


/**
 * Representatie van een attribuut voor generatie toepassingen.
 *
 */
public class Field {

    /** De scope van het attribuut (public/private/protected...) */
    private String       scope       = "private";

    /** De naam van het attribuut */
    private Identifier   name;

    /** Het type van het attribuut */
    private String       type;

    private String       initializer;

    /** De documentatie. */
    private String       javaDoc;

    private List<String> annotations = new ArrayList<String>();

    public Field(final String javaType, final String fieldNaam) {
        this.type = javaType;
        this.name = new Identifier(fieldNaam);
    }

    /**
     * Gets the java doc.
     *
     * @return the java doc
     */
    public String getJavaDoc() {
        return javaDoc;
    }

    /**
     * @return the scope
     */
    public String getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(final String scope) {
        this.scope = scope;
    }

    /**
     * @return de naam.
     */
    public Identifier getName() {
        return name;
    }

    /**
     * @return de naam.
     */
    public Identifier getNameLowerCamel() {
        return name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    public String getInitializer() {
        return initializer;
    }

    public void setInitializer(final String format, final Object... args) {
        this.initializer = " = " + String.format(format, args);
    }

    /**
     * Gets the annotations.
     *
     * @return the annotations
     */
    public List<String> getAnnotations() {
        return annotations;
    }

    /**
     * Adds the annotation as a formatted string
     *
     * @param annotation the annotation format
     * @param args the format parameters
     */
    public void addAnnotation(final String format, final Object... args) {
        this.annotations.add(String.format(format, args));
    }

    public void setJavaDoc(final String javaDocForObject) {
        this.javaDoc = javaDocForObject;
    }

}
