/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * De mogelijke reden voor het verliezen van de Nederlandse nationaliteit.
 */
@Entity
@Table(schema = "Kern", name = "RdnVerliesNLNation")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class RedenVerliesNLNationaliteit extends AbstractRedenVerliesNLNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected RedenVerliesNLNationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van RedenVerliesNLNationaliteit.
     * @param omschrijving           omschrijving van RedenVerliesNLNationaliteit.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van RedenVerliesNLNationaliteit.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van RedenVerliesNLNationaliteit.
     */
    public RedenVerliesNLNationaliteit(final RedenVerliesCodeAttribuut code,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
