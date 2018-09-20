/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonIndicatieStandaardGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonIndicatieStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3654;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3659, 21234, 21235);
    private JaAttribuut waarde;
    private LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit;
    private ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit;

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getWaarde() {
        return waarde;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LO3RedenOpnameNationaliteitAttribuut getMigratieRedenOpnameNationaliteit() {
        return migratieRedenOpnameNationaliteit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ConversieRedenBeeindigenNationaliteitAttribuut getMigratieRedenBeeindigenNationaliteit() {
        return migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Zet Waarde van Standaard.
     *
     * @param waarde Waarde.
     */
    public void setWaarde(final JaAttribuut waarde) {
        this.waarde = waarde;
    }

    /**
     * Zet Migratie Reden opname nationaliteit van Standaard.
     *
     * @param migratieRedenOpnameNationaliteit Migratie Reden opname nationaliteit.
     */
    public void setMigratieRedenOpnameNationaliteit(final LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit) {
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
    }

    /**
     * Zet Migratie Reden beeindigen nationaliteit van Standaard.
     *
     * @param migratieRedenBeeindigenNationaliteit Migratie Reden beeindigen nationaliteit.
     */
    public void setMigratieRedenBeeindigenNationaliteit(final ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit) {
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (waarde != null) {
            attributen.add(waarde);
        }
        if (migratieRedenOpnameNationaliteit != null) {
            attributen.add(migratieRedenOpnameNationaliteit);
        }
        if (migratieRedenBeeindigenNationaliteit != null) {
            attributen.add(migratieRedenBeeindigenNationaliteit);
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
