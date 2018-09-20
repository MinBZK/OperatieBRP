/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import java.util.Collections;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.business.stappen.bevraging.AbstractBevragingUitvoerStap;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.bevraging.BevragingsBericht;
import nl.bzk.brp.model.bevraging.levering.GeefDetailsPersoonBericht;
import nl.bzk.brp.model.bevraging.levering.ZoekPersoonBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.MagGroepTonenPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.AttribuutUtil;
import nl.bzk.brp.util.ObjectUtil;


/**
 * Uitvoer stap die het opvragen van een persoon afhandelt. De persoon wordt via de DAL laag opgehaald.
 * <p/>
 * TODO: Betere integratie met bevraging tbv bijhouding.
 */
public class LeveringBevragingUitvoerStap extends AbstractBevragingUitvoerStap {

    @Override
    public final boolean voerStapUit(final BevragingsBericht bericht, final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        final boolean verwerkingsResultaat;
        if (bericht instanceof GeefDetailsPersoonBericht) {
            verwerkingsResultaat = geefDetailPersoon(bericht, context, resultaat);
        } else if (bericht instanceof ZoekPersoonBericht) {
            verwerkingsResultaat = zoekPersoon((ZoekPersoonBericht) bericht, context, resultaat);
        } else {
            resultaat
                .voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001,
                    "Er is geen verwerker gevonden voor het soort bericht: "
                        + bericht.getClass().getSimpleName() + "."));
            verwerkingsResultaat = Stap.STOPPEN;
        }
        return verwerkingsResultaat;
    }

    /**
     * Methode om personen te zoeken.
     *
     * @param bericht   het ZoekPersoonBericht.
     * @param context   de bericht context.
     * @param resultaat een set met gevonden personen. @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     * @return true wanneer de verwerking foutloos is verlopen
     * @brp.bedrijfsregel VR00054
     * @brp.bedrijfsregel VR00055
     */
    @Regels({ Regel.VR00054, Regel.VR00055 })
    protected boolean zoekPersoon(final ZoekPersoonBericht bericht,
        final BevragingBerichtContextBasis context,
        final BevragingResultaat resultaat)
    {
        PersoonHisVolledigImpl persoon = null;
        if (isBsnCriteria(bericht)) {
            persoon = haalPersoonOpMetBurgerservicenummer(bericht.getZoekcriteriaPersoon().getBurgerservicenummer());
        } else if (isAnrCriteria(bericht)) {
            persoon = haalPersoonOpMetAdministratienummer(bericht.getZoekcriteriaPersoon().getAdministratienummer());
        }
        if (persoon != null) {
            final PersoonHisVolledigView persoonHisVolledigView = maakHuidigePersoon(persoon);
            persoonHisVolledigView
                .voegPredikaatToe(
                    new MagGroepTonenPredikaat(context.getLeveringinformatie().getDienst().getDienstbundel().geefGeautoriseerdeGroepen()));

            bewerkBetrokkenhedenInViews(Collections.singletonList(persoonHisVolledigView));
            bepaalEnZetObjectSleutels(Collections.singletonList(persoonHisVolledigView), context);
            resultaat.voegGevondenPersoonToe(persoonHisVolledigView);

            // Voeg persoon toe aan de context, voor de naVerwerkingRegels
            context.setPersoonHisVolledigImpl(persoon);

            // Voeg persoon Id toe aan het resultaat voor archivering
            resultaat.getTeArchiverenPersonenIngaandBericht().add(persoonHisVolledigView.getID());
            resultaat.getTeArchiverenPersonenUitgaandBericht().add(persoonHisVolledigView.getID());
        } else {
            resultaat.voegMeldingToe(new Melding(SoortMelding.INFORMATIE, Regel.ALG0001,
                "Er zijn geen personen gevonden die voldoen aan de opgegeven criteria.", bericht
                .getZoekcriteriaPersoon(), "burgerservicenummer"));
        }

        return Stap.DOORGAAN;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected final PersoonHisVolledigView getPersoonHisVolledigView(final PersoonHisVolledigImpl persoon, final DatumTijdAttribuut peilmomentFormeel,
        final DatumAttribuut peilmomentMaterieel, final HistorievormAttribuut historievorm, final BevragingBerichtContextBasis context)
    {
        final PersoonHisVolledigView persoonHisVolledigView = super
            .getPersoonHisVolledigView(persoon, peilmomentFormeel, peilmomentMaterieel, historievorm, context);
        persoonHisVolledigView
            .voegPredikaatToe(new MagGroepTonenPredikaat(context.getLeveringinformatie().getDienst().getDienstbundel().geefGeautoriseerdeGroepen()));
        return persoonHisVolledigView;
    }

    /**
     * Controleer of het om een bsn zoek criteria gaat.
     *
     * @param bericht het bericht
     * @return true als alleen de BSN is ingevuld in het zoek bericht
     */
    private boolean isBsnCriteria(final ZoekPersoonBericht bericht) {
        final boolean isBsnCriteria;

        final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
        isBsnCriteria =
            AttribuutUtil.isNotBlank(zoekCriteria.getBurgerservicenummer())
                && ObjectUtil.isAllEmpty(zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(),
                    zoekCriteria.getHuisletter(), zoekCriteria.getHuisnummertoevoeging(),
                    zoekCriteria.getAdministratienummer(), zoekCriteria.getGeboortedatum(),
                    zoekCriteria.getGeslachtsnaamstam());

        return isBsnCriteria;
    }

    /**
     * Controleer of het om een anummer zoek criteria gaat.
     *
     * @param bericht het bericht
     * @return true als alleen het administratienummer is ingevuld in het zoek bericht
     */
    private boolean isAnrCriteria(final ZoekPersoonBericht bericht) {
        final boolean isAnrCriteria;

        final BerichtZoekcriteriaPersoonGroepBericht zoekCriteria = bericht.getZoekcriteriaPersoon();
        isAnrCriteria =
            !ObjectUtil.isAllEmpty(zoekCriteria.getAdministratienummer())
                && ObjectUtil.isAllEmpty(zoekCriteria.getPostcode(), zoekCriteria.getHuisnummer(),
                    zoekCriteria.getHuisletter(), zoekCriteria.getHuisnummertoevoeging(),
                    zoekCriteria.getBurgerservicenummer(), zoekCriteria.getGeboortedatum(),
                    zoekCriteria.getGeslachtsnaamstam());

        return isAnrCriteria;
    }
}
