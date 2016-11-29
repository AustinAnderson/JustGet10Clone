/*
testIndexing.cpp: ...
Created: Fri Sep 23 11:03:37 CDT 2016
*/
#include <iostream>
#include <sstream>
using namespace std;
string adjlist(int x, int n){
    stringstream toReturn;
    if((x-n)>=0){
        toReturn<<(x-n)<<" ";
    }
    if(x%n!=0){
        toReturn<<(x-1)<<" ";
    }
    if((x+1)%n!=0){
        toReturn<<(x+1)<<" ";
    }
    if((x+n)<(n*n)){
        toReturn<<(x+n)<<" ";
    }
    return toReturn.str();
}

int main(int argc, char** argv){
    int n=4;
    for(int i=0;i<(n*n);i++){
        cout<<i<<"   "<<adjlist(i,n)<<endl;
    }
    return 0;
}
