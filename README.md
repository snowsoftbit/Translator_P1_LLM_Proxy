## General

Dies ist der GitHub Repo für unser LLM-Proxy-Klasse Projekt in OOP2. Das Repository ist [hier](https://github.com/snowsoftbit/Translator_P1_LLM_Proxy) abrufbar. Ziel war es ein Translator Tool zu erstellen welches...

-(Idee)Eigene Java-Klasse kapselt Zugriff auf
ein LLM (z.B. OpenAI API). Die
Anwendung nutzt die Proxy-Klasse
für KI-gestützte Features.

-(GUI Swing) Chat-Interface, Verlaufsanzeige,
Modell-Auswahl, Prompt-Templates –
alles mit Swing-Komponenten
realisiert.

-(Persistenz) Gesprächsverläufe, API-Schlüssel
(verschlüsselt) und Einstellungen
werden in Dateien oder einer
Datenbank gespeichert.

## Root Folder

```text
Translator_P1_LLM_Proxy/
├── data/
│   └── history.json
├── assets/
│   └── hwr_logo.png
│
├── src/
│   └── main/
│       ├── resources/
│       │    └── images/
│       │        └── HWR_LOGO.png
│       └── java/
│           ├── app/
│           │   └── Main.java
│           │
│           ├── model/
│           │   ├── ChatEntry.java
│           │   ├── TranslationRequest.java
│           │   └── TranslationResponse.java
│           │
│           ├── persistence/
│           │   ├── ChatHistoryDAO.java
│           │   └── FileChatHistoryDAO.java
│           │
│           ├── service/
│           │   ├── LlmClient.java
│           │   ├── LlmProxy.java
│           │   └── TranslationService.java
│           │
│           └── ui/
│               ├── HeaderPanel.java
│               ├── HistoryPanel.java
│               ├── MainFrame.java
                ├── StatusPanel.java
│               └── TranslationPanel.java
│
├── .gitignore
├── pom.xml
├── README.md
└── TRANSLATOR_P1_LLMPROXY.env.example
```

## Team Mitglieder und Rollen

- Zuständig für Fenster und Grundlayout:[@leonjonasilg-design](https://github.com/leonjonasilg-design)

- Zuständig für Service, API und Integration: [@snowsoftbit](https://github.com/snowsoftbit)

- Zuständig für Daten und JSON-Speicherung:[@LeanderGuelland](https://github.com/Burak92)

- Zuständig für Übersetzungsbereich:[@Burak92](https://github.com/LeanderGuelland)

## Bedienung und Anleitung für die Nutzung der App

![Demo Gif of APP](XXXX)

1.
2.
3.
4.
