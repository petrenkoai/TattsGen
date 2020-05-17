
package tattsgen;

import com.opencsv.CSVReader;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TattsArchive {
    
    private final String tattsArchiveFile;
    
    public TattsArchive(String tattsArchiveFile){
        this.tattsArchiveFile = tattsArchiveFile;
    }
    
    public int getNumberOfDraws()throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(this.tattsArchiveFile));
		try {
			byte[] c = new byte[1024];
			int count = -1;
			int readChars = 0;
			boolean empty = true;
			while ((readChars = is.read(c)) != -1) {
				empty = false;
				for (int i = 0; i < readChars; ++i) {
					if (c[i] == '\n') {
						++count;
					}
				}
			}
//значение return будет зависеть от выражения (count == -1 && !empty), которое стоит до вопросительного знака. Далее следует значение новой переменой в случае истинности, двоеточие и значение в противном случае.                        
			return (count == -1 && !empty) ? 1 : count;
                        
		} finally {
			is.close();
		}
    }
    
    
    public int[] getColumnDraw(int columnNumber, int numOfDraws) throws IOException {
        int[] nums = new int[numOfDraws];
        
        for (int i = 0; i < numOfDraws; i++) {
            nums[i] = this.getArchiveDrawNonSort(this.getNumberOfDraws() - i)[columnNumber];
        }
        
        return nums;
    }
    
    public int[] getFullArchiveDraw(int drowNumber){
        Reader reader = null;
        String tempSup;
        try {
            reader = new FileReader(this.tattsArchiveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        CSVReader TattsArchive = new CSVReader(reader, ',', ' ', drowNumber);
        String[] Drawline = null;
        try {
            Drawline = TattsArchive.readNext();
        } catch (IOException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int[] draw = new int[8];
        draw[0] = Integer.parseInt((Drawline[2].replace("\"", "")));
        draw[1] = Integer.parseInt((Drawline[3].replace("\"", "")));
        draw[2] = Integer.parseInt((Drawline[4].replace("\"", "")));
        draw[3] = Integer.parseInt((Drawline[5].replace("\"", "")));
        draw[4] = Integer.parseInt((Drawline[6].replace("\"", "")));
        draw[5] = Integer.parseInt((Drawline[7].replace("\"", "")));
        draw[6] = Integer.parseInt((Drawline[8].replace("\"", "")));
        tempSup = Drawline[9];
        if ("-".equals(tempSup)){
            draw[7] = 0;
        } else {
            draw[7] = Integer.parseInt((Drawline[9].replace("\"", "")));
        }
        return draw;
    }
    
    public int[] getArchiveDraw(int drowNumber) {
        Reader reader = null;
        String tempSup;
        try {
            reader = new FileReader(this.tattsArchiveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        CSVReader TattsArchive = new CSVReader(reader, ',', ' ', drowNumber);
        String[] Drawline = null;
        try {
            Drawline = TattsArchive.readNext();
        } catch (IOException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }

        int[] draw = new int[6];
        draw[0] = Integer.parseInt((Drawline[2].replace("\"", "")));
        draw[1] = Integer.parseInt((Drawline[3].replace("\"", "")));
        draw[2] = Integer.parseInt((Drawline[4].replace("\"", "")));
        draw[3] = Integer.parseInt((Drawline[5].replace("\"", "")));
        draw[4] = Integer.parseInt((Drawline[6].replace("\"", "")));
        draw[5] = Integer.parseInt((Drawline[7].replace("\"", "")));

        Arrays.sort(draw);
        return draw;
    }

   public int[] getArchiveDrawNonSort(int drowNumber) {
        Reader reader = null;
        String tempSup;
        try {
            reader = new FileReader(this.tattsArchiveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        CSVReader TattsArchive = new CSVReader(reader, ',', ' ', drowNumber);
        String[] Drawline = null;
        try {
            Drawline = TattsArchive.readNext();
        } catch (IOException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }

        int[] draw = new int[6];
        draw[0] = Integer.parseInt((Drawline[2].replace("\"", "")));
        draw[1] = Integer.parseInt((Drawline[3].replace("\"", "")));
        draw[2] = Integer.parseInt((Drawline[4].replace("\"", "")));
        draw[3] = Integer.parseInt((Drawline[5].replace("\"", "")));
        draw[4] = Integer.parseInt((Drawline[6].replace("\"", "")));
        draw[5] = Integer.parseInt((Drawline[7].replace("\"", "")));

        //Draw ddraw;
        //ddraw = new Draw(new Ball (draw[0]), new Ball (draw[1]), new Ball (draw[2]), new Ball (draw[3]), new Ball (draw[4]), new Ball (draw[5]));
        
        return draw;
    }
    
    public Date getDateDraw(int drowNumber) {
        Reader reader = null;
        String tempSup;
        try {
            reader = new FileReader(this.tattsArchiveFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        CSVReader TattsArchive = new CSVReader(reader, ',', ' ', drowNumber);
        String[] Drawline = null;
        try {
            Drawline = TattsArchive.readNext();
        } catch (IOException ex) {
            Logger.getLogger(TattsArchive.class.getName()).log(Level.SEVERE, null, ex);
        }
        tempSup = Drawline[1].replace("\"", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = null;
        try {
            date = sdf.parse(tempSup);
        } catch (ParseException ex) {
            Logger.getLogger(Program.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public int[][] getFullArchiveDraws() throws IOException {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        int TattsArchiveRowNumber = 0;
        int TattsArchiveRows = this.getNumberOfDraws(); //Кол-во строк в архиве
        int[][] TattsArchiveArray = new int[TattsArchiveRows + 1][6];
        try {

            br = new BufferedReader(new FileReader(this.tattsArchiveFile));
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
                String[] TattsArchiveNumLine = line.split(cvsSplitBy);
                String TattsArchiveFirstNum = TattsArchiveNumLine[2].replace("\"", "");
                String TattsArchiveSecondNum = TattsArchiveNumLine[3].replace("\"", "");
                String TattsArchiveThirdNum = TattsArchiveNumLine[4].replace("\"", "");
                String TattsArchiveFourthNum = TattsArchiveNumLine[5].replace("\"", "");
                String TattsArchiveFifthNum = TattsArchiveNumLine[6].replace("\"", "");
                String TattsArchiveSixthNum = TattsArchiveNumLine[7].replace("\"", "");
                        
                int[] TempRowArray = new int[6];
                TempRowArray[0] = Integer.parseInt(TattsArchiveFirstNum);
                TempRowArray[1] = Integer.parseInt(TattsArchiveSecondNum);
                TempRowArray[2] = Integer.parseInt(TattsArchiveThirdNum);
                TempRowArray[3] = Integer.parseInt(TattsArchiveFourthNum);
                TempRowArray[4] = Integer.parseInt(TattsArchiveFifthNum);
                TempRowArray[5] = Integer.parseInt(TattsArchiveSixthNum);
                Arrays.sort(TempRowArray);
                TattsArchiveArray[TattsArchiveRowNumber][0] = TempRowArray[0];
                TattsArchiveArray[TattsArchiveRowNumber][1] = TempRowArray[1];
                TattsArchiveArray[TattsArchiveRowNumber][2] = TempRowArray[2];
                TattsArchiveArray[TattsArchiveRowNumber][3] = TempRowArray[3];
                TattsArchiveArray[TattsArchiveRowNumber][4] = TempRowArray[4];
                TattsArchiveArray[TattsArchiveRowNumber][5] = TempRowArray[5];

                TattsArchiveRowNumber++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return TattsArchiveArray;
    }
    
      public boolean checkWithArchiveDraw(int drawNumber ,int[] generatedDraw){
        int[] archiveDraw = this.getArchiveDraw(drawNumber);
        return Arrays.equals(archiveDraw, generatedDraw);
    }
      
    public boolean checkWithArchiveDraws(int[] generatedDraw) throws IOException {
        boolean t = true;
        int[][] fullArchive = this.getFullArchiveDraws();
        int[] aDraw = new int[6];
        for (int i = 0; i < this.getNumberOfDraws(); i++) {
            aDraw[0] = fullArchive[i][0];
            aDraw[1] = fullArchive[i][1];
            aDraw[2] = fullArchive[i][2];
            aDraw[3] = fullArchive[i][3];
            aDraw[4] = fullArchive[i][4];
            aDraw[5] = fullArchive[i][5];
            if (Arrays.equals(aDraw, generatedDraw)) {
                t = false;
            }
        }
        return t;
    }
    /**
    *@param d тираж
    *@param s количество сумм последних тиражей 
    *@return true если сумма не совпала. Иначе false 
    *@throws java.io.IOException 
    */       
    public boolean checkWithSum(Draw d, int s) throws IOException {
        boolean b = true;
        for (int i = 0; i < s; i++) {
            if (d.getSum() == Arrays.stream(this.getArchiveDraw(this.getNumberOfDraws() - i)).sum()) {
                b = false;
                break;
            }
        }
        return b;
    }
    
  public boolean twoNum(int draws, int[] generatedDraw) throws IOException {
        int a = 0;
        e:
        for (int k = 0; k<=draws; k++){
        int[] aDraw = this.getFullArchiveDraw(this.getNumberOfDraws() - k);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (generatedDraw[i] == aDraw[j]) {
                    a++;
                }
                
            }
        }
        if (a == 2)
             break e;
        else
            a = 0;
        }
        return (a == 2);
    }
  
  public boolean checkWithLastDraw(int[] generatedDraw) throws IOException {
        int a = 0;
        int[] lastDraw = this.getArchiveDraw(this.getNumberOfDraws());
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (generatedDraw[i] == lastDraw[j]) {
                    a++;
                }
            }
        }
        return (a == 1);
    }
  
  public boolean threeNum(int draws, int[] generatedDraw) throws IOException {
        int a = 0;
        e:
        for (int k = 1; k<=draws; k++){
        int[] aDraw = this.getArchiveDraw(this.getNumberOfDraws() - k);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (generatedDraw[i] == aDraw[j]) {
                    a++;
                }
                
            }
        }
        if (a == 3)
             break e;
        else
            a = 0;
        }
        return (a == 3);
    }
  
}



/*    
    public boolean checkWithPenultimateDraw(int[] generatedDraw) throws IOException {
        int a = 0;
        int[] penultimateDraw = this.getFullArchiveDraw(this.getNumberOfDraws() - 1);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (generatedDraw[i] == penultimateDraw[j]) {
                    a++;
                }
            }
        }
        return (a == 1);
    }
    
    public boolean checkWithPenultimateNineDraw(int[] generatedDraw) throws IOException {
        int a = 0;
        int[] penultimateDraw = this.getFullArchiveDraw(this.getNumberOfDraws() - 9);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (generatedDraw[i] == penultimateDraw[j]) {
                    a++;
                }
            }
        }
        return (a == 1);
    }
    
// В разработке
    public boolean checkWithHashSum(Draw d, int s) throws IOException {
        boolean b = true;
        for (int i = 0; i < s; i++) {
            Draw current = this.getArchiveDrawNonSort(this.getNumberOfDraws() - i);
            int a = (d.getSum()-d.getSixthBall()) + (d.getSum()/6);
            int c = (current.getSum()-current.getSixthBall()) + (current.getSum()/6);
            if (a == c) {
                b = false;
                break;
            }
        }
        return b;
    }
    

    public boolean checkWithPenultimateThreeDraw(int[] generatedDraw) throws IOException {
        int a = 0;
        int[] penultimateDraw = this.getFullArchiveDraw(this.getNumberOfDraws() - 2);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                if (generatedDraw[i] == penultimateDraw[j]) {
                    a++;
                }
            }
        }
        return (a == 1);
    }
  */ 