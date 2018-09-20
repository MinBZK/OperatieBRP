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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenWijzigingVerblijf;
import nl.bzk.brp.model.basis.StamgegevenEntityListener;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Immutable;

/**
 * Conversietabel ten behoeve van de aangifte adreshouding (LO3) enerzijds, en de aangever adreshouding en reden
 * wijziging (BRP) anderzijds.
 *
 * Bij de conversie wordt de waarde van de rubriek (LO3) vertaalt naar een waarde van de aangever adreshouding en/of een
 * waarde van reden wijziging (BRP).
 *
 * Van de naamgevingsconventie beschreven bij het schema conversie, wordt in deze tabel afgeweken: aangever adreshouding
 * en reden wijziging adres hebben niet de naam van de bijbehorende attribuuttypen (die in dit geval ook objecttypen
 * zijn). Reden hiervoor is dat de benodigde impact hiervan voor nu "te groot" wordt geacht, gezien het kleine belang.
 * Wel is voorzien dat - indien een andere wijziging het bijbehorende stuk software sowieso doet aanpassen - deze
 * wijziging wellicht met een andere wijziging meelift.
 *
 *
 *
 */
@Access(value = AccessType.FIELD)
@EntityListeners(value = StamgegevenEntityListener.class)
@Immutable
@Generated(value = "nl.bzk.brp.generatoren.java.DynamischeStamgegevensGenerator")
@MappedSuperclass
public abstract class AbstractConversieAangifteAdreshouding {

    @Id
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr7210OmsVanDeAangifteAdre"))
    @JsonProperty
    private LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut rubriek7210OmschrijvingVanDeAangifteAdreshouding;

    @ManyToOne
    @JoinColumn(name = "Aang")
    @Fetch(value = FetchMode.JOIN)
    private Aangever aangever;

    @ManyToOne
    @JoinColumn(name = "RdnWijzVerblijf")
    @Fetch(value = FetchMode.JOIN)
    private RedenWijzigingVerblijf redenWijzigingVerblijf;

    /**
     * Constructor is protected, want de BRP zal geen stamgegevens beheren.
     *
     */
    protected AbstractConversieAangifteAdreshouding() {
    }

    /**
     * Constructor die direct alle attributen instantieert.
     *
     * @param rubriek7210OmschrijvingVanDeAangifteAdreshouding rubriek7210OmschrijvingVanDeAangifteAdreshouding van
     *            ConversieAangifteAdreshouding.
     * @param aangever aangever van ConversieAangifteAdreshouding.
     * @param redenWijzigingVerblijf redenWijzigingVerblijf van ConversieAangifteAdreshouding.
     */
    protected AbstractConversieAangifteAdreshouding(
        final LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut rubriek7210OmschrijvingVanDeAangifteAdreshouding,
        final Aangever aangever,
        final RedenWijzigingVerblijf redenWijzigingVerblijf)
    {
        this.rubriek7210OmschrijvingVanDeAangifteAdreshouding = rubriek7210OmschrijvingVanDeAangifteAdreshouding;
        this.aangever = aangever;
        this.redenWijzigingVerblijf = redenWijzigingVerblijf;

    }

    /**
     * Retourneert ID van Conversie aangifte adreshouding.
     *
     * @return ID.
     */
    protected Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 7210 Omschrijving van de aangifte adreshouding van Conversie aangifte adreshouding.
     *
     * @return Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    public final LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut getRubriek7210OmschrijvingVanDeAangifteAdreshouding() {
        return rubriek7210OmschrijvingVanDeAangifteAdreshouding;
    }

    /**
     * Retourneert Aangever van Conversie aangifte adreshouding.
     *
     * @return Aangever.
     */
    public final Aangever getAangever() {
        return aangever;
    }

    /**
     * Retourneert Reden wijziging verblijf van Conversie aangifte adreshouding.
     *
     * @return Reden wijziging verblijf.
     */
    public final RedenWijzigingVerblijf getRedenWijzigingVerblijf() {
        return redenWijzigingVerblijf;
    }

}
