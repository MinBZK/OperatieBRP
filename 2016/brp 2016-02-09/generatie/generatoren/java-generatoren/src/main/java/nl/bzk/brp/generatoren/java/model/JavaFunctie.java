/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;

/**
 * Klasse die een Java functie voorstelt.
 */
public class JavaFunctie extends AbstractJavaConstruct implements Cloneable {

    private JavaAccessModifier accessModifier;
    private boolean isStatic;
    private boolean isAbstract;
    private boolean isFinal;
    private JavaType returnType;
    // Om af te dwingen dat je eigen javadoc wilt wegschrijven, ondanks een @Override
    private String returnWaardeJavaDoc;
    private boolean forceerJavaDoc;
    private String body;
    private List<JavaAnnotatie> annotaties = new ArrayList<>();
    private List<String> thrownExceptions = new ArrayList<>();
    private List<JavaFunctieParameter> parameters = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param accessModifier De access modifier voor deze functie.
     * @param naam           Naam van de functie.
     */
    public JavaFunctie(final JavaAccessModifier accessModifier, final String naam) {
        this(accessModifier, JavaType.VOID, naam, "");
    }

    /**
     * Constructor.
     *
     * @param accessModifier      De access modifier voor deze functie.
     * @param returnType          Het type object wat de java functie retourneert.
     * @param naam                Naam van de functie.
     * @param returnWaardeJavaDoc Javadocs voor de return waarde.
     */
    public JavaFunctie(final JavaAccessModifier accessModifier, final JavaType returnType, final String naam,
                       final String returnWaardeJavaDoc)
    {
        this(accessModifier, returnType, naam, returnWaardeJavaDoc, false);
    }

    /**
     * Constructor.
     *
     * @param accessModifier      De access modifier voor deze functie.
     * @param returnType          Het type object wat de java functie retourneert.
     * @param naam                Naam van de functie.
     * @param returnWaardeJavaDoc Javadocs voor de return waarde.
     * @param isStatic            geeft aan of de methode static is of niet.
     */
    public JavaFunctie(final JavaAccessModifier accessModifier, final JavaType returnType, final String naam,
                       final String returnWaardeJavaDoc, final boolean isStatic)
    {
        super(naam, "");
        this.accessModifier = accessModifier;
        this.returnType = returnType;
        this.returnWaardeJavaDoc = returnWaardeJavaDoc;
        this.forceerJavaDoc = false;
        this.isStatic = isStatic;
        this.body = "";
    }

    @Override
    // De cast na de clone() is nodig en veilig.
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        final JavaFunctie clone = (JavaFunctie) super.clone();
        clone.accessModifier = accessModifier;
        clone.returnType = returnType;
        clone.returnWaardeJavaDoc = returnWaardeJavaDoc;
        clone.body = body;
        clone.annotaties = (ArrayList<JavaAnnotatie>) ((ArrayList<JavaAnnotatie>) this.annotaties).clone();
        clone.parameters =
                (ArrayList<JavaFunctieParameter>) ((ArrayList<JavaFunctieParameter>) this.parameters).clone();
        clone.isStatic = isStatic;
        clone.isAbstract = isAbstract;
        clone.isFinal = isFinal;
        return clone;
    }


    public final JavaType getReturnType() {
        return returnType;
    }

    public final void setReturnType(final JavaType returnType) {
        this.returnType = returnType;
    }

    /**
     * Geeft aan of deze functie een return waarde heeft.
     *
     * @return True indien de functie een return waarde heeft, anders false.
     */
    public final boolean hasReturnValue() {
        return !returnType.getNaam().equals(JavaType.VOID.getNaam());
    }

    public final String getReturnWaardeJavaDoc() {
        return returnWaardeJavaDoc;
    }

    public final void setReturnWaardeJavaDoc(final String returnWaardeJavaDoc) {
        this.returnWaardeJavaDoc = returnWaardeJavaDoc;
    }

    public boolean isForceerJavaDoc() {
        return forceerJavaDoc;
    }

    public void setForceerJavaDoc(final boolean forceerJavaDoc) {
        this.forceerJavaDoc = forceerJavaDoc;
    }

    public final String getBody() {
        return body;
    }

    public final void setBody(final String body) {
        this.body = body;
    }

    public final List<JavaAnnotatie> getAnnotaties() {
        return annotaties;
    }

    public final void setAnnotaties(final List<JavaAnnotatie> annotaties) {
        this.annotaties = annotaties;
    }

    /**
     * Voegt een annotatie toe aan deze Java-functie.
     *
     * @param annotatie De toe te voegen annotatie.
     */
    public final void voegAnnotatieToe(final JavaAnnotatie annotatie) {
        annotaties.add(annotatie);
    }

    public final List<String> getThrownExceptions() {
        return thrownExceptions;
    }

    public final void setThrownExceptions(final List<String> thrownExceptions) {
        this.thrownExceptions = thrownExceptions;
    }

    /**
     * Geeft aan of deze functie excepties gooit.
     *
     * @return True indien functie excepties gooit, anders false.
     */
    public final boolean hasExceptions() {
        return thrownExceptions.size() > 0;
    }

    /**
     * Voegt een exception toe aan de Java-functie.
     *
     * @param exception De toe te voegen exception.
     */
    public final void voegThrownExceptionToe(final String exception) {
        thrownExceptions.add(exception);
    }

    public final List<JavaFunctieParameter> getParameters() {
        return parameters;
    }

    /**
     * Voegt een parameter toe aan deze functie.
     *
     * @param parameter De toe te voegen parameter.
     */
    public final void voegParameterToe(final JavaFunctieParameter parameter) {
        this.parameters.add(parameter);
    }

    public final void setAccessModifier(final JavaAccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    public final JavaAccessModifier getAccessModifier() {
        return accessModifier;
    }

    public final boolean isOverriden() {
        return !annotaties.isEmpty() && annotaties.contains(new JavaAnnotatie(JavaType.OVERRIDE));
    }

    /**
     * Geeft de gebruikte types van deze functie terug.
     *
     * @return de gebruikte types
     */
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<>();
        if (!this.returnType.equals(JavaType.VOID) && !this.returnType.equals(JavaType.BOOLEAN_PRIMITIVE)) {
            gebruikteTypes.addAll(this.returnType.getGebruikteTypes());
        }
        for (JavaAnnotatie annotatie : this.annotaties) {
            gebruikteTypes.addAll(annotatie.getGebruikteTypes());
        }
        for (JavaFunctieParameter parameter : this.parameters) {
            gebruikteTypes.add(parameter.getJavaType());
        }
        return gebruikteTypes;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(final boolean aStatic) {
        isStatic = aStatic;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(final boolean anAbstract) {
        isAbstract = anAbstract;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(final boolean aFinal) {
        isFinal = aFinal;
    }
}
