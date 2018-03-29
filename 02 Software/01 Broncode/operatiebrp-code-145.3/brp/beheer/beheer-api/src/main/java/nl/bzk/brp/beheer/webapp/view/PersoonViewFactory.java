/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonAfnemerindicatieRepository;
import nl.bzk.brp.beheer.webapp.view.blob.BlobMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Persoon view factory.
 */
@Component
public final class PersoonViewFactory {

    private final PersoonAfnemerindicatieRepository afnemerindicatieRepository;

    /**
     * Constructor.
     * @param afnemerindicatieRepository afnemerindicatie repository
     */
    @Inject
    public PersoonViewFactory(final PersoonAfnemerindicatieRepository afnemerindicatieRepository) {
        this.afnemerindicatieRepository = afnemerindicatieRepository;
    }

    /**
     * Maakt een lijst view voor een persoon.
     * @param persoon persoon
     * @return lijst view
     */
    public PersoonListView mapPersoonListView(final Persoon persoon) {
        return new PersoonListView(persoon);
    }

    /**
     * Maakt een detail view voor een persoon.
     * @param persoon persoon
     * @return detail view
     */
    public PersoonDetailView mapPersoonDetailView(final Persoon persoon) {

        final BlobMapper blobMapper = new BlobMapper();

        // Hoofdpersoon - Blob
        final PersoonBlob persoonBlob = Blobber.maakBlob(persoon);

        // Hoofdpersoon - Verantwoording
        for (final BlobRoot verantwoordingBlob : persoonBlob.getVerantwoording()) {
            blobMapper.map(verantwoordingBlob.getRecordList());
        }
        // Hoofdpersoon - Persoonsgegevens
        blobMapper.map(persoonBlob.getPersoonsgegevens().getRecordList());

        // Hoofdpersoon - Afnemersindicaties - Blob
        final Page<PersoonAfnemerindicatie> afnemerindicaties =
                afnemerindicatieRepository.findAll(new ZoekAfnemerindicatiesSpecification(persoon.getId()), new PageRequest(0, Integer.MAX_VALUE));
        final AfnemerindicatiesBlob afnemersindicatiesBlob = Blobber.maakBlob(afnemerindicaties.getContent());

        // Hoofdpersoon - Afnemersindicaties
        for (final BlobRoot afnemerindicatieBlob : afnemersindicatiesBlob.getAfnemerindicaties()) {
            blobMapper.map(afnemerindicatieBlob.getRecordList());
        }

        blobMapper.maakHierarchieVoorInzienPersoon();

        return new PersoonDetailView(
                blobMapper.getObject(Element.PERSOON),
                blobMapper.getObjecten(Element.ADMINISTRATIEVEHANDELING),
                blobMapper.getObjecten(Element.PERSOON_AFNEMERINDICATIE));
    }

    /**
     * Zoek afnemerindicaties query.
     */
    private static final class ZoekAfnemerindicatiesSpecification implements Specification<PersoonAfnemerindicatie> {
        private final Long persoonId;

        /**
         * Constructor.
         * @param persoonId persoon id
         */
        ZoekAfnemerindicatiesSpecification(final Long persoonId) {
            this.persoonId = persoonId;
        }

        @Override
        public Predicate toPredicate(final Root<PersoonAfnemerindicatie> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            return cb.equal(root.join("persoon").get("id"), persoonId);
        }

    }

}
