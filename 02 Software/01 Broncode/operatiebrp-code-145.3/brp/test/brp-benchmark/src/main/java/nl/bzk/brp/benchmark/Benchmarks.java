/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.benchmark;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.tooling.apitest.StoryOmgeving;
import nl.bzk.brp.tooling.apitest.autorisatie.AutorisatieData;
import nl.bzk.brp.tooling.apitest.autorisatie.Autorisatielader;
import org.apache.commons.io.IOUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

@SuppressWarnings("all")
@Fork(1)
@Warmup(iterations = 15)
@Measurement(iterations = 30)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class Benchmarks {

    static {
        System.setProperty("verzoek_resp_to_file", "false");
        System.setProperty("schrijf_async_bericht_naar_file", "false");
    }

    @Benchmark
    public void benchmarkGeefDetailsPersoon(final GeefDetailsPersoonState state) {
        BrpNu.set();
        state.omgeving.getApiService().getBevragingApiService().getGeefDetailsPersoonApiService().verzoek(state.verzoekMap);
    }

    @Benchmark
    public void mut1_100(final Mutatie1_100_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut1350_0(final Mutatie1350_0_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut1350_1(final Mutatie1350_1_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut1350_5(final Mutatie1350_5_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut1350_25(final Mutatie1350_25_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut5000_1(final Mutatie5000_1_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    @Benchmark
    public void mut5000_5(final Mutatie5000_5_State state) {
        leverLaatsteHandelingVoorPersoon(state);
    }

    private void leverLaatsteHandelingVoorPersoon(final BasisState state) {
        BrpNu.set();
        state.omgeving.getApiService().getMutatieleveringApiService().leverLaatsteHandelingVoorPersoon("854820425");
    }

    /**
     * maaakAutorisatieData
     *
     * @return
     * @throws IOException io fout
     */
    private static AutorisatieData maaakAutorisatieData(int aantalAutorisaties, int percentageWaar) throws IOException {

        final String bestand = "/bench_autorisatie.template";

        final Resource classPathResource = new ClassPathResource(bestand);
        final List<Resource> resources = new ArrayList<>();
        final int aantalWaar = (int) Math.ceil(((double) percentageWaar / 100) * aantalAutorisaties);

        for (int i = 0; i < aantalAutorisaties; i++) {
            final StringWriter writer = new StringWriter();
            IOUtils.copy(classPathResource.getInputStream(), writer, "UTF-8");
            final String content = writer.toString();
            final String formatted = String.format(content, i < aantalWaar
                ? AutorisatieInformatie.DOELBINDING_WAAR : AutorisatieInformatie.DOELBINDING_ONWAAR);
            final ByteArrayResource byteArrayResource = new ByteArrayResource(formatted.getBytes());
            resources.add(byteArrayResource);
        }
        final AutorisatieData autorisatieData = Autorisatielader.laadAutorisatie(resources);
        return autorisatieData;
    }


    @State(Scope.Benchmark)
    public static class BasisState {

        StoryOmgeving omgeving;
        Map<String, String> verzoekMap = Maps.newHashMap();
        Persoonslijst persoonslijst;
        Expressie     expressie;

        private final int aantalAutorisaties;
        private final int percentageLeveren;

        protected BasisState(final int aantalAutorisaties, final int percentageLeveren) {
            this.aantalAutorisaties = aantalAutorisaties;
            this.percentageLeveren = percentageLeveren;
        }

        @Setup(Level.Trial)
        public void doSetup() throws Exception {
            omgeving = new StoryOmgeving();

            final AutorisatieData autorisatieData = maaakAutorisatieData(aantalAutorisaties, percentageLeveren);

            omgeving.getPersoonDataStubService().laadPersonen(Lists.newArrayList("benchmarks:DELTAVERS01a/DELTAVERS01aC10T10_xls"));
            verzoekMap.put("leveringsautorisatieNaam", "Benchautorisatie");
            verzoekMap.put("zendendePartijNaam", "Gemeente Utrecht");
            verzoekMap.put("bsn", "854820425");

            omgeving.laadAutorisaties(autorisatieData, UUID.randomUUID().toString());
            omgeving.getStoryService().setStory("teststory");
            omgeving.getStoryService().setScenario("testscenario");
            omgeving.getStoryService().setStep("step");
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie1_100_State extends BasisState {
        public Mutatie1_100_State() {
            super(1, 100);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie1350_0_State extends BasisState {
        public Mutatie1350_0_State() {
            super(1350, 0);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie1350_1_State extends BasisState {
        public Mutatie1350_1_State() {
            super(1350, 1);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie1350_5_State extends BasisState {
        public Mutatie1350_5_State() {
            super(1350, 5);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie1350_25_State extends BasisState {
        public Mutatie1350_25_State() {
            super(1350, 25);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie5000_1_State extends BasisState {
        public Mutatie5000_1_State() {
            super(5000, 1);
        }
    }

    @State(Scope.Benchmark)
    public static class Mutatie5000_5_State extends BasisState {
        public Mutatie5000_5_State() {
            super(5000, 5);
        }
    }

    @State(Scope.Benchmark)
    public static class GeefDetailsPersoonState extends BasisState {

        public GeefDetailsPersoonState() {
            super(1, 100);
        }
    }
}
