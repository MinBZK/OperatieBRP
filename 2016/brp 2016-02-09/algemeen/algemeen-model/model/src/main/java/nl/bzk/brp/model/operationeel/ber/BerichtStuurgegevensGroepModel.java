/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;


/**
 *
 *
 */
@Embeddable
public class BerichtStuurgegevensGroepModel extends AbstractBerichtStuurgegevensGroepModel implements
    BerichtStuurgegevensGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected BerichtStuurgegevensGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param zendendePartijId      zendendePartij van Stuurgegevens.
     * @param zendendeSysteem       zendendeSysteem van Stuurgegevens.
     * @param ontvangendePartijId   ontvangendePartij van Stuurgegevens.
     * @param ontvangendeSysteem    ontvangendeSysteem van Stuurgegevens.
     * @param referentienummer      referentienummer van Stuurgegevens.
     * @param crossReferentienummer crossReferentienummer van Stuurgegevens.
     * @param datumTijdVerzending   datumTijdVerzending van Stuurgegevens.
     * @param datumTijdOntvangst    datumTijdOntvangst van Stuurgegevens.
     */
    public BerichtStuurgegevensGroepModel(final Short zendendePartijId, final SysteemNaamAttribuut zendendeSysteem,
        final Short ontvangendePartijId, final SysteemNaamAttribuut ontvangendeSysteem,
        final ReferentienummerAttribuut referentienummer, final ReferentienummerAttribuut crossReferentienummer,
        final DatumTijdAttribuut datumTijdVerzending, final DatumTijdAttribuut datumTijdOntvangst)
    {
        super(zendendePartijId, zendendeSysteem, ontvangendePartijId, ontvangendeSysteem, referentienummer,
            crossReferentienummer, datumTijdVerzending, datumTijdOntvangst);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStuurgegevensGroep te kopieren groep.
     */
    public BerichtStuurgegevensGroepModel(final BerichtStuurgegevensGroep berichtStuurgegevensGroep) {
        super(berichtStuurgegevensGroep);
    }

}
