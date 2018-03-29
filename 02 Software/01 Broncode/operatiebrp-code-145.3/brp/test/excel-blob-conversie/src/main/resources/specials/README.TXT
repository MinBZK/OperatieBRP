Om nieuwe specials als blob beschikbaar te krijgen in de BRP API TEST zijn  de volgende stappen noodzakelijk:
1. Op Jenkins voer BRP Specials2Blob Conversie uit
2. Neem het build nummer uit jenkins over naar de POM van BRP API TEST

 <artifactItem>
     <groupId>nl.bzk.brp.excel2blob</groupId>
     <artifactId>blob-specials</artifactId>
     <version>73</version>
     <classifier>blob-specials</classifier>
     <overWrite>false</overWrite>
     <outputDirectory>${project.build.directory}/blobs/specials</outputDirectory>
 </artifactItem>

 3. Voer een clean verify met profiel unpackblobs uit, de nieuwe 'special' blob zou nu beschikbaar moeten zijn