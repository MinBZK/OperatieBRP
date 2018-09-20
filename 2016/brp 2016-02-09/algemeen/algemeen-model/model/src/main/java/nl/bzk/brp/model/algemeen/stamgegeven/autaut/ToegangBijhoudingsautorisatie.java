/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import java.util.List;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Vastlegging van autorisaties welke Partijen gerechtigd zijn bijhoudingen in te dienen voor andere Partijen.
 *
 * De Toegang bijhoudingsautorisatie geeft invulling aan de bewerkersconstructie voor bijhouders.
 *
 *
 *
 */
@Table(schema = "AutAut", name = "ToegangBijhautorisatie")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ToegangBijhoudingsautorisatie extends AbstractToegangBijhoudingsautorisatie {

    @OneToMany(mappedBy = "toegangBijhoudingsautorisatie", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.JOIN)
    private List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected ToegangBijhoudingsautorisatie() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param geautoriseerde geautoriseerde van ToegangBijhoudingsautorisatie.
     * @param ondertekenaar ondertekenaar van ToegangBijhoudingsautorisatie.
     * @param transporteur transporteur van ToegangBijhoudingsautorisatie.
     * @param datumIngang datumIngang van ToegangBijhoudingsautorisatie.
     * @param datumEinde datumEinde van ToegangBijhoudingsautorisatie.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van ToegangBijhoudingsautorisatie.
     * @param geautoriseerdeHandelingen de lijst van geautoriseerde handelingen.
     */
    protected ToegangBijhoudingsautorisatie(
        final PartijRol geautoriseerde,
        final Partij ondertekenaar,
        final Partij transporteur,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final List<BijhoudingsautorisatieSoortAdministratieveHandeling> geautoriseerdeHandelingen)
    {
        super(geautoriseerde, ondertekenaar, transporteur, datumIngang, datumEinde, indicatieGeblokkeerd);
        this.geautoriseerdeHandelingen = geautoriseerdeHandelingen;
    }

    public List<BijhoudingsautorisatieSoortAdministratieveHandeling> getGeautoriseerdeHandelingen() {
        return geautoriseerdeHandelingen;
    }
}
