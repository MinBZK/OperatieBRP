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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SoortNederlandsReisdocumentCode;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.basis.AbstractSoortNederlandsReisdocument;
import org.apache.commons.lang.builder.CompareToBuilder;


/**
 * De mogelijke soorten voor een Nederlandse reisdocument.
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
@Table(schema = "Kern", name = "SrtNLReisdoc")
public class SoortNederlandsReisdocument extends AbstractSoortNederlandsReisdocument implements Comparable<SoortNederlandsReisdocument> {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected SoortNederlandsReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code code van SoortNederlandsReisdocument.
     * @param omschrijving omschrijving van SoortNederlandsReisdocument.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van SoortNederlandsReisdocument.
     * @param datumEindeGeldigheid datumEindeGeldigheid van SoortNederlandsReisdocument.
     */
    protected SoortNederlandsReisdocument(final SoortNederlandsReisdocumentCode code,
            final OmschrijvingEnumeratiewaarde omschrijving, final Datum datumAanvangGeldigheid,
            final Datum datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

    @Override
    public int compareTo(SoortNederlandsReisdocument o) {
        return new CompareToBuilder().append(getCode(), o.getCode()).append(getOmschrijving(), o.getOmschrijving()).toComparison();
    }
}
