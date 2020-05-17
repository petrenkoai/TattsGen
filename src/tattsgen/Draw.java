
package tattsgen;

import javafx.beans.property.StringProperty;

public class Draw {

    Ball firstBall;
    Ball secondBall;
    Ball thirdBall;
    Ball fourthBall;
    Ball fifthBall;
    Ball sixthBall;
    Ball firstSupBall;
    Ball secondSupBall;
    
    public Draw ( Ball firstBall, Ball secondBall, Ball thirdBall, Ball fourthBall, Ball fifthBall, Ball sixthBall ){
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.thirdBall = thirdBall;
        this.fourthBall = fourthBall;
        this.fifthBall = fifthBall;
        this.sixthBall = sixthBall;
        firstSupBall = null;
        secondSupBall = null;
    }
    
    public Draw ( Ball firstBall, Ball secondBall, Ball thirdBall, Ball fourthBall, Ball fifthBall, Ball sixthBall, Ball firstSupBall, Ball secondSupBall ){
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.thirdBall = thirdBall;
        this.fourthBall = fourthBall;
        this.fifthBall = fifthBall;
        this.sixthBall = sixthBall;
        this.firstSupBall = firstSupBall;
        this.secondSupBall = secondSupBall;
    }

    public int getFirstBall() {
        return firstBall.getNumber();
    }

    public int getSecondBall() {
        return secondBall.getNumber();
    }

    public int getThirdBall() {
        return thirdBall.getNumber();
    }

    public int getFourthBall() {
        return fourthBall.getNumber();
    }

    public int getFifthBall() {
        return fifthBall.getNumber();
    }

    public int getSixthBall() {
        return sixthBall.getNumber();
    }

    public int getFirstSupBall() {
        return firstSupBall.getNumber();
    }

    public int getSecondSupBall() {
        return secondSupBall.getNumber();
    }
    
    public int[] getNumbers(){
        int[] drawNumbers = new int[6];
        drawNumbers[0] = this.getFirstBall();
        drawNumbers[1] = this.getSecondBall();
        drawNumbers[2] = this.getThirdBall();
        drawNumbers[3] = this.getFourthBall();
        drawNumbers[4] = this.getFifthBall();
        drawNumbers[5] = this.getSixthBall();
        return drawNumbers;
    }
    
    public int[] getNumbersWithSup(){
        int[] drawNumbers = new int[8];
        drawNumbers[0] = this.getFirstBall();
        drawNumbers[1] = this.getSecondBall();
        drawNumbers[2] = this.getThirdBall();
        drawNumbers[3] = this.getFourthBall();
        drawNumbers[4] = this.getFifthBall();
        drawNumbers[5] = this.getSixthBall();
        drawNumbers[6] = this.getFirstSupBall();
        drawNumbers[7] = this.getSecondSupBall();
        return drawNumbers;
    }
    
    public boolean oddEvenCheck(){
        int b = 0;
        if (firstBall.isEven()) b++;
        if (secondBall.isEven()) b++;
        if (thirdBall.isEven()) b++;
        if (fourthBall.isEven()) b++;
        if (fifthBall.isEven()) b++;
        if (sixthBall.isEven()) b++;
        return (b == 2 || b == 4);
    }
    
    public int getSum(){
        int sum;
        sum = (this.firstBall.getNumber() + this.secondBall.getNumber() + this.thirdBall.getNumber() + this.fourthBall.getNumber() + this.fifthBall.getNumber() + this.sixthBall.getNumber());
        return sum;
    }
    
    public String toString(){
        String str = firstBall.toString() + ", " + secondBall.toString() + ", " + thirdBall.toString() + ", " + fourthBall.toString() + ", " + fifthBall.toString() + ", " + sixthBall.toString();
        return str;
    }
}
