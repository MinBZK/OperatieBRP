/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DynamischeStamtabelMetGeldigheid;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

/**
 * Test voor valideren aanvang en einde geldigheid.
 */
@RunWith(MockitoJUnitRunner.class)
public class GenericGeldigheidStamgegevenValidatorTest {

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Mock
    private ReadonlyRepository<GeldigStamgegeven, Number> repository;

    @Mock
    private GeldigStamgegeven geldigStamgegeven;

    @Test
    public void testHuidigeDatumAanvangEnEindeGeldigheidLeeg() throws Exception {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(null);
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(null);

        final LocalDate dag = LocalDate.from(Instant.now().plus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(dag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(0, errors.getErrorCount());

    }

    @Test
    public void testNieuweDatumAanvangEnEindeGeldigheidLeeg() throws Exception {
        Mockito.when(repository.findOne(1)).thenReturn(null);

        final LocalDate dag = LocalDate.from(Instant.now().plus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(dag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                null);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(0, errors.getErrorCount());

    }

    @Test
    public void testHuidigeDatumAanvangEnEindeGeldigheidLeegNieuweAanvangInVerleden() throws Exception {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(null);
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(null);

        final LocalDate dag = LocalDate.from(Instant.now().minus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(dag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(1, errors.getErrorCount());

    }

    @Test
    public void testHuidigeDatumAanvangEnEindeGeldigheidLeegNieuweAanvangEnEindeInVerleden() throws Exception {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(null);
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(null);

        final LocalDate dag = LocalDate.from(Instant.now().minus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate deg = LocalDate.from(Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(dag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(2, errors.getErrorCount());

    }

    @Test
    public void HuidigeAanvangGeldigheidInVerleden() {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        final LocalDate oudeDag = LocalDate.from(Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(Integer.valueOf(oudeDag.format(dateFormat)));
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(null);

        final LocalDate dag = LocalDate.from(Instant.now().plus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(dag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void HuidigeAanvangGeldigheidInVerledenOngewijzigd() {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        final LocalDate oudeDag = LocalDate.from(Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(Integer.valueOf(oudeDag.format(dateFormat)));
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(null);

        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(oudeDag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(0, errors.getErrorCount());
    }

    @Test
    public void HuidigeEindeGeldigheidInVerleden() {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        final LocalDate oudeDag = LocalDate.from(Instant.now().minus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate oudeDeg = LocalDate.from(Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(Integer.valueOf(oudeDag.format(dateFormat)));
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(Integer.valueOf(oudeDeg.format(dateFormat)));

        final LocalDate deg = LocalDate.from(Instant.now().plus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(oudeDag.format(dateFormat)),
                Integer.valueOf(deg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(1, errors.getErrorCount());
    }

    @Test
    public void HuidigeEindeGeldigheidInVerledenOngewijzigd() {
        Mockito.when(repository.findOne(1)).thenReturn(geldigStamgegeven);
        final LocalDate oudeDag = LocalDate.from(Instant.now().minus(10, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        final LocalDate oudeDeg = LocalDate.from(Instant.now().minus(1, ChronoUnit.DAYS).atZone(ZoneId.systemDefault()));
        Mockito.when(geldigStamgegeven.getDatumAanvangGeldigheid()).thenReturn(Integer.valueOf(oudeDag.format(dateFormat)));
        Mockito.when(geldigStamgegeven.getDatumEindeGeldigheid()).thenReturn(Integer.valueOf(oudeDeg.format(dateFormat)));

        final GeldigStamgegeven geldigStamgegeven = new GeldigStamgegeven(Integer.valueOf(oudeDag.format(dateFormat)),
                Integer.valueOf(oudeDeg.format(dateFormat)),
                1);

        final GenericGeldigheidStamgegevenValidator<GeldigStamgegeven, Number> validator =
                new GenericGeldigheidStamgegevenValidator<>(GeldigStamgegeven.class, repository);

        Assert.assertTrue(validator.supports(GeldigStamgegeven.class));

        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "geldigstamgegeven");
        validator.validate(geldigStamgegeven, errors);

        Assert.assertEquals(0, errors.getErrorCount());
    }

    private class GeldigStamgegeven implements DynamischeStamtabelMetGeldigheid {

        private final Integer dag;
        private final Integer deg;
        private final Number id;

        private GeldigStamgegeven(final Integer dag, final Integer deg, final Number id) {
            this.dag = dag;
            this.deg = deg;
            this.id = id;
        }


        @Override
        public Integer getDatumAanvangGeldigheid() {
            return dag;
        }

        @Override
        public Integer getDatumEindeGeldigheid() {
            return deg;
        }

        @Override
        public Number getId() {
            return id;
        }
    }
}