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

import nl.bzk.brp.dataaccess.repository.PersoonAdresMdlRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.basis.AbstractAttribuutType;
import nl.bzk.brp.model.groep.impl.usr.PersoonAdresStandaardGroepMdl;
import nl.bzk.brp.model.groep.interfaces.usr.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.objecttype.impl.usr.PersoonAdresMdl;
import nl.bzk.brp.model.objecttype.interfaces.usr.PersoonAdres;
import nl.bzk.brp.model.objecttype.statisch.FunctieAdres;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdresMdl} class en standaard implementatie van de
 * {@link PersoonAdresMdlRepository} class.
 */
@Repository
public class PersoonAdresMdlJpaRepository implements PersoonAdresMdlRepository {

    @PersistenceContext
    private EntityManager                  em;

//    @Inject
//    private ReferentieDataRepository       referentieDataRepository;
//
//    @Inject
//    private PersoonMdlRepository              persoonMdlRepository;
//


//    /** {@inheritDoc} */
//    @Override
//    public PersistentPersoonAdres opslaanNieuwPersoonAdres(final PersoonAdres persoonAdres,
//                                                           final Integer datumAanvangGeldigheid,
//                                                           final Integer datumEindeGeldigheid,
//                                                           final Date tijdstipRegistratie)
//    {
//        // Het nieuwe record moet worden toegevoegd
//        PersistentPersoonAdres nieuwAdres =
//            voegNieuwPersoonAdresToe(persoonAdres, datumEindeGeldigheid);
//
//        return nieuwAdres;
//    }

    /**
     * .
     * @param att .
     * @return .
     */
    private boolean isNotBlankAttribuute(final AbstractAttribuutType<String> att) {
        if (att != null) {
            return StringUtils.isNotBlank(att.getWaarde());
        }
        return false;
    }


    /** {@inheritDoc} */
    @Override
    public boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersoonAdresMdl> cq = cb.createQuery(PersoonAdresMdl.class);
        final Root<PersoonAdresMdl> root = cq.from(PersoonAdresMdl.class);
        final Path<PersoonAdresStandaardGroepMdl> gegevensPath = root.get("gegevens");
        final PersoonAdresStandaardGroep gegevens = persoonAdres.getGegevens();
        Predicate p = cb.conjunction();

        if (gegevens.getWoonplaats() != null
            && StringUtils.isNotBlank(gegevens.getWoonplaats().getCode().getWaarde()))
        {
            p = cb.and(p, cb.equal(gegevensPath.get("woonplaats").get("code"),
                    gegevens.getWoonplaats().getCode()));
        }

        if (isNotBlankAttribuute(gegevens.getPostcode())) {
            p = cb.and(p, cb.equal(gegevensPath.get("postcode"), gegevens.getPostcode()));
        }

        if (gegevens.getGemeente() != null
            && isNotBlankAttribuute(gegevens.getGemeente().getGemeenteCode()))
        {
            p = cb.and(p, cb.equal(gegevensPath.get("gemeente").get("gemeenteCode"),
                    gegevens.getGemeente().getGemeenteCode()));
        }

        if (isNotBlankAttribuute(gegevens.getHuisnummer())) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisnummer"), gegevens.getHuisnummer()));
        }

        if (isNotBlankAttribuute(gegevens.getHuisletter())) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisletter"), gegevens.getHuisletter()));
        }

        if (isNotBlankAttribuute(gegevens.getHuisnummertoevoeging())) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisnummertoevoeging"),
                    gegevens.getHuisnummertoevoeging()));
        }

        cq.where(p);
        return !em.createQuery(cq).getResultList().isEmpty();
    }

    @Override
    public PersoonAdresMdl vindHuidigWoonAdresVoorPersoon(final Burgerservicenummer bsn) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersoonAdresMdl> cq = cb.createQuery(PersoonAdresMdl.class);
        final Root<PersoonAdresMdl> root = cq.from(PersoonAdresMdl.class);

        Predicate predicate = cb.equal(root.get("persoon").get("identificatieNummers").get("burgerServiceNummer"), bsn);
        predicate = cb.and(predicate, cb.equal(root.get("gegevens").get("soort"), FunctieAdres.WOONADRES));
        cq.where(predicate);
        return em.createQuery(cq).getSingleResult();
    }

