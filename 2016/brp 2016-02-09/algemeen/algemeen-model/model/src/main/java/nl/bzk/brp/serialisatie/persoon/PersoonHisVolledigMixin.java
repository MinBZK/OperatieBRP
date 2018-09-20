/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Set;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;


/**
 * Klasse die gebruikt wordt voor het (de)serializeren van historieModel objecten. Gebruikt in de PersoonHisVolledig serializer.
 */
@SuppressWarnings("unused")
@JsonPropertyOrder(value = { "administratieveHandelingen", "iD", "hisPersoonAfgeleidAdministratiefLijst" }, alphabetic = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "iD", scope = PersoonHisVolledigImpl.class)
public abstract class PersoonHisVolledigMixin implements PersoonHisVolledig {

    @JsonIgnore
    private Set<BetrokkenheidHisVolledigImpl> betrokkenheden;

    @JsonIgnore
    private Set<PersoonOnderzoekHisVolledigImpl> onderzoeken;

}
