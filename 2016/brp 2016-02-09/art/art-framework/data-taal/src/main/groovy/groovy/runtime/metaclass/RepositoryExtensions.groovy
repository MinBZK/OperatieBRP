package groovy.runtime.metaclass
import javax.persistence.NoResultException
import nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie
import nl.bzk.brp.dataaccess.repository.jpa.PersoonHisVolledigJpaRepository
import nl.bzk.brp.dataaccess.repository.jpa.ReferentieDataJpaRepository
import nl.bzk.brp.model.algemeen.attribuuttype.kern.*
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Gemeente
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
/**
 * Groovy Extension Module voor JpaRepository klasses in dataaccess module.
 */
class RepositoryExtensions {

    /**
     * Zoek een gemeente in de database.
     *
     * @param self
     * @param gemeenteNaam de naam van de gemeente
     * @return Gemeente instantie
     */
    static Gemeente vindGemeenteOpNaam(ReferentieDataJpaRepository self, String gemeenteNaam) {
        final String sql = 'SELECT gemeente FROM Gemeente gemeente WHERE gemeente.naam = :naam'
        try {
            return (Gemeente) self.maakQuery(sql).setParameter('naam', new NaamEnumeratiewaardeAttribuut(gemeenteNaam)).getSingleResult()
        } catch (NoResultException e) {
            self.LOGGER.info "Onbekende gemeentenaam '{}' niet gevonden.", gemeenteNaam
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.GEMEENTECODE, gemeenteNaam, e)
        }
    }

    /**
     * Zoek een gemeente in de database.
     *
     * @param self
     * @param code de gemeente code waarop wordt gezocht
     * @return Gemeente instantie
     */
    static Gemeente vindGemeenteOpCode(ReferentieDataJpaRepository self, Short code) {
        return self.vindGemeenteOpCode(new GemeenteCodeAttribuut(code))
    }

    /**
     * Zoek een nationaliteit in de database.
     *
     * @param self
     * @param nationaliteitNaam de naam van de nationaliteit
     * @return Nationaliteit instantie
     */
    static Nationaliteit vindNationaliteitOpNaam(ReferentieDataJpaRepository self, String nationaliteitNaam) {
        final String sql = 'SELECT nat FROM Nationaliteit nat WHERE nat.naam = :naam'
        try {
            return (Nationaliteit) self.maakQuery(sql).setParameter('naam', new NaamEnumeratiewaardeAttribuut(nationaliteitNaam)).getSingleResult()
        } catch (NoResultException e) {
            self.LOGGER.info "Onbekende nationaliteitnaam '{}' niet gevonden.", nationaliteitNaam
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.NATIONALITITEITCODE, nationaliteitNaam, e)
        }
    }

    /**
     * Zoek een nationaliteit in de database.
     *
     * @param self
     * @param code de nationaliteitcode
     * @return Nationaliteit instantie
     */
    static Nationaliteit vindNationaliteitOpCode(ReferentieDataJpaRepository self, Short code) {
        return self.vindNationaliteitOpCode(new NationaliteitcodeAttribuut(code))
    }

    /**
     * Zoek een partij in de database.
     *
     * @param self
     * @param code de partijcode
     * @return Partij instantie
     */
    static Partij vindPartijOpCode(ReferentieDataJpaRepository self, Integer code) {
        return self.vindPartijOpCode(new PartijCodeAttribuut(code))
    }

    /**
     * Zoek een partij in de database.
     *
     * @param self
     * @param naam de partij naam
     * @return Partij instantie
     */
    static Partij vindPartijOpNaam(ReferentieDataJpaRepository self, String naam) {
        final String sql = 'SELECT partij FROM Partij partij WHERE partij.naam = :naam'
        try {
            return (Partij) self.maakQuery(sql).setParameter('naam', new NaamEnumeratiewaardeAttribuut(naam)).getSingleResult()
        } catch (NoResultException e) {
            self.LOGGER.info "Onbekende partijnaam '{}' niet gevonden.", naam
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.PARTIJCODE, naam, e)
        }
    }

    /**
     * Zoek een land in de database.
     *
     * @param self
     * @param naam de land naam
     * @return LandGebied instantie
     */
    static LandGebied vindLandOpNaam(ReferentieDataJpaRepository self, String naam) {
        final String sql = 'SELECT land FROM LandGebied land WHERE land.naam = :naam'
        try {
            return (LandGebied) self.maakQuery(sql).setParameter('naam', new NaamEnumeratiewaardeAttribuut(naam)).getSingleResult()
        } catch (NoResultException e) {
            self.LOGGER.info "Onbekend land '{}' niet gevonden.", naam
            throw new OnbekendeReferentieExceptie(OnbekendeReferentieExceptie.ReferentieVeld.LANDGEBIEDCODE, naam, e)
        }
    }

    /**
     * Zoek een persoon in de database.
     *
     * @param self
     * @param bsn de burgerservinummer van de persoon
     * @return PersoonHisVolledigImpl instantie
     */
    static PersoonHisVolledigImpl vindPersoonMetBsn(PersoonHisVolledigJpaRepository self, Integer bsn) {
        final String sql = 'SELECT persoon FROM PersoonHisVolledigImpl persoon, PersoonModel p WHERE p.id = persoon.id AND p.identificatienummers.burgerservicenummer = :bsn'

        try {
            return self.em.createQuery(sql, PersoonHisVolledigImpl).setParameter('bsn', new BurgerservicenummerAttribuut(bsn)).getSingleResult()
        } catch (NoResultException e) {
            throw new IllegalArgumentException("Persoon met BSN $bsn bestaat niet", e)
        }
    }
}
