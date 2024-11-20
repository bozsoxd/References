#include <fstream>
#include "Csapatok.h"
#include "string5.h"
#include "memtrace.h"

//cpy ctor
Csapatok::Csapatok(const Csapatok& cpy)
{
	nev = cpy.nev;
	alapletszam = cpy.alapletszam;
}

//op=
Csapatok Csapatok::operator=(Csapatok rhs) {
	if (this != &rhs) {
		alapletszam = rhs.alapletszam;
		nev = rhs.nev;
	}
	return *this;
}
//név beallatas
void Csapatok::setname(String setthisname) {
	nev = setthisname;
}
//meret beallitas
void Csapatok::setsize(size_t setthissize) {
	alapletszam = setthissize;
}

//nev lekerdezes
String Csapatok::getname() const{
	return nev;
}

//meret lekerdezes
size_t Csapatok::getsize() const{
	return alapletszam;
}
//Kiírás os-re
void Csapatok::kiir() {

}
//fileba iras
void Csapatok::fkiir(std::ofstream& filenev) {

}
//dtor
Csapatok::~Csapatok() {

}

//1. edzo nevenek lekerdezese
String Labdarugas::lek1() const{
	return edzo1;
}

//2. edzo nevenek lekerdezese
String Labdarugas::lek2() const{
	return edzo2;
}
//1. edzo nevenek megadasa
void Labdarugas::set1(String name) {
	edzo1 = name;
}

//2. edzo nevenek megadasa
void Labdarugas::set2(String name) {
	edzo2 = name;
}

//cpy ctor
Labdarugas::Labdarugas(const Labdarugas& cpy) {
	this->setname(cpy.getname());
	this->setsize(cpy.getsize());
	this->set1(cpy.lek1());
	this->set2(cpy.lek2());
}

//op=
Labdarugas Labdarugas::operator=(Labdarugas rhs) {
	if (this != &rhs) {
		this->setname(rhs.getname());
		this->setsize(rhs.getsize());
		this->set1(rhs.lek1());
		this->set2(rhs.lek2());
	}
	return *this;
}

//Kiírás os-re
void Labdarugas::kiir() {
	std::cout << "Sportag: Labdarugas\n" << "Csapat nev: " << this->getname() << "\nCsapat meret: " << this->getsize() << "\nEdzo1: " << this->lek1() << "\nEdzo2: " << this->lek2() << std::endl;
}

//Kiírás fileba
void Labdarugas::fkiir(std::ofstream& filenev) {
	filenev << "FOCI," << this->getname() << "," << this->getsize() << "," << this->lek1() << "," << this->lek2() << std::endl;
}

//dtor
Labdarugas::~Labdarugas() {
}


//cpy ctor
Kosarlabda::Kosarlabda(const Kosarlabda& cpy) {
	this->setname(cpy.getname());
	this->setsize(cpy.getsize());
	this->setpp(cpy.getpp());
}

//op=
Kosarlabda Kosarlabda::operator=(Kosarlabda rhs) {
	if (this != &rhs) {
		this->setname(rhs.getname());
		this->setsize(rhs.getsize());
		this->setpp(rhs.getpp());
	}

	return *this;
}

//pom-pom lanyok szamanak megadasa
void Kosarlabda::setpp(size_t set){
	pplanyok = set;
}

//pom-pom lanyok szamanak lekerdezese
size_t Kosarlabda::getpp() const {
	return pplanyok;
}

//Kiírás os-re
void Kosarlabda::kiir() {
	std::cout << "Sportag: Kosarlabda\n" << "Csapat nev: " << this->getname() << "\nCsapat meret: " << this->getsize() << "\nPom-pom lanyok szama: " << this->getpp() << std::endl;
}
//Kiírás fileba
void Kosarlabda::fkiir(std::ofstream& filenev) {
	filenev << "KOSAR," << this->getname() << "," << this->getsize() << "," << this->getpp() << std::endl;
}

//dtor
Kosarlabda::~Kosarlabda(){
}

//cpy ctor
Kezilabda::Kezilabda(const Kezilabda& cpy){
	this->setname(cpy.getname());
	this->setsize(cpy.getsize());
	this->settam(cpy.gettam());
}

//op=
Kezilabda Kezilabda::operator=(Kezilabda rhs) {
	if (this != &rhs) {
		this->setname(rhs.getname());
		this->setsize(rhs.getsize());
		this->settam(rhs.gettam());
	}
	return *this;
}

//tamogatas megadasa
void Kezilabda::settam(int tam) {
	tamogatas = tam;
}

//tamogatas lekerdezese
int Kezilabda::gettam() const {
	return tamogatas;
}

//Kiírás os-re
void Kezilabda::kiir(){
	std::cout << "Sportag: Kezilabda\n" << "Csapat nev: " << this->getname() << "\nCsapat meret: " << this->getsize() << "\nTamogatas: " << this->gettam() << std::endl;
}

//Kiírás fileba
void Kezilabda::fkiir(std::ofstream& filenev) {
	filenev << "KEZI," << this->getname() << "," << this->getsize() << "," << this->gettam() << std::endl;
}

//dtor
Kezilabda::~Kezilabda(){
	
}
