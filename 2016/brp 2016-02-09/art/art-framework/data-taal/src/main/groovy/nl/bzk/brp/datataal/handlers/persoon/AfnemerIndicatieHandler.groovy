package nl.bzk.brp.datataal.handlers.persoon
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.datataal.util.ApplicatieServerTijdThreadLocal
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst
import nl.bzk.brp.util.hisvolledig.autaut.PersoonAfnemerindicatieHisVolledigImplBuilder
/**
 * Handler voor het behandelen van een gebeurtenis "plaatsing afnemerindicatie".
 */
class AfnemerIndicatieHandler extends AbstractGebeurtenisHandler {
    AfnemerIndicatieHandler(GebeurtenisAttributen m, def builder) {
        super(m, builder)
    }

    @Override
    void startGebeurtenis() {
        // geen afgeleidadministratief voor Afnemerindicaties
    }

    private PersoonAfnemerindicatieHisVolledigImplBuilder.PersoonAfnemerindicatieHisVolledigImplBuilderStandaard afnemerIndicatieRecord

    def plaatsVoor(Map m, @DelegatesTo(AfnemerIndicatieHandler) Closure closure = {} ) {
        valideerMapKeys(m, ['afnemer', 'abonnement', 'registratieDatum'], 'De attributen "afnemer", "abonnement" en "registratieDatum" zijn verplicht')

        def afnemer = bepaalPartij(m.afnemer)

        def abonnement = referentieData.vindLeveringsAutorisatieOpNaam(new NaamEnumeratiewaardeAttribuut(m.abonnement as String))
        def dienst = abonnement.getAbonnementDiensten().find {
            it.catalogusOptie.waarde == CatalogusOptie.ONDERHOUDEN_AFNEMERINDICATIE
        }

        def bestaandeIndicatie = builder.hisVolledigImpl.afnemerindicaties.find {
            it.abonnement.waarde.ID == abonnement.ID && it.afnemer.waarde.ID == afnemer.ID
        }

        if (!bestaandeIndicatie) {
            afnemerIndicatieRecord = new PersoonAfnemerindicatieHisVolledigImplBuilder(afnemer, abonnement).nieuwStandaardRecord(dienst)
        } else if (bestaandeIndicatie.persoonAfnemerindicatieHistorie.actueleRecord == null) {
            afnemerIndicatieRecord = new PersoonAfnemerindicatieHisVolledigImplBuilder(bestaandeIndicatie).nieuwStandaardRecord(dienst)
        } else {
            throw new IllegalStateException("Persoon heeft al een actuele afnemerindicatie voor afnemer $afnemer.ID, abo $abonnement.naam")
        }

        closure.call()


        Date tijdstipRegistratie
        def meegegevenRegistratieDatum = geefEvtMeegegevenRegistratieDatum();
        if (meegegevenRegistratieDatum != null) {
            tijdstipRegistratie = meegegevenRegistratieDatum.getWaarde()
        } else if (ApplicatieServerTijdThreadLocal.get() != null) {
            tijdstipRegistratie = ApplicatieServerTijdThreadLocal.get()
            logger.debug("Tijdstip registratie van applicatieserver wordt gebruikt bij vastleggen administratieve handeling: {}", tijdstipRegistratie)
        } else {
            // Aangezien de server waarop funqmachine draait in tijd voor kan lopen op applicatieserver, dient hier een veiligheidsmarge te worden
            // ingebouwd.
            // Anders bestaat de mogelijkheid dat afnemerindicaties in de toekomst "geplaatst" zijn en zijn ze niet zichtbaar in een mogelijke view.

            Calendar tijdstipRegistratieCal = Calendar.getInstance()
            tijdstipRegistratieCal.add(Calendar.MINUTE, -2)
            tijdstipRegistratie = tijdstipRegistratieCal.getTime()
        }

        builder.voegPersoonAfnemerindicatieToe(
            afnemerIndicatieRecord.eindeRecord(new DatumTijdAttribuut(tijdstipRegistratie)).build()
        )

        afnemerIndicatieRecord = null
    }

    def verwijderVan(Map m) {
        valideerMapKeys(m, ['afnemer', 'abonnement', 'registratieDatum'], 'De attributen "afnemer", "abonnement" en "registratieDatum" zijn verplicht')

        def partij = bepaalPartij(m.afnemer)

        def abonnement = referentieData.vindLeveringsAutorisatieOpNaam(new NaamEnumeratiewaardeAttribuut(m.abonnement as String))
        def dienst = abonnement.getDienstbundels().find {
            it.diensten.find {
                it.soort == SoortDienst.PLAATSEN_AFNEMERINDICATIE
            }

        }

        def indicaties = builder.hisVolledigImpl.afnemerindicaties
        def indicatie = indicaties.find {
            it.leveringsautorisatie.waarde.ID == abonnement.ID && it.afnemer.waarde.ID == partij.ID
        }

        indicatie?.persoonAfnemerindicatieHistorie?.verval(dienst, admhnd.tijdstipRegistratie)
    }

    def datumAanvangMaterielePeriode(def dat) {
        def datum = bepaalDatum(dat)
        afnemerIndicatieRecord.datumAanvangMaterielePeriode(datum)
    }

    def eindeVolgen(def dat) {
        def datum = bepaalDatum(dat)
        afnemerIndicatieRecord.datumEindeVolgen(datum)
    }
}
