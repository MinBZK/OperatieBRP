/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.kern;

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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OINAttribuut;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.ElementIdentificeerbaar;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.operationeel.kern.ActieModel;

/**
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractHisPartij extends AbstractFormeelHistorischMetActieVerantwoording implements ModelIdentificeerbaar<Integer>,
        ElementIdentificeerbaar
{

    @Id
    @SequenceGenerator(name = "HIS_PARTIJ", sequenceName = "Kern.seq_His_Partij")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "HIS_PARTIJ")
    @JsonProperty
    private Integer iD;

    @ManyToOne
    @JoinColumn(name = "Partij")
    private Partij partij;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Naam"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut naam;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatIngang"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumIngang;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = OINAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OIN"))
    @JsonProperty
    private OINAttribuut oIN;

    //handmatige wijziging
    @Embedded
    @AttributeOverride(name = OINAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SoortPartij"))
    @JsonProperty
    private SoortPartijAttribuut soort;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndVerstrbeperkingMogelijk"))
    @JsonProperty
    private JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractHisPartij() {
    }

    /**
     * Constructor voor het initialiseren van alle attributen. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param partij partij van HisPartij
     * @param naam naam van HisPartij
     * @param datumIngang datumIngang van HisPartij
     * @param datumEinde datumEinde van HisPartij
     * @param oIN oIN van HisPartij
     * @param soort soort van HisPartij
     * @param indicatieVerstrekkingsbeperkingMogelijk indicatieVerstrekkingsbeperkingMogelijk van HisPartij
     * @param actieInhoud Verantwoording inhoud; de verantwoording die leidt tot dit nieuwe record.
     */
    public AbstractHisPartij(
        final Partij partij,
        final NaamEnumeratiewaardeAttribuut naam,
        final DatumEvtDeelsOnbekendAttribuut datumIngang,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final OINAttribuut oIN,
        final SoortPartijAttribuut soort,
        final JaNeeAttribuut indicatieVerstrekkingsbeperkingMogelijk,
        final ActieModel actieInhoud)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.partij = partij;
        this.naam = naam;
        this.datumIngang = datumIngang;
        this.datumEinde = datumEinde;
        this.oIN = oIN;
        this.soort = soort;
        this.indicatieVerstrekkingsbeperkingMogelijk = indicatieVerstrekkingsbeperkingMogelijk;
        setVerantwoordingInhoud(actieInhoud);
        getFormeleHistorie().setDatumTijdRegistratie(actieInhoud.getTijdstipRegistratie());

    }

    /**
     * Copy Constructor die op basis van een C/D-laag klasse een C/D-laag klasse construeert.
     *
     * @param kopie backrefence instantie.
     */
    public AbstractHisPartij(final AbstractHisPartij kopie) {
        super(kopie);
        partij = kopie.getPartij();
        naam = kopie.getNaam();
        datumIngang = kopie.getDatumIngang();
        datumEinde = kopie.getDatumEinde();
        oIN = kopie.getOIN();
        soort = kopie.getSoort();
        indicatieVerstrekkingsbeperkingMogelijk = kopie.getIndicatieVerstrekkingsbeperkingMogelijk();

    }

    /**
     * Retourneert ID van His Partij.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Partij van His Partij.
     *
     * @return Partij.
     */
    public Partij getPartij() {
        return partij;
    }

    /**
     * Retourneert Naam van His Partij.
     *
     * @return Naam.
     */
    public NaamEnumeratiewaardeAttribuut getNaam() {
        return naam;
    }

    /**
     * Retourneert Datum ingang van His Partij.
     *
     * @return Datum ingang.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumIngang() {
        return datumIngang;
    }

    /**
     * Retourneert Datum einde van His Partij.
     *
     * @return Datum einde.
     */
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * Retourneert OIN van His Partij.
     *
     * @return OIN.
     */
    public OINAttribuut getOIN() {
        return oIN;
    }

    /**
     * Retourneert Soort van His Partij.
     *
     * @return Soort.
     */
    public SoortPartijAttribuut getSoort() {
        return soort;
    }

    /**
     * Retourneert Verstrekkingsbeperking mogelijk? van His Partij.
     *
     * @return Verstrekkingsbeperking mogelijk?.
     */
    public JaNeeAttribuut getIndicatieVerstrekkingsbeperkingMogelijk() {
        return indicatieVerstrekkingsbeperkingMogelijk;
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
        if (oIN != null) {
            attributen.add(oIN);
        }
        if (soort != null) {
            attributen.add(soort);
        }
        if (indicatieVerstrekkingsbeperkingMogelijk != null) {
            attributen.add(indicatieVerstrekkingsbeperkingMogelijk);
        }
        return attributen;
    }

    /**
     * Retourneert het Element behorende bij deze groep.
     *
     * @return Element enum instantie behorende bij deze groep.
     */
    public final ElementEnum getElementIdentificatie() {
        return ElementEnum.PARTIJ_STANDAARD;
    }

}
