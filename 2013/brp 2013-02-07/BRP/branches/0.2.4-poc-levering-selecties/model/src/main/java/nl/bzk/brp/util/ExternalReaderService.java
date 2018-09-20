/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.bzk.brp.model.attribuuttype.Administratienummer;
import nl.bzk.brp.model.attribuuttype.Adresregel;
import nl.bzk.brp.model.attribuuttype.Adresseerbaarobject;
import nl.bzk.brp.model.attribuuttype.AfgekorteNaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.attribuuttype.Gemeentedeel;
import nl.bzk.brp.model.attribuuttype.Huisletter;
import nl.bzk.brp.model.attribuuttype.Huisnummer;
import nl.bzk.brp.model.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.model.attribuuttype.IdentificatiecodeNummeraanduiding;
import nl.bzk.brp.model.attribuuttype.Landcode;
import nl.bzk.brp.model.attribuuttype.LocatieOmschrijving;
import nl.bzk.brp.model.attribuuttype.LocatieTovAdres;
import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.PlaatsCode;
import nl.bzk.brp.model.attribuuttype.Postcode;
import nl.bzk.brp.model.attribuuttype.RedenWijzigingAdresCode;
import nl.bzk.brp.model.basis.AttribuutType;

public class ExternalReaderService {

	public ExternalReaderService() {
		// Default constructor
	}

	public static Integer leesInteger(final ObjectInput in) throws ClassNotFoundException, IOException {
		return (Integer) in.readObject();
	}

	public static Short leesShort(final ObjectInput in) throws ClassNotFoundException, IOException {
		return (Short) in.readObject();
	}

	public static String leesString(final ObjectInput in) throws ClassNotFoundException, IOException {
		return (String) in.readObject();
	}

	public static Long leesLong(final ObjectInput in) throws ClassNotFoundException, IOException {
		return (Long) in.readObject();
	}

	public static List leesList(final ObjectInput in) throws ClassNotFoundException, IOException {
		return (ArrayList) in.readObject();
	}

	public static Externalizable leesNullableObject(final ObjectInput in, final Class type) throws IOException {
		try {
			if (in.readBoolean()) {
				Externalizable object = (Externalizable) type.newInstance();
				object.readExternal(in);
				return object;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static AttribuutType leesWaarde(final ObjectInput in, final Class type) throws IOException,
			ClassNotFoundException {
		AttribuutType result = null;

		if (type.equals(Datum.class)) {
			Integer waarde = (Integer) in.readObject();
			if (waarde != null) {
				result = new Datum(waarde);
			}
		} else if (type.equals(Naam.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Naam(waarde);
			}
		} else if (type.equals(DatumTijd.class)) {
			Long longWaarde = (Long) in.readObject();
			if (longWaarde != null) {
				result = new DatumTijd(new Date(longWaarde));
			}
		} else if (type.equals(Gemeentecode.class)) {
			Short shortWaarde = (Short) in.readObject();
			if (shortWaarde != null) {
				result = new Gemeentecode(shortWaarde);
			}
		} else if (type.equals(Naam.class)) {
			String stringWaarde = (String) in.readObject();
			if (stringWaarde != null) {
				result = new Naam(stringWaarde);
			}
		} else if (type.equals(NaamOpenbareRuimte.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new NaamOpenbareRuimte(waarde);
			}
		} else if (type.equals(AfgekorteNaamOpenbareRuimte.class)) {
			String afgekorteNaamOpenbareRuimteWaarde = (String) in.readObject();
			if (afgekorteNaamOpenbareRuimteWaarde != null) {
				result = new AfgekorteNaamOpenbareRuimte(afgekorteNaamOpenbareRuimteWaarde);
			}
		} else if (type.equals(Gemeentedeel.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Gemeentedeel(waarde);
			}
		} else if (type.equals(Huisletter.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Huisletter(waarde);
			}
		} else if (type.equals(Postcode.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Postcode(waarde);
			}
		} else if (type.equals(Huisnummertoevoeging.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Huisnummertoevoeging(waarde);
			}
		} else if (type.equals(LocatieTovAdres.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new LocatieTovAdres(waarde);
			}
		} else if (type.equals(LocatieOmschrijving.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new LocatieOmschrijving(waarde);
			}
		} else if (type.equals(Adresregel.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Adresregel(waarde);
			}
		} else if (type.equals(RedenWijzigingAdresCode.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new RedenWijzigingAdresCode(waarde);
			}
		} else if (type.equals(Landcode.class)) {
			Short waarde = (Short) in.readObject();
			if (waarde != null) {
				result = new Landcode(waarde);
			}
		} else if (type.equals(PlaatsCode.class)) {
			Short waarde = (Short) in.readObject();
			if (waarde != null) {
				result = new PlaatsCode(waarde);
			}
		} else if (type.equals(Aktenummer.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Aktenummer(waarde);
			}
		} else if (type.equals(DocumentIdentificatie.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new DocumentIdentificatie(waarde);
			}
		} else if (type.equals(DocumentOmschrijving.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new DocumentOmschrijving(waarde);
			}

		} else if (type.equals(Adresseerbaarobject.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new Adresseerbaarobject(waarde);
			}
		} else if (type.equals(IdentificatiecodeNummeraanduiding.class)) {
			String waarde = (String) in.readObject();
			if (waarde != null) {
				result = new IdentificatiecodeNummeraanduiding(waarde);
			}
		} else if (type.equals(Huisnummer.class)) {
			Integer waarde = (Integer) in.readObject();
			if (waarde != null) {
				result = new Huisnummer(waarde);
			}
		}  else if (type.equals(BuitenlandsePlaats.class)) {
            String waarde = (String) in.readObject();
            if (waarde != null) {
                result = new BuitenlandsePlaats(waarde);
            }
        }  else if (type.equals(BuitenlandseRegio.class)) {
            String waarde = (String) in.readObject();
            if (waarde != null) {
                result = new BuitenlandseRegio(waarde);
            }
        }  else if (type.equals(Omschrijving.class)) {
            String waarde = (String) in.readObject();
            if (waarde != null) {
                result = new Omschrijving(waarde);
            }
        } else if(type.equals(Administratienummer.class)) {
        	Long waarde = (Long) in.readObject();
            if (waarde != null) {
                result = new Administratienummer(waarde);
            }
        } else if(type.equals(Burgerservicenummer.class)) {
        	String waarde = (String) in.readObject();
            if (waarde != null) {
                result = new Burgerservicenummer(waarde);
            }
        } else {
			throw new NullPointerException("Unknown class type: " + type);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public static Enum<?> leesEnum(final ObjectInput in, final Class<? extends Enum> enumType) throws IOException {
		String enumStringWaarde = null;
		try {
			enumStringWaarde = (String) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		if (enumStringWaarde != null) {
			return Enum.valueOf(enumType, enumStringWaarde);
		}
		return null;
	}

}
