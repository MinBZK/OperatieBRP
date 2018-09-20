/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.gegevensfilter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.brp.levering.business.toegang.populatie.LeveringinformatieService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.FormeelHistorisch;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelHistorisch;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.hisvolledig.kern.ActieHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.DocumentHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.ActieBronHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieBronModel;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisDocumentModel;


/**
 * De implementatie van het filter om de verantwoordingsinformatie te filteren op relevante verantwoordingsinformatie.
 * <p/>
 * VR00086: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Actie’ worden opgenomen waarnaar geen enkele verwijzing bestaat vanuit een
 * inhoudelijke groep in hetzelfde resultaat. Dit betekent dat wanneer een Actie alleen groepen heeft geraakt die door autorisatie of een ander
 * filtermechanisme niet in het bericht komen, de Actie dus niets meer 'verantwoordt' en zelf ook uit het bericht verwijderd moet worden.
 * <p/>
 * VR00087: In het resultaat van een levering mogen geen verantwoordingsgroepen ‘Administratieve handeling’ en onderliggende groepen ‘Bron’ en ‘Document’
 * worden opgenomen als er binnen die Administratieve handeling geen enkele Actie voorkomt waarvoor een verwijzing bestaat vanuit een inhoudelijke groep
 * uit hetzelfde resultaat.
 * <p/>
 * VR00095: In een Inhoudelijke groep van een mutatiebericht worden alleen actieverwijzingen opgenomen die samenhangen met de onderhanden Administratieve
 * handeling.
 * <p/>
 * R2015: Alleen bronnen waarnaar daadwerkelijk wordt verwezen meeleveren.
 * <p/>
 * VR00094: Als stelselbeheerder wil ik dat afnemers alleen de actuele waarde van documenten ontvangen.
 */
@Regels({ Regel.VR00086, Regel.VR00087, Regel.VR00095, Regel.R2015, Regel.VR00094, Regel.R2063, Regel.R2051 })
public abstract class AbstractVerantwoordingsinformatieFilter {

    @Inject
    private LeveringinformatieService leveringinformatieService;

