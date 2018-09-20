package nl.bzk.brp.datataal.handlers.persoon
import nl.bzk.brp.datataal.handlers.AbstractAdmhndHandler
import nl.bzk.brp.datataal.model.Acties
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.test.util.ReflectionTestUtils

/**
 * Abstracte handler met basis functionaliteit voor het vastleggen van een gebeurtenis.
 *
 */
abstract class AbstractGebeurtenisHandler extends AbstractAdmhndHandler {
    Logger                        logger = LoggerFactory.getLogger(getClass())
    AdministratieveHandelingModel admhnd

    /**
     * Constructor.
     *
     * @param attr
     * @param b
     * @param admhnd
     */
    AbstractGebeurtenisHandler(GebeurtenisAttributen attr, PersoonHisVolledigImplBuilder b, AdministratieveHandelingModel admhnd) {
        super(attr, b)

        this.admhnd = admhnd
    }

    /**
     * Constructor.
     *
     * @param m waardes standaard benodigd voor een gebeurtenis
     * @param b persoonHisVolledig builder
     */
    AbstractGebeurtenisHandler(GebeurtenisAttributen attr, PersoonHisVolledigImplBuilder b) {
        super(attr, b)
        this.admhnd = genereerHandeling(attr.soortHandeling)

        if (!builder.hisVolledigImpl.isIngeschrevene()) {
            throw new IllegalArgumentException('Alleen van Ingeschreven personen kunnen gebeurtenissen worden beschreven')
        }
    }

    /**
     * Start van de registratie van een gebeurtenis.
     * Hierbij worden (standaard) wijzigende voorkomens toegevoegd aan de persoon, denk aan
     * afgeleidadministratief of de groep bijhouding.
     */
    abstract void startGebeurtenis()

    /**
     * Einde van de registratie van een gebeurtenis.
     * Hierbij worden (standaard) wijzigende voorkomens toegevoegd aan de persoon, denk aan
     * afgeleidadministratief of de groep bijhouding of afleiding van gegevens.
     */
    void eindeGebeurtenis() {}

    /**
     * Geeft de acties die zijn geregistreerd voor deze gebeurtenis.
     * @return
     */
    Acties getActies() {
        new Acties(admhnd.acties)
    }

    protected ActieModel maakActie(SoortActie soort) {
        ActieModel actie = maakActie(soort, this.admhnd)

        DatumTijdAttribuut registratieDatum = geefEvtMeegegevenRegistratieDatum()
        if (registratieDatum != null) {
            ReflectionTestUtils.setField(actie, "tijdstipRegistratie", registratieDatum)
            ReflectionTestUtils.setField(this.admhnd, "tijdstipRegistratie", registratieDatum)
        }

        return actie
    }

    AdministratieveHandelingModel getAdmhnd() {
        return admhnd
    }

    DatumTijdAttribuut geefRegistratieDatum() {
        def registratieDatum = handelingDefaults.geefRegistratieDatum()
        if (registratieDatum > 0) {
            return new DatumTijdAttribuut(new DatumEvtDeelsOnbekendAttribuut(registratieDatum).toDate())
        } else {
            return null;
        }
    }
}
