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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut;
import nl.bzk.brp.model.beheer.kern.Aangever;
import nl.bzk.brp.model.beheer.kern.RedenWijzigingVerblijf;

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
@Entity(name = "beheer.ConversieAangifteAdreshouding")
@Table(schema = "Conv", name = "ConvAangifteAdresh")
@Generated(value = "nl.bzk.brp.generatoren.java.BeheerGenerator")
@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public class ConversieAangifteAdreshouding {

    @Id
    @SequenceGenerator(name = "CONVERSIEAANGIFTEADRESHOUDING", sequenceName = "Conv.seq_ConvAangifteAdresh")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "CONVERSIEAANGIFTEADRESHOUDING")
    @JsonProperty
    private Integer iD;

    @Embedded
    @AttributeOverride(name = LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Rubr7210OmsVanDeAangifteAdre"))
    private LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut rubriek7210OmschrijvingVanDeAangifteAdreshouding;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Aang")
    private Aangever aangever;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RdnWijzVerblijf")
    private RedenWijzigingVerblijf redenWijzigingVerblijf;

    /**
     * Retourneert ID van Conversie aangifte adreshouding.
     *
     * @return ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Integer getID() {
        return iD;
    }

    /**
     * Retourneert Rubriek 7210 Omschrijving van de aangifte adreshouding van Conversie aangifte adreshouding.
     *
     * @return Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut getRubriek7210OmschrijvingVanDeAangifteAdreshouding() {
        return rubriek7210OmschrijvingVanDeAangifteAdreshouding;
    }

    /**
     * Retourneert Aangever van Conversie aangifte adreshouding.
     *
     * @return Aangever.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public Aangever getAangever() {
        return aangever;
    }

    /**
     * Retourneert Reden wijziging verblijf van Conversie aangifte adreshouding.
     *
     * @return Reden wijziging verblijf.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public RedenWijzigingVerblijf getRedenWijzigingVerblijf() {
        return redenWijzigingVerblijf;
    }

    /**
     * Zet ID van Conversie aangifte adreshouding.
     *
     * @param pID ID.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setID(final Integer pID) {
        this.iD = pID;
    }

    /**
     * Zet Rubriek 7210 Omschrijving van de aangifte adreshouding van Conversie aangifte adreshouding.
     *
     * @param pRubriek7210OmschrijvingVanDeAangifteAdreshouding Rubriek 7210 Omschrijving van de aangifte adreshouding.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRubriek7210OmschrijvingVanDeAangifteAdreshouding(
        final LO3OmschrijvingVanDeAangifteAdreshoudingAttribuut pRubriek7210OmschrijvingVanDeAangifteAdreshouding)
    {
        this.rubriek7210OmschrijvingVanDeAangifteAdreshouding = pRubriek7210OmschrijvingVanDeAangifteAdreshouding;
    }

    /**
     * Zet Aangever van Conversie aangifte adreshouding.
     *
     * @param pAangever Aangever.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setAangever(final Aangever pAangever) {
        this.aangever = pAangever;
    }

    /**
     * Zet Reden wijziging verblijf van Conversie aangifte adreshouding.
     *
     * @param pRedenWijzigingVerblijf Reden wijziging verblijf.
     */
    @SuppressWarnings("checkstyle:designforextension")
    public void setRedenWijzigingVerblijf(final RedenWijzigingVerblijf pRedenWijzigingVerblijf) {
        this.redenWijzigingVerblijf = pRedenWijzigingVerblijf;
    }

}
