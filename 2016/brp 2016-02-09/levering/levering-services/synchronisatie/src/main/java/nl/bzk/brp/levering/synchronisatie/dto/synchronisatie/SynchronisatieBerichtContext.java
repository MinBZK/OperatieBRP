/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.dto.synchronisatie;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContextBasis;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;

/**
 * Context voor de synchronisatie stappen.
 */
public class SynchronisatieBerichtContext extends AbstractBerichtContextBasis {

    private VolledigBericht volledigBericht;

    private Leveringinformatie leveringAutorisatie;

    private Leveringsautorisatie leveringsautorisatie;

    private Dienst relevanteDienst;

    private PersoonHisVolledig persoonHisVolledig;

    private String xmlBericht;

    private Map<Integer, Map<String, List<Attribuut>>> bijgehoudenPersonenAttributenMap;

    private Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap;

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param afzender            de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public SynchronisatieBerichtContext(final BerichtenIds berichtIds,
        final Partij afzender,
        final String berichtReferentieNr,
        final CommunicatieIdMap identificeerbareObj)
    {
        super(berichtIds, afzender, berichtReferentieNr, identificeerbareObj);
    }

    public VolledigBericht getVolledigBericht() {
        return volledigBericht;
    }

    public final void setVolledigBericht(final VolledigBericht volledigBericht) {
        this.volledigBericht = volledigBericht;
    }

    public final Leveringinformatie getLeveringinformatie() {
        return leveringAutorisatie;
    }

    public final void setLeveringinformatie(final Leveringinformatie leveringAutorisatie) {
        this.leveringAutorisatie = leveringAutorisatie;
    }

    public final PersoonHisVolledig getPersoonHisVolledig() {
        return persoonHisVolledig;
    }

    public final void setPersoonHisVolledig(final PersoonHisVolledig persoonHisVolledig) {
        this.persoonHisVolledig = persoonHisVolledig;
    }

    public final String getXmlBericht() {
        return xmlBericht;
    }

    public final void setXmlBericht(final String xmlBericht) {
        this.xmlBericht = xmlBericht;
    }

    public final Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    public final void setLeveringsautorisatie(final Leveringsautorisatie leveringsautorisatie) {
        this.leveringsautorisatie = leveringsautorisatie;
    }

    public final Dienst getRelevanteDienst() {
        return relevanteDienst;
    }

    public final void setRelevanteDienst(final Dienst relevanteDienst) {
        this.relevanteDienst = relevanteDienst;
    }

    public final Map<Integer, Map<String, List<Attribuut>>> getBijgehoudenPersonenAttributenMap() {
        return bijgehoudenPersonenAttributenMap;
    }

    public final void setBijgehoudenPersonenAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> bijgehoudenPersonenAttributenMap) {
        this.bijgehoudenPersonenAttributenMap = bijgehoudenPersonenAttributenMap;
    }

    public final Map<Integer, Map<Integer, List<Attribuut>>> getPersoonOnderzoekenMap() {
        return persoonOnderzoekenMap;
    }

    public final void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap) {
        this.persoonOnderzoekenMap = persoonOnderzoekenMap;
    }

    @Override
    public final Collection<Integer> getLockingIds() {
        return null;
    }

    @Override
    public final LockingMode getLockingMode() {
        return null;
    }


}
