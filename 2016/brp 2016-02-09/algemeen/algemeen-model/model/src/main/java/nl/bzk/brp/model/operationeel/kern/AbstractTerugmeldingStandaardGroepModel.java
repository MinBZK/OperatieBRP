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
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.KenmerkTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.TekstTerugmeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusTerugmeldingAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.TerugmeldingStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractTerugmeldingStandaardGroepModel implements TerugmeldingStandaardGroepBasis {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Onderzoek")
    @JsonProperty
    private OnderzoekModel onderzoek;

    @Embedded
    @AttributeOverride(name = StatusTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Status"))
    @JsonProperty
    private StatusTerugmeldingAttribuut status;

    @Embedded
    @AttributeOverride(name = TekstTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Toelichting"))
    @JsonProperty
    private TekstTerugmeldingAttribuut toelichting;

    @Embedded
    @AttributeOverride(name = KenmerkTerugmeldingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "KenmerkMeldendePartij"))
    @JsonProperty
    private KenmerkTerugmeldingAttribuut kenmerkMeldendePartij;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractTerugmeldingStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param onderzoek onderzoek van Standaard.
     * @param status status van Standaard.
     * @param toelichting toelichting van Standaard.
     * @param kenmerkMeldendePartij kenmerkMeldendePartij van Standaard.
     */
    public AbstractTerugmeldingStandaardGroepModel(
        final OnderzoekModel onderzoek,
        final StatusTerugmeldingAttribuut status,
        final TekstTerugmeldingAttribuut toelichting,
        final KenmerkTerugmeldingAttribuut kenmerkMeldendePartij)
    {
        this.onderzoek = onderzoek;
        this.status = status;
        this.toelichting = toelichting;
        this.kenmerkMeldendePartij = kenmerkMeldendePartij;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekModel getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusTerugmeldingAttribuut getStatus() {
        return status;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TekstTerugmeldingAttribuut getToelichting() {
        return toelichting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public KenmerkTerugmeldingAttribuut getKenmerkMeldendePartij() {
        return kenmerkMeldendePartij;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (status != null) {
            attributen.add(status);
        }
        if (toelichting != null) {
            attributen.add(toelichting);
        }
        if (kenmerkMeldendePartij != null) {
            attributen.add(kenmerkMeldendePartij);
        }
        return attributen;
    }

}
