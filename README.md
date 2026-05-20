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
│
├── src/
│   └── main/
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
│           │   ├── LLMProxy.java
│           │   └── TranslationService.java
│           │
│           └── ui/
│               ├── HeaderPanel.java
│               ├── HistoryPanel.java
│               ├── MainFrame.java
│               └── TranslationPanel.java
│
├── .gitignore
├── pom.xml
├── README.md
└── TRANSLATOR_P1_LLMPROXY.env.example
```

## Team Mitglieder und Rollen

- Zuständig für XXX:

- Zuständig für XXXX: [@snowsoftbit](https://github.com/snowsoftbit)

- Zuständig für XXXX:

- Zuständig für XXXX:

## Bedienung und Anleitung für die Nutzung der App

![Demo Gif of APP](XXXX)

1.
2.
3.
4.
