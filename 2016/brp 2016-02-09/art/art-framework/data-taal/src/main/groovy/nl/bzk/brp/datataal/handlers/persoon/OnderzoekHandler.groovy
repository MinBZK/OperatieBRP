package nl.bzk.brp.datataal.handlers.persoon

import nl.bzk.brp.datataal.handlers.GegevensAfleider
import nl.bzk.brp.datataal.model.GebeurtenisAttributen
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeLangAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OnderzoekOmschrijvingAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SleutelwaardeAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortElement
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonOnderzoek
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoek
import nl.bzk.brp.model.algemeen.stamgegeven.kern.StatusOnderzoekAttribuut
import nl.bzk.brp.model.basis.ElementIdentificeerbaar
import nl.bzk.brp.model.basis.GerelateerdIdentificeerbaar
import nl.bzk.brp.model.basis.HistorieEntiteit
import nl.bzk.brp.model.basis.ModelIdentificeerbaar
import nl.bzk.brp.model.bericht.kern.OnderzoekStandaardGroepBericht
import nl.bzk.brp.model.hisvolledig.impl.kern.GegevenInOnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel
import nl.bzk.brp.util.PersoonHisVolledigViewUtil
import nl.bzk.brp.util.hisvolledig.kern.OnderzoekHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder
import nl.bzk.brp.util.hisvolledig.kern.PersoonOnderzoekHisVolledigImplBuilder

/**
 * Handler voor het aanmaken van onderzoeken.
 *
 */
class OnderzoekHandler extends AbstractGebeurtenisHandler {
    protected ActieModel onderzoekActie;
    protected OnderzoekHisVolledigImpl onderzoek
    protected OnderzoekHisVolledigImplBuilder onderzoekBuilder = new OnderzoekHisVolledigImplBuilder()

    OnderzoekHandler(final GebeurtenisAttributen attr, final PersoonHisVolledigImplBuilder b) {
        super(attr, b)
        onderzoekActie = maakActie(SoortActie.REGISTRATIE_ONDERZOEK)
    }

    @Override
    void startGebeurtenis() {
    }

    /**
     * Maak een onderzoek aan.
     *
     * @param datum
     * @return de builder
     */
    def gestartOp(Map map) {
        admhnd.soort.waarde = SoortAdministratieveHandeling.AANVANG_ONDERZOEK

        String omschrijving = map.omschrijving ? map.omschrijving : null
        Integer aanvangsDatum = map.aanvangsDatum ? bepaalDatum(map.aanvangsDatum) : null
        Integer verwachteAfhandelDatum = map.verwachteAfhandelDatum ? bepaalDatum(map.verwachteAfhandelDatum) : null

        if (verwachteAfhandelDatum == null) {
            throw new IllegalArgumentException("verwachteAfhandelDatum kan niet null zijn")
        }

        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def persoonOnderzoekHisVolledigImpl = new PersoonOnderzoekHisVolledigImplBuilder(builder.hisVolledigImpl)
            .nieuwStandaardRecord(onderzoekActie)
            .rol(SoortPersoonOnderzoek.DIRECT)
            .eindeRecord().build();

        onderzoek = onderzoekBuilder
            .voegPersoonOnderzoekToe(persoonOnderzoekHisVolledigImpl)
            .nieuwStandaardRecord(onderzoekActie)
            .datumAanvang(aanvangsDatum)
            .omschrijving(omschrijving)
            .verwachteAfhandeldatum(verwachteAfhandelDatum)
            .status(StatusOnderzoek.IN_UITVOERING)
            .eindeRecord().build()

        builder.voegPersoonOnderzoekToe(persoonOnderzoekHisVolledigImpl)
    }

    /**
     * Voeg een onderzoek voorkomen toe aan de historie, zet huidige status op gestaakt.
     *
     * @param datum
     * @return
     */
    def gestaaktOp(Map map) {
        onderzoekActie.soort.waarde = SoortActie.WIJZIGING_ONDERZOEK
        admhnd.soort.waarde = SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK

        Integer eindDatum = map.eindDatum ? bepaalDatum(map.eindDatum) : null

        PersoonOnderzoekHisVolledig persoonOnderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()
        HisOnderzoekModel historieModel = persoonOnderzoek.onderzoek.onderzoekHistorie.actueleRecord

        persoonOnderzoek.onderzoek.onderzoekHistorie.verval(onderzoekActie, geefRegistratieDatum() ?: admhnd.tijdstipRegistratie)

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def bericht = new OnderzoekStandaardGroepBericht()
        bericht.datumAanvang = historieModel.datumAanvang
        bericht.omschrijving = historieModel.omschrijving
        bericht.verwachteAfhandeldatum = historieModel.verwachteAfhandeldatum
        bericht.datumEinde = new DatumEvtDeelsOnbekendAttribuut(eindDatum)
        bericht.status = new StatusOnderzoekAttribuut(StatusOnderzoek.GESTAAKT)

        def record = new HisOnderzoekModel(persoonOnderzoek.onderzoek, bericht, onderzoekActie)
        persoonOnderzoek.onderzoek.onderzoekHistorie.voegToe(record)
    }

