/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.GemeenteType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesGemeenteRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.register.Gemeente;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.GemeenteRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees gemeente register antwoord.
 */
public final class LeesGemeenteRegisterAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private static final String DATE_FORMAT = "yyyyMMdd";

    static {
        // Registreer gemeenteRegister als veld om te negeren bij hashCode, equals en toString
        registerExcludedFieldNames(LeesGemeenteRegisterAntwoordBericht.class, new String[] {"gemeenteRegister" });
    }

    private final LeesGemeenteRegisterAntwoordType leesGemeenteRegisterAntwoordType;
    private GemeenteRegister gemeenteRegister;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesGemeenteRegisterAntwoordBericht() {
        this(new LeesGemeenteRegisterAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param jaxb
     *            het jaxb element
     */
    public LeesGemeenteRegisterAntwoordBericht(final LeesGemeenteRegisterAntwoordType jaxb) {
        super("LeesGemeenteRegisterAntwoord");
        leesGemeenteRegisterAntwoordType = jaxb;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesGemeenteRegisterAntwoord(leesGemeenteRegisterAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van status.
     *
     * @return status
     */
    public StatusType getStatus() {
        return leesGemeenteRegisterAntwoordType.getStatus();
    }

    /**
     * Zet de waarde van status.
     *
     * @param status
     *            status
     */
    public void setStatus(final StatusType status) {
        leesGemeenteRegisterAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van gemeente register.
     *
     * @return gemeente register
     */
    public GemeenteRegister getGemeenteRegister() {
        // Threading/locking: niet nodig, niet erg als dit twee keer gebeurt
        if (gemeenteRegister == null) {
            final List<Gemeente> gemeenten = new ArrayList<>();
            final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
            if (leesGemeenteRegisterAntwoordType.getGemeenteRegister() != null) {
                for (final GemeenteType gemeente : leesGemeenteRegisterAntwoordType.getGemeenteRegister().getGemeente()) {
                    final Date datumOvergangNaarBrp;
                    if (gemeente.getDatumBrp() != null) {
                        try {
                            datumOvergangNaarBrp = dateFormat.parse(gemeente.getDatumBrp().toString());
                        } catch (final ParseException e) {
                            throw new IllegalArgumentException("Ongeldige datum ontvangen voor gemeente register '" + gemeente.getGemeenteCode() + "'.", e);
                        }
                    } else {
                        datumOvergangNaarBrp = null;
                    }

                    gemeenten.add(new Gemeente(gemeente.getGemeenteCode(), gemeente.getPartijCode(), datumOvergangNaarBrp));
                }
            }
            gemeenteRegister = new GemeenteRegisterImpl(gemeenten);
        }
        return gemeenteRegister;
    }

}
