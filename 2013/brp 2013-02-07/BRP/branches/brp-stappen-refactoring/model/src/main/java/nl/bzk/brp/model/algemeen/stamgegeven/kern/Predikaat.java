/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PredikaatCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractPredikaat;


/**
 * De mogelijke predikaat van een Persoon.
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
@Table(schema = "Kern", name = "Predikaat")
public class Predikaat extends AbstractPredikaat {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected Predikaat() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van Predikaat.
     * @param naamMannelijk naamMannelijk van Predikaat.
     * @param naamVrouwelijk naamVrouwelijk van Predikaat.
     */
    public Predikaat(final PredikaatCode code, final NaamEnumeratiewaarde naamMannelijk,
            final NaamEnumeratiewaarde naamVrouwelijk)
    {
        super(code, naamMannelijk, naamVrouwelijk);
    }

}
