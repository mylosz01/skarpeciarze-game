package com.skarpeta.skarpeciarzegame.tools;

import java.io.Serializable;

/** Klasa służąca do wykonywania obliczeń na punktach typu Integer
 *  Opakowana również w funkcjonalności ekskluzywne dla matematyki na heksagonach
 */

public class Point implements Serializable {
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

    /** Zwraca true gdy punkt posiada przynajmniej jedną wartość ujemną */
    public boolean isNegative() {
        return x < 0 || y < 0;
    }

    /** Zwraca punkt pomiędzy dwoma punktami (wynik jest zaokrąglany w dół)*/
    Point between(Point point) {
       return new Point((this.x + point.x)/2, (this.y + point.y)/2);
    }

    /** Zwraca true gdy podany punkt leży na którejkolwiek z przekątnych */
    boolean isDiagonalTo(Point point) {
        return yDiffAbs(point) == xDiffAbs(point);
    }
    /** Zwraca true gdy podany punkt jest jednym z ośmiu sąsiadów */
    public boolean isNextTo(Point point) {
        return yDiffAbs(point) <= 1 && xDiffAbs(point) <= 1;
    }

    /** Zwraca true gdy podany punkt jest jednym z sześciu sąsiadów punktu w tablicy heksagonalnej (nieparzyste rzędy przesunięte są do góry) */
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

    /** Zwraca odległość między dwoma punktami (wynik jest zaokrąglany w dół)*/
    public int diagonalDistance(Point point) {
        if(isDiagonalTo(point))
        {
            return (xDiffAbs(point) + yDiffAbs(point)) / 2;
        }
        return -1;
    }

    /** Różnica na osi x (wartość bezwzględna)*/
    int xDiffAbs(Point point) {
        return Math.abs(yDiff(point));
    }
    /** Różnica na osi y (wartość bezwzględna)*/
    int yDiffAbs(Point point) {
        return Math.abs(xDiff(point));
    }
    /** Różnica na osi x*/
    int xDiff(Point point) {
        return this.x - point.x;
    }
    /** Różnica na osi y*/
    int yDiff(Point point) {
        return this.y - point.y;
    }

    /** Zwraca punkt będący sumą wektorów */
    public Point add(int x, int y) {
        return new Point(this.x+x,this.y+y);
    }
    /** Zwraca punkt będący sumą wektorów */
    public Point add(Point p) {
        return add(p.x,p.y);
    }
    /** Zwraca punkt będący różnicą wektorów */
    public Point subtract(int x, int y) {
        return new Point(this.x-x,this.y-y);
    }

    /** Zwraca punkt będący różnicą wektorów */
    public Point subtract(Point p) {
        return subtract(p.x,p.y);
    }

    /** Zwraca punkt będący o jedną wartość bliżej punktu towards licząc od source.
     *  Służy do pathfindingu
     */
    public static Point moveTowards(Point source, Point towards) {
        Point vector = towards.subtract(source);
        return vector.normalize().add(source);
    }

    /** Zwraca punkt podzielony przez wartość bezwzględną z siebie. Wartośći takiego punktu to jedynie -1, 0 lub 1 */
    private Point normalize() {
        Point p = new Point(this);
        if(p.x!=0)
            p.x/=Math.abs(p.x);
        if(p.y!=0)
            p.y/=Math.abs(p.y);
        return p;
    }

    /** Przemieszcza punkt o podany wektor */
    private void move(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public String toString() {
        return "[" + this.x.toString() + ", " + this.y.toString() + "]";
    }

    /** Zwraca true gdy suma x i y jest parzysta */
    public boolean isEven() {
        return (x+y)%2==0;
    }
}
