/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Entity
@Table(schema = "Kern", name = "His_PartijBijhouding")
@Access(value = AccessType.FIELD)
public class HisPartijBijhouding extends AbstractHisPartijBijhouding implements ModelIdentificeerbaar<Integer>, ElementIdentificeerbaar {

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected HisPartijBijhouding() {
        super();
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param partij partij van HisPartijBijhouding
     * @param indicatieAutomatischFiatteren indicatieAutomatischFiatteren van HisPartijBijhouding
     * @param datumOvergangNaarBRP datumOvergangNaarBRP van HisPartijBijhouding
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public HisPartijBijhouding(
        final Partij partij,
        final JaNeeAttribuut indicatieAutomatischFiatteren,
        final DatumAttribuut datumOvergangNaarBRP,
        final ActieModel actieInhoud)
    {
        super(partij, indicatieAutomatischFiatteren, datumOvergangNaarBRP, actieInhoud);
    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public HisPartijBijhouding(final AbstractHisPartijBijhouding kopie) {
        super(kopie);
    }

}
