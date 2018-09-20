/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java.domein;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Het generatie rapport.
 */
public class GenerationReport {

    /** Het type object dat gegenereerd is, het onderwerp van het generatie rapport.*/
    private String objectType; 
    
    /** De succesvol gegenereerde objecten. */
    private Map<String, String> generatedObjects = new LinkedHashMap<String, String>();

    /** De fouten, als key de objectnaam en de foutmelding als value. */
    private Map<String, String> errors = new LinkedHashMap<String, String>();

    /**
     * Voeg een succesvolle generatie toe.
     *
     * @param objectName de object naam
     * @param sourceCode de gegenereerde code
     */
    public void addSucccess(String objectName, String sourceCode) {
        generatedObjects.put(objectName, sourceCode);
    }
    

    /**
     * Voeg een foutmelding toe bij het genereren van een object.
     *
     * @param objectName de object naam
     * @param errorMessage the error message
     */
    public void addFailure(String objectName, String errorMessage) {
        errors.put(objectName, errorMessage);
    }

    /**
     * Geef de succesvol gegenereerde objecten terug.
     *
     * @return de gegenereerde objecten
     */
    public Map<String, String> getGeneratedObjects() {
        return generatedObjects;
    }

    /**
     * Geef alle fouten terug.
     *
     * @return the errors
     */
    public Map<String, String> getErrors() {
        return errors;
    }


    
    public String getObjectType() {
        return objectType;
    }


    
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

}
