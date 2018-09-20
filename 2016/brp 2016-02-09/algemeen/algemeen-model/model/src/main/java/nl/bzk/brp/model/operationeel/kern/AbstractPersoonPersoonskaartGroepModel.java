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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroepBasis;

/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.OperationeelModelGenerator")
@MappedSuperclass
public abstract class AbstractPersoonPersoonskaartGroepModel implements PersoonPersoonskaartGroepBasis {

    @Embedded
    @AssociationOverride(name = PartijAttribuut.WAARDE_VELD_NAAM, joinColumns = @JoinColumn(name = "GemPK"))
    @JsonProperty
    private PartijAttribuut gemeentePersoonskaart;

    @Embedded
    @AttributeOverride(name = JaNeeAttribuut.WAARDE_VELD_NAAM, column = @Column(name = "IndPKVolledigGeconv"))
    @JsonProperty
    private JaNeeAttribuut indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Default lege constructor t.b.v. Hibernate / JPA
     *
     */
    protected AbstractPersoonPersoonskaartGroepModel() {
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param gemeentePersoonskaart gemeentePersoonskaart van Persoonskaart.
     * @param indicatiePersoonskaartVolledigGeconverteerd indicatiePersoonskaartVolledigGeconverteerd van Persoonskaart.
     */
    public AbstractPersoonPersoonskaartGroepModel(
        final PartijAttribuut gemeentePersoonskaart,
        final JaNeeAttribuut indicatiePersoonskaartVolledigGeconverteerd)
    {
        this.gemeentePersoonskaart = gemeentePersoonskaart;
        this.indicatiePersoonskaartVolledigGeconverteerd = indicatiePersoonskaartVolledigGeconverteerd;

    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonPersoonskaartGroep te kopieren groep.
     */
    public AbstractPersoonPersoonskaartGroepModel(final PersoonPersoonskaartGroep persoonPersoonskaartGroep) {
        this.gemeentePersoonskaart = persoonPersoonskaartGroep.getGemeentePersoonskaart();
        this.indicatiePersoonskaartVolledigGeconverteerd = persoonPersoonskaartGroep.getIndicatiePersoonskaartVolledigGeconverteerd();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PartijAttribuut getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNeeAttribuut getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

    /**
     * Geeft alle attributen van de groep met uitzondering van attributen die null zijn.
     *
     * @return Lijst met attributen van de groep.
     */
    public final List<Attribuut> getAttributen() {
        final List<Attribuut> attributen = new ArrayList<>();
        if (gemeentePersoonskaart != null) {
            attributen.add(gemeentePersoonskaart);
        }
        if (indicatiePersoonskaartVolledigGeconverteerd != null) {
            attributen.add(indicatiePersoonskaartVolledigGeconverteerd);
        }
        return attributen;
    }

}
