/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.PersoonVolledigRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepMaterieleHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.HistoriePersoonAdresRepository;
import nl.bzk.brp.dataaccess.serializatie.PersoonVolledigSerializer;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonVolledigCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * JPA implementatie van {@link PersoonVolledigRepository}.
 */
@Repository
public class PersoonVolledigJpaRepository implements PersoonVolledigRepository {

    private static final boolean INCLUSIEF_FORMELE_HISTORIE = false;

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonVolledigJpaRepository.class);

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    @Named("jacksonJsonSerializer")
    private PersoonVolledigSerializer serializer;

    @Inject
    private PersoonRepository persoonRepository;

    @Inject
    @Named("historiePersoonGeslachtsaanduidingRepository")
    private GroepMaterieleHistorieRepository hisPersoonGeslachtsaanduidingRepository;

    @Inject
    @Named("historiePersoonGeslachtsnaamcomponentRepository")
    private GroepMaterieleHistorieRepository hisPersoonGeslachtsnaamcomponentRepository;

    @Inject
    private HistoriePersoonAdresRepository hisPersoonAdresRepository;

    @Inject
    @Named("historiePersoonIdentificatienummersRepository")
    private GroepMaterieleHistorieRepository hisPersoonIdentificatienummersRepository;

    @Override
    public PersoonVolledig haalPersoonOp(final Integer id) {
        PersoonVolledigCache cache = em.find(PersoonVolledigCache.class, id);

        if (cache != null) {
            return deserializeObject(cache);
        } else {
            PersoonVolledig persoonVolledig = leesGenormalizeerdModel(id);
            // TODO: slaan we de blob meteen op?
            opslaanPersoon(persoonVolledig);
            return persoonVolledig;
        }
    }

    /**
     * Deserializeert een byte[] naar {@link PersoonVolledig} instantie.
     *
     * @param cache object met de byte[]
     * @return instantie van PersoonVolledig
     */
    protected PersoonVolledig deserializeObject(final PersoonVolledigCache cache) {
        try {
            return serializer.deserializeer(cache.getData());
        } catch (IOException e) {
            LOGGER.error("Kan cache data [{}] niet deserializeren: {}", cache.getId(),  e);
            return null;
        }
    }

    /**
     * Leest een {@link nl.bzk.brp.model.operationeel.kern.PersoonVolledig} uit de genormalizeerde opslag.
     *
     * @param id de identiteit van de instantie
     * @return instantie van PersoonVolledig
     */
    private PersoonVolledig leesGenormalizeerdModel(final Integer id) {
        // TODO complete implementation
        PersoonVolledig persoon = new PersoonVolledig(id);

        PersoonModel persoonModel = persoonRepository.haalPersoonMetAdres(id);

        if (null == persoonModel) {
            //Bijbehorende persoon niet gevonden
            return null;
        }
        persoon.setSoort(persoonModel.getSoort());
        persoon.setGeslachtsaanduiding(
                hisPersoonGeslachtsaanduidingRepository.haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));
        persoon.setIdentificatienummers(
                hisPersoonIdentificatienummersRepository.haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));

        for (PersoonAdresModel adres : persoonModel.getAdressen()) {
            persoon.getAdressen().addAll(
                    hisPersoonAdresRepository.haalopHistorie(adres, INCLUSIEF_FORMELE_HISTORIE));
        }

        for (PersoonGeslachtsnaamcomponentModel component : persoonModel.getGeslachtsnaamcomponenten()) {
            List<HisPersoonGeslachtsnaamcomponentModel> hisModel =
                    hisPersoonGeslachtsnaamcomponentRepository.haalopHistorie(component, INCLUSIEF_FORMELE_HISTORIE);
            persoon.getGeslachtsnaamcomponent().addAll(hisModel);
        }


        return persoon;
    }

    @Override
    public PersoonVolledig haalPersoonOp(final PersoonModel persoonModel) {
        return this.haalPersoonOp(persoonModel.getID());
    }

    @Override
    public void opslaanPersoon(final PersoonVolledig persoon) {
        if (null == persoon) {
            throw new IllegalArgumentException("PersoonVolledig is verplicht");
        }

        PersoonVolledigCache cache = em.find(PersoonVolledigCache.class, persoon.getId());

        boolean exists = (cache != null);

        if (!exists) {
            cache = new PersoonVolledigCache();

            PersoonModel persoonModel = em.find(PersoonModel.class, persoon.getId());
            cache.setPersoon(persoonModel);
        }

        cache.setData(serializeObject(persoon));

        if (!exists) {
            em.persist(cache);
        }
    }

    /**
     * Serializeert een {@link PersoonVolledig} instantie.
     *
     * @param persoon de persoon instantie om te serializeren
     * @return de geserializeerde vorm van persoon
     */
    protected byte[] serializeObject(final PersoonVolledig persoon) {
        try {
            return serializer.serializeer(persoon);
        } catch (IOException e) {
            LOGGER.error("Kan persoon [{}] niet serializeren: {}", persoon.getId(), e);
            return null;
        }
    }
}