    /**
     * Voeg een onderzoek voorkomen toe aan de historie, zet huidige status op afgesloten.
     *
     * @param datum
     * @return
     */
    def afgeslotenOp(Map map) {
        onderzoekActie.soort.waarde = SoortActie.WIJZIGING_ONDERZOEK
        admhnd.soort.waarde = SoortAdministratieveHandeling.BEEINDIGING_ONDERZOEK

        Integer eindDatum = map.eindDatum ? bepaalDatum(map.eindDatum) : null

        PersoonOnderzoekHisVolledig persoonOnderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()
        HisOnderzoekModel historieModel = persoonOnderzoek.onderzoek.onderzoekHistorie.actueleRecord

        persoonOnderzoek.onderzoek.onderzoekHistorie.verval(onderzoekActie, geefRegistratieDatum() ?: admhnd.tijdstipRegistratie)

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def bericht = new OnderzoekStandaardGroepBericht()
        bericht.datumAanvang = historieModel.datumAanvang
        bericht.omschrijving = historieModel.omschrijving
        bericht.verwachteAfhandeldatum = historieModel.verwachteAfhandeldatum
        bericht.datumEinde = new DatumEvtDeelsOnbekendAttribuut(eindDatum)
        bericht.status = new StatusOnderzoekAttribuut(StatusOnderzoek.AFGESLOTEN)

        def record = new HisOnderzoekModel(persoonOnderzoek.onderzoek, bericht, onderzoekActie)
        persoonOnderzoek.onderzoek.onderzoekHistorie.voegToe(record)
    }

    /**
     * Voeg een onderzoek voorkomen toe aan de historie, zet huidige status op afgesloten.
     *
     * @param datum
     * @return
     */
    def wijzigOnderzoek(Map map) {
        onderzoekActie.soort.waarde = SoortActie.WIJZIGING_ONDERZOEK
        admhnd.soort.waarde = SoortAdministratieveHandeling.WIJZIGING_ONDERZOEK

        String omschrijving = map.omschrijving ? map.omschrijving : null
        Integer aanvangsDatum = map.aanvangsDatum ? bepaalDatum(map.aanvangsDatum) : null
        Integer verwachteAfhandelDatum = map.verwachteAfhandelDatum ? bepaalDatum(map.verwachteAfhandelDatum) : null
        Integer eindDatum = map.eindDatum ? bepaalDatum(map.eindDatum) : null

        PersoonOnderzoekHisVolledig persoonOnderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()
        HisOnderzoekModel historieModel = persoonOnderzoek.onderzoek.onderzoekHistorie.actueleRecord

        persoonOnderzoek.onderzoek.onderzoekHistorie.verval(onderzoekActie, geefRegistratieDatum() ?: admhnd.tijdstipRegistratie)

        builder.hisVolledigImpl.getAdministratieveHandelingen().add(admhnd)
        builder.hisVolledigImpl.getPersoonAfgeleidAdministratiefHistorie()
            .voegToe(GegevensAfleider.maakAfgeleidAdministratief(builder.hisVolledigImpl, onderzoekActie, 1))

        def bericht = new OnderzoekStandaardGroepBericht()
        bericht.datumAanvang = aanvangsDatum ? new DatumEvtDeelsOnbekendAttribuut(aanvangsDatum) : historieModel.datumAanvang
        bericht.omschrijving = omschrijving ? new OnderzoekOmschrijvingAttribuut(omschrijving) : historieModel.omschrijving
        bericht.verwachteAfhandeldatum = verwachteAfhandelDatum ? new DatumEvtDeelsOnbekendAttribuut(verwachteAfhandelDatum) : historieModel.verwachteAfhandeldatum
        bericht.datumEinde = eindDatum ? new DatumEvtDeelsOnbekendAttribuut(eindDatum) : historieModel.datumEinde
        bericht.status = historieModel.status

        def record = new HisOnderzoekModel(persoonOnderzoek.onderzoek, bericht, onderzoekActie)
        persoonOnderzoek.onderzoek.onderzoekHistorie.voegToe(record)
    }

    /**
     * Voeg een gegeven in onderzoek toe.
     *
     * @param map
     * @return
     */
    def gegevensInOnderzoek(String elementNaam) {
        if (onderzoek == null) {
            throw new IllegalArgumentException('Beeindiging onderzoek is niet mogelijk als persoon geen onderzoek heeft')
        }

        Element element = referentieData.vindElementOpNaam(new NaamEnumeratiewaardeLangAttribuut(elementNaam))
        Element targetElement
        if (element.soort == SoortElement.ATTRIBUUT && element.groep != null && element.groep.elementNaam.waarde.equals("Identiteit")) {
            targetElement = element.objecttype;
        } else {
            targetElement = SoortElement.ATTRIBUUT == element.soort ? element.groep : element
        }

        PersoonHisVolledigImpl persoonImpl = builder.hisVolledigImpl
        PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoonImpl, null)
        PersoonHisVolledigViewUtil.initialiseerView(persoonView);

