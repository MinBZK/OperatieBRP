/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern.basis;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.logisch.kern.PersoonPersoonskaartGroep;
import nl.bzk.brp.model.logisch.kern.basis.PersoonPersoonskaartGroepBasis;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


/**
 * Vorm van historie: alleen formeel. Immers, een persoonskaart is wel-of-niet geconverteerd, en een persoonskaart
 * 'verhuist' niet mee (c.q.: de plaats van een persoonskaart veranderd in principe niet).
 *
 *
 *
 */
@MappedSuperclass
public abstract class AbstractPersoonPersoonskaartGroepModel implements PersoonPersoonskaartGroepBasis {

    @ManyToOne
    @JoinColumn(name = "GemPK")
    @Fetch(value = FetchMode.JOIN)
    @JsonProperty
    private Partij gemeentePersoonskaart;

    @Embedded
    @AttributeOverride(name = "waarde", column = @Column(name = "IndPKVolledigGeconv"))
    @JsonProperty
    private JaNee  indicatiePersoonskaartVolledigGeconverteerd;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
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
    public AbstractPersoonPersoonskaartGroepModel(final Partij gemeentePersoonskaart,
            final JaNee indicatiePersoonskaartVolledigGeconverteerd)
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
        this.indicatiePersoonskaartVolledigGeconverteerd =
            persoonPersoonskaartGroep.getIndicatiePersoonskaartVolledigGeconverteerd();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getGemeentePersoonskaart() {
        return gemeentePersoonskaart;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JaNee getIndicatiePersoonskaartVolledigGeconverteerd() {
        return indicatiePersoonskaartVolledigGeconverteerd;
    }

}
