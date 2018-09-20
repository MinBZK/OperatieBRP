/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBerichtZonderGerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.generated.AutorisatieRegisterVoorkomenType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.LeesAutorisatieRegisterAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.register.Autorisatie;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegister;
import nl.bzk.migratiebrp.bericht.model.sync.register.AutorisatieRegisterImpl;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;

/**
 * Lees autorisatie register antwoord.
 */
public final class LeesAutorisatieRegisterAntwoordBericht extends AbstractSyncBerichtZonderGerelateerdeInformatie {
    private static final long serialVersionUID = 1L;

    private final LeesAutorisatieRegisterAntwoordType leesAutorisatieRegisterAntwoordType;
    private AutorisatieRegister autorisatieRegister;

    /* ************************************************************************************************************* */

    /**
     * Default constructor.
     */
    public LeesAutorisatieRegisterAntwoordBericht() {
        this(new LeesAutorisatieRegisterAntwoordType());
    }

    /**
     * Deze constructor wordt gebruikt door de factory om op basis van een Jaxb element een storebericht te maken.
     *
     * @param jaxb
     *            het jaxb element
     */
    public LeesAutorisatieRegisterAntwoordBericht(final LeesAutorisatieRegisterAntwoordType jaxb) {
        super("LeesAutorisatieRegisterAntwoord");
        leesAutorisatieRegisterAntwoordType = jaxb;
    }

    /* ************************************************************************************************************* */

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createLeesAutorisatieRegisterAntwoord(leesAutorisatieRegisterAntwoordType));
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van status.
     *
     * @return status
     */
    public StatusType getStatus() {
        return leesAutorisatieRegisterAntwoordType.getStatus();
    }

    /**
     * Zet de waarde van status.
     *
     * @param status
     *            status
     */
    public void setStatus(final StatusType status) {
        leesAutorisatieRegisterAntwoordType.setStatus(status);
    }

    /* ************************************************************************************************************* */

    /**
     * Geef de waarde van afnemer register.
     *
     * @return afnemer register
     */
    public AutorisatieRegister getAutorisatieRegister() {
        // Threading/locking: niet nodig, niet erg als dit twee keer gebeurt
        if (autorisatieRegister == null) {
            final List<Autorisatie> autorisaties = new ArrayList<>();
            if (leesAutorisatieRegisterAntwoordType.getAutorisatieRegister() != null) {
                for (final AutorisatieRegisterVoorkomenType autorisatie : leesAutorisatieRegisterAntwoordType.getAutorisatieRegister().getAutorisatie()) {
                    autorisaties.add(maakAutorisatie(autorisatie));
                }
            }
            autorisatieRegister = new AutorisatieRegisterImpl(autorisaties);
        }
        return autorisatieRegister;
    }

    private Autorisatie maakAutorisatie(final AutorisatieRegisterVoorkomenType autorisatie) {
        return new Autorisatie(
            autorisatie.getPartijCode(),
            autorisatie.getToegangLeveringsautorisatieId(),
            autorisatie.getPlaatsenAfnemersindicatiesDienstId(),
            autorisatie.getVerwijderenAfnemersindicatiesDienstId(),
            autorisatie.getBevragenPersoonDienstId(),
            autorisatie.getBevragenAdresDienstId());
    }

}
