/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LengteInCm;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReisdocumentNummer;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AutoriteitVanAfgifteReisdocument;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVervallenReisdocument;
import nl.bzk.brp.model.logisch.kern.PersoonReisdocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonReisdocumentStandaardGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * 1. Vorm van historie: alleen formeel. Motivatie: de gegevens van een reisdocument zijn enerzijds de gegevens die in
 * het document staan, zoals lengte van de houder. Anderzijds zijn het gegevens die normaliter ��nmalig wijzigen, zoals
 * reden vervallen.
 * Omdat hetzelfde reisdocument niet tweemaal wordt uitgegeven, is formele historie voldoende.
 * RvdP 26 jan 2012.
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonReisdocumentStandaardGroepModel implements PersoonReisdocumentStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "Nr"))
    @JsonProperty
    private ReisdocumentNummer               nummer;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "LengteHouder"))
    @JsonProperty
    private LengteInCm                       lengteHouder;

    @ManyToOne
    @JoinColumn(name = "AutVanAfgifte")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatIngangDoc"))
    @JsonProperty
    private Datum                            datumIngangDocument;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatUitgifte"))
    @JsonProperty
    private Datum                            datumUitgifte;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatVoorzeEindeGel"))
    @JsonProperty
    private Datum                            datumVoorzieneEindeGeldigheid;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "DatInhingVermissing"))
    @JsonProperty
    private Datum                            datumInhoudingVermissing;

    @ManyToOne
    @JoinColumn(name = "RdnVervallen")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private RedenVervallenReisdocument       redenVervallen;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected AbstractPersoonReisdocumentStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     * CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param nummer nummer van Standaard.
     * @param lengteHouder lengteHouder van Standaard.
     * @param autoriteitVanAfgifte autoriteitVanAfgifte van Standaard.
     * @param datumIngangDocument datumIngangDocument van Standaard.
     * @param datumUitgifte datumUitgifte van Standaard.
     * @param datumVoorzieneEindeGeldigheid datumVoorzieneEindeGeldigheid van Standaard.
     * @param datumInhoudingVermissing datumInhoudingVermissing van Standaard.
     * @param redenVervallen redenVervallen van Standaard.
     */
    public AbstractPersoonReisdocumentStandaardGroepModel(final ReisdocumentNummer nummer,
            final LengteInCm lengteHouder, final AutoriteitVanAfgifteReisdocument autoriteitVanAfgifte,
            final Datum datumIngangDocument, final Datum datumUitgifte, final Datum datumVoorzieneEindeGeldigheid,
            final Datum datumInhoudingVermissing, final RedenVervallenReisdocument redenVervallen)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.nummer = nummer;
        this.lengteHouder = lengteHouder;
        this.autoriteitVanAfgifte = autoriteitVanAfgifte;
        this.datumIngangDocument = datumIngangDocument;
        this.datumUitgifte = datumUitgifte;
        this.datumVoorzieneEindeGeldigheid = datumVoorzieneEindeGeldigheid;
        this.datumInhoudingVermissing = datumInhoudingVermissing;
        this.redenVervallen = redenVervallen;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonReisdocumentStandaardGroep te kopieren groep.
     */
    public AbstractPersoonReisdocumentStandaardGroepModel(
            final PersoonReisdocumentStandaardGroep persoonReisdocumentStandaardGroep)
    {
        this.nummer = persoonReisdocumentStandaardGroep.getNummer();
        this.lengteHouder = persoonReisdocumentStandaardGroep.getLengteHouder();
        this.autoriteitVanAfgifte = persoonReisdocumentStandaardGroep.getAutoriteitVanAfgifte();
        this.datumIngangDocument = persoonReisdocumentStandaardGroep.getDatumIngangDocument();
        this.datumUitgifte = persoonReisdocumentStandaardGroep.getDatumUitgifte();
        this.datumVoorzieneEindeGeldigheid = persoonReisdocumentStandaardGroep.getDatumVoorzieneEindeGeldigheid();
        this.datumInhoudingVermissing = persoonReisdocumentStandaardGroep.getDatumInhoudingVermissing();
        this.redenVervallen = persoonReisdocumentStandaardGroep.getRedenVervallen();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReisdocumentNummer getNummer() {
        return nummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LengteInCm getLengteHouder() {
        return lengteHouder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AutoriteitVanAfgifteReisdocument getAutoriteitVanAfgifte() {
        return autoriteitVanAfgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumIngangDocument() {
        return datumIngangDocument;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumUitgifte() {
        return datumUitgifte;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumVoorzieneEindeGeldigheid() {
        return datumVoorzieneEindeGeldigheid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Datum getDatumInhoudingVermissing() {
        return datumInhoudingVermissing;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVervallenReisdocument getRedenVervallen() {
        return redenVervallen;
    }

}
