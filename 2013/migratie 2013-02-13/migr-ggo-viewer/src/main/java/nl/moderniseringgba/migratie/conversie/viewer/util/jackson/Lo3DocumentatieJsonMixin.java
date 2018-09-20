/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Deze class vertelt Jackson wat het mag negeren van de bijbehorende originele class, Lo3Documentatie.
 */

public abstract class Lo3DocumentatieJsonMixin {

    /**
     * De ID is niet noodzakelijk voor de Viewer, maar wel hinderlijk in vergelijkingen, want deze verandert per run.
     * 
     * @return niks dus.
     */
    @JsonIgnore
    public abstract long getId();

    /**
     * isAkte en isDocument veranderen steeds van volgorde in de gegenereerde JSON. We hebben deze velden niet nodig.
     * 
     * @return niks dus.
     */
    @JsonIgnore
    public abstract boolean isAkte();

    /**
     * isAkte en isDocument veranderen steeds van volgorde in de gegenereerde JSON. We hebben deze velden niet nodig.
     * 
     * @return niks dus.
     */
    @JsonIgnore
    public abstract boolean isDocument();
}
