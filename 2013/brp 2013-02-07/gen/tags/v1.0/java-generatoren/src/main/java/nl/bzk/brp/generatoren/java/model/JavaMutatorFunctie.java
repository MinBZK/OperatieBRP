/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;

/** Deze class stelt een java mutator functie voor. Oftewel een setter. */
public class JavaMutatorFunctie extends JavaFunctie implements Cloneable {

    /** De klasse variabele naam waar deze mutator toegang tot geeft. */
    private String mutatingClassVariabeleNaam;

    /**
     * Standaard constructor die een setter methode aanmaakt voor de opgegeven variabele van het opgegeven type.
     *
     * @param variabeleNaam de naam van de variabele waarvoor de setter gemaakt dient te worden.
     * @param type het type van de variabele.
     */
    public JavaMutatorFunctie(final String variabeleNaam, final JavaType type) {
        this(JavaAccessModifier.PUBLIC,
            String.format("set%s", GeneratieUtil.upperTheFirstCharacter(variabeleNaam)),
            GeneratieUtil.lowerTheFirstCharacter(variabeleNaam));
        this.voegParameterToe(
            new JavaFunctieParameter(variabeleNaam, type));
    }

    /**
     * Constructor die een setter methode aanmaakt voor een {@link JavaVeld}.
     *
     * @param veld het veld waarvoor een setter methode dient te worden geinstantieerd.
     */
    public JavaMutatorFunctie(final JavaVeld veld) {
        this(JavaAccessModifier.PUBLIC,
            String.format("set%s", GeneratieUtil.upperTheFirstCharacter(veld.getNaam())), veld.getNaam());
        this.voegParameterToe(new JavaFunctieParameter(veld.getNaam(), veld.getType()));
    }

    /**
     * Constructor.
     *
     * @param accessModifier Access modifier voor deze mutator.
     * @param naam Naam van de mutator functie.
     * @param mutatingClassVariabeleNaam De klasse variabele naam waar deze mutator toegang tot geeft.
     */
    public JavaMutatorFunctie(final JavaAccessModifier accessModifier,
        final String naam,
        final String mutatingClassVariabeleNaam)
    {
        super(accessModifier, naam);
        this.mutatingClassVariabeleNaam = mutatingClassVariabeleNaam;
    }

    /**
     * Retourneert de klasse variabele naam waar deze mutator toegang tot geeft.
     *
     * @return Klasse variabele naam.
     */
    public String getMutatingClassVariabeleNaam() {
        return mutatingClassVariabeleNaam;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final JavaMutatorFunctie clone = (JavaMutatorFunctie) super.clone();
        clone.mutatingClassVariabeleNaam = this.mutatingClassVariabeleNaam;
        return clone;
    }
}