        ModelIdentificeerbaar elementDatInOnderzoekMoetWordenGezet
        if (SoortElement.OBJECTTYPE == targetElement.soort) {
            elementDatInOnderzoekMoetWordenGezet = extractObjectInOnderzoek(targetElement.geefElementEnumName(), persoonView)
        } else {
            elementDatInOnderzoekMoetWordenGezet = geefElementDatInOnderzoekMoetWordenGezet(targetElement.geefElementEnumName(), persoonView)
        }


        def gegevensInOnderzoek = new GegevenInOnderzoekHisVolledigImpl(
            onderzoek,
            new ElementAttribuut(element),
            SoortElement.OBJECTTYPE == targetElement.soort && elementDatInOnderzoekMoetWordenGezet ? new SleutelwaardeAttribuut
                (Long.valueOf(elementDatInOnderzoekMoetWordenGezet.ID)) : null,
            SoortElement.OBJECTTYPE != targetElement.soort && elementDatInOnderzoekMoetWordenGezet ? new SleutelwaardeAttribuut
                (Long.valueOf(elementDatInOnderzoekMoetWordenGezet.ID)) : null
        )

        onderzoek.gegevensInOnderzoek.add(gegevensInOnderzoek)

        if (elementDatInOnderzoekMoetWordenGezet == null) {
            logger.debug("Er wordt getracht een gegeven in onderzoek te zetten, maar deze is niet aanwezig in de persoonslijst: {}.", elementNaam)
        }
    }

    /**
     * Geeft het object dat in onderzoek dient te worden gezet.
     *
     * @param elementEnumName elementEnumName
     * @param persoonHisVolledigView persoonHisVolledigView
     * @return ModelIdentificeerbaar
     */
    ModelIdentificeerbaar extractObjectInOnderzoek(final String elementEnumName, final PersoonHisVolledigView persoonView) {
        for (ElementIdentificeerbaar elementIdentificeerbaar : persoonView.lijstVanObjecten()) {

            if (elementEnumName.equals(elementIdentificeerbaar.getElementIdentificatie().name())) {
                return (ModelIdentificeerbaar) elementIdentificeerbaar
            }
        }
    }

    /**
     * Geeft de groep dat in onderzoek dient te worden gezet.
     *
     * @param elementEnumName elementEnumName
     * @param persoonHisVolledigView persoonHisVolledigView
     * @return ModelIdentificeerbaar
     */
    ModelIdentificeerbaar geefElementDatInOnderzoekMoetWordenGezet(final String elementEnumName, final PersoonHisVolledigView persoonHisVolledigView) {
        for (HistorieEntiteit hisElement : persoonHisVolledigView.totaleLijstVanHisElementenOpPersoonsLijst) {

            // Eerst verifieren of dit een element betreft van een betrokkene
            if (hisElement instanceof GerelateerdIdentificeerbaar) {
                GerelateerdIdentificeerbaar gerelateerdIdentificeerbaar = (GerelateerdIdentificeerbaar) hisElement
                if (gerelateerdIdentificeerbaar.getGerelateerdeObjectType() != null
                    && elementEnumName.equalsIgnoreCase(gerelateerdIdentificeerbaar.getGerelateerdeObjectType().name())) {
                    return hisElement
                }
            }

            // Geen gerelateerdeObjectType betekent hoofdpersoon
            if (hisElement instanceof ElementIdentificeerbaar) {
                ElementIdentificeerbaar elementIdentificeerbaar = (ElementIdentificeerbaar) hisElement
                if (elementEnumName.equalsIgnoreCase(elementIdentificeerbaar.getElementIdentificatie().name())) {
                    return hisElement
                }

            } else {
                logger.warn("hisElement is geen ElementIdentificeerbaar: " + hisElement)
            }
        }
        return null
    }

    /**
     * Voeg een gegeven in onderzoek toe.
     *
     * @param map
     * @return
     */
    def persoonInOnderzoek() {

        def onderzoek = builder.hisVolledigImpl.onderzoeken.iterator().next()?.getOnderzoek()
        if (onderzoek == null) {
            throw new IllegalArgumentException('In onderzoek zetten is niet mogelijk als persoon geen onderzoek heeft')
        }

        def persoonInOnderzoek = new PersoonOnderzoekHisVolledigImpl(
            builder.hisVolledigImpl, onderzoek
        )

        onderzoek.personenInOnderzoek.add(persoonInOnderzoek)
    }
}
