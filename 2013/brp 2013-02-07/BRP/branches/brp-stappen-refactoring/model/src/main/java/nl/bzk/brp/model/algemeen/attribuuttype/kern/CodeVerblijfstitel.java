/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractCodeVerblijfstitel;


/**
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.AttribuutTypenGenerator.
 * Metaregister versie: 1.4.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-20 07:32:27.
 * Gegenereerd op: Thu Dec 20 07:36:43 CET 2012.
 */
@Embeddable
public class CodeVerblijfstitel extends AbstractCodeVerblijfstitel {

    /**
     * Lege (value-object) constructor voor CodeVerblijfstitel, niet gedeclareerd als public om te voorkomen dat
     * objecten
     * zonder waarde worden geïnstantieerd.
     * 
     */
    private CodeVerblijfstitel() {
        super();
    }

    /**
     * Constructor voor CodeVerblijfstitel die de waarde toekent aan het (value-)object.
     * 
     * @param waarde De waarde voor dit value-object.
     */
    public CodeVerblijfstitel(final Short waarde) {
        super(waarde);
    }

}
