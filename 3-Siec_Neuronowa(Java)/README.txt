#Jednowarstowa sieć neuronowa

Opis programu:      

Użyjemy 1-warstwowej sieci neuronowej do klasyfikowania języków naturalnych tekstów.      

 

Bierzemy dokument w dowolnym języku (w alfabecie łacińskim) z pliku ".txt", wyrzucamy wszystkie znaki poza literami alfabetu angielskiego (ASCII) i przerabiamy na 26-elementowy wektor proporcji liter (czyli: jaka jest proporcja 'a', 'b', etc.)      

 

Okazuje się, ze taki wektor rozkładu znaków wystarcza do rozróżniania języka naturalnego dokumentu tekstowego, nawet dla tak podobnych języków jak np. czeski i słowacki.      

 

Tworzymy więc 1 warstwę K perceptronów (gdzie K to liczba języków) i uczymy każdy perceptron rozpoznawania "jego" języka.      

 

Uczenie perceptronów przebiega jak w poprzednim projekcie, tzn. z dyskretną {0,1} funkcją aktywacji.      

 

Mając wyuczony każdy z perceptronów, klasyfikacji do jednej z K klas dokonujemy używając maximum selector (zdjąć dyskretną funkcję aktywacji) i normalizować zarówno wektor wag jak i wejść.  //np. dla wyniku (0.24, 0.56, 0.90) po użyciu maximum selectora będzie (0,0,1)    

 

UWAGA: przy normalizacji można użyć miary euklidesowej.    

Normalizując wektor wag nie dokładamy do niego parametru progu (theta).     

 

Należy zapewnić okienko tekstowe do testowania: po nauczeniu wklejamy dowolny nowy tekst w danym języku i sprawdzamy, czy sieć prawidłowo go klasyfikuje.      

 

Oczywiście w momencie pisania programu nie powinno być wiadome ile i jakie będą języki.       

 

Nie można używać żadnych bibliotek ML, wszystko ma być zaimplementowane od zera w pętlach, ifach, odległość tez należy samemu liczyć używając działań arytmetycznych (do operacji na liczbach można używać java.lang.Math), etc. Można używać java.util.     