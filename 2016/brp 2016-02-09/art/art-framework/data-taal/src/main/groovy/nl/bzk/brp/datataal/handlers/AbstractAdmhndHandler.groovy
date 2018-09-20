package nl.bzk.brp.datataal.handlers
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.datataal.util.ApplicatieServerTijdThreadLocal
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingLeveringGroepModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Abstracte handler die de basis is voor alle gebeurtenissen die een
 * administratieve handeling hebben als oorsprong.
 *
 * Deze handler heeft de kennis om een {@link AdministratieveHandelingModel} en
 * {@link ActieModel} te kunnen maken.
 */
abstract class AbstractAdmhndHandler extends AbstractHandler {
    Logger logger = LoggerFactory.getLogger(getClass())

    PersoonHisVolledigImplBuilder builder
    GebeurtenisAttributen         handelingDefaults

    /**
     * Maak een handler aan, met een persoonbuilder en gebruikte waardes voor de
     * basis informatie voor een administratieve handeling en of acties.
     *
     * @param attr de waardes voor adminstratieve handeling
     * @param b de persoonHisVolledigBuilder om een persoon te maken
     */
    AbstractAdmhndHandler(GebeurtenisAttributen attr, PersoonHisVolledigImplBuilder b) {
        this.builder = b
        this.handelingDefaults = attr
    }

    /**
     * Geeft een datumtijdattribuut als de registratiedatum is ingevuld.
     *
     * @return DatumTijdAttribuut DatumTijdAttribuut
     */
    protected DatumTijdAttribuut geefEvtMeegegevenRegistratieDatum() {
        if (this.handelingDefaults.registratieDatum != 0) {
            final int jaar = Integer.parseInt(this.handelingDefaults.geefRegistratieDatum().toString().substring(0, 4));
            final int maand = Integer.parseInt(this.handelingDefaults.geefRegistratieDatum().toString().substring(4, 6));
            final int dag = Integer.parseInt(this.handelingDefaults.geefRegistratieDatum().toString().substring(6, 8));
            final Calendar c = Calendar.getInstance()
            c.setTime(new Date())
            c.set(Calendar.YEAR, jaar)
            c.set(Calendar.MONTH, maand -1)
            c.set(Calendar.DAY_OF_MONTH, dag)

            // Veiligheidsmarge, de applicatieserver kan mogelijk achterlopen qua tijd.
            c.add(Calendar.MINUTE, -2)

            return new DatumTijdAttribuut(c.getTime())
        } else {
            return null
        }
    }

    /**
     * Maak een administratieve handeling van het opgegeven soort. De handeling is gemarkeerd als
     * geleverd, door het invullen van het attribuut "levering".
     *
     * @param soort de soort van de administratieve handelin
     * @return instantie van {@link AdministratieveHandelingModel}
     */
    protected AdministratieveHandelingModel genereerHandeling(SoortAdministratieveHandeling soort = null) {
        Date tijdstipRegistratie
        if (ApplicatieServerTijdThreadLocal.get() != null) {
            tijdstipRegistratie = ApplicatieServerTijdThreadLocal.get()
        } else {
            // Veiligheidsmarge van 2 minuten aangezien applicatieserver achter kan lopen in tijd, anders zou deze handeling mogelijk in toekomst
            // plaatsvinden.
            Calendar tijdstipRegistratieCal = Calendar.getInstance()
            tijdstipRegistratieCal.add(Calendar.MINUTE, -2)
            tijdstipRegistratie = tijdstipRegistratieCal.getTime()
        }

        def admhnd = new AdministratieveHandelingModel(
            new SoortAdministratieveHandelingAttribuut(soort ?: handelingDefaults.soortHandeling),
            new PartijAttribuut(handelingDefaults.geefPartij()),
            new OntleningstoelichtingAttribuut(handelingDefaults.toelichting),
            new DatumTijdAttribuut(tijdstipRegistratie))
        admhnd.levering = new AdministratieveHandelingLeveringGroepModel(DatumTijdAttribuut.bouwDatumTijd(1970, 1, 1))

        return admhnd
    }

    /**
     * Maak een actie van het gegeven soort, die hoort bij de opgegeven administratieve handeling.
     * Gebruikt de attributen opgegeven in de constructor.
     *
     * @param soort de soort van de actie
     * @param admhnd de handeling waar de actie onder valt
     * @return instantie van {@link ActieModel}
     *
     * @see nl.bzk.brp.datataal.handlers.persoon.AbstractGebeurtenisHandler
     *
     */
    protected ActieModel maakActie(SoortActie soort, AdministratieveHandelingModel admhnd) {
        return maakActie(soort, admhnd, null);
    }

    /**
     * Maak een actie van het gegeven soort, die hoort bij de opgegeven administratieve handeling.
     * Gebruikt de attributen opgegeven in de constructor.
     *
     * @param soort de soort van de actie
     * @param admhnd de handeling waar de actie onder valt
     * @param datumAanvang de optionele datumAanvang van de actie
     * @return instantie van {@link ActieModel}
     *
     * @see nl.bzk.brp.datataal.handlers.persoon.AbstractGebeurtenisHandler
     *
     */
    protected ActieModel maakActie(SoortActie soort, AdministratieveHandelingModel admhnd, Integer datumAanvang) {
        def datumAanvangActie = datumAanvang ?: handelingDefaults.geefDatumAanvang()

        def actie = new ActieModel(new SoortActieAttribuut(soort), admhnd, admhnd.partij, new DatumEvtDeelsOnbekendAttribuut(datumAanvangActie),
                null, admhnd.tijdstipRegistratie, null)
        admhnd.acties << actie

        return actie
    }
}
