/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AssociationOverride;
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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperking;
import nl.bzk.brp.model.logisch.kern.PersoonVerstrekkingsbeperkingBasis;

/**
 * De verstrekkingsbeperking zoals die voor een in de BRP gekende partij of een in een gemeentelijke verordening
 * benoemde derde voor de persoon van toepassing is.
 *
 * De formele historie van de identiteit groep is geen 'echte' formele historie maar een 'bestaandsperiode'-patroon op
 * formele historie; een verstrekkingsbeperking kan niet stoppen en weer herleven, het is dan een nieuwe
 * verstrekkingsbeperking die niets met de vorige te maken heeft. Feitelijk was er geen his_ tabel nodig en hadden de
 * verantwoordingsvelden (inclusief tijdstippen) direct op de tabel kunnen komen.
 *
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVerstrekkingsbeperkingModel extends AbstractDynamischObject implements PersoonVerstrekkingsbeperkingBasis,
        ModelIdentificeerbaar<Integer>
{

    @Transient
    @JsonProperty
    private Integer iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = OmschrijvingEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OmsDerde"))
    @JsonProperty
    private OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemVerordening"))
    @JsonProperty
    private PartijAttribuut gemeenteVerordening;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonVerstrekkingsbeperkingModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Verstrekkingsbeperking.
     * @param partij partij van Persoon \ Verstrekkingsbeperking.
     * @param omschrijvingDerde omschrijvingDerde van Persoon \ Verstrekkingsbeperking.
     * @param gemeenteVerordening gemeenteVerordening van Persoon \ Verstrekkingsbeperking.
     */
    public AbstractPersoonVerstrekkingsbeperkingModel(
        final PersoonModel persoon,
        final PartijAttribuut partij,
        final OmschrijvingEnumeratiewaardeAttribuut omschrijvingDerde,
        final PartijAttribuut gemeenteVerordening)
    {
        this();
        this.persoon = persoon;
        this.partij = partij;
        this.omschrijvingDerde = omschrijvingDerde;
        this.gemeenteVerordening = gemeenteVerordening;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerstrekkingsbeperking Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonVerstrekkingsbeperkingModel(final PersoonVerstrekkingsbeperking persoonVerstrekkingsbeperking, final PersoonModel persoon) {
        this();
        this.persoon = persoon;
        this.partij = persoonVerstrekkingsbeperking.getPartij();
        this.omschrijvingDerde = persoonVerstrekkingsbeperking.getOmschrijvingDerde();
        this.gemeenteVerordening = persoonVerstrekkingsbeperking.getGemeenteVerordening();

    }

    /**
     * Retourneert ID van Persoon \ Verstrekkingsbeperking.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONVERSTREKKINGSBEPERKING", sequenceName = "Kern.seq_PersVerstrbeperking")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVERSTREKKINGSBEPERKING")
    @Access(value = AccessType.PROPERTY)
    public Integer getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OmschrijvingEnumeratiewaardeAttribuut getOmschrijvingDerde() {
        return omschrijvingDerde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getGemeenteVerordening() {
        return gemeenteVerordening;
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
        if (partij != null) {
            attributen.add(partij);
        }
        if (omschrijvingDerde != null) {
            attributen.add(omschrijvingDerde);
        }
        if (gemeenteVerordening != null) {
            attributen.add(gemeenteVerordening);
        }
        return attributen;
    }

}
