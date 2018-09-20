/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.brp.impl;

import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.brp.AbstractBrpBericht;
import nl.bzk.migratiebrp.bericht.model.brp.generated.MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat;
import nl.bzk.migratiebrp.bericht.model.brp.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.brp.xml.BrpXml;

/**
 * Resultaat van een overlijden bijhouding.
 */
public final class RegistreerOverlijdenBijhoudingResultaat extends AbstractBrpBericht<MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat> {

    private static final long serialVersionUID = 1L;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public RegistreerOverlijdenBijhoudingResultaat() {
        this(new MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     *
     * @param resultaat
     *            het resultaat type
     */
    public RegistreerOverlijdenBijhoudingResultaat(final MigratievoorzieningRegistreerOverlijdenBijhoudingResultaat resultaat) {
        super("RegistreerOverlijdenBijhoudingResultaat", resultaat);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return BrpXml.SINGLETON.elementToString(new ObjectFactory().createIscMigRegistreerOverlijdenR(getInhoud()));
    }
}
