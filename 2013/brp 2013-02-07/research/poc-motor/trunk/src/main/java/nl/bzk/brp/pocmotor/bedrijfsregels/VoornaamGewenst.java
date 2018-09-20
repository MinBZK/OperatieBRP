/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.bedrijfsregels;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortRelatie;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Relatie;

/**
 * Bedrijfsregel die controleert of de nieuwe situatie van een kind in een relatie wel voornamen heeft.
 */
public class VoornaamGewenst implements BedrijfsRegel<Relatie> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BedrijfsRegelFout executeer(final Relatie nieuweSituatie, final List<RootObject> huidigeSituatie) {
        BedrijfsRegelFout fout = null;

        List<Persoon> kinderen = haalKinderenUitRelatie(nieuweSituatie);
        for (Persoon kind : kinderen) {
            if (kind.getSamengesteldeNaam() == null || kind.getSamengesteldeNaam().getVoornamen() == null
                    || kind.getSamengesteldeNaam().getVoornamen().getWaarde() == null
                    || kind.getSamengesteldeNaam().getVoornamen().getWaarde().isEmpty())
            {
                fout = new BedrijfsRegelFout("VOORNAAM-GEWENST", BedrijfsRegelFoutErnst.WAARSCHUWING,
                        "Klopt het dat er geen voornamen zijn ingevuld");
                break;
            }
        }
        return fout;
    }

    /**
     * Haalt de persoon uit de opgegeven relatie die binnen de relatie kind zijn.
     *
     * @param relatie de relatie waaruit de kinderen moeten worden gehaald.
     * @return een lijst van kinderen zoals voorkomend in de opgegeven relatie. Indien er geen kinderen in de relatie
     *         waren, dan is deze lijst leeg.
     */
    private List<Persoon> haalKinderenUitRelatie(final Relatie relatie) {
        List<Persoon> kinderen = new ArrayList<Persoon>();
        if (relatie != null && relatie.getIdentiteit().getSoort() == SoortRelatie.FAMILIERECHTELIJKE_BETREKKING) {
            for (Betrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                if (betrokkenheid.getIdentiteit().getRol() == SoortBetrokkenheid.KIND) {
                    kinderen.add(betrokkenheid.getIdentiteit().getBetrokkene());
                }
            }
        }
        return kinderen;
    }

}
