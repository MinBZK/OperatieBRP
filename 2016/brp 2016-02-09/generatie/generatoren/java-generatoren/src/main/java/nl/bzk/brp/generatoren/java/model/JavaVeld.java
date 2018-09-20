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
 * Representatie van een attribuut in een java klasse..
 */
public class JavaVeld extends AbstractJavaConstruct {

    /**
     * De access modifier voor dit veld. De standaard is private omdat de gegenereerde klassen
     * zoveel mogelijk moeten voldoen aan de JavaBeans conventie.
     */
    private JavaAccessModifier accessModifier = JavaAccessModifier.PRIVATE;
    private boolean isFinal = false;
    private boolean isStatic = false;
    private String geinstantieerdeWaarde = null;

    /**
     * Het type van het veld.
     */
    private JavaType type;

    private List<JavaAnnotatie> annotaties = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param javaType Java type voor dit veld.
     * @param veldNaam Naam van dit veld.
     */
    public JavaVeld(final JavaType javaType, final String veldNaam) {
        this(javaType, veldNaam, "");
    }

    /**
     * Constructor.
     *
     * @param javaType Java type voor dit veld.
     * @param veldNaam Naam van dit veld.
     * @param isFinal  Indicator of het veld final moet zijn.
     */
    public JavaVeld(final JavaType javaType, final String veldNaam, final boolean isFinal) {
        this(javaType, veldNaam, "");
        setFinal(isFinal);
    }

    /**
     * Constructor.
     *
     * @param javaType Java type voor dit veld.
     * @param veldNaam Naam van dit veld.
     * @param javaDoc  JavaDoc voor dit veld.
     */
    public JavaVeld(final JavaType javaType, final String veldNaam, final String javaDoc) {
        super(veldNaam, javaDoc);
        this.type = javaType;
    }

    /**
     * @return the scope
     */
    public JavaAccessModifier getAccessModifier() {
        return accessModifier;
    }

    /**
     * @param accessModifier the access modifier to set.
     */
    public void setAccessModifier(final JavaAccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    /**
     * Retourneert of het veld final is of niet.
     *
     * @return of het veld final is of niet.
     */
    public boolean isFinal() {
        return isFinal;
    }

    /**
     * Zet of het veld final is of niet.
     *
     * @param isFinalVeld of het veld final is of niet.
     */
    public void setFinal(final boolean isFinalVeld) {
        this.isFinal = isFinalVeld;
    }

    /**
     * Retourneert of het veld static is of niet.
     *
     * @return of het veld static is of niet.
     */
    public boolean isStatic() {
        return isStatic;
    }

    /**
     * Zet of het veld static is of niet.
     *
     * @param isStaticVeld of het veld static is of niet.
     */
    public void setStatic(final boolean isStaticVeld) {
        this.isStatic = isStaticVeld;
    }

    /**
     * Geeft TRUE als het veld public is; anders FALSE.
     *
     * @return TRUE als het veld public is; anders FALSE.
     */
    public boolean isPublic() {
        return accessModifier == JavaAccessModifier.PUBLIC;
    }

    /**
     * Geeft TRUE als het veld private is; anders FALSE.
     *
     * @return TRUE als het veld private is; anders FALSE.
     */
    public boolean isPrivate() {
        return accessModifier == JavaAccessModifier.PRIVATE;
    }

    /**
     * Geeft TRUE als het veld private is; anders FALSE.
     *
     * @return TRUE als het veld private is; anders FALSE.
     */
    public boolean isProtected() {
        return accessModifier == JavaAccessModifier.PROTECTED;
    }

    /**
     * Geeft TRUE als het veld private is; anders FALSE.
     *
     * @return TRUE als het veld private is; anders FALSE.
     */
    public boolean isPackageLevelAccess() {
        return accessModifier == null;
    }

    /**
     * @return the type.
     */
    public JavaType getType() {
        return type;
    }

    /**
     * @param type the type to set.
     */
    public void setType(final JavaType type) {
        this.type = type;
    }

    /**
     * Retourneert de geinstantieerde (initiele) waarde van dit veld. Als dit attribuut is gezet, betekent dit dat het
     * veld bij declaratie gelijk wordt geinstantieerd naar opgegeven waarde.
     *
     * @return string representatie van de waarde waarnaar het veld in de declaratie naar moet worden geinstantieerd.
     */
    public String getGeinstantieerdeWaarde() {
        return geinstantieerdeWaarde;
    }

    /**
     * Zet de geinstantieerde (initiele) waarde van dit veld. Als dit attribuut is gezet, betekent dit dat het veld
     * bij declaratie gelijk wordt geinstantieerd naar opgegeven waarde.
     *
     * @param waarde string representatie van de waarde waarnaar het veld in de declaratie naar moet worden
     *               geinstantieerd.
     */
    public void setGeinstantieerdeWaarde(final String waarde) {
        this.geinstantieerdeWaarde = waarde;
    }

    /**
     * Boolean indicatie die aangeeft of het veld direct (bij declaratie) moet worden geinstantieerd of niet. Dit is
     * alleen als er ook een waarde is opgegeven.
     *
     * @return of het veld geinstantieerd moet worden bij declaratie of niet.
     */
    public boolean isGeinstantieerd() {
        return geinstantieerdeWaarde != null;
    }

    /**
     * Gets the annotations.
     *
     * @return the annotations
     */
    public List<JavaAnnotatie> getAnnotaties() {
        return annotaties;
    }

    /**
     * Zet de meegegeven lijst met annotaties als property.
     * NB: De eventueel aanwezige annotaties worden dus verwijderd.
     *
     * @param annotaties de nieuwe annotaties
     */
    public void setAnnotaties(final List<JavaAnnotatie> annotaties) {
        this.annotaties.clear();
        this.annotaties.addAll(annotaties);
    }

    /**
     * Voegt een annotatie toe aan dit veld.
     *
     * @param annotatie De toe te voegen annotatie.
     */
    public void voegAnnotatieToe(final JavaAnnotatie annotatie) {
        this.annotaties.add(annotatie);
    }

    /**
     * Bepaalt of dit veld een annotatie heeft van het meegegeven type.
     *
     * @param annotatieType het type
     * @return te ja of te nee
     */
    public boolean heeftAnnotatieVanType(final JavaType annotatieType) {
        boolean annotatieGevonden = false;
        for (JavaAnnotatie javaAnnotatie : this.annotaties) {
            annotatieGevonden = annotatieGevonden || javaAnnotatie.getType().equals(annotatieType);
        }
        return annotatieGevonden;
    }

    /**
     * Retourneert de annotatie van een bepaald type.
     *
     * @param annotatieType te retourneren annotatie type.
     * @return java annotatie van het type indien aanwezig, anders null.
     */
    public JavaAnnotatie getAnnotatieVanType(final JavaType annotatieType) {
        JavaAnnotatie annotatie = null;
        for (JavaAnnotatie javaAnnotatie : this.annotaties) {
            if (javaAnnotatie.getType().equals(annotatieType)) {
                annotatie = javaAnnotatie;
            }
        }
        return annotatie;
    }

    /**
     * Geeft de gebruikte types van dit veld terug.
     *
     * @return de gebruikte types
     */
    public List<JavaType> getGebruikteTypes() {
        List<JavaType> gebruikteTypes = new ArrayList<>();
        gebruikteTypes.addAll(this.type.getGebruikteTypes());
        for (JavaAnnotatie annotatie : this.annotaties) {
            gebruikteTypes.addAll(annotatie.getGebruikteTypes());
        }
        return gebruikteTypes;
    }

}
