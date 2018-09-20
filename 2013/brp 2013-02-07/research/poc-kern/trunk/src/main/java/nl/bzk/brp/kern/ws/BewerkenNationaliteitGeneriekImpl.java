/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.kern.ws;

import java.util.Date;

import javax.inject.Inject;
import javax.jws.WebService;

import nl.brp.actie.toevoegennationaliteit.ToevoegenNationaliteit;
import nl.brp.actie.verwijderennationaliteit.VerwijderenNationaliteit;
import nl.brp.basis.antwoord.StandaardAntwoord;
import nl.brp.contract.bewerkennationaliteitgeneriek.BewerkenNationaliteitGeneriekPortType;

import org.apache.commons.logging.Log;
import org.springframework.stereotype.Service;

@Service("BewerkenNationaliteitGeneriek")
@WebService(
		serviceName = "BewerkenNationaliteitGeneriekService",
		portName = "BewerkenNationaliteitGeneriekPort",
		targetNamespace = "http://www.brp.nl/contract/BewerkenNationaliteitGeneriek/",
		wsdlLocation = "WEB-INF/classes/wsdl/BewerkenNationaliteitGeneriek.wsdl",
		endpointInterface = "nl.brp.contract.bewerkennationaliteitgeneriek.BewerkenNationaliteitGeneriekPortType"
		)
public class BewerkenNationaliteitGeneriekImpl implements BewerkenNationaliteitGeneriekPortType
	{
	@Inject
	Log log;
	
	@Override
	public StandaardAntwoord verwijderenNationaliteitGeneriek(VerwijderenNationaliteit parameters)
		{
		log.info("verwijderenNationaliteitGeneriek()");
		return null;
		}

	private static volatile long lastPrint = System.currentTimeMillis();
	private static volatile long counter = 0;
	
	private static Long bull = new Long(100);
	
	private final void count()
		{
		counter++;
		long newTime = System.currentTimeMillis();
		
		synchronized (bull)
			{
			if ((newTime - lastPrint ) > 5000)
				{
				lastPrint = newTime;
				log.debug("Average: " + (counter/5) + " requests/second");
				counter = 0;
				}
			}
		}
	
	@Override
	public StandaardAntwoord toevoegenNationaliteitGeneriek(ToevoegenNationaliteit parameters)
		{
		//log.info("toevoegenNationaliteitGeneriek()");
		
		count();
		
		StandaardAntwoord standaardAntwoord = new nl.brp.basis.antwoord.ObjectFactory().createStandaardAntwoord();
		
		standaardAntwoord.setActieId(104);
		standaardAntwoord.setAntwoordcode("Mijn CODE");
		standaardAntwoord.setMelding("Allesh OK " + new Date());
		// TODO Auto-generated method stub
		return standaardAntwoord;
		}

	}
