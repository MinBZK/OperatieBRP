/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * De aanduiding van de staat waarmee een persoon een juridische band kan hebben zoals bedoeld in het Europees verdrag inzake nationaliteit, Straatsburg
 * 06-11-1997.
 * <p/>
 * Er is een nuanceverschil tussen de lijst van Landen en de lijst van Nationaliteiten.
 */
@Entity
@Table(schema = "Kern", name = "Nation")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Nationaliteit extends AbstractNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected Nationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van Nationaliteit.
     * @param naam                   naam van Nationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van Nationaliteit.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van Nationaliteit.
     */
    public Nationaliteit(final NationaliteitcodeAttribuut code, final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
