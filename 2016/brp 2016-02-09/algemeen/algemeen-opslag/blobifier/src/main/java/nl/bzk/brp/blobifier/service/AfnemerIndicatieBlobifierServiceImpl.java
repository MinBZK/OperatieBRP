/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import java.util.Set;

import javax.inject.Inject;

import nl.bzk.brp.blobifier.exceptie.AfnemerindicatiesNietAanwezigExceptie;
import nl.bzk.brp.blobifier.repository.alleenlezen.HisAfnemerindicatieBlobRepository;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.afnemerindicatie.AfnemerIndicatieSerializer;

import org.springframework.stereotype.Component;


/**
 * De implementatie van de service die het serialiseren en deserialiseren van afnemerindicaties mogelijk maakt.
 */
@Component
public final class AfnemerIndicatieBlobifierServiceImpl extends AbstractBlobifierService implements
        AfnemerIndicatieBlobifierService
{

    private static final Logger                LOGGER                            = LoggerFactory.getLogger();
    private static final String                PERSOON_HIS_VOLLEDIG_IS_VERPLICHT = "PersoonHisVolledig is verplicht";

    @Inject
    private AfnemerIndicatieSerializer         afnemerIndicatieSerializer;

    @Inject
    private HisAfnemerindicatieBlobRepository afnemerindicatieTabelRepository;

    @Override
    public void blobify(final PersoonHisVolledigImpl persoonHisVolledig) {
        if (persoonHisVolledig == null) {
            throw new IllegalArgumentException(PERSOON_HIS_VOLLEDIG_IS_VERPLICHT);
        }
        opslaanInAfnemerindicatieCache(persoonHisVolledig.getID(), persoonHisVolledig.getAfnemerindicaties());
    }

    @Override
    public void blobify(final Integer technischId) {
        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicatiesUitDatabase =
            haalAfnemerindicatiesUitDatabase(technischId);
        opslaanInAfnemerindicatieCache(technischId, afnemerindicatiesUitDatabase);
    }

    @Override
    public void blobify(final Integer technischId, final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties)
    {
        opslaanInAfnemerindicatieCache(technischId, afnemerindicaties);
    }

    @Override
    public Set<PersoonAfnemerindicatieHisVolledigImpl> leesBlob(final Integer technischId) {
        if (hisPersTabelRepository.bestaatPersoonMetId(technischId)) {
            return haalAfnemerindicatiesUitCacheOfDatabase(technischId);
        }

        throw new AfnemerindicatiesNietAanwezigExceptie("Afnemerindicaties voor persoon met id: " + technischId + " bestaan niet");
    }

    @Override
    public Set<PersoonAfnemerindicatieHisVolledigImpl> leesBlob(final PersoonHisVolledigImpl persoonHisVolledig) {
        if (persoonHisVolledig == null) {
            throw new IllegalArgumentException(PERSOON_HIS_VOLLEDIG_IS_VERPLICHT);
        }

        return haalAfnemerindicatiesUitCacheOfDatabase(persoonHisVolledig.getID());
    }

    /**
     * Haal de afnemerindicaties voor een persoon op.
     *
     * @param technischId de identificatie van de persoon
     * @return set van afnemerindicaties. Deze set kan leeg zijn.
     */
    private Set<PersoonAfnemerindicatieHisVolledigImpl> haalAfnemerindicatiesUitDatabase(final Integer technischId) {
        return afnemerindicatieTabelRepository.leesGenormaliseerdModelVoorNieuweBlob(technischId);
    }

    /**
     * Haalt set afnemerindicaties uit de cache of uit de database als geen cache bestaat.
     *
     * @param technischId De technische sleutel van de afnemerindicaties
     * @return set van afnemerindicaties
     */
    private Set<PersoonAfnemerindicatieHisVolledigImpl> haalAfnemerindicatiesUitCacheOfDatabase(
            final Integer technischId)
    {
        final PersoonCacheModel cache = leesPersoonCacheRepository.haalPersoonCacheOp(technischId);

        Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = null;

        if (cache != null) {
            afnemerindicaties = haalAfnemerindicatiesUitCache(cache);
            LOGGER.debug("Afnemerindicaties [{}] uit cache gehaald", technischId);
        }

        if (afnemerindicaties == null) {
            LOGGER.warn("Geen cache kunnen vinden/deserialiseren voor afnemerindicaties met id {}. Cache wordt in-memory gemaakt", technischId);
            afnemerindicaties = maakInMemoryBlob(technischId);
        }

        return afnemerindicaties;
    }

    /**
     * Maakt een tijdelijke in-memory blob voor een set afnemerindicaties. Dit doen we als er geen blob in de database
     * werd aangetroffen of als deze
     * corrupt is. We maken een in-memory blob zodat de objecten altijd hetzelfde reageren, of we nu een 'echte' blob
     * hebben of een 'in-memory' blob.
     *
     * @param technischId De technische sleutel van de afnemerindicaties
     * @return set van afnemerindicaties vanuit de in-memory blob
     */
    private Set<PersoonAfnemerindicatieHisVolledigImpl> maakInMemoryBlob(final Integer technischId) {
        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties =
            afnemerindicatieTabelRepository.leesGenormaliseerdModelVoorInMemoryBlob(technischId);
        if (afnemerindicaties == null) {
            throw new AfnemerindicatiesNietAanwezigExceptie("Afnemerindicaties met id " + technischId + " niet gevonden.");
        }

        LOGGER.debug("Ophalen van {} afnemerindicaties voor persoon met id: {}", afnemerindicaties.size(), technischId);

        final byte[] data = afnemerIndicatieSerializer.serialiseer(afnemerindicaties);
        return afnemerIndicatieSerializer.deserialiseer(data);
    }

    /**
     * Haalt de set van afnemerindicaties uit het cacheModel.
     *
     * @param cache het cachemodel om de indicaties uit te halen
     * @return set van afnemerindicaties, of {@code null} als deze niet gelezen kan worden
     */
    private Set<PersoonAfnemerindicatieHisVolledigImpl> haalAfnemerindicatiesUitCache(final PersoonCacheModel cache) {
        if (cache.getStandaard().getAfnemerindicatieChecksum() == null) {
            return null;
        }

        Set<PersoonAfnemerindicatieHisVolledigImpl> result = null;
        final String hash = checksumBerekenaar.berekenChecksum(cache.getStandaard().getAfnemerindicatieGegevens().getWaarde());
        if (hash.equals(cache.getStandaard().getAfnemerindicatieChecksum().getWaarde())) {
            result = deserializeObject(cache);
        } else {
            LOGGER.error("Afnemerindicaties blob [{}] heeft niet de correcte checksum: '{}' ipv '{}'",
                    cache.getPersoonId(), hash, cache.getStandaard().getAfnemerindicatieChecksum().getWaarde());
        }
        return result;
    }

    /**
     * Sla de afnemerindicaties op in de 'blob'.
     *
     * @param technischId identificatie van de blob
     * @param afnemerindicaties de indicaties om op te slaan
     */
    private void opslaanInAfnemerindicatieCache(final Integer technischId,
            final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties)
    {
        if (afnemerindicaties == null) {
            throw new IllegalArgumentException("Set afnemerindicaties is verplicht");
        }

        PersoonCacheModel persoonCache = schrijfPersoonCacheRepository.haalPersoonCacheOpUitMasterDatabase(technischId);
        if (persoonCache != null) {
            vulPersoonCache(persoonCache, afnemerindicaties);
            LOGGER.debug("Afnemerindicatie cache voor persoon {} aangepast in de database.", technischId);
        } else {
            persoonCache = new PersoonCacheModel(technischId);
            vulPersoonCache(persoonCache, afnemerindicaties);
            schrijfPersoonCacheRepository.opslaanNieuwePersoonCache(persoonCache);
            LOGGER.debug("Afnemerindicatie cache voor persoon {} toegevoegd aan de database.", technischId);
        }
    }

    /**
     * Vult de persoonCache met (verplichte) waarden: de checksum voor de afnemerindicaties en de geserialiseerde vorm
     * van de afnemerindicaties.
     *
     * @param persoonCache de bij te werken persooncache
     * @param afnemerindicaties de afnemerindicaties om op te slaan
     */
    private void vulPersoonCache(final PersoonCacheModel persoonCache,
            final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties)
    {
        final byte[] gegevens = serializeObject(afnemerindicaties);
        final String checksum = checksumBerekenaar.berekenChecksum(gegevens);

        persoonCache.getStandaard().setAfnemerindicatieChecksum(new ChecksumAttribuut(checksum));
        persoonCache.getStandaard().setAfnemerindicatieGegevens(new ByteaopslagAttribuut(gegevens));

        LOGGER.debug("Afnemerindicatie cache '{}' heeft hash '{}'", persoonCache.getID(), checksum);
    }

    /**
     * Serializeert een Set<{@link nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl}>
     * instantie.
     *
     * @param afnemerindicaties de set afnemerindicaties om te serialiseren
     * @return de geserializeerde vorm
     */
    private byte[] serializeObject(final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties) {
        try {
            return afnemerIndicatieSerializer.serialiseer(afnemerindicaties);
        } catch (final SerialisatieExceptie e) {
            LOGGER.error("Kan afnemerindicaties niet serializeren.", e);
            throw e;
        }
    }

    /**
     * Deserialiseert een byte[] naar een Set<
     * {@link nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl}> instantie.
     *
     * @param cache cache waarin de byte[] is opgeslagen
     * @return een set van afnemerindicaties, of {@code null} als deze niet gelezen kan worden.
     */
    private Set<PersoonAfnemerindicatieHisVolledigImpl> deserializeObject(final PersoonCacheModel cache) {
        try {
            return afnemerIndicatieSerializer.deserialiseer(cache.getStandaard().getAfnemerindicatieGegevens().getWaarde());
        } catch (final SerialisatieExceptie e) {
            LOGGER.error("Kan cache data [{}] niet deserializeren: {}", cache.getPersoonId(), e);
            return null;
        }
    }
}
