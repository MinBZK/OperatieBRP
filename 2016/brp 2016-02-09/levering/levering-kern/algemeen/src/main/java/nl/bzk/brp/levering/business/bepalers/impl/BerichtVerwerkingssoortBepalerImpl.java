/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortBepaler;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.AbstractFormeelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.Groep;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.VerantwoordingTbvLeveringMutaties;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.HisVolledigPredikaatViewUtil;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.springframework.stereotype.Component;

/**
 * Deze klasse is de implementatie van de bericht verwerkingssoort bepaler. Deze verzorgt het bepalen van de verwerkingssoorten voor de elementen in de
 * kennisgeving-berichten.
 */
@Component
public class BerichtVerwerkingssoortBepalerImpl implements BerichtVerwerkingssoortBepaler {

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoort(final HistorieEntiteit formeleOfMaterieleHistorie, final Long administratieveHandelingId)
    {
        final Verwerkingssoort verwerkingssoort;
        if (formeleOfMaterieleHistorie instanceof MaterieelVerantwoordbaar) {
            final MaterieelVerantwoordbaar materieleHistorie = (MaterieelVerantwoordbaar) formeleOfMaterieleHistorie;
            verwerkingssoort = getVerwerkingssoortVoorMaterieleHistorie(materieleHistorie, administratieveHandelingId);
        } else {
            final FormeelVerantwoordbaar formeleHistorie = (FormeelVerantwoordbaar) formeleOfMaterieleHistorie;
            verwerkingssoort = getVerwerkingssoortVoorFormeleHistorie(formeleHistorie, administratieveHandelingId);
        }
        return verwerkingssoort;
    }

