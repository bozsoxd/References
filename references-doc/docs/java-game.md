---
title: Local PvP Game in Java
sidebar_position: 4
---

## Description

A szoftver egy két csapatból álló stratégiai játék, ahol az egyik csapat feladata a víz eljuttatása a végcélba, míg a másik csapat célja ennek megakadályozása.
Első körben egy vázat készítünk el, amely a készülő program alapvető kapcsolatait és felépítését szemlélteti.
Ezután egy már játszható, de még nem teljes változat készül el.
Végül a grafikus megjelenítés és a teljes játék valósul meg.

## Use-Case Diagram

![Use-Case diagram](../static/img/java-pvp-use-case.jpg)

## Class Diagram

![class diagram](../static/img/java-pvp-class.png)

## Installation

`javac *.java` kóddal tudjuk lefordítani a kódot, ezt a parancsot a projekt mappán belül kell kiadni.

## How to run

`java Main` paranccsal tudjuk futtatni a kódot, abban a mappában, ahol fordítottuk a
kódot.

## Screenshots

### Main screen
![Menu](../static/img/java-game-menu.png)
:::info
Megadhatjuk a kezdőképernyőn a csapatok játékosainak számát.
:::

### Game
![Game](../static/img/java-game.png)
:::info
Az aktuális játékost piros négyzet jelöli.  
Jobb oldalon láthatók az aktuálisan végrehajtható akciók és a játékállás.
:::

## Projects Content

|Folder(s)/File(s)|about|
|-|-|
|javadoc|A project Javadoc dokumentációja|
|src|A projekt maga|
|Dokumentáció.pdf|A végső dokumentáció|