/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Een voor de BRP relevant overheidsorgaan of derde, zoals bedoeld in de Wet BRP, of onderdeel daarvan, die met een
 * bepaalde gerechtvaardigde doelstelling is aangesloten op de BRP.
 *
 * Dit betreft - onder andere - gemeenten, (overige) overheidsorganen en derden.
 *
 * 1. Er is voor gekozen om gemeenten, overige overheidsorganen etc te zien als soorten partijen, en ze allemaal op te
 * nemen in een partij tabel. Van oudsher voorkomende tabellen zoals 'de gemeentetabel' is daarmee een subtype van de
 * partij tabel geworden.
 *
 *
 *
 */
@Entity(name = "beheer.Partij")
@Table(schema = "Kern", name = "Partij")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Partij {

    @Id
    @SequenceGenerator(name = "PARTIJ", sequenceName = "Kern.seq_Partij")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PARTIJ")
    @JsonProperty
    private Short iD;

    @Embedded
    @AttributeOverride(name = PartijCodeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Code"))
    private PartijCodeAttribuut code;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumEvtDeelsOnbekendAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = OINAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OIN"))
    private OINAttribuut oIN;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Srt")
    private SoortPartij soort;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVerstrbeperkingMogelijk"))
    private JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndAutoFiat"))
    private JaNeeAttribuut indicatieAutomatischFiatteren;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatOvergangNaarBRP"))
    private DatumAttribuut datumOvergangNaarBRP;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partij", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisPartij> hisPartijLijst = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "partij", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisPartijBijhouding> hisPartijBijhoudingLijst = new HashSet<>();

    /**
     * Retourneert ID van Partij.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Short getID() {
        return iD;
    }

    /**
     * Retourneert Code van Partij.
     *
     * @return Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PartijCodeAttribuut getCode() {
        return code;
    }

    /**
     * Retourneert Naam van Partij.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van Partij.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Partij.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert OIN van Partij.
     *
     * @return OIN.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OINAttribuut getOIN() {
        return oIN;
    }

    /**
     * Retourneert Soort van Partij.
     *
     * @return Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortPartij getSoort() {
        return soort;
    }

    /**
     * Retourneert Verstrekkingsbeperking mogelijk? van Partij.
     *
     * @return Verstrekkingsbeperking mogelijk?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Retourneert Automatisch fiatteren? van Partij.
     *
     * @return Automatisch fiatteren?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaNeeAttribuut getIndicatieAutomatischFiatteren() {
        return indicatieAutomatischFiatteren;
    }

    /**
     * Retourneert Datum overgang naar BRP van Partij.
     *
     * @return Datum overgang naar BRP.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumOvergangNaarBRP() {
        return datumOvergangNaarBRP;
    }

    /**
     * Retourneert Standaard van Partij.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisPartij> getHisPartijLijst() {
        return hisPartijLijst;
    }

    /**
     * Retourneert Bijhouding van Partij.
     *
     * @return Bijhouding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisPartijBijhouding> getHisPartijBijhoudingLijst() {
        return hisPartijBijhoudingLijst;
    }

    /**
     * Zet ID van Partij.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Short pID) {
        this.iD = pID;
    }

    /**
     * Zet Code van Partij.
     *
     * @param pCode Code.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setCode(final PartijCodeAttribuut pCode) {
        this.code = pCode;
    }

    /**
     * Zet Naam van Partij.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum ingang van Partij.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumEvtDeelsOnbekendAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Partij.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumEvtDeelsOnbekendAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet OIN van Partij.
     *
     * @param pOIN OIN.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setOIN(final OINAttribuut pOIN) {
        this.oIN = pOIN;
    }

    /**
     * Zet Soort van Partij.
     *
     * @param pSoort Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoort(final SoortPartij pSoort) {
        this.soort = pSoort;
    }

    /**
     * Zet Verstrekkingsbeperking mogelijk? van Partij.
     *
     * @param pIndicatieVerstrekkingsbeperkingMogelijk Verstrekkingsbeperking mogelijk?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieVerstrekkingsbeperkingMogelijk(final JaNeeAttribuut pIndicatieVerstrekkingsbeperkingMogelijk) {
        this.indicatieVerstrekkingsbeperkingMogelijk = pIndicatieVerstrekkingsbeperkingMogelijk;
    }

    /**
     * Zet Automatisch fiatteren? van Partij.
     *
     * @param pIndicatieAutomatischFiatteren Automatisch fiatteren?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieAutomatischFiatteren(final JaNeeAttribuut pIndicatieAutomatischFiatteren) {
        this.indicatieAutomatischFiatteren = pIndicatieAutomatischFiatteren;
    }

    /**
     * Zet Datum overgang naar BRP van Partij.
     *
     * @param pDatumOvergangNaarBRP Datum overgang naar BRP.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumOvergangNaarBRP(final DatumAttribuut pDatumOvergangNaarBRP) {
        this.datumOvergangNaarBRP = pDatumOvergangNaarBRP;
    }

}
