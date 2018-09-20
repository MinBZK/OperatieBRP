/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.stamgegeven;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.StamTabelRepository;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieResultaat;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.StamgegevenAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.SynchronisatieStamgegeven;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Element;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regels;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtValidatieStap;


/**
 * De stap die het opgevraagde stamgegeven ophaalt en in het resultaat zet.
 */
public class BerichtVerwerkingsStap
        extends
        AbstractBerichtValidatieStap<BerichtBericht, GeefSynchronisatieStamgegevenBericht, SynchronisatieBerichtContext, SynchronisatieResultaat>
{

    @Inject
    private StamTabelRepository stamTabelRepository;

    @Override
    public final boolean voerStapUit(final GeefSynchronisatieStamgegevenBericht bericht,
            final SynchronisatieBerichtContext context, final SynchronisatieResultaat resultaat)
    {
        final StamgegevenAttribuut opgevraagdeStamgegevenAttr = bericht.getParameters().getStamgegeven();
        if (opgevraagdeStamgegevenAttr != null) {
            final String stamgegevenString = opgevraagdeStamgegevenAttr.getWaarde();

            final SynchronisatieStamgegeven synchronisatieStamgegeven =
                SynchronisatieStamgegeven.vindEnumVoorTabelNaam(stamgegevenString);

            final Class<? extends SynchroniseerbaarStamgegeven> stamgegevenKlasse =
                synchronisatieStamgegeven.getStamgegevenKlasse();

            if (stamgegevenKlasse.isEnum()) {
                // Haal alle waarden van de enumeratie op.
                bepaalEnZetEnumeratieStamgegevensInResultaat(stamgegevenKlasse, resultaat);
            } else {
                // Haal de waarden op via data access.
                bepaalEnZetDatabaseStamgegevensInResultaat(stamgegevenKlasse, resultaat);
            }
        }
        return DOORGAAN;
    }

    /**
     * Haalt het stamgegeven uit de database en zet de records in het resultaat.
     * 
     * @param stamgegevenKlasse model klasse behorend bij het stamgegeven.
     * @param resultaat het resultaat waar de records in moeten.
     */
    private void bepaalEnZetDatabaseStamgegevensInResultaat(
            final Class<? extends SynchroniseerbaarStamgegeven> stamgegevenKlasse,
            final SynchronisatieResultaat resultaat)
    {
        final List<? extends SynchroniseerbaarStamgegeven> alleStamgegevens =
            stamTabelRepository.vindAlleStamgegevens(stamgegevenKlasse);
        final List<SynchroniseerbaarStamgegeven> teLeverenLijst = new ArrayList<>();
        if (stamgegevenKlasse == Element.class) {
            teLeverenLijst.addAll(bepaalTeLeverenElementen(alleStamgegevens));
        } else {
            teLeverenLijst.addAll(alleStamgegevens);
        }
        resultaat.setStamgegeven(teLeverenLijst);

    }

    /**
     * Past verwerkingsregel toe om element stamgegevens te filteren en sorteren
     * 
     * @param alleStamgegevens lijst met all element stamgegevens
     */
    @Regels(Regel.VR00111)
    private List<? extends SynchroniseerbaarStamgegeven> bepaalTeLeverenElementen(
            final List<? extends SynchroniseerbaarStamgegeven> alleStamgegevens)
    {
        final List<Element> elementLijst = new LinkedList<>();
        // enkel de elementen waar leveren als stamgegeven = J
        for (final SynchroniseerbaarStamgegeven synchroniseerbaarStamgegeven : alleStamgegevens) {
            final Element element = (Element) synchroniseerbaarStamgegeven;
            if (element.getLeverenAlsStamgegeven() != null
                && element.getLeverenAlsStamgegeven().getWaarde().getVasteWaarde())
            {
                elementLijst.add(element);
            }
        }
        // sorteer oplopend op volgnr
        Collections.sort(elementLijst, new Comparator<Element>() {

            @Override
            public int compare(final Element o1, final Element o2) {
                return o1.getVolgnummer().getWaarde() - o2.getVolgnummer().getWaarde();
            }
        });
        return elementLijst;
    }

    /**
     * Haalt de stamgegeven records uit de enumeratie en zet deze in het resultaat.
     * 
     * @param stamgegevenKlasse enumeratie klasse van het stamgegeven
     * @param resultaat het resultaat waar de records in moeten.
     */
    private void bepaalEnZetEnumeratieStamgegevensInResultaat(
            final Class<? extends SynchroniseerbaarStamgegeven> stamgegevenKlasse,
            final SynchronisatieResultaat resultaat)
    {
        final List<SynchroniseerbaarStamgegeven> stamgegeven = new ArrayList<>();
        for (final SynchroniseerbaarStamgegeven synchroniseerbaarStamgegeven : stamgegevenKlasse.getEnumConstants()) {
            // Skip de 'DUMMY' waardes.
            if (((Enum<?>) synchroniseerbaarStamgegeven).ordinal() > 0) {
                stamgegeven.add(synchroniseerbaarStamgegeven);
            }
        }
        resultaat.setStamgegeven(stamgegeven);
    }
}
