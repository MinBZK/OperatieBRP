/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern.basis;

import com.fasterxml.jackson.annotation.JsonCreator;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.basis.AbstractGegevensAttribuutType;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.AttribuutTypenGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:49 CET 2013.
 */
@MappedSuperclass
public abstract class AbstractBuitenlandsePlaats extends AbstractGegevensAttribuutType<String> {

    /**
     * Lege (value-object) constructor voor BuitenlandsePlaats, niet gedeclareerd als public om te voorkomen dat
     * objecten
     * zonder waarde worden geïnstantieerd.
     *
     */
    protected AbstractBuitenlandsePlaats() {
        super(null);
    }

    /**
     * Constructor voor BuitenlandsePlaats die de waarde toekent aan het (value-)object.
     *
     * @param waarde De waarde voor dit value-object.
     */
    @JsonCreator
    public AbstractBuitenlandsePlaats(final String waarde) {
        super(waarde);
    }

}
