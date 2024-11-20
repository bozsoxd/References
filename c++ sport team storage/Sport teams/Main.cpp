#include <iostream>
#include <fstream>
#include <cstring>
#include "string5.h"
#include "Csapatok.h"
#include "Tarolo.hpp"
#include "memtrace.h"
#include "gtest_lite.h"
using std::cout;
using std::endl;
using std::cin;

int main()
{
#ifdef CPORTA
	
	Tarolo<Csapatok> tar;
	String olvasott;
	std::ifstream olvasfile("adatok.txt");
	while (olvasfile) {
		fgetlineString(olvasfile, olvasott, '\n');
		char* felvagott = std::strtok(olvasott.c_str(), ",");
		if (olvasott.size() == 0) { break; }
		if (std::strcmp(felvagott, "FOCI") == 0) {
			String olvasottomb[4];
			int i = 0;
			while (felvagott != NULL) {
				felvagott = strtok(NULL, ",");
				if (felvagott != NULL) {
					olvasottomb[i] = felvagott;
					++i;
				}
			}
			Csapatok* la = new Labdarugas(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2], olvasottomb[3]);
			tar.add(la);
		}
		else if (strcmp(felvagott, "KOSAR") == 0) {
			String olvasottomb[3];
			int i = 0;
			while (felvagott != NULL) {
				felvagott = strtok(NULL, ",");
				if (felvagott != NULL) {
					olvasottomb[i] = felvagott;
					++i;
				}
			}
			Csapatok* ko = new Kosarlabda(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2].konverttosizet());
			tar.add(ko);
		}
		else if (strcmp(felvagott, "KEZI") == 0) {
			String olvasottomb[3];

			int i = 0;
			while (felvagott != NULL) {
				felvagott = strtok(NULL, ",");
				if (felvagott != NULL) {
					olvasottomb[i] = felvagott;
					++i;
				}

			}
			Csapatok* ke = new Kezilabda(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2].konverttoint());
			tar.add(ke);
		}
	}
	olvasfile.close();

	cout << "Csapatok:\n\n";
	tar.listaz();


	TEST(Tarolo, kivetel) {
		EXPECT_ANY_THROW(tar[5]);
	}
	END

	TEST(Tarolo, csapatnev){
		String teszt = tar[0]->getname();
		EXPECT_STREQ("Kezinev", teszt.c_str());
	}
	END

	TEST(Tarolo, letszam) {
		int teszt = tar[1]->getsize();
		cout << teszt;
		EXPECT_EQ(20, teszt);
	}
	END
	cout << "\n\nCsapat hozzaadas\n\n";
	Csapatok* la = new Labdarugas("Ujfocicsapat", 23, "Jancsi", "Juliska");
	tar.add(la);

	

	tar.listaz();

	cout << "csapat torlese\n\n";
	tar.del(3);

	tar.listaz();

	cout << "Csapatok vissaz irasa fileba\n";

	std::ofstream file("adatok.txt");
	tar.flistaz(file);
	file.close();


#endif


#ifndef CPORTA
		



	while (true) {

#pragma region beolvas



		Tarolo<Csapatok> tar;
		String olvasott;
		std::ifstream olvasfile("adatok.txt");
		while (olvasfile) {
			fgetlineString(olvasfile, olvasott, '\n');
			char* felvagott = std::strtok(olvasott.c_str(), ",");
			if (olvasott.size() == 0) { break; }
			if (std::strcmp(felvagott, "FOCI") == 0) {
				String olvasottomb[4];
				int i = 0;
				while (felvagott != NULL) {
					felvagott = strtok(NULL, ",");
					if (felvagott != NULL) {
						olvasottomb[i] = felvagott;
						++i;
					}
				}
				Csapatok* la = new Labdarugas(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2], olvasottomb[3]);
				tar.add(la);
			}
			else if (strcmp(felvagott, "KOSAR") == 0) {
				String olvasottomb[3];
				int i = 0;
				while (felvagott != NULL) {
					felvagott = strtok(NULL, ",");
					if (felvagott != NULL) {
						olvasottomb[i] = felvagott;
						++i;
					}
				}
				Csapatok* ko = new Kosarlabda(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2].konverttosizet());
				tar.add(ko);
			}
			else if (strcmp(felvagott, "KEZI") == 0) {
				String olvasottomb[3];

				int i = 0;
				while (felvagott != NULL) {
					felvagott = strtok(NULL, ",");
					if (felvagott != NULL) {
						olvasottomb[i] = felvagott;
						++i;
					}

				}
				Csapatok* ke = new Kezilabda(olvasottomb[0], olvasottomb[1].konverttosizet(), olvasottomb[2].konverttoint());
				tar.add(ke);
			}
		}
		olvasfile.close();
