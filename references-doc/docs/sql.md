---
title: Database Management
sidebar_position: 8
---

## Description

Ezen projekten belül két komplex önállóan megoldott mérés jegyzőkönyve található, mely bemutatni hivatoot az SQL tudásomat lekérdezéseken és adatmódosításokon keresztül. A feladatok megoldásához az Oracle adatbáziskezelő szoftverét használtam.

## examples

1. Könyvtári adatbázisból a legalább 3x kikölcsönzött könyvek lekérdezése.

```sql
SELECT books.author, books.title, books.subtitle  
FROM books, borrows  
WHERE books.bookid=borrows.bookid  
GROUP BY author, title, subtitle  
HAVING COUNT(borrows.borrowid)>=3  
ORDER BY books.author, books.title, books.subtitle;
```

2. Törlés beágyazott lekérdezéssel

```sql
DELETE FROM books  
WHERE books.bookid  
IN  
    (SELECT books.bookid  
    FROM books, borrows  
    WHERE books.bookid=borrows.bookid(+)  
    AND books.cover=1  
    AND borrows.borrowid IS NULL  
    AND ADD_MONTHS(SYSDATE,-108)>books.dateofpublish);
```