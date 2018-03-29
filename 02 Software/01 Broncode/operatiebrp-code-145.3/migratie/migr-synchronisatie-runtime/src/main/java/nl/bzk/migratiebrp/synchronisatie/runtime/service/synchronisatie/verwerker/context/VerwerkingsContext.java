/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;

/**
 * De context van een verwerking.
 */
public final class VerwerkingsContext {

    private final SynchroniseerNaarBrpVerzoekBericht verzoek;
    private final Lo3Bericht logging;
    private final Lo3Persoonslijst lo3Persoonslijst;
    private final BrpPersoonslijst brpPersoonslijst;
    private final BrpPartijCode brpVerzendendePartij;
    private final String anummer;
    private final String burgerServicenummer;
    private final String vorigAnummer;
    private final Map<Identifier, List<BrpPersoonslijst>> resultaatCache = new HashMap<>();
    private final Map<Long, BrpPersoonslijst> kandidaten = new HashMap<>();

    /**
     * Constructor.
     * @param verzoek het synchronisatie verzoek
     * @param logging het lo3 logging bericht
     * @param lo3Persoonslijst de geparsde aangeboden lo3 persoonsljist
     * @param brpPersoonslijst de geconverteerde aangeboden brp persoonslijst
     */
    public VerwerkingsContext(
            final SynchroniseerNaarBrpVerzoekBericht verzoek,
            final Lo3Bericht logging,
            final Lo3Persoonslijst lo3Persoonslijst,
            final BrpPersoonslijst brpPersoonslijst) {
        this(verzoek, logging, lo3Persoonslijst, brpPersoonslijst, null);
    }

    /**
     * Constructor.
     * @param verzoek het synchronisatie verzoek
     * @param logging het lo3 logging bericht
     * @param lo3Persoonslijst de geparsde aangeboden lo3 persoonsljist
     * @param brpPersoonslijst de geconverteerde aangeboden brp persoonslijst
     * @param brpVerzendendePartij de partij welke het bericht heeft verstuurt
     */
    public VerwerkingsContext(
            final SynchroniseerNaarBrpVerzoekBericht verzoek,
            final Lo3Bericht logging,
            final Lo3Persoonslijst lo3Persoonslijst,
            final BrpPersoonslijst brpPersoonslijst,
            final BrpPartijCode brpVerzendendePartij) {
        this.verzoek = verzoek;
        this.logging = logging;
        this.lo3Persoonslijst = lo3Persoonslijst;
        this.brpPersoonslijst = brpPersoonslijst;
        this.brpVerzendendePartij = brpVerzendendePartij;

        anummer = bepaalAnummer(this.brpPersoonslijst);
        burgerServicenummer = bepaalBurgerServicenummer(this.brpPersoonslijst);
        vorigAnummer = bepaalVorigAnummer(this.brpPersoonslijst);
    }

    private static String bepaalAnummer(final BrpPersoonslijst brpPersoonslijst) {
        if (brpPersoonslijst != null) {
            return brpPersoonslijst.getActueelAdministratienummer();
        } else {
            return null;
        }
    }

    private static String bepaalBurgerServicenummer(final BrpPersoonslijst brpPersoonslijst) {
        if (brpPersoonslijst != null) {
            return brpPersoonslijst.getActueelBurgerservicenummer();
        } else {
            return null;
        }
    }

    private static String bepaalVorigAnummer(final BrpPersoonslijst brpPersoonslijst) {
        if (brpPersoonslijst != null
                && brpPersoonslijst.getNummerverwijzingStapel() != null
                && brpPersoonslijst.getNummerverwijzingStapel().getActueel() != null
                && brpPersoonslijst.getNummerverwijzingStapel().getActueel().getInhoud() != null
                && brpPersoonslijst.getNummerverwijzingStapel().getActueel().getInhoud().getVorigeAdministratienummer() != null) {
            return brpPersoonslijst.getNummerverwijzingStapel().getActueel().getInhoud().getVorigeAdministratienummer().getWaarde();
        } else {
            return null;
        }
    }

    /**
     * Geef de waarde van anummer.
     * @return anummer
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Geef de waarde van burgerServicenummer.
     * @return burgerServicenummer
     */
    public String getBurgerServicenummer() {
        return burgerServicenummer;
    }

    /**
     * Geef de waarde van verzoek.
     * @return verzoek
     */
    public SynchroniseerNaarBrpVerzoekBericht getVerzoek() {
        return verzoek;
    }

    /**
     * Geef de waarde van brp persoonslijst.
     * @return brp persoonslijst
     */
    public BrpPersoonslijst getBrpPersoonslijst() {
        return brpPersoonslijst;
    }

    /**
     * Geef de waarde van lo3 persoonslijst.
     * @return lo3 persoonslijst
     */
    public Lo3Persoonslijst getLo3Persoonslijst() {
        return lo3Persoonslijst;
    }

    /**
     * Geef de waarde van logging bericht.
     * @return logging bericht
     */
    public Lo3Bericht getLoggingBericht() {
        return logging;
    }

    /**
     * Geef de waarde van kandidaten.
     * @return kandidaten
     */
    public List<BrpPersoonslijst> getKandidaten() {
        return new ArrayList<>(kandidaten.values());
    }

    /**
     * Voeg kandidaten toe.
     * @param nieuweKandidaten kandidaten
     */
    public void kandidatenToevoegen(final List<BrpPersoonslijst> nieuweKandidaten) {
        for (final BrpPersoonslijst nieuweKandidaat : nieuweKandidaten) {
            kandidaten.put(nieuweKandidaat.getPersoonId(), nieuweKandidaat);
        }
    }

    /**
     * Cache: bevat resultaat voor.
     * @param identifier identifier
     * @return true, als het resultaat al bekend is
     */
    public boolean bevatResultaatVoor(final Identifier identifier) {
        return resultaatCache.containsKey(identifier);
    }

    /**
     * Cache: geef resultaat voor.
     * @param identifier identifier
     * @return resultaat, null als het niet bekend is
     */
    public List<BrpPersoonslijst> geefResultaatVoor(final Identifier identifier) {
        return resultaatCache.get(identifier);
    }

    /**
     * Cache: bewaar resultaat voor.
     * @param identifier identifier
     * @param resultaat resultaat
     */
    public void bewaarResultaatVoor(final Identifier identifier, final List<BrpPersoonslijst> resultaat) {
        resultaatCache.put(identifier, resultaat);
    }

    /**
     * Geef de waarde van vorig anummer.
     * @return vorig anummer
     */
    public String getVorigAnummer() {
        return vorigAnummer;
    }

    public BrpPartijCode getVerzendendePartij() {
        return brpVerzendendePartij;
    }

}
