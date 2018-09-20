/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa.blob;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.blob.BetrokkenheidHisOpslagplaats;
import nl.bzk.brp.dataaccess.repository.blob.PlBlobOpslagplaats;
import nl.bzk.brp.dataaccess.repository.historie.GroepFormeleHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.GroepHistorieRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.dataaccess.repository.jpa.serialization.BlobSerializer;
import nl.bzk.brp.model.blob.PlBlob;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOverlijdenHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonGeslachtsnaamcomponentModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;
import nl.bzk.brp.model.objecttype.operationeel.RelatieModel;
import nl.bzk.brp.model.objecttype.pojo.BetrokkenheidHisModel;
import nl.bzk.brp.model.objecttype.pojo.PersoonHisModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class PlBlobJpaOpslagplaats implements PlBlobOpslagplaats {

    private static final Boolean INCLUSIEF_FORMELE_HISTORIE = false;

    @PersistenceContext(unitName = "nl.bzk.brp")
    EntityManager em;

    @Inject
    PersoonRepository persoonRepository;

    @Inject
    @Named("jacksonBlobSerializer")
    BlobSerializer blobSerializer;

    @Inject
    @Named("persoonGeslachtsaanduidingHistorieRepository")
    GroepHistorieRepository persoonGeslachtsaanduidingHistorieRepository;

    @Inject
    @Named("persoonGeslachtsnaamcomponentHistorieRepository")
    GroepHistorieRepository persoonGeslachtsnaamcomponentHistorieRepository;

    @Inject
    @Named("persoonIdentificatienummersHistorieRepository")
    GroepHistorieRepository persoonIdentificatienummersHistorieRepository;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    @Inject
    @Named("persoonGeboorteHistorieRepository")
    private GroepFormeleHistorieRepository<PersoonModel> persoonGeboorteHistorieRepository;

    @Inject
    private BetrokkenheidHisOpslagplaats betrokkenheidHisOpslagplaats;

    @Inject
    @Named("relatieStandaardHistorieRepository")
    private GroepFormeleHistorieRepository<RelatieModel> relatieStandaardHistorieRepository;

    @Inject
    @Named("persoonOverlijdenHistorieRepository")
    private GroepFormeleHistorieRepository<PersoonModel> persoonOverlijdenHistorieRepository;


    /**
     * Leest een plblob van de blob-cache. Als deze niet bestaat wordt hij gemaakt (uit het genormalizeerde model
     * (Kern.Pers etc.)) en ook direct weggeschreven in de blob-cache.
     *
     * @param id technische sleutel van de blob
     * @return de gedeserializeerde {@link PersoonHisModel}
     */
    @Override
    @Transactional
    public PersoonHisModel leesPlBlob(final int id) throws IOException, ClassNotFoundException {
        PlBlob plBlob = em.find(PlBlob.class, id);

        if (plBlob != null) {
            return deserializeObject(plBlob);
        } else {
            PersoonHisModel persoonHisModel = createBlobFromModel(id);
            // TODO: slaan we de blob meteen op?
            schrijfPlBlob(persoonHisModel);
            return persoonHisModel;
        }
    }

    /*
     * Maakt een nieuwe PersoonHisModel instantie op basis van de toestand van de database (Kern.Pers, etc...)
     */
    private PersoonHisModel createBlobFromModel(final int id) {
        PersoonHisModel persoonHisModel = new PersoonHisModel(id);

        PersoonModel persoonModel = persoonRepository.haalPersoonMetAdres(id);

        if (null == persoonModel) {
            return null;   //Bijbehorende persoon niet gevonden
        }
        persoonHisModel.setSoort(persoonModel.getSoort());
        persoonHisModel.setGeslachtsaanduiding(
                persoonGeslachtsaanduidingHistorieRepository.haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));
        persoonHisModel.setAdressen(
                persoonAdresHistorieRepository.haalopHistorie(persoonModel.getWoonAdres(), INCLUSIEF_FORMELE_HISTORIE));

        persoonHisModel.setGeboorte(
                (List<PersoonGeboorteHisModel>) persoonGeboorteHistorieRepository
                        .haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));
        persoonHisModel.setOverlijden((List<PersoonOverlijdenHisModel>) persoonOverlijdenHistorieRepository
                .haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));

        for(PersoonGeslachtsnaamcomponentModel component : persoonModel.getGeslachtsnaamcomponenten()) {
            List<PersoonGeslachtsnaamcomponentHisModel> hisModel = persoonGeslachtsnaamcomponentHistorieRepository.haalopHistorie(component, INCLUSIEF_FORMELE_HISTORIE);
            persoonHisModel.getGeslachtsnaamcomponent().addAll(hisModel);
        }

        persoonHisModel.setIdentificatienummers(
                persoonIdentificatienummersHistorieRepository.haalopHistorie(persoonModel, INCLUSIEF_FORMELE_HISTORIE));

        for (BetrokkenheidModel betrokkenheid : persoonModel.getBetrokkenheden()) {
            BetrokkenheidHisModel hisBetrokkenheid = betrokkenheidHisOpslagplaats.leesHistorie(betrokkenheid);
            persoonHisModel.getBetrokkenheden().add(hisBetrokkenheid);
        }

        return persoonHisModel;
    }

    /**
     * Schrijft een {@link PersoonHisModel} als blob weg in de database.
     *
     * @param persoonHisModel het model om weg te schrijven
     * @throws IOException  d
     */
    @Override
    @Transactional
    public void schrijfPlBlob(final PersoonHisModel persoonHisModel) throws IOException {
        if (null == persoonHisModel) {
            throw new IllegalArgumentException("Persoon argument is verplicht!");
        }
        PlBlob plBlob = em.find(PlBlob.class, persoonHisModel.getId());

        boolean exists = (plBlob != null);

        if (!exists) {
            plBlob = new PlBlob();

            PersoonModel persoon = em.find(PersoonModel.class, persoonHisModel.getId());
            plBlob.setPersoon(persoon);
        }

        plBlob.setPl(serializeObject(persoonHisModel));

        if (!exists) {
            em.persist(plBlob);
        }

    }

    @Override
    public void setBlobSerializer(final BlobSerializer serializer) {
        this.blobSerializer = serializer;
    }

    protected byte[] serializeObject(final PersoonHisModel object) throws IOException {
        return blobSerializer.serializeObject(object);
    }

    protected PersoonHisModel deserializeObject(final PlBlob plBlob) throws IOException, ClassNotFoundException {
        return blobSerializer.deserializeObject(plBlob);
    }


}
