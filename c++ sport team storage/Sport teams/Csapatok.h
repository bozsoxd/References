#pragma once
#ifndef CSAPATOK_H
#define CSAPATOK_H
#include "string5.h"
#include "memtrace.h"


//�soszt�ly
class Csapatok {
	String nev;				//Csapat neve
	size_t alapletszam;		//Csapat l�tsz�ma
public:
	Csapatok(String nev = "", size_t alapletszam = 0) :nev(nev), alapletszam(alapletszam) { }	
	Csapatok(const Csapatok& cpy);
	virtual Csapatok operator=(Csapatok rhs);
	void setname(String setthisname);
	void setsize(size_t setthissize);
	String getname() const;
	size_t getsize() const;
	virtual void kiir();
	virtual void fkiir(std::ofstream& filenev);
	virtual ~Csapatok();
};


//Lesz�rmazott oszt�ly, �r�kli a csapatok nev�t �s l�tsz�m�t
class Labdarugas :public Csapatok {
	String edzo1;		//Csapat edz�i
	String edzo2;		//Csapat edz�i
public:
	Labdarugas(String nev, size_t alapletszam, String edzo1, String edzo2):Csapatok(nev, alapletszam), edzo1(edzo1), edzo2(edzo2){}
	Labdarugas(const Labdarugas& cpy);
	Labdarugas operator=(Labdarugas rhs);
	String lek1() const;
	String lek2() const;
	void set1(String name);
	void set2(String name);
	void kiir();
	void fkiir(std::ofstream& filenev);
	~Labdarugas();
};

//Lesz�rmazott oszt�ly, �r�kli a csapatok nev�t �s l�tsz�m�t
class Kosarlabda :public Csapatok {
	size_t pplanyok;		//pom-pom l�nyok sz�ma
public:
	Kosarlabda(String nev, size_t alapletszam, size_t pplanyok):Csapatok(nev, alapletszam), pplanyok(pplanyok){}
	Kosarlabda(const Kosarlabda& cpy);
	Kosarlabda operator=(Kosarlabda rhs);
	void setpp(size_t set);
	size_t getpp() const;
	void kiir();
	void fkiir(std::ofstream& filenev);
	~Kosarlabda();
};


//Lesz�rmazott oszt�ly, �r�kli a csapatok nev�t �s l�tsz�m�t

class Kezilabda :public Csapatok {
	int tamogatas;			//Csapat t�mogat�sa
public:
	Kezilabda(String nev, size_t alapletszam, int tamogatas):Csapatok(nev, alapletszam), tamogatas(tamogatas){}
	Kezilabda(const Kezilabda& cpy);
	Kezilabda operator=(Kezilabda rhs);
	void settam(int tam);
	int gettam() const;
	void kiir();
	void fkiir(std::ofstream& filenev);
	~Kezilabda();
};



#endif // !CSAPATOK_H



