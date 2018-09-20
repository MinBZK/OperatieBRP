/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.java.model;

import java.util.List;

import nl.bzk.brp.generatoren.java.util.JavaGeneratieUtil;

/**
 * Algemene identificeerbare Java-constructie. Een AbstractJavaConstruct is identificeerbaar door middel van een naam en
 * kan worden voorzien van JavaDoc-commentaar.
 */
public abstract class AbstractJavaConstruct implements Cloneable, JavaDocable {

    private String       naam;
    private List<String> javaDoc;

    /**
     * Standaard constructor die de naam van het Java element zet en tevens de javadoc voor het element.
     *
     * @param naam de naam van het Java element.
     * @param javaDocVoorObject de javadoc voor het Java element.
     */
    protected AbstractJavaConstruct(final String naam, final String javaDocVoorObject) {
        this.naam = naam;
        setJavaDoc(javaDocVoorObject);
    }

    /**
     * Geeft de naam van het Java-element.
     *
     * @return de naam van het Java-element.
     */
    public final String getNaam() {
        return naam;
    }

    /**
     * Zet de naam van het Java-element.
     *
     * @param naam de naam van het Java-element.
     */
    public final void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geeft de JavaDoc-documentatie van het Java-element.
     *
     * @return de JavaDoc-documentatie van het Java-element.
     */
    public final List<String> getJavaDoc() {
        return javaDoc;
    }

    /**
     * Zet de JavaDoc-documentatie die de beschrijving geeft van het Java-element.
     *
     * @param javaDocVoorObject de JavaDoc-beschrijving van het object.
     */
    public final void setJavaDoc(final String javaDocVoorObject) {
        javaDoc = JavaGeneratieUtil.genereerJavaDoc(javaDocVoorObject);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        final AbstractJavaConstruct clone = (AbstractJavaConstruct) super.clone();
        clone.naam = naam;
        clone.javaDoc = javaDoc;
        return clone;
    }
}
