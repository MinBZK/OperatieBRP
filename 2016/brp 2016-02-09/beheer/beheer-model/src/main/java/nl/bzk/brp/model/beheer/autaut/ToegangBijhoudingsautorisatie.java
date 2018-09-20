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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.beheer.kern.Partij;
import nl.bzk.brp.model.beheer.kern.PartijRol;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Vastlegging van autorisaties welke Partijen gerechtigd zijn bijhoudingen in te dienen voor andere Partijen.
 *
 * De Toegang bijhoudingsautorisatie geeft invulling aan de bewerkersconstructie voor bijhouders.
 *
 *
 *
 */
@Entity(name = "beheer.ToegangBijhoudingsautorisatie")
@Table(schema = "AutAut", name = "ToegangBijhautorisatie")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ToegangBijhoudingsautorisatie {

    @Id
    @SequenceGenerator(name = "TOEGANGBIJHOUDINGSAUTORISATIE", sequenceName = "AutAut.seq_ToegangBijhautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "TOEGANGBIJHOUDINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Geautoriseerde")
    private PartijRol geautoriseerde;

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
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toegangBijhoudingsautorisatie", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisToegangBijhoudingsautorisatie> hisToegangBijhoudingsautorisatieLijst = new HashSet<>();

    /**
     * Retourneert ID van Toegang bijhoudingsautorisatie.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Geautoriseerde van Toegang bijhoudingsautorisatie.
     *
     * @return Geautoriseerde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PartijRol getGeautoriseerde() {
        return geautoriseerde;
    }

    /**
     * Retourneert Ondertekenaar van Toegang bijhoudingsautorisatie.
     *
     * @return Ondertekenaar.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getOndertekenaar() {
        return ondertekenaar;
    }

    /**
     * Retourneert Transporteur van Toegang bijhoudingsautorisatie.
     *
     * @return Transporteur.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Partij getTransporteur() {
        return transporteur;
    }

    /**
     * Retourneert Datum ingang van Toegang bijhoudingsautorisatie.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Toegang bijhoudingsautorisatie.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Geblokkeerd? van Toegang bijhoudingsautorisatie.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Standaard van Toegang bijhoudingsautorisatie.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisToegangBijhoudingsautorisatie> getHisToegangBijhoudingsautorisatieLijst() {
        return hisToegangBijhoudingsautorisatieLijst;
    }

    /**
     * Zet ID van Toegang bijhoudingsautorisatie.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Geautoriseerde van Toegang bijhoudingsautorisatie.
     *
     * @param pGeautoriseerde Geautoriseerde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setGeautoriseerde(final PartijRol pGeautoriseerde) {
        this.geautoriseerde = pGeautoriseerde;
    }

    /**
     * Zet Ondertekenaar van Toegang bijhoudingsautorisatie.
     *
     * @param pOndertekenaar Ondertekenaar.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOndertekenaar(final Partij pOndertekenaar) {
        this.ondertekenaar = pOndertekenaar;
    }

    /**
     * Zet Transporteur van Toegang bijhoudingsautorisatie.
     *
     * @param pTransporteur Transporteur.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setTransporteur(final Partij pTransporteur) {
        this.transporteur = pTransporteur;
    }

    /**
     * Zet Datum ingang van Toegang bijhoudingsautorisatie.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Toegang bijhoudingsautorisatie.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Geblokkeerd? van Toegang bijhoudingsautorisatie.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

}
