/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.lev;

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
import nl.bzk.brp.model.hisvolledig.lev.LeveringHisVolledigBasis;

/**
 * HisVolledig klasse voor Levering.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractLeveringHisVolledigImpl implements HisVolledigImpl, LeveringHisVolledigBasis {

    @Transient
    @JsonProperty
    private Long iD;

    @Column(name = "ToegangAbonnement")
    @JsonProperty
    private Integer toegangAbonnementId;

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
    protected AbstractLeveringHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangAbonnementId toegangAbonnementId van Levering.
     * @param dienstId dienstId van Levering.
     * @param datumTijdKlaarzettenLevering datumTijdKlaarzettenLevering van Levering.
     * @param datumMaterieelSelectie datumMaterieelSelectie van Levering.
     * @param datumAanvangMaterielePeriodeResultaat datumAanvangMaterielePeriodeResultaat van Levering.
     * @param datumEindeMaterielePeriodeResultaat datumEindeMaterielePeriodeResultaat van Levering.
     * @param datumTijdAanvangFormelePeriodeResultaat datumTijdAanvangFormelePeriodeResultaat van Levering.
     * @param datumTijdEindeFormelePeriodeResultaat datumTijdEindeFormelePeriodeResultaat van Levering.
     * @param administratieveHandelingId administratieveHandelingId van Levering.
     * @param soortSynchronisatie soortSynchronisatie van Levering.
     */
    public AbstractLeveringHisVolledigImpl(
        final Integer toegangAbonnementId,
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
        this.toegangAbonnementId = toegangAbonnementId;
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
     * Retourneert ID van Levering.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LEVERING", sequenceName = "Lev.seq_Lev")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "LEVERING")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Toegang abonnement van Levering.
     *
     * @return Toegang abonnement.
     */
    public Integer getToegangAbonnementId() {
        return toegangAbonnementId;
    }

    /**
     * Retourneert Dienst van Levering.
     *
     * @return Dienst.
     */
    public Integer getDienstId() {
        return dienstId;
    }

    /**
     * Retourneert Datum/tijd klaarzetten levering van Levering.
     *
     * @return Datum/tijd klaarzetten levering.
     */
    public DatumTijdAttribuut getDatumTijdKlaarzettenLevering() {
        return datumTijdKlaarzettenLevering;
    }

    /**
     * Retourneert Datum materieel selectie van Levering.
     *
     * @return Datum materieel selectie.
     */
    public DatumAttribuut getDatumMaterieelSelectie() {
        return datumMaterieelSelectie;
    }

    /**
     * Retourneert Datum aanvang materi�le periode resultaat van Levering.
     *
     * @return Datum aanvang materi�le periode resultaat.
     */
    public DatumAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * Retourneert Datum einde materi�le periode resultaat van Levering.
     *
     * @return Datum einde materi�le periode resultaat.
     */
    public DatumAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    /**
     * Retourneert Datum/tijd aanvang formele periode resultaat van Levering.
     *
     * @return Datum/tijd aanvang formele periode resultaat.
     */
    public DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    /**
     * Retourneert Datum/tijd einde formele periode resultaat van Levering.
     *
     * @return Datum/tijd einde formele periode resultaat.
     */
    public DatumTijdAttribuut getDatumTijdEindeFormelePeriodeResultaat() {
        return datumTijdEindeFormelePeriodeResultaat;
    }

    /**
     * Retourneert Administratieve handeling van Levering.
     *
     * @return Administratieve handeling.
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * Retourneert Soort synchronisatie van Levering.
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
