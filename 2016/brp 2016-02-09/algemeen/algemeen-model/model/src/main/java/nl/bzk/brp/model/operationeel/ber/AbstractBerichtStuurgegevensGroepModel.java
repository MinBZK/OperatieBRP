/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.ber;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroep;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroepBasis;

/**
 * De naam van de groep is gebaseerd op de naam die StUF voor dit soort gegevens hanteert. Het gaat in principe om een
 * beperkte set gegevens die "op de envelop" horen c.q. zouden kunnen staan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractBerichtStuurgegevensGroepModel implements BerichtStuurgegevensGroepBasis {

    @JsonProperty
    @Column(name = "ZendendePartij")
    private Short zendendePartijId;

    @Embedded
    @AttributeOverride(name = SysteemNaamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "ZendendeSysteem"))
    @JsonProperty
    private SysteemNaamAttribuut zendendeSysteem;

    @JsonProperty
    @Column(name = "OntvangendePartij")
    private Short ontvangendePartijId;

    @Embedded
    @AttributeOverride(name = SysteemNaamAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "OntvangendeSysteem"))
    @JsonProperty
    private SysteemNaamAttribuut ontvangendeSysteem;

    @Embedded
    @AttributeOverride(name = ReferentienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Referentienr"))
    @JsonProperty
    private ReferentienummerAttribuut referentienummer;

    @Embedded
    @AttributeOverride(name = ReferentienummerAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "CrossReferentienr"))
    @JsonProperty
    private ReferentienummerAttribuut crossReferentienummer;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsVerzending"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdVerzending;

    @Embedded
    @AttributeOverride(name = DatumTijdAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "TsOntv"))
    @JsonProperty
    private DatumTijdAttribuut datumTijdOntvangst;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractBerichtStuurgegevensGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert. CHECKSTYLE-BRP:OFF - MAX PARAMS
     *
     * @param zendendePartijId zendendePartijId van Stuurgegevens.
     * @param zendendeSysteem zendendeSysteem van Stuurgegevens.
     * @param ontvangendePartijId ontvangendePartijId van Stuurgegevens.
     * @param ontvangendeSysteem ontvangendeSysteem van Stuurgegevens.
     * @param referentienummer referentienummer van Stuurgegevens.
     * @param crossReferentienummer crossReferentienummer van Stuurgegevens.
     * @param datumTijdVerzending datumTijdVerzending van Stuurgegevens.
     * @param datumTijdOntvangst datumTijdOntvangst van Stuurgegevens.
     */
    public AbstractBerichtStuurgegevensGroepModel(
        final Short zendendePartijId,
        final SysteemNaamAttribuut zendendeSysteem,
        final Short ontvangendePartijId,
        final SysteemNaamAttribuut ontvangendeSysteem,
        final ReferentienummerAttribuut referentienummer,
        final ReferentienummerAttribuut crossReferentienummer,
        final DatumTijdAttribuut datumTijdVerzending,
        final DatumTijdAttribuut datumTijdOntvangst)
    {
        // CHECKSTYLE-BRP:ON - MAX PARAMS
        this.zendendePartijId = zendendePartijId;
        this.zendendeSysteem = zendendeSysteem;
        this.ontvangendePartijId = ontvangendePartijId;
        this.ontvangendeSysteem = ontvangendeSysteem;
        this.referentienummer = referentienummer;
        this.crossReferentienummer = crossReferentienummer;
        this.datumTijdVerzending = datumTijdVerzending;
        this.datumTijdOntvangst = datumTijdOntvangst;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param berichtStuurgegevensGroep te kopieren groep.
     */
    public AbstractBerichtStuurgegevensGroepModel(final BerichtStuurgegevensGroep berichtStuurgegevensGroep) {
        this.zendendeSysteem = berichtStuurgegevensGroep.getZendendeSysteem();
        this.ontvangendeSysteem = berichtStuurgegevensGroep.getOntvangendeSysteem();
        this.referentienummer = berichtStuurgegevensGroep.getReferentienummer();
        this.crossReferentienummer = berichtStuurgegevensGroep.getCrossReferentienummer();
        this.datumTijdVerzending = berichtStuurgegevensGroep.getDatumTijdVerzending();
        this.datumTijdOntvangst = berichtStuurgegevensGroep.getDatumTijdOntvangst();

    }

    /**
     * Retourneert Zendende partij van Stuurgegevens.
     *
     * @return Zendende partij.
     */
    public Short getZendendePartijId() {
        return zendendePartijId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysteemNaamAttribuut getZendendeSysteem() {
        return zendendeSysteem;
    }

    /**
     * Retourneert Ontvangende partij van Stuurgegevens.
     *
     * @return Ontvangende partij.
     */
    public Short getOntvangendePartijId() {
        return ontvangendePartijId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SysteemNaamAttribuut getOntvangendeSysteem() {
        return ontvangendeSysteem;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferentienummerAttribuut getReferentienummer() {
        return referentienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReferentienummerAttribuut getCrossReferentienummer() {
        return crossReferentienummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getDatumTijdVerzending() {
        return datumTijdVerzending;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatumTijdAttribuut getDatumTijdOntvangst() {
        return datumTijdOntvangst;
    }

    /**
     * Zet Zendende partij van Stuurgegevens.
     *
     * @param zendendePartijId Zendende partij.
     */
    public void setZendendePartijId(final Short zendendePartijId) {
        this.zendendePartijId = zendendePartijId;
    }

    /**
     * Zet Zendende systeem van Stuurgegevens.
     *
     * @param zendendeSysteem Zendende systeem.
     */
    public void setZendendeSysteem(final SysteemNaamAttribuut zendendeSysteem) {
        this.zendendeSysteem = zendendeSysteem;
    }

    /**
     * Zet Ontvangende partij van Stuurgegevens.
     *
     * @param ontvangendePartijId Ontvangende partij.
     */
    public void setOntvangendePartijId(final Short ontvangendePartijId) {
        this.ontvangendePartijId = ontvangendePartijId;
    }

    /**
     * Zet Ontvangende systeem van Stuurgegevens.
     *
     * @param ontvangendeSysteem Ontvangende systeem.
     */
    public void setOntvangendeSysteem(final SysteemNaamAttribuut ontvangendeSysteem) {
        this.ontvangendeSysteem = ontvangendeSysteem;
    }

    /**
     * Zet Referentienummer van Stuurgegevens.
     *
     * @param referentienummer Referentienummer.
     */
    public void setReferentienummer(final ReferentienummerAttribuut referentienummer) {
        this.referentienummer = referentienummer;
    }

    /**
     * Zet Cross referentienummer van Stuurgegevens.
     *
     * @param crossReferentienummer Cross referentienummer.
     */
    public void setCrossReferentienummer(final ReferentienummerAttribuut crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    /**
     * Zet Datum/tijd verzending van Stuurgegevens.
     *
     * @param datumTijdVerzending Datum/tijd verzending.
     */
    public void setDatumTijdVerzending(final DatumTijdAttribuut datumTijdVerzending) {
        this.datumTijdVerzending = datumTijdVerzending;
    }

    /**
     * Zet Datum/tijd ontvangst van Stuurgegevens.
     *
     * @param datumTijdOntvangst Datum/tijd ontvangst.
     */
    public void setDatumTijdOntvangst(final DatumTijdAttribuut datumTijdOntvangst) {
        this.datumTijdOntvangst = datumTijdOntvangst;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (zendendeSysteem != null) {
            attributen.add(zendendeSysteem);
        }
        if (ontvangendeSysteem != null) {
            attributen.add(ontvangendeSysteem);
        }
        if (referentienummer != null) {
            attributen.add(referentienummer);
        }
        if (crossReferentienummer != null) {
            attributen.add(crossReferentienummer);
        }
        if (datumTijdVerzending != null) {
            attributen.add(datumTijdVerzending);
        }
        if (datumTijdOntvangst != null) {
            attributen.add(datumTijdOntvangst);
        }
        return attributen;
    }

}
