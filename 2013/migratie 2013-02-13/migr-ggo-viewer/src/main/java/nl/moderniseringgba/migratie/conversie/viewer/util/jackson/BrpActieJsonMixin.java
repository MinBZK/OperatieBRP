/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.util.jackson;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * De @JsonIdentityInfo annotatie kan gebruikt worden om recursie te voorkomen. Bij de 2e keer dat we dezelfde BrpActie
 * tegenkomen (dit gebeurt doorgaans bij de BrpStapel<BrpDocumentInhoud>), wordt deze alleen genoemd bij zijn ID in
 * plaats van hem recursief uit te schrijven. Dat is prima voor ons, we zijn toch niet geinteresseerd in de 2e
 * verwijzing.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public abstract class BrpActieJsonMixin {
    /**
     * De oorspronkelijke ID is niet noodzakelijk voor de Viewer, maar wel hinderlijk in vergelijkingen, want deze
     * verandert per run.
     * 
     * @return niks dus.
     */
    @JsonIgnore
    public abstract long getId();

}
