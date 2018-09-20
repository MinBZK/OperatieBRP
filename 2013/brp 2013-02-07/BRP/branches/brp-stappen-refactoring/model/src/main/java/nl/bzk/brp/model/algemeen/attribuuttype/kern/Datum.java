/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.attribuuttype.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.basis.AbstractDatum;


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
public class Datum extends AbstractDatum {

    /**
     * Lege (value-object) constructor voor Datum, niet gedeclareerd als public om te voorkomen dat objecten zonder
     * waarde
     * worden geïnstantieerd.
     * 
     */
    private Datum() {
        super();
    }

    /**
     * Constructor voor Datum die de waarde toekent aan het (value-)object.
     * 
     * @param waarde De waarde voor dit value-object.
     */
    public Datum(final Integer waarde) {
        super(waarde);
    }

    /**
     * Test of deze datum na de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum na de opgegeven datum ligt.
     */
    public boolean na(final Datum vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() > vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum voor de opgegeven datum ligt.
     *
     * @param vergelijkingsDatum de datum waarmee vergeleken wordt.
     * @return of deze datum voor de opgegeven datum ligt.
     */
    public boolean voor(final Datum vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde() < vergelijkingsDatum.getWaarde();
    }

    /**
     * Test of deze datum dezelfde datum is als de opgegeven datum
     * @param vergelijkingsDatum
     * @return
     */
    public boolean op(final Datum vergelijkingsDatum) {
        if (vergelijkingsDatum == null || vergelijkingsDatum.getWaarde() == null) {
            throw new IllegalArgumentException("Datum moet aanwezig zijn.");
        }
        return getWaarde().equals(vergelijkingsDatum.getWaarde());

    }

}
