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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * De wijze waarop een partij toegang krijgt tot (de gegevens geleverd via) een abonnement.
 *
 * Een partij (lees: afnemer) kan via één of meer abonnementen gegevens uit de BRP ontvangen. Het is hierbij mogelijk
 * dat een specifiek autorisatiemiddel wordt ingezet, en eventueel dat er sprake is van een intermediair.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractToegangLeveringsautorisatie {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Geautoriseerde")
    @Fetch(value = FetchMode.JOIN)
    private PartijRol geautoriseerde;

    @ManyToOne
    @JoinColumn(name = "Levsautorisatie")
    @Fetch(value = FetchMode.JOIN)
    private Leveringsautorisatie leveringsautorisatie;

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
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaderePopulatiebeperking"))
    private PopulatiebeperkingAttribuut naderePopulatiebeperking;

    @Embedded
    @AttributeOverride(name = UriAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Afleverpunt"))
    private UriAttribuut afleverpunt;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractToegangLeveringsautorisatie() {
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param geautoriseerde geautoriseerde van ToegangLeveringsautorisatie.
     * @param leveringsautorisatie leveringsautorisatie van ToegangLeveringsautorisatie.
     * @param ondertekenaar ondertekenaar van ToegangLeveringsautorisatie.
     * @param transporteur transporteur van ToegangLeveringsautorisatie.
     * @param datumIngang datumIngang van ToegangLeveringsautorisatie.
     * @param datumEinde datumEinde van ToegangLeveringsautorisatie.
     * @param naderePopulatiebeperking naderePopulatiebeperking van ToegangLeveringsautorisatie.
     * @param afleverpunt afleverpunt van ToegangLeveringsautorisatie.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van ToegangLeveringsautorisatie.
     */
    protected AbstractToegangLeveringsautorisatie(
        final PartijRol geautoriseerde,
        final Leveringsautorisatie leveringsautorisatie,
        final Partij ondertekenaar,
        final Partij transporteur,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final UriAttribuut afleverpunt,
        final JaAttribuut indicatieGeblokkeerd)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.geautoriseerde = geautoriseerde;
        this.leveringsautorisatie = leveringsautorisatie;
        this.ondertekenaar = ondertekenaar;
        this.transporteur = transporteur;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.afleverpunt = afleverpunt;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * Retourneert ID van Toegang leveringsautorisatie.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geautoriseerde van Toegang leveringsautorisatie.
     *
     * @return Geautoriseerde.
     */
    public final PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Leveringsautorisatie van Toegang leveringsautorisatie.
     *
     * @return Leveringsautorisatie.
     */
    public final Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Ondertekenaar van Toegang leveringsautorisatie.
     *
     * @return Ondertekenaar.
     */
    public final Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Retourneert Transporteur van Toegang leveringsautorisatie.
     *
     * @return Transporteur.
     */
    public final Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Retourneert Datum ingang van Toegang leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Toegang leveringsautorisatie.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van Toegang leveringsautorisatie.
     *
     * @return Nadere populatiebeperking.
     */
    public final PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Afleverpunt van Toegang leveringsautorisatie.
     *
     * @return Afleverpunt.
     */
    public final UriAttribuut getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Retourneert Geblokkeerd? van Toegang leveringsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     *
     * @param afleverpunt
     */
    //handmatige wijziging
    public final void setAfleverpunt(final UriAttribuut afleverpunt) {
        this.afleverpunt = afleverpunt;
    }

}
