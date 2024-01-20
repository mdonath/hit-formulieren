# hit-formulieren
Maakt m.b.v. Selenium in Java de inschrijfformulieren voor de HIT aan in SOL.

Vaak moet je na een jaar ook weer de laatste chromedriver downloaden en installeren.
De nieuwe moet in mijn geval in ` /opt/WebDriver/bin/chromedriver` worden gezet.

## 2023
Door 2FA moet je eerst Chrome starten met `/opt/google/chrome/chrome --remote-debugging-port=9222  --remote-allow-origins=http://127.0.0.1:9222`,
zelf inloggen in SOL en de code invullen en daarna kun je de `Main` starten.

Als de browser open blijft staan, dan kan er steeds opnieuw gebruik van worden gemaakt. Handig, want 
dan hoef je niet steeds opnieuw in te loggen.

## 2024

### TODO
- 'Inschrijven vanaf tijdstip' (frm_book_from_ts) meenemen in export zodat deze gebruikt kan worden bij het vullen van formulieren i.p.v. hardcoded