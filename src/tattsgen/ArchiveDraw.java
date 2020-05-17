
package tattsgen;

import java.util.Date;

public class ArchiveDraw extends Draw{

    private final int drawNumber;
    private Date drawDate;
    
    public ArchiveDraw(int drawNumber, Date drawDate, Ball firstBall, Ball secondBall, Ball thirdBall, Ball fourthBall, Ball fifthBall, Ball sixthBall) {
        super(firstBall, secondBall, thirdBall, fourthBall, fifthBall, sixthBall);
        this.drawNumber = drawNumber;
        this.drawDate = drawDate;
    }

    public ArchiveDraw(int drawNumber, Date drawDate, Ball firstBall, Ball secondBall, Ball thirdBall, Ball fourthBall, Ball fifthBall, Ball sixthBall, Ball firstSupBall, Ball secondSupBall) {
        super(firstBall, secondBall, thirdBall, fourthBall, fifthBall, sixthBall, firstSupBall, secondSupBall);
        this.drawNumber = drawNumber;
        this.drawDate = drawDate;
    }
}
