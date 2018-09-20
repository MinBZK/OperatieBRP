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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.*;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisToegangLeveringsautorisatie extends AbstractFormeelHistorischMetActieVerantwoording implements
        ModelIdentificeerbaar<Integer>, BestaansperiodeFormeelImplicietMaterieelAutaut
{

    @Id
    @SequenceGenerator(name = "HIS_TOEGANGLEVERINGSAUTORISATIE", sequenceName = "AutAut.seq_His_ToegangLevsautorisatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_TOEGANGLEVERINGSAUTORISATIE")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "ToegangLevsautorisatie")
    private ToegangLeveringsautorisatie toegangLeveringsautorisatie;

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
    @AttributeOverride(name = UriAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Afleverpunt"))
    @JsonProperty
    private UriAttribuut afleverpunt;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBlok"))
    @JsonProperty
    private JaAttribuut indicatieGeblokkeerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisToegangLeveringsautorisatie() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen.
     *
     * @param toegangLeveringsautorisatie toegangLeveringsautorisatie van HisToegangLeveringsautorisatie
     * @param datumIngang datumIngang van HisToegangLeveringsautorisatie
     * @param datumEinde datumEinde van HisToegangLeveringsautorisatie
     * @param naderePopulatiebeperking naderePopulatiebeperking van HisToegangLeveringsautorisatie
     * @param afleverpunt afleverpunt van HisToegangLeveringsautorisatie
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van HisToegangLeveringsautorisatie
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisToegangLeveringsautorisatie(
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final PopulatiebeperkingAttribuut naderePopulatiebeperking,
        final UriAttribuut afleverpunt,
        final JaAttribuut indicatieGeblokkeerd,
        final ActieModel actieInhoud)
    {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.naderePopulatiebeperking = naderePopulatiebeperking;
        this.afleverpunt = afleverpunt;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisToegangLeveringsautorisatie(final AbstractHisToegangLeveringsautorisatie kopie) {
        super(kopie);
        toegangLeveringsautorisatie = kopie.getToegangLeveringsautorisatie();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        naderePopulatiebeperking = kopie.getNaderePopulatiebeperking();
        afleverpunt = kopie.getAfleverpunt();
        indicatieGeblokkeerd = kopie.getIndicatieGeblokkeerd();

    }

    /**
     * Retourneert ID van His Toegang leveringsautorisatie.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Toegang leveringsautorisatie van His Toegang leveringsautorisatie.
     *
     * @return Toegang leveringsautorisatie.
     */
    public ToegangLeveringsautorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    /**
     * Retourneert Datum ingang van His Toegang leveringsautorisatie.
     *
     * @return Datum ingang.
     */
    public DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Toegang leveringsautorisatie.
     *
     * @return Datum einde.
     */
    public DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Nadere populatiebeperking van His Toegang leveringsautorisatie.
     *
     * @return Nadere populatiebeperking.
     */
    public PopulatiebeperkingAttribuut getNaderePopulatiebeperking() {
        return naderePopulatiebeperking;
    }

    /**
     * Retourneert Afleverpunt van His Toegang leveringsautorisatie.
     *
     * @return Afleverpunt.
     */
    public UriAttribuut getAfleverpunt() {
        return afleverpunt;
    }

    /**
     * Retourneert Geblokkeerd? van His Toegang leveringsautorisatie.
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
        if (datumIngang != null) {
            attributen.add(datumIngang);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (naderePopulatiebeperking != null) {
            attributen.add(naderePopulatiebeperking);
        }
        if (afleverpunt != null) {
            attributen.add(afleverpunt);
        }
        if (indicatieGeblokkeerd != null) {
            attributen.add(indicatieGeblokkeerd);
        }
        return attributen;
    }

}