#pragma endregion

		int menu = 0;
		cout << "1. Csapatok listazas\n2.Csapat felvetele\n3.kilepes\n";
		while (menu < 1 || menu > 3) {
			cin >> menu;
			if (menu < 1 || menu > 3) {
				cout << "\nHibas menupont, probalja ujra:";
			}
		}
		if (menu == 1) {
			tar.listaz();
			int torol = 2;
			cout << "Akar csapatot torolni? (0/1)\n";
			while (torol < 0 || torol > 1) {
				cin >> torol;
				if (torol < 0 || torol > 1) {
					cout << "Hibas valasz, probalja ujra:";
				}
			}
			if (torol == 0) {

			}
			else if (torol == 1) {
				int melyik;
				cout << "\nMelyik csapatot akarja torolni?\n";
				cin >> melyik;
				tar.del(melyik);
				std::ofstream file("adatok.txt");
				tar.flistaz(file);
				file.close();
			}

		}
		else if (menu == 2) {
			int csapat = 0;
			cout << "Valassza ki, hogy milyen csapatot akar hozzaadni!\n1\tfoci\n2\tkosar\n3\tkezi\n";
			while (csapat < 1 || csapat > 3) {
				cin >> csapat;
				if (csapat < 1 || csapat > 3) {
					cout << "\nNem letezik ilyen csapat, probalja ujra:";
				}
			}
			if (csapat == 1) {
				String nev, ed1, ed2;
				size_t meret;
				cout << "\nCsapatnev: ";
				getchar();
				getlineString(cin, nev);
				cout << "\nMeret: ";
				cin >> meret;
				cout << "\n1. edzo: ";
				getchar();
				getlineString(cin, ed1);
				cout << "\n2. edzo: ";
				getchar();
				getlineString(cin, ed2);
				Csapatok* la = new Labdarugas(nev, meret, ed1, ed2);
				tar.add(la);
				std::ofstream file("adatok.txt");
				tar.flistaz(file);
				file.close();
			}
			else if (csapat == 2) {
				String nev;
				size_t meret, pompom;
				cout << "\nCsapatnev: ";
				getchar();
				getlineString(cin, nev);
				cout << "\nMeret: ";
				cin >> meret;
				cout << "\nPom-Pom lanyok szama:";
				cin >> pompom;
				Csapatok* ko = new Kosarlabda(nev, meret, pompom);
				tar.add(ko);
				std::ofstream file("adatok.txt");
				tar.flistaz(file);
				file.close();
			}
			else if (csapat == 3) {
				String nev;
				size_t meret;
				int tam;
				cout << "\nCsapatnev: ";
				getchar();
				getlineString(cin, nev);
				cout << "\nMeret: ";
				cin >> meret;
				cout << "\nTamogatas osszege:";
				cin >> tam;
				Csapatok* ke = new Kezilabda(nev, meret, tam);
				tar.add(ke);
				std::ofstream file("adatok.txt");
				tar.flistaz(file);
				file.close();
			}

		}
		else if (menu == 3) {
			return 0;
		}

	}


#endif
	return 0;
}
