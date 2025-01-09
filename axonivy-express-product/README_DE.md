# Axon Ivy Express

Axon Ivy Express ist ein Zusatzmodul für das Axon Ivy Portal. Als Geschäftsanwender kannst du damit deine Prozessanwendungen erstellen und mit deinen Kolleg:innen teilen. Diese Funktionen sind auch als No-Code Application Platforms oder Citizen Developer Platforms bekannt. Daher ist es das perfekte Werkzeug, um deine Prozesse zu digitalisieren und Standardisierung, Zuverlässigkeit sowie Nachverfolgbarkeit zu schaffen.

Axon Ivy Express:

   * Ermöglicht es dir, Prozesse ohne IT-Kenntnisse zu erstellen.
   * Automatisiert Geschäftsprozesse ohne die Einbindung der IT-Abteilung.
   * Unterstützt alle Standardfunktionen wie E-Mail-Benachrichtigungen, Aufgabenweiterleitung usw.
   * Verfügt über ein Import-Tool, um deine No-Code-Geschäftsprozesse an Low-Code- oder Pro-Code-Entwickler:innen zu übergeben.

![express-workflow-properties](images/express-workflow-properties.png)

## Demo

### Erstelle einen neuen Express-Prozess

1. Starte die Axon Ivy Engine.
2. Öffne die Seite **Prozesse**.
3. Klicke auf den Prozess **Express Management**, um zum Express Management Dashboard zu gelangen.

![Express Management process](images/express-management-process.png)

4. Klicke im **Express Management** auf die Schaltfläche **Erstellen**.

5. Die Seite **Workflow-Eigenschaften** wird geöffnet.

![Workflow properties](images/workflow-properties.png)

#### Definiere die Workflow-Eigenschaften

1. **Prozessart** definieren:

   * Wähle die Option *Einmalig*, wenn du den Prozess nur einmal ausführen möchtest.

   * Wähle die Option *Wiederkehrend*, wenn du den Prozess für wiederholte Nutzung speichern möchtest. Der Prozess wird dann in der Tabelle auf der Seite **Express Management** angezeigt.

2. **Benutzeroberfläche** definieren:

   * Mit der Option *Erstellen* kannst du für jeden Prozessschritt eigene Benutzerdialoge erstellen.
   * Mit der Option *Standardoberfläche* werden die Benutzerdialoge automatisch von Axon Ivy Express generiert.

3. Gib unter **Prozessname** einen aussagekräftigen Namen ein.

4. Optional kannst du unter **Prozessbeschreibung** eine Beschreibung hinzufügen. Wir empfehlen dir, die Beschreibung zu nutzen, um Details zu deinem Prozess anzugeben.

5. Klicke auf den Link **Ändern** neben dem Icon, um das Icon auszuwählen, das am besten zu deinem Prozess passt.

![express-workflow-properties](images/express-workflow-properties.png)

6. Der erste Prozessschritt ist bereits zur Konfiguration verfügbar.
7. Du kannst weitere Prozessschritte mit der Schaltfläche **Prozessschritt hinzufügen** hinzufügen.
8. Du kannst unnötige Prozessschritte mit der Schaltfläche **Prozessschritt löschen** löschen.
9. Für jeden Prozessschritt:

   * Wähle den **Aufgabentyp**:

      |**Aufgabentyp**|**Beschreibung**|
      | ----------- | ------------- |
      |**Aufgabe**|Für diese Aufgabe kannst du eine Benutzeroberfläche definieren.|
      |**Aufgabe mit E-Mail Schritt**|Zusätzlich zur normalen Benutzeraufgabe kannst du direkt aus dem Axon Ivy Portal eine E-Mail senden, ohne zu einem anderen System wechseln zu müssen.|
      |**Information E-Mail**|Diese E-Mail wird vom Ersteller des Express-Workflows definiert und automatisch ohne Benutzeraktion versendet.|
      |**Genehmigung**|ieser Aufgabentyp erstellt eine Genehmigungsaufgabe.|

   * Gib einen aussagekräftigen Namen unter **Aufgabenname** ein.
   * Optional kannst du eine Beschreibung unter **Aufgabenbeschreibung** hinzufügen.

