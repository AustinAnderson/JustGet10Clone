/*
test.cpp: ...
Created: Tue Jun 14 04:28:10 CDT 2016
*/
#include <iostream>
#include <vector>

using namespace std;
class Cell{
    public:
    Cell(int number){
        num=number;
    }
    void setGrid(void* g){
        grid=g;
    }
    void print(){
        cout<<"im number "<<num<<endl;
    }
    Cell* next();
    Cell* prev();
    private:
        int num;
        void* grid;

};
class Grid{
    public:
        friend class Cell;
        Grid(){
            for(int i=0;i<4;i++){
                cells.push_back(Cell(i));
                cells[i].setGrid(this);
            }
        }
        Cell* at(unsigned int i){
            return &cells[i];
        }
    private:
        vector<Cell> cells;

};
Cell* Cell::next(){
    Grid* g=(Grid*)grid;
    unsigned int nextNum=num+1;
    if(nextNum>=g->cells.size()){
        nextNum=0;
    }
    return &g->cells[nextNum];
}
Cell* Cell::prev(){
    Grid* g=(Grid*)grid;
    int nextNum=num-1;
    if(nextNum<0){
        nextNum=g->cells.size()-1;
    }
    return &g->cells[nextNum];
}

int main(int argc, char** argv){
    Grid bob;
    bob.at(0)->next()->print();
    bob.at(0)->next()->next()->print();
    bob.at(1)->print();
    return 0;
}
