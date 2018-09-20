/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp;

import java.io.Serializable;
import java.math.BigDecimal;

import nl.moderniseringgba.isc.esb.message.AbstractBericht;
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeenten;
import nl.moderniseringgba.isc.esb.message.brp.generated.IscGemeentenEnkelBron;
import nl.moderniseringgba.isc.esb.message.brp.generated.ObjectFactory;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;

/**
 * Basis BRP bericht implementatie.
 */
public abstract class AbstractBrpBericht extends AbstractBericht implements BrpBericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final ObjectFactory FACTORY = new ObjectFactory();

    /**
     * Haal de Brp gemeente uit de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @return brp gemeente (of null)
     */
    protected final BrpGemeenteCode getBrpGemeente(final IscGemeenten iscGemeenten) {
        return iscGemeenten == null ? null : asBrpGemeenteCode(iscGemeenten.getBrpGemeente());
    }

    /**
     * Haal de Brp gemeente uit de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @return brp gemeente (of null)
     */
    protected final BrpGemeenteCode getBrpGemeente(final IscGemeentenEnkelBron iscGemeenten) {
        return iscGemeenten == null ? null : asBrpGemeenteCode(iscGemeenten.getBrpGemeente());
    }

    /**
     * Haal de Lo3 gemeente uit de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @return brp gemeente (of null)
     */
    protected final BrpGemeenteCode getLo3Gemeente(final IscGemeenten iscGemeenten) {
        return iscGemeenten == null ? null : asBrpGemeenteCode(iscGemeenten.getLo3Gemeente());
    }

    private BrpGemeenteCode asBrpGemeenteCode(final String gemeente) {
        return gemeente == null ? null : new BrpGemeenteCode(new BigDecimal(gemeente));
    }

    /**
     * Zet de Brp gemeente in de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @param gemeente
     *            gemeente
     * @return isc gemeenten (nieuwe structuur als input null is)
     */
    protected final IscGemeenten setBrpGemeente(final IscGemeenten iscGemeenten, final BrpGemeenteCode gemeente) {
        IscGemeenten result;
        if (iscGemeenten == null) {
            result = FACTORY.createIscGemeenten();
        } else {
            result = iscGemeenten;
        }

        if (gemeente == null) {
            result.setBrpGemeente(null);
        } else {
            result.setBrpGemeente(gemeente.getFormattedStringCode());
        }
        return result;
    }

    /**
     * Zet de Brp gemeente in de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @param gemeente
     *            gemeente
     * @return isc gemeenten (nieuwe structuur als input null is)
     */
    protected final IscGemeentenEnkelBron setBrpGemeente(
            final IscGemeentenEnkelBron iscGemeenten,
            final BrpGemeenteCode gemeente) {
        IscGemeentenEnkelBron result;
        if (iscGemeenten == null) {
            result = FACTORY.createIscGemeentenEnkelBron();
        } else {
            result = iscGemeenten;
        }

        if (gemeente == null) {
            result.setBrpGemeente(null);
        } else {
            result.setBrpGemeente(gemeente.getFormattedStringCode());
        }
        return result;
    }

    /**
     * Zet de Lo3 gemeente in de algemene isc gemeenten structuur.
     * 
     * @param iscGemeenten
     *            isc gemeenten
     * @param gemeente
     *            gemeente
     * @return isc gemeenten (nieuwe structuur als input null is)
     */
    protected final IscGemeenten setLo3Gemeente(final IscGemeenten iscGemeenten, final BrpGemeenteCode gemeente) {
        IscGemeenten result;
        if (iscGemeenten == null) {
            result = FACTORY.createIscGemeenten();
        } else {
            result = iscGemeenten;
        }

        if (gemeente == null) {
            result.setLo3Gemeente(null);
        } else {
            result.setLo3Gemeente(gemeente.getFormattedStringCode());
        }
        return result;
    }
}
