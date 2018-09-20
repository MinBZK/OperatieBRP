/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import nl.bzk.migratiebrp.conversie.model.validatie.ValidationUtils;

import org.hibernate.annotations.Any;
import org.hibernate.annotations.AnyMetaDef;
import org.hibernate.annotations.MetaValue;

/**
 * The persistent class for the gegeveninonderzoek database table.
 *
 */
@Entity
@EntityListeners(GegevenInOnderzoekListener.class)
@Table(name = "gegeveninonderzoekormmapping", schema = "kern")
@SuppressWarnings("checkstyle:designforextension")
public class GegevenInOnderzoek extends AbstractDeltaEntiteit implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "gegeveninonderzoek_id_generator", sequenceName = "kern.seq_gegeveninonderzoek", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gegeveninonderzoek_id_generator")
    @Column(nullable = false)
    /**
     * Het veld zou Integer moeten zijn maar is Long i.v.m. de link naar GegevenInOnderzoek.
     */
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "onderzoek", nullable = false)
    private Onderzoek onderzoek;

    @Column(name = "element", nullable = false)
    private int elementId;

    @Any(metaColumn = @Column(name = "tblvoorkomen"))
    @AnyMetaDef(idType = "long", metaType = "int",
            metaValues = {@MetaValue(value = "3211", targetEntity = BetrokkenheidOuderlijkGezagHistorie.class),
                          @MetaValue(value = "3858", targetEntity = BetrokkenheidOuderHistorie.class),
                          @MetaValue(value = "2071", targetEntity = BetrokkenheidHistorie.class),
                          @MetaValue(value = "3784", targetEntity = DocumentHistorie.class),
                          @MetaValue(value = "3599", targetEntity = RelatieHistorie.class),
                          @MetaValue(value = "10841", targetEntity = OnderzoekAfgeleidAdministratiefHistorie.class),
                          @MetaValue(value = "3774", targetEntity = OnderzoekHistorie.class),
                          @MetaValue(value = "4618", targetEntity = PartijHistorie.class),
                          @MetaValue(value = "6063", targetEntity = PersoonAdresHistorie.class),
                          @MetaValue(value = "3909", targetEntity = PersoonAfgeleidAdministratiefHistorie.class),
                          @MetaValue(value = "3664", targetEntity = PersoonBijhoudingHistorie.class),
                          @MetaValue(value = "3901", targetEntity = PersoonDeelnameEuVerkiezingenHistorie.class),
                          @MetaValue(value = "3514", targetEntity = PersoonGeboorteHistorie.class),
                          @MetaValue(value = "3554", targetEntity = PersoonGeslachtsaanduidingHistorie.class),
                          @MetaValue(value = "3598", targetEntity = PersoonGeslachtsnaamcomponentHistorie.class),
                          @MetaValue(value = "3344", targetEntity = PersoonIDHistorie.class),
                          @MetaValue(value = "3654", targetEntity = PersoonIndicatieHistorie.class),
                          @MetaValue(value = "3521", targetEntity = PersoonInschrijvingHistorie.class),
                          @MetaValue(value = "3790", targetEntity = PersoonMigratieHistorie.class),
                          @MetaValue(value = "3487", targetEntity = PersoonNaamgebruikHistorie.class),
                          @MetaValue(value = "3604", targetEntity = PersoonNationaliteitHistorie.class),
                          @MetaValue(value = "10900", targetEntity = PersoonNummerverwijzingHistorie.class),
                          @MetaValue(value = "10763", targetEntity = PersoonOnderzoekHistorie.class),
                          @MetaValue(value = "3515", targetEntity = PersoonOverlijdenHistorie.class),
                          @MetaValue(value = "3662", targetEntity = PersoonPersoonskaartHistorie.class),
                          @MetaValue(value = "3577", targetEntity = PersoonReisdocumentHistorie.class),
                          @MetaValue(value = "3557", targetEntity = PersoonSamengesteldeNaamHistorie.class),
                          @MetaValue(value = "3519", targetEntity = PersoonUitsluitingKiesrechtHistorie.class),
                          @MetaValue(value = "3517", targetEntity = PersoonVerblijfsrechtHistorie.class),
                          @MetaValue(value = "3783", targetEntity = PersoonVerificatieHistorie.class),
                          @MetaValue(value = "9347", targetEntity = PersoonVerstrekkingsbeperkingHistorie.class),
                          @MetaValue(value = "3050", targetEntity = PersoonVoornaamHistorie.class) })
    @JoinColumn(name = "voorkomensleutelgegeven")
    private FormeleHistorie voorkomen;

    @Any(metaColumn = @Column(name = "tblobject"))
    @AnyMetaDef(idType = "long", metaType = "int", metaValues = {@MetaValue(value = "3071", targetEntity = BRPActie.class),
                                                                 @MetaValue(value = "8118", targetEntity = ActieBron.class),
                                                                 @MetaValue(value = "9018", targetEntity = AdministratieveHandeling.class),
                                                                 @MetaValue(value = "3857", targetEntity = Betrokkenheid.class),
                                                                 @MetaValue(value = "3135", targetEntity = Document.class),
                                                                 @MetaValue(value = "3863", targetEntity = GegevenInOnderzoek.class),
                                                                 @MetaValue(value = "3167", targetEntity = Onderzoek.class),
                                                                 @MetaValue(value = "3141", targetEntity = Partij.class),
                                                                 @MetaValue(value = "3010", targetEntity = Persoon.class),
                                                                 @MetaValue(value = "3237", targetEntity = PersoonAdres.class),
                                                                 @MetaValue(value = "3020", targetEntity = PersoonGeslachtsnaamcomponent.class),
                                                                 @MetaValue(value = "3637", targetEntity = PersoonIndicatie.class),
                                                                 @MetaValue(value = "3129", targetEntity = PersoonNationaliteit.class),
                                                                 @MetaValue(value = "6127", targetEntity = PersoonOnderzoek.class),
                                                                 @MetaValue(value = "3576", targetEntity = PersoonReisdocument.class),
                                                                 @MetaValue(value = "3775", targetEntity = PersoonVerificatie.class),
                                                                 @MetaValue(value = "9344", targetEntity = PersoonVerstrekkingsbeperking.class),
                                                                 @MetaValue(value = "3022", targetEntity = PersoonVoornaam.class),
                                                                 @MetaValue(value = "3184", targetEntity = Relatie.class) })
    @JoinColumn(name = "objectsleutelgegeven")
    private DeltaEntiteit deltaEntiteit;

    /**
     * JPA default constructor.
     */
    protected GegevenInOnderzoek() {
    }

    /**
     * Maak een nieuwe gegeven in onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     * @param soortGegeven
     *            soort gegeven
     */
    public GegevenInOnderzoek(final Onderzoek onderzoek, final Element soortGegeven) {
        setOnderzoek(onderzoek);
        setSoortGegeven(soortGegeven);

    }

    /**
     * Geef de Id waarde voor de entiteit. Intern is de Id een Long voor integratie met GegevenInOnderzoek. De waarde
     * wordt geconverteerd naar een Integer.
     *
     * @return de Id waarde.
     */
    public Integer getId() {
        return convertLongNaarInteger(id);
    }

    /**
     * Zet de Id waarde voor de entiteit. Intern wordt de Id waarde geconverteert naar een Long voor integratie met
     * GegevenInOnderzoek.
     *
     * @param id
     *            de Id waarde.
     */
    public void setId(final Integer id) {
        this.id = convertIntegerNaarLong(id);
    }

    /**
     * Geef de waarde van onderzoek.
     *
     * @return onderzoek
     */
    public Onderzoek getOnderzoek() {
        return onderzoek;
    }

    /**
     * Zet de waarde van onderzoek.
     *
     * @param onderzoek
     *            onderzoek
     */
    public void setOnderzoek(final Onderzoek onderzoek) {
        ValidationUtils.controleerOpNullWaarden("onderzoek mag niet null zijn", onderzoek);
        this.onderzoek = onderzoek;
    }

    /**
     * Geef de waarde van soort gegeven.
     *
     * @return soort gegeven
     */
    public Element getSoortGegeven() {
        return Element.parseId((short) elementId);
    }

    /**
     * Zet de waarde van soort gegeven.
     *
     * @param soortGegeven
     *            soort gegeven
     */
    public void setSoortGegeven(final Element soortGegeven) {
        ValidationUtils.controleerOpNullWaarden("element mag niet null zijn", soortGegeven);
        elementId = soortGegeven.getId();
    }

    /**
     * Geef de waarde van voorkomen.
     *
     * @return voorkomen
     */
    public FormeleHistorie getVoorkomen() {
        return voorkomen;
    }

    /**
     * Zet de waarde van voorkomen.
     *
     * @param voorkomen
     *            voorkomen
     */
    public void setVoorkomen(final FormeleHistorie voorkomen) {
        setObjectOfVoorkomen(voorkomen);
    }

    /**
     * Koppelt het {@link DeltaEntiteit} wat in onderzoek staat met dit object. Als de huidige instantie van dit object
     * al een koppeling had, dan wordt deze eerst verbroken voordat de nieuwe koppeling wordt vastgelegd. Ook
     * registreert dit object zich bij het {@link DeltaEntiteit} object zodat de koppeling tweezijdig is.
     *
     * @param deltaEntiteitParam
     *            het {@link DeltaEntiteit} object wat in onderzoek staat.
     */
    public void setObjectOfVoorkomen(final DeltaEntiteit deltaEntiteitParam) {
        ValidationUtils.controleerOpNullWaarden("Het gegeven in dat in onderzoek staat mag geen NULL zijn", deltaEntiteitParam);
        verbreekBestaandeKoppelingDeltaEntiteit();
        if (deltaEntiteitParam instanceof FormeleHistorie) {
            this.voorkomen = (FormeleHistorie) deltaEntiteitParam;

        } else {
            this.deltaEntiteit = deltaEntiteitParam;
        }

        if (deltaEntiteitParam != null) {
            deltaEntiteitParam.setGegevenInOnderzoek(this);
        }
    }

    /**
     * Verbreekt de koppeling vanuit een {@link DeltaEntiteit} of {@link FormeleHistorie} naar deze instantie.
     */
    private void verbreekBestaandeKoppelingDeltaEntiteit() {
        final DeltaEntiteit entiteit = getObjectOfVoorkomen();
        if (entiteit != null) {
            entiteit.removeGegevenInOnderzoek(getSoortGegeven());
        }
    }

    /**
     * Geef de waarde van object.
     *
     * @return object
     */
    public DeltaEntiteit getObject() {
        return deltaEntiteit;
    }

    /**
     * Zet de waarde van entiteit.
     *
     * @param deltaEntiteitParam
     *            entiteit
     */
    public void setObject(final DeltaEntiteit deltaEntiteitParam) {
        setObjectOfVoorkomen(deltaEntiteitParam);
    }

    /**
     * Geef de waarde van object of voorkomen. Er is altijd precies 1 gezet.
     *
     * @return object of voorkomen
     */
    public DeltaEntiteit getObjectOfVoorkomen() {
        return deltaEntiteit != null ? deltaEntiteit : voorkomen;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " -> " + (getObjectOfVoorkomen() != null ? getObjectOfVoorkomen().getClass().getSimpleName() : null);
    }
}
