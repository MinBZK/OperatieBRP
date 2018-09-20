/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AutoriteittypeVanAfgifteReisdocumentCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;


/**
 * De mogelijke Autoriteittypen van afgifte van een reisdocument.
 * <p/>
 * Naamgeving is wat problematisch, zo is de code BZ en BU beiden voor 'minister van buitenlandse zaken'. Om de naam toch uniek te maken, is deze
 * "stamtabel", die toch alleen bestaat ter afdekking van een bedrijfsregel voor de code voor een autoriteit, uniek gemaakt. RvdP 3-3-2014.
 */
@Entity
@Table(schema = "Kern", name = "AuttypeVanAfgifteReisdoc")
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class AutoriteittypeVanAfgifteReisdocument extends AbstractAutoriteittypeVanAfgifteReisdocument {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     */
    protected AutoriteittypeVanAfgifteReisdocument() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param code                   code van AutoriteittypeVanAfgifteReisdocument.
     * @param naam                   naam van AutoriteittypeVanAfgifteReisdocument.
     * @param datumAanvangGeldigheid datumAanvangGeldigheid van AutoriteittypeVanAfgifteReisdocument.
     * @param datumEindeGeldigheid   datumEindeGeldigheid van AutoriteittypeVanAfgifteReisdocument.
     */
    protected AutoriteittypeVanAfgifteReisdocument(final AutoriteittypeVanAfgifteReisdocumentCodeAttribuut code,
        final NaamEnumeratiewaardeAttribuut naam, final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheid,
        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid)
    {
        super(code, naam, datumAanvangGeldigheid, datumEindeGeldigheid);
    }

}
