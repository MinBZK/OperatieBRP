/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.sync.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.bericht.model.GerelateerdeInformatie;
import nl.bzk.migratiebrp.bericht.model.sync.AbstractSyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.generated.ObjectFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType;
import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpAntwoordType.Kandidaat;
import nl.bzk.migratiebrp.bericht.model.sync.xml.SyncXml;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;

/**
 * Dit bericht wordt verstuurd om een LO3 Persoonslijst (serialized) te valideren op pre-condities, te converteren naar
 * een BRP-persoon en vervolgens op te slaan in de BRP database. Dit bericht wordt beantwoord met een
 * SynchroniseerNaarBrpAntwoordBericht.
 * @see SynchroniseerNaarBrpAntwoordBericht
 */
public final class SynchroniseerNaarBrpAntwoordBericht extends AbstractSyncBericht {

    private static final long serialVersionUID = 1L;

    private final SynchroniseerNaarBrpAntwoordType synchroniseerNaarBrpAntwoordType;

    // ****************************** Constructors ******************************

    /**
     * Default constructor.
     */
    public SynchroniseerNaarBrpAntwoordBericht() {
        this(new SynchroniseerNaarBrpAntwoordType());
    }

    /**
     * JAXB constructor.
     * @param synchroniseerBrpAntwoordType Het synchronisatieantwoord type.
     */
    public SynchroniseerNaarBrpAntwoordBericht(final SynchroniseerNaarBrpAntwoordType synchroniseerBrpAntwoordType) {
        super("SynchroniseerNaarBrpAntwoord");
        synchroniseerNaarBrpAntwoordType = synchroniseerBrpAntwoordType;
    }

    // ****************************** Bericht methodes ******************************

    @Override
    public String format() {
        return SyncXml.SINGLETON.elementToString(new ObjectFactory().createSynchroniseerNaarBrpAntwoord(synchroniseerNaarBrpAntwoordType));
    }

    // ****************************** Public methodes ******************************

    /**
     * Geeft de status {@link StatusType} van het bericht terug.
     * @return De status {@link StatusType} van het bericht.
     */
    public StatusType getStatus() {
        return synchroniseerNaarBrpAntwoordType.getStatus();
    }

    /**
     * Zet de status {@link StatusType} op het bericht.
     * @param status De te zetten status {@link StatusType}.
     */
    public void setStatus(final StatusType status) {
        synchroniseerNaarBrpAntwoordType.setStatus(status);
    }

    /**
     * Geeft de melding van het bericht terug.
     * @return De melding van het bericht.
     */
    public String getMelding() {
        return synchroniseerNaarBrpAntwoordType.getMelding();
    }

    /**
     * Zet de melding op het bericht.
     * @param melding De te zetten melding.
     */
    public void setMelding(final String melding) {
        synchroniseerNaarBrpAntwoordType.setMelding(melding);
    }

    /**
     * Geeft de logregels op het bericht terug.
     * @return De logregels op het bericht.
     */
    public List<LogRegel> getLogging() {
        return asLogRegelList(synchroniseerNaarBrpAntwoordType.getLogging());
    }

    /**
     * Zet de logregels op het bericht.
     * @param logRegels De te zetten logregels.
     */
    public void setLogging(final Set<LogRegel> logRegels) {
        synchroniseerNaarBrpAntwoordType.setLogging(asLogRegelType(logRegels));
    }

    /**
     * Kandidaat toevoegen.
     * @param persoonid persoon id
     * @param versie versie
     * @param plAlsTeletexString persoonslijst als lo3 teletex string
     */
    public void toevoegenKandidaat(final int persoonid, final int versie, final String plAlsTeletexString) {
        final Kandidaat kandidaat = new Kandidaat();
        kandidaat.setPersoonId(persoonid);
        kandidaat.setVersie(versie);
        kandidaat.setLo3PersoonslijstAlsTeletexString(plAlsTeletexString);
        synchroniseerNaarBrpAntwoordType.getKandidaat().add(kandidaat);
    }

    /**
     * Geef de kandidaten.
     * @return kandidaten
     */
    public List<Kandidaat> getKandidaten() {
        return synchroniseerNaarBrpAntwoordType.getKandidaat();
    }

    /**
     * Zet de kandidaten.
     * @param kandidaten kandidaten
     */
    public void setKandidaten(final List<Kandidaat> kandidaten) {
        synchroniseerNaarBrpAntwoordType.getKandidaat().clear();

        if (kandidaten != null) {
            synchroniseerNaarBrpAntwoordType.getKandidaat().addAll(kandidaten);
        }
    }

    /**
     * Geeft de administratie handeling ids.
     * @return administratie handeling ids
     */
    public List<Long> getAdministratieveHandelingIds() {
        return synchroniseerNaarBrpAntwoordType.getAdministratieveHandelingId();
    }

    /**
     * Zet de administratieve handeling ids.
     * @param administratieveHandelingIds lijst van de administratieve handeling ids
     */
    public void setAdministratieveHandelingIds(final List<Long> administratieveHandelingIds) {
        synchroniseerNaarBrpAntwoordType.getAdministratieveHandelingId().clear();
        synchroniseerNaarBrpAntwoordType.getAdministratieveHandelingId().addAll(administratieveHandelingIds);
    }

    // ******************************

    @Override
    public GerelateerdeInformatie getGerelateerdeInformatie() {
        return new GerelateerdeInformatie(getAdministratieveHandelingIds(), Collections.<String>emptyList(), Collections.<String>emptyList());
    }

}
