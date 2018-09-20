/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.locking.LockingMode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.webservice.business.stappen.AbstractBerichtContext;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;


/**
 * Context voor de stappen van de berichtverwerking in de webservice Onderhoud Afnemerindicaties.
 */
public class OnderhoudAfnemerindicatiesBerichtContextImpl extends AbstractBerichtContext
    implements OnderhoudAfnemerindicatiesBerichtContext
{

    private VolledigBericht                             volledigBericht;
    private Leveringinformatie                          leveringAutorisatie;
    private PersoonHisVolledig                          persoonHisVolledig;
    private String                                      xmlBericht;
    private Partij                                      zendendePartij;
    private DatumEvtDeelsOnbekendAttribuut              datumAanvangMaterielePeriode;
    private Map<Integer, Map<String, List<Attribuut>>>  bijgehoudenPersonenAttributenMap;
    private Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap;

    /**
     * Constructor die de basis van de bericht context direct initialiseert.
     *
     * @param berichtIds          de ids van inkomende en daaraan gerelateerd uitgaande bericht.
     * @param afzender            de partij die de bericht verwerking heeft aangeroepen.
     * @param berichtReferentieNr Referentie nummer uit de stuurgegevens van het binnengekomen bericht.
     * @param identificeerbareObj map van identificeerbare objecten die zijn gevonden in het bericht.
     */
    public OnderhoudAfnemerindicatiesBerichtContextImpl(final BerichtenIds berichtIds,
        final Partij afzender,
        final String berichtReferentieNr,
        final CommunicatieIdMap identificeerbareObj)
    {
        super(berichtIds, new PartijAttribuut(afzender), berichtReferentieNr, identificeerbareObj);
    }

    @Override
    public final VolledigBericht getVolledigBericht() {
        return volledigBericht;
    }

    @Override
    public final void setVolledigBericht(final VolledigBericht volledigBericht) {
        this.volledigBericht = volledigBericht;
    }

    @Override
    public final Leveringinformatie getLeveringinformatie() {
        return leveringAutorisatie;
    }

    @Override
    public final void setLeveringinformatie(final Leveringinformatie leveringAutorisatie) {
        this.leveringAutorisatie = leveringAutorisatie;
    }

    @Override
    public final PersoonHisVolledig getPersoonHisVolledig() {
        return persoonHisVolledig;
    }

    @Override
    public final void setPersoonHisVolledig(final PersoonHisVolledig persoonHisVolledig) {
        this.persoonHisVolledig = persoonHisVolledig;
    }

    @Override
    public final String getXmlBericht() {
        return xmlBericht;
    }

    @Override
    public final void setXmlBericht(final String xmlBericht) {
        this.xmlBericht = xmlBericht;
    }

    @Override
    @Deprecated
    public final Leveringsautorisatie getLeveringautorisatie() {
        return leveringAutorisatie.getToegangLeveringsautorisatie().getLeveringsautorisatie();
    }

    @Override
    public Collection<Integer> getLockingIds() {
        return null;
    }

    @Override
    public final LockingMode getLockingMode() {
        return null;
    }

    @Override
    public final Partij getZendendePartij() {
        return zendendePartij;
    }

    @Override
    public final void setZendendePartij(final Partij zendendePartij) {
        this.zendendePartij = zendendePartij;
    }

    @Override
    public final DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    @Override
    public final void setDatumAanvangMaterielePeriode(final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode) {
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
    }

    @Override
    public final Map<Integer, Map<String, List<Attribuut>>> getBijgehoudenPersonenAttributenMap() {
        return bijgehoudenPersonenAttributenMap;
    }

    @Override
    public final void setBijgehoudenPersonenAttributenMap(final Map<Integer, Map<String, List<Attribuut>>> bijgehoudenPersonenAttributenMap) {
        this.bijgehoudenPersonenAttributenMap = bijgehoudenPersonenAttributenMap;
    }

    @Override
    public Map<Integer, Map<Integer, List<Attribuut>>> getPersoonOnderzoekenMap() {
        return persoonOnderzoekenMap;
    }

    @Override
    public void setPersoonOnderzoekenMap(final Map<Integer, Map<Integer, List<Attribuut>>> persoonOnderzoekenMap) {
        this.persoonOnderzoekenMap = persoonOnderzoekenMap;
    }
}
