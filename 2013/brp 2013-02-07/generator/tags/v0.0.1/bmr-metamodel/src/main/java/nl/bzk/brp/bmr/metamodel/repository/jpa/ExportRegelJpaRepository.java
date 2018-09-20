/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nl.bzk.brp.bmr.metamodel.ExportRegel;
import nl.bzk.brp.bmr.metamodel.Laag;
import nl.bzk.brp.bmr.metamodel.SoortExport;
import nl.bzk.brp.bmr.metamodel.repository.ExportRegelRepository;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


@Repository
public class ExportRegelJpaRepository implements ExportRegelRepository {

    /**
     * Abstracte superklasse voor zoeksleutels voor de in-memory zoeklijst van export regels. Beide zoeksleutels hebben
     * een {@link #interneIdentifier} namelijk. Getters en setters zijn overbodig, omdat dit een interne private klasse
     * is.
     */
    abstract private class Sleutel {

        @SuppressWarnings("unused")
        public String interneIdentifier;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(final Object obj) {
            return EqualsBuilder.reflectionEquals(this, obj);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this);
        }
    }

    /**
     * Sleutel om in de in-memory zoeklijst van export regels te zoeken op interne identifier en naam.
     */
    private class NaamSleutel extends Sleutel {

        @SuppressWarnings("unused")
        public String naam;

        public NaamSleutel(final String interneIdentifier, final String naam) {
            this.interneIdentifier = interneIdentifier;
            this.naam = naam;
        }
    }

    /**
     * Sleutel om in de in-memory zoeklijst van export regels te zoeken op interne identifier en syncId.
     */
    private class SyncIdSleutel extends Sleutel {

        @SuppressWarnings("unused")
        public Integer syncId;

        public SyncIdSleutel(final String interneIdentifier, final Integer syncId) {
            this.interneIdentifier = interneIdentifier;
            this.syncId = syncId;
        }
    }

    private static final Logger             LOGGER = LoggerFactory.getLogger(ExportRegelJpaRepository.class);

    @PersistenceContext
    private EntityManager                   em;
    /**
     * Een in-memory index voor de export regels op interne identifier en syncId.
     */
    private Map<SyncIdSleutel, ExportRegel> syncIdMap;

    /**
     * Een in-memory index voor de export regels op interne identifier en naam.
     */
    private Map<NaamSleutel, ExportRegel>   naamMap;

    public EntityManager getEntityManager() {
        return em;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExportIdentifier(final String interneIdentifier, final String naam, final Integer syncId) {
        assert interneIdentifier != null;
        assert naam != null;
        assert syncId != null;

        if (syncIdMap == null) {
            initialiseerSyncIdMap();
        }
        String exportIdentifier = null;
        ExportRegel regel = syncIdMap.get(new SyncIdSleutel(interneIdentifier, syncId));
        if ((regel != null) && (regel.getExportIdentifier() != null)) {
            exportIdentifier = regel.getExportIdentifier();
        } else {
            exportIdentifier = voorlopigeExportIdentifier(interneIdentifier, syncId);
        }
        if (regel == null) {
            regel = new ExportRegel();
            regel.setInterneIdentifier(interneIdentifier);
            regel.setLaag(Laag.getHuidigeLaag());
            regel.setSoort(String.valueOf(SoortExport.getHuidigeSoort().ordinal()));
            regel.setSyncId(syncId);
            regel.setNaam(naam);
            Query query =
                em.createNativeQuery("INSERT INTO exportregel(soort, laag, naam, syncid, interne_identifier) "
                    + "VALUES (:soort, :laag, :naam, :syncid, :interne_identifier);");
            query.setParameter("soort", regel.getSoort());
            query.setParameter("laag", regel.getLaag());
            query.setParameter("syncid", regel.getSyncId());
            query.setParameter("naam", regel.getNaam());
            query.setParameter("interne_identifier", regel.getInterneIdentifier());
            query.executeUpdate();
            SyncIdSleutel key = new SyncIdSleutel(regel.getInterneIdentifier(), regel.getSyncId());
            LOGGER.debug("Nieuwe exportregel gepersisteerd: {} met key {}.", regel, key);
            syncIdMap.put(key, regel);
            return voorlopigeExportIdentifier(interneIdentifier, syncId);
        }
        if (!naam.equals(regel.getNaam())) {
            /*
             * Anders kan de import deze regel een volgende keer niet vinden.
             */
            regel.setNaam(naam);
        }
        return exportIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExportIdentifier(final String interneIdentifier, final String naam, final String exportIdentifier) {
        if (naamMap == null) {
            initialiseerNaamMap();
        }
        ExportRegel regel = naamMap.get(new NaamSleutel(interneIdentifier, naam));
        /*
         * We accepteren alleen export identifiers van EA voor elementen die we zelf eerst geëxporteerd hebben.
         */
        if (regel == null) {
            StringBuilder message = new StringBuilder("Export identifier niet gevonden: ");
            throw new IllegalStateException(message.append(interneIdentifier).append(":").append(naam).toString());
        }
        /*
         * We accepteren ook geen wijzigingen op export identifiers die we al hebben, want dat zou betekenen dat de
         * export van EA gemaakt is van een model dat niet geïmporteerd is uit onze export.
         */
        if ((regel.getExportIdentifier() != null) && !regel.getExportIdentifier().equals(exportIdentifier)) {
            StringBuilder message = new StringBuilder("Bestaande export identifier voor ");
            message.append(interneIdentifier).append(":").append(naam);
            message.append(" is door EA gewijzigd.");
            throw new IllegalStateException(message.toString());
        }
        if (regel.getExportIdentifier() == null) {
            /*
             * Voorkom onnodige updates, want die vertragen anders de import.
             */
            LOGGER.debug("Nieuwe export identifier voor {} en {}: {})", new Object[] { interneIdentifier, naam,
                exportIdentifier });
            regel.setExportIdentifier(exportIdentifier);
        }
    }

    /**
     * Fabriceer een voorlopige export identifier voor gebruik in de export file. Deze wordt niet bij de export regels
     * bewaard. Enterprise Architect zal deze vervangen door een GUID, die dan met de import terugkomt en w&egrave;l in
     * de
     * export regel bewaard wordt.
     *
     * @param interneIdentifier wordt gebruikt om een export identifier uit te destilleren.
     * @param syncId wordt ook gebruikt om een export identifier uit te destilleren.
     * @return de gedestilleerde voorlopige export identifier.
     */
    private String voorlopigeExportIdentifier(final String interneIdentifier, final Integer syncId) {
        StringBuilder resultaat = new StringBuilder();
        resultaat.append(interneIdentifier).append("_").append(String.valueOf(syncId));
        return resultaat.toString();
    }

    /**
     * Zoek alle exportregels van de huidige soort en laag.
     *
     * @return alle exportregels van de huidige soort en laag.
     */
    private List<ExportRegel> findAll() {
        List<ExportRegel> resultList =
            em.createQuery("SELECT er FROM ExportRegel er WHERE soort = :soort AND laag = :laag", ExportRegel.class)
                    .setParameter("soort", String.valueOf(SoortExport.getHuidigeSoort().ordinal()))
                    .setParameter("laag", Laag.getHuidigeLaag()).getResultList();
        return resultList;
    }

    private void initialiseerSyncIdMap() {
        syncIdMap = new HashMap<SyncIdSleutel, ExportRegel>();
        for (ExportRegel regel : findAll()) {
            syncIdMap.put(new SyncIdSleutel(regel.getInterneIdentifier(), regel.getSyncId()), regel);
        }
    }

    private void initialiseerNaamMap() {
        naamMap = new HashMap<NaamSleutel, ExportRegel>();
        for (ExportRegel regel : findAll()) {
            naamMap.put(new NaamSleutel(regel.getInterneIdentifier(), regel.getNaam()), regel);
        }
    }
}
