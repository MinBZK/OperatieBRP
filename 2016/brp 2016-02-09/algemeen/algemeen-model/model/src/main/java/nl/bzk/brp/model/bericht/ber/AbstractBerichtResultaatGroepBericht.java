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
import nl.bzk.brp.model.algemeen.stamgegeven.ber.BijhoudingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.basis.AbstractBerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.ber.BerichtResultaatGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractBerichtResultaatGroepBericht extends AbstractBerichtIdentificeerbaar implements Groep, BerichtResultaatGroepBasis,
        MetaIdentificeerbaar
{

    private static final Integer META_ID = 6229;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(6230, 6245, 6246);
    private VerwerkingsresultaatAttribuut verwerking;
    private BijhoudingsresultaatAttribuut bijhouding;
    private SoortMeldingAttribuut hoogsteMeldingsniveau;

    /**
     * {@inheritDoc}
     */
    @Override
    public VerwerkingsresultaatAttribuut getVerwerking() {
        return verwerking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BijhoudingsresultaatAttribuut getBijhouding() {
        return bijhouding;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortMeldingAttribuut getHoogsteMeldingsniveau() {
        return hoogsteMeldingsniveau;
    }

    /**
     * Zet Verwerking van Resultaat.
     *
     * @param verwerking Verwerking.
     */
    public void setVerwerking(final VerwerkingsresultaatAttribuut verwerking) {
        this.verwerking = verwerking;
    }

    /**
     * Zet Bijhouding van Resultaat.
     *
     * @param bijhouding Bijhouding.
     */
    public void setBijhouding(final BijhoudingsresultaatAttribuut bijhouding) {
        this.bijhouding = bijhouding;
    }

    /**
     * Zet Hoogste meldingsniveau van Resultaat.
     *
     * @param hoogsteMeldingsniveau Hoogste meldingsniveau.
     */
    public void setHoogsteMeldingsniveau(final SoortMeldingAttribuut hoogsteMeldingsniveau) {
        this.hoogsteMeldingsniveau = hoogsteMeldingsniveau;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (verwerking != null) {
            attributen.add(verwerking);
        }
        if (bijhouding != null) {
            attributen.add(bijhouding);
        }
        if (hoogsteMeldingsniveau != null) {
            attributen.add(hoogsteMeldingsniveau);
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