    /**
     * Reset de filtering van een lijst met persoon his volledig views.
     *
     * @param persoonHisVolledigView de persoon his volledig view
     * @param leveringAutorisatie    de leveringautorisatie
     */
    protected final void resetPersoonHisVolledigViews(final PersoonHisVolledigView persoonHisVolledigView, final Leveringinformatie leveringAutorisatie) {
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : persoonHisVolledigView.getAdministratieveHandelingen()) {
            administratieveHandeling.setMagGeleverdWorden(false);
            for (final ActieHisVolledig actie : administratieveHandeling.getActies()) {
                zetMagGeleverdWordenOpActieEnOnderliggendeObjecten(actie, false, null, leveringAutorisatie);
            }
        }
    }


    /**
     * Zet de magGeleverdWorden vlag op een attribuut als deze niet null is naar een bepaalde waarde.
     *
     * @param attribuut         Het attribuut.
     * @param magGeleverdWorden De magGeleverdWorden waarde.
     */
    private void zetMagGeleverdWordenVlag(final Attribuut attribuut, final boolean magGeleverdWorden) {
        if (attribuut != null) {
            attribuut.setMagGeleverdWorden(magGeleverdWorden);
        }
    }

    /**
     * .
     *
     * @param persoonHisVolledigView de persoon view
     * @return lijst met actie id's
     */
    @SuppressWarnings("unchecked")
    protected final ActieInformatie bepaalRelevanteActiesInGroepen(final PersoonHisVolledigView persoonHisVolledigView) {
        final Set<Long> actiesVanuitGroepen = new HashSet<>();
        final Set<Long> documentenVanuitGroepen = new HashSet<>();
        final Set<Short> rechtsGrondenVanuitGroepen = new HashSet<>();

        final ActieInformatie actieInformatie = new ActieInformatie(actiesVanuitGroepen, documentenVanuitGroepen, rechtsGrondenVanuitGroepen);

        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst =
            persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst();
        for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {
            if (historieEntiteit instanceof MaterieelHistorisch) {
                final MaterieelHistorisch materieleHistorie = (MaterieelHistorisch) historieEntiteit;
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) materieleHistorie;
                voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid(), actieInformatie);
                voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingInhoud(), actieInformatie);
                voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(materieelVerantwoordbaar.getVerantwoordingVerval(), actieInformatie);
            } else {
                final FormeelHistorisch formeleHistorie = (FormeelHistorisch) historieEntiteit;
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) formeleHistorie;
                voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(formeelVerantwoordbaar.getVerantwoordingInhoud(), actieInformatie);
                voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(formeelVerantwoordbaar.getVerantwoordingVerval(), actieInformatie);
            }
        }
        return actieInformatie;
    }

    /**
     * Zorgt ervoor dat de magGeleverdWorden vlaggen gezet worden voor een actie en diens onderliggende objecten.
     *
     * @param actie               De actie.
     * @param magGeleverdWorden   De waarde die de magGeleverdWorden vlaggen moeten krijgen.
     * @param actieInformatie     voor het zetten van de maggeleverd vlag in bronnen  wordt actieinformatie meegenomen in controle. Dus als magGeleverd
     *                            worden true dan volgt er extra controle of referenties aanwezig zijn.
     * @param leveringAutorisatie de leveringautorisatie.
     */
    protected final void zetMagGeleverdWordenOpActieEnOnderliggendeObjecten(final ActieHisVolledig actie,
        final boolean magGeleverdWorden, final ActieInformatie actieInformatie, final Leveringinformatie leveringAutorisatie)
    {
        actie.setMagGeleverdWorden(magGeleverdWorden);
        actie.getSoort().setMagGeleverdWorden(magGeleverdWorden);
        for (final ActieBronHisVolledigView actieBronHisVolledig : (Set<ActieBronHisVolledigView>) actie.getBronnen()) {
            boolean bevatMagGeleverdWordenData = false;
            if (actieBronHisVolledig.getDocument() != null) {
                final boolean magGeleverdWordenBerekend = magGeleverdWorden
                    && actieInformatie.getDocumentenVanuitGroepen().contains(actieBronHisVolledig.getDocument().getID());
                final DocumentHisVolledig documentHisVolledig = actieBronHisVolledig.getDocument();
                bevatMagGeleverdWordenData = zetMagGeleverdVlaggetjesVoorDocument(leveringAutorisatie, actieBronHisVolledig,
                    magGeleverdWordenBerekend, documentHisVolledig);
            }
            if (actieBronHisVolledig.getRechtsgrond() != null) {
                final boolean magGeleverdWordenBerekend = magGeleverdWorden
                    && actieInformatie.getRechtsGrondenVanuitGroepen().contains(actieBronHisVolledig.getRechtsgrond().getWaarde().getID());
                zetMagGeleverdWordenVlag(actieBronHisVolledig.getRechtsgrond().getWaarde().getCode(), magGeleverdWordenBerekend);
                zetMagGeleverdWordenVlag(actieBronHisVolledig.getRechtsgrond().getWaarde().getOmschrijving(), magGeleverdWordenBerekend);
                bevatMagGeleverdWordenData = bevatMagGeleverdWordenData || magGeleverdWordenBerekend;
            }
            if (actieBronHisVolledig.getRechtsgrondomschrijving() != null) {
                zetMagGeleverdWordenVlag(actieBronHisVolledig.getRechtsgrondomschrijving(), magGeleverdWorden);
                bevatMagGeleverdWordenData = bevatMagGeleverdWordenData || magGeleverdWorden;
            }
            actieBronHisVolledig.setZichtbaar(bevatMagGeleverdWordenData);
        }

    }

    private boolean zetMagGeleverdVlaggetjesVoorDocument(final Leveringinformatie leveringinformatie,
        final ActieBronHisVolledigView actieBronHisVolledig, final boolean magGeleverdWordenBerekend,
        final DocumentHisVolledig documentHisVolledig)
    {
        boolean bevatMagGeleverdWordenData = false;
        if (!magGeleverdWordenBerekend) {
            for (final HisDocumentModel documentHistorie : actieBronHisVolledig.getDocument().getDocumentHistorie()) {
                //zet de vlaggetjes
                zetDeVlaggetjesVoorDocument(magGeleverdWordenBerekend, documentHistorie);
            }
        } else if (leveringinformatie.getToegangLeveringsautorisatie().heeftBijHouderRol()) {
            for (final HisDocumentModel documentHistorie : actieBronHisVolledig.getDocument().getDocumentHistorie()) {
                //zet de vlaggetjes
                zetDeVlaggetjesVoorDocument(magGeleverdWordenBerekend, documentHistorie);
                bevatMagGeleverdWordenData = true;
            }
        } else if (documentHisVolledig.getDocumentHistorie().getActueleRecord() != null) {
            final HisDocumentModel actueelRecord = documentHisVolledig.getDocumentHistorie().getActueleRecord();
            //zet de vlaggetjes
            zetDeVlaggetjesVoorDocument(magGeleverdWordenBerekend, actueelRecord);
            bevatMagGeleverdWordenData = true;
        }
        return bevatMagGeleverdWordenData;
    }

    private void zetDeVlaggetjesVoorDocument(final boolean magGeleverdWordenBerekend, final HisDocumentModel hisDocumentModel) {
        zetMagGeleverdWordenVlag(hisDocumentModel.getAktenummer(), magGeleverdWordenBerekend);
        zetMagGeleverdWordenVlag(hisDocumentModel.getPartij(), magGeleverdWordenBerekend);
        zetMagGeleverdWordenVlag(hisDocumentModel.getIdentificatie(), magGeleverdWordenBerekend);
        zetMagGeleverdWordenVlag(hisDocumentModel.getOmschrijving(), magGeleverdWordenBerekend);
    }

    /**
     * Voegt de id van een actie toe aan de meegegeven set als de actie niet null is en deze geleverd mag worden.
     *
     * @param actie de actie
     */
    private void voegActieVanuitGroepToeAanSetIndienMagGeleverdWorden(final ActieModel actie, final ActieInformatie actieInformatie) {
        if (actie != null && actie.isMagGeleverdWorden()) {
            actieInformatie.getActiesVanuitGroepen().add(actie.getID());
            final Set<ActieBronModel> bronnen = actie.getBronnen();
            for (final ActieBronModel actieBronModel : bronnen) {
                if (actieBronModel.getRechtsgrond() != null) {
                    actieInformatie.getRechtsGrondenVanuitGroepen().add(actieBronModel.getRechtsgrond().getWaarde().getID());
                }
                if (actieBronModel.getDocument() != null) {
                    actieInformatie.getDocumentenVanuitGroepen().add(actieBronModel.getDocument().getID());
                }
            }
        }
    }

    /**
     * Filtert de administratieve van een persoon op basis van relevante acties.
     *
     * @param persoonHisVolledigView de persoon his volledig view
     * @param actieInformatie        de actie informatie
     * @param leveringAutorisatie    de leveringautorisatie
     */
    @Regels(Regel.VR00087)
    protected final void filterActiesInVerantwoording(final PersoonHisVolledigView persoonHisVolledigView,
        final ActieInformatie actieInformatie, final Leveringinformatie leveringAutorisatie)
    {
        final List<AdministratieveHandelingHisVolledig> administratieveHandelingen =
            persoonHisVolledigView.getAdministratieveHandelingen();

        for (final AdministratieveHandelingHisVolledig administratieveHandeling : administratieveHandelingen) {
            for (final ActieHisVolledig actie : administratieveHandeling.getActies()) {
                // Wanneer de actie relevant is, wordt de magGeleverdWorden-vlag op true gezet.
                if (actieInformatie.getActiesVanuitGroepen().contains(actie.getID())) {
                    administratieveHandeling.setMagGeleverdWorden(true);
                    zetMagGeleverdWordenOpActieEnOnderliggendeObjecten(actie, true, actieInformatie, leveringAutorisatie);
                }
            }
        }
    }

    /**
     * Filtert de acties in de groepen. Dat wil zeggen, zorg ervoor dat er alleen acties in de groepen staan die ook in de verantwoording voorkomen.
     *
     * @param persoonHisVolledigView de persoon his volledig view
     */
    @SuppressWarnings("unchecked")
    @Regels(Regel.VR00086)
    protected final void filterActiesInGroepen(final PersoonHisVolledigView persoonHisVolledigView) {
        final List<AdministratieveHandelingHisVolledig> administratieveHandelingen =
            persoonHisVolledigView.getAdministratieveHandelingen();

        final Set<Long> actieIdsInVerantwoording = new HashSet<>();
        for (final AdministratieveHandelingHisVolledig administratieveHandeling : administratieveHandelingen) {
            for (final ActieHisVolledig actie : administratieveHandeling.getActies()) {
                if (actie.isMagGeleverdWorden()) {
                    actieIdsInVerantwoording.add(actie.getID());
                }
            }
        }

        final List<HistorieEntiteit> totaleLijstVanHisElementenOpPersoonsLijst = persoonHisVolledigView.getTotaleLijstVanHisElementenOpPersoonsLijst();

        for (final HistorieEntiteit historieEntiteit : totaleLijstVanHisElementenOpPersoonsLijst) {
            if (historieEntiteit instanceof MaterieelVerantwoordbaar) {
                final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = (MaterieelVerantwoordbaar<ActieModel>) historieEntiteit;
                filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(materieelVerantwoordbaar.getVerantwoordingInhoud(), actieIdsInVerantwoording);
                filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(materieelVerantwoordbaar.getVerantwoordingVerval(), actieIdsInVerantwoording);
                filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid(),
                    actieIdsInVerantwoording);
            } else {
                final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = (FormeelVerantwoordbaar<ActieModel>) historieEntiteit;
                filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(formeelVerantwoordbaar.getVerantwoordingInhoud(), actieIdsInVerantwoording);
                filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(formeelVerantwoordbaar.getVerantwoordingVerval(), actieIdsInVerantwoording);
            }
        }
    }

    /**
     * Filtert de actie in een groep die niet in de verantwoording voorkomt.
     *
     * @param actieModel               De actie in de groep.
     * @param actieIdsInVerantwoording De actie ids die in verantwoording voorkomen.
     */
    private void filterActieInGroepAlsDezeNietVoorkomtInVerantwoording(final ActieModel actieModel, final Set<Long> actieIdsInVerantwoording) {
        if (actieModel != null && !actieIdsInVerantwoording.contains(actieModel.getID())) {
            actieModel.setMagGeleverdWorden(false);
        }
    }

    /**
     * Zet de mag geleverd worden vlag op true als de actie niet null is en deze geleverd mag worden.
     *
     * @param actie de actie
     */
    protected final void zetActieMagGeleverdWorden(final ActieModel actie) {
        if (actie != null && actie.heeftMinimaal1AttribuutDatGeleverdMagWorden()) {
            actie.setMagGeleverdWorden(true);
        }
    }
}
