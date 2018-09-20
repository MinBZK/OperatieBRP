/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.blobifier.exceptie.NietUniekeAnummerExceptie;
import nl.bzk.brp.blobifier.exceptie.NietUniekeBsnExceptie;
import nl.bzk.brp.blobifier.repository.alleenlezen.AfnemerindicatiesHisVolledigImplLuieLader;
import nl.bzk.brp.blobifier.repository.alleenlezen.PersoonHisVolledigImplLuieLader;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdministratienummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.hisvolledig.SerialisatieExceptie;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigSerializer;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.stereotype.Component;


/**
 * De implementatie van de service die het serialiseren en deserialiseren van een persoon mogelijk maakt.
 */
@Component
public final class BlobifierServiceImpl extends AbstractBlobifierService implements BlobifierService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PersoonHisVolledigSerializer persoonHisVolledigSerializer;

    @Inject
    private AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    @Override
    public void blobify(final PersoonHisVolledigImpl persoonHisVolledig, final boolean blobifyNietIngeschrevenBetrokkenen) {
        opslaanInPersoonCache(persoonHisVolledig);
    }

    @Override
    public void blobify(final Integer technischId, final boolean blobifyNietIngeschrevenBetrokkenen) {
        final PersoonHisVolledigImpl persoonHisVolledig = haalPersoonUitDatabase(technischId);
        opslaanInPersoonCache(persoonHisVolledig);
    }

    @Override
    public void blobify(final BurgerservicenummerAttribuut burgerservicenummer, final boolean blobifyNietIngeschrevenBetrokkenen)
        throws NietUniekeBsnExceptie
    {
        final Integer technischId = vindTechnischIdVoorBsn(burgerservicenummer);
        final PersoonHisVolledigImpl persoonHisVolledig = haalPersoonUitDatabase(technischId);
        opslaanInPersoonCache(persoonHisVolledig);
    }

    @Override
    public void blobify(final AdministratienummerAttribuut administratienummer, final boolean blobifyNietIngeschrevenBetrokkenen)
        throws NietUniekeAnummerExceptie
    {
        final Integer technischId = vindTechnischIdVoorANummer(administratienummer);
        final PersoonHisVolledigImpl persoonHisVolledig = haalPersoonUitDatabase(technischId);
        opslaanInPersoonCache(persoonHisVolledig);
    }

    @Override
    public PersoonHisVolledigImpl leesBlob(final Integer technischId) {
        return haalPersoonHisVolledigUitCacheOfDatabase(technischId);
    }

    @Override
    public PersoonHisVolledigImpl leesBlob(final BurgerservicenummerAttribuut burgerservicenummer) throws
        NietUniekeBsnExceptie
    {
        final Integer technischId = vindTechnischIdVoorBsn(burgerservicenummer);
        return haalPersoonHisVolledigUitCacheOfDatabase(technischId);
    }

    @Override
    public PersoonHisVolledigImpl leesBlobActievePersoon(final BurgerservicenummerAttribuut burgerservicenummer) throws
        NietUniekeBsnExceptie
    {
        final Integer technischId = vindTechnischIdVoorBsnActievePersoon(burgerservicenummer);
        return haalPersoonHisVolledigUitCacheOfDatabase(technischId);
    }

    @Override
    public PersoonHisVolledigImpl leesBlob(final AdministratienummerAttribuut administratienummer) throws NietUniekeAnummerExceptie {
        final Integer technischId = vindTechnischIdVoorANummer(administratienummer);
        return haalPersoonHisVolledigUitCacheOfDatabase(technischId);
    }

    @Override
    public List<PersoonHisVolledigImpl> leesBlobs(final List<Integer> technischeIds) {
        final List<PersoonHisVolledigImpl> resultaat = new ArrayList<>();
        for (final Integer technischeId : technischeIds) {
            resultaat.add(haalPersoonHisVolledigUitCacheOfDatabase(technischeId));
        }
        return resultaat;
    }

    /**
     * Zoekt een technisch persoon id bij een bsn van een actieve persoon.
     *
     * @param burgerservicenummer   Het bsn.
     * @return De persoon id.
     * @throws NietUniekeBsnExceptie niet unieke bsn exceptie
     */
    private Integer vindTechnischIdVoorBsnActievePersoon(final BurgerservicenummerAttribuut burgerservicenummer) throws
        NietUniekeBsnExceptie
    {
        return hisPersTabelRepository.zoekIdBijBSNVoorActievePersoon(burgerservicenummer);
    }

    /**
     * Zoekt een technisch persoon id bij een bsn van een actieve persoon.
     *
     * @param burgerservicenummer   Het bsn.
     * @return De persoon id.
     * @throws NietUniekeBsnExceptie niet unieke bsn exceptie
     */
    private Integer vindTechnischIdVoorBsn(final BurgerservicenummerAttribuut burgerservicenummer) throws
        NietUniekeBsnExceptie
    {
        return hisPersTabelRepository.zoekIdBijBSN(burgerservicenummer);
    }

    /**
     * Zoekt een technisch persoon id bij een a-nummer.
     *
     * @param administratienummer Het a-nummer.
     * @return De persoon id.
     * @throws NietUniekeAnummerExceptie the niet unieke anummer exceptie
     */
    private Integer vindTechnischIdVoorANummer(final AdministratienummerAttribuut administratienummer) throws NietUniekeAnummerExceptie {
        return hisPersTabelRepository.zoekIdBijAnummer(administratienummer);
    }

    /**
     * Slaat een persoon op in de blob.
     *
     * @param persoon De persoon.
     */
    private void opslaanInPersoonCache(final PersoonHisVolledigImpl persoon) {
        if (null == persoon) {
            throw new IllegalArgumentException("PersoonHisVolledig is verplicht");
        }

        final PersoonCacheModel persoonCache;
        final PersoonCacheModel persoonCacheModel = schrijfPersoonCacheRepository.haalPersoonCacheOpUitMasterDatabase(persoon.getID());

        if (persoonCacheModel != null) {
            persoonCache = persoonCacheModel;
            vulPersoonCache(persoon, persoonCache);
            LOGGER.debug("Persoon cache voor persoon {} aangepast in de database.", persoon.getID());
        } else {
            persoonCache = new PersoonCacheModel(persoon.getID());
            vulPersoonCache(persoon, persoonCache);
            schrijfPersoonCacheRepository.opslaanNieuwePersoonCache(persoonCache);
            LOGGER.debug("Persoon cache voor persoon {} toegevoegd aan de database.", persoon.getID());
        }
    }

    /**
     * Vul het persoon cache object met (verplichte) waarden zoals de PersoonHisVolledigChecksum en PersoonHisVolledigGegevens.
     *
     * @param persoon      de persoon
     * @param persoonCache de persoon cache
     */
    private void vulPersoonCache(final PersoonHisVolledigImpl persoon, final PersoonCacheModel persoonCache) {
        vulAanMetVerantwoording(persoon);
        final byte[] gegevens = serializeObject(persoon);
        final String checksum = checksumBerekenaar.berekenChecksum(gegevens);

        persoonCache.getStandaard().setPersoonHistorieVolledigChecksum(new ChecksumAttribuut(checksum));
        persoonCache.getStandaard().setPersoonHistorieVolledigGegevens(new ByteaopslagAttribuut(gegevens));

        LOGGER.debug("Persoon cache '{}' heeft hash '{}'", persoon.getID(), checksum);
    }

    /**
     * Serializeert een {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} instantie.
     *
     * @param persoon de persoon instantie om te serializeren
     * @return de geserializeerde vorm van persoon
     */
    private byte[] serializeObject(final PersoonHisVolledig persoon) {
        try {
            return persoonHisVolledigSerializer.serialiseer((PersoonHisVolledigImpl) persoon);
        } catch (final SerialisatieExceptie e) {
            LOGGER.error("Kan persoon '" + persoon.getID() + "' niet serializeren.", e);
            throw e;
        }
    }

    /**
     * Deserializeert een byte[] naar {@link nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig} instantie.
     *
     * @param cache object met de byte[]
     * @return instantie van PersoonHisVolledig
     */
    private PersoonHisVolledigImpl deserializeObject(final PersoonCacheModel cache) {
        try {
            return persoonHisVolledigSerializer.deserialiseer(cache.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde());
        } catch (final SerialisatieExceptie e) {
            LOGGER.error("Kan cache data [{}] niet deserializeren: {}", cache.getPersoonId(), e);
            return null;
        }
    }

    /**
     * Haalt een persoon volledig object uit de cache of uit de database als geen cache bestaat.
     *
     * @param persoonId De technische sleutel van de persoon.
     * @return Het PersoonHisVolledig object.
     */
    private PersoonHisVolledigImpl haalPersoonHisVolledigUitCacheOfDatabase(final Integer persoonId) {
        final PersoonCacheModel cache = leesPersoonCacheRepository.haalPersoonCacheOpMetPersoonHisVolledigGegevens(persoonId);

        PersoonHisVolledigImpl persoonHisVolledig = null;
        if (cache != null) {
            persoonHisVolledig = haalPersoonHisVolledigUitCache(cache);
            LOGGER.debug("Persoon {} uit Cache gehaald.", persoonId);
        }

        if (persoonHisVolledig == null) {
            LOGGER.warn("Geen cache kunnen vinden/deserialiseren voor persoon met id {}. Cache wordt in-memory gemaakt", persoonId);
            persoonHisVolledig = maakInMemoryBlob(persoonId);
        }

        verrijkMetBetrokkenPersonenProxies(persoonHisVolledig);
        verrijkMetBetrokkenPersonenInOnderzoekProxies(persoonHisVolledig);
        verrijkMetAfnemerindicatieProxies(persoonHisVolledig);

        LOGGER.debug("Persoon {} uit de Database opgehaald.", persoonId);

        return persoonHisVolledig;
    }

    /**
     * Maakt een tijdelijke in-memory blob voor een persoon. Dit doen we als er geen blob in de database werd aan- getroffen of als deze corrupt is. We
     * maken een in-memory blob zodat de objecten altijd hetzelfde reageren, of we nu een 'echte' blob hebben of een 'in-memory' blob.
     *
     * @param persoonId Het id van de persoon.
     * @return De PersoonHisVolledig vanuit de in-memory blob.
     */
    private PersoonHisVolledigImpl maakInMemoryBlob(final Integer persoonId) {
        final PersoonHisVolledigImpl persoonHisVolledig = hisPersTabelRepository.leesGenormalizeerdModelVoorInMemoryBlob(persoonId);

        vulAanMetVerantwoording(persoonHisVolledig);

        final byte[] data = persoonHisVolledigSerializer.serialiseer(persoonHisVolledig);

        return persoonHisVolledigSerializer.deserialiseer(data);
    }

    /**
     * Vult een persoonHisVolledig aan met verantwoordingsinformatie (administratieve handelingenen).
     *
     * @param persoonHisVolledig persoon om aan te vullen.
     */
    private void vulAanMetVerantwoording(final PersoonHisVolledigImpl persoonHisVolledig) {
        final List<AdministratieveHandelingHisVolledigImpl> administratieveHandelingHisVolledigs =
            schrijfPersoonCacheRepository.haalVerantwoordingOp(persoonHisVolledig.getID());

        if (HibernateProxy.class.isInstance(persoonHisVolledig)) {
            final PersoonHisVolledigImpl werkelijkObject =
                (PersoonHisVolledigImpl) HibernateProxy.class.cast(persoonHisVolledig).getHibernateLazyInitializer().getImplementation();

            LOGGER.debug("Verantwoording voor persoon met id: {} wordt ingeladen via hibernate-proxy.", persoonHisVolledig.getID());

            werkelijkObject.vulAanMetAdministratieveHandelingen(administratieveHandelingHisVolledigs);
        } else {
            persoonHisVolledig.vulAanMetAdministratieveHandelingen(administratieveHandelingHisVolledigs);
        }
    }

    /**
     * Haalt de persoonHisVolledig uit de cache.
     *
     * @param cache De cache.
     * @return Het PersoonHisVolledig object.
     */
    private PersoonHisVolledigImpl haalPersoonHisVolledigUitCache(final PersoonCacheModel cache) {
        if (cache.getStandaard().getPersoonHistorieVolledigChecksum() == null) {
            return null;
        }

        PersoonHisVolledigImpl result = null;
        final String hash = checksumBerekenaar.berekenChecksum(cache.getStandaard().getPersoonHistorieVolledigGegevens().getWaarde());

        if (hash.equals(cache.getStandaard().getPersoonHistorieVolledigChecksum().getWaarde())) {
            result = deserializeObject(cache);
        } else {
            LOGGER.error("Persoon blob [{}] heeft niet de correcte checksum: '{}' ipv '{}'", cache.getPersoonId(), hash,
                cache.getStandaard().getPersoonHistorieVolledigChecksum().getWaarde());
        }
        return result;
    }

    /**
     * Verrijkt de betrokken personen met proxies, zodat deze via de luie lader geladen kunnen worden.
     *
     * @param persoonHisVolledig De persoon his volledig.
     */
    private void verrijkMetBetrokkenPersonenProxies(final PersoonHisVolledigImpl persoonHisVolledig) {
        for (final BetrokkenheidHisVolledigImpl betrokkenheid : persoonHisVolledig.getBetrokkenheden()) {
            for (final BetrokkenheidHisVolledigImpl betrokkeneBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                if (betrokkeneBetrokkenheid.getPersoon() != null) {
                    final PersoonHisVolledigImpl betrokkeneMetAlleenIdGevuld = betrokkeneBetrokkenheid.getPersoon();

                    if (betrokkeneMetAlleenIdGevuld.getID().equals(persoonHisVolledig.getID())) {
                        // referentie naar dezelfde persoon als we aan het verrijken zijn
                        betrokkeneBetrokkenheid.setPersoon(persoonHisVolledig);
                    } else {
                        // proxy naar een andere persoon
                        final PersoonHisVolledigImpl persoonProxy = maakProxyVoorPersoon(betrokkeneMetAlleenIdGevuld);
                        betrokkeneBetrokkenheid.setPersoon(persoonProxy);
                    }
                }
            }
        }
    }

    /**
     * Verrijkt de betrokken personen in onderzoeken met proxies, zodat deze via de luie lader geladen kunnen worden.
     *
     * @param persoonHisVolledig De persoon his volledig.
     */
    private void verrijkMetBetrokkenPersonenInOnderzoekProxies(final PersoonHisVolledigImpl persoonHisVolledig) {
        for (final PersoonOnderzoekHisVolledigImpl onderzoekVanuitPersoon : persoonHisVolledig.getOnderzoeken()) {
            for (final PersoonOnderzoekHisVolledigImpl persoonOnderzoek : onderzoekVanuitPersoon.getOnderzoek().getPersonenInOnderzoek()) {
                final PersoonHisVolledigImpl persoonMetAlleenIdGevuld = persoonOnderzoek.getPersoon();

                if (persoonMetAlleenIdGevuld.getID().equals(persoonHisVolledig.getID())) {
                    // referentie naar dezelfde persoon als we aan het verrijken zijn
                    persoonOnderzoek.setPersoon(persoonHisVolledig);
                } else {
                    // proxy naar een andere persoon
                    final PersoonHisVolledigImpl persoonProxy = maakProxyVoorPersoon(persoonMetAlleenIdGevuld);
                    persoonOnderzoek.setPersoon(persoonProxy);
                }
            }
        }
    }

    /**
     * Verrijkt de persoon met een proxy voor de set afnemerindicaties, zodat deze via de luie lader geladen kunnen worden.
     *
     * @param persoonHisVolledig De persoon his volledig
     */
    private void verrijkMetAfnemerindicatieProxies(final PersoonHisVolledigImpl persoonHisVolledig) {
        final AfnemerindicatiesHisVolledigImplLuieLader afnemerindicatiesLuieLader =
            new AfnemerindicatiesHisVolledigImplLuieLader(afnemerIndicatieBlobifierService, persoonHisVolledig.getID());
        final Set<PersoonAfnemerindicatieHisVolledigImpl> afnemerindicaties = afnemerindicatiesLuieLader.getProxy();

        persoonHisVolledig.setAfnemerindicaties(afnemerindicaties);
    }

    /**
     * Maakt een proxy object voor een persoon his volledig waarmee de persoon lazy uit de database kan worden geladen.
     *
     * @param betrokkeneMetAlleenIdGevuld De persoon his volledig.
     * @return De persoon his volledig met proxies voor de betrokkenen.
     */
    private PersoonHisVolledigImpl maakProxyVoorPersoon(final PersoonHisVolledigImpl betrokkeneMetAlleenIdGevuld) {
        final PersoonHisVolledigImplLuieLader persoonHisVolledigImplLuieLader =
            new PersoonHisVolledigImplLuieLader(this, betrokkeneMetAlleenIdGevuld.getID());

        return persoonHisVolledigImplLuieLader.getProxy();
    }

    /**
     * Haalt een persoon uit de database op basis van de id.
     *
     * @param technischId De persoon id.
     * @return De persoon.
     */
    protected PersoonHisVolledigImpl haalPersoonUitDatabase(final Integer technischId) {
        return hisPersTabelRepository.leesGenormalizeerdModelVoorNieuweBlob(technischId);
    }
}
