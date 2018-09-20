/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Bijhoudingsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.AbstractStatischObjectType;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Situatie waarin de bijhoudingsautorisatie de bijhouding toestaat.
 *
 * Bijhoudingsautorisaties zijn restrictief: alleen door iets expliciets toe te staan is bijhouding mogelijk. Situaties
 * waarin bijhouden is toegestaan, worden gekenmerkt door een soort actie, of een actie en een soort document. Om
 * bijvoorbeeld toe te staan dat de bijhoudingsautorisatie iets toestaat voor acties van 'soort 1' mits onderbouwt met
 * document van 'soort 2', dient er een bijhoudingssituatie te zijn met soort actie =1 en soort document =2.
 * Zijn er bij een bijhoudingsautorisaties g��n bijhoudingssituaties onderkend, dan mogen �lle situaties (c.q.: alle
 * combinaties van soort actie en soort document).
 * Een bijhoudingssituatie met een lege soort document bepaalt dat alle situaties gebaseerd op de soort actie zijn
 * toegestaan.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@MappedSuperclass
public abstract class AbstractBijhoudingssituatie extends AbstractStatischObjectType {

    @Id
    @JsonProperty
    private Integer                       iD;

    @ManyToOne
    @JoinColumn(name = "Bijhautorisatie")
    @Fetch(value = FetchMode.JOIN)
    private Bijhoudingsautorisatie        bijhoudingsautorisatie;

    @Column(name = "SrtAdmHnd")
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    @ManyToOne
    @JoinColumn(name = "SrtDoc")
    @Fetch(value = FetchMode.JOIN)
    private SoortDocument                 soortDocument;

    @Type(type = "StatusHistorie")
    @Column(name = "BijhsituatieStatusHis")
    private StatusHistorie                bijhoudingssituatieStatusHis;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractBijhoudingssituatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param bijhoudingsautorisatie bijhoudingsautorisatie van Bijhoudingssituatie.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van Bijhoudingssituatie.
     * @param soortDocument soortDocument van Bijhoudingssituatie.
     * @param bijhoudingssituatieStatusHis bijhoudingssituatieStatusHis van Bijhoudingssituatie.
     */
    protected AbstractBijhoudingssituatie(final Bijhoudingsautorisatie bijhoudingsautorisatie,
            final SoortAdministratieveHandeling soortAdministratieveHandeling, final SoortDocument soortDocument,
            final StatusHistorie bijhoudingssituatieStatusHis)
    {
        this.bijhoudingsautorisatie = bijhoudingsautorisatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.soortDocument = soortDocument;
        this.bijhoudingssituatieStatusHis = bijhoudingssituatieStatusHis;

    }

    /**
     * Retourneert ID van Bijhoudingssituatie.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Bijhoudingsautorisatie van Bijhoudingssituatie.
     *
     * @return Bijhoudingsautorisatie.
     */
    public Bijhoudingsautorisatie getBijhoudingsautorisatie() {
        return bijhoudingsautorisatie;
    }

    /**
     * Retourneert Soort administratieve handeling van Bijhoudingssituatie.
     *
     * @return Soort administratieve handeling.
     */
    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Retourneert Soort document van Bijhoudingssituatie.
     *
     * @return Soort document.
     */
    public SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Retourneert Bijhoudingssituatie StatusHis van Bijhoudingssituatie.
     *
     * @return Bijhoudingssituatie StatusHis.
     */
    public StatusHistorie getBijhoudingssituatieStatusHis() {
        return bijhoudingssituatieStatusHis;
    }

}
