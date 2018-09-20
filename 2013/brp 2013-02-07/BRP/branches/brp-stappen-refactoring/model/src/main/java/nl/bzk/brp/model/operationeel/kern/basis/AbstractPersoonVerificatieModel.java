/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortVerificatie;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonVerificatie;
import nl.bzk.brp.model.logisch.kern.basis.PersoonVerificatieBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVerificatieStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * Verificatie van gegevens in de BRP.
 *
 * Vooral voor personen die zich in het buitenland bevinden, is de kans groot dat gegevens snel verouderen. Zo is het
 * niet gegarandeerd dat een overlijden van een niet-ingezetene snel wordt gemeld.
 * Om die reden is het, (vooral) voor de populatie waarvan de bijhoudingsverantwoordelijkheid bij de Minister ligt, van
 * belang om te weten of er verificatie heeft plaatsgevonden. Het kan bijvoorbeeld zo zijn dat een Aangewezen
 * bestuursorgaan ('RNI deelnemer') recent nog contact heeft gehad met de persoon, en dat dit tot verificatie van
 * gegevens heeft geleid.
 * Er zijn verschillende soorten verificatie; de bekendste is de 'Attestie de vita' die aangeeft dat de persoon nog in
 * leven was ten tijde van de verificatie.
 * Door verificatiegegevens te registreren, kan de actualiteit van de persoonsgegevens van de niet-ingezetene beter op
 * waarde worden geschat.
 *
 * Hier speelt een issue: is het een vrij tekstveld oplossing �we vinden XXX voor persoon�, of is het een beschreven
 * waardebereik. Is nog twistpunt tussen makers LO3.8 en makers LO BRP.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-21 13:38:20.
 * Gegenereerd op: Mon Jan 21 13:42:15 CET 2013.
 */
@Access(value = AccessType.FIELD)
@MappedSuperclass
public abstract class AbstractPersoonVerificatieModel extends AbstractDynamischObjectType implements
        PersoonVerificatieBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONVERIFICATIE", sequenceName = "Kern.seq_PersVerificatie")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONVERIFICATIE")
    @JsonProperty
    private Long                                  iD;

    @ManyToOne
    @JoinColumn(name = "Geverifieerde")
    @JsonProperty
    private PersoonModel                          geverifieerde;

    @ManyToOne
    @JoinColumn(name = "Srt")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private SoortVerificatie                      soort;

    @Embedded
    @JsonProperty
    private PersoonVerificatieStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersVerificatieStatusHis")
    @JsonProperty
    private StatusHistorie                        persoonVerificatieStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonVerificatieModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param geverifieerde geverifieerde van Persoon \ Verificatie.
     * @param soort soort van Persoon \ Verificatie.
     */
    public AbstractPersoonVerificatieModel(final PersoonModel geverifieerde, final SoortVerificatie soort) {
        this();
        this.geverifieerde = geverifieerde;
        this.soort = soort;

    }

    /**
     * Retourneert ID van Persoon \ Verificatie.
     *
     * @return ID.
     */
    public Long getID() {
        return iD;
    }

    /**
     * Retourneert Geverifieerde van Persoon \ Verificatie.
     *
     * @return Geverifieerde.
     */
    public PersoonModel getGeverifieerde() {
        return geverifieerde;
    }

    /**
     * Retourneert Soort van Persoon \ Verificatie.
     *
     * @return Soort.
     */
    public SoortVerificatie getSoort() {
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
     * Retourneert Persoon \ Verificatie StatusHis van Persoon \ Verificatie.
     *
     * @return Persoon \ Verificatie StatusHis.
     */
    public StatusHistorie getPersoonVerificatieStatusHis() {
        return persoonVerificatieStatusHis;
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
     * Zet Persoon \ Verificatie StatusHis van Persoon \ Verificatie.
     *
     * @param persoonVerificatieStatusHis Persoon \ Verificatie StatusHis.
     */
    public void setPersoonVerificatieStatusHis(final StatusHistorie persoonVerificatieStatusHis) {
        this.persoonVerificatieStatusHis = persoonVerificatieStatusHis;
    }

}
