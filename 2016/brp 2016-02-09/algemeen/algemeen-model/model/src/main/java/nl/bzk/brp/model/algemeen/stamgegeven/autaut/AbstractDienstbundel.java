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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractDienstbundel {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Levsautorisatie")
    @Fetch(value = FetchMode.JOIN)
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

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDienstbundel() {
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param leveringsautorisatie leveringsautorisatie van Dienstbundel.
     * @param naam naam van Dienstbundel.
     * @param datumIngang datumIngang van Dienstbundel.
     * @param datumEinde datumEinde van Dienstbundel.
     * @param naderePopulatiebeperking naderePopulatiebeperking van Dienstbundel.
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     *            indicatieNaderePopulatiebeperkingVolledigGeconverteerd van Dienstbundel.
     * @param toelichting toelichting van Dienstbundel.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Dienstbundel.
     */
    protected AbstractDienstbundel(
        final Leveringsautorisatie leveringsautorisatie,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.leveringsautorisatie = leveringsautorisatie;
        this.naam = naam;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
        this.toelichting = toelichting;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;

    }

    /**
     * Retourneert ID van Dienstbundel.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Leveringsautorisatie van Dienstbundel.
     *
     * @return Leveringsautorisatie.
     */
    public final Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    /**
     * Retourneert Naam van Dienstbundel.
     *
     * @return Naam.
     */
    public final NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van Dienstbundel.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Dienstbundel.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van Dienstbundel.
     *
     * @return Nadere populatiebeperking.
     */
    public final PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Nadere populatiebeperking volledig geconverteerd? van Dienstbundel.
     *
     * @return Nadere populatiebeperking volledig geconverteerd?.
     */
    public final NeeAttribuut getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van Dienstbundel.
     *
     * @return Toelichting.
     */
    public final OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van Dienstbundel.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }
}
