/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.beheer.conv;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.beheer.kern.RedenVerliesNLNationaliteit;

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
@Entity(name = "beheer.ConversieRedenBeeindigenNationaliteit")
@Table(schema = "Conv", name = "ConvRdnBeeindigenNation")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieRedenBeeindigenNationaliteit {

    @Id
    @SequenceGenerator(name = "CONVERSIEREDENBEEINDIGENNATIONALITEIT", sequenceName = "Conv.seq_ConvRdnBeeindigenNation")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEREDENBEEINDIGENNATIONALITEIT")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = ConversieRedenBeeindigenNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr6410RdnBeeindigenNation"))
    private ConversieRedenBeeindigenNationaliteitAttribuut rubriek6410RedenBeeindigenNationaliteit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RdnVerlies")
    private RedenVerliesNLNationaliteit redenVerlies;

    /**
     * Retourneert ID van Conversie reden beëindigen nationaliteit.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 6410 Reden beëindigen nationaliteit van Conversie reden beëindigen nationaliteit.
     *
     * @return Rubriek 6410 Reden beëindigen nationaliteit.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public ConversieRedenBeeindigenNationaliteitAttribuut getRubriek6410RedenBeeindigenNationaliteit() {
        return rubriek6410RedenBeeindigenNationaliteit;
    }

    /**
     * Retourneert Reden verlies van Conversie reden beëindigen nationaliteit.
     *
     * @return Reden verlies.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenVerliesNLNationaliteit getRedenVerlies() {
        return redenVerlies;
    }

    /**
     * Zet ID van Conversie reden beëindigen nationaliteit.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 6410 Reden beëindigen nationaliteit van Conversie reden beëindigen nationaliteit.
     *
     * @param pRubriek6410RedenBeeindigenNationaliteit Rubriek 6410 Reden beëindigen nationaliteit.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek6410RedenBeeindigenNationaliteit(final ConversieRedenBeeindigenNationaliteitAttribuut pRubriek6410RedenBeeindigenNationaliteit) {
        this.rubriek6410RedenBeeindigenNationaliteit = pRubriek6410RedenBeeindigenNationaliteit;
    }

    /**
     * Zet Reden verlies van Conversie reden beëindigen nationaliteit.
     *
     * @param pRedenVerlies Reden verlies.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRedenVerlies(final RedenVerliesNLNationaliteit pRedenVerlies) {
        this.redenVerlies = pRedenVerlies;
    }

}
