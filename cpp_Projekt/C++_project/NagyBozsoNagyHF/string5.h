#ifndef STRING_H
#define STRING_H
#include <iostream>      
#include "memtrace.h"
class String {
    char *pData;        //< pointer az adatra
    size_t len;         //< hossz lezáró nulla nélkül
public:
    // Paraméter nélküli konstruktor:
    String() :pData(0), len(0) {
        len = 0;
        pData = new char[1];
        pData[0] = '\0';
    };

    // Sztring hosszát adja vissza.
    // @return sztring tényleges hossza (lezáró nulla nélkül).
    size_t size() const { return len; }

    // C-sztringet ad vissza
    // @return pointer a tárolt, vagy azzal azonos tartalmú nullával lezárt sztring-re.
    char* c_str() const { 
        return pData;
    }
    //Osztaly fuggvenyei, reszletes leirasuk a string5.cpp-ben
    String(char s);
    String(char const* s);
    String(const String& masik);
    String& operator=(const String& masik);
    String operator+(String const& rhs) const;
    String operator+(char c);
    String& operator+=(char c);
    char& operator[](unsigned int i);
    int konverttoint();
    size_t konverttosizet();
    ~String();
    

};

//Globalis fuggvenyek es operatorok. Reszletes leirasuk a string5.cpp-ben
String operator+(String const& lhs, char rhs);
std::ostream& operator<<(std::ostream& os, String const& rhs);
bool operator==(const String& c1, const String& c2);
void fgetlineString(std::ifstream& is, String& str, char delim);
std::istream& getlineString(std::istream& is, String& str);


#endif
