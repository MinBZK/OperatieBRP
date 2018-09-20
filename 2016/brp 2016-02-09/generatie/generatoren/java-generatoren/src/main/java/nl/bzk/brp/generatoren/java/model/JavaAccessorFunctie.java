/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;

/** Deze class stelt een java accessor functie voor. Oftewel een getter. */
public class JavaAccessorFunctie extends JavaFunctie implements Cloneable {

    /** De klasse variabele naam waar deze accessor toegang tot geeft. */
    private String accessingClassVariabeleNaam;

    /**
     * Standaard constructor die een getter methode aanmaakt voor de opgegeven variabele van het opgegeven type.
     *
     * @param variabeleNaam de naam van de variabele waarvoor de getter gemaakt dient te worden.
     * @param type het type van de variabele.
     */
    public JavaAccessorFunctie(final String variabeleNaam, final JavaType type) {
        this(JavaAccessModifier.PUBLIC,
            String.format("get%s", GeneratieUtil.upperTheFirstCharacter(variabeleNaam)),
            GeneratieUtil.lowerTheFirstCharacter(variabeleNaam));
        this.setReturnType(type);
    }

    /**
     * Constructor die een getter methode aanmaakt voor een {@link JavaVeld}.
     *
     * @param veld het veld waarvoor een getter methode dient te worden geinstantieerd.
     */
    public JavaAccessorFunctie(final JavaVeld veld) {
        this(JavaAccessModifier.PUBLIC,
            String.format("get%s", GeneratieUtil.upperTheFirstCharacter(veld.getNaam())), veld.getNaam());
        this.setReturnType(veld.getType());
    }

    /**
     * Constructor.
     *
     * @param accessModifier Access modifier voor deze accessor.
     * @param naam Naam van de accessor functie.
     * @param accessingClassVariabeleNaam De klasse variabele naam waar deze accessor toegang tot geeft.
     */
    public JavaAccessorFunctie(final JavaAccessModifier accessModifier,
        final String naam,
        final String accessingClassVariabeleNaam)
    {
        super(accessModifier, naam);
        this.accessingClassVariabeleNaam = accessingClassVariabeleNaam;
    }

    /**
     * Constructor.
     *
     * @param naam Naam van de accessor functie.
     * @param returnType Return type van deze accessor functie.
     * @param returnwaardeJavaDoc Javadocs voor de return waarde.
     */
    public JavaAccessorFunctie(final String naam,
        final JavaType returnType,
        final String returnwaardeJavaDoc)
    {
        this(naam, returnType);
        setReturnWaardeJavaDoc(returnwaardeJavaDoc);
    }

    /**
     * Retourneert de klasse variabele naam waar deze accessor toegang tot geeft.
     *
     * @return Klasse variabele naam.
     */
    public String getAccessingClassVariabeleNaam() {
        return accessingClassVariabeleNaam;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final JavaAccessorFunctie clone = (JavaAccessorFunctie) super.clone();
        clone.accessingClassVariabeleNaam = this.accessingClassVariabeleNaam;
        return clone;
    }

}
