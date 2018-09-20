/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.List;

import nl.bzk.brp.generator.java.GeneratorUtils;
import org.apache.commons.collections.list.TreeList;


/**
 * De representatie van de enum waarde zoals die door een Java generator kan worden gebruikt.
 */
public class EnumValue {

    /** De naam van de waarde. */
    private String       name;

    /** De code voor de waarde. */
    private String       code;

    /** De javadoc voor de waarde */
    private String      javaDoc;

    /** De lijst van initialisatie parameters. */
    private List<String> parameters;

    public EnumValue(String naam) {
        this.name = naam;
        parameters = new TreeList();
    }

    public EnumValue(String naam, String enumCode) {
        this.name = naam;
        this.code = enumCode;
        parameters = new TreeList();
    }

    /**
     * Geeft de naam van de enum waarde in hoofdletters terug.
     * 
     * @return the naam
     */
    public String getName() {
        return name;
    }

    /**
     * Geeft de naam van de enum waarde in hoofdletters terug.
     * Hierbij worden spaties omgezet in _ en , vervangen met niets om valide java enumeratie codes te krijgen.
     * 
     * @return the naam
     */
    public String getNameCode() {
        return GeneratorUtils.toValidEnumName(name.toUpperCase());
    }

    /**
     * Zet de naam van de waarde.
     * 
     * @param name the new name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Geeft de lijst van parameters voor initialisatie van deze enum waarde.
     * 
     * @return the parameters
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Zet de lijst van parameters voor initialisatie van deze enum waarde.
     * 
     * @param parameters the new parameters
     */
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(String parameter) {
        this.parameters.add(parameter);
    }

    public String getCode() {
        return code.toUpperCase();
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getJavaDoc() {
        return javaDoc;
    }

    public void setJavaDoc(String javaDoc) {
        this.javaDoc = javaDoc;
    }

}
