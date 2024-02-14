#ifndef TAROLO_HPP
#define TAROLO_HPP
#include <iostream>
#include "string5.h"
#include "Csapatok.h"
#include "memtrace.h"

//heterogen kollekcio
template <typename T>
class Tarolo{
	T** tomb;
	size_t meret;
	Tarolo(const Tarolo&);
	Tarolo& operator=(const Tarolo&);
public:
	Tarolo() :meret(0) {}

	//aktualis meret lekerdezese
	size_t merete() {
		return meret;
	}
	
	//elem hozzaadasa. Bemenetk�nt kap egy T pointert �s ezt felveszi a heterog�n kollekci�ba
	void add(T* p) {
		T** temp = new T*[meret];
		for (size_t i = 0; i < meret; i++)
		{
			temp[i] = tomb[i];
		}
		if (meret > 0) {
			delete[] tomb;
		}
		++meret;
		tomb = new T* [meret];
		for (size_t i = 0; i < meret-1; i++)
		{
			tomb[i] = temp[i];
		}
		tomb[meret-1] = p;
		delete[] temp;
	}

	//valahanyadik elem torlese. Bemenetk�nt kap egy size_t valahanyadik elemet �s a heterog�n kollekci� azon elem�t kit�rli. Hiba eset�n Kiv�telt ad.
	void del(size_t a) {
		if (a >= meret || a < 0) {
			std::cout << "A tarolo hatarain kivulre mutat" << std::endl;
		}
		else {
			size_t ujcter = 0;
			T** temp = new T * [meret];
			for (size_t i = 0; i < meret; i++)
			{
				if (i != a) {
					temp[ujcter] = tomb[i];
					++ujcter;
				}
				else {
					delete tomb[i];
				}
			}
			delete[] tomb;
			--meret;
			tomb = new T * [meret];
			for (size_t i = 0; i < meret; i++)
			{
				tomb[i] = temp[i];
			}
			delete[] temp;
			
		}
		}
		

	//felszabad�tja a t�rol�t
	void clear() {
		for (size_t i = 0; i < meret; i++)
		{
			delete tomb[i];
		}
		meret = 0;
		delete[] tomb;
		
	}

	//indexel� [] oper�tor. size_t valahanyadik elemre mutat.
	T* operator[](size_t i) {
		if (i >= meret || i < 0) {
			throw "a tarolo hatarain kivulre mutat";
		}
		else {
			return tomb[i];
		}
	}

	//Ny�l oper�tor, mutat�val ell�tott f�ggv�nyek haszn�lat�nak lehet�v� t�tel�hez.
	T* operator->() {
		return *this;
	}
	
	//Kilist�zza a heterog�n kollekci� elemeit.
	void listaz() {
		for (size_t i = 0; i < meret; i++)
		{
			tomb[i]->kiir();
			std::cout << std::endl;
		}
	}
	//Kilist�zza fileba a heterog�n kollekci� elemeit.
	void flistaz(std::ofstream& filenev) {
		for (size_t i = 0; i < meret; i++)
		{
			tomb[i]->fkiir(filenev);
		}
	}

	//dtor
	~Tarolo() {
		clear();
	}

};




#endif // !TAROLO_HPP
