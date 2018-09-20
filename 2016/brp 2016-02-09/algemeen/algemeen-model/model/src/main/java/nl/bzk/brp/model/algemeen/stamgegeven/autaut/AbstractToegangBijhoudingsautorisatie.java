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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractToegangBijhoudingsautorisatie {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Geautoriseerde")
    @Fetch(value = FetchMode.JOIN)
    private PartijRol geautoriseerde;

    @ManyToOne
    @JoinColumn(name = "Ondertekenaar")
    @Fetch(value = FetchMode.JOIN)
    private Partij ondertekenaar;

    @ManyToOne
    @JoinColumn(name = "Transporteur")
    @Fetch(value = FetchMode.JOIN)
    private Partij transporteur;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractToegangBijhoudingsautorisatie() {
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
     */
    protected AbstractToegangBijhoudingsautorisatie(
        final PartijRol geautoriseerde,
        final Partij ondertekenaar,
        final Partij transporteur,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd)
    {
        this.geautoriseerde = geautoriseerde;
        this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * Retourneert ID van Toegang bijhoudingsautorisatie.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geautoriseerde van Toegang bijhoudingsautorisatie.
     *
     * @return Geautoriseerde.
     */
    public final PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Ondertekenaar van Toegang bijhoudingsautorisatie.
     *
     * @return Ondertekenaar.
     */
    public final Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Retourneert Transporteur van Toegang bijhoudingsautorisatie.
     *
     * @return Transporteur.
     */
    public final Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Retourneert Datum ingang van Toegang bijhoudingsautorisatie.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Toegang bijhoudingsautorisatie.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Geblokkeerd? van Toegang bijhoudingsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

}
