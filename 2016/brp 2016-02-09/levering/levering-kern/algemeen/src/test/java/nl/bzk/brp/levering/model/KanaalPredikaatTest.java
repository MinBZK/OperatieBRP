/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

//package nl.bzk.brp.levering.model;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.List;
//
//import nl.bzk.brp.model.algemeen.attribuuttype.autaut.UriAttribuut;
//import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Kanaal;
//import nl.bzk.brp.model.algemeen.stamgegeven.autaut.KanaalAttribuut;
//import nl.bzk.brp.model.hisvolledig.autaut.AfleverwijzeHisVolledig;
//import nl.bzk.brp.model.hisvolledig.impl.autaut.AfleverwijzeHisVolledigImpl;
//import nl.bzk.brp.model.logisch.autaut.Afleverwijze;
//import nl.bzk.brp.model.operationeel.autaut.AfleverwijzeModel;
//import org.apache.commons.collections.CollectionUtils;
//import org.hamcrest.CoreMatchers;
//import org.junit.Assert;
//import org.junit.Test;
//
//
//public class KanaalPredikaatTest {
//
//    private static final String URI_LOCALHOST = "http://localhost";
//
//    @Test
//    public final void predikaatWerktMetModel() {
//        final KanaalPredikaat predikaat = new KanaalPredikaat(Kanaal.DUMMY);
//
//        final List<? extends Afleverwijze> afleverwijzes = Arrays.asList(
//                new AfleverwijzeModel(null, new KanaalAttribuut(Kanaal.BRP), new UriAttribuut(URI_LOCALHOST)),
//                new AfleverwijzeModel(null, new KanaalAttribuut(Kanaal.DUMMY), new UriAttribuut(URI_LOCALHOST)),
//                new AfleverwijzeModel(null, new KanaalAttribuut(Kanaal.LO3_Netwerk), new UriAttribuut(URI_LOCALHOST)));
//
//        final Afleverwijze afleverwijze = (Afleverwijze) CollectionUtils.find(afleverwijzes, predikaat);
//        Assert.assertThat(afleverwijze, CoreMatchers.notNullValue());
//    }
//
//    @Test
//    public final void predikaatWerktMetHisVolledig() {
//        final KanaalPredikaat predikaat = new KanaalPredikaat(Kanaal.DUMMY);
//
//        final List<? extends AfleverwijzeHisVolledig> afleverwijzes = Arrays.asList(
//                new AfleverwijzeHisVolledigImpl(null, new KanaalAttribuut(Kanaal.LO3_Netwerk), new UriAttribuut(URI_LOCALHOST)),
//                new AfleverwijzeHisVolledigImpl(null, new KanaalAttribuut(Kanaal.DUMMY), new UriAttribuut(URI_LOCALHOST)),
//                new AfleverwijzeHisVolledigImpl(null, new KanaalAttribuut(Kanaal.DUMMY), new UriAttribuut(URI_LOCALHOST)));
//
//        final Collection<AfleverwijzeHisVolledig> resultaat = new ArrayList<>();
//        CollectionUtils.select(afleverwijzes, predikaat, resultaat);
//        Assert.assertThat(resultaat.size(), CoreMatchers.is(2));
//    }
//
//    @Test
//    public final void predikaatWerktMetAnderSoortObject() {
//        final KanaalPredikaat predikaat = new KanaalPredikaat(Kanaal.DUMMY);
//
//        final List<? extends String> afleverwijzes = Arrays.asList("test", "nog eentje");
//
//        final Collection<AfleverwijzeHisVolledig> resultaat = new ArrayList<>();
//        CollectionUtils.select(afleverwijzes, predikaat, resultaat);
//        Assert.assertThat(resultaat.size(), CoreMatchers.is(0));
//    }
//
//    @Test
//    public final void predikaatWerktMetModelEnLeegKanaal() {
//        final KanaalPredikaat predikaat = new KanaalPredikaat(Kanaal.DUMMY);
//
//        final List<? extends Afleverwijze> afleverwijzes = Arrays.asList(new AfleverwijzeModel(null, null, new UriAttribuut(URI_LOCALHOST)));
//
//        final Afleverwijze afleverwijze = (Afleverwijze) CollectionUtils.find(afleverwijzes, predikaat);
//        Assert.assertThat(afleverwijze, CoreMatchers.nullValue());
//    }
//
//    @Test
//    public final void predikaatWerktMetHisVolledigEnLeegKanaal() {
//        final KanaalPredikaat predikaat = new KanaalPredikaat(Kanaal.DUMMY);
//
//        final List<? extends AfleverwijzeHisVolledig> afleverwijzes =
//                Arrays.asList(new AfleverwijzeHisVolledigImpl(null, null, new UriAttribuut(URI_LOCALHOST)));
//
//        final Collection<AfleverwijzeHisVolledig> resultaat = new ArrayList<>();
//        CollectionUtils.select(afleverwijzes, predikaat, resultaat);
//
//        Assert.assertThat(resultaat.size(), CoreMatchers.is(0));
//    }
//}