//    /**
//     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
//     * waardes (indien het een nieuw adres is) en dient er een nieuw record aan de C-laag te worden toegevoegd.
//     *
//     * @param persoonAdres het nieuwe adres.
//     * @param datumEindeGeldigheid Datum wanneer het adres niet meer geldig is.
//     *
//     * @return de aangepaste huidige adres
//     */
//    private PersistentPersoonAdres voegNieuwPersoonAdresToe(final PersoonAdres persoonAdres,
//                                                            final Datum datumEindeGeldigheid)
//    {
//        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
//        PersoonAdresMdl huidigAdres =
//            haalHuidigAdresOp(persoonAdres.getPersoon().getIdentificatieNummers().getBurgerServiceNummer());
//
//        if (datumEindeGeldigheid == null) {
//            huidigAdres.setSoort(gegevens.getSoort());
//            // TODO: BLiem: set de RedenWijzingAdres, new Entity moet nog gemaakt worden.
//            // huidigAdres.setxxxxxx(persoonAdres.getRedenWijziging());
//
//            // TODO: BLiem: set de AangeverAdreshouding, new Entity moet nog gemaakt worden.
//            // huidigAdres.setxxxxx(persoonAdres.getAangeverAdreshouding());
//            if (persoonAdres.getGemeente() != null
//                    && StringUtils.isNotBlank(persoonAdres.getGemeente().getGemeentecode()))
//            {
//                huidigAdres.setGemeente(referentieDataRepository.vindGemeenteOpCode(persoonAdres.getGemeente()
//                        .getGemeentecode()));
//            } else {
//                huidigAdres.setGemeente(null);
//            }
//
//            if (persoonAdres.getLand() != null
//                    && StringUtils.isNotBlank(persoonAdres.getLand().getLandcode()))
//            {
//                huidigAdres.setLand(referentieDataRepository.vindLandOpCode(persoonAdres.getLand().getLandcode()));
//            } else {
//                huidigAdres.setLand(null);
//            }
//
//            huidigAdres.setDatumAanvangAdreshouding(persoonAdres.getDatumAanvangAdreshouding());
//            huidigAdres.setNaamOpenbareRuimte(persoonAdres.getNaamOpenbareRuimte());
//            huidigAdres.setAfgekorteNaamOpenbareRuimte(persoonAdres.getAfgekorteNaamOpenbareRuimte());
//            huidigAdres.setHuisnummer(persoonAdres.getHuisnummer());
//            huidigAdres.setHuisletter(persoonAdres.getHuisletter());
//            huidigAdres.setHuisnummertoevoeging(persoonAdres.getHuisnummertoevoeging());
//            if (persoonAdres.getWoonplaats() != null
//                && StringUtils.isNotBlank(persoonAdres.getWoonplaats().getWoonplaatscode()))
//            {
//                huidigAdres.setWoonplaats(
//                        referentieDataRepository.findWoonplaatsOpCode(persoonAdres.getWoonplaats().getWoonplaatscode())
//                );
//            } else {
//                huidigAdres.setWoonplaats(null);
//            }
//            huidigAdres.setPostcode(persoonAdres.getPostcode());
//            huidigAdres.setAdresseerbaarObject(persoonAdres.getAdresseerbaarObject());
//            huidigAdres.setIdentificatiecodeNummeraanduiding(persoonAdres.getIdentificatiecodeNummeraanduiding());
//            huidigAdres.setRedenWijziging(persoonAdres.getRedenWijziging());
//            huidigAdres.setAangeverAdreshouding(persoonAdres.getAangeverAdreshouding());
//
//            em.merge(huidigAdres);
//        }
//
//        return huidigAdres;
//    }

//    /**
//     * Haalt het huidige adres op voor de opgegeven bsn. Indien er meerdere adressen worden gevonden, wordt een
//     * exceptie gegooid. Het kan overigens ook zijn dat er geen huidig adres wordt gevonden, dan wordt er een nieuw
//     * adres aangemaakt (conform operationeel model) en deze wordt dan geretourneerd.
//     *
//     * @param bsn het burgerservicenummer waarvoor het huidige adres moet worden opgehaald.
//     * @return het huidige adres, of een leeg adres indien er geen adres aanwezig is.
//     */
//    private PersistentPersoonAdres haalHuidigAdresOp(final String bsn) {
//        final String sql =
//            "SELECT adres FROM PersistentPersoonAdres adres WHERE adres.persoon.burgerservicenummer = :bsn";
//
//        List<PersistentPersoonAdres> adressen =
//                em.createQuery(sql, PersistentPersoonAdres.class).setParameter("bsn", bsn).getResultList();
//
//        PersistentPersoonAdres resultaat;
//        if (adressen.size() == 0) {
//            PersistentPersoon persoon = persoonMdlRepository.findByBurgerservicenummer(bsn);
//
//            PersistentPersoonAdres adres = new PersistentPersoonAdres();
//            adres.setPersoon(persoon);
//            em.persist(adres);
//
//            resultaat = adres;
//        } else {
//            // TODO Er dient een exceptie gegooid te worden indien er meer dan 1 adres wordt gevonden.
//            resultaat = adressen.get(0);
//        }
//        return resultaat;
//    }

}
