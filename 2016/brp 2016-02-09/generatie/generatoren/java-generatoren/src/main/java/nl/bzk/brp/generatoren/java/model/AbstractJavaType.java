/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.generatoren.java.model.annotatie.JavaAnnotatie;
import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;

import org.apache.commons.lang.StringUtils;

/** Een java OO concept is bijvoorbeeld een interface of een klass. */
public abstract class AbstractJavaType extends AbstractJavaConstruct implements Cloneable {

    private String  packagePad;
    private Set<JavaType>       extraImportClasses = new HashSet<>();
    private List<JavaFunctie>   functies           = new ArrayList<>();
    private List<JavaType>      superInterfaces    = new ArrayList<>();
    private List<JavaAnnotatie> annotaties         = new ArrayList<>();

    private String handmatigeCodeBovenin;
    private String handmatigeCodeOnderin;

    /**
     * Dit is de super constructor voor elk Java OO Concept zoals een interface of klasse.
     *
     * @param naam Naam van de klasse of interface.
     * @param javaDoc De java doc van de klasse of interface.
     * @param packagePad De package waaring de klasse of interface thuis hoort.
     */
    protected AbstractJavaType(final String naam, final String javaDoc, final String packagePad) {
        super(naam, javaDoc);
        this.packagePad = packagePad;
    }

    public final JavaType getType() {
        return new JavaType(getNaam(), getPackagePad());
    }

    /**
     * Voegt een extra import classes toe aan dit java type.
     * Let op! Gebruik deze methode alleen als er niet gebruik gemaakt kan worden van een
     * JavaType object, bijvoorbeeld bij een annotatie parameter.
     *
     * @param javaTypes de java types
     */
    public final void voegExtraImportsToe(final JavaType ... javaTypes) {
        Collections.addAll(extraImportClasses, javaTypes);
    }

    /** Verwijder alle extra import classes. */
    public final void maakExtraImportsLeeg() {
        this.extraImportClasses.clear();
    }

    /**
     * Geef de imports voor dit type terug zoals ze toegevoegd moeten worden in de .java file.
     * Wordt opgebouwd uit de afgeleide imports en handmatig toegevoegde extra imports.
     *
     * @return de imports
     */
    public final List<String> getImports() {
        return JavaGeneratieUtil.maakGesorteerdeImportStatements(this, getAfgeleideImportClasses());
    }

    /**
     * Voegt van alle gebruikte types de fully qualified classname toe aan de result set,
     * waarmee meteen de dubbelen eruit gefiltert worden.
     *
     * @return de set met import classes.
     */
    private Set<String> getAfgeleideImportClasses() {
        final Set<String> afgeleideImportClasses = new HashSet<>();
        for (JavaType javaType : this.getGebruikteTypes()) {
            // Neem geen imports op voor een leeg package pad.
            // In een uitzonderlijk geval is een package niet bekend,
            // maar is er wel zekerheid dat de import aanwezig is.
            // Dit komt tevens van pas bij basistypen (int, byte[], etc),
            // die ook 'null' als package gebruiken.
            if (StringUtils.isNotBlank(javaType.getPackagePad())
                    && !JavaType.DUMMY_PACKAGE.equals(javaType.getPackagePad()))
            {
                afgeleideImportClasses.add(javaType.getFullyQualifiedClassName());
            }
        }
        return afgeleideImportClasses;
    }

    /**
     * Geef de gebruikte types terug. Deze moeten worden verzamelt aan de hand van de types
     * in supertypes, velden, functies, annotaties, etc.
     * NB: Vergeet bij het overriden niet de super.getGebruikteTypes() ook mee te nemen!
     *
     * @return de gebruikte types
     */
    public List<JavaType> getGebruikteTypes() {
        final List<JavaType> gebruikteTypes = new ArrayList<>();

        for (JavaAnnotatie annotatie : annotaties) {
            gebruikteTypes.addAll(annotatie.getGebruikteTypes());
        }

        for (JavaType superInterface : this.superInterfaces) {
            gebruikteTypes.addAll(superInterface.getGebruikteTypes());
        }
        for (JavaFunctie functie : this.functies) {
            gebruikteTypes.addAll(functie.getGebruikteTypes());
        }
        for (JavaType extraImportClass : this.extraImportClasses) {
            gebruikteTypes.add(extraImportClass);
        }
        return gebruikteTypes;
    }

    /**
     * Voegt een functie toe aan deze java interface of class.
     *
     * @param functie De toe te voegen functie.
     */
    public final void voegFunctieToe(final JavaFunctie functie) {
        functies.add(functie);
    }

    public final List<JavaFunctie> getFuncties() {
        return functies;
    }

    public final String getPackagePad() {
        return packagePad;
    }

    public final void setPackagePad(final String packagePad) {
        this.packagePad = packagePad;
    }

    public final List<JavaType> getSuperInterfaces() {
        return superInterfaces;
    }

    /**
     * Voegt een super interface toe aan deze Java interface, deze interface zal dus een uitbreiding zijn op de
     * super interface(s).
     *
     * @param interfaceDefinition De super interface.
     */
    public final void voegSuperInterfaceToe(final JavaType interfaceDefinition) {
        superInterfaces.add(interfaceDefinition);
    }

    public final String getHandmatigeCodeBovenin() {
        return handmatigeCodeBovenin;
    }

    public final void setHandmatigeCodeBovenin(final String handmatigeCodeBovenin) {
        this.handmatigeCodeBovenin = handmatigeCodeBovenin;
    }

    public final String getHandmatigeCodeOnderin() {
        return handmatigeCodeOnderin;
    }

    public final void setHandmatigeCodeOnderin(final String handmatigeCodeOnderin) {
        this.handmatigeCodeOnderin = handmatigeCodeOnderin;
    }

    public final List<JavaAnnotatie> getAnnotaties() {
        return annotaties;
    }

    public final void setAnnotaties(final List<JavaAnnotatie> annotaties) {
        this.annotaties = annotaties;
    }

    /**
     * Voegt een annotatie toe aan deze java klasse.
     *
     * @param annotatie De toe te voegen annotatie.
     */
    public final void voegAnnotatieToe(final JavaAnnotatie annotatie) {
        annotaties.add(annotatie);
    }

    @Override
    public final String toString() {
        return this.packagePad + "." + this.getNaam();
    }

    @Override
    // De cast na de clone() is nodig en veilig.
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        final AbstractJavaType clone = (AbstractJavaType) super.clone();
        clone.packagePad = packagePad;
        clone.extraImportClasses = (Set<JavaType>) ((HashSet<JavaType>) this.extraImportClasses).clone();
        clone.functies = (List<JavaFunctie>) ((ArrayList<JavaFunctie>) functies).clone();
        clone.superInterfaces = (List<JavaType>) ((ArrayList<JavaType>) superInterfaces).clone();
        clone.annotaties = (ArrayList<JavaAnnotatie>) ((ArrayList<JavaAnnotatie>) this.annotaties).clone();
        return clone;
    }
}
