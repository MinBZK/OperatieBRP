/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.autaut;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.autaut.basis.AbstractProtocolleringsniveauCode;


/**
 * 
 * 
 * Generator: nl.bzk.brp.generatoren.java.AttribuutTypenGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 10:41:47.
 * Gegenereerd op: Tue Nov 27 10:43:34 CET 2012.
 */
@Embeddable
public class ProtocolleringsniveauCode extends AbstractProtocolleringsniveauCode {

    /**
     * Lege (value-object) constructor voor ProtocolleringsniveauCode, niet gedeclareerd als public om te voorkomen dat
     * objecten zonder waarde worden geïnstantieerd.
     * 
     */
    private ProtocolleringsniveauCode() {
        super();
    }

    /**
     * Constructor voor ProtocolleringsniveauCode die de waarde toekent aan het (value-)object.
     * 
     * @param waarde De waarde voor dit value-object.
     */
    public ProtocolleringsniveauCode(final Short waarde) {
        super(waarde);
    }

}
