/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.ber;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.SysteemNaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ReferentienummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtStuurgegevensGroepBasis;

/**
 * De naam van de groep is gebaseerd op de naam die StUF voor dit soort gegevens hanteert. Het gaat in principe om een
 * beperkte set gegevens die "op de envelop" horen c.q. zouden kunnen staan.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtStuurgegevensGroepBericht extends AbstractBerichtIdentificeerbaar implements Groep, BerichtStuurgegevensGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 6188;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(6189, 6190, 11324, 11325, 6191, 6228, 6106, 4831);
    private String zendendePartijCode;
    private PartijAttribuut zendendePartij;
    private SysteemNaamAttribuut zendendeSysteem;
    private String ontvangendePartijCode;
    private PartijAttribuut ontvangendePartij;
    private SysteemNaamAttribuut ontvangendeSysteem;
    private ReferentienummerAttribuut referentienummer;
    private ReferentienummerAttribuut crossReferentienummer;
    private DatumTijdAttribuut datumTijdVerzending;
    private DatumTijdAttribuut datumTijdOntvangst;

    /**
     * Retourneert Zendende partij van Stuurgegevens.
     *
     * @return Zendende partij.
     */
    public String getZendendePartijCode() {
        return zendendePartijCode;
    }

    /**
     * Retourneert Zendende partij van Stuurgegevens.
     *
     * @return Zendende partij.
     */
    public PartijAttribuut getZendendePartij() {
        return zendendePartij;
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
    public String getOntvangendePartijCode() {
        return ontvangendePartijCode;
    }

    /**
     * Retourneert Ontvangende partij van Stuurgegevens.
     *
     * @return Ontvangende partij.
     */
    public PartijAttribuut getOntvangendePartij() {
        return ontvangendePartij;
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
     * @param zendendePartijCode Zendende partij.
     */
    public void setZendendePartijCode(final String zendendePartijCode) {
        this.zendendePartijCode = zendendePartijCode;
    }

    /**
     * Zet Zendende partij van Stuurgegevens.
     *
     * @param zendendePartij Zendende partij.
     */
    public void setZendendePartij(final PartijAttribuut zendendePartij) {
        this.zendendePartij = zendendePartij;
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
     * @param ontvangendePartijCode Ontvangende partij.
     */
    public void setOntvangendePartijCode(final String ontvangendePartijCode) {
        this.ontvangendePartijCode = ontvangendePartijCode;
    }

    /**
     * Zet Ontvangende partij van Stuurgegevens.
     *
     * @param ontvangendePartij Ontvangende partij.
     */
    public void setOntvangendePartij(final PartijAttribuut ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
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
        if (zendendePartij != null) {
            attributen.add(zendendePartij);
        }
        if (zendendeSysteem != null) {
            attributen.add(zendendeSysteem);
        }
        if (ontvangendePartij != null) {
            attributen.add(ontvangendePartij);
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getMetaId() {
        return META_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean bevatElementMetMetaId(final Integer metaId) {
        return ONDERLIGGENDE_ATTRIBUTEN.contains(metaId);
    }

}
