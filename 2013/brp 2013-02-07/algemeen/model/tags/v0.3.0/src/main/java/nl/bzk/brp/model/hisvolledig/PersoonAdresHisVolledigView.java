/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static nl.bzk.brp.model.hisvolledig.MaterieleHistoriePredikaat.geldigOp;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.hisvolledig.kern.PersoonAdresHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonAdres;
import nl.bzk.brp.model.logisch.kern.PersoonAdresStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import org.apache.commons.collections.CollectionUtils;

/**
 * Proxy voor {@link PersoonAdresHisVolledig} die de informatie terug geeft die op een
 * peildatum bekend is van het adres.
 */
public class PersoonAdresHisVolledigView implements PersoonAdres {

    private final Persoon persoon;
    private final PersoonAdresHisVolledig adres;

    private final MaterieleHistoriePredikaat predikaat;

    /**
     * Proxy een {@link PersoonAdresHisVolledig} instantie met de gegeven formeelPeilmoment en materieelPeilmoment.
     * De formeelPeilmoment is het moment waarop de doorsnede door de historie van een PersoonAdres wordt gemaakt.
     * De materieelPeilmoment is de datum waarnaar we kijken vanaf de formeelPeilmoment.
     *
     * @param persoon             de persoon waar dit adres bij hoort
     * @param adres               de persoonAdresHisVolledig instantie die de informatie bevat
     * @param formeelPeilmoment   de formeelPeilmoment waarop informatie wordt opgehaald
     * @param materieelPeilmoment de datum waarnaar wordt gekeken
     */
    public PersoonAdresHisVolledigView(final Persoon persoon, final PersoonAdresHisVolledig adres,
                                       final DatumTijd formeelPeilmoment,
                                       final Datum materieelPeilmoment)
    {
        this.persoon = persoon;
        this.adres = adres;
        this.predikaat = geldigOp(formeelPeilmoment, materieelPeilmoment);
    }

    @Override
    public Persoon getPersoon() {
        return this.persoon;
    }

    @Override
    public PersoonAdresStandaardGroep getStandaard() {
        return (HisPersoonAdresModel) CollectionUtils.find(adres.getHisPersoonAdresLijst(), predikaat);
    }
}
