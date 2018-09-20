/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.algemeen.stamgegeven.conv;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de conversie tussen Reden be�indigen nationaliteit (LO3) en de Reden verlies van
 * Persoon \ Nationaliteit (BRP).
 *
 * Bij de conversie van LO naar BRP wordt de _Rubriek 6410 Reden be�indigen nationaliteit_ opgezocht in deze tabel.
 * Wordt deze gevonden, dan komt de bijbehorende _Reden verlies_ in de Persoon \ Nationaliteit.
 *
 * -Migratie: velden- In het geval van een buitenlandse nationaliteit is er geen bijpassende _Reden verlies_. Om de
 * heen-terug conversie zonder afwijkingen te kunnen uitvoeren, zijn er een aantal Migratie:_ velden opgenomen op de
 * _Persoon \ Nationaliteit_. Zo zal de _Rubriek 6410 Reden be�indigen nationaliteit_ vastgelegd worden in de A:"Persoon
 * \ Nationaliteit.Migratie: Reden be�indigen nationaliteit". Er is bewust voor gekozen om geen relatie te leggen tussen
 * de Migratie: velden en de Conversie tabellen. Mocht er namelijk tijdelijk een conversie zijn opgevoerd die achteraf
 * fout is, dan krijg je deze niet meer weg (tenzij je hier complexiteit toevoegt en formele historie introduceert).
 * Daarom wordt verwezen naar de Attribuuttypen en niet de Objecttypen. Na de duale periode kunnen deze _Migratie:_
 * velden vervallen.
 *
 *
 *
 */
@Table(schema = "Conv", name = "ConvRdnBeeindigenNation")
@Access(value = AccessType.FIELD)
@Entity
@Immutable
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class ConversieRedenBeeindigenNationaliteit extends AbstractConversieRedenBeeindigenNationaliteit {

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected ConversieRedenBeeindigenNationaliteit() {
        super();
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek6410RedenBeeindigenNationaliteit rubriek6410RedenBeeindigenNationaliteit van
     *            ConversieRedenBeeindigenNationaliteit.
     * @param redenVerlies redenVerlies van ConversieRedenBeeindigenNationaliteit.
     */
    protected ConversieRedenBeeindigenNationaliteit(
        final ConversieRedenBeeindigenNationaliteitAttribuut rubriek6410RedenBeeindigenNationaliteit,
        final RedenVerliesNLNationaliteit redenVerlies)
    {
        super(rubriek6410RedenBeeindigenNationaliteit, redenVerlies);
    }

}
