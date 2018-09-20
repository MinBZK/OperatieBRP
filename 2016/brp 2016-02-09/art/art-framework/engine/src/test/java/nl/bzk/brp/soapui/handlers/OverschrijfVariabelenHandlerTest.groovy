package nl.bzk.brp.soapui.handlers

import nl.bzk.brp.soapui.steps.DataSourceValues
import org.junit.Test
import org.mockito.Mockito

import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verifyZeroInteractions

class OverschrijfVariabelenHandlerTest {

    DataSourceValues dataSourceValues = mock(DataSourceValues.class)

    @Test
    void testLaadOverschrijfVariabelen1Variabele() {
        String overschijfbareVariabelenString = 'variabele1=abc'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('variabele1', 'abc')
    }

    @Test
    void testLaadOverschrijfVariabelenMeerVariabelenEnReturns() {
        String overschijfbareVariabelenString = '\nvariabele1=abc,\nvariabele2=bb\n'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('variabele1', 'abc')
        Mockito.verify(dataSourceValues).setPropertyValue('variabele2', 'bb')
    }

    @Test
    void testLaadOverschrijfVariabelenMeerVariabelenEnRareTekens() {
        String overschijfbareVariabelenString = 'variabele1=abc\n\\ variabele2\\ =bd aa \n\$variabele3|%&=abaaa&*%\$'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('variabele1', 'abc')
        Mockito.verify(dataSourceValues).setPropertyValue(' variabele2 ', 'bd aa ')
        Mockito.verify(dataSourceValues).setPropertyValue('\$variabele3|%&', 'abaaa&*%\$')
    }

    @Test
    void testLaadOverschrijfVariabelenNullString() {
        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, null)
        verifyZeroInteractions(dataSourceValues)
    }

    @Test
    void testLaadOverschrijfVariabelenLegeString() {
        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, '')
        verifyZeroInteractions(dataSourceValues)
    }

    @Test
    void testLaadOverschrijfVariabelenNietVolledigeLijst() {
        String overschijfbareVariabelenString = 'variabele1=a,'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('variabele1', 'a')
    }

    @Test
    void testLaadOverschrijfVariabelenLegeVariabele() {
        String overschijfbareVariabelenString = 'variabele1=\nvariabele2=a'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('variabele1', '')
        Mockito.verify(dataSourceValues).setPropertyValue('variabele2', 'a')
    }

    @Test
    void testLaadOverschrijfVariabelenMetDatumVariabele() {
        String overschijfbareVariabelenString = 'burgerservicenummer_ipr1=110015927,\n' +
                'afnemerCode_aap1=017401,\n' +
                'abonnementNaam_aap1=Geen pop.bep. levering van BSN = en ANR op basis van doelbinding,\n' +
                'datumAanvangMaterielePeriode_aap1=[vandaag(-1,0,0)],\n' +
                'burgerservicenummer_ipv1=110015927'

        OverschrijfVariabelenHandler.laadOverschrijfVariabelen(dataSourceValues, overschijfbareVariabelenString)

        Mockito.verify(dataSourceValues).setPropertyValue('burgerservicenummer_ipr1', '110015927')
        Mockito.verify(dataSourceValues).setPropertyValue('afnemerCode_aap1', '017401')
        Mockito.verify(dataSourceValues).setPropertyValue('abonnementNaam_aap1', 'Geen pop.bep. levering van BSN = en ANR op basis van doelbinding')
        Mockito.verify(dataSourceValues).setPropertyValue('datumAanvangMaterielePeriode_aap1', '[vandaag(-1,0,0)]')
        Mockito.verify(dataSourceValues).setPropertyValue('burgerservicenummer_ipv1', '110015927')
    }

}
