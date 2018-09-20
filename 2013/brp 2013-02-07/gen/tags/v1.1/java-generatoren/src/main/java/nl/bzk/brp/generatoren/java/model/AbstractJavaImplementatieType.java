/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;

/** Een java type met een implementatie (dus geen interface). */
public abstract class AbstractJavaImplementatieType extends AbstractJavaType {

    private List<JavaAnnotatie>       annotaties    = new ArrayList<JavaAnnotatie>();
    private List<JavaVeld>            velden        = new ArrayList<JavaVeld>();
    private List<Constructor>         constructoren = new ArrayList<Constructor>();
    private List<JavaAccessorFunctie> getters       = new ArrayList<JavaAccessorFunctie>();
    private List<JavaMutatorFunctie>  setters       = new ArrayList<JavaMutatorFunctie>();

    /**
     * Constructor.
     *
     * @param naam naam van dit type.
     * @param javaDoc documentatie
     * @param packageNaam package naam.
     */
    public AbstractJavaImplementatieType(final String naam, final String javaDoc, final String packageNaam) {
        super(naam, javaDoc, packageNaam);
    }

    public List<JavaAccessorFunctie> getGetters() {
        return getters;
    }

    /**
     * Voegt een accessor functie toe aan deze interface of class.
     *
     * @param accessor De toe te voegen accessor.
     */
    public void voegGetterToe(final JavaAccessorFunctie accessor) {
        getters.add(accessor);
    }

    /**
     * Voegt een accessor functie toe aan deze interface of class.
     *
     * @param mutator De toe te voegen mutator.
     */
    public void voegSetterToe(final JavaMutatorFunctie mutator) {
        setters.add(mutator);
    }

    public List<JavaMutatorFunctie> getSetters() {
        return setters;
    }

    public List<JavaVeld> getVelden() {
        return velden;
    }

    public List<Constructor> getConstructoren() {
        return constructoren;
    }

    /**
     * Retourneert de eerste constructor uit de lijst van constructoren.
     *
     * @return de (eerste) constructor.
     */
    public Constructor getConstructor() {
        return constructoren.get(0);
    }

    /**
     * Voegt een constructor toe aan deze klasse.
     *
     * @param constructor De toe te voegen constructor.
     */
    public void voegConstructorToe(final Constructor constructor) {
        constructoren.add(constructor);
    }

    /**
     * Voegt een veld toe aan deze java klasse.
     *
     * @param veld Toe te voegen veld.
     */
    public void voegVeldToe(final JavaVeld veld) {
        velden.add(veld);
    }

    public List<JavaAnnotatie> getAnnotaties() {
        return annotaties;
    }

    public void setAnnotaties(final List<JavaAnnotatie> annotaties) {
        this.annotaties = annotaties;
    }

    /**
     * Voegt een annotatie toe aan deze java klasse.
     *
     * @param annotatie De toe te voegen annotatie.
     */
    public void voegAnnotatieToe(final JavaAnnotatie annotatie) {
        annotaties.add(annotatie);
    }

    /**
     * Bepaalt of dit java type al een dergelijke constructor heeft.
     * De vergelijking is op basis van het aantal parameters en de types van die parameters.
     *
     * @param eenConstructor een constructor
     * @return true als er al een dergelijk is, anders false
     */
    public boolean heeftConstructor(final Constructor eenConstructor) {
        boolean heeftConstructor = false;
        for (Constructor constructor : this.constructoren) {
            boolean evenveelParameters = constructor.getParameters().size() == eenConstructor.getParameters().size();
            if (evenveelParameters) {
                boolean alleParametersZelfdeType = true;
                for (int i = 0; i < constructor.getParameters().size(); i++) {
                    alleParametersZelfdeType = alleParametersZelfdeType
                            && constructor.getParameters().get(i).getJavaType().equals(
                                    eenConstructor.getParameters().get(i).getJavaType());
                }
                heeftConstructor = alleParametersZelfdeType;
            }
        }
        return heeftConstructor;
    }


    @Override
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = super.getGebruikteTypes();

        for (JavaAnnotatie annotatie : this.annotaties) {
            gebruikteTypes.addAll(annotatie.getGebruikteTypes());
        }

        for (JavaVeld veld : this.velden) {
            gebruikteTypes.addAll(veld.getGebruikteTypes());
        }

        // Constructoren, getters en setters kunnen we op dezelfde manier aflopen (als een functie).
        List<JavaFunctie> alleFuncties = new ArrayList<JavaFunctie>();
        alleFuncties.addAll(this.constructoren);
        alleFuncties.addAll(this.getters);
        alleFuncties.addAll(this.setters);

        for (JavaFunctie functie : alleFuncties) {
            gebruikteTypes.addAll(functie.getGebruikteTypes());
        }

        return gebruikteTypes;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        final AbstractJavaImplementatieType clone = (AbstractJavaImplementatieType) super.clone();
        clone.getters = (ArrayList<JavaAccessorFunctie>) ((ArrayList<JavaAccessorFunctie>) this.getters).clone();
        clone.setters = (ArrayList<JavaMutatorFunctie>) ((ArrayList<JavaMutatorFunctie>) this.setters).clone();
        clone.velden = (ArrayList<JavaVeld>) ((ArrayList<JavaVeld>) this.velden).clone();
        clone.annotaties = (ArrayList<JavaAnnotatie>) ((ArrayList<JavaAnnotatie>) this.annotaties).clone();

        clone.constructoren = new ArrayList<Constructor>();
        for (Constructor constructor : getConstructoren()) {
            try {
                clone.constructoren.add((Constructor) constructor.clone());
            } catch (CloneNotSupportedException e) {
                throw new GeneratorExceptie("Clone methode niet supported voor constructor: '" + constructor + "'.", e);
            }
        }

        return clone;
    }

}
