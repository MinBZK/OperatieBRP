/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.PartijRol;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * De wijze waarop een partij toegang krijgt tot (de gegevens geleverd via) een abonnement.
 *
 * Een partij (lees: afnemer) kan via één of meer abonnementen gegevens uit de BRP ontvangen. Het is hierbij mogelijk
 * dat een specifiek autorisatiemiddel wordt ingezet, en eventueel dat er sprake is van een intermediair.
 *
 *
 *
 */
@Entity(name = "beheer.ToegangLeveringsautorisatie")
@Table(schema = "AutAut", name = "ToegangLevsautorisatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ToegangLeveringsautorisatie {

    @Id
    @SequenceGenerator(name = "TOEGANGLEVERINGSAUTORISATIE", sequenceName = "AutAut.seq_ToegangLevsautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOEGANGLEVERINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Geautoriseerde")
    private PartijRol geautoriseerde;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Levsautorisatie")
    private Leveringsautorisatie leveringsautorisatie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Ondertekenaar")
    private Partij ondertekenaar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Transporteur")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toegangLeveringsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisToegangLeveringsautorisatie> hisToegangLeveringsautorisatieLijst = new HashSet<>();

    /**
     * Retourneert ID van Toegang leveringsautorisatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geautoriseerde van Toegang leveringsautorisatie.
     *
     * @return Geautoriseerde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Leveringsautorisatie van Toegang leveringsautorisatie.
     *
     * @return Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Ondertekenaar van Toegang leveringsautorisatie.
     *
     * @return Ondertekenaar.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Retourneert Transporteur van Toegang leveringsautorisatie.
     *
     * @return Transporteur.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Retourneert Datum ingang van Toegang leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Toegang leveringsautorisatie.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van Toegang leveringsautorisatie.
     *
     * @return Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Afleverpunt van Toegang leveringsautorisatie.
     *
     * @return Afleverpunt.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public UriAttribuut getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Retourneert Geblokkeerd? van Toegang leveringsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Standaard van Toegang leveringsautorisatie.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisToegangLeveringsautorisatie> getHisToegangLeveringsautorisatieLijst() {
        return hisToegangLeveringsautorisatieLijst;
    }

    /**
     * Zet ID van Toegang leveringsautorisatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Geautoriseerde van Toegang leveringsautorisatie.
     *
     * @param pGeautoriseerde Geautoriseerde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setGeautoriseerde(final PartijRol pGeautoriseerde) {
        this.geautoriseerde = pGeautoriseerde;
    }

    /**
     * Zet Leveringsautorisatie van Toegang leveringsautorisatie.
     *
     * @param pLeveringsautorisatie Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setLeveringsautorisatie(final Leveringsautorisatie pLeveringsautorisatie) {
        this.leveringsautorisatie = pLeveringsautorisatie;
    }

    /**
     * Zet Ondertekenaar van Toegang leveringsautorisatie.
     *
     * @param pOndertekenaar Ondertekenaar.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOndertekenaar(final Partij pOndertekenaar) {
        this.ondertekenaar = pOndertekenaar;
    }

    /**
     * Zet Transporteur van Toegang leveringsautorisatie.
     *
     * @param pTransporteur Transporteur.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setTransporteur(final Partij pTransporteur) {
        this.transporteur = pTransporteur;
    }

    /**
     * Zet Datum ingang van Toegang leveringsautorisatie.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Toegang leveringsautorisatie.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Nadere populatiebeperking van Toegang leveringsautorisatie.
     *
     * @param pNaderePopulatiebeperking Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaderePopulatiebeperking(final PopulatiebeperkingAttribuut pNaderePopulatiebeperking) {
        this.naderePopulatiebeperking = pNaderePopulatiebeperking;
    }

    /**
     * Zet Afleverpunt van Toegang leveringsautorisatie.
     *
     * @param pAfleverpunt Afleverpunt.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAfleverpunt(final UriAttribuut pAfleverpunt) {
        this.afleverpunt = pAfleverpunt;
    }

    /**
     * Zet Geblokkeerd? van Toegang leveringsautorisatie.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

}
