/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.autaut;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.PopulatiebeperkingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnbeperkteOmschrijvingAttribuut;
import nl.bzk.brp.model.basis.*;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisDienstbundel extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
        BestaansperiodeFormeelImplicietMaterieelAutaut
{

    @Id
    @SequenceGenerator(name = "HIS_DIENSTBUNDEL", sequenceName = "AutAut.seq_His_Dienstbundel")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_DIENSTBUNDEL")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienstbundel")
    private Dienstbundel dienstbundel;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = PopulatiebeperkingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "NaderePopulatiebeperking"))
    @JsonProperty
    private PopulatiebeperkingAttribuut naderePopulatiebeperking;

    @Embedded
    @AttributeOverride(name = NeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndNaderePopbeperkingVolConv"))
    @JsonProperty
    private NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd;

    @Embedded
    @AttributeOverride(name = OnbeperkteOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    @JsonProperty
    private OnbeperkteOmschrijvingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisDienstbundel() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param dienstbundel dienstbundel van HisDienstbundel
     * @param naam naam van HisDienstbundel
     * @param datumIngang datumIngang van HisDienstbundel
     * @param datumEinde datumEinde van HisDienstbundel
     * @param naderePopulatiebeperking naderePopulatiebeperking van HisDienstbundel
     * @param indicatieNaderePopulatiebeperkingVolledigGeconverteerd
     *            indicatieNaderePopulatiebeperkingVolledigGeconverteerd van HisDienstbundel
     * @param toelichting toelichting van HisDienstbundel
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisDienstbundel
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisDienstbundel(
        final Dienstbundel dienstbundel,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final NeeAttribuut indicatieNaderePopulatiebeperkingVolledigGeconverteerd,
        final OnbeperkteOmschrijvingAttribuut toelichting,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.dienstbundel = dienstbundel;
        this.naam = naam;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.indicatieNaderePopulatiebeperkingVolledigGeconverteerd = indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
        this.toelichting = toelichting;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisDienstbundel(final AbstractHisDienstbundel kopie) {
        super(kopie);
        dienstbundel = kopie.getDienstbundel();
        naam = kopie.getNaam();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        naderePopulatiebeperking = kopie.getNaderePopulatiebeperking();
        indicatieNaderePopulatiebeperkingVolledigGeconverteerd = kopie.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd();
        toelichting = kopie.getToelichting();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Retourneert ID van His Dienstbundel.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van His Dienstbundel.
     *
     * @return Dienstbundel.
     */
    public Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Naam van His Dienstbundel.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van His Dienstbundel.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Dienstbundel.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van His Dienstbundel.
     *
     * @return Nadere populatiebeperking.
     */
    public PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Nadere populatiebeperking volledig geconverteerd? van His Dienstbundel.
     *
     * @return Nadere populatiebeperking volledig geconverteerd?.
     */
    public NeeAttribuut getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() {
        return indicatieNaderePopulatiebeperkingVolledigGeconverteerd;
    }

    /**
     * Retourneert Toelichting van His Dienstbundel.
     *
     * @return Toelichting.
     */
    public OnbeperkteOmschrijvingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * Retourneert Geblokkeerd? van His Dienstbundel.
     *
     * @return Geblokkeerd?.
     */
    public JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (naam != null) {
            attributen.add(naam);
        }
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (naderePopulatiebeperking != null) {
            attributen.add(naderePopulatiebeperking);
        }
        if (indicatieNaderePopulatiebeperkingVolledigGeconverteerd != null) {
            attributen.add(indicatieNaderePopulatiebeperkingVolledigGeconverteerd);
        }
        if (toelichting != null) {
            attributen.add(toelichting);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
