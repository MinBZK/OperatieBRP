/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.serialize;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.conversie.model.herkomst.Lo3Herkomst;

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
public final class BrpActieConverter implements Converter<BrpActie> {

    private static final DecimalFormat GEMEENTE_CODE_FORMAT = new DecimalFormat("0000");

    private static final String ELEMENT_DOCUMENT_STAPELS = "documentStapels";
    private static final String ELEMENT_LO3_HERKOMST = "lo3Herkomst";
    private static final String ELEMENT_VERDRAG_CODE = "verdragCode";
    private static final String ELEMENT_SOORT_ACTIE_CODE = "soortActieCode";
    private static final String ELEMENT_PARTIJ_NAAM = "partijNaam";
    private static final String ELEMENT_PARTIJ_GEMEENTE_CODE = "partijGemeenteCode";
    private static final String ELEMENT_DATUM_TIJD_REGISTRATIE = "datumTijdRegistratie";
    private static final String ELEMENT_SORTERING = "sortering";
    private static final String ELEMENT_DATUM_TIJD_ONTLENING = "datumTijdOntlening";
    private static final String ID_ATTRIBUUT = "id";
    private static final String REF_ATTRIBUUT = "ref";

    private final Map<Long, BrpActie> actieReferentieMap;
    private final Serializer serializer;

