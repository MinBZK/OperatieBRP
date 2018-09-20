/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;


/**
 * Een java-functieparameter. Deze kent een naam en een javatype.
 */
public class JavaFunctieParameter extends AbstractJavaConstruct {

    private final JavaType javaType;

    /**
     * Constructor.
     * @param naam De naam van de parameter.
     * @param javaType Javatype voor deze parameter. Dit kan een primitieve type zijn of een andere klasse.
     */
    public JavaFunctieParameter(final String naam, final JavaType javaType) {
        this(naam, javaType, null);
    }

    /**
     * Constructor.
     * @param naam De naam van de parameter.
     * @param javaType Javatype voor deze parameter. Dit kan een primitieve type zijn of een andere klasse.
     * @param javaDoc Javadoc documentatie voor deze parameter.
     */
    public JavaFunctieParameter(final String naam, final JavaType javaType, final String javaDoc) {
        super(naam, javaDoc);
        this.javaType = javaType;
    }

    /**
     * Retourneert het type van de parameter.
     * @return Type.
     */
    public final JavaType getJavaType() {
        return javaType;
    }

}
