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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.StatusHistorie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.basis.AbstractDynamischObjectType;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.model.logisch.kern.basis.PersoonNationaliteitBasis;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonNationaliteitStandaardGroepModel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;


/**
 * De juridische band tussen een persoon en een staat zoals bedoeld in het Europees verdrag inzake nationaliteit,
 * Straatsburg 06-11-1997.
 *
 * Indien iemand tegelijk meerdere nationaliteiten heeft, zijn dit ook aparte exemplaren van Nationaliteit. Indien
 * aannemelijk is dat iemand een Nationaliteit heeft, maar deze is onbekend, dat wordt dit vastgelegd als 'Onbekend'.
 * Situaties als 'behandeld als Nederlander', 'Vastgesteld niet-Nederlander' en 'Staatloos' worden geregistreerd onder
 * 'overige indicaties', en niet als Nationaliteit.
 *
 * 1. Waarden 'Vastgesteld niet-Nederlander', 'Behandelen als Nederlander' en 'Statenloos' worden niet geregistreerd als
 * Nationaliteit, maar onder een aparte groep. Motivatie voor het apart behandelen van 'Vastgesteld niet-Nederlander',
 * is dat het een expliciete uitspraak is (van een rechter), dat iemand geen Nederlander (meer) is. Deze waarde kan best
 * n��st bijvoorbeeld een Belgische Nationaliteit gelden, en moet niet worden gezien als 'deelinformatie' over de
 * Nationaliteit. De 'Behandelen als Nederlander' gaat over de wijze van behandeling, en past dientengevolge minder goed
 * als waarde voor 'Nationaliteit'. Als er (vermoedelijk) wel een Nationaliteit is, alleen die is onbekend, d�n wordt
 * ""Onbekend"" ingevuld. 2.Nationaliteit is niet sterk gedefinieerd. Het wijkt af van de Wikipedia definitie (althans,
 * op 23 februari 2011). Beste bron lijkt een Europees verdrag (Europees Verdrag inzake nationaliteit, Straatsburg,
 * 06-11-1997), en dan de Nederlandse vertaling ervan: ""de juridische band tussen een persoon en een Staat; deze term
 * verwijst niet naar de etnische afkomst van de persoon"". Deze zin loop niet heel lekker en is ook niet ��nduidig (er
 * is ook een juridische band tussen een president van een Staat en die Staat ten gevolge van het presidentschap, en die
 * wordt niet bedoeld). Daarom gekozen voor deze definitie met verwijzing. Verder goed om in de toelichting te
 * beschrijven hoe wordt omgegaan met meerdere Nationaliteiten (ook wel dubbele Nationaliteiten genoemd), dan wel
 * statenloosheid en vastgesteld niet-nederlander.
 *
 * HUP text: omgaan met niet erkende nationaliteiten (bijv. palestijnse authoriteit)
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
public abstract class AbstractPersoonNationaliteitModel extends AbstractDynamischObjectType implements
        PersoonNationaliteitBasis
{

    @Id
    @SequenceGenerator(name = "PERSOONNATIONALITEIT", sequenceName = "Kern.seq_PersNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "PERSOONNATIONALITEIT")
    @JsonProperty
    private Integer                                 iD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pers")
    private PersoonModel                            persoon;

    @ManyToOne
    @JoinColumn(name = "Nation")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Nationaliteit                           nationaliteit;

    @Embedded
    @JsonProperty
    private PersoonNationaliteitStandaardGroepModel standaard;

    @Type(type = "StatusHistorie")
    @Column(name = "PersNationStatusHis")
    @JsonProperty
    private StatusHistorie                          persoonNationaliteitStatusHis;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonNationaliteitModel() {
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoon persoon van Persoon \ Nationaliteit.
     * @param nationaliteit nationaliteit van Persoon \ Nationaliteit.
     */
    public AbstractPersoonNationaliteitModel(final PersoonModel persoon, final Nationaliteit nationaliteit) {
        this();
        this.persoon = persoon;
        this.nationaliteit = nationaliteit;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteit Te kopieren object type.
     * @param persoon Bijbehorende Persoon.
     */
    public AbstractPersoonNationaliteitModel(final PersoonNationaliteit persoonNationaliteit, final PersoonModel persoon)
    {
        this();
        this.persoon = persoon;
        this.nationaliteit = persoonNationaliteit.getNationaliteit();
        if (persoonNationaliteit.getStandaard() != null) {
            this.standaard = new PersoonNationaliteitStandaardGroepModel(persoonNationaliteit.getStandaard());
        }

    }

    /**
     * Retourneert ID van Persoon \ Nationaliteit.
     *
     * @return ID.
     */
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Persoon van Persoon \ Nationaliteit.
     *
     * @return Persoon.
     */
    public PersoonModel getPersoon() {
        return persoon;
    }

    /**
     * Retourneert Nationaliteit van Persoon \ Nationaliteit.
     *
     * @return Nationaliteit.
     */
    public Nationaliteit getNationaliteit() {
        return nationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersoonNationaliteitStandaardGroepModel getStandaard() {
        return standaard;
    }

    /**
     * Retourneert Persoon \ Nationaliteit StatusHis van Persoon \ Nationaliteit.
     *
     * @return Persoon \ Nationaliteit StatusHis.
     */
    public StatusHistorie getPersoonNationaliteitStatusHis() {
        return persoonNationaliteitStatusHis;
    }

    /**
     * Zet Standaard van Persoon \ Nationaliteit.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PersoonNationaliteitStandaardGroepModel standaard) {
        this.standaard = standaard;
    }

    /**
     * Zet Persoon \ Nationaliteit StatusHis van Persoon \ Nationaliteit.
     *
     * @param persoonNationaliteitStatusHis Persoon \ Nationaliteit StatusHis.
     */
    public void setPersoonNationaliteitStatusHis(final StatusHistorie persoonNationaliteitStatusHis) {
        this.persoonNationaliteitStatusHis = persoonNationaliteitStatusHis;
    }

}
