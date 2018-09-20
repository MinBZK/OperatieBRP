/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.prot;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.prot.LeveringsaantekeningHisVolledigBasis;

/**
 * HisVolledig klasse voor Leveringsaantekening.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLeveringsaantekeningHisVolledigImpl implements HisVolledigImpl, LeveringsaantekeningHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @Column(name = "ToegangLevsautorisatie")
    @JsonProperty
    private Integer toegangLeveringsautorisatieId;

    @Column(name = "Dienst")
    @JsonProperty
    private Integer dienstId;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsKlaarzettenLev"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdKlaarzettenLevering;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatMaterieelSelectie"))
    @JsonProperty
    private DatumAttribuut datumMaterieelSelectie;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanvMaterielePeriodeRes"))
    @JsonProperty
    private DatumAttribuut datumAanvangMaterielePeriodeResultaat;

    @Embedded
    @AttributeOverride(name = DatumAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEindeMaterielePeriodeRes"))
    @JsonProperty
    private DatumAttribuut datumEindeMaterielePeriodeResultaat;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsAanvFormelePeriodeRes"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsEindeFormelePeriodeRes"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat;

    @Column(name = "AdmHnd")
    @JsonProperty
    private Long administratieveHandelingId;

    @Embedded
    @AttributeOverride(name = SoortSynchronisatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtSynchronisatie"))
    @JsonProperty
    private SoortSynchronisatieAttribuut soortSynchronisatie;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractLeveringsaantekeningHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangLeveringsautorisatieId toegangLeveringsautorisatieId van Leveringsaantekening.
     * @param dienstId dienstId van Leveringsaantekening.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Leveringsaantekening.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Leveringsaantekening.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Leveringsaantekening.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Leveringsaantekening.
     * @param administratieveHandelingId administratieveHandelingId van Leveringsaantekening.
     * @param soortSynchronisatie soortSynchronisatie van Leveringsaantekening.
     */
    public AbstractLeveringsaantekeningHisVolledigImpl(
        final Integer toegangLeveringsautorisatieId,
        final Integer dienstId,
        final DatumTijdAttribuut datumTijdKlaarzettenLevering,
        final DatumAttribuut datumMaterieelSelectie,
        final DatumAttribuut datumAanvangMaterielePeriodeResultaat,
        final DatumAttribuut datumEindeMaterielePeriodeResultaat,
        final DatumTijdAttribuut datumTijdAanvangFormelePeriodeResultaat,
        final DatumTijdAttribuut datumTijdEindeFormelePeriodeResultaat,
        final Long administratieveHandelingId,
        final SoortSynchronisatieAttribuut soortSynchronisatie)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this();
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
        this.dienstId = dienstId;
        this.datumTijdKlaarzettenLevering = datumTijdKlaarzettenLevering;
        this.datumMaterieelSelectie = datumMaterieelSelectie;
        this.datumAanvangMaterielePeriodeResultaat = datumAanvangMaterielePeriodeResultaat;
        this.datumEindeMaterielePeriodeResultaat = datumEindeMaterielePeriodeResultaat;
        this.datumTijdAanvangFormelePeriodeResultaat = datumTijdAanvangFormelePeriodeResultaat;
        this.datumTijdEindeFormelePeriodeResultaat = datumTijdEindeFormelePeriodeResultaat;
        this.administratieveHandelingId = administratieveHandelingId;
        this.soortSynchronisatie = soortSynchronisatie;

    }

    /**
     * Retourneert ID van Leveringsaantekening.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LEVERINGSAANTEKENING", sequenceName = "Prot.seq_Levsaantek")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERINGSAANTEKENING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Toegang leveringsautorisatie van Leveringsaantekening.
     *
     * @return Toegang leveringsautorisatie.
     */
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
    }

    /**
     * Retourneert Dienst van Leveringsaantekening.
     *
     * @return Dienst.
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Retourneert Datum/tijd klaarzetten levering van Leveringsaantekening.
     *
     * @return Datum/tijd klaarzetten levering.
     */
    public DatumTijdAttribuut getDatumTijdKlaarzettenLevering() {
        return datumTijdKlaarzettenLevering;
    }

    /**
     * Retourneert Datum materieel selectie van Leveringsaantekening.
     *
     * @return Datum materieel selectie.
     */
    public DatumAttribuut getDatumMaterieelSelectie() {
        return datumMaterieelSelectie;
    }

    /**
     * Retourneert Datum aanvang materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum aanvang materiële periode resultaat.
     */
    public DatumAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * Retourneert Datum einde materiële periode resultaat van Leveringsaantekening.
     *
     * @return Datum einde materiële periode resultaat.
     */
    public DatumAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    /**
     * Retourneert Datum/tijd aanvang formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd aanvang formele periode resultaat.
     */
    public DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    /**
     * Retourneert Datum/tijd einde formele periode resultaat van Leveringsaantekening.
     *
     * @return Datum/tijd einde formele periode resultaat.
     */
    public DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat() {
        return datumTijdEindeFormelePeriodeResultaat;
    }

    /**
     * Retourneert Administratieve handeling van Leveringsaantekening.
     *
     * @return Administratieve handeling.
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * Retourneert Soort synchronisatie van Leveringsaantekening.
     *
     * @return Soort synchronisatie.
     */
    public SoortSynchronisatieAttribuut getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Long id) {
        this.iD = id;
    }

}
