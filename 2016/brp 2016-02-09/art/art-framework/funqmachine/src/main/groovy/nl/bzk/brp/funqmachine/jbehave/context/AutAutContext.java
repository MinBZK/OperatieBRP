/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.context;


import java.util.LinkedList;
import java.util.List;
import nl.bzk.brp.datataal.leveringautorisatie.DienstDsl;
import nl.bzk.brp.datataal.leveringautorisatie.DienstbundelDsl;
import nl.bzk.brp.datataal.leveringautorisatie.LeveringautorisatieDsl;
import nl.bzk.brp.datataal.leveringautorisatie.ToegangLeveringautorisatieDsl;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import org.springframework.util.Assert;

/**
 * Threadlocal voor het vasthouden van de actuele leveringautorisaties
 */
public class AutAutContext {

    private final static ThreadLocal<AutAutContext> context = new ThreadLocal<AutAutContext>() {
        @Override
        protected AutAutContext initialValue() {
            return new AutAutContext();
        }
    };

    public static AutAutContext get() {
        return context.get();
    }

    private final List<ToegangLeveringautorisatieDsl> toegangLeveringsautorisaties = new LinkedList<>();

    private VerzoekAutorisatie autorisatieVoorVerzoek;

    public boolean bestaatLeveringsautorisatie(final int berichtLeveringautorisatieId,
                                                           final String zoekLeveringautorisatieNaam) {
        Assert.notNull(zoekLeveringautorisatieNaam);
        return geefLeveringsautorisatie(berichtLeveringautorisatieId, zoekLeveringautorisatieNaam) != null;
    }

    public boolean bestaatDienst(final int berichtleveringautorisatieId,
                             final String zoekLeveringsautorisatieNaam,
                             final int berichtDienstId,
                             final String zoekDienstSoort) {
        final LeveringautorisatieDsl leveringautorisatieDsl = geefLeveringsautorisatie(berichtleveringautorisatieId, zoekLeveringsautorisatieNaam);
        if (leveringautorisatieDsl != null) {
            for (DienstbundelDsl bundel : leveringautorisatieDsl.getDienstBundels()) {
                for (DienstDsl dienstDsl : bundel.getDiensten()) {
                    if (dienstDsl.getId() == berichtDienstId && dienstDsl.getSrt().equals(zoekDienstSoort)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void voegToegangleveringsautorisatieToe(ToegangLeveringautorisatieDsl tla) {
        toegangLeveringsautorisaties.add(tla);
    }

    public void reset() {
        toegangLeveringsautorisaties.clear();
        autorisatieVoorVerzoek = null;
    }

    public void resetAutorisatieVoorVerzoek() {
        autorisatieVoorVerzoek = null;
    }

    private LeveringautorisatieDsl geefLeveringsautorisatie(final int berichtLeveringautorisatieId, final String zoekLeveringautorisatieNaam) {
        for (ToegangLeveringautorisatieDsl tla : toegangLeveringsautorisaties) {
            if (tla.getLeveringautorisatie().getId() == berichtLeveringautorisatieId &&
                    tla.getLeveringautorisatie().getNaam().equals(zoekLeveringautorisatieNaam)) {
                return tla.getLeveringautorisatie();
            }
        }
        return null;
    }

    public void setVerzoekLeveringsautorisatie(String leveringsautorisatieNaam, String partijNaam, String oinOndertekenaar, String oinTransporteur) {
        for (ToegangLeveringautorisatieDsl tla : toegangLeveringsautorisaties) {
            if (tla.getLeveringautorisatie().getNaam().equals(leveringsautorisatieNaam) && tla.getGeautoriseerde().equals(partijNaam)) {
                autorisatieVoorVerzoek = new VerzoekAutorisatie();
                autorisatieVoorVerzoek.toegangLeveringautorisatieDSL = tla;
                autorisatieVoorVerzoek.leveringautorisatieId = tla.getLeveringautorisatie().getId();
                autorisatieVoorVerzoek.geautoriseerde = tla.getGeautoriseerde();
                autorisatieVoorVerzoek.partijNaam = partijNaam;
                autorisatieVoorVerzoek.oinOndertekenaar = oinOndertekenaar;
                autorisatieVoorVerzoek.oinTransporteur = oinTransporteur;
                return;
            }
        }
        throw new IllegalArgumentException(String.format("Toegangleveringsautorisatie %s met partij %s bestaat niet", leveringsautorisatieNaam, partijNaam));
    }

    public VerzoekAutorisatie getAutorisatieVoorVerzoek() {
        return autorisatieVoorVerzoek;
    }

    public void setVerzoekBijhoudingsautorisatie(final String partijNaam, final String oinOndertekenaar, final String oinTransporteur) {
        autorisatieVoorVerzoek = new VerzoekAutorisatie();
        autorisatieVoorVerzoek.partijNaam = partijNaam;
        autorisatieVoorVerzoek.oinOndertekenaar = oinOndertekenaar;
        autorisatieVoorVerzoek.oinTransporteur = oinTransporteur;
    }

    public static class VerzoekAutorisatie {

        private ToegangLeveringautorisatieDsl toegangLeveringautorisatieDSL;
        private int                           leveringautorisatieId;
        private String                        geautoriseerde;
        private String                        partijNaam;

        private String partijCode;
        private String oinOndertekenaar;
        private String oinTransporteur;

        public int getLeveringautorisatieId() {
            return leveringautorisatieId;
        }

        public void setLeveringautorisatieId(int leveringautorisatieId) {
            this.leveringautorisatieId = leveringautorisatieId;
        }

        public String getGeautoriseerde() {
            return geautoriseerde;
        }

        public void setGeautoriseerde(String geautoriseerde) {
            this.geautoriseerde = geautoriseerde;
        }

        public String getPartijNaam() {
            return partijNaam;
        }

        public void setPartijNaam(String partijNaam) {
            this.partijNaam = partijNaam;
        }

        public String getPartijCode() {
            return partijCode;
        }

        public void setPartijCode(String partijCode) {
            this.partijCode = partijCode;
        }

        public String getOinOndertekenaar() {
            return oinOndertekenaar;
        }

        public void setOinOndertekenaar(String oinOndertekenaar) {
            this.oinOndertekenaar = oinOndertekenaar;
        }

        public String getOinTransporteur() {
            return oinTransporteur;
        }

        public void setOinTransporteur(String oinTransporteur) {
            this.oinTransporteur = oinTransporteur;
        }

        public int geefBevragingDienstId() {
            final List<DienstbundelDsl> dienstBundels = toegangLeveringautorisatieDSL.getLeveringautorisatie().getDienstBundels();
            for (DienstbundelDsl dienstBundel : dienstBundels) {
                for (DienstDsl dienstDsl : dienstBundel.getDiensten()) {
                    if(dienstDsl.getSrt().equals(SoortDienst.GEEF_DETAILS_PERSOON.getNaam())) {
                       return dienstDsl.getId();
                    }
                }
            }
            throw new IllegalStateException("Geen GEEF_DETAILS_PERSOON dienst gevonden");
        }
    }
}
