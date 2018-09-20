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
import nl.bzk.brp.model.algemeen.attribuuttype.autaut.SelectieperiodeInMaandenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AttenderingscriteriumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

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
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
//handmatige wijziging: AbstractDienstModel is komen te vervallen, in generatoren zat mogelijk een uitzondering om deze interface hier op te plaatsen
public abstract class AbstractDienst implements VerantwoordingsEntiteit {

    @Id
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Dienstbundel")
    @Fetch(value = FetchMode.JOIN)
    private Dienstbundel dienstbundel;

    @Column(name = "Srt")
    private SoortDienst soort;

    @Column(name = "EffectAfnemerindicaties")
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

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractDienst() {
    }

    /**
     * Constructor die direct alle attributen instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param dienstbundel dienstbundel van Dienst.
     * @param soort soort van Dienst.
     * @param effectAfnemerindicaties effectAfnemerindicaties van Dienst.
     * @param datumIngang datumIngang van Dienst.
     * @param datumEinde datumEinde van Dienst.
     * @param indicatieGeblokkeerd indicatieGeblokkeerd van Dienst.
     * @param attenderingscriterium attenderingscriterium van Dienst.
     * @param eersteSelectiedatum eersteSelectiedatum van Dienst.
     * @param selectieperiodeInMaanden selectieperiodeInMaanden van Dienst.
     */
    protected AbstractDienst(
        final Dienstbundel dienstbundel,
        final SoortDienst soort,
        final EffectAfnemerindicaties effectAfnemerindicaties,
        final DatumAttribuut datumIngang,
        final DatumAttribuut datumEinde,
        final JaAttribuut indicatieGeblokkeerd,
        final AttenderingscriteriumAttribuut attenderingscriterium,
        final DatumAttribuut eersteSelectiedatum,
        final SelectieperiodeInMaandenAttribuut selectieperiodeInMaanden)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.dienstbundel = dienstbundel;
        this.soort = soort;
        this.effectAfnemerindicaties = effectAfnemerindicaties;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.indicatieGeblokkeerd = indicatieGeblokkeerd;
        this.attenderingscriterium = attenderingscriterium;
        this.eersteSelectiedatum = eersteSelectiedatum;
        this.selectieperiodeInMaanden = selectieperiodeInMaanden;

    }

    /**
     * Retourneert ID van Dienst.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Dienstbundel van Dienst.
     *
     * @return Dienstbundel.
     */
    public final Dienstbundel getDienstbundel() {
        return dienstbundel;
    }

    /**
     * Retourneert Soort van Dienst.
     *
     * @return Soort.
     */
    public final SoortDienst getSoort() {
        return soort;
    }

    /**
     * Retourneert Effect afnemerindicaties van Dienst.
     *
     * @return Effect afnemerindicaties.
     */
    public final EffectAfnemerindicaties getEffectAfnemerindicaties() {
        return effectAfnemerindicaties;
    }

    /**
     * Retourneert Datum ingang van Dienst.
     *
     * @return Datum ingang.
     */
    public final DatumAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van Dienst.
     *
     * @return Datum einde.
     */
    public final DatumAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert Geblokkeerd? van Dienst.
     *
     * @return Geblokkeerd?.
     */
    public final JaAttribuut getIndicatieGeblokkeerd() {
        return indicatieGeblokkeerd;
    }

    /**
     * Retourneert Attenderingscriterium van Dienst.
     *
     * @return Attenderingscriterium.
     */
    public final AttenderingscriteriumAttribuut getAttenderingscriterium() {
        return attenderingscriterium;
    }

    /**
     * Retourneert Eerste selectiedatum van Dienst.
     *
     * @return Eerste selectiedatum.
     */
    public final DatumAttribuut getEersteSelectiedatum() {
        return eersteSelectiedatum;
    }

    /**
     * Retourneert Selectieperiode in maanden van Dienst.
     *
     * @return Selectieperiode in maanden.
     */
    public final SelectieperiodeInMaandenAttribuut getSelectieperiodeInMaanden() {
        return selectieperiodeInMaanden;
    }

}
