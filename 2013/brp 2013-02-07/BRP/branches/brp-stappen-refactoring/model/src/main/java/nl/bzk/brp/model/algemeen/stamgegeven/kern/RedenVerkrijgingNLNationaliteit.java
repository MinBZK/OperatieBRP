/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractRedenVerkrijgingNLNationaliteit;


/**
 * De mogelijke reden voor het verkrijgen van de Nederlandse nationaliteit.
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
@Table(schema = "Kern", name = "RdnVerkNLNation")
public class RedenVerkrijgingNLNationaliteit extends AbstractRedenVerkrijgingNLNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected RedenVerkrijgingNLNationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van RedenVerkrijgingNLNationaliteit.
     * @param omschrijving omschrijving van RedenVerkrijgingNLNationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van RedenVerkrijgingNLNationaliteit.
     * @param datumEindeGeldigheid datumEindeGeldigheid van RedenVerkrijgingNLNationaliteit.
     */
    public RedenVerkrijgingNLNationaliteit(final RedenVerkrijgingCode code,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
