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
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroep;
import nl.bzk.brp.model.logisch.kern.OnderzoekStandaardGroepBasis;

/**
 * Vorm van historie: formeel. Motivatie: 'onderzoek' is een construct om vast te leggen dat een bepaald gegeven
 * onderwerp is van onderzoek. Hierbij is het in principe alleen relevant of een gegeven NU in onderzoek is. Verder is
 * het voldoende om te weten of tijdens een bepaalde levering een gegeven wel of niet als 'in onderzoek' stond
 * geregistreerd. NB: de gegevens over het onderzoek zelf staan niet in de BRP, maar in bijvoorbeeld de zaaksystemen.
 * Omdat formele historie dus volstaat, wordt de materiële historie onderdrukt.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractOnderzoekStandaardGroepModel implements OnderzoekStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatAanv"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumAanvang;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "VerwachteAfhandeldat"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "DatEinde"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut datumEinde;

    @Embedded
    @AttributeOverride(name = OnderzoekOmschrijvingAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Oms"))
    @JsonProperty
    private OnderzoekOmschrijvingAttribuut omschrijving;

    @Embedded
    @AttributeOverride(name = StatusOnderzoekAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Status"))
    @JsonProperty
    private StatusOnderzoekAttribuut status;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractOnderzoekStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumAanvang datumAanvang van Standaard.
     * @param verwachteAfhandeldatum verwachteAfhandeldatum van Standaard.
     * @param datumEinde datumEinde van Standaard.
     * @param omschrijving omschrijving van Standaard.
     * @param status status van Standaard.
     */
    public AbstractOnderzoekStandaardGroepModel(
        final DatumEvtDeelsOnbekendAttribuut datumAanvang,
        final DatumEvtDeelsOnbekendAttribuut verwachteAfhandeldatum,
        final DatumEvtDeelsOnbekendAttribuut datumEinde,
        final OnderzoekOmschrijvingAttribuut omschrijving,
        final StatusOnderzoekAttribuut status)
    {
        this.datumAanvang = datumAanvang;
        this.verwachteAfhandeldatum = verwachteAfhandeldatum;
        this.datumEinde = datumEinde;
        this.omschrijving = omschrijving;
        this.status = status;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param onderzoekStandaardGroep te kopieren groep.
     */
    public AbstractOnderzoekStandaardGroepModel(final OnderzoekStandaardGroep onderzoekStandaardGroep) {
        this.datumAanvang = onderzoekStandaardGroep.getDatumAanvang();
        this.verwachteAfhandeldatum = onderzoekStandaardGroep.getVerwachteAfhandeldatum();
        this.datumEinde = onderzoekStandaardGroep.getDatumEinde();
        this.omschrijving = onderzoekStandaardGroep.getOmschrijving();
        this.status = onderzoekStandaardGroep.getStatus();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumAanvang() {
        return datumAanvang;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getVerwachteAfhandeldatum() {
        return verwachteAfhandeldatum;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getDatumEinde() {
        return datumEinde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekOmschrijvingAttribuut getOmschrijving() {
        return omschrijving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusOnderzoekAttribuut getStatus() {
        return status;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (datumAanvang != null) {
            attributen.add(datumAanvang);
        }
        if (verwachteAfhandeldatum != null) {
            attributen.add(verwachteAfhandeldatum);
        }
        if (datumEinde != null) {
            attributen.add(datumEinde);
        }
        if (omschrijving != null) {
            attributen.add(omschrijving);
        }
        if (status != null) {
            attributen.add(status);
        }
        return attributen;
    }

}
