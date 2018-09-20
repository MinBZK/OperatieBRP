/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.gedeeld.FunctieAdres;
import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdres} class en standaard implementatie van de {@link PersoonAdresRepository} class.
 */
@Repository
public class PersoonAdresJpaRepository implements PersoonAdresRepository {

    @PersistenceContext
    private EntityManager                  em;

    @Inject
    private ReferentieDataRepository       referentieDataRepository;

    @Inject
    private PersoonRepository              persoonRepository;


    /** {@inheritDoc} */
    @Override
    public PersistentPersoonAdres opslaanNieuwPersoonAdres(final PersoonAdres persoonAdres,
                                                           final Integer datumAanvangGeldigheid,
                                                           final Integer datumEindeGeldigheid,
                                                           final Date tijdstipRegistratie)
    {
        // Het nieuwe record moet worden toegevoegd
        PersistentPersoonAdres nieuwAdres =
            voegNieuwPersoonAdresToe(persoonAdres, datumEindeGeldigheid);

        return nieuwAdres;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersistentPersoonAdres> cq = cb.createQuery(PersistentPersoonAdres.class);
        final Root<PersistentPersoonAdres> root = cq.from(PersistentPersoonAdres.class);
        Predicate p = cb.conjunction();

        if (persoonAdres.getWoonplaats() != null
            && StringUtils.isNotBlank(persoonAdres.getWoonplaats().getWoonplaatscode()))
        {
            p = cb.and(p, cb.equal(root.get("woonplaats").get("woonplaatscode"),
                    persoonAdres.getWoonplaats().getWoonplaatscode()));
        }

        if (StringUtils.isNotBlank(persoonAdres.getPostcode())) {
            p = cb.and(p, cb.equal(root.get("postcode"), persoonAdres.getPostcode()));
        }

        if (persoonAdres.getGemeente() != null
            && StringUtils.isNotBlank(persoonAdres.getGemeente().getGemeentecode()))
        {
            p = cb.and(p, cb.equal(root.get("gemeente").get("gemeentecode"),
                    persoonAdres.getGemeente().getGemeentecode()));
        }

        if (StringUtils.isNotBlank(persoonAdres.getHuisnummer())) {
            p = cb.and(p, cb.equal(root.get("huisnummer"), persoonAdres.getHuisnummer()));
        }

        if (StringUtils.isNotBlank(persoonAdres.getHuisletter())) {
            p = cb.and(p, cb.equal(root.get("huisletter"), persoonAdres.getHuisletter()));
        }

        if (StringUtils.isNotBlank(persoonAdres.getHuisnummertoevoeging())) {
            p = cb.and(p, cb.equal(root.get("huisnummertoevoeging"), persoonAdres.getHuisnummertoevoeging()));
        }

        cq.where(p);
        return !em.createQuery(cq).getResultList().isEmpty();
    }

    @Override
    public PersistentPersoonAdres vindHuidigWoonAdresVoorPersoon(final String bsn) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersistentPersoonAdres> cq = cb.createQuery(PersistentPersoonAdres.class);
        final Root<PersistentPersoonAdres> root = cq.from(PersistentPersoonAdres.class);

