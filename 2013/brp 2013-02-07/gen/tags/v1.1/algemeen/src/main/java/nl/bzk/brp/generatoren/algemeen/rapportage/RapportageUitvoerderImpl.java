/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generatoren.algemeen.rapportage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.bprbzk.brp.generatoren.rapportage.GeneratieKlassen;
import nl.bprbzk.brp.generatoren.rapportage.Generator;
import nl.bprbzk.brp.generatoren.rapportage.GeneratorNaam;
import nl.bprbzk.brp.generatoren.rapportage.Generatoren;
import nl.bprbzk.brp.generatoren.rapportage.GeneratorenRapportage;
import nl.bprbzk.brp.generatoren.rapportage.Klasse;

import org.apache.commons.io.IOUtils;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.springframework.stereotype.Component;

/**
 * Voert rapportage uit over gegenereerde artefacten.
 */
@Component("rapportageUitvoerder")
public class RapportageUitvoerderImpl implements RapportageUitvoerder {

    /** standaard tab size voor het rapportage xml bestand. **/
    private static final int TAB_SIZE = 4;

    private GeneratorenRapportage generatorenRapportage;

    @Override
    public void initialiseerRapportageBestand(final File generatieXmlRapportageBestand) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(generatieXmlRapportageBestand);
            if (generatieXmlRapportageBestand.length() < 1) {
                maakLegeRapportageAanInBestand(generatieXmlRapportageBestand);
            }
            IBindingFactory bfact = BindingDirectory.getFactory(GeneratorenRapportage.class);
            IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            generatorenRapportage = (GeneratorenRapportage) uctx.unmarshalDocument(inputStream, null);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Rapportage bestand niet gevonden: "
                                                       + generatieXmlRapportageBestand.getName() + ".", e);
        } catch (JiBXException e) {
            throw new IllegalStateException("Kan rapportage bestand niet inlezen vanwege JiBX fout.", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * Indien het rapportage bestand leeg is moet de basis van het (xml) bestand worden opgebouwd.
     * In deze functie wordt het bestand geinitialiseerd.
     *
     * @param generatieXmlRapportageBestand het rapportage bestand.
     */
    private void maakLegeRapportageAanInBestand(final File generatieXmlRapportageBestand) {
        final GeneratorenRapportage rapportage = new GeneratorenRapportage();
        rapportage.setGeneratoren(new Generatoren());
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(generatieXmlRapportageBestand);
            IBindingFactory bfact = BindingDirectory.getFactory(GeneratorenRapportage.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.marshalDocument(rapportage, "UTF-8", null, outputStream);
        } catch (JiBXException e) {
            throw new IllegalStateException("Kan rapportage bestand niet wegschrijven vanwege JiBX fout.", e);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Rapportage bestand niet gevonden: "
                                                       + generatieXmlRapportageBestand.getName() + ".", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    @Override
    public void rapporteerGegenereerdeKlassen(final GeneratorNaam generatorNaam, final List<Klasse> klassen) {
        Generator aanroependeGenerator = null;
        //Bij een nieuw bestand zou de lijst leeg kunnen zijn.
        if (generatorenRapportage.getGeneratoren().getGeneratorList() == null) {
            generatorenRapportage.getGeneratoren().setGeneratorList(new ArrayList<Generator>());
        }

        for (Generator generator : generatorenRapportage.getGeneratoren().getGeneratorList()) {
            if (generator.getNaam() == generatorNaam) {
                aanroependeGenerator = generator;
            }
        }

        if (aanroependeGenerator == null) {
            // Als deze generator nog niet voorkomt in de rapportage, voeg hem dan toe met zijn klassen.
            aanroependeGenerator = new Generator();
            aanroependeGenerator.setNaam(generatorNaam);
            generatorenRapportage.getGeneratoren().getGeneratorList().add(aanroependeGenerator);
            aanroependeGenerator.setGeneratieKlassen(new GeneratieKlassen());
            aanroependeGenerator.getGeneratieKlassen().setKlasseList(klassen);
        } else {
            // Als deze generator al wel voorkomt, merge de lijst met klassen dan met de bestaande lijst.
            aanroependeGenerator.getGeneratieKlassen().setKlasseList(mergeGegenereerdeKlassenMetOudeKlassen(
                    aanroependeGenerator.getGeneratieKlassen().getKlasseList(),
                    klassen));
        }

        //Sorteer de klassen en de generatoren op naam zodat de output altijd gelijk zal zijn.
        //Dit maakt de analyse van een diff een stuk makkelijker.
        Collections.sort(aanroependeGenerator.getGeneratieKlassen().getKlasseList(), new Comparator<Klasse>() {
            @Override
            public int compare(final Klasse o1, final Klasse o2) {
                return o1.getNaam().compareTo(o2.getNaam());
            }
        });

        Collections.sort(generatorenRapportage.getGeneratoren().getGeneratorList(), new Comparator<Generator>() {
            @Override
            public int compare(final Generator o1, final Generator o2) {
                return o1.getNaam().compareTo(o2.getNaam());
            }
        });
    }

    /**
     * Merge de lijst met zojuist gegenereerde klassen met de lijst die al in het xml info rapportage bestand zitten.
     *
     * @param oudeKlassen de klassen die in het huidige xml rapportage bestand zitten.
     * @param nieuweKlassen de zojuist opnieuw gegenereerde klassen.
     * @return lijst met klassen die als output in het nieuwe xml bestand moeten.
     */
    private List<Klasse> mergeGegenereerdeKlassenMetOudeKlassen(final List<Klasse> oudeKlassen,
                                                                final List<Klasse> nieuweKlassen)
    {
        final List<Klasse> resultaat = new ArrayList<Klasse>();

        final Map<String, Klasse> nieuweKlassenMap = new HashMap<String, Klasse>();
        // Bouw een map van de nieuwe klassen op en voeg ze toe aan het resultaat.
        for (Klasse klasse : nieuweKlassen) {
            nieuweKlassenMap.put(klasse.getNaam(), klasse);
            resultaat.add(klasse);
        }

        // Elke oude klasse, die niet in de map met nieuwe klassen voorkomt, wordt ook toegevoegd.
        for (Klasse klasse : oudeKlassen) {
            if (nieuweKlassenMap.get(klasse.getNaam()) == null) {
                resultaat.add(klasse);
            }
        }

        return resultaat;
    }

    @Override
    public void schrijfRapportageWeg(final File generatieXmlRapportageBestand) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(generatieXmlRapportageBestand);
            IBindingFactory bfact = BindingDirectory.getFactory(GeneratorenRapportage.class);
            IMarshallingContext mctx = bfact.createMarshallingContext();
            mctx.setIndent(TAB_SIZE);
            mctx.marshalDocument(generatorenRapportage, "UTF-8", null, outputStream);
        } catch (JiBXException e) {
            throw new IllegalStateException("Kan rapportage bestand niet wegschrijven vanwege JiBX fout.", e);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Rapportage bestand niet gevonden: "
                                                       + generatieXmlRapportageBestand.getName() + ".", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }
}
