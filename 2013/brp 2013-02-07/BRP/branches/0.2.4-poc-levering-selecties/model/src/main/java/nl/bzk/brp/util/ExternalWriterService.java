/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.basis.AttribuutType;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAanschrijvingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonBijhoudingsgemeenteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOpschortingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOverlijdenHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonSamengesteldeNaamHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonVoornaamHisModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonAdresModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonVoornaamModel;

public class ExternalWriterService {

	public ExternalWriterService() {
		// Default constructor
	}

	public List<PersoonIdentificatienummersHisModel> schoonOpIdentificatienummers(final List<PersoonIdentificatienummersHisModel> modellen) {
		ArrayList<PersoonIdentificatienummersHisModel> opgeschoondLijst = new ArrayList<PersoonIdentificatienummersHisModel>();
		for (PersoonIdentificatienummersHisModel model : modellen) {
			PersoonIdentificatienummersHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonGeslachtsaanduidingHisModel> schoonOpGeslachtsaanduiding(final List<PersoonGeslachtsaanduidingHisModel> modellen) {
		ArrayList<PersoonGeslachtsaanduidingHisModel> opgeschoondLijst = new ArrayList<PersoonGeslachtsaanduidingHisModel>();
		for (PersoonGeslachtsaanduidingHisModel model : modellen) {
			PersoonGeslachtsaanduidingHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonSamengesteldeNaamHisModel> schoonOpSamengesteldeNaam(final List<PersoonSamengesteldeNaamHisModel> modellen) {
		ArrayList<PersoonSamengesteldeNaamHisModel> opgeschoondLijst = new ArrayList<PersoonSamengesteldeNaamHisModel>();
		for (PersoonSamengesteldeNaamHisModel model : modellen) {
			PersoonSamengesteldeNaamHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonAanschrijvingHisModel> schoonOpAanschrijving(final List<PersoonAanschrijvingHisModel> modellen) {
		ArrayList<PersoonAanschrijvingHisModel> opgeschoondLijst = new ArrayList<PersoonAanschrijvingHisModel>();
		for (PersoonAanschrijvingHisModel model : modellen) {
			PersoonAanschrijvingHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonGeboorteHisModel> schoonOpGeboorte(final List<PersoonGeboorteHisModel> modellen) {
		ArrayList<PersoonGeboorteHisModel> opgeschoondLijst = new ArrayList<PersoonGeboorteHisModel>();
		for (PersoonGeboorteHisModel model : modellen) {
			PersoonGeboorteHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonOpschortingHisModel> schoonOpOpschorting(final List<PersoonOpschortingHisModel> modellen) {
		ArrayList<PersoonOpschortingHisModel> opgeschoondLijst = new ArrayList<PersoonOpschortingHisModel>();
		for (PersoonOpschortingHisModel model : modellen) {
			PersoonOpschortingHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonBijhoudingsgemeenteHisModel> schoonOpBijhoudenGemeente(final List<PersoonBijhoudingsgemeenteHisModel> modellen) {
		ArrayList<PersoonBijhoudingsgemeenteHisModel> opgeschoondLijst = new ArrayList<PersoonBijhoudingsgemeenteHisModel>();
		for (PersoonBijhoudingsgemeenteHisModel model : modellen) {
			PersoonBijhoudingsgemeenteHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonOverlijdenHisModel> schoonOpOverlijden(final List<PersoonOverlijdenHisModel> modellen) {
		ArrayList<PersoonOverlijdenHisModel> opgeschoondLijst = new ArrayList<PersoonOverlijdenHisModel>();
		for (PersoonOverlijdenHisModel model : modellen) {
			PersoonOverlijdenHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoon(null);
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	public List<PersoonAdresHisModel> schoonOpAdressen(final List<PersoonAdresHisModel> modellen) {
		ArrayList<PersoonAdresHisModel> opgeschoondLijst = new ArrayList<PersoonAdresHisModel>();
		for (PersoonAdresHisModel model : modellen) {
			PersoonAdresHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoonAdres(schoonOpPersoonAdres(opgeschoond.getPersoonAdres()));
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	private PersoonAdresModel schoonOpPersoonAdres(final PersoonAdresModel model) {
		PersoonAdresModel opgeschoond = model.kopieer();
		opgeschoond.setPersoon(null);
		return opgeschoond;
	}

	public List<PersoonVoornaamHisModel> schoonOpPersoonVoornaam(final List<PersoonVoornaamHisModel> modellen) {
		ArrayList<PersoonVoornaamHisModel> opgeschoondLijst = new ArrayList<PersoonVoornaamHisModel>();
		for (PersoonVoornaamHisModel model : modellen) {
			PersoonVoornaamHisModel opgeschoond = model.kopieer();
			opgeschoond.setPersoonVoornaam(schoonOpPersoonVoornaam(opgeschoond.getPersoonVoornaam()));
			opgeschoondLijst.add(opgeschoond);
		}
		return opgeschoondLijst;
	}

	private PersoonVoornaamModel schoonOpPersoonVoornaam(final PersoonVoornaamModel model) {
		PersoonVoornaamModel opgeschoond = model.kopieer();
		opgeschoond.setPersoon(null);
		return opgeschoond;
	}

    public static void schrijfNullableObject(final ObjectOutput out, final Externalizable obj) throws IOException {
        if (obj == null) {
            out.writeBoolean(false);
        } else {
            out.writeBoolean(true);
            obj.writeExternal(out);
        }
    }

    public static void schrijfWaarde(final ObjectOutput out, final AttribuutType attribuut)
            throws IOException {
        if(attribuut != null){
            out.writeObject((Object) attribuut.getWaarde());
        } else {
            out.writeObject(null);
        }
    }

    public static void schrijfEnum(final ObjectOutput out, final Enum enumWaarde) throws IOException {
        if(enumWaarde != null){
            out.writeObject(enumWaarde.toString());
        } else {
            out.writeObject(null);
        }
    }
}