        Predicate predicate = cb.equal(root.get("persoon").get("burgerservicenummer"), bsn);
        predicate = cb.and(predicate, cb.equal(root.get("soort"), FunctieAdres.WOONADRES));
        cq.where(predicate);
        return em.createQuery(cq).getSingleResult();
    }

    /**
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes (indien het een nieuw adres is) en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param persoonAdres het nieuwe adres.
     * @param datumEindeGeldigheid Datum wanneer het adres niet meer geldig is.
     *
     * @return de aangepaste huidige adres
     */
    private PersistentPersoonAdres voegNieuwPersoonAdresToe(final PersoonAdres persoonAdres,
                                                            final Integer datumEindeGeldigheid)
    {
        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
        PersistentPersoonAdres huidigAdres =
            haalHuidigAdresOp(persoonAdres.getPersoon().getIdentificatienummers().getBurgerservicenummer());

        if (datumEindeGeldigheid == null) {
            huidigAdres.setSoort(persoonAdres.getSoort());
            // TODO: BLiem: set de RedenWijzingAdres, new Entity moet nog gemaakt worden.
            // huidigAdres.setxxxxxx(persoonAdres.getRedenWijziging());

            // TODO: BLiem: set de AangeverAdreshouding, new Entity moet nog gemaakt worden.
            // huidigAdres.setxxxxx(persoonAdres.getAangeverAdreshouding());
            if (persoonAdres.getGemeente() != null
                    && StringUtils.isNotBlank(persoonAdres.getGemeente().getGemeentecode()))
            {
                huidigAdres.setGemeente(referentieDataRepository.vindGemeenteOpCode(persoonAdres.getGemeente()
                        .getGemeentecode()));
            } else {
                huidigAdres.setGemeente(null);
            }

            if (persoonAdres.getLand() != null
                    && StringUtils.isNotBlank(persoonAdres.getLand().getLandcode()))
            {
                huidigAdres.setLand(referentieDataRepository.vindLandOpCode(persoonAdres.getLand().getLandcode()));
            } else {
                huidigAdres.setLand(null);
            }

            huidigAdres.setDatumAanvangAdreshouding(persoonAdres.getDatumAanvangAdreshouding());
            huidigAdres.setNaamOpenbareRuimte(persoonAdres.getNaamOpenbareRuimte());
            huidigAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getAfgekorteNaamOpenbareRuimte());
            huidigAdres.setHuisnummer(persoonAdres.getHuisnummer());
            huidigAdres.setHuisletter(persoonAdres.getHuisletter());
            huidigAdres.setHuisnummertoevoeging(persoonAdres.getHuisnummertoevoeging());
            if (persoonAdres.getWoonplaats() != null
                && StringUtils.isNotBlank(persoonAdres.getWoonplaats().getWoonplaatscode()))
            {
                huidigAdres.setWoonplaats(
                        referentieDataRepository.findWoonplaatsOpCode(persoonAdres.getWoonplaats().getWoonplaatscode())
                );
            } else {
                huidigAdres.setWoonplaats(null);
            }
            huidigAdres.setPostcode(persoonAdres.getPostcode());
            huidigAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
            huidigAdres.setIdentificatiecodeNummeraanduiding(persoonAdres.getIdentificatiecodeNummeraanduiding());
            huidigAdres.setRedenWijziging(persoonAdres.getRedenWijziging());
            huidigAdres.setAangeverAdreshouding(persoonAdres.getAangeverAdreshouding());

            em.merge(huidigAdres);
        }

        return huidigAdres;
    }

    /**
     * Haalt het huidige adres op voor de opgegeven bsn. Indien er meerdere adressen worden gevonden, wordt een
     * exceptie gegooid. Het kan overigens ook zijn dat er geen huidig adres wordt gevonden, dan wordt er een nieuw
     * adres aangemaakt (conform operationeel model) en deze wordt dan geretourneerd.
     *
     * @param bsn het burgerservicenummer waarvoor het huidige adres moet worden opgehaald.
     * @return het huidige adres, of een leeg adres indien er geen adres aanwezig is.
     */
    private PersistentPersoonAdres haalHuidigAdresOp(final String bsn) {
        final String sql =
            "SELECT adres FROM PersistentPersoonAdres adres WHERE adres.persoon.burgerservicenummer = :bsn";

        List<PersistentPersoonAdres> adressen =
                em.createQuery(sql, PersistentPersoonAdres.class).setParameter("bsn", bsn).getResultList();

        PersistentPersoonAdres resultaat;
        if (adressen.size() == 0) {
            PersistentPersoon persoon = persoonRepository.findByBurgerservicenummer(bsn);

            PersistentPersoonAdres adres = new PersistentPersoonAdres();
            adres.setPersoon(persoon);
            em.persist(adres);

            resultaat = adres;
        } else {
            // TODO Er dient een exceptie gegooid te worden indien er meer dan 1 adres wordt gevonden.
            resultaat = adressen.get(0);
        }
        return resultaat;
    }

}
