/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonNummerverwijzingGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonNummerverwijzingGroepModel extends AbstractPersoonNummerverwijzingGroepModel implements
    PersoonNummerverwijzingGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonNummerverwijzingGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param vorigeBurgerservicenummer   vorigeBurgerservicenummer van Nummerverwijzing.
     * @param volgendeBurgerservicenummer volgendeBurgerservicenummer van Nummerverwijzing.
     * @param vorigeAdministratienummer   vorigeAdministratienummer van Nummerverwijzing.
     * @param volgendeAdministratienummer volgendeAdministratienummer van Nummerverwijzing.
     */
    public PersoonNummerverwijzingGroepModel(final BurgerservicenummerAttribuut vorigeBurgerservicenummer,
        final BurgerservicenummerAttribuut volgendeBurgerservicenummer,
        final AdministratienummerAttribuut vorigeAdministratienummer,
        final AdministratienummerAttribuut volgendeAdministratienummer)
    {
        super(vorigeBurgerservicenummer, volgendeBurgerservicenummer, vorigeAdministratienummer,
            volgendeAdministratienummer);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNummerverwijzingGroep te kopieren groep.
     */
    public PersoonNummerverwijzingGroepModel(final PersoonNummerverwijzingGroep persoonNummerverwijzingGroep) {
        super(persoonNummerverwijzingGroep);
    }

}
