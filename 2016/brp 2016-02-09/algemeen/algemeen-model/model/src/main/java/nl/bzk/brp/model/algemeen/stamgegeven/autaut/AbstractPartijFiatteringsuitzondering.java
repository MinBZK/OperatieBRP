/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocument;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Bijhoudingsvoorstellen waarvan de Partij niet wil dat deze automatisch gefiatteerd worden.
 *
 * Fiatteringsuitzonderingen mogen alleen gedefinieerd zijn als de Partij (in principe) automatisch fiatteert
 * (A:"Partij.Automatisch fiatteren?" = Ja). Alle bijhoudingen van andere bijhouders die voldoen aan een criterium in
 * dit Objecttype zijn dan uitzonderingen op het automatisch fiatteren en zal deze Partij handmatig fiatteren.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractPartijFiatteringsuitzondering {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    @Fetch(value = FetchMode.JOIN)
    private Partij partij;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @ManyToOne
    @JoinColumn(name = "PartijBijhvoorstel")
    @Fetch(value = FetchMode.JOIN)
    private Partij partijBijhoudingsvoorstel;

    @ManyToOne
    @JoinColumn(name = "SrtDoc")
    @Fetch(value = FetchMode.JOIN)
    private SoortDocument soortDocument;

    @Column(name = "SrtAdmHnd")
    private SoortAdministratieveHandeling soortAdministratieveHandeling;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractPartijFiatteringsuitzondering() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param partij partij van PartijFiatteringsuitzondering.
     * @param datumIngang datumIngang van PartijFiatteringsuitzondering.
     * @param datumEinde datumEinde van PartijFiatteringsuitzondering.
     * @param partijBijhoudingsvoorstel partijBijhoudingsvoorstel van PartijFiatteringsuitzondering.
     * @param soortDocument soortDocument van PartijFiatteringsuitzondering.
     * @param soortAdministratieveHandeling soortAdministratieveHandeling van PartijFiatteringsuitzondering.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van PartijFiatteringsuitzondering.
     */
    protected AbstractPartijFiatteringsuitzondering(
        final Partij partij,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final Partij partijBijhoudingsvoorstel,
        final SoortDocument soortDocument,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final JaAttribuut indicatieGeblokkeerd)
    {
        this.partij = partij;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.partijBijhoudingsvoorstel = partijBijhoudingsvoorstel;
        this.soortDocument = soortDocument;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * Retourneert ID van Partij \ Fiatteringsuitzondering.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van Partij \ Fiatteringsuitzondering.
     *
     * @return Partij.
     */
    public final Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Datum ingang van Partij \ Fiatteringsuitzondering.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij \ Fiatteringsuitzondering.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Partij bijhoudingsvoorstel van Partij \ Fiatteringsuitzondering.
     *
     * @return Partij bijhoudingsvoorstel.
     */
    public final Partij getPartijBijhoudingsvoorstel() {
        return partijBijhoudingsvoorstel;
    }

    /**
     * Retourneert Soort document van Partij \ Fiatteringsuitzondering.
     *
     * @return Soort document.
     */
    public final SoortDocument getSoortDocument() {
        return soortDocument;
    }

    /**
     * Retourneert Soort administratieve handeling van Partij \ Fiatteringsuitzondering.
     *
     * @return Soort administratieve handeling.
     */
    public final SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Retourneert Geblokkeerd? van Partij \ Fiatteringsuitzondering.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

}
