/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.groep.operationeel.historisch.PersoonAdresHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeboorteHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsaanduidingHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonGeslachtsnaamcomponentHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonIdentificatienummersHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.PersoonOverlijdenHisModel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.util.ExternalReaderService;

/**
 */
public class PersoonHisModel extends VersionedHis implements Externalizable {

    @JsonProperty
    private final int id;

	public PersoonHisModel(final int id) {
		this.id = id;
        this.betrokkenheden = new ArrayList<BetrokkenheidHisModel>();
        this.geslachtsnaamcomponent = new ArrayList<PersoonGeslachtsnaamcomponentHisModel>();
        this.adressen = new ArrayList<PersoonAdresHisModel>();
	}

	public PersoonHisModel() {
		this(-1);
	}

	public int getId() {
		return id;
	}

    @JsonProperty
	private SoortPersoon soort;

    @JsonProperty
	private List<PersoonGeslachtsaanduidingHisModel> geslachtsaanduiding;

    @JsonProperty
    private List<PersoonIdentificatienummersHisModel> identificatienummers;

    @JsonProperty
    private List<PersoonGeslachtsnaamcomponentHisModel> geslachtsnaamcomponent;

    @JsonProperty
	private List<PersoonGeboorteHisModel> geboorte;

    @JsonProperty
    private List<PersoonOverlijdenHisModel> overlijden;

    @JsonProperty
	private List<PersoonAdresHisModel> adressen;

    @JsonProperty
    private List<BetrokkenheidHisModel> betrokkenheden;

    // getters and setters
    public List<BetrokkenheidHisModel> getBetrokkenheden() {
        if(this.betrokkenheden == null) {
            this.betrokkenheden = new ArrayList<BetrokkenheidHisModel>();
        }
        return betrokkenheden;
    }

