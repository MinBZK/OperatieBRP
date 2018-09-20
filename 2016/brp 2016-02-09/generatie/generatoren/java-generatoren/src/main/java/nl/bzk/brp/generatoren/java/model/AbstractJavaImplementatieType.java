/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.bzk.brp.generatoren.algemeen.basis.GeneratorExceptie;
import nl.bzk.brp.generatoren.algemeen.common.GeneratieUtil;
import nl.bzk.brp.generatoren.java.naamgeving.NaamgevingStrategie;
import nl.bzk.brp.metaregister.model.GeneriekElement;

/** Een java type met een implementatie (dus geen interface). */
public abstract class AbstractJavaImplementatieType extends AbstractJavaType {

    private SortedSet<JavaVeld>       velden        = new TreeSet<>(new JavaVeldenComparator());
    private List<Constructor>         constructoren = new ArrayList<>();
    private List<JavaAccessorFunctie> getters       = new ArrayList<>();
    private List<JavaMutatorFunctie>  setters       = new ArrayList<>();

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

    public Set<JavaVeld> getVelden() {
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

    /**
     * Voegt een veld toe aan deze java klasse voor het opgegeven {@link GeneriekElement} en conform de opgegeven
     * {@link NaamgevingStrategie}. Daarnaast retourneert deze methode het veld dat wordt toegevoegd.
     *
     * @param element het generieke element waarvoor een veld wordt aangemaakt.
     * @param naamgevingStrategie de naamgeving strategie die gebruikt moet worden voor het veld.
     * @param isFinalVeld of het veld final moet zijn of niet.
     * @return het nieuwe, toegevoegde veld.
     */
    public JavaVeld voegVeldToeVoorElement(final GeneriekElement element, final NaamgevingStrategie naamgevingStrategie,
        final boolean isFinalVeld)
    {
        final JavaVeld javaVeld = new JavaVeld(naamgevingStrategie.getJavaTypeVoorElement(element),
            GeneratieUtil.lowerTheFirstCharacter(naamgevingStrategie.getJavaTypeVoorElement(element).getNaam()));
        javaVeld.setFinal(isFinalVeld);
        velden.add(javaVeld);
        return javaVeld;
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

        for (JavaVeld veld : this.velden) {
            gebruikteTypes.addAll(veld.getGebruikteTypes());
        }

        // Constructoren, getters en setters kunnen we op dezelfde manier aflopen (als een functie).
        List<JavaFunctie> alleFuncties = new ArrayList<>();
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
        clone.velden = (TreeSet<JavaVeld>) ((TreeSet<JavaVeld>) this.velden).clone();

        clone.constructoren = new ArrayList<>();
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
