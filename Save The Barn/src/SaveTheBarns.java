/*
 * This Program will bring take data from a .dat file, and calculate the total records
 * for each group of people the amount, and average of donations they made.
 * Nico Busatto 17/01/2019
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.*;
import java.util.*;

public class SaveTheBarns {
	
	static String iContributorName;  //Hold contributor name
	static String iAddress;          //Hold contributor address
	static String iCity;             //Hold contributor city
	static String iState;            //Hold contributor state
	static String iZip;              //Hold contributor Zip code
	static char iParty;              //Hold contributore Party
	static char iGender;             //Hold contributor Gender
	static String iContribution;     
	static double cContribution;     //Hold contributor contribution amount
	static boolean eof = false;       //Used to control user loop
	static boolean error = false;     //Used to control validation error
	static Scanner contributorScanner;	//input device to read from .dat file
	static PrintWriter pw;		       //used to write data to the .prt file
	static String iRecord;             //Used to hold the whole record coming in
	static String cErrorCode;          //Hold error message
	static String iString;             //Hold generic string
	static String iUserPrompt;
	static NumberFormat nf;            //Used to format currency
    static Date iDate = new Date();    //Today's date
    static String FormattedDate = new SimpleDateFormat("MM-dd-yyyy").format(iDate);   //Date format
	static String colHdgFormat = "%-12s%5s%11s%5s%10s%n";                //Heading formatter
	static String colDetailFormat = "%-23s%5d%3s%7s%3s%7s%n";            //Detail line formatter
	static String colDetailFormat2 = "%-23s%5d%3s%7s%4s%7s%n";           //Detail line 2 formatter
	//Records counters 
	static int cMen = 0, cWomen = 0, cDems = 0, cReps = 0, cInd = 0, cDemsMen = 0, cDemsWomen = 0, cRepsMen = 0,
	           cRepsWomen = 0, cIndMen = 0,	cIndWomen = 0, cOverall = 0;
	//Total amount accumulator
	static double cGtContributionMen = 0, cGtContributionWomen = 0, cGtContributionDems = 0, cGtContributionReps = 0,
			      cGtContributionInd = 0, cGtContributionDemsMen = 0, cGtContributionDemsWomen = 0, cGtContributionRepsMen = 0,
			      cGtContributionRepsWomen = 0, cGtContributionIndMen = 0, cGtContributionIndWomen = 0, cGtContributionOverall = 0;
    //Amount averages
	static double cGtContributionMenAvg, cGtContributionWomenAvg, cGtContributionDemsAvg, cGtContributionRepsAvg,
	              cGtContributionIndAvg, cGtContributionDemsMenAvg, cGtContributionDemsWomenAvg, cGtContributionRepsMenAvg,
				  cGtContributionRepsWomenAvg, cGtContributionIndMenAvg, cGtContributionIndWomenAvg, cGtContributionOverallAvg;
	//Output formatters
    static String oGtContributionMen ,oGtContributionWomen, oGtContributionDems, oGtContributionReps, oGtContributionInd,
				  oGtContributionDemsMen, oGtContributionDemsWomen, oGtContributionRepsMen, oGtContributionRepsWomen, 
				  oGtContributionIndMen, oGtContributionIndWomen, oGtContributionOverall, oGtContributionMenAvg, 
				  oGtContributionWomenAvg, oGtContributionDemsAvg, oGtContributionRepsAvg, oGtContributionIndAvg, 
				  oGtContributionDemsMenAvg, oGtContributionDemsWomenAvg, oGtContributionRepsMenAvg, 
				  oGtContributionRepsWomenAvg, oGtContributionIndMenAvg, oGtContributionIndWomenAvg, oGtContributionOverallAvg;
					
	public static void main(String[] args) {

		//call init()		
		init();
		
		//loop until no more records
		while (eof == false) {
			
			validation();
			
			if (error == false) {
			
				calcs();

			} else {
		    	  
				printerror();  
	  
				}
			
			input();
			
		}
		    			
		output();
			 
		pw.close();
		System.out.println("Program ending, have a good one!");
			
	}
													
	public static void init() {
		
		//set scanner to the input file
		//File payrollFile = new File("payroll.dat");
		try {
			contributorScanner = new Scanner(new File("contributors.dat"));
			contributorScanner.useDelimiter(System.getProperty("line.separator"));
		} catch (FileNotFoundException e1) {
			System.out.println("File error");

		}
		
		//initialize the PrintWriter object
		try {
			pw = new PrintWriter(new File ("error.prt"));
		} catch (FileNotFoundException e) {
			System.out.println("Output file error");
		}
		//
		nf = NumberFormat.getCurrencyInstance(java.util.Locale.US);
			
		//do first read
		input();
			
	}
	
	public static void input() {

		if (contributorScanner.hasNext()) {
			iRecord = contributorScanner.next();
			iContributorName = iRecord.substring(0,15);	//file position 1 - 25
			iAddress = iRecord.substring(15,39);	   //file position 26 - 60
			iCity = iRecord.substring(39,54);		   //file position 61 - 81
			iState = iRecord.substring(54,56);        //file position 82 - 83
			iZip = iRecord.substring(56,61);          //file position 84 - 88
			iString = iRecord.substring(61,62);
			iParty = iString.charAt(0);
			iString = iRecord.substring(62,63);
			iGender = iString.charAt(0);
			iContribution = iRecord.substring(63,68);		
			cContribution = Double.parseDouble(iContribution);
			
			
		} else {
			
			eof = true;
			
		}	
		
	}
		
	public static void validation() {
				
				//Validate name
				if (iContributorName.trim().isEmpty()) {
				
						cErrorCode = "Name is required";
						error = true;
						printerror();
				
				} else {

						error = false;
				
				}
	
			    //Validate address
				if (iAddress.trim().isEmpty()) {
				
					cErrorCode = "Address is required";
				 	error = true;
				 	printerror();
				 	
			    } else {

			    	error = false;

			    }
		
	            //Validate city
				if (iCity.trim().isEmpty()) {
				
					cErrorCode = "City is required";
					error = true;
					
			 	} else {
				
			 		error = false;

			 	}
		        //Validate state
				if (iState.trim().isEmpty()) {
					cErrorCode = "State is required";
				
					error = true;
					printerror();
					
				} else {
				
					error = false;

				}

                //Validate zip code
			try {	
				
				if (iZip.trim().length() < 1 || iZip.trim().length() > 5) {
				
					cErrorCode = "Wrong Zip length";
					error = true;
					printerror();
					}
				
				} catch (Exception e) {
				
					error = true;

				}
	
			   //Validate party
			if (iParty != 'D' && iParty != 'R' && iParty != 'I') {
				
				cErrorCode = "Wrong Party";
				error = true;
				printerror();
			} else {
				
				error = false;
				
			}
		
            //Validate Gender
			if (iGender != 'M' && iGender != 'F') {
				
				cErrorCode = "Wrong Gender";
				error = true;

			} else {
				
				error = false;
				
			}
				
            //Validate contribution
			try {
	
				if (cContribution < 0.01 && cContribution > 9999.99) {
					cErrorCode = "Amount out of range";
					error = true;
				}	
				
			} catch (Exception e) {

				error = true;

			  }
			
	}
	
	public static void printerror() {
		
		pw.format("%-70s%2s%30s%n", iRecord , "  " , cErrorCode);
		//error = false;
	}
	
	public static void calcs() {
	
		if (iParty == 'D') {
		
			cDems++;
			
			if(iGender == 'M') {
				cGtContributionDemsMen += cContribution;
				cDemsMen++;
				cMen++;
			}
			
			if(iGender == 'F') {
				cGtContributionDemsWomen += cContribution;
				cDemsWomen++;
				cWomen++;
			}
		}
		
		if (iParty == 'R') {
			
			cReps++;
			
			if(iGender == 'M') {
				cGtContributionRepsMen += cContribution;
				cRepsMen++;
				cMen++;
			}
			
			if(iGender == 'F') {
				cGtContributionRepsWomen += cContribution;
				cRepsWomen++;
				cWomen++;
			}
		}
		
		if (iParty == 'I') {
			
			cInd++;
					
			if(iGender == 'M') {
				cGtContributionIndMen += cContribution;
				cIndMen++;
				cMen++;
			}
			
			if(iGender == 'F') {
				cGtContributionIndWomen += cContribution;
				cIndWomen++;
				cWomen++;
			}
		}
		
		//Calculate total amounts
		cGtContributionDems = cGtContributionDemsMen + cGtContributionDemsWomen;
		cGtContributionReps = cGtContributionRepsMen + cGtContributionRepsWomen;
		cGtContributionInd = cGtContributionIndMen + cGtContributionIndMen;
		cGtContributionOverall = cGtContributionDems + cGtContributionReps + cGtContributionInd;
		cGtContributionMen = cGtContributionDemsMen + cGtContributionRepsMen + cGtContributionIndMen;
		cGtContributionWomen = cGtContributionDemsWomen + cGtContributionRepsWomen + cGtContributionIndWomen;
		cOverall = cDems + cReps + cInd;
		
		//Calculate average amounts
		cGtContributionMenAvg = cGtContributionMen / cMen;
		cGtContributionWomenAvg = cGtContributionWomen / cWomen;
		cGtContributionDemsAvg = cGtContributionDems / cDems;
		cGtContributionRepsAvg = cGtContributionReps / cReps;
		cGtContributionIndAvg = cGtContributionInd / cInd;
		cGtContributionDemsMenAvg = cGtContributionDemsMen / cDemsMen;
		cGtContributionDemsWomenAvg = cGtContributionDemsWomen / cDemsWomen;
		cGtContributionRepsMenAvg = cGtContributionRepsMen / cRepsMen;
		cGtContributionRepsWomenAvg = cGtContributionRepsWomen / cRepsWomen;
		cGtContributionIndMenAvg = cGtContributionIndMen / cIndMen;
		cGtContributionIndWomenAvg = cGtContributionIndWomen / cIndWomen;
		cGtContributionOverallAvg = cGtContributionOverall / cOverall;
		
	}
	
	public static void output() {
		
		//$ format vars
		oGtContributionMen = nf.format(cGtContributionMen);
		oGtContributionWomen = nf.format(cGtContributionWomen);
		oGtContributionDems = nf.format(cGtContributionDems);
		oGtContributionReps = nf.format(cGtContributionReps);
		oGtContributionInd  = nf.format(cGtContributionInd);
		oGtContributionDemsMen  = nf.format(cGtContributionDemsMen);
		oGtContributionDemsWomen  = nf.format(cGtContributionDemsWomen);
		oGtContributionRepsMen  = nf.format(cGtContributionRepsMen);
		oGtContributionRepsWomen  = nf.format(cGtContributionRepsWomen);
		oGtContributionIndMen  = nf.format(cGtContributionIndMen);
		oGtContributionIndWomen = nf.format(cGtContributionIndWomen);
		oGtContributionOverall = nf.format(cGtContributionOverall);
		oGtContributionMenAvg = nf.format(cGtContributionMenAvg);
		oGtContributionWomenAvg = nf.format(cGtContributionWomenAvg);
		oGtContributionDemsAvg = nf.format(cGtContributionDemsAvg);
		oGtContributionRepsAvg = nf.format(cGtContributionRepsAvg);
		oGtContributionIndAvg = nf.format(cGtContributionIndAvg);
		oGtContributionDemsMenAvg = nf.format(cGtContributionDemsMenAvg);
		oGtContributionDemsWomenAvg = nf.format(cGtContributionDemsWomenAvg);
		oGtContributionRepsMenAvg = nf.format(cGtContributionRepsMenAvg);
		oGtContributionRepsWomenAvg = nf.format(cGtContributionRepsWomenAvg);
		oGtContributionIndMenAvg = nf.format(cGtContributionIndMenAvg);
		oGtContributionIndWomenAvg = nf.format(cGtContributionIndWomenAvg);
		oGtContributionOverallAvg  = nf.format(cGtContributionOverallAvg);
		
		//Print out detail lines
		System.out.format(colHdgFormat, "Save the Barns", " ", "Contribution Report", " ", FormattedDate);
		System.out.println();
		System.out.format(colDetailFormat, "Democratics: " , cDems , "   " , oGtContributionDems , "   " , oGtContributionDemsAvg);
		System.out.format(colDetailFormat2, "Republicans: " , cReps , "   " , oGtContributionReps , "    " , oGtContributionRepsAvg);
		System.out.format(colDetailFormat2, "Indipendents: " , cInd , "   " , oGtContributionInd , "    " , oGtContributionIndAvg);
		System.out.format(colDetailFormat, "Men: " , cMen , "   " , oGtContributionMen , "   " , oGtContributionMenAvg);
		System.out.format(colDetailFormat, "Women " , cWomen , "   " , oGtContributionWomen , "   " , oGtContributionWomenAvg);
		System.out.format(colDetailFormat2, "Democratic Men: " , cDemsMen , "   " , oGtContributionDemsMen , "    " , oGtContributionDemsMenAvg);
		System.out.format(colDetailFormat2, "Democratic Women: " , cDemsWomen , "   " , oGtContributionDemsWomen , "    " , oGtContributionDemsWomenAvg);
		System.out.format(colDetailFormat2, "Republican Men: " , cRepsMen , "   " , oGtContributionRepsMen , "    " , oGtContributionRepsMenAvg);
		System.out.format(colDetailFormat2, "Republican Women: " , cRepsWomen , "   " , oGtContributionRepsWomen , "    " , oGtContributionRepsWomenAvg);
		System.out.format(colDetailFormat2, "Indipendent Men: " , cIndMen , "   " , oGtContributionIndMen , "    " , oGtContributionIndMenAvg);
		System.out.format(colDetailFormat2, "Indipendent Women: " , cIndWomen , "   " , oGtContributionIndWomen , "    " , oGtContributionIndWomenAvg);
		System.out.format(colDetailFormat, "Overall: " , cOverall , "   " , oGtContributionOverall , "   " , oGtContributionOverallAvg);
		//System.out.format(colDetFormat, iContributorName , iAddress , iCity , iState , iZip , iParty , iGender , cContribution);
		System.out.println();
		
		
		
	}
		
}
