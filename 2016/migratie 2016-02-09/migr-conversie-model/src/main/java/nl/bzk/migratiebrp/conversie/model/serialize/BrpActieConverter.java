/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.serialize;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActie;
import nl.bzk.migratiebrp.conversie.model.brp.BrpActieBron;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

/**
 * The BrpActieConverter object is used to convert an <code>BrpActie</code> to XML by intercepting the normal
 * serialization process. When serializing an <code>BrpActie</code> the write method is invoked. This is provided with
 * the <code>BrpActie</code> instance to be serialized and the OutputNode to use to write the XML. Values can be taken
 * from the instance and transferred to the node.
 * <p/>
 * For deserialization the read method is invoked. This is provided with the InputNode, which can be used to read the
 * elements and attributes representing the member data of the <code>BrpActie</code> being deserialized. Once the
 * <code>BrpActie</code> object has been instantiated it must be returned.
 * <p/>
 * This custom converter is designed to cope with the circular dependencies between BrpActie objects.
 *
 * @see BrpActie
 */
@SuppressWarnings("checkstyle:illegalcatch")
public final class BrpActieConverter implements Converter<BrpActie> {

    private static final DecimalFormat PARTIJ_CODE_FORMAT = new DecimalFormat("000000");

    private static final String ELEMENT_ACTIE_BRONNEN = "actieBronnen";
    private static final String ELEMENT_LO3_HERKOMST = "lo3Herkomst";
    private static final String ELEMENT_SOORT_ACTIE_CODE = "soortActieCode";
    private static final String ELEMENT_PARTIJ_CODE = "partijCode";
    private static final String ELEMENT_DATUM_TIJD_REGISTRATIE = "datumTijdRegistratie";
    private static final String ELEMENT_DATUM_ONTLENING = "datumOntlening";
    private static final String ELEMENT_SORTERING = "sortering";
    private static final String ID_ATTRIBUUT = "id";
    private static final String REF_ATTRIBUUT = "ref";
    private static final String WAARDE = "waarde";
    private static final String ONDERZOEK = "onderzoek";
    private static final String ONDERZOEK_GEGEVENS = "aanduidingGegevensInOnderzoek";
    private static final String ONDERZOEK_AANVANG = "datumIngangOnderzoek";
    private static final String ONDERZOEK_EINDE = "datumEindeOnderzoek";

    private final Map<Long, BrpActie> actieReferentieMap;
    private final Serializer serializer;

