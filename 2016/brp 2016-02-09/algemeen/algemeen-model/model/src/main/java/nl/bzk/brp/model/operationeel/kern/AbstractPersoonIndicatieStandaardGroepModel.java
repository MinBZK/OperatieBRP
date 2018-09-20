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
import nl.bzk.brp.model.algemeen.attribuuttype.conv.ConversieRedenBeeindigenNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.conv.LO3RedenOpnameNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIndicatieStandaardGroepBasis;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonIndicatieStandaardGroepModel implements PersoonIndicatieStandaardGroepBasis {

    @Embedded
    @AttributeOverride(name = JaAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "Waarde"))
    @JsonProperty
    private JaAttribuut waarde;

    @Embedded
    @AttributeOverride(name = LO3RedenOpnameNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnOpnameNation"))
    @JsonProperty
    private LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit;

    @Embedded
    @AttributeOverride(name = ConversieRedenBeeindigenNationaliteitAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "MigrRdnBeeindigenNation"))
    @JsonProperty
    private ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonIndicatieStandaardGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param waarde waarde van Standaard.
     * @param migratieRedenOpnameNationaliteit migratieRedenOpnameNationaliteit van Standaard.
     * @param migratieRedenBeeindigenNationaliteit migratieRedenBeeindigenNationaliteit van Standaard.
     */
    public AbstractPersoonIndicatieStandaardGroepModel(
        final JaAttribuut waarde,
        final LO3RedenOpnameNationaliteitAttribuut migratieRedenOpnameNationaliteit,
        final ConversieRedenBeeindigenNationaliteitAttribuut migratieRedenBeeindigenNationaliteit)
    {
        this.waarde = waarde;
        this.migratieRedenOpnameNationaliteit = migratieRedenOpnameNationaliteit;
        this.migratieRedenBeeindigenNationaliteit = migratieRedenBeeindigenNationaliteit;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonIndicatieStandaardGroep te kopieren groep.
     */
    public AbstractPersoonIndicatieStandaardGroepModel(final PersoonIndicatieStandaardGroep persoonIndicatieStandaardGroep) {
        this.waarde = persoonIndicatieStandaardGroep.getWaarde();
        this.migratieRedenOpnameNationaliteit = persoonIndicatieStandaardGroep.getMigratieRedenOpnameNationaliteit();
        this.migratieRedenBeeindigenNationaliteit = persoonIndicatieStandaardGroep.getMigratieRedenBeeindigenNationaliteit();

    }

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

}
