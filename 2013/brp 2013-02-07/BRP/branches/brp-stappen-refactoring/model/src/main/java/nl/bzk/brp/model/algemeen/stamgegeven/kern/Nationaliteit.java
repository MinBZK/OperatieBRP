/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractNationaliteit;


/**
 * De aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het Europees verdrag
 * inzake nationaliteit, Straatsburg 06-11-1997.
 *
 * Er is een nuanceverschil tussen de lijst van Landen en de lijst van Nationaliteiten.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.DynamischStamgegevensModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 10:41:47.
 * Gegenereerd op: Tue Nov 27 10:43:33 CET 2012.
 */
@Entity
@Table(schema = "Kern", name = "Nation")
public class Nationaliteit extends AbstractNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Nationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Nationaliteit.
     * @param naam naam van Nationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Nationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van Nationaliteit.
     */
    public Nationaliteit(final Nationaliteitcode code, final NaamEnumeratiewaarde naam,
            final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
