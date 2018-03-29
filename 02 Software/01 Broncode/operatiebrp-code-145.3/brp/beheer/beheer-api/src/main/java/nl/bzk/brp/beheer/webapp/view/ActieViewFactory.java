/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.view;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.brp.beheer.webapp.repository.kern.PersoonRepository;
import nl.bzk.brp.beheer.webapp.view.blob.BlobMapper;
import nl.bzk.brp.beheer.webapp.view.blob.BlobViewObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Actie view factory.
 */
@Component
public final class ActieViewFactory {

    private final PersoonRepository persoonRepository;

    /**
     * Constructor.
     * @param persoonRepository persoon repository
     */
    @Inject
    public ActieViewFactory(final PersoonRepository persoonRepository) {
        this.persoonRepository = persoonRepository;
    }

    /**
     * Maakt een lijst view voor een actie.
     *
     * @param actie
     *            actie
     * @return lijst view
     */
    public ActieListView mapActieListView(final BRPActie actie) {
        return new ActieListView(actie);
    }

    /**
     * Maakt een detail view voor een actie.
     *
     * @param actie
     *            actie
     * @return detail view
     */
    public ActieDetailView mapActieDetailView(final BRPActie actie) {
        // Bepaal personen waarop de actie betrekking heeft (via de administratieve handeling)
        final Page<Persoon> personen =
                persoonRepository.findAll(
                    new ZoekPersonenSpecification(actie.getAdministratieveHandeling().getId()),
                    new PageRequest(0, Integer.MAX_VALUE));

        final BlobMapper blobMapper = new BlobMapper();

        // Alle personen (plus gerelateerden) inlezen
        for (final Persoon persoon : personen.getContent()) {
            // Persoon
            final PersoonBlob persoonBlob = Blobber.maakBlob(persoon);

            // Persoon - Verantwoording
            for (final BlobRoot verantwoordingBlob : persoonBlob.getVerantwoording()) {
                blobMapper.map(verantwoordingBlob.getRecordList());
            }
            // Persoon - Persoonsgegevens
            blobMapper.map(persoonBlob.getPersoonsgegevens().getRecordList());
        }

        blobMapper.maakHierarchieVoorInzienActie(actie.getId());

        final List<BlobViewObject> relaties = new ArrayList<>();
        relaties.addAll(blobMapper.getObjecten(Element.FAMILIERECHTELIJKEBETREKKING));
        relaties.addAll(blobMapper.getObjecten(Element.HUWELIJK));
        relaties.addAll(blobMapper.getObjecten(Element.GEREGISTREERDPARTNERSCHAP));

        return new ActieDetailView(actie, blobMapper.getObjecten(Element.PERSOON), relaties, blobMapper.getObjecten(Element.ONDERZOEK));
    }

    /**
     * Zoek personen query.
     */
    private static final class ZoekPersonenSpecification implements Specification<Persoon> {
        private final Long administratieveHandelingId;

        /**
         * Constructor.
         *
         * @param administratieveHandelingId
         *            administratieve handeling id
         */
        ZoekPersonenSpecification(final Long administratieveHandelingId) {
            this.administratieveHandelingId = administratieveHandelingId;
        }

        @Override
        public Predicate toPredicate(final Root<Persoon> root, final CriteriaQuery<?> query, final CriteriaBuilder cb) {
            return cb.equal(root.join("persoonAfgeleidAdministratiefHistorieSet").get("administratieveHandeling").get("id"), administratieveHandelingId);
        }

    }

}
