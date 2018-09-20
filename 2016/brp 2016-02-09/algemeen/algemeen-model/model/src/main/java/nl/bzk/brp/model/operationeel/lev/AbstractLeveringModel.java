/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.lev;

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
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatieAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.lev.LeveringBasis;

/**
 * De (voorgenomen) Levering van persoonsgegevens aan een Afnemer.
 *
 * Een Afnemer krijgt gegevens doordat er sprake is van een Abonnement. Vlak voordat de gegevens daadwerkelijk
 * afgeleverd gaan worden, wordt dit geprotocolleerd door een regel weg te schrijven in de Levering tabel.
 *
 * Voorheen was er een link tussen de uitgaande en eventueel inkomende (vraag) berichten. Omdat de bericht tabel
 * geschoond wordt, is deze afhankelijkheid niet wenselijk. Het is daarom ook van belang om alle informatie die
 * noodzakelijk is te kunnen voldoen aan de protocollering hier vast te leggen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractLeveringModel extends AbstractDynamischObject implements LeveringBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @JsonProperty
    @Column(name = "toeganglevsautorisatie")
    private Integer toegangLeveringsautorisatieId;

    @JsonProperty
    @Column(name = "Dienst")
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

    @JsonProperty
    @Column(name = "AdmHnd")
    private Long administratieveHandelingId;

    @Embedded
    @AttributeOverride(name = SoortSynchronisatieAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "SrtSynchronisatie"))
    @JsonProperty
    private SoortSynchronisatieAttribuut soortSynchronisatie;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractLeveringModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft. CHECKSTYLE-BRP:OFF -
     * MAX PARAMS
     *
     * @param toegangLeveringsautorisatieId toegangLeveringsautorisatieId van Levering.
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
    public AbstractLeveringModel(
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
     * Retourneert ID van Levering.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "LEVERING", sequenceName = "prot.seq_levsaantek")
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
    public Integer getToegangLeveringsautorisatieId() {
        return toegangLeveringsautorisatieId;
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
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getDatumTijdKlaarzettenLevering() {
        return datumTijdKlaarzettenLevering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumMaterieelSelectie() {
        return datumMaterieelSelectie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumAanvangMaterielePeriodeResultaat() {
        return datumAanvangMaterielePeriodeResultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumAttribuut getDatumEindeMaterielePeriodeResultaat() {
        return datumEindeMaterielePeriodeResultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getDatumTijdAanvangFormelePeriodeResultaat() {
        return datumTijdAanvangFormelePeriodeResultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * {@inheritDoc}
     */
    @Override
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

    /**
     * Zet Toegang abonnement van Levering.
     *
     * @param toegangLeveringsautorisatieId Toegang abonnement.
     */
    public void setToegangLeveringsautorisatieId(final Integer toegangLeveringsautorisatieId) {
        this.toegangLeveringsautorisatieId = toegangLeveringsautorisatieId;
    }

    /**
     * Zet Dienst van Levering.
     *
     * @param dienstId Dienst.
     */
    public void setDienstId(final Integer dienstId) {
        this.dienstId = dienstId;
    }

    /**
     * Zet Administratieve handeling van Levering.
     *
     * @param administratieveHandelingId Administratieve handeling.
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        return groepen;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van het object.
     */
    public List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumTijdKlaarzettenLevering != null) {
            attributen.add(datumTijdKlaarzettenLevering);
        }
        if (datumMaterieelSelectie != null) {
            attributen.add(datumMaterieelSelectie);
        }
        if (datumAanvangMaterielePeriodeResultaat != null) {
            attributen.add(datumAanvangMaterielePeriodeResultaat);
        }
        if (datumEindeMaterielePeriodeResultaat != null) {
            attributen.add(datumEindeMaterielePeriodeResultaat);
        }
        if (datumTijdAanvangFormelePeriodeResultaat != null) {
            attributen.add(datumTijdAanvangFormelePeriodeResultaat);
        }
        if (datumTijdEindeFormelePeriodeResultaat != null) {
            attributen.add(datumTijdEindeFormelePeriodeResultaat);
        }
        if (soortSynchronisatie != null) {
            attributen.add(soortSynchronisatie);
        }
        return attributen;
    }

}
