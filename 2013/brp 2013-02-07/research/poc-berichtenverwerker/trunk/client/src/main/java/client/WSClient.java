/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package client;

import java.math.BigInteger;

import nl.brp.actie.toevoegennationaliteit.Actie;
import nl.brp.actie.toevoegennationaliteit.Pers;
import nl.brp.actie.toevoegennationaliteit.PersNation;
import nl.brp.actie.toevoegennationaliteit.ToevoegenNationaliteit;
import nl.brp.basis.NationIDE;
import nl.brp.basis.antwoord.StandaardAntwoord;
import nl.brp.contract.bewerkennationaliteitspecifiek.BewerkenNationaliteitSpecifiekPortType;
import nl.brp.contract.bewerkennationaliteitspecifiek.BewerkenNationaliteitSpecifiekService;

public class WSClient {
    public static void main (String[] args) {
        BewerkenNationaliteitSpecifiekService tnss = new BewerkenNationaliteitSpecifiekService();
        BewerkenNationaliteitSpecifiekPortType tnspt = tnss.getBewerkenNationaliteitSpecifiekPort();
        
        tns(tnspt, "Test", "Tja");
    } 
    
    private static void tns(BewerkenNationaliteitSpecifiekPortType bnspt,
			String actie, String string2)
    {
    	NationIDE ni = new NationIDE();
    	ni.setValue(new BigInteger("2"));
    	PersNation persNat = new PersNation();
    	persNat.setNation(ni);
    	Pers pers = new Pers();
    	pers.setPersNation(persNat);
    	Actie a = new Actie();
    	a.setActie(actie);
    	
    	ToevoegenNationaliteit tn = new ToevoegenNationaliteit();
    	tn.setBRPActie(a);
    	tn.setPers(pers);
		StandaardAntwoord sa = bnspt.toevoegenNationaliteitSpecifiek(tn);
		
		System.out.println("Antwoord: " + sa.getAntwoordcode() + " - " + sa.getMelding());
	}

}