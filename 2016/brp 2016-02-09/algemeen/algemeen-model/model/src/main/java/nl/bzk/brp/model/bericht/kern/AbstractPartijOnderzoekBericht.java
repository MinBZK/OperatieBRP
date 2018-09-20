/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtEntiteitGroep;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PartijOnderzoekBasis;

/**
 * De wijze waarop een Partij betrokken is bij een Onderzoek.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPartijOnderzoekBericht extends AbstractBerichtEntiteit implements BrpObject, BerichtEntiteit, MetaIdentificeerbaar,
        PartijOnderzoekBasis
{

    private static final Integer META_ID = 10775;
    private String partijCode;
    private PartijAttribuut partij;
    private OnderzoekBericht onderzoek;
    private PartijOnderzoekStandaardGroepBericht standaard;

    /**
     * Retourneert Partij van Identiteit.
     *
     * @return Partij.
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getPartij() {
        return partij;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OnderzoekBericht getOnderzoek() {
        return onderzoek;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijOnderzoekStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Partij van Identiteit.
     *
     * @param partijCode Partij.
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Partij \ Onderzoek.
     *
     * @param partij Partij.
     */
    public void setPartij(final PartijAttribuut partij) {
        this.partij = partij;
    }

    /**
     * Zet Onderzoek van Partij \ Onderzoek.
     *
     * @param onderzoek Onderzoek.
     */
    public void setOnderzoek(final OnderzoekBericht onderzoek) {
        this.onderzoek = onderzoek;
    }

    /**
     * Zet Standaard van Partij \ Onderzoek.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final PartijOnderzoekStandaardGroepBericht standaard) {
        this.standaard = standaard;
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
    public List<BerichtEntiteit> getBerichtEntiteiten() {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BerichtEntiteitGroep> getBerichtEntiteitGroepen() {
        final List<BerichtEntiteitGroep> berichtGroepen = new ArrayList<>();
        berichtGroepen.add(getStandaard());
        return berichtGroepen;
    }

}
