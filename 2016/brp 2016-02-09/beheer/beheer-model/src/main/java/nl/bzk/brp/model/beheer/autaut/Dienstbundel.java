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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * De inzet om maar één dienst van elke soort onder een autorisatie te hebben wordt ingeschat als niet houdbaar voor
 * zoeken, bevragen en selecties. Voor die diensten gaan we dus toestaan dat er meerdere voorkomen onder dezelfde
 * autorisatie.
 *
 * We staan geen meerdere diensten toe bij alles wat met mutatielevering van doen heeft en bij Attendering.
 *
 *
 *
 *
 */
@Entity(name = "beheer.Dienstbundel")
@Table(schema = "AutAut", name = "Dienstbundel")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class Dienstbundel {

    @Id
    @SequenceGenerator(name = "DIENSTBUNDEL", sequenceName = "AutAut.seq_Dienstbundel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "DIENSTBUNDEL")
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Levsautorisatie")
    private Leveringsautorisatie leveringsautorisatie;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    private NaamEnumeratiewaardeAttribuut naam;

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
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNaderePopbeperkingVolConv"))
    private NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    private JaAttribuut indicatieGeblokkeerd;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<HisDienstbundel> hisDienstbundelLijst = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<Dienst> diensten = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelGroep> groepen = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "dienstbundel", cascade = CascadeType.ALL, orphanRemoval = true)
    @Fetch(value = FetchMode.SELECT)
    private Set<DienstbundelLO3Rubriek> lO3Rubrieken = new HashSet<>();

    /**
     * Retourneert ID van Dienstbundel.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Leveringsautorisatie van Dienstbundel.
     *
     * @return Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Naam van Dienstbundel.
     *
     * @return Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van Dienstbundel.
     *
     * @return Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Dienstbundel.
     *
     * @return Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van Dienstbundel.
     *
     * @return Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Nadere populatiebeperking volledig geconverteerd? van Dienstbundel.
     *
     * @return Nadere populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public NeeAttribuut getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van Dienstbundel.
     *
     * @return Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van Dienstbundel.
     *
     * @return Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Standaard van Dienstbundel.
     *
     * @return Standaard.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<HisDienstbundel> getHisDienstbundelLijst() {
        return hisDienstbundelLijst;
    }

    /**
     * Retourneert de lijst van Diensten.
     *
     * @return lijst van Diensten
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<Dienst> getDiensten() {
        return diensten;
    }

    /**
     * Retourneert de lijst van Dienstbundel\ Groepen.
     *
     * @return lijst van Dienstbundel\ Groepen
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<DienstbundelGroep> getGroepen() {
        return groepen;
    }

    /**
     * Retourneert de lijst van Dienstbundel \ LO3 Rubrieken.
     *
     * @return lijst van Dienstbundel \ LO3 Rubrieken
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Set<DienstbundelLO3Rubriek> getLO3Rubrieken() {
        return lO3Rubrieken;
    }

    /**
     * Zet ID van Dienstbundel.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Leveringsautorisatie van Dienstbundel.
     *
     * @param pLeveringsautorisatie Leveringsautorisatie.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setLeveringsautorisatie(final Leveringsautorisatie pLeveringsautorisatie) {
        this.leveringsautorisatie = pLeveringsautorisatie;
    }

    /**
     * Zet Naam van Dienstbundel.
     *
     * @param pNaam Naam.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaam(final NaamEnumeratiewaardeAttribuut pNaam) {
        this.naam = pNaam;
    }

    /**
     * Zet Datum ingang van Dienstbundel.
     *
     * @param pDatumIngang Datum ingang.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumIngang(final DatumAttribuut pDatumIngang) {
        this.datumIngang = pDatumIngang;
    }

    /**
     * Zet Datum einde van Dienstbundel.
     *
     * @param pDatumEinde Datum einde.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setDatumEinde(final DatumAttribuut pDatumEinde) {
        this.datumEinde = pDatumEinde;
    }

    /**
     * Zet Nadere populatiebeperking van Dienstbundel.
     *
     * @param pNaderePopulatiebeperking Nadere populatiebeperking.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setNaderePopulatiebeperking(final PopulatiebeperkingAttribuut pNaderePopulatiebeperking) {
        this.naderePopulatiebeperking = pNaderePopulatiebeperking;
    }

    /**
     * Zet Nadere populatiebeperking volledig geconverteerd? van Dienstbundel.
     *
     * @param pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd Nadere populatiebeperking volledig geconverteerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(final NeeAttribuut pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd) {
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = pIndicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Zet Toelichting van Dienstbundel.
     *
     * @param pToelichting Toelichting.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setToelichting(final OnbeperkteOmschrijvingAttribuut pToelichting) {
        this.toelichting = pToelichting;
    }

    /**
     * Zet Geblokkeerd? van Dienstbundel.
     *
     * @param pIndicatieGeblokkeerd Geblokkeerd?.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setIndicatieGeblokkeerd(final JaAttribuut pIndicatieGeblokkeerd) {
        this.indicatieGeblokkeerd = pIndicatieGeblokkeerd;
    }

}
