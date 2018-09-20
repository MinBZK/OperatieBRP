/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.MultiRealiteitRegel;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;

/**
 * Context voor een conversie.
 */
public final class ConverterContext {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final Map<Integer, BRPActie> acties = new HashMap<Integer, BRPActie>();
    private final Map<Integer, Persoon> personen = new HashMap<Integer, Persoon>();
    private final Map<Integer, Relatie> relaties = new HashMap<Integer, Relatie>();
    private final Map<Integer, Betrokkenheid> betrokkenheden = new HashMap<Integer, Betrokkenheid>();
    private final Map<Integer, MultiRealiteitRegel> mrRegels = new HashMap<Integer, MultiRealiteitRegel>();

    /**
     * Registreer een persoon.
     * 
     * @param localId
     *            lokaal id
     * @param persoon
     *            persoon
     */
    public void storePersoon(final int localId, final Persoon persoon) {
        LOG.info("storePersoon(localId={}, persoon={}", localId, persoon);
        personen.put(localId, persoon);
    }

    /**
     * Zoek een persoon.
     * 
     * @param localId
     *            lokaal id
     * @return persoon
     * @throws IllegalArgumentException
     *             wanneer de persoon niet is gevonden
     */
    public Persoon getPersoon(final int localId) {
        if (personen.containsKey(localId)) {
            return personen.get(localId);
        } else {
            throw new IllegalArgumentException(String.format("Persoon met localId '%s' niet bekend.", localId));
        }
    }

    /**
     * Geef alle geregistreerde personen.
     * 
     * @return lijst met personen
     */
    public List<Persoon> getPersonen() {
        return new ArrayList<Persoon>(personen.values());
    }

    /**
     * Registreer een actie.
     * 
     * @param localId
     *            lokaal id
     * @param actie
     *            actie
     */
    public void storeActie(final int localId, final BRPActie actie) {
        LOG.info("storeActie(localId={}, actie={}", localId, actie);
        acties.put(localId, actie);
    }

    /**
     * Zoek een actie.
     * 
     * @param localId
     *            lokaal id
     * @return actie
     * @throws IllegalArgumentException
     *             wanneer de actie niet is gevonden
     */
    public BRPActie getActie(final int localId) {
        if (acties.containsKey(localId)) {
            return acties.get(localId);
        } else {
            throw new IllegalArgumentException(String.format("Actie met localId '%s' niet bekend.", localId));
        }
    }

    /**
     * Geef alle geregistreerde actie.
     * 
     * @return lijst met acties
     */
    public List<BRPActie> getActies() {
        return new ArrayList<BRPActie>(acties.values());
    }

    /**
     * Registreer een relatie.
     * 
     * @param localId
     *            lokaal id
     * @param relatie
     *            relatie
     */
    public void storeRelatie(final int localId, final Relatie relatie) {
        LOG.info("storeRelatie(localId={}, relatie={}", localId, relatie);
        relaties.put(localId, relatie);
    }

    /**
     * Zoek een relatie.
     * 
     * @param localId
     *            lokaal id
     * @return relatie
     * @throws IllegalArgumentException
     *             wanneer de relatie niet is gevonden
     */
    public Relatie getRelatie(final int localId) {
        if (relaties.containsKey(localId)) {
            return relaties.get(localId);
        } else {
            throw new IllegalArgumentException(String.format("Relatie met localId '%s' niet bekend.", localId));
        }
    }

    /**
     * Geef alle geregistreerde relaties.
     * 
     * @return lijst met relaties
     */
    public List<Relatie> getRelaties() {
        return new ArrayList<Relatie>(relaties.values());
    }

    /**
     * Registreer een betrokkenheid.
     * 
     * @param localId
     *            lokaal id
     * @param betrokkenheid
     *            betrokkenheid
     */
    public void storeBetrokkenheid(final int localId, final Betrokkenheid betrokkenheid) {
        LOG.info("storeBetrokkenheid(localId={}, relatie={}", localId, betrokkenheid);
        betrokkenheden.put(localId, betrokkenheid);
    }

    /**
     * Zoek een betrokkenheid.
     * 
     * @param localId
     *            lokaal id
     * @return betrokkenheid
     * @throws IllegalArgumentException
     *             wanneer de betrokkenheid niet is gevonden
     */
    public Betrokkenheid getBetrokkenheid(final int localId) {
        if (betrokkenheden.containsKey(localId)) {
            return betrokkenheden.get(localId);
        } else {
            throw new IllegalArgumentException(String.format("Betrokkenheid met localId '%s' niet bekend.", localId));
        }
    }

    /**
     * Geef alle geregistreerde betrokkenheden.
     * 
     * @return lijst met betrokkenheden
     */
    public List<Betrokkenheid> getBetrokkenheden() {
        return new ArrayList<Betrokkenheid>(betrokkenheden.values());
    }

    /**
     * Registreer een MR-regel.
     * 
     * @param localId
     *            lokaal id
     * @param mrRegel
     *            MR-regel
     */
    public void storeMRRegel(final int localId, final MultiRealiteitRegel mrRegel) {
        LOG.info("storeMRRegel(localId={}, actie={}", localId, mrRegel);
        mrRegels.put(localId, mrRegel);
    }

    /**
     * Zoek een MR-regel.
     * 
     * @param localId
     *            lokaal id
     * @return MR-regel
     * @throws IllegalArgumentException
     *             wanneer de MR-regel niet is gevonden
     */
    public MultiRealiteitRegel getMRRegel(final int localId) {
        if (mrRegels.containsKey(localId)) {
            return mrRegels.get(localId);
        } else {
            throw new IllegalArgumentException(String.format("MR-regel met localId '%s' niet bekend.", localId));
        }
    }

    /**
     * Geef alle geregistreerde MR-regels.
     * 
     * @return lijst met MR-regels
     */
    public List<MultiRealiteitRegel> getMRRegels() {
        return new ArrayList<MultiRealiteitRegel>(mrRegels.values());
    }

}