    /**
     * Bepaalt de verwerkingssoort voor de persoon, dit is bij een mutatielevering altijd WIJZIGING.
     *
     * @param administratieveHandelingId De huidige administratieve handeling.
     * @param persoonHisVolledigView     De persoon his volledig delta view.
     * @return de verwerkingssoort voor deze persoon
     */
    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortPersoon(final Long administratieveHandelingId,
        final PersoonHisVolledigView persoonHisVolledigView)
    {
        // Maak een nieuwe view waar alleen het AdministratieveHandelingDeltaPredikaat tegen aan is gehouden, zodat verwerkingssoort kan worden
        // bepaald, zonder dat informatie eventueel is weggefilterd door andere predikaten.
        final PersoonHisVolledigView tijdelijkePersoonHisVolledigView =
            new PersoonHisVolledigView(persoonHisVolledigView.getPersoon(), new AdministratieveHandelingDeltaPredikaat(administratieveHandelingId));

        final Verwerkingssoort verwerkingsSoort;
        if (tijdelijkePersoonHisVolledigView.getPersoonAfgeleidAdministratiefHistorie().getAantal() == 1) {
            verwerkingsSoort = Verwerkingssoort.TOEVOEGING;
        } else if (tijdelijkePersoonHisVolledigView.getPersoonAfgeleidAdministratiefHistorie().getAantal() == 0) {
            verwerkingsSoort = Verwerkingssoort.IDENTIFICATIE;
        } else {
            verwerkingsSoort = Verwerkingssoort.WIJZIGING;
        }

        return verwerkingsSoort;
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortBetrokkenPersoon(final Long administratieveHandelingId,
        final PersoonHisVolledigView persoonHisVolledigView)
    {
        return bepaalObjectVerwerkingssoort(persoonHisVolledigView.getIdentificerendeHistorieRecords(), administratieveHandelingId);
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortBetrokkenKind(final Long administratieveHandelingId,
        final PersoonHisVolledigView persoonHisVolledigView)
    {
        final Set<HistorieEntiteit> juridischePL = new HashSet<>();
        juridischePL.addAll(persoonHisVolledigView.getPersoonIdentificatienummersHistorie().getHistorie());
        juridischePL.addAll(persoonHisVolledigView.getPersoonSamengesteldeNaamHistorie().getHistorie());
        juridischePL.addAll(persoonHisVolledigView.getPersoonGeboorteHistorie().getHistorie());
        return bepaalObjectVerwerkingssoort(juridischePL, administratieveHandelingId);
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortVoorRelaties(final RelatieHisVolledigView relatieHisVolledigView,
        final Long administratieveHandelingId)
    {
        return bepaalObjectVerwerkingssoort(relatieHisVolledigView.getAlleHistorieRecords(), administratieveHandelingId);
    }

    @Override
    public final Verwerkingssoort bepaalVerwerkingssoortVoorBetrokkenheden(final BetrokkenheidHisVolledigView betrokkenheidHisVolledigView,
        final Long administratieveHandelingId)
    {
        return bepaalObjectVerwerkingssoort(betrokkenheidHisVolledigView.getAlleHistorieRecords(), administratieveHandelingId);
    }

    /**
     * Bepaalt de verwerkingssoort van een materiele historie object.
     *
     * @param materieelVerantwoordbaar   Het materiele historie object.
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @return De verwerkingssoort.
     */
    private Verwerkingssoort getVerwerkingssoortVoorMaterieleHistorie(final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar,
        final Long administratieveHandelingId)
    {
        final ActieModel verantwoordingVerval = bepaalVerantwoordingVerval(materieelVerantwoordbaar);
        return getVerwerkingssoort((HistorieEntiteit) materieelVerantwoordbaar, materieelVerantwoordbaar.getVerantwoordingInhoud(),
            verantwoordingVerval, materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid(), administratieveHandelingId);
    }

    /**
     * Bepaalt de verwerkingssoort van een formele historie object.
     *
     * @param formeelVerantwoordbaar     Het formele historie object.
     * @param administratieveHandelingId De id van de huidige administratieve handeling.
     * @return De verwerkingssoort.
     */
    private Verwerkingssoort getVerwerkingssoortVoorFormeleHistorie(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar,
        final Long administratieveHandelingId)
    {
        final ActieModel verantwoordingVerval = bepaalVerantwoordingVerval(formeelVerantwoordbaar);
        return getVerwerkingssoort((HistorieEntiteit) formeelVerantwoordbaar, formeelVerantwoordbaar.getVerantwoordingInhoud(),
            verantwoordingVerval, null, administratieveHandelingId);
    }

    /**
     * Bepaalt de actie verval op basis van een formeel record.
     *
     * @param formeelVerantwoordbaar een record
     * @return een actie
     */
    private ActieModel bepaalVerantwoordingVerval(final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar) {
        final ActieModel verantwoordingVerval;
        if (formeelVerantwoordbaar instanceof VerantwoordingTbvLeveringMutaties) {
            verantwoordingVerval = bepaalVerantwoordingVerval((VerantwoordingTbvLeveringMutaties) formeelVerantwoordbaar);
        } else {
            verantwoordingVerval = formeelVerantwoordbaar.getVerantwoordingVerval();
        }
        return verantwoordingVerval;
    }

    /**
     * Bepaalt de verantwoordingverval op basis van een VerantwoordingTbvLeveringMutaties record
     *
     * @param verantwoordingTbvLeveringMutaties een record
     * @return een actie
     */
    private ActieModel bepaalVerantwoordingVerval(final VerantwoordingTbvLeveringMutaties verantwoordingTbvLeveringMutaties) {
        final ActieModel actieModel;
        if (verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties() != null)
        {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVervalTbvLeveringMutaties();
        } else {
            actieModel = verantwoordingTbvLeveringMutaties.getVerantwoordingVerval();
        }
        return actieModel;
    }

    /**
     * Bepaalt de verwerkingssoort op basis van de actie inhoud, actie verval, actie aanpassing geldigheid en de huidige administratieve handeling.
     *
     * @param verantwoordingInhoud          De verantwoordingInhoud.
     * @param verantwoordingVerval          De verantwoordingVerval.
     * @param verantwoordingEindeGeldigheid De datum verantwoordingEindeGeldigheid.
     * @param administratieveHandelingId    De id van de administratieve handeling.
     * @return De verwerkingssoort.
     */
    @Regels(Regel.VR00073)
    private Verwerkingssoort getVerwerkingssoort(final HistorieEntiteit formeleOfMaterieleHistorie, final ActieModel verantwoordingInhoud,
        final ActieModel verantwoordingVerval, final ActieModel verantwoordingEindeGeldigheid, final Long administratieveHandelingId)
    {
        final Verwerkingssoort verwerkingssoort;

        if (isToevoegingDoorDezeAdministratievehandeling(verantwoordingInhoud, verantwoordingVerval, verantwoordingEindeGeldigheid,
            administratieveHandelingId))
        {
            verwerkingssoort = Verwerkingssoort.TOEVOEGING;
        } else if (isToevoegingMetAanpassingGeldigheid(verantwoordingInhoud, verantwoordingEindeGeldigheid, administratieveHandelingId)) {
            verwerkingssoort = Verwerkingssoort.TOEVOEGING;
        } else if (isWijziging(verantwoordingInhoud, verantwoordingEindeGeldigheid, administratieveHandelingId)) {
            verwerkingssoort = Verwerkingssoort.WIJZIGING;
        } else if (isVervallenDoorDezeHandeling(verantwoordingVerval, administratieveHandelingId)) {
            verwerkingssoort = Verwerkingssoort.VERVAL;
        } else if (isIdentificatie(formeleOfMaterieleHistorie, verantwoordingInhoud, verantwoordingEindeGeldigheid, verantwoordingVerval,
            administratieveHandelingId))
        {
            verwerkingssoort = Verwerkingssoort.IDENTIFICATIE;
        } else {
            verwerkingssoort = Verwerkingssoort.REFERENTIE;
        }

        return verwerkingssoort;
    }

    private boolean isIdentificatie(final HistorieEntiteit formeleOfMaterieleHistorie, final ActieModel verantwoordingInhoud,
                                    final ActieModel verantwoordingEindeGeldigheid, final ActieModel verantwoordingVerval,
                                    final Long administratieveHandelingId)
    {
        if (!isIdentificerendeGroep(formeleOfMaterieleHistorie)
            || isHeeftDatumEindeGeldigheid(formeleOfMaterieleHistorie))
        {
            return false;
        }

        final boolean actieInhoudIdentificerend = verantwoordingInhoud == null
            || !isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingInhoud);
        final boolean actieEindeGeldigHeidIdentificerend = verantwoordingEindeGeldigheid == null
            || !isZelfdeAdministratieveHandeling(administratieveHandelingId,
            verantwoordingEindeGeldigheid);
        final boolean actieVervalIdentificerend = verantwoordingVerval == null
            || !isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingVerval);
        return actieInhoudIdentificerend && actieEindeGeldigHeidIdentificerend && actieVervalIdentificerend;
    }

    private boolean isHeeftDatumEindeGeldigheid(final HistorieEntiteit formeleOfMaterieleHistorie) {
        final boolean heeftDatumEindeGeldigheid;
        if (formeleOfMaterieleHistorie instanceof MaterieleHistorie) {
            final MaterieleHistorie materieleHistorie = (MaterieleHistorie) formeleOfMaterieleHistorie;
            heeftDatumEindeGeldigheid = materieleHistorie.getDatumEindeGeldigheid() != null;
        } else {
            heeftDatumEindeGeldigheid = false;
        }
        return heeftDatumEindeGeldigheid;
    }

    private boolean isIdentificerendeGroep(final HistorieEntiteit formeleOfMaterieleHistorie) {
        if (formeleOfMaterieleHistorie instanceof Groep) {
            final Groep groep = (Groep) formeleOfMaterieleHistorie;
            return HisVolledigPredikaatViewUtil.isAltijdTonenGroep(groep.getClass());
        }
        return false;
    }

    /**
     * Controleert of de actie verval is gevuld, in dit geval is het element vervallen.
     *
     * @param verantwoordingVerval       De verantwoordingVerval.
     * @param administratieveHandelingId De id van de administratieve handeling.
     * @return Boolean true als het element is vervallen, anders false.
     */
    private boolean isVervallenDoorDezeHandeling(final ActieModel verantwoordingVerval, final Long administratieveHandelingId) {
        return verantwoordingVerval != null
            && isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingVerval);
    }

