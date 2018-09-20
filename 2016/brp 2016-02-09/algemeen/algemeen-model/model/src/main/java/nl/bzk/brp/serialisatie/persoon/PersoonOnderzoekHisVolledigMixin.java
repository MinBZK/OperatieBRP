/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import nl.bzk.brp.model.hisvolledig.impl.kern.OnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonOnderzoekHisVolledig;


/**
 * Klasse die gebruikt wordt voor het (de)serializeren van historieModel objecten.
 */
@SuppressWarnings("unused")
@JsonPropertyOrder(value = { "persoon", "onderzoek" }, alphabetic = true)
public abstract class PersoonOnderzoekHisVolledigMixin implements PersoonOnderzoekHisVolledig {

    @JsonProperty
    @JsonFilter(IdPropertyFilterProvider.IdPropertyFilter.NAAM)
    private PersoonHisVolledigImpl persoon;

    @JsonProperty
    private OnderzoekHisVolledigImpl onderzoek;

}
