/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Entity;
import javax.persistence.Table;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerKleinAttribuut;
import nl.bzk.brp.model.basis.ModelIdentificeerbaar;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonCache;
import org.hibernate.annotations.DynamicUpdate;


/**
 * Deze entiteit heeft dynamic-update om het het mogelijk te maken voor Hibernate om individuele attributen bij te werken in een update statement, i.p.v.
 * alle attributen bij te werken. Meerdere processen kunnen tegelijk in deze entiteit werken aangezien het om een blob van de persoon en de
 * afnemerindicaties gaat.
 */
@Entity
@DynamicUpdate
@Table(schema = "Kern", name = "PersCache")
public class PersoonCacheModel extends AbstractPersoonCacheModel implements PersoonCache, ModelIdentificeerbaar<Integer> {

    private static final Byte BMR_VERSIE = 36;

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     */
    protected PersoonCacheModel() {
        super();
        this.setStandaard(new PersoonCacheStandaardGroepModel(new VersienummerKleinAttribuut(BMR_VERSIE), null, null, null, null));
    }

    /**
     * Standaard constructor die direct alle identificerende attributen instantieert of doorgeeft.
     *
     * @param persoonId persoon van Persoon cache.
     */
    public PersoonCacheModel(final Integer persoonId) {
        super(persoonId);
        this.setStandaard(new PersoonCacheStandaardGroepModel(new VersienummerKleinAttribuut(BMR_VERSIE), null, null, null, null));
    }

    @Override
    public final Persoon getPersoon() {
        return null;
    }
}
