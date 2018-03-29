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
import java.util.stream.Collectors;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesPartijRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.PartijType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.register.Partij;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.PartijRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.register.Rol;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees gemeente register antwoord.
 */
public final class LeesPartijRegisterAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private static final String DATE_FORMAT = "yyyyMMdd";

    static {
        // Registreer partijRegister als veld om te negeren bij hashCode, equals en toString
        registerExcludedFieldNames(LeesPartijRegisterAntwoordBericht.class, new String[]{"partijRegister"});
    }

    private final LeesPartijRegisterAntwoordType leesPartijRegisterAntwoordType;
    private PartijRegister partijRegister;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesPartijRegisterAntwoordBericht() {
        this(new LeesPartijRegisterAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     * @param jaxb het jaxb element
     */
    public LeesPartijRegisterAntwoordBericht(final LeesPartijRegisterAntwoordType jaxb) {
        super("LeesPartijRegisterAntwoord");
        leesPartijRegisterAntwoordType = jaxb;
        final List<Partij> partijen = new ArrayList<>();
        final SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        if (leesPartijRegisterAntwoordType.getPartijRegister() != null) {
            verwerkPartijen(partijen, dateFormat);
        }
        partijRegister = new PartijRegisterImpl(partijen);
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesPartijRegisterAntwoord(leesPartijRegisterAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van status.
     * @return status
     */
    public StatusType getStatus() {
        return leesPartijRegisterAntwoordType.getStatus();
    }

    /**
     * Zet de waarde van status.
     * @param status status
     */
    public void setStatus(final StatusType status) {
        leesPartijRegisterAntwoordType.setStatus(status);
    }

    /**
     * Geef de waarde van partij register.
     * @return partij register
     */
    public PartijRegister getPartijRegister() {
        return partijRegister;
    }

    private void verwerkPartijen(final List<Partij> partijen, final SimpleDateFormat dateFormat) {
        for (final PartijType partij : leesPartijRegisterAntwoordType.getPartijRegister().getPartij()) {
            final Date datumOvergangNaarBrp;
            if (partij.getDatumBrp() != null) {
                try {
                    datumOvergangNaarBrp = dateFormat.parse(partij.getDatumBrp().toString());
                } catch (final ParseException e) {
                    throw new IllegalArgumentException("Ongeldige datum ontvangen voor partij register '" + partij.getPartijCode() + "'.", e);
                }
            } else {
                datumOvergangNaarBrp = null;
            }

            final List<Rol> rollen = partij.getRollen().stream().map(rolType -> Rol.valueOf(rolType.value())).collect(Collectors.toList());
            partijen.add(new Partij(partij.getPartijCode(), partij.getGemeenteCode(), datumOvergangNaarBrp, rollen));
        }
    }

}
