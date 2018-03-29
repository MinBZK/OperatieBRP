/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.benchmark;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import nl.bzk.brp.domain.expressie.ExpressieException;
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
public class BlobBenchmarks {

    @State(Scope.Benchmark)
    public static class OmgevingState {

        StoryOmgeving omgeving;
        Persoonslijst persoonslijst;
        MetaObject    metaObject;

        @Setup(Level.Trial)
        public void doSetup() throws IOException, ExpressieException {
            omgeving = new StoryOmgeving();
            omgeving.getPersoonDataStubService().laadPersonen(Lists.newArrayList("benchmarks:DELTAVERS01a/DELTAVERS01aC10T10_xls"));
        }
    }

    @Benchmark
    public void deserialiseerBlob(final OmgevingState state) throws ExpressieException {
        final Persoonslijst byId = state.omgeving.getBasisContextBean(PersoonslijstService.class).getById(1);
    }
}
