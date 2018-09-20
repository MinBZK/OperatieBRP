/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;


/**
 * POJO voor het bericht van de BRP.
 */
public abstract class BerichtRequest {

    private BerichtKenmerken kenmerken;

    private Verwerking verwerking;

    private List<Melding>    meldingen;

    public BerichtRequest() {
    }

    public BerichtRequest(final BerichtKenmerken kenmerken) {
        this.kenmerken = kenmerken;
    }

    public BerichtKenmerken getKenmerken() {
        return kenmerken;
    }

    public void setKenmerken(final BerichtKenmerken kenmerken) {
        this.kenmerken = kenmerken;
    }

    public Verwerking getVerwerking() {
        return verwerking;
    }

    public void setVerwerking(final Verwerking verwerking) {
        this.verwerking = verwerking;
    }

    public List<Melding> getMeldingen() {
        return meldingen;
    }

    public void setMeldingen(final List<Melding> meldingen) {
        this.meldingen = meldingen;
    }

    @JsonIgnore
    public abstract OndersteundeBijhoudingsTypes getSoortBijhouding();

    @JsonIgnore
    public void valideer() {
        if (getKenmerken() == null) {
            throw new IllegalStateException("Kenmerken ontbreken");
        }
        if (getKenmerken().getVerzendendePartij() == null) {
            throw new IllegalStateException("Verzendende partij ontbreekt");
        }
        if (getKenmerken().getBurgerZakenModuleNaam() == null) {
            throw new IllegalStateException("Verzendende partij ontbreekt");
        }
        if (getVerwerking() == null) {
            throw new IllegalStateException("Verwerkingstatus ontbreekt");
        }
        if (getVerwerking().getVerwerkingsmoment() == null) {
            throw new IllegalStateException("Verwerkingmoment ontbreekt");
        }
        if (getVerwerking().getStatus() == null) {
            throw new IllegalStateException("Verwerkingmoment ontbreekt");
        }
    }

    @JsonIgnore
    public abstract String creeerBerichtTekst();

    @JsonIgnore
    public String creeerDetailsTekst() {
        String tekst;

        if (getVerwerking().getStatus() == VerwerkingStatus.GESLAAGD) {
            tekst = creeerDetailTekstVoorGevolg();
        } else {
            tekst = "";
        }

        if (getMeldingen() != null && getMeldingen().size() != 0) {
            if (!tekst.equalsIgnoreCase("")) {
                tekst += "\n";
            }
            tekst += creeerDetailTekstVoorMeldingen(getMeldingen());
        }

        return tekst;
    }

    protected String creeerGeneriekeTekstVoorMeldingen(final List<Melding> meldingen) {
        String tekst;
        if (meldingen == null || meldingen.size() == 0) {
            tekst = "";
        } else {
            tekst = " " + meldingen.size();
            if (meldingen.size() == 1) {
                tekst += " melding.";
            } else {
                tekst += " meldingen.";
            }
        }
        return tekst;
    }

    protected abstract String creeerDetailTekstVoorGevolg();

    protected String creeerPrevalidatieTekst(final boolean prevalidatie) {
        String tekst;
        if (prevalidatie) {
            tekst = " prevalidatie";
        } else {
            tekst = "";
        }
        return tekst;
    }

    protected String creeerDetailTekstVoorMeldingen(final List<Melding> meldingen) {
        String tekst = "";
        if (meldingen != null && meldingen.size() != 0) {
            for (Melding melding : meldingen) {
                if (!tekst.equalsIgnoreCase("")) {
                    tekst += "\n";
                }
                tekst += melding.getSoort().getNaam() + ": " + melding.getTekst();
            }
        }
        return tekst;
    }

}
