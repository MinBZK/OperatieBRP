package nl.bzk.brp.datataal.model

import nl.bzk.brp.dataaccess.repository.jpa.ReferentieDataJpaRepository
import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import org.apache.commons.lang.RandomStringUtils

/**
 * Abstractie rond attributen die nodig zijn voor het vastleggen van een gebeurtenis.
 * Indien deze in de DSL worden beschreven worden de waardes gezet, anders biedt deze
 * klasse "random" waardes voor attributen die nodig zijn bij het vastleggen van o.a.
 * de administratieve handeling.
 */
class GebeurtenisAttributen {
    def registratieDatum
    def aanvang
    def partij
    String toelichting
    SoortAdministratieveHandeling soortHandeling

    private ReferentieDataJpaRepository referentieData = SpringBeanProvider.getBean(ReferentieDataJpaRepository)

    /**
     * Default constructor, geeft willekeurige data benodigd voor het vastleggen van een gebeurtenis.
     */
    GebeurtenisAttributen() {
        final def datum = DatumAttribuut.vandaag()
        datum.voegDagToe(-10)
        registratieDatum = 0
        partij = randomPartij()
        aanvang = datum.waarde
        toelichting = RandomStringUtils.random(15, true, false)
        soortHandeling = SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING
    }

    /**
     * Geeft een partij.
     *
     * @return een partij
     * @throws nl.bzk.brp.dataaccess.exceptie.OnbekendeReferentieExceptie als de partij niet bestaat
     */
    Partij geefPartij() {
        final def result
        if (partij instanceof Number) {
            result = referentieData.vindPartijOpCode(new PartijCodeAttribuut(partij as Integer))
        } else if (partij instanceof String) {
            if (partij.isNumber()) {
                result = referentieData.vindPartijOpCode(new PartijCodeAttribuut(partij as Integer))
            } else {
                result = referentieData.vindPartijOpNaam(partij)
            }
        } else {
            result = null
        }

        result
    }

    /**
     * Geeft de datumAanvang
     *
     * @return
     */
    Integer geefDatumAanvang() {
        GegevensAfleider.maakDatum(aanvang)
    }

    Integer geefRegistratieDatum() {
        return registratieDatum;
    }

    /*
     * Willekeurige partij uit de lijst gemeentes: Alkmaar, Purmerend, Leiden
     */
    private int randomPartij() {
        def partijen = [36101, 43901, 54601] as int[]
        def idx = new Random().nextInt(partijen.size())

        partijen[idx]
    }
}
