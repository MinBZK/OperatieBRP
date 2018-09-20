/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.PersoonAdresStandaardGroepModel;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersoonAdresModel} class en standaard implementatie van
 * de {@link nl.bzk.brp.dataaccess.repository.PersoonAdresRepository} class.
 */
@Repository
public final class PersoonAdresJpaRepository implements PersoonAdresRepository {

    private static final String WOONPLAATSNAAM = "woonplaatsnaam";
    private static final String POSTCODE = "postcode";
    private static final String GEMEENTE = "gemeente";
    private static final String WAARDE = "waarde";
    private static final String CODE = "code";
    private static final String HUISNUMMER = "huisnummer";
    private static final String HUISLETTER = "huisletter";
    private static final String HUISNUMMERTOEVOEGING = "huisnummertoevoeging";

    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager em;

    /**
     * Checkt of attribuut niet null is en waarde niet null of leeg.
     *
     * @param att attribuut.
     * @return not blank
     */
    protected boolean isNotBlankAttribuut(final Attribuut<String> att) {
        return att != null && StringUtils.isNotBlank(att.getWaarde());
    }

    /**
     * Checkt of attribuut niet null is en waarde niet null is.
     *
     * @param att attribuut
     * @return not null
     */
    protected boolean isNotNullAttribuut(final Attribuut<?> att) {
        return att != null && att.heeftWaarde();
    }

    /** {@inheritDoc} */
    @Override
    public boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersoonAdresModel> cq = cb.createQuery(PersoonAdresModel.class);
        final Root<PersoonAdresModel> root = cq.from(PersoonAdresModel.class);
        final Path<PersoonAdresStandaardGroepModel> standaardPad = root.get("standaard");
        final PersoonAdresStandaardGroep standaardGroep = persoonAdres.getStandaard();

        Predicate p = cb.conjunction();

        // Woonplaats dient gelijk te zijn; als woonplaats leeg is, dan hoeft deze niet in de vergelijking
        // meegenomen te worden met een check op leeg (in tegenstelling tot overige attributen).
        // Indien de woonplaats wel ingevuld is, maar in de database leeg is, dan wordt dat ook als
        // hetzelfde gezien.
        if (isNotNullAttribuut(standaardGroep.getWoonplaatsnaam())) {
            final Predicate ofPred = cb.or(
                    standaardPad.get(WOONPLAATSNAAM).isNull(),
                    cb.equal(standaardPad.get(WOONPLAATSNAAM), standaardGroep.getWoonplaatsnaam())
            );
            p = cb.and(p, ofPred);
        }

        if (isNotBlankAttribuut(standaardGroep.getPostcode())) {
            p = cb.and(p, cb.equal(standaardPad.get(POSTCODE), standaardGroep.getPostcode()));
        } else {
            p = cb.and(p, cb.isNull(standaardPad.get(POSTCODE)));
        }

        if (isNotNullAttribuut(standaardGroep.getGemeente())
                && standaardGroep.getGemeente().getWaarde().getCode() != null)
        {
            p = cb.and(p, cb.equal(standaardPad.get(GEMEENTE).get(WAARDE).get(CODE),
                                   standaardGroep.getGemeente().getWaarde().getCode()));
        } else {
            p = cb.and(p, cb.isNull(standaardPad.get(GEMEENTE)));
        }

        if (isNotNullAttribuut(standaardGroep.getHuisnummer())) {
            p = cb.and(p, cb.equal(standaardPad.get(HUISNUMMER).get(WAARDE),
                                   standaardGroep.getHuisnummer().getWaarde()));
        } else {
            p = cb.and(p, cb.isNull(standaardPad.get(HUISNUMMER)));
        }

        if (isNotBlankAttribuut(standaardGroep.getHuisletter())) {
            p = cb.and(p, cb.equal(standaardPad.get(HUISLETTER).get(WAARDE),
                                   standaardGroep.getHuisletter().getWaarde()));
        } else {
            p = cb.and(p, cb.isNull(standaardPad.get(HUISLETTER)));
        }

        if (isNotBlankAttribuut(standaardGroep.getHuisnummertoevoeging())) {
            p = cb.and(p, cb.equal(standaardPad.get(HUISNUMMERTOEVOEGING), standaardGroep.getHuisnummertoevoeging()));
        } else {
            p = cb.and(p, cb.isNull(standaardPad.get(HUISNUMMERTOEVOEGING)));
        }

        cq.where(p);
        return !em.createQuery(cq).getResultList().isEmpty();
    }
}
