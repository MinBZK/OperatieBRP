/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.benchmark;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.BRPExpressies;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.tooling.apitest.StoryOmgeving;
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

/**
 */
@SuppressWarnings("all")
@Fork(1)
@Warmup(iterations = 15)
@Measurement(iterations = 20)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
public class ExpressieBenchmarks {

    private static final String EXPR_EIN_FUNCTIE_GROOT = "Persoon.Bijhouding.PartijCode EIN {3401, 5001, 10901, 11801, 11901, 14101, 14701, "
        + "14801, 15001, "
        + "15301, 15801,"
        + " 16001, 16301, 16401, 16601, 16801, 17101,17301, 17501, 17701, 18001, 18301, 18401, 18901, 19301, 19601, 19701, 20001, 20301, 21301, 22101, "
        + "22201, 22601, 22801, 23001, 23201, 23301, 24301, 24401, 24601, 26201, 26701, 26901, 27301, 27401, 27501, 27701, 27901, 28501, 28901, 29301, "
        + "29401, 29901, 30101, 30201, 30301, 30701, 30801, 31301, 31701, 32701, 33901, 34201, 34501, 35101, 99501, 150901, 158601, 169001, 170001, "
        + "170101, 170801, 173101, 173501, 174201, 177301, 177401, 185901, 187601, 189601, 195501}";

    private static final String EXPR_IN_FUNCTIE_KLEIN = "Persoon.Bijhouding.PartijCode EIN {3401}";

    @State(Scope.Benchmark)
    public static class OmgevingState {

        private StoryOmgeving omgeving;
        private Persoonslijst persoonslijst;
        private MetaObject    metaObject;
        private Expressie     exprInKlein;
        private Expressie     exprEInGroot;

        @Setup(Level.Trial)
        public void doSetup() throws IOException, ExpressieException {
            omgeving = new StoryOmgeving();
            omgeving.getPersoonDataStubService().laadPersonen(Lists.newArrayList("benchmarks:DELTAVERS01a/DELTAVERS01aC10T10_xls"));
            persoonslijst = omgeving.getBasisContextBean(PersoonslijstService.class).getById(1);
            metaObject = persoonslijst.getMetaObject();
            exprInKlein = ExpressieParser.parse(EXPR_IN_FUNCTIE_KLEIN);
            exprEInGroot = ExpressieParser.parse(EXPR_EIN_FUNCTIE_GROOT);
        }
    }

    @Benchmark
    public void parseInKlein(final OmgevingState state) throws ExpressieException {
        ExpressieParser.parse(EXPR_IN_FUNCTIE_KLEIN);
    }

    @Benchmark
    public void parseInGroot(final OmgevingState state) throws ExpressieException {
        ExpressieParser.parse(EXPR_EIN_FUNCTIE_GROOT);
    }

    @Benchmark
    public void evalInKlein(final OmgevingState state) throws ExpressieException {
        BRPExpressies.evalueer(state.exprInKlein, state.persoonslijst);
    }

    @Benchmark
    public void evalInGroot(final OmgevingState state) throws ExpressieException {
        BRPExpressies.evalueer(state.exprEInGroot, state.persoonslijst);
    }
}
