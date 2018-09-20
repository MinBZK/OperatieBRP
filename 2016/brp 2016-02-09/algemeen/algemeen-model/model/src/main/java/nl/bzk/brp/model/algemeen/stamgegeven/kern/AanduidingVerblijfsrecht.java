/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AanduidingVerblijfsrechtCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * Categorisatie van de Aanduiding van het verblijfsrecht van Persoon.
 * <p/>
 * 1. Naam: aanduiding verblijfsrecht is een verwarrende naam. Immers, de hier bedoelde aanduiding betreft niet alleen het recht op verblijf van de
 * vreemdeling (zoals geregeld in de Vreemdelingenwet 2000), maar ook o.a. het recht op arbeid (zoals [deels] geregeld in de wet arbeid vreemdelingen). De
 * in LO3.x gebruikte terminologie (verblijfstitel) leek een correcte term. Deze term stuit echter op bezwaren vanuit juridische hoek. Om die reden is toch
 * de term verblijfsrecht gehanteerd, voor dit gegeven dat in de hoek van de IND ook wel wordt gedefinieerd als 'het recht op voorzieningen'. RvdP 27
 * november 2012, aangepast 23 december 2013 & 20 januari 2014.
 */
@Entity
@Table(schema = "Kern", name = "AandVerblijfsr")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class AanduidingVerblijfsrecht extends AbstractAanduidingVerblijfsrecht {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected AanduidingVerblijfsrecht() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van AanduidingVerblijfsrecht.
     * @param omschrijving           omschrijving van AanduidingVerblijfsrecht.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van AanduidingVerblijfsrecht.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van AanduidingVerblijfsrecht.
     */
    protected AanduidingVerblijfsrecht(final AanduidingVerblijfsrechtCodeAttribuut code,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijving,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, omschrijving, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