    public void setBetrokkenheden(final List<BetrokkenheidHisModel> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    public SoortPersoon getSoort() {
		return soort;
	}

	public void setSoort(final SoortPersoon soort) {
		this.soort = soort;
	}

	public List<PersoonGeslachtsaanduidingHisModel> getGeslachtsaanduiding() {
		return geslachtsaanduiding;
	}

	public void setGeslachtsaanduiding(final List<PersoonGeslachtsaanduidingHisModel> geslachtsaanduiding) {
		this.geslachtsaanduiding = geslachtsaanduiding;
	}

	public List<PersoonGeboorteHisModel> getGeboorte() {
		return geboorte;
	}

	public void setGeboorte(final List<PersoonGeboorteHisModel> geboorte) {
		this.geboorte = geboorte;
	}

	public List<PersoonAdresHisModel> getAdressen() {
		return adressen;
	}

	public void setAdressen(final List<PersoonAdresHisModel> adressen) {
		this.adressen = adressen;
	}

    public List<PersoonOverlijdenHisModel> getOverlijden() {
        return overlijden;
    }

    public void setOverlijden(final List<PersoonOverlijdenHisModel> overlijden) {
        this.overlijden = overlijden;
    }

    /**
     * TODO {@link #overlijden}, {@link #betrokkenheden}
     * @param out
     * @throws IOException
     */
    @Override
	public void writeExternal(final ObjectOutput out) throws IOException {
		out.writeObject(soort.toString());

		if (geslachtsaanduiding != null) {
			out.writeObject(getGeslachtsaanduiding().size());
			for (PersoonGeslachtsaanduidingHisModel geslaanduiding : geslachtsaanduiding) {
				geslaanduiding.writeExternal(out);
			}
		} else {
			out.writeObject(null);
		}

        if(geboorte != null){
            out.writeObject(geboorte.size());
            for(PersoonGeboorteHisModel geb : geboorte){
                geb.writeExternal(out);
            }
        } else {
            out.writeObject(null);
        }

		if (adressen != null) {
			out.writeObject(adressen.size());
			for (PersoonAdresHisModel adres : adressen) {
				adres.writeExternal(out);
			}
		} else {
			out.writeObject(null);
		}

		if (identificatienummers != null) {
			out.writeObject(getIdentificatienummers().size());
			for (PersoonIdentificatienummersHisModel identificatienummer : identificatienummers) {
				identificatienummer.writeExternal(out);
			}
		} else {
			out.writeObject(null);
		}

		if (geslachtsnaamcomponent != null) {
            out.writeObject(geslachtsnaamcomponent.size());
			for(PersoonGeslachtsnaamcomponentHisModel geslachtsnaamcomp : geslachtsnaamcomponent){
                geslachtsnaamcomp.writeExternal(out);
            }
		} else {
			out.writeObject(null);
		}
	}

    /**
     * TODO {@link #overlijden}, {@link #betrokkenheden}
     * @param in
     * @throws IOException
     * @throws ClassNotFoundException
     */
	@Override
	public void readExternal(final ObjectInput in) throws IOException, ClassNotFoundException {
		soort = SoortPersoon.valueOf(ExternalReaderService.leesString(in));

		Integer aantalGeslAand = ExternalReaderService.leesInteger(in);
		if (aantalGeslAand != null) {
			geslachtsaanduiding = new ArrayList<PersoonGeslachtsaanduidingHisModel>();
			for (int i = 0; i < aantalGeslAand; i++) {
                PersoonGeslachtsaanduidingHisModel persoonGeslachtsaanduidingHisModel = new PersoonGeslachtsaanduidingHisModel();
                persoonGeslachtsaanduidingHisModel.readExternal(in);
				geslachtsaanduiding.add(persoonGeslachtsaanduidingHisModel);
			}
		}

        Integer aantalGeboortes = (Integer) in.readObject();
        if(aantalGeboortes != null){
            geboorte = new ArrayList<PersoonGeboorteHisModel>();
            for(int i=0; i < aantalGeboortes; i++){
                PersoonGeboorteHisModel geb = new PersoonGeboorteHisModel();
                geb.readExternal(in);
                geboorte.add(geb);
            }
        }

		Integer aantalAdressen = ExternalReaderService.leesInteger(in);
		if (aantalAdressen != null) {
			adressen = new ArrayList<PersoonAdresHisModel>();
			for (int i = 0; i < aantalAdressen; i++) {
				PersoonAdresHisModel adres = new PersoonAdresHisModel();
				adres.readExternal(in);
				adressen.add(adres);
			}
		}

		Integer aantalIdentificatienummers = ExternalReaderService.leesInteger(in);
		if (aantalIdentificatienummers != null) {
			identificatienummers = new ArrayList<PersoonIdentificatienummersHisModel>();
			for (int i = 0; i < aantalIdentificatienummers; i++) {
				PersoonIdentificatienummersHisModel identificatienummer = new PersoonIdentificatienummersHisModel();
				identificatienummer.readExternal(in);
				identificatienummers.add(identificatienummer);
			}
		}

        Integer aantalGeslachtsNaamComponent = ExternalReaderService.leesInteger(in);
        if(aantalGeslachtsNaamComponent != null){
            geslachtsnaamcomponent = new ArrayList<PersoonGeslachtsnaamcomponentHisModel>();
            for (int i = 0; i < aantalGeslachtsNaamComponent; i++) {
            	PersoonGeslachtsnaamcomponentHisModel geslnaam = new PersoonGeslachtsnaamcomponentHisModel();
            	geslnaam.readExternal(in);
            	geslachtsnaamcomponent.add(geslnaam);
            }
        }

	}

	public List<PersoonIdentificatienummersHisModel> getIdentificatienummers() {
		return identificatienummers;
	}

	public void setIdentificatienummers(final List<PersoonIdentificatienummersHisModel> identificatienummers) {
		this.identificatienummers = identificatienummers;
	}

	public List<PersoonGeslachtsnaamcomponentHisModel> getGeslachtsnaamcomponent() {
        if( geslachtsnaamcomponent == null) {
            geslachtsnaamcomponent = new ArrayList<PersoonGeslachtsnaamcomponentHisModel>();
        }
		return geslachtsnaamcomponent;
	}

	public void setGeslachtsnaamcomponent(final List<PersoonGeslachtsnaamcomponentHisModel> geslachtsnaamcomponent) {
		this.geslachtsnaamcomponent = geslachtsnaamcomponent;
	}
}
