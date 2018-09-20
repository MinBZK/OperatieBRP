/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.factories;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.PartijID;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRelatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.BetrokkenheidIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PartijIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.RelatieIdentiteit;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Partij;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;
import nl.bzk.brp.pocmotor.util.DatumEnTijdUtil;

public class BindingFactory {

    public static Partij partijVanPartijCode(String partijCode) {
        Partij partij = null;
        if (partijCode != null) {
            PartijID partijId = new PartijID();
            partijId.setWaarde(Integer.valueOf(partijCode));

            PartijIdentiteit partijIdentiteit = new PartijIdentiteit();
            partijIdentiteit.setID(partijId);

            partij = new Partij();
            partij.setIdentiteit(partijIdentiteit);
        }
        return partij;
    }

    public static DatumTijd datumTijdVanString(final String datumTijdString) {
        DatumTijd datumTijd = new DatumTijd();
        datumTijd.setWaarde(DatumEnTijdUtil.zetDatumEnTijdOmNaarDate(datumTijdString));
        return datumTijd;
    }

    public static Datum datumVanString(final String datumString) {
        Datum datum = new Datum();
        datum.setWaarde(Integer.decode(datumString));
        return datum;
    }

    public static Relatie familieRechtelijkeBetrekkingRelatie() {
        Relatie relatie = new Relatie();
        relatie.setIdentiteit(new RelatieIdentiteit());
        relatie.getIdentiteit().setSoort(SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);
        return relatie;
    }

    public static List newListInstance() {
        return new ArrayList();
    }

    public static Set newSetInstance() {
        //Let op: Een gesorteerde set:
        return new TreeSet();
    }

    public static Betrokkenheid kindBetrokkenHeid() {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setIdentiteit(new BetrokkenheidIdentiteit());
        betrokkenheid.getIdentiteit().setRol(SoortBetrokkenheid.KIND);
        return betrokkenheid;
    }

    public static Betrokkenheid ouderBetrokkenHeid() {
        Betrokkenheid betrokkenheid = new Betrokkenheid();
        betrokkenheid.setIdentiteit(new BetrokkenheidIdentiteit());
        betrokkenheid.getIdentiteit().setRol(SoortBetrokkenheid.OUDER);
        return betrokkenheid;
    }

}
