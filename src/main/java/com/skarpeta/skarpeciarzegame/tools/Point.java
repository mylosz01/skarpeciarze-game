package com.skarpeta.skarpeciarzegame.tools;

import java.util.Objects;

public class Point {
    public Integer x;
    public Integer y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point1) {
        this.x=point1.x;
        this.y= point1.y;
    }

    public boolean isNegative() {
        return x < 0 || y < 0;
    }

    Point between(Point point) {
       return new Point((this.x + point.x)/2, (this.y + point.y)/2);
    }

    boolean isDiagonalTo(Point point) {
        return yDiffAbs(point) == xDiffAbs(point);
    }
    public boolean isNextTo(Point point) {
        return yDiffAbs(point) <= 1 && xDiffAbs(point) <= 1;
    }

    public boolean isTouchingHexagonal(Point point) {
        //ruchy lewo prawo (z perspektywy tablicy na "kwadratach")
        if(Math.abs(this.y - point.y) + Math.abs(this.x - point.x) == 1)
            return true;

        //dodatkowe sprawdzenie dla pól które dotykają się tylko z perspektywy heksagonów
        if(this.x%2==1) {
            //dla NIEparzystej wartosci X pola, poprawnym ruchem beda DOLNE przekatne (z perspektywy tablicy na "kwadratach")
            if((point.y - this.y)==1 && (Math.abs(point.x - this.x)) == 1)
                return true;
        }else {
            //dla PARZYSTEJ wartosci X pola, poprawnym ruchem beda GÓRNE przekatne (z perspektywy tablicy na "kwadratach")
            if((point.y - this.y)==-1 && (Math.abs(point.x - this.x)) == 1)
                return true;
        }
        return false;
    }

    public int diagonalDistance(Point point) {
        if(isDiagonalTo(point))
        {
            return (xDiffAbs(point) + yDiffAbs(point)) / 2;
        }
        return -1;
    }
    int xDiffAbs(Point point) {
        return Math.abs(this.x - point.x);
    }
    int yDiffAbs(Point point) {
        return Math.abs(this.x - point.x);
    }
    int xDiff(Point point) {
        return this.x - point.x;
    }
    int yDiff(Point point) {
        return this.y - point.y;
    }

    public Point add(int x, int y) {
        return new Point(this.x+x,this.y+y);
    }
    public Point add(Point p) {
        return new Point(this.x+p.x,this.y+p.y);
    }
    public Point subtract(int x, int y) {
        return new Point(this.x-x,this.y-y);
    }
    public Point subtract(Point p) {
        return new Point(this.x-p.x,this.y-p.y);
    }

    public static Point moveTowards(Point source, Point towards) {
        Point vector = towards.subtract(source);
        return vector.normalize().add(source);
    }

    private Point normalize() {
        Point p = new Point(this);
        if(p.x!=0)
            p.x/=Math.abs(p.x);
        if(p.y!=0)
            p.y/=Math.abs(p.y);
        return p;
    }

    private void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public String toString() {
        return "[" + this.x.toString() + ", " + this.y.toString() + "]";
    }

    public int isEven() {
        return (x+y)%2;
    }
}
