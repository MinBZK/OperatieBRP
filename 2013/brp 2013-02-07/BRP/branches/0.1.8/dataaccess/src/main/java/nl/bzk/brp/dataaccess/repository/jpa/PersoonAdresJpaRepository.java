/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link PersoonAdresModel} class en standaard implementatie van de
 * {@link nl.bzk.brp.dataaccess.repository.PersoonAdresRepository} class.
 */
@Repository
public class PersoonAdresJpaRepository implements PersoonAdresRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private PersoonAdresHistorieRepository persoonAdresHistorieRepository;

    /** {@inheritDoc} */
    @Override
    public PersoonAdresModel opslaanNieuwPersoonAdres(final PersoonAdresModel persoonAdres,
        final ActieModel actie, final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        // Het nieuwe record moet worden toegevoegd
        final PersoonAdresModel persoonAdresModel = voegNieuwPersoonAdresToe(persoonAdres, datumEindeGeldigheid);

        //Werk historie bij
        persoonAdresHistorieRepository.persisteerHistorie(persoonAdresModel, actie,
                datumAanvangGeldigheid, datumEindeGeldigheid);

        return persoonAdresModel;
    }

    /**
     * .
     *
     * @param att .
     * @return .
     */
    private boolean isNotBlankAttribuute(final AttribuutType<String> att) {
        if (att != null) {
            return StringUtils.isNotBlank(att.getWaarde());
        }
        return false;
    }

    /** {@inheritDoc} */
    @Override
    public boolean isIemandIngeschrevenOpAdres(final PersoonAdres persoonAdres) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersoonAdresModel> cq = cb.createQuery(PersoonAdresModel.class);
        final Root<PersoonAdresModel> root = cq.from(PersoonAdresModel.class);
        final Path<PersoonAdresStandaardGroepModel> gegevensPath = root.get("gegevens");
        final PersoonAdresStandaardGroep gegevens = persoonAdres.getGegevens();
        Predicate p = cb.conjunction();

        if (gegevens.getWoonplaats() != null && gegevens.getWoonplaats().getCode() != null) {
            p = cb.and(p, cb.equal(gegevensPath.get("woonplaats").get("code"), gegevens.getWoonplaats().getCode()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("woonplaats")));
        }

        if (isNotBlankAttribuute(gegevens.getPostcode())) {
            p = cb.and(p, cb.equal(gegevensPath.get("postcode"), gegevens.getPostcode()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("postcode")));
        }

        if (gegevens.getGemeente() != null && gegevens.getGemeente().getGemeentecode() != null) {
            p = cb.and(p, cb.equal(gegevensPath.get("gemeente").get("gemeentecode"),
                gegevens.getGemeente().getGemeentecode()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("gemeente")));
        }

        if (gegevens.getHuisnummer() != null && gegevens.getHuisnummer().getWaarde() != null) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisnummer"), gegevens.getHuisnummer()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("huisnummer")));
        }

        if (isNotBlankAttribuute(gegevens.getHuisletter())) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisletter"), gegevens.getHuisletter()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("huisletter")));
        }

        if (isNotBlankAttribuute(gegevens.getHuisnummertoevoeging())) {
            p = cb.and(p, cb.equal(gegevensPath.get("huisnummertoevoeging"), gegevens.getHuisnummertoevoeging()));
        } else {
            p = cb.and(p, cb.isNull(gegevensPath.get("huisnummertoevoeging")));
        }

        cq.where(p);
        return !em.createQuery(cq).getResultList().isEmpty();
    }

    @Override
    public PersoonAdresModel vindHuidigWoonAdresVoorPersoon(final Burgerservicenummer bsn) {
        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<PersoonAdresModel> cq = cb.createQuery(PersoonAdresModel.class);
        final Root<PersoonAdresModel> root = cq.from(PersoonAdresModel.class);

        Predicate predicate = cb.equal(root.get("persoon").get("identificatienummers").get("burgerservicenummer"), bsn);
        predicate = cb.and(predicate, cb.equal(root.get("gegevens").get("soort"), FunctieAdres.WOONADRES));
        cq.where(predicate);
        return em.createQuery(cq).getSingleResult();
    }

    /**
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes (indien het een nieuw adres is) en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param nieuwAdres het nieuwe adres.
     * @param datumEindeGeldigheid Datum wanneer het adres niet meer geldig is.
     * @return de aangepaste huidige adres
     */
    private PersoonAdresModel voegNieuwPersoonAdresToe(final PersoonAdresModel nieuwAdres,
        final Datum datumEindeGeldigheid)
    {
        // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
        PersoonAdresModel huidigAdres =
            haalHuidigAdresOp(nieuwAdres.getPersoon().getIdentificatienummers().getBurgerservicenummer());

        if (datumEindeGeldigheid == null) {
            if (huidigAdres == null) {
                // TODO Check: dit stuk is waarschijnlijk tijdelijk, het is ingebouwd omdat in de test data sommige
                // personen geen adressen hebben.
                huidigAdres = new PersoonAdresModel(nieuwAdres, nieuwAdres.getPersoon());
                em.persist(huidigAdres);
            } else {
                huidigAdres.vervang(nieuwAdres);
                em.merge(huidigAdres);
            }
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
    private PersoonAdresModel haalHuidigAdresOp(final Burgerservicenummer bsn) {
        final String sql = "SELECT adres FROM PersoonAdresModel adres "
            + "WHERE adres.persoon.identificatienummers.burgerservicenummer = :bsn";

        List<PersoonAdresModel> adressen =
            em.createQuery(sql, PersoonAdresModel.class).setParameter("bsn", bsn).getResultList();

        PersoonAdresModel resultaat;
        if (adressen.size() == 0) {
            resultaat = null;
        } else {
            // TODO Er dient een exceptie gegooid te worden indien er meer dan 1 adres wordt gevonden.
            resultaat = adressen.get(0);
        }
        return resultaat;
    }

}
