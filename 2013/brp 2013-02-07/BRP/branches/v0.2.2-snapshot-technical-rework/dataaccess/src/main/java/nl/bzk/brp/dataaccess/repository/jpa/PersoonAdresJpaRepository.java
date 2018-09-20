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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import nl.bzk.brp.dataaccess.repository.PersoonAdresRepository;
import nl.bzk.brp.dataaccess.repository.historie.PersoonAdresHistorieRepository;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.groep.logisch.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonAdresStandaardGroep;
import nl.bzk.brp.model.groep.operationeel.actueel.PersoonAdresStandaardGroepModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresStandaardHisModel;
import nl.bzk.brp.model.objecttype.logisch.PersoonAdres;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.FunctieAdres;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Plaats;
import org.apache.commons.collections.CollectionUtils;
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
    public PersoonAdresModel opslaanNieuwPersoonAdres(final PersoonAdresModel persoonAdres, final ActieModel actie,
        final Datum datumAanvangGeldigheid)
    {
        return pasAdresEnHistorieAan(persoonAdres, actie, datumAanvangGeldigheid, null);
    }

    @Override
    public void voerCorrectieAdresUit(final PersoonAdresModel adres, final ActieModel actie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        pasAdresEnHistorieAan(adres, actie, datumAanvangGeldigheid, datumEindeGeldigheid);
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

        SingularAttribute attrGegevens =
            em.getMetamodel().entity(PersoonAdresModel.class).getSingularAttribute("gegevens");
        SingularAttribute attrPlaats = em.getMetamodel().embeddable(
            PersoonAdresStandaardGroepModel.class).getSingularAttribute("woonplaats");

        final Join<PersoonAdresStandaardGroepModel, Plaats> plaats =
            root.join(attrGegevens, JoinType.LEFT).join(attrPlaats, JoinType.LEFT);
        Predicate p = cb.conjunction();

        // Woonplaats dient gelijk te zijn; als woonplaats leeg is, dan hoeft deze niet in de vergelijking
        // meegenomen te worden met een check op leeg (in tegenstelling tot overige attributen).
        // Indien de woonplaats wel ingevuld is, maar in de database leeg is, dan wordt dat ook als
        // hetzelfde gezien.
        if (gegevens.getWoonplaats() != null && gegevens.getWoonplaats().getCode() != null) {
            Predicate ofPred = cb.or(
                gegevensPath.get("woonplaats").isNull(),
                cb.equal(plaats.get("code"), gegevens.getWoonplaats().getCode())
            );
            p = cb.and(p, ofPred);
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
     * Past het adres aan wanneer het gaat om het huidige adres en werk de historie bij.
     *
     * @param persoonAdres het adres dat opgeslagen dient te worden.
     * @param datumAanvangGeldigheid Datum dat het nieuwe adres ingaat.
     * @param actie De actie die leidt tot deze adres wijziging.
     * @param datumEindeGeldigheid Datum einde geldigheid van het adres.
     * @return Opgeslagen PersoonAdres.
     */
    private PersoonAdresModel pasAdresEnHistorieAan(final PersoonAdresModel persoonAdres, final ActieModel actie,
        final Datum datumAanvangGeldigheid, final Datum datumEindeGeldigheid)
    {
        AbstractPersoonAdresStandaardGroep gegevens = persoonAdres.getGegevens();

        PersoonAdresModel huidigAdres =
            haalHuidigAdresOp(persoonAdres.getPersoon().getIdentificatienummers().getBurgerservicenummer());

        if (datumEindeGeldigheid == null) {
            // Als datumEindeGeldigheid is leeg, is dit een normale verhuizing, of een correctie die opgelost
            // had kunnen worden (technisch gezien) met een verhuizing.
            // De huidige adres wordt aangepast en deze kan dan naar de historie worden weggeschreven
            // Er wordt niet echt een adres toegevoegd in de A laag, maar huidige wordt gewijzigd.
            huidigAdres = wijzigHuidigAdres(persoonAdres, huidigAdres);
            gegevens = huidigAdres.getGegevens();
        }
        // in andere gevallen (datumEindeGeldigheid is niet null => is NOOIT in de toekomst)
        // wordt de A-Laag nooit aangepast en kunnen we de historie updaten met de record die origineel werd
        // aangeleverd.

        // Werk historie bij
        persoonAdresHistorieRepository.persisteerHistorie(huidigAdres, gegevens, actie,
            datumAanvangGeldigheid, datumEindeGeldigheid);
        return huidigAdres;
    }

    /**
     * Voor het toevoegen van een nieuw record dient het record uit de A-laag gewoon aangepast te worden naar de nieuwe
     * waardes (indien het een nieuw adres is) en dient er een nieuw record aan de C-laag te worden toegevoegd.
     *
     * @param nieuwAdres Het nieuwe adres.
     * @param huidigAdres Het huidige adre.
     * @return de aangepaste huidige adres
     */
    private PersoonAdresModel wijzigHuidigAdres(final PersoonAdresModel nieuwAdres,
        final PersoonAdresModel huidigAdres)
    {
        PersoonAdresModel resultaat;
        if (huidigAdres == null) {
            // TODO Check: dit stuk is waarschijnlijk tijdelijk, het is ingebouwd omdat in de test data sommige
            // personen geen adressen hebben.
            resultaat = new PersoonAdresModel(nieuwAdres, nieuwAdres.getPersoon());
            em.persist(resultaat);
        } else {
            huidigAdres.vervang(nieuwAdres);
            em.merge(huidigAdres);
            resultaat = huidigAdres;
        }
        return resultaat;
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
        final String sql =
            "SELECT adres FROM PersoonAdresModel adres "
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

    @Override
    public PersoonAdresModel vulaanAdresMetHistorie(final PersoonAdresModel adres, final boolean inclFormeleHistorie) {
        if (adres != null) {
            // TODO: bolie, moeten we niet eerst kijken of de flag statusHustorie = A, voordat we
            // de query uitvoeren?
            List<PersoonAdresStandaardHisModel> historie =
                persoonAdresHistorieRepository.haalopHistorie(adres, inclFormeleHistorie);
            if (CollectionUtils.isNotEmpty(historie)) {
                adres.setHistorie(historie);
            } else {
                adres.setHistorie(null);
            }
        }
        return adres;
    }

}
