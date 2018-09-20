/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.logisch.kern.PersoonAfgeleidAdministratiefGroep;


/**
 *
 *
 */
@Embeddable
public class PersoonAfgeleidAdministratiefGroepModel extends AbstractPersoonAfgeleidAdministratiefGroepModel implements
    PersoonAfgeleidAdministratiefGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonAfgeleidAdministratiefGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param administratieveHandeling administratieveHandeling van Afgeleid administratief.
     * @param tijdstipLaatsteWijziging tijdstipLaatsteWijziging van Afgeleid administratief.
     * @param sorteervolgorde          sorteervolgorde van Afgeleid administratief.
     * @param indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig
     *                                 indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig van Afgeleid administratief.
     * @param tijdstipLaatsteWijzigingGBASystematiek
     *                                 tijdstipLaatsteWijzigingGBASystematiek van Afgeleid administratief.
     */
    public PersoonAfgeleidAdministratiefGroepModel(final AdministratieveHandelingModel administratieveHandeling,
        final DatumTijdAttribuut tijdstipLaatsteWijziging,
        final SorteervolgordeAttribuut sorteervolgorde,
        final JaNeeAttribuut indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
        final DatumTijdAttribuut tijdstipLaatsteWijzigingGBASystematiek)
    {
        super(administratieveHandeling, tijdstipLaatsteWijziging,
            sorteervolgorde, indicatieOnverwerktBijhoudingsvoorstelNietIngezeteneAanwezig,
            tijdstipLaatsteWijzigingGBASystematiek);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonAfgeleidAdministratiefGroep
     *         te kopieren groep.
     */
    public PersoonAfgeleidAdministratiefGroepModel(
        final PersoonAfgeleidAdministratiefGroep persoonAfgeleidAdministratiefGroep)
    {
        super(persoonAfgeleidAdministratiefGroep);
    }

    @Transient
    private Verwerkingssoort verwerkingssoort;

    public Verwerkingssoort getVerwerkingssoort() {
        return verwerkingssoort;
    }

    public void setVerwerkingssoort(final Verwerkingssoort verwerkingssoort) {
        this.verwerkingssoort = verwerkingssoort;
    }

}
