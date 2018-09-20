/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.binding;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonIndicatieStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonIndicatieOnderCurateleHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIndicatieOnderCurateleModel;


import org.jibx.runtime.JiBXException;
import org.jibx.runtime.impl.MarshallingContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;


/**
 *
 */
public class AbstractIndicatieMapperTest {

    private AbstractIndicatieMapper indicatieMapper;

    @Test
    public void testMarshallingMetHistorieEnSleutels() throws JiBXException, IOException {
        indicatieMapper = new AbstractIndicatieMapper() {

            @Override
            protected boolean historieVeldenMarshallen() {
                return true;
            }

            @Override
            protected boolean marshalObjectSleutel() {
                return true;
            }

            @Override
            protected boolean marshalVoorkomenSleutel() {
                return true;
            }

            @Override
            protected boolean isAutorisatieVanKracht() {
                return false;
            }

            @Override
            protected boolean marshalVerantwoording() {
                return true;
            }
        };

        PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledig =
                new PersoonIndicatieOnderCurateleHisVolledigImpl(null);
        final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
        standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
        standaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final ActieModel actieInhoud =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        final ActieModel actieVerval =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        final ActieModel actieAanpaasingGeldigheid =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        ReflectionTestUtils.setField(actieInhoud, "iD", 1001L);
        ReflectionTestUtils.setField(actieVerval, "iD", 2002L);
        ReflectionTestUtils.setField(actieAanpaasingGeldigheid, "iD", 3003L);

        HisPersoonIndicatieOnderCurateleModel indicatie =
                new HisPersoonIndicatieOnderCurateleModel(persoonIndicatieOnderCurateleHisVolledig,
                        standaardGroepBericht,
                        standaardGroepBericht, actieInhoud);

        ReflectionTestUtils.setField(indicatie, "iD", 9000);
        ReflectionTestUtils.setField(persoonIndicatieOnderCurateleHisVolledig, "iD", 9000);
        ReflectionTestUtils.setField(indicatie, "actieVerval", actieVerval);
        ReflectionTestUtils.setField(indicatie, "actieAanpassingGeldigheid", actieAanpaasingGeldigheid);

        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumTijdVerval",
                DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1));
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumEindeGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20120101));

        Writer outputWriter = new StringWriter();
        final MarshallingContext marshallingContext =
                new MarshallingContext(new String[] { "foo", "bar", "baz" }, new String[] { "foo", "bar", "baz" },
                        new String[] { "foo", "bar", "baz" }, null);
        marshallingContext.setOutput(outputWriter);
        indicatieMapper.marshal(indicatie, marshallingContext);

        Assert.assertEquals(
                "<onderCuratele objecttype=\"PersoonIndicatie\" objectSleutel=\"9000\" voorkomenSleutel=\"9000\"><tijdstipRegistratie>2012-01-01T12:00:00.000+01:00</tijdstipRegistratie><actieInhoud>1001</actieInhoud><tijdstipVerval>2012-01-01T12:00:00.000+01:00</tijdstipVerval><actieVerval>2002</actieVerval><datumAanvangGeldigheid>2012-01-01</datumAanvangGeldigheid><datumEindeGeldigheid>2012-01-01</datumEindeGeldigheid><actieAanpassingGeldigheid>3003</actieAanpassingGeldigheid><waarde>J</waarde></onderCuratele>",
                outputWriter.toString());
    }

    @Test
    public void testMarshallingZonderHistorieEnTechnischeSleutel() throws JiBXException, IOException {
        indicatieMapper = new AbstractIndicatieMapper() {

            @Override
            protected boolean historieVeldenMarshallen() {
                return false;
            }

            @Override
            protected boolean marshalObjectSleutel() {
                return false;
            }

            @Override
            protected boolean marshalVoorkomenSleutel() {
                return false;
            }

            @Override
            protected boolean isAutorisatieVanKracht() {
                return false;
            }

            @Override
            protected boolean marshalVerantwoording() {
                return false;
            }
        };

        PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledig =
                new PersoonIndicatieOnderCurateleHisVolledigImpl(null);
        final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
        standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
        standaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        HisPersoonIndicatieModel indicatie =
                new HisPersoonIndicatieOnderCurateleModel(persoonIndicatieOnderCurateleHisVolledig,
                        standaardGroepBericht,
                        standaardGroepBericht, new ActieModel(null, null, null,
                        new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null)

                );

        Writer outputWriter = new StringWriter();
        final MarshallingContext marshallingContext =
                new MarshallingContext(new String[] { "foo", "bar", "baz" }, new String[] { "foo", "bar", "baz" },
                        new String[] { "foo", "bar", "baz" }, null);
        marshallingContext.setOutput(outputWriter);
        indicatieMapper.marshal(indicatie, marshallingContext);

        Assert.assertEquals("<onderCuratele objecttype=\"PersoonIndicatie\"><waarde>J</waarde></onderCuratele>",
                outputWriter.toString());
    }

    @Test
    public void testMarshallingZonderAutorisatieOpHistorieEnVerantwoording() throws JiBXException, IOException {
        indicatieMapper = new AbstractIndicatieMapper() {

            @Override
            protected boolean historieVeldenMarshallen() {
                return true;
            }

            @Override
            protected boolean marshalObjectSleutel() {
                return true;
            }

            @Override
            protected boolean marshalVoorkomenSleutel() {
                return true;
            }

            @Override
            protected boolean isAutorisatieVanKracht() {
                return true;
            }

            @Override
            protected boolean marshalVerantwoording() {
                return true;
            }
        };

        PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledig =
                new PersoonIndicatieOnderCurateleHisVolledigImpl(null);
        final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
        standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
        standaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final ActieModel actieInhoud =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        final ActieModel actieVerval =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        final ActieModel actieAanpaasingGeldigheid =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        ReflectionTestUtils.setField(actieInhoud, "iD", 1001L);
        ReflectionTestUtils.setField(actieVerval, "iD", 2002L);
        ReflectionTestUtils.setField(actieAanpaasingGeldigheid, "iD", 3003L);

        HisPersoonIndicatieOnderCurateleModel indicatie =
                new HisPersoonIndicatieOnderCurateleModel(persoonIndicatieOnderCurateleHisVolledig,
                        standaardGroepBericht,
                        standaardGroepBericht, actieInhoud);
        indicatie.getWaarde().setMagGeleverdWorden(true);

        ReflectionTestUtils.setField(indicatie, "iD", 9000);
        ReflectionTestUtils.setField(persoonIndicatieOnderCurateleHisVolledig, "iD", 9000);
        ReflectionTestUtils.setField(indicatie, "actieVerval", actieVerval);
        ReflectionTestUtils.setField(indicatie, "actieAanpassingGeldigheid", actieAanpaasingGeldigheid);

        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumTijdVerval",
                DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1));
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumEindeGeldigheid",
                new DatumEvtDeelsOnbekendAttribuut(20120101));

        Writer outputWriter = new StringWriter();
        final MarshallingContext marshallingContext =
                new MarshallingContext(new String[] { "foo", "bar", "baz" }, new String[] { "foo", "bar", "baz" },
                        new String[] { "foo", "bar", "baz" }, null);
        marshallingContext.setOutput(outputWriter);
        indicatieMapper.marshal(indicatie, marshallingContext);

        Assert.assertEquals(
                "<onderCuratele objecttype=\"PersoonIndicatie\" objectSleutel=\"9000\" voorkomenSleutel=\"9000\"><waarde>J</waarde></onderCuratele>",
                outputWriter.toString());
    }

    @Test
    public void testMarshallingMetAutorisatieOpHistorieEnVerantwoording() throws JiBXException, IOException {
        indicatieMapper = new AbstractIndicatieMapper() {

            @Override
            protected boolean historieVeldenMarshallen() {
                return true;
            }

            @Override
            protected boolean marshalObjectSleutel() {
                return true;
            }

            @Override
            protected boolean marshalVoorkomenSleutel() {
                return true;
            }

            @Override
            protected boolean isAutorisatieVanKracht() {
                return true;
            }

            @Override
            protected boolean marshalVerantwoording() {
                return true;
            }
        };

        PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledig =
                new PersoonIndicatieOnderCurateleHisVolledigImpl(null);
        final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
        standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
        standaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final ActieModel actieInhoud =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieInhoud.setMagGeleverdWorden(true);
        final ActieModel actieVerval =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieVerval.setMagGeleverdWorden(true);
        final ActieModel actieAanpaasingGeldigheid =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieAanpaasingGeldigheid.setMagGeleverdWorden(true);

        ReflectionTestUtils.setField(actieInhoud, "iD", 1001L);
        ReflectionTestUtils.setField(actieVerval, "iD", 2002L);
        ReflectionTestUtils.setField(actieAanpaasingGeldigheid, "iD", 3003L);

        HisPersoonIndicatieOnderCurateleModel indicatie =
                new HisPersoonIndicatieOnderCurateleModel(persoonIndicatieOnderCurateleHisVolledig,
                        standaardGroepBericht,
                        standaardGroepBericht, actieInhoud);
        indicatie.getWaarde().setMagGeleverdWorden(true);

        ReflectionTestUtils.setField(indicatie, "iD", 9000);
        ReflectionTestUtils.setField(persoonIndicatieOnderCurateleHisVolledig, "iD", 9000);
        ReflectionTestUtils.setField(indicatie, "actieVerval", actieVerval);
        ReflectionTestUtils.setField(indicatie, "actieAanpassingGeldigheid", actieAanpaasingGeldigheid);

        final DatumTijdAttribuut datumTijdVerval = DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1);
        datumTijdVerval.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumTijdVerval", datumTijdVerval);

        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(20120101);
        datumEindeGeldigheid.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumEindeGeldigheid", datumEindeGeldigheid);

        Writer outputWriter = new StringWriter();
        final MarshallingContext marshallingContext =
                new MarshallingContext(new String[] { "foo", "bar", "baz" }, new String[] { "foo", "bar", "baz" },
                        new String[] { "foo", "bar", "baz" }, null);
        marshallingContext.setOutput(outputWriter);
        indicatieMapper.marshal(indicatie, marshallingContext);

        Assert.assertEquals(
                "<onderCuratele objecttype=\"PersoonIndicatie\" objectSleutel=\"9000\" voorkomenSleutel=\"9000\"><tijdstipVerval>2012-01-01T12:00:00.000+01:00</tijdstipVerval><datumEindeGeldigheid>2012-01-01</datumEindeGeldigheid><waarde>J</waarde></onderCuratele>",
                outputWriter.toString());
    }

    @Test
    public void testMarshallingGeenAutorisatieIndicatieVoorkomen() throws JiBXException, IOException {
        indicatieMapper = new AbstractIndicatieMapper() {

            @Override
            protected boolean historieVeldenMarshallen() {
                return true;
            }

            @Override
            protected boolean marshalObjectSleutel() {
                return true;
            }

            @Override
            protected boolean marshalVoorkomenSleutel() {
                return true;
            }

            @Override
            protected boolean isAutorisatieVanKracht() {
                return true;
            }

            @Override
            protected boolean marshalVerantwoording() {
                return true;
            }
        };

        PersoonIndicatieOnderCurateleHisVolledigImpl persoonIndicatieOnderCurateleHisVolledig =
                new PersoonIndicatieOnderCurateleHisVolledigImpl(null);
        final PersoonIndicatieStandaardGroepBericht standaardGroepBericht = new PersoonIndicatieStandaardGroepBericht();
        standaardGroepBericht.setWaarde(new JaAttribuut(Ja.J));
        standaardGroepBericht.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        final ActieModel actieInhoud =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieInhoud.setMagGeleverdWorden(true);
        final ActieModel actieVerval =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieVerval.setMagGeleverdWorden(true);
        final ActieModel actieAanpaasingGeldigheid =
                new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(20120101), null,
                        DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1), null);
        actieAanpaasingGeldigheid.setMagGeleverdWorden(true);

        ReflectionTestUtils.setField(actieInhoud, "iD", 1001L);
        ReflectionTestUtils.setField(actieVerval, "iD", 2002L);
        ReflectionTestUtils.setField(actieAanpaasingGeldigheid, "iD", 3003L);

        HisPersoonIndicatieOnderCurateleModel indicatie =
                new HisPersoonIndicatieOnderCurateleModel(persoonIndicatieOnderCurateleHisVolledig,
                        standaardGroepBericht,
                        standaardGroepBericht, actieInhoud);
        // Geen autorisatie:
        indicatie.getWaarde().setMagGeleverdWorden(false);

        ReflectionTestUtils.setField(indicatie, "iD", 9000);
        ReflectionTestUtils.setField(persoonIndicatieOnderCurateleHisVolledig, "iD", 9000);
        ReflectionTestUtils.setField(indicatie, "actieVerval", actieVerval);
        ReflectionTestUtils.setField(indicatie, "actieAanpassingGeldigheid", actieAanpaasingGeldigheid);

        final DatumTijdAttribuut datumTijdVerval = DatumTijdAttribuut.bouwDatumTijd(2012, 1, 1);
        datumTijdVerval.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumTijdVerval", datumTijdVerval);

        final DatumEvtDeelsOnbekendAttribuut datumEindeGeldigheid = new DatumEvtDeelsOnbekendAttribuut(20120101);
        datumEindeGeldigheid.setMagGeleverdWorden(true);
        ReflectionTestUtils.setField(indicatie.getMaterieleHistorie(), "datumEindeGeldigheid", datumEindeGeldigheid);

        Writer outputWriter = new StringWriter();
        final MarshallingContext marshallingContext =
                new MarshallingContext(new String[] { "foo", "bar", "baz" }, new String[] { "foo", "bar", "baz" },
                        new String[] { "foo", "bar", "baz" }, null);
        marshallingContext.setOutput(outputWriter);
        indicatieMapper.marshal(indicatie, marshallingContext);

        Assert.assertEquals("", outputWriter.toString());
    }
}
