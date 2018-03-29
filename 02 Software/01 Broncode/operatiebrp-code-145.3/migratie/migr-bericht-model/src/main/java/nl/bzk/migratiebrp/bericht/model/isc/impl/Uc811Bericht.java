/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.isc.impl;

import java.util.Arrays;
import nl.bzk.migratiebrp.bericht.model.AbstractBericht;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.isc.AbstractIscBericht;
import nl.bzk.migratiebrp.bericht.model.isc.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.isc.generated.Uc811Type;
import nl.bzk.migratiebrp.bericht.model.isc.xml.IscXml;

/**
 * Uc811 Bericht.
 */
public final class Uc811Bericht extends AbstractIscBericht {
    private static final long serialVersionUID = 1L;

    private final Uc811Type uc811Type;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public Uc811Bericht() {
        uc811Type = new Uc811Type();
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een bericht te maken.
     * @param uc811Type het uc811Type type
     */
    public Uc811Bericht(final Uc811Type uc811Type) {
        this.uc811Type = uc811Type;
    }

    /* ************************************************************************************************************* */

    @Override
    public String getBerichtType() {
        return "Uc811";
    }

    /* ************************************************************************************************************* */

    /**
     * Geeft de gemeente code.
     * @return De gemeente code.
     */
    public String getGemeenteCode() {
        return uc811Type.getGemeenteCode();
    }

    /**
     * Zet de gemeente code.
     * @param gemeenteCode De te zetten gemeente code
     */
    public void setGemeenteCode(final String gemeenteCode) {
        uc811Type.setGemeenteCode(gemeenteCode);
    }

    /**
     * Geef de waarde van anummer.
     * @return het-nummer
     */
    public Long getAnummer() {
        return AbstractBericht.asLong(uc811Type.getANummer());
    }

    /**
     * Zet de waarde van a nummer.
     * @param aNummer Het te zetten a-nummer
     */
    public void setANummer(final Long aNummer) {
        uc811Type.setANummer(AbstractBericht.asString(aNummer));
    }

    /* ************************************************************************************************************* */

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.Bericht#getGerelateerdeInformatie()
     */
    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(null, Arrays.asList(getGemeenteCode()), Arrays.asList(getAnummer().toString()));
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() throws BerichtInhoudException {
        return IscXml.SINGLETON.elementToString(new ObjectFactory().createUc811(uc811Type));
    }
}
