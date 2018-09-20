/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import nl.bzk.brp.binding.BRPBericht;
import nl.bzk.brp.model.gedeeld.Partij;

/** Model class voor het xsd type OpvragenPersoon wat gebruikt wordt in bevragingsberichten. */
public class OpvragenPersoonBericht implements BRPBericht {

    private Partij                  afzender;
    private Calendar                tijdstipVerzonden;
    private OpvragenPersoonCriteria opvragenPersoonCriteria;

    public OpvragenPersoonCriteria getOpvragenPersoonCriteria() {
        return opvragenPersoonCriteria;
    }

    public Partij getAfzender() {
        return afzender;
    }

    public Calendar getTijdstipVerzonden() {
        return tijdstipVerzonden;
    }

    /**
     * Helper methode voor de Jibx binding. Zet tijdstipverzonden via een Date object.
     * @param ts Het date object wat de calendar in moet.
     */
    public void setTijdstipVerzondenDate(final Date ts) {
        if (ts != null) {
            tijdstipVerzonden = Calendar.getInstance();
            tijdstipVerzonden.setTime(ts);
        }
    }

    public Date getTijdstipVerzondenDate() {
        return tijdstipVerzonden.getTime();
    }

    /** {@inheritDoc} */
    @Override
    public Integer getPartijId() {
        Integer resultaat;

        if (afzender != null) {
            resultaat = afzender.getId();
        } else {
            resultaat = null;
        }
        return resultaat;
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getReadBsnLocks() {
        return Arrays.asList(opvragenPersoonCriteria.getIdentificatienummers().getBurgerservicenummer());
    }

    /** {@inheritDoc} */
    @Override
    public Collection<String> getWriteBsnLocks() {
        return null;
    }
}
