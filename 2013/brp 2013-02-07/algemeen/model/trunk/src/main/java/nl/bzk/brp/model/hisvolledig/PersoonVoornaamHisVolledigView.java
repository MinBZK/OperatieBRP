/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig;

import static nl.bzk.brp.model.hisvolledig.MaterieleHistoriePredikaat.geldigOp;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijd;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonVoornaamHisVolledig;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaamStandaardGroep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonVoornaamModel;
import org.apache.commons.collections.CollectionUtils;

/**
 * Proxy voor {@link PersoonVoornaamHisVolledig} die de informatie terug geeft die op een
 * peildatum bekend is van de voornaam.
 */
public class PersoonVoornaamHisVolledigView implements PersoonVoornaam {

    private final Persoon persoon;
    private final PersoonVoornaamHisVolledig voornaam;

    private final MaterieleHistoriePredikaat predikaat;

    /**
     * Proxy een {@link PersoonVoornaamHisVolledig} instantie met de gegeven formeelPeilmoment en materieelPeilmoment.
     * De formeelPeilmoment is het moment waarop de doorsnede door de historie van een PersoonVoornaam wordt gemaakt.
     * De materieelPeilmoment is de datum waarnaar we kijken vanaf de formeelPeilmoment.
     *
     * @param persoon             de persoon waar deze voornaams bij hoort
     * @param voornaam            de persoonVoornaamHisVolledig instantie die de informatie bevat
     * @param formeelPeilmoment   de formeelPeilmoment waarop informatie wordt opgehaald
     * @param materieelPeilmoment de datum waarnaar wordt gekeken
     */
    public PersoonVoornaamHisVolledigView(final Persoon persoon, final PersoonVoornaamHisVolledig voornaam,
                                          final DatumTijd formeelPeilmoment,
                                          final Datum materieelPeilmoment)
    {
        this.persoon = persoon;
        this.voornaam = voornaam;
        this.predikaat = geldigOp(formeelPeilmoment, materieelPeilmoment);
    }

    @Override
    public Persoon getPersoon() {
        return this.persoon;
    }

    @Override
    public Volgnummer getVolgnummer() {
        HisPersoonVoornaamModel standaardGroep = vindStandaardGroep();

        return standaardGroep.getPersoonVoornaam().getVolgnummer();
    }

    @Override
    public PersoonVoornaamStandaardGroep getStandaard() {
        return vindStandaardGroep();
    }

    /**
     * Vind de geldende groep mbv een predikaat.
     *
     * @return de geldende groep
     */
    private HisPersoonVoornaamModel vindStandaardGroep() {
        return (HisPersoonVoornaamModel) CollectionUtils.find(voornaam.getHisPersoonVoornaamLijst(), predikaat);
    }
}