    /**
     * Controleert of het element gewijzigd is of is toegevoegd op basis van de actie inhoud en actie aanpassing geldigheid.
     *
     * @param verantwoordingInhoud          De verantwoordingInhoud.
     * @param verantwoordingEindeGeldigheid De datum verantwoordingEindeGeldigheid.
     * @param administratieveHandelingId    De id van de administratieve handeling.
     * @return Boolean true als het element is gewijzigd of is toegevoegd, anders false.
     */
    private boolean isWijziging(
        final ActieModel verantwoordingInhoud,
        final ActieModel verantwoordingEindeGeldigheid,
        final Long administratieveHandelingId)
    {
        return verantwoordingEindeGeldigheid != null
            && verantwoordingInhoud != null
            && isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingEindeGeldigheid)
            && !isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingInhoud);
    }

    /**
     * Controleert of het element gewijzigd is of is toegevoegd op basis van de actie inhoud en actie aanpassing geldigheid.
     *
     * @param verantwoordingInhoud          De verantwoordingInhoud.
     * @param verantwoordingEindeGeldigheid De datum verantwoordingEindeGeldigheid.
     * @param administratieveHandelingId    De id van de administratieve handeling.
     * @return Boolean true als het element is gewijzigd of is toegevoegd, anders false.
     */
    private boolean isToevoegingMetAanpassingGeldigheid(
        final ActieModel verantwoordingInhoud,
        final ActieModel verantwoordingEindeGeldigheid,
        final Long administratieveHandelingId)
    {
        return verantwoordingEindeGeldigheid != null
            && verantwoordingInhoud != null
            && isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingInhoud)
            && isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingEindeGeldigheid);
    }

    /**
     * Controleert of een element is toegevoegd of een identificerend element is.
     *
     * @param verantwoordingInhoud          De verantwoordingInhoud.
     * @param verantwoordingVerval          De verantwoordingVerval.
     * @param verantwoordingEindeGeldigheid De datum verantwoordingEindeGeldigheid.
     * @param administratieveHandelingId    De id van de administratieve handeling.
     * @return Boolean true als het element is toegevoegd of identificerend is, anders false.
     */
    private boolean isToevoegingDoorDezeAdministratievehandeling(
        final ActieModel verantwoordingInhoud,
        final ActieModel verantwoordingVerval,
        final ActieModel verantwoordingEindeGeldigheid,
        final Long administratieveHandelingId)
    {
        return verantwoordingInhoud != null
            && verantwoordingVerval == null
            && verantwoordingEindeGeldigheid == null
            && isZelfdeAdministratieveHandeling(administratieveHandelingId, verantwoordingInhoud);
    }

    /**
     * Controleert of de administratieve handeling van de actie gelijk is aan de huidige administratieve handeling.
     *
     * @param verantwoording             De verantwoordingInhoud, verantwoordingVerval of verantwoordingEindeGeldigheid.
     * @param administratieveHandelingId De id van de administratieve handeling.
     * @return Boolean true als de administratieve handelingen gelijk zijn, anders false.
     */
    private boolean isZelfdeAdministratieveHandeling(
        final Long administratieveHandelingId,
        final ActieModel verantwoording)
    {
        return administratieveHandelingId.equals(verantwoording.getAdministratieveHandeling().getID());
    }

    /**
     * Bepaal de verwerkingssoort voor een object.
     *
     * @param historieEntiteiten historie entiteiten van het object
     * @param administratieveHandelingId administratieve handeling id
     * @return de verwerkingssoort
     */
    @Regels(Regel.R1320)
    private Verwerkingssoort bepaalObjectVerwerkingssoort(final Set<HistorieEntiteit> historieEntiteiten, final Long administratieveHandelingId) {
        final Verwerkingssoort objectVerwerkingssoort;
        final Map<HistorieEntiteit, Verwerkingssoort> verwerkingssoortMap = maakVerwerkingssoortMap(historieEntiteiten, administratieveHandelingId);
        if (isVervallenObject(historieEntiteiten, verwerkingssoortMap)) {
            objectVerwerkingssoort = Verwerkingssoort.VERVAL;
        } else {
            final Set<Verwerkingssoort> groepsVerwerkingssoorten = new HashSet<>(verwerkingssoortMap.values());
            if (groepsVerwerkingssoorten.size() == 1) {
                objectVerwerkingssoort = groepsVerwerkingssoorten.iterator().next();
            } else if (groepsVerwerkingssoorten.size() == 2) {
                final Iterator<Verwerkingssoort> iterator = groepsVerwerkingssoorten.iterator();
                final Verwerkingssoort eersteVerwerkingssoort = iterator.next();
                final Verwerkingssoort tweedeVerwerkingssoort = iterator.next();
                if ((Verwerkingssoort.IDENTIFICATIE.equals(eersteVerwerkingssoort) || Verwerkingssoort.REFERENTIE.equals(eersteVerwerkingssoort)) && (
                    Verwerkingssoort.IDENTIFICATIE.equals(tweedeVerwerkingssoort) || Verwerkingssoort.REFERENTIE.equals(tweedeVerwerkingssoort)))
                {
                    objectVerwerkingssoort = Verwerkingssoort.IDENTIFICATIE;
                } else {
                    objectVerwerkingssoort = Verwerkingssoort.WIJZIGING;
                }
            } else {
                objectVerwerkingssoort = Verwerkingssoort.WIJZIGING;
            }
        }
        return objectVerwerkingssoort;
    }

    /**
     * Maak een map met verwerkingssoorten per historie entiteit op basis van een administratievehandeling id.
     *
     * @param historieEntiteiten historie entiteiten
     * @param administratieveHandelingId administratieve handeling id
     * @return de map
     */
    private Map<HistorieEntiteit, Verwerkingssoort> maakVerwerkingssoortMap(final Set<HistorieEntiteit> historieEntiteiten,
        final Long administratieveHandelingId)
    {
        final Map<HistorieEntiteit, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
        for (final HistorieEntiteit historieEntiteit : historieEntiteiten) {
            final Verwerkingssoort groepsVerwerkingssoort = bepaalVerwerkingssoort(historieEntiteit, administratieveHandelingId);
            verwerkingssoortMap.put(historieEntiteit, groepsVerwerkingssoort);
        }
        return verwerkingssoortMap;
    }

    /**
     * Controleert of object vervallen is. Een historie entiteit kan een andere verwerkingssoort hebben dan Verwerkingssoort.VERVAL, maar toch vervallen
     * zijn in historisch perspectief (door een eerdere administratieve handeling). Als een object historisch gezien enkel vervallen historie entiteiten
     * heeft, dan kan het gehele object als vervallen worden gezien.
     *
     * @param historieEntiteiten historie entiteiten
     * @param verwerkingssoortMap verwerkingssoort map
     * @return true als object vervallen is, anders false.
     */
    private boolean isVervallenObject(final Set<HistorieEntiteit> historieEntiteiten, final Map<HistorieEntiteit, Verwerkingssoort> verwerkingssoortMap) {
        if (verwerkingssoortMap.containsValue(Verwerkingssoort.VERVAL)) {
            boolean isObjectVervallen = true;
            for (final HistorieEntiteit historieEntiteit : historieEntiteiten) {
                if (!Verwerkingssoort.VERVAL.equals(verwerkingssoortMap.get(historieEntiteit))) {
                    isObjectVervallen &= isVervallenHistorieEntiteit(historieEntiteit);
                }
            }
            if (isObjectVervallen) {
                return true;
            }
        }
        return false;
    }

    /**
     * Controleert of historie entiteit vervallen is.
     *
     * @param historieEntiteit historie entiteit
     * @return true als historie entiteit vervallen is, anders false.
     */
    private boolean isVervallenHistorieEntiteit(final HistorieEntiteit historieEntiteit) {
        boolean isVervallen = false;
        if (historieEntiteit instanceof AbstractFormeelHistorischMetActieVerantwoording) {
            final AbstractFormeelHistorischMetActieVerantwoording formeleHistorie = (AbstractFormeelHistorischMetActieVerantwoording) historieEntiteit;
            isVervallen = formeleHistorie.getDatumTijdVerval() != null && formeleHistorie.getVerantwoordingVerval() != null;
        } else if (historieEntiteit instanceof AbstractMaterieelHistorischMetActieVerantwoording) {
            final AbstractMaterieelHistorischMetActieVerantwoording materieleHistorie = (AbstractMaterieelHistorischMetActieVerantwoording) historieEntiteit;
            isVervallen = materieleHistorie.getDatumTijdVerval() != null && materieleHistorie.getVerantwoordingVerval() != null;
        }
        return isVervallen;
    }

}