    /**
     * Maakt een BrpActieConverter object.
     *
     * @param serializer
     *            the serializer used to perform nested serialization
     */
    public BrpActieConverter(final Serializer serializer) {
        actieReferentieMap = new HashMap<>();
        this.serializer = serializer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrpActie read(final InputNode node) {
        final InputNode actieReferentieNode = node.getAttribute(REF_ATTRIBUUT);
        final BrpActie result;
        if (actieReferentieNode != null) {
            final Long actieId = Long.valueOf(leesInputNodeWaarde(actieReferentieNode));
            if (actieReferentieMap.containsKey(actieId)) {
                result = actieReferentieMap.get(actieId);
            } else {
                // Deze lege dummy BrpActie zal automatisch worden vervangen door de constructor van BrpActie.
                result = new BrpActie(actieId, null, BrpPartijCode.MIGRATIEVOORZIENING, null, null, null, 0, null);
            }
        } else {
            result = readNieuwActie(node);
            actieReferentieMap.put(result.getId(), result);
        }
        return result;
    }

    private BrpActie readNieuwActie(final InputNode node) {
        final Long id = Long.valueOf(leesInputNodeWaarde(node.getAttribute(ID_ATTRIBUUT)));

        final BrpDatumTijd datumTijdRegistratie = readDatumTijdRegistratie(node);

        final BrpDatum datumOntlening = readDatumOntlening(node);

        final int sortering = readSortering(node);

        final BrpPartijCode partijCode = readPartijCode(node);

        final BrpSoortActieCode soortActieCode = readSoortActieCode(node);

        final List<BrpActieBron> actieBronnen = readActieBronnen(node);

        final Lo3Herkomst lo3Herkomst = readLo3Herkomst(node);

        return new BrpActie(id, soortActieCode, partijCode, datumTijdRegistratie, datumOntlening, actieBronnen, sortering, lo3Herkomst);
    }

    private Lo3Herkomst readLo3Herkomst(final InputNode node) {
        Lo3Herkomst result = null;
        final InputNode lo3HerkomstNode = getVolgendeKindNode(node, ELEMENT_LO3_HERKOMST);
        if (lo3HerkomstNode != null) {
            result = leesInputNode(lo3HerkomstNode, Lo3Herkomst.class);
        }
        return result;
    }

    private List<BrpActieBron> readActieBronnen(final InputNode node) {
        List<BrpActieBron> result = null;
        final InputNode documentStapelsNode = getVolgendeKindNode(node, ELEMENT_ACTIE_BRONNEN);
        if (documentStapelsNode != null) {
            result = new ArrayList<>();

            InputNode documentStapelNode = getVolgendeKindNode(documentStapelsNode);
            while (documentStapelNode != null) {
                result.add(leesInputNode(documentStapelNode, BrpActieBron.class));
                documentStapelNode = getVolgendeKindNode(documentStapelsNode);
            }

        }
        return result;
    }

    private BrpSoortActieCode readSoortActieCode(final InputNode node) {
        BrpSoortActieCode result = null;
        final InputNode soortActieCodeNode = getVolgendeKindNode(node, ELEMENT_SOORT_ACTIE_CODE);
        if (soortActieCodeNode != null) {
            result = new BrpSoortActieCode(readAttribuutWaarde(soortActieCodeNode), readOnderzoek(soortActieCodeNode));
        }
        return result;
    }

    private int readSortering(final InputNode node) {
        int result = 0;
        final InputNode sorteringNode = getVolgendeKindNode(node, ELEMENT_SORTERING);
        if (sorteringNode != null) {
            result = Integer.parseInt(leesInputNodeWaarde(sorteringNode));
        }
        return result;
    }

    private BrpDatumTijd readDatumTijdRegistratie(final InputNode node) {
        BrpDatumTijd result = null;
        final InputNode datumTijdRegistratieNode = getVolgendeKindNode(node, ELEMENT_DATUM_TIJD_REGISTRATIE);
        if (datumTijdRegistratieNode != null) {
            result =
                    BrpDatumTijd.fromDatumTijdMillis(
                        Long.parseLong(readAttribuutWaarde(datumTijdRegistratieNode)),
                        readOnderzoek(datumTijdRegistratieNode));
        }
        return result;
    }

    private BrpDatum readDatumOntlening(final InputNode node) {
        final BrpDatum result;
        final InputNode datumOntleningNode = getVolgendeKindNode(node, ELEMENT_DATUM_ONTLENING);
        if (datumOntleningNode != null) {
            final String waardeObject = readAttribuutWaarde(datumOntleningNode);
            final Lo3Onderzoek onderzoek = readOnderzoek(datumOntleningNode);
            if (waardeObject != null && !waardeObject.isEmpty()) {
                result = new BrpDatum(Integer.valueOf(waardeObject), onderzoek);
            } else {
                result = new BrpDatum(null, onderzoek);
            }

        } else {
            result = null;
        }
        return result;
    }

    private BrpPartijCode readPartijCode(final InputNode node) {
        BrpPartijCode result = null;
        final InputNode partijCodeNode = getVolgendeKindNode(node, ELEMENT_PARTIJ_CODE);
        final String partijCode = partijCodeNode == null ? null : readAttribuutWaarde(partijCodeNode);

        if (partijCodeNode != null) {
            result = new BrpPartijCode(Integer.parseInt(partijCode), readOnderzoek(partijCodeNode));
        }
        return result;
    }

    private String readAttribuutWaarde(final InputNode base) {
        final String resultaat;
        final InputNode waardeNode = getVolgendeKindNode(base, WAARDE);

        if (waardeNode != null) {
            resultaat = leesInputNodeWaarde(waardeNode);
        } else {
            final String baseValue = leesInputNodeWaarde(base);
            if (baseValue != null && baseValue.length() > 0) {
                resultaat = baseValue;
            } else {
                resultaat = null;
            }
        }

        return resultaat != null ? resultaat.trim() : null;
    }

    private Lo3Onderzoek readOnderzoek(final InputNode base) {
        final Lo3Onderzoek resultaat;
        final InputNode onderzoekNode = getVolgendeKindNode(base, ONDERZOEK);

        if (onderzoekNode != null) {
            final Lo3Integer gegevens =
                    new Lo3Integer(leesInputNodeWaarde(getVolgendeKindNode(getVolgendeKindNode(onderzoekNode, ONDERZOEK_GEGEVENS), WAARDE)), null);
            final Lo3Datum aanvang =
                    new Lo3Datum(leesInputNodeWaarde(getVolgendeKindNode(getVolgendeKindNode(onderzoekNode, ONDERZOEK_AANVANG), WAARDE)), null);
            final InputNode onderzoekEindeNode = getVolgendeKindNode(onderzoekNode, ONDERZOEK_EINDE);
            final Lo3Datum einde;
            if (onderzoekEindeNode != null) {
                einde = new Lo3Datum(leesInputNodeWaarde(getVolgendeKindNode(onderzoekEindeNode, WAARDE)), null);
            } else {
                einde = null;
            }
            resultaat = new Lo3Onderzoek(gegevens, aanvang, einde);
        } else {
            resultaat = null;
        }

        return resultaat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final OutputNode node, final BrpActie brpActie) {
        if (brpActie.getId() == null) {
            throw new NullPointerException("BrpActie.getId() is verplicht voor XML serialisatie.");
        }
        final Long actieId = brpActie.getId();
        if (actieReferentieMap.containsKey(actieId)) {
            writeActieReferentie(node, actieId);
        } else {
            actieReferentieMap.put(actieId, brpActie);
            writeNieuweActie(node, brpActie);
        }
    }

    private void writeActieReferentie(final OutputNode node, final Long actieId) {
        node.setAttribute(REF_ATTRIBUUT, actieId.toString());
    }

    private void writeNieuweActie(final OutputNode node, final BrpActie brpActie) {
        node.setAttribute(ID_ATTRIBUUT, brpActie.getId().toString());
        if (brpActie.getDatumTijdRegistratie() != null) {
            writeAttribuut(
                maakKindNode(node, ELEMENT_DATUM_TIJD_REGISTRATIE),
                brpActie.getDatumTijdRegistratie().getWaarde(),
                brpActie.getDatumTijdRegistratie().getOnderzoek());
        }
        if (brpActie.getDatumOntlening() != null) {
            writeAttribuut(maakKindNode(node, ELEMENT_DATUM_ONTLENING), brpActie.getDatumOntlening().getWaarde(), brpActie.getDatumOntlening()
                .getOnderzoek());
        }
        maakKindNode(node, ELEMENT_SORTERING).setValue(String.valueOf(brpActie.getSortering()));
        if (brpActie.getPartijCode() != null) {
            writePartij(node, brpActie);
        }
        if (brpActie.getSoortActieCode() != null) {
            writeAttribuut(maakKindNode(node, ELEMENT_SOORT_ACTIE_CODE), brpActie.getSoortActieCode().getWaarde(), brpActie.getSoortActieCode()
                .getOnderzoek());
        }
        if (brpActie.getActieBronnen() != null && !brpActie.getActieBronnen().isEmpty()) {
            final OutputNode actieBronnenNode = maakKindNode(node, ELEMENT_ACTIE_BRONNEN);
            for (final BrpActieBron actieBron : brpActie.getActieBronnen()) {
                schrijfObjectNaarNode(actieBronnenNode, actieBron);
            }
        }
        if (brpActie.getLo3Herkomst() != null) {
            schrijfObjectNaarNode(node, brpActie.getLo3Herkomst());
        }
    }

    private void writeAttribuut(final OutputNode base, final Object attribuutWaarde, final Lo3Onderzoek onderzoek) {
        if (attribuutWaarde != null) {
            maakKindNode(base, WAARDE).setValue(String.valueOf(attribuutWaarde));
        }
        if (onderzoek != null) {
            final OutputNode onderzoekNode = maakKindNode(base, ONDERZOEK);
            maakKindNode(maakKindNode(onderzoekNode, ONDERZOEK_GEGEVENS), WAARDE).setValue(
                String.valueOf(onderzoek.getAanduidingGegevensInOnderzoek().getWaarde()));
            maakKindNode(maakKindNode(onderzoekNode, ONDERZOEK_AANVANG), WAARDE).setValue(
                String.valueOf(onderzoek.getDatumIngangOnderzoek().getIntegerWaarde()));
            if (onderzoek.getDatumEindeOnderzoek() != null) {
                maakKindNode(maakKindNode(onderzoekNode, ONDERZOEK_EINDE), WAARDE).setValue(
                    String.valueOf(onderzoek.getDatumEindeOnderzoek().getIntegerWaarde()));
            }
        }
    }

    private void writePartij(final OutputNode node, final BrpActie brpActie) {
        writeAttribuut(maakKindNode(node, ELEMENT_PARTIJ_CODE), PARTIJ_CODE_FORMAT.format(brpActie.getPartijCode().getWaarde()), brpActie.getPartijCode()
            .getOnderzoek());
    }

    private OutputNode maakKindNode(final OutputNode outputNode, final String kindNodeNaam) {
        try {
            return outputNode.getChild(kindNodeNaam);
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan geen kind node maken met naam '" + kindNodeNaam + "'.", e);
        }
    }


    private <T> void schrijfObjectNaarNode(final OutputNode actieBronnenNode, final T actieBron) {
        try {
            serializer.write(actieBron, actieBronnenNode);
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan object niet schrijven.", e);
        }
    }

    private <T> T leesInputNode(final InputNode inputNode, final Class<T> resultaatType) {
        try {
            return serializer.read(resultaatType, inputNode);
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan node niet lezen als type " + resultaatType.getName(), e);
        }
    }

    private String leesInputNodeWaarde(final InputNode inputNode) {
        try {
            return inputNode.getValue();
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan geen waarde voor InputNode bepalen.", e);
        }
    }

    private InputNode getVolgendeKindNode(final InputNode inputNode, final String kindNodeNaam) {
        try {
            return inputNode.getNext(kindNodeNaam);
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan geen kind InputNode met naam '" + kindNodeNaam + "' bepalen.", e);
        }
    }

    private InputNode getVolgendeKindNode(final InputNode inputNode) {
        try {
            return inputNode.getNext();
        } catch (final Exception e /* Exceptie uit software bibliotheek afvangen */) {
            throw new IllegalArgumentException("Kan geen kind InputNode bepalen.", e);
        }
    }
}
