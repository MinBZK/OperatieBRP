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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractDynamischObject;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatie;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatieBasis;

/**
 * Verificatie van gegevens in de BRP.
 *
 * Vooral voor personen die zich in het buitenland bevinden, is de kans groot dat gegevens snel verouderen. Zo is het
 * niet gegarandeerd dat een overlijden van een niet-ingezetene snel wordt gemeld. Om die reden is het, (vooral) voor de
 * populatie waarvan de bijhoudingsverantwoordelijkheid bij de Minister ligt, van belang om te weten of er verificatie
 * heeft plaatsgevonden. Het kan bijvoorbeeld zo zijn dat een Aangewezen bestuursorgaan ('RNI deelnemer') recent nog
 * contact heeft gehad met de persoon, en dat dit tot verificatie van gegevens heeft geleid. Er zijn verschillende
 * soorten verificatie; de bekendste is de 'Attestie de vita' die aangeeft dat de persoon nog in leven was ten tijde van
 * de verificatie. Door verificatiegegevens te registreren, kan de actualiteit van de persoonsgegevens van de
 * niet-ingezetene beter op waarde worden geschat.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonVerificatieModel extends AbstractDynamischObject implements PersoonVerificatieBasis, ModelIdentificeerbaar<Long> {

    @Transient
    @JsonProperty
    private Long iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Geverifieerde")
    private PersoonModel geverifieerde;

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "Partij"))
    @JsonProperty
    private PartijAttribuut partij;

    @Embedded
    @AttributeOverride(name = NaamEnumeratiewaardeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Srt"))
    @JsonProperty
    private NaamEnumeratiewaardeAttribuut soort;

    @Embedded
    @JsonProperty
    private PersoonVerificatieStandaardGroepModel standaard;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonVerificatieModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param partij partij van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     */
    public AbstractPersoonVerificatieModel(final PersoonModel geverifieerde, final PartijAttribuut partij, final NaamEnumeratiewaardeAttribuut soort) {
        this();
        this.geverifieerde = geverifieerde;
        this.partij = partij;
        this.soort = soort;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonVerificatie Te kopieren object type.
     * @param geverifieerde Bijbehorende Persoon.
     */
    public AbstractPersoonVerificatieModel(final PersoonVerificatie persoonVerificatie, final PersoonModel geverifieerde) {
        this();
        this.geverifieerde = geverifieerde;
        this.partij = persoonVerificatie.getPartij();
        this.soort = persoonVerificatie.getSoort();
        if (persoonVerificatie.getStandaard() != null) {
            this.standaard = new PersoonVerificatieStandaardGroepModel(persoonVerificatie.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Verificatie.
     *
     * @return ID.
     */
    @Id
    @SequenceGenerator(name = "PERSOONVERIFICATIE", sequenceName = "Kern.seq_PersVerificatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVERIFICATIE")
    @Access(value = AccessType.PROPERTY)
    public Long getID() {
        return iD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonModel getGeverifieerde() {
        return geverifieerde;
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
    public NaamEnumeratiewaardeAttribuut getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonVerificatieStandaardGroepModel getStandaard() {
        return standaard;
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
     * Zet Standaard van Persoon \ Verificatie.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonVerificatieStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Geeft alle groepen van de object met uitzondering van groepen die null zijn.
     *
     * @return Lijst met groepen.
     */
    public List<Groep> getGroepen() {
        final List<Groep> groepen = new ArrayList<>();
        if (standaard != null) {
            groepen.add(standaard);
        }
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
        if (soort != null) {
            attributen.add(soort);
        }
        return attributen;
    }

}
