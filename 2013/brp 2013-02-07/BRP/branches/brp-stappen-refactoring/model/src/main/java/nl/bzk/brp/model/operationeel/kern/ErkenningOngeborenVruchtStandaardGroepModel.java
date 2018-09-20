/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.logisch.kern.ErkenningOngeborenVruchtStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractErkenningOngeborenVruchtStandaardGroepModel;


/**
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.2.3.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-12-04 09:33:11.
 * Gegenereerd op: Tue Dec 04 09:50:47 CET 2012.
 */
@Embeddable
public class ErkenningOngeborenVruchtStandaardGroepModel extends AbstractErkenningOngeborenVruchtStandaardGroepModel
        implements ErkenningOngeborenVruchtStandaardGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected ErkenningOngeborenVruchtStandaardGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param datumAanvang datumAanvang van Standaard.
     */
    public ErkenningOngeborenVruchtStandaardGroepModel(final Datum datumAanvang) {
        super(datumAanvang);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param erkenningOngeborenVruchtStandaardGroep te kopieren groep.
     */
    public ErkenningOngeborenVruchtStandaardGroepModel(
            final ErkenningOngeborenVruchtStandaardGroep erkenningOngeborenVruchtStandaardGroep)
    {
        super(erkenningOngeborenVruchtStandaardGroep);
    }

}
