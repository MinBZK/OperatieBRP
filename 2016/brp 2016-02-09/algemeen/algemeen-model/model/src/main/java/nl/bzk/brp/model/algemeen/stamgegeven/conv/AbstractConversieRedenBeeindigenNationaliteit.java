/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de conversie tussen Reden beëindigen nationaliteit (LO3) en de Reden verlies van
 * Persoon \ Nationaliteit (BRP).
 *
 * Bij de conversie van LO naar BRP wordt de _Rubriek 6410 Reden beëindigen nationaliteit_ opgezocht in deze tabel.
 * Wordt deze gevonden, dan komt de bijbehorende _Reden verlies_ in de Persoon \ Nationaliteit.
 *
 * -Migratie: velden- In het geval van een buitenlandse nationaliteit is er geen bijpassende _Reden verlies_. Om de
 * heen-terug conversie zonder afwijkingen te kunnen uitvoeren, zijn er een aantal Migratie:_ velden opgenomen op de
 * _Persoon \ Nationaliteit_. Zo zal de _Rubriek 6410 Reden beëindigen nationaliteit_ vastgelegd worden in de A:"Persoon
 * \ Nationaliteit.Migratie Reden beeindigen nationaliteit". Er is bewust voor gekozen om geen relatie te leggen tussen
 * de Migratie: velden en de Conversie tabellen. Mocht er namelijk tijdelijk een conversie zijn opgevoerd die achteraf
 * fout is, dan krijg je deze niet meer weg (tenzij je hier complexiteit toevoegt en formele historie introduceert).
 * Daarom wordt verwezen naar de Attribuuttypen en niet de Objecttypen. Na de duale periode kunnen deze _Migratie:_
 * velden vervallen.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieRedenBeeindigenNationaliteit {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = ConversieRedenBeeindigenNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6410RdnBeeindigenNation"))
    @JsonProperty
    private ConversieRedenBeeindigenNationaliteitAttribuut rubriek6410RedenBeeindigenNationaliteit;

    @ManyToOne
    @JoinColumn(name = "RdnVerlies")
    @Fetch(value = FetchMode.JOIN)
    private RedenVerliesNLNationaliteit redenVerlies;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieRedenBeeindigenNationaliteit() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6410RedenBeeindigenNationaliteit rubriek6410RedenBeeindigenNationaliteit van
     *            ConversieRedenBeeindigenNationaliteit.
     * @param redenVerlies redenVerlies van ConversieRedenBeeindigenNationaliteit.
     */
    protected AbstractConversieRedenBeeindigenNationaliteit(
        final ConversieRedenBeeindigenNationaliteitAttribuut rubriek6410RedenBeeindigenNationaliteit,
        final RedenVerliesNLNationaliteit redenVerlies)
    {
        this.rubriek6410RedenBeeindigenNationaliteit = rubriek6410RedenBeeindigenNationaliteit;
        this.redenVerlies = redenVerlies;

    }

    /**
     * Retourneert ID van Conversie reden beëindigen nationaliteit.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6410 Reden beëindigen nationaliteit van Conversie reden beëindigen nationaliteit.
     *
     * @return Rubriek 6410 Reden beëindigen nationaliteit.
     */
    public final ConversieRedenBeeindigenNationaliteitAttribuut getRubriek6410RedenBeeindigenNationaliteit() {
        return rubriek6410RedenBeeindigenNationaliteit;
    }

    /**
     * Retourneert Reden verlies van Conversie reden beëindigen nationaliteit.
     *
     * @return Reden verlies.
     */
    public final RedenVerliesNLNationaliteit getRedenVerlies() {
        return redenVerlies;
    }

}
