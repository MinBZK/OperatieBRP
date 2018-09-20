/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.impl.ist;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.ist.StapelVoorkomenHisVolledigBasis;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieGerelateerdenGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieGezagsverhoudingGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenCategorieOudersGroepModel;
import nl.bzk.brp.model.operationeel.ist.StapelVoorkomenStandaardGroepModel;

/**
 * HisVolledig klasse voor Stapel voorkomen.
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.HisVolledigModelGenerator")
@MappedSuperclass
public abstract class AbstractStapelVoorkomenHisVolledigImpl implements HisVolledigImpl, StapelVoorkomenHisVolledigBasis {

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Stapel")
    @JsonBackReference
    private StapelHisVolledigImpl stapel;

    @Embedded
    @AttributeOverride(name = VolgnummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Volgnr"))
    @JsonProperty
    private VolgnummerAttribuut volgnummer;

    @Embedded
    private StapelVoorkomenStandaardGroepModel standaard;

    @Embedded
    private StapelVoorkomenCategorieOudersGroepModel categorieOuders;

    @Embedded
    private StapelVoorkomenCategorieGerelateerdenGroepModel categorieGerelateerden;

    @Embedded
    private StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel categorieHuwelijkGeregistreerdPartnerschap;

    @Embedded
    private StapelVoorkomenCategorieGezagsverhoudingGroepModel categorieGezagsverhouding;

    /**
     * Default contructor voor JPA.
     *
     */
    protected AbstractStapelVoorkomenHisVolledigImpl() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param stapel stapel van Stapel voorkomen.
     * @param volgnummer volgnummer van Stapel voorkomen.
     */
    public AbstractStapelVoorkomenHisVolledigImpl(final StapelHisVolledigImpl stapel, final VolgnummerAttribuut volgnummer) {
        this();
        this.stapel = stapel;
        this.volgnummer = volgnummer;

    }

    /**
     * Retourneert ID van Stapel voorkomen.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "STAPELVOORKOMEN", sequenceName = "IST.seq_StapelVoorkomen")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "STAPELVOORKOMEN")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Stapel van Stapel voorkomen.
     *
     * @return Stapel.
     */
    public StapelHisVolledigImpl getStapel() {
        return stapel;
    }

    /**
     * Retourneert Volgnummer van Stapel voorkomen.
     *
     * @return Volgnummer.
     */
    public VolgnummerAttribuut getVolgnummer() {
        return volgnummer;
    }

    /**
     * Setter is verplicht voor JPA, omdat de Id annotatie op de getter zit. We maken de functie private voor de
     * zekerheid.
     *
     * @param id Id
     */
    private void setID(final Integer id) {
        this.iD = id;
    }

    /**
     * Zet Standaard van Stapel voorkomen.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final StapelVoorkomenStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Categorie ouders van Stapel voorkomen.
     *
     * @param categorieOuders Categorie ouders.
     */
    public void setCategorieOuders(final StapelVoorkomenCategorieOudersGroepModel categorieOuders) {
        this.categorieOuders = categorieOuders;
    }

    /**
     * Zet Categorie gerelateerden van Stapel voorkomen.
     *
     * @param categorieGerelateerden Categorie gerelateerden.
     */
    public void setCategorieGerelateerden(final StapelVoorkomenCategorieGerelateerdenGroepModel categorieGerelateerden) {
        this.categorieGerelateerden = categorieGerelateerden;
    }

    /**
     * Zet Categorie Huwelijk/Geregistreerd partnerschap van Stapel voorkomen.
     *
     * @param categorieHuwelijkGeregistreerdPartnerschap Categorie Huwelijk/Geregistreerd partnerschap.
     */
    public void setCategorieHuwelijkGeregistreerdPartnerschap(
        final StapelVoorkomenCategorieHuwelijkGeregistreerdPartnerschapGroepModel categorieHuwelijkGeregistreerdPartnerschap)
    {
        this.categorieHuwelijkGeregistreerdPartnerschap = categorieHuwelijkGeregistreerdPartnerschap;
    }

    /**
     * Zet Categorie gezagsverhouding van Stapel voorkomen.
     *
     * @param categorieGezagsverhouding Categorie gezagsverhouding.
     */
    public void setCategorieGezagsverhouding(final StapelVoorkomenCategorieGezagsverhoudingGroepModel categorieGezagsverhouding) {
        this.categorieGezagsverhouding = categorieGezagsverhouding;
    }

}
