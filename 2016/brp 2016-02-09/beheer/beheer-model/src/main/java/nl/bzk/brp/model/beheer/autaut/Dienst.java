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
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Door de BRP in te zetten dienst ten behoeve van een specifiek abonnement.
 *
 * TODO: opmerking voorheen catalodus optie, was menukaart. Nu niet alle combinaties mogelijk. Afvangen in beheer.
 *
 *
 * Gevolg daarvan is dat we met de afnemer moeten communiceren over welke dienst we het hebben. Daarvoor gebruiken we de
 * Dienst-ID (het toevoegen van een 'leesbare' Dienst-Code heeft niet de voorkeur omdat er dan weer allerlei
 * betekenisvolle semantiek in de sleutel kan sluipen, zoals bijvoorbeeld bij de huidige codes voor de
 * autorisatietabelregels)
 *
 *
 * Een abonnement wordt geregeld doordat één of verschillende soorten diensten worden ingezet. Het inzetten van één
 * soort dienst ten behoeve van één abonnement, is één dienst. Het kan hierbij overigens zijn dat één soort dienst
 * meerdere keren wordt ingezet ten behoeve van één en hetzelfde abonnement. In dat geval leidt dit tot meerdere
 * diensten.
 *
 *
 *
 */
@Entity(name = "beheer.Dienst")
@Table(schema = "AutAut", name = "Dienst")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Dienst {

    @Id
    @SequenceGenerator(name = "DIENST", sequenceName = "AutAut.seq_Dienst")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIENST")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Dienstbundel")
    private Dienstbundel dienstbundel;

    @Column(name = "Srt")
    @Enumerated
    private SoortDienst soort;

    @Column(name = "EffectAfnemerindicaties")
    @Enumerated
    private EffectAfnemerindicaties effectAfnemerindicaties;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    @Embedded
    @AttributeOverride(name = AttenderingscriteriumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Attenderingscriterium"))
    private AttenderingscriteriumAttribuut attenderingscriterium;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "EersteSelectiedat"))
    private DatumAttribuut eersteSelectiedatum;

    @Embedded
    @AttributeOverride(name = SelectieperiodeInMaandenAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SelectieperiodeInMaanden"))
    private SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienst> hisDienstLijst = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstAttendering> hisDienstAttenderingLijst = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienst", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstSelectie> hisDienstSelectieLijst = new HashSet<>();

    /**
     * Retourneert ID van Dienst.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienst.
     *
     * @return Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Soort van Dienst.
     *
     * @return Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SoortDienst getSoort() {
        return soort;
    }

    /**
     * Retourneert Effect afnemerindicaties van Dienst.
     *
     * @return Effect afnemerindicaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public EffectAfnemerindicaties getEffectAfnemerindicaties() {
        return effectAfnemerindicaties;
    }

    /**
     * Retourneert Datum ingang van Dienst.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Dienst.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Geblokkeerd? van Dienst.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Attenderingscriterium van Dienst.
     *
     * @return Attenderingscriterium.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public AttenderingscriteriumAttribuut getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Retourneert Eerste selectiedatum van Dienst.
     *
     * @return Eerste selectiedatum.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getEersteSelectiedatum() {
        return eersteSelectiedatum;
    }

    /**
     * Retourneert Selectieperiode in maanden van Dienst.
     *
     * @return Selectieperiode in maanden.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public SelectieperiodeInMaandenAttribuut getSelectieperiodeInMaanden() {
        return selectieperiodeInMaanden;
    }

    /**
     * Retourneert Standaard van Dienst.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienst> getHisDienstLijst() {
        return hisDienstLijst;
    }

    /**
     * Retourneert Attendering van Dienst.
     *
     * @return Attendering.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstAttendering> getHisDienstAttenderingLijst() {
        return hisDienstAttenderingLijst;
    }

    /**
     * Retourneert Selectie van Dienst.
     *
     * @return Selectie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstSelectie> getHisDienstSelectieLijst() {
        return hisDienstSelectieLijst;
    }

    /**
     * Zet ID van Dienst.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Dienstbundel van Dienst.
     *
     * @param pDienstbundel Dienstbundel.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDienstbundel(final Dienstbundel pDienstbundel) {
        this.dienstbundel = pDienstbundel;
    }

    /**
     * Zet Soort van Dienst.
     *
     * @param pSoort Soort.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSoort(final SoortDienst pSoort) {
        this.soort = pSoort;
    }

    /**
     * Zet Effect afnemerindicaties van Dienst.
     *
     * @param pEffectAfnemerindicaties Effect afnemerindicaties.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setEffectAfnemerindicaties(final EffectAfnemerindicaties pEffectAfnemerindicaties) {
        this.effectAfnemerindicaties = pEffectAfnemerindicaties;
    }

    /**
     * Zet Datum ingang van Dienst.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Dienst.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Geblokkeerd? van Dienst.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

    /**
     * Zet Attenderingscriterium van Dienst.
     *
     * @param pAttenderingscriterium Attenderingscriterium.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAttenderingscriterium(final AttenderingscriteriumAttribuut pAttenderingscriterium) {
        this.attenderingscriterium = pAttenderingscriterium;
    }

    /**
     * Zet Eerste selectiedatum van Dienst.
     *
     * @param pEersteSelectiedatum Eerste selectiedatum.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setEersteSelectiedatum(final DatumAttribuut pEersteSelectiedatum) {
        this.eersteSelectiedatum = pEersteSelectiedatum;
    }

    /**
     * Zet Selectieperiode in maanden van Dienst.
     *
     * @param pSelectieperiodeInMaanden Selectieperiode in maanden.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setSelectieperiodeInMaanden(final SelectieperiodeInMaandenAttribuut pSelectieperiodeInMaanden) {
        this.selectieperiodeInMaanden = pSelectieperiodeInMaanden;
    }

}