    /**
     * Maakt een BrpActieConverter object.
     * 
     * @param serializer
     *            the serializer used to perform nested serialization
     */
    public BrpActieConverter(final Serializer serializer) {
        actieReferentieMap = new HashMap<Long, BrpActie>();
        this.serializer = serializer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BrpActie read(final InputNode node) throws Exception {
        final InputNode actieReferentieNode = node.getAttribute(REF_ATTRIBUUT);
        BrpActie result;
        if (actieReferentieNode != null) {
            final Long actieId = Long.valueOf(actieReferentieNode.getValue());
            if (actieReferentieMap.containsKey(actieId)) {
                result = actieReferentieMap.get(actieId);
            } else {
                // Deze lege dummy BrpActie zal automatisch worden vervangen door de constructor van BrpActie.
                result =
                        new BrpActie(actieId, null, BrpPartijCode.MIGRATIEVOORZIENING, null, null, null, null, 0,
                                null);
            }
        } else {
            result = readNieuwActie(node);
            actieReferentieMap.put(result.getId(), result);
        }
        return result;
    }

    private BrpActie readNieuwActie(final InputNode node) throws Exception {
        final Long id = Long.valueOf(node.getAttribute(ID_ATTRIBUUT).getValue());

        final BrpDatumTijd datumTijdOntlening = readDatumTijdOntlening(node);

        final BrpDatumTijd datumTijdRegistratie = readDatumTijdRegistratie(node);

        final int sortering = readSortering(node);

        final BrpPartijCode partijCode = readPartijCode(node);

        final BrpSoortActieCode soortActieCode = readSoortActieCode(node);

        final BrpVerdragCode verdragCode = readVerdragCode(node);

        final List<BrpStapel<BrpDocumentInhoud>> documentStapels = readDocumentStapels(node);

        final Lo3Herkomst lo3Herkomst = readLo3Herkomst(node);

        return new BrpActie(id, soortActieCode, partijCode, verdragCode, datumTijdOntlening, datumTijdRegistratie,
                documentStapels, sortering, lo3Herkomst);
    }

    private Lo3Herkomst readLo3Herkomst(final InputNode node) throws Exception {
        Lo3Herkomst result = null;
        final InputNode lo3HerkomstNode = node.getNext(ELEMENT_LO3_HERKOMST);
        if (lo3HerkomstNode != null) {
            result = serializer.read(Lo3Herkomst.class, lo3HerkomstNode);
        }
        return result;
    }

    private List<BrpStapel<BrpDocumentInhoud>> readDocumentStapels(final InputNode node) throws Exception {
        List<BrpStapel<BrpDocumentInhoud>> result = null;
        final InputNode documentStapelsNode = node.getNext(ELEMENT_DOCUMENT_STAPELS);
        if (documentStapelsNode != null) {
            result = new ArrayList<BrpStapel<BrpDocumentInhoud>>();

            InputNode documentStapelNode = documentStapelsNode.getNext();
            while (documentStapelNode != null) {
                @SuppressWarnings("unchecked")
                final BrpStapel<BrpDocumentInhoud> brpStapel = serializer.read(BrpStapel.class, documentStapelNode);
                result.add(brpStapel);

                documentStapelNode = documentStapelsNode.getNext();

            }

        }
        return result;
    }

    private BrpVerdragCode readVerdragCode(final InputNode node) throws Exception {
        BrpVerdragCode result = null;
        final InputNode verdragCodeNode = node.getNext(ELEMENT_VERDRAG_CODE);
        if (verdragCodeNode != null) {
            result = new BrpVerdragCode(verdragCodeNode.getValue());
        }
        return result;
    }

    private BrpSoortActieCode readSoortActieCode(final InputNode node) throws Exception {
        BrpSoortActieCode result = null;
        final InputNode soortActieCodeNode = node.getNext(ELEMENT_SOORT_ACTIE_CODE);
        if (soortActieCodeNode != null) {
            result = BrpSoortActieCode.valueOfCode(soortActieCodeNode.getValue());
        }
        return result;
    }

    private int readSortering(final InputNode node) throws Exception {
        int result = 0;
        final InputNode sorteringNode = node.getNext(ELEMENT_SORTERING);
        if (sorteringNode != null) {
            result = Integer.valueOf(sorteringNode.getValue());
        }
        return result;
    }

    private BrpDatumTijd readDatumTijdRegistratie(final InputNode node) throws Exception {
        BrpDatumTijd result = null;
        final InputNode datumTijdRegistratieNode = node.getNext(ELEMENT_DATUM_TIJD_REGISTRATIE);
        if (datumTijdRegistratieNode != null) {
            result = BrpDatumTijd.fromDatumTijdMillis(Long.parseLong(datumTijdRegistratieNode.getValue()));
        }
        return result;
    }

    private BrpDatumTijd readDatumTijdOntlening(final InputNode node) throws Exception {
        BrpDatumTijd result = null;
        final InputNode datumTijdOntleningNode = node.getNext(ELEMENT_DATUM_TIJD_ONTLENING);
        if (datumTijdOntleningNode != null) {
            result = BrpDatumTijd.fromDatumTijdMillis(Long.parseLong(datumTijdOntleningNode.getValue()));
        }
        return result;
    }

    private BrpPartijCode readPartijCode(final InputNode node) throws Exception {
        BrpPartijCode result = null;
        final InputNode partijNaamNode = node.getNext(ELEMENT_PARTIJ_NAAM);
        final String partijNaam = partijNaamNode == null ? null : partijNaamNode.getValue();

        final InputNode partijGemeenteCodeNode = node.getNext(ELEMENT_PARTIJ_GEMEENTE_CODE);
        final String partijGemeenteCode = partijGemeenteCodeNode == null ? null : partijGemeenteCodeNode.getValue();

        if (partijNaam != null || partijGemeenteCodeNode != null) {
            result =
                    new BrpPartijCode(partijNaam, partijGemeenteCode == null ? null
                            : Integer.valueOf(partijGemeenteCode));
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void write(final OutputNode node, final BrpActie brpActie) throws Exception {
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

    private void writeNieuweActie(final OutputNode node, final BrpActie brpActie) throws Exception {
        node.setAttribute(ID_ATTRIBUUT, brpActie.getId().toString());
        if (brpActie.getDatumTijdOntlening() != null) {
            node.getChild(ELEMENT_DATUM_TIJD_ONTLENING).setValue(
                    String.valueOf(brpActie.getDatumTijdOntlening().getDatumTijdMillis()));
        }
        if (brpActie.getDatumTijdRegistratie() != null) {
            node.getChild(ELEMENT_DATUM_TIJD_REGISTRATIE).setValue(
                    String.valueOf(brpActie.getDatumTijdRegistratie().getDatumTijdMillis()));
        }
        node.getChild(ELEMENT_SORTERING).setValue(String.valueOf(brpActie.getSortering()));
        if (brpActie.getPartijCode() != null) {
            writePartij(node, brpActie);
        }
        if (brpActie.getSoortActieCode() != null) {
            node.getChild(ELEMENT_SOORT_ACTIE_CODE).setValue(brpActie.getSoortActieCode().getCode());
        }
        if (brpActie.getVerdragCode() != null) {
            node.getChild(ELEMENT_VERDRAG_CODE).setValue(brpActie.getVerdragCode().getOmschrijving());
        }
        if (brpActie.getDocumentStapels() != null && !brpActie.getDocumentStapels().isEmpty()) {
            final OutputNode documentStapelsNode = node.getChild(ELEMENT_DOCUMENT_STAPELS);
            for (final BrpStapel<BrpDocumentInhoud> documentStapel : brpActie.getDocumentStapels()) {
                serializer.write(documentStapel, documentStapelsNode);
            }
        }
        if (brpActie.getLo3Herkomst() != null) {
            serializer.write(brpActie.getLo3Herkomst(), node);
        }
    }

    private void writePartij(final OutputNode node, final BrpActie brpActie) throws Exception {
        if (brpActie.getPartijCode().getNaam() != null) {
            node.getChild(ELEMENT_PARTIJ_NAAM).setValue(brpActie.getPartijCode().getNaam());
        }
        if (brpActie.getPartijCode().getGemeenteCode() != null) {
            node.getChild(ELEMENT_PARTIJ_GEMEENTE_CODE).setValue(
                    GEMEENTE_CODE_FORMAT.format(brpActie.getPartijCode().getGemeenteCode()));
        }
    }
}