10. Für den Prozesstyp *Einmalig* definiert der erste Prozessschritt die Benutzer oder Rollen unter **Startberechtigung**, die den Prozess starten dürfen.

![able-to-start](images/able-to-start.png)

11. Für alle anderen Prozessschritte definiere die Benutzer oder Rollen, die für die Ausführung der Aufgabe verantwortlich sind, unter **Verantwortlich**.

![responsible](images/responsible.png)

12. Für jeden Prozessschritt (außer dem ersten) definiere die Zeit, bevor die Aufgabe abläuft, unter **Fälligkeitsfrist in Tagen**.
13. Nachdem du alle Schritte definiert hast, klicke auf die Schaltfläche **Weiter**, um die Details jedes Schrittes zu konfigurieren.

![express-preview](images/express-preview.png)

#### Prozessschritte konfigurieren

![express-add-input](images/express-add-input.png)

Wenn der Express-Prozess einen Schritt vom Typ **Aufgabe** oder **Aufgabe mit E-Mail** enthält, wird die Seite **Formular-definition** geöffnet. Mit diesem Editor kannst du eine Benutzeroberfläche für jede Aufgabe der genannten Typen erstellen. Die **Formular-definition** stellt bereits die notwendigen UI-Elemente bereit, die du nach Belieben zur Benutzeroberfläche des Prozessschrittes hinzufügen kannst.

   |**UI-Element**|**Beschreibung und Optionen**|
   | --------------- | ------------------------- |
   |**Eingabefeld**|Eingabefeld für Text, Zahlen oder Daten  <br>   - Textfeld  <br>   - Nummernfeld  <br>   - Datumsauswahl|
   |**Eingabebereich**|Text-Eingabefeld mit 1 bis 10 Zeilen|
   |**Auswahlfeld**|Liste von Elementen, die dem Benutzer eine Mehrfachauswahl ermöglichen|
   |**Optionsfeld**|Liste von Elementen, die dem Benutzer eine Einzelne Auswahl ermöglichen|
   |**Dateiupload**|Bietet dem Benutzer ein Dialogfeld zum Hochladen von Dateien. Du kannst definieren:  <br>   - Erlaubte Dateitypen <br>   - Anzahl der erlaubten Anhänge|

1. Wähle ein UI-Element aus und konfiguriere es. Jedes UI-Element hat eigene Konfigurationsmöglichkeiten Zum Beispiel, wenn du ein **Eingabefeld** hinzufügen möchtest, könntest du es wie folgt konfigurieren:

   * Gib einen aussagekräftigen Namen für das Eingabefeld unter **Label** ein.
   * Wähle den Eingabetyp unter **Eingabe-Typ** aus.
   * Gib an, ob das Eingabefeld für dieses Datenelement erforderlich ist, indem du den Schalter **Dies ist ein Pflichtfeld** umschaltest.

2. Nachdem du die Einstellungen konfiguriert hast, klicke auf die Schaltfläche Erstellen, um das UI-Element zur Liste der **Formularelemente** hinzuzufügen.
3. Für jedes UI-Element in der Liste der **Formularelemente** kannst du auf die Papierkorb-Schaltfläche klicken, um es zu löschen.
4. Um die Position der UI-Elemente im Layout des Prozessschrittes zu konfigurieren, ziehe jedes UI-Element aus der Liste der **Formularelemente** in einen der Bereiche der **Platzierung der Formularelemente**-Sektion.
5. Klicke auf die Schaltfläche **Vorschau** oben in der **Platzierung der Formularelemente**-Sektion, um die Vorschau-Version zu sehen.
6. Für Schritte vom Typ **Information E-Mail** wird stattdessen der E-Mail-Editor angezeigt. Hier kannst du die Informationen für den E-Mail-Schritt konfigurieren:

   * **E-Mail Adresse**: E-Mail-Adressen der Empfänger, durch Komma getrennt.
   * **Antwort an**: Die Antwort-E-Mail-Adresse. Dieses Feld ist optional.
   * **Betreff**: Der Betreff der E-Mail.
   * **Emailnachricht**: Der Inhalt der E-Mail.
   * **Anhang**: Anhänge, die du zusammen mit der E-Mail senden möchtest. Dieses Feld ist optional.

