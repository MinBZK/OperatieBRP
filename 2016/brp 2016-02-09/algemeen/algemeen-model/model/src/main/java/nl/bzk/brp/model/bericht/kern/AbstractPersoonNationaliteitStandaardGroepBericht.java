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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieleHistorieGroepBericht;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.MetaIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroepBasis;

/**
 * Vorm van historie: beiden. Motivatie: een persoon kan een bepaalde Nationaliteit verkrijgen, dan wel verliezen. Naast
 * een formele historie ('wat stond geregistreerd') is dus ook materiële historie denkbaar ('over welke periode
 * beschikte hij over de Nederlandse Nationaliteit'). We leggen beide vast, óók omdat dit van oudsher gebeurde vanuit de
 * GBA.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.BerichtModelGenerator")
public abstract class AbstractPersoonNationaliteitStandaardGroepBericht extends AbstractMaterieleHistorieGroepBericht implements Groep,
        PersoonNationaliteitStandaardGroepBasis, MetaIdentificeerbaar
{

    private static final Integer META_ID = 3604;
    private static final List<Integer> ONDERLIGGENDE_ATTRIBUTEN = Arrays.asList(3229, 3230, 21230, 21231, 21232, 21233);
    private String redenVerkrijgingCode;
    private RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging;
    private String redenVerliesCode;
    private RedenVerliesNLNationaliteitAttribuut redenVerlies;
    private JaAttribuut indicatieBijhoudingBeeindigd;
    private LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit;
    private ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit;
    private DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding;

    /**
     * Retourneert Reden verkrijging van Standaard.
     *
     * @return Reden verkrijging.
     */
    public String getRedenVerkrijgingCode() {
        return redenVerkrijgingCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteitAttribuut getRedenVerkrijging() {
        return redenVerkrijging;
    }

    /**
     * Retourneert Reden verlies van Standaard.
     *
     * @return Reden verlies.
     */
    public String getRedenVerliesCode() {
        return redenVerliesCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerliesNLNationaliteitAttribuut getRedenVerlies() {
        return redenVerlies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaAttribuut getIndicatieBijhoudingBeeindigd() {
        return indicatieBijhoudingBeeindigd;
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
     * {@inheritDoc}
     */
    @Override
    public DatumEvtDeelsOnbekendAttribuut getMigratieDatumEindeBijhouding() {
        return migratieDatumEindeBijhouding;
    }

    /**
     * Zet Reden verkrijging van Standaard.
     *
     * @param redenVerkrijgingCode Reden verkrijging.
     */
    public void setRedenVerkrijgingCode(final String redenVerkrijgingCode) {
        this.redenVerkrijgingCode = redenVerkrijgingCode;
    }

    /**
     * Zet Reden verkrijging van Standaard.
     *
     * @param redenVerkrijging Reden verkrijging.
     */
    public void setRedenVerkrijging(final RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging) {
        this.redenVerkrijging = redenVerkrijging;
    }

    /**
     * Zet Reden verlies van Standaard.
     *
     * @param redenVerliesCode Reden verlies.
     */
    public void setRedenVerliesCode(final String redenVerliesCode) {
        this.redenVerliesCode = redenVerliesCode;
    }

    /**
     * Zet Reden verlies van Standaard.
     *
     * @param redenVerlies Reden verlies.
     */
    public void setRedenVerlies(final RedenVerliesNLNationaliteitAttribuut redenVerlies) {
        this.redenVerlies = redenVerlies;
    }

    /**
     * Zet Bijhouding beeindigd? van Standaard.
     *
     * @param indicatieBijhoudingBeeindigd Bijhouding beeindigd?.
     */
    public void setIndicatieBijhoudingBeeindigd(final JaAttribuut indicatieBijhoudingBeeindigd) {
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
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
     * Zet Migratie Datum einde bijhouding van Standaard.
     *
     * @param migratieDatumEindeBijhouding Migratie Datum einde bijhouding.
     */
    public void setMigratieDatumEindeBijhouding(final DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding) {
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (redenVerkrijging != null) {
            attributen.add(redenVerkrijging);
        }
        if (redenVerlies != null) {
            attributen.add(redenVerlies);
        }
        if (indicatieBijhoudingBeeindigd != null) {
            attributen.add(indicatieBijhoudingBeeindigd);
        }
        if (migratieRedenOpnameNationaliteit != null) {
            attributen.add(migratieRedenOpnameNationaliteit);
        }
        if (migratieRedenBeeindigenNationaliteit != null) {
            attributen.add(migratieRedenBeeindigenNationaliteit);
        }
        if (migratieDatumEindeBijhouding != null) {
            attributen.add(migratieDatumEindeBijhouding);
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
