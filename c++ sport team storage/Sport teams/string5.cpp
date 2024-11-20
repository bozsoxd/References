#ifdef _MSC_VER
#define _CRT_SECURE_NO_WARNINGS
#endif

#include <iostream>             
#include <cstring>              
#include <fstream>
#include "memtrace.h"
#include "string5.h"




//char-ból stringet készítő konstrukto
String::String(char s) {
	len = 1;
	pData = new char[len + 1];
	pData[0] = s;
	pData[1] = '\0';
}

//Karaktertömbből stringet készítő konstruktor
String::String(char const* s) {
	len = strlen(s);
	pData = new char[len + 1];
	strcpy(pData, s);

}

// Másoló konstruktor: String-ből készít (createString)
String::String(const String& masik) {
	len = masik.len;
	pData = new char[len + 1];
	strcpy(pData, masik.pData);
}

// Destruktor
String::~String() {
	delete[] this->pData;
}

// operator=
String& String::operator=(const String& masik) {
	if (this != &masik) {
		delete[] pData;
		len = masik.len;
		pData = new char[len + 1];
		strcpy(pData, masik.pData);
	}
	return *this;
}

// [] operátorok: egy megadott indexű elem REFERENCIÁJÁVAL térnek vissza
// indexhiba esetén túlindexelés kivételt dob!
	
char& String::operator[]( unsigned int i) {
	if (i < 0 || i >= strlen(pData)) {
		throw "Tulindexeles";
	}
	return pData[i];
}


// + operátorok:
//String-hez jobbról karaktert ad
String String::operator+(const char c) {
	String uj;
	delete[] uj.pData;
	uj.len = len + 2;
	uj.pData = new char[uj.len];
	strcpy(uj.pData, pData);
	uj.pData[uj.len-2] = c;
	uj.pData[uj.len - 1] = '\0';
	return uj;
}
//String-hez String-et ad
String String::operator+(String const& rhs) const {
	String uj;
	delete[] uj.pData;
	uj.len = rhs.len + len;
	uj.pData = new char[uj.len + 1];
	strcpy(uj.pData, pData);
	strcat(uj.pData, rhs.pData);
	return uj;
}

//stringhez (önmagához) karaktert ad
String& String::operator+=(char c) {
	char tomb[2] = { c, '\0' };
	return (*this = *this + String(tomb));
}

//Karakterhez stringet ad jobbról
String operator+(char lhs, String const& rhs) {
	String lhsString(lhs);
	String uj = lhsString + rhs;
	return uj;
}
// << operator, ami kiír az ostream-re
std::ostream& operator<<(std::ostream& os, String const& rhs) {
	os << rhs.c_str();
	return os;
}

//Egy sort olvas be az istreamről
std::istream& getlineString(std::istream& is, String& str) {
	char ch;
	int i = 0;
	while (is.get(ch) && ch != '\n') {
		str += ch;
		i++;
	}
	return is;
}

//Két stringet hasonlít össze
bool operator==(const String& c1, const String& c2) {
	if (strcmp(c1.c_str(), c2.c_str()) == 0) {
		return true;
	}
	else return false;

}

//Stringet konvertál intre
int String::konverttoint() {
	return std::atoi(this->c_str());
}

//Stringet konvertál size_t-re
size_t String::konverttosizet() {
	unsigned int tmp = std::atoi(this->c_str());

	return  tmp;
}

//Beolvas egy sort fileból Stringként
void fgetlineString(std::ifstream& is, String& str, char delim) {
	char ch;
	String a;
	while (is.get(ch) && ch != delim && ch != '\n') {
		a += ch;
	}
	str = a;
	
}