7. Nachdem du die Benutzeroberfläche für einen Schritt konfiguriert hast, klicke auf die Schaltfläche **Weiter**, um den nächsten Schritt zu definieren.

#### Express Geschäftsübersicht

Seit Version 12.0.0 kann Axon Ivy Express unabhängig vom Axon Ivy Portal betrieben werden. Es wird jedoch dringend empfohlen, das Portal zu nutzen, um Axon Ivy Express-Fälle und -Aufgaben zu verwalten. Durch die Nutzung des Portals kannst du die Zusammenfassungsdaten jedes Express-Falles einsehen, indem du die Seite **Business Details** dieses Falls im Portal aufrufst.

![express-business-case-detail](images/express-business-case-detail.png)

#### Express-Management

Die **Express-Management** ermöglicht es dir, Express-Prozesse effizient zu verwalten. Darüber hinaus kannst du Express-Workflows mit JSON-Dateien importieren/exportieren.

![Express management](images/express-management-process.png)

Um auf diese Funktion zugreifen zu können, musst du die Rolle **AXON_IVY_EXPRESS_ADMIN** zugewiesen bekommen.

##### HowTo: Exportiere einen Express-Workflow

1. Suche im Express-Workflows-Tabellenbereich den gewünschten Workflow und klicke auf das Menü-Symbol.
2. Wähle im Dropdown-Menü **Exportieren** aus.

![export-express](images/export-express.png)

Der Workflow wird automatisch als JSON-Datei heruntergeladen.

**Wichtig**: Die exportierte Datei ist ein JSON-Dokument, das die Versionsinformationen von Axon Ivy Express und die Express-Prozessdaten enthält. Vermeide es, diese Datei manuell zu bearbeiten, um die Datenintegrität zu gewährleisten.

##### HowTo: Importiere einen Express-Prozess

Die Funktion **Express-Prozess importieren** ermöglicht es dem Administrator, Express-Prozesse aus einer Backup-JSON-Datei in das Portal zu importieren.
Klicke auf die Schaltfläche **Importieren**, der Dialog zum Importieren von Express-Prozessen wird angezeigt. Du kannst auf die Schaltfläche **Auswählen** klicken und die Express-JSON-Datei auswählen, die die Workflows enthält, die du importieren möchtest.

![import-express-dialog](images/import-express-dialog.png)

Drücke anschließend die Schaltfläche **Hochladen** und warte, bis der Bereitstellungsprozess abgeschlossen ist.

![import-express-action](images/import-express-action.png)

Nach Abschluss des Bereitstellungsprozesses wird ein Ausgabenprotokoll-Panel angezeigt. Du kannst alle Informationen einsehen, die während des Bereitstellungsprozesses gesammelt wurden.

Wenn der Bereitstellungsprozess erfolgreich war, sind deine Workflows importiert, und ein Administrator kann sie überprüfen und/oder bearbeiten, bevor sie bereit zur Nutzung sind.

![import-express-status](images/import-express-status.png)

## Migration

### Migration auf 12.0.0

**Axon Ivy Express** ist jetzt eine eigenständige Anwendung und benötigt nicht mehr das Axon Ivy Portal. Darüber hinaus wurde es aktualisiert, um mit der neuesten Struktur des Axon Ivy Market-Projekttemplates übereinzustimmen.

Wichtig: Workflows, die in früheren Versionen von Express erstellt wurden, sind mit Axon Ivy Express 12 nicht kompatibel. Um deine alten Workflows weiterhin zu verwenden:

Exportiere alle Workflows aus der vorherigen Version.

Importiere sie in das neue Axon Ivy Express über die Workflow [importieren-Funktion](#howto-importiere-einen-express-prozess).