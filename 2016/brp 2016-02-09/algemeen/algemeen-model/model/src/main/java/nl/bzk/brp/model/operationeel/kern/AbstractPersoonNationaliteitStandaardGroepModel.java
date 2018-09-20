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
import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteitAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteitStandaardGroep;
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
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonNationaliteitStandaardGroepModel implements PersoonNationaliteitStandaardGroepBasis {

    @Embedded
    @AssociationOverride(name = RedenVerkrijgingNLNationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnVerk"))
    @JsonProperty
    private RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging;

    @Embedded
    @AssociationOverride(name = RedenVerliesNLNationaliteitAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "RdnVerlies"))
    @JsonProperty
    private RedenVerliesNLNationaliteitAttribuut redenVerlies;

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndBijhoudingBeeindigd"))
    @JsonProperty
    private JaAttribuut indicatieBijhoudingBeeindigd;

    @Embedded
    @AttributeOverride(name = LO3RedenOpnameNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnOpnameNation"))
    @JsonProperty
    private LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit;

    @Embedded
    @AttributeOverride(name = ConversieRedenBeeindigenNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnBeeindigenNation"))
    @JsonProperty
    private ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit;

    @Embedded
    @AttributeOverride(name = DatumEvtDeelsOnbekendAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrDatEindeBijhouding"))
    @JsonProperty
    private DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonNationaliteitStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param redenVerkrijging redenVerkrijging van Standaard.
     * @param redenVerlies redenVerlies van Standaard.
     * @param indicatieBijhoudingBeeindigd indicatieBijhoudingBeeindigd van Standaard.
     * @param migratieRedenOpnameNationaliteit migratieRedenOpnameNationaliteit van Standaard.
     * @param migratieRedenBeeindigenNationaliteit migratieRedenBeeindigenNationaliteit van Standaard.
     * @param migratieDatumEindeBijhouding migratieDatumEindeBijhouding van Standaard.
     */
    public AbstractPersoonNationaliteitStandaardGroepModel(
        final RedenVerkrijgingNLNationaliteitAttribuut redenVerkrijging,
        final RedenVerliesNLNationaliteitAttribuut redenVerlies,
        final JaAttribuut indicatieBijhoudingBeeindigd,
        final LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit,
        final ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit,
        final DatumEvtDeelsOnbekendAttribuut migratieDatumEindeBijhouding)
    {
        this.redenVerkrijging = redenVerkrijging;
        this.redenVerlies = redenVerlies;
        this.indicatieBijhoudingBeeindigd = indicatieBijhoudingBeeindigd;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;
        this.migratieDatumEindeBijhouding = migratieDatumEindeBijhouding;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonNationaliteitStandaardGroep te kopieren groep.
     */
    public AbstractPersoonNationaliteitStandaardGroepModel(final PersoonNationaliteitStandaardGroep persoonNationaliteitStandaardGroep) {
        this.redenVerkrijging = persoonNationaliteitStandaardGroep.getRedenVerkrijging();
        this.redenVerlies = persoonNationaliteitStandaardGroep.getRedenVerlies();
        this.indicatieBijhoudingBeeindigd = persoonNationaliteitStandaardGroep.getIndicatieBijhoudingBeeindigd();
        this.migratieRedenOpnameNationaliteit = persoonNationaliteitStandaardGroep.getMigratieRedenOpnameNationaliteit();
        this.migratieRedenBeeindigenNationaliteit = persoonNationaliteitStandaardGroep.getMigratieRedenBeeindigenNationaliteit();
        this.migratieDatumEindeBijhouding = persoonNationaliteitStandaardGroep.getMigratieDatumEindeBijhouding();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RedenVerkrijgingNLNationaliteitAttribuut getRedenVerkrijging() {
        return redenVerkrijging;
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

}
