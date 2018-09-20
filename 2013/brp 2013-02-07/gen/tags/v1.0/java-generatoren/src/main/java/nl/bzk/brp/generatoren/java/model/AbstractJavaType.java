/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;

/**
 * Een java OO concept is bijvoorbeeld een interface of een klass.
 */
public abstract class AbstractJavaType extends AbstractJavaConstruct implements Cloneable {

    private String packagePad;
    private boolean codeAanpasbaar;
    private Set<String> extraImportClasses = new HashSet<String>();
    private List<JavaFunctie> functies = new ArrayList<JavaFunctie>();
    private List<JavaType> superInterfaces = new ArrayList<JavaType>();

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

    /**
     * Voegt een extra import classes toe aan dit java type.
     * Let op! Gebruik deze methode alleen als er niet gebruik gemaakt kan worden van een
     * JavaType object, bijvoorbeeld bij een annotatie parameter.
     *
     * @param importClass De import class als fully qualified class name.
     */
    public final void voegExtraImportToe(final String importClass) {
        extraImportClasses.add(importClass);
    }

    /**
     * Verwijder alle extra import classes.
     */
    public void maakExtraImportsLeeg() {
        this.extraImportClasses.clear();
    }

    /**
     * Geef de imports voor dit type terug zoals ze toegevoegd moeten worden in de .java file.
     * Wordt opgebouwd uit de afgeleide imports en handmatig toegevoegde extra imports.
     *
     * @return de imports
     */
    public final List<String> getImports() {
        Set<String> alleImports = new HashSet<String>();
        alleImports.addAll(this.getAfgeleideImportClasses());
        alleImports.addAll(this.extraImportClasses);
        return JavaGeneratieUtil.maakImportsCheckstyleStijl(this, alleImports);
    }

    /**
     * Voegt van alle gebruikte types de fully qualified classname toe aan de result set,
     * waarmee meteen de dubbelen eruit gefiltert worden.
     *
     * @return de set met import classes.
     */
    private Set<String> getAfgeleideImportClasses() {
        Set<String> afgeleideImportClasses = new HashSet<String>();
        for (JavaType javaType : this.getGebruikteTypes()) {
            afgeleideImportClasses.add(javaType.getFullyQualifiedClassName());
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
        List<JavaType> gebruikteTypes = new ArrayList<JavaType>();
        for (JavaType superInterface : this.superInterfaces) {
            gebruikteTypes.addAll(superInterface.getGebruikteTypes());
        }
        for (JavaFunctie functie : this.functies) {
            gebruikteTypes.addAll(functie.getGebruikteTypes());
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

    public final void setCodeAanpasbaar(final boolean codeAanpasbaar) {
        this.codeAanpasbaar = codeAanpasbaar;
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
     * @param interfaceDefinition De super interface.
     */
    public final void voegSuperInterfaceToe(final JavaType interfaceDefinition) {
        superInterfaces.add(interfaceDefinition);
    }

    @Override
    public String toString() {
        return this.packagePad + "." + this.getNaam();
    }

    @Override
    // De cast na de clone() is nodig en veilig.
    @SuppressWarnings("unchecked")
    public Object clone() throws CloneNotSupportedException {
        final AbstractJavaType clone = (AbstractJavaType) super.clone();
        clone.packagePad = packagePad;
        clone.codeAanpasbaar = codeAanpasbaar;
        clone.extraImportClasses = (Set<String>) ((HashSet<String>) this.extraImportClasses).clone();
        clone.functies = (List<JavaFunctie>) ((ArrayList<JavaFunctie>) functies).clone();
        clone.superInterfaces = (List<JavaType>) ((ArrayList<JavaType>) superInterfaces).clone();
        return clone;
    }
}
