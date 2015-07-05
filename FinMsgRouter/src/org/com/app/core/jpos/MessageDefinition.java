package org.com.app.core.jpos;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jpos.iso.packager.GenericPackager;

public class MessageDefinition {
	public static final Logger logger = Logger.getLogger(MessageDefinition.class);
	private static GenericPackager packager;
	public static GenericPackager getGenericPackager(){
		return packager;
	}
	public static String integerFormater(int number,String format){
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(number);
	}
	/**
	 * Static Data Element
	 * Processing code set for Balance Inquiry and Cash Withdrawal
	 */
	public static final String NETMTIREQ = "0800";
	public static final String NETMTIRES = "0810";
	public static final String FINMTIREQ = "0200";
	public static final String FINMTIRES = "0210";
	public static final String REVMTIREQ = "0420";
	public static final String REVMTIRES = "0430";
	/***
	 * 
	 * Balance Inquiry Processing code
	 * 
	 */
	public static final String BINQ00 = "300000";
	public static final String BINQ01 = "301000";
	public static final String BINQ02 = "302000";
	public static final String BINQ03 = "303000";
	
	/*****
	 * 
	 * Cash withdrawal Processing code
	 * 
	 */
	public static final String CWD00 = "010000";
	public static final String CWD01 = "011000";
	public static final String CWD02 = "012000";
	public static final String CWD03 = "013000";
	
	public static final String ZEROAMOUNT = "000000000000";
	public static final String ZEROFEE ="D00000000";
	public static final String CWDFEE = "D00002000";
	
	public static final String NETECHO = "301";
	public static final String NETSIGON = "001";
	public static final String NETSIGOF = "002";
	
	/**
	 *  Dynamic Date Element
	 *  
	 * @param field11
	 * @return retrieval reference number
	 */
	public static String buildField37(String field11) {
		GregorianCalendar gc = new GregorianCalendar();
		int jDate = gc.get(GregorianCalendar.DAY_OF_YEAR);
		DecimalFormat numFormatter = new DecimalFormat("000");
		String jDateFormat = numFormatter.format(jDate);
		SimpleDateFormat simpleDateFormatY = new SimpleDateFormat("yyyy");
		String y = simpleDateFormatY.format(new java.util.Date()).substring(3);
		SimpleDateFormat simpleDateFormatH = new SimpleDateFormat("HH");
		String hh = simpleDateFormatH.format(new java.util.Date());
		String field37 = y + jDateFormat + hh + field11;
		return field37;
	}
	/** 
	 * Dynamic Data Element
	 * @return transaction date time
	 */
	public static final String buildField07() {
		java.util.Date uDate = new java.util.Date();
		SimpleDateFormat sDateFormat = new SimpleDateFormat("MMddHHmmss");
		return sDateFormat.format(uDate);
	}
	
	public static String buildField11(){
		Random random = new Random();
		int randomNumber = Math.abs(random.nextInt()%10000);
		return integerFormater(randomNumber,"000000");
	}
	
	/**
	 *  build Data Element 70 network message code
	 */
	
	public static final String SIGNON 	= "001";
	public static final String SIGNOFF 	= "002";
	public static final String KEYEXC 	= "161";
	public static final String STARTADV = "010";
	public static final String STOPADV 	= "020";
	public static final String ECHO 	= "301";
	
	/**
	 * 
	 * @return Local Time
	 */
	public static String buildField12(){
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("HHmmss");
		return format.format(date);
	}
	/**
	 * Dynamic Data Element
	 * @return Local Date
	 */
	public static String buildField13() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		return format.format(date);
	}
	/**
	 * Dynamic Data Element
	 * @return Settlement Date
	 */
	public static String buildField15(){
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("MMdd");
		return format.format(date);
	}
	
	/**
	 * Dynamic Data Element
	 * @return PIN Block
	 */
	public static String buildField52(){
		return "A1BCEF123456123A";
	}
	
	public static String buildField90(String mti,String de11,String de7,String de32){
		String de33 ="00000000000";
		de32 = integerFormater(Integer.parseInt(de32),"00000000000");
		return mti+de11+de7+de32+de33;
	}
	
	/**
	 * 
	 * Message Response Checker
	 * 
	 */
	private static final String PROCCODE = "[0-9]{6}";
	public static boolean checkField03(String field03){
		boolean check = true;
		Pattern pattern = Pattern.compile(PROCCODE);
		Matcher matcher = pattern.matcher(field03);
		check = check && matcher.find();
		return check;
	}
	private static final String AMT = "[0-9]{12}";
	public static boolean checkField04(String field04){
		boolean check = true;
		Pattern pattern = Pattern.compile(AMT);
		Matcher matcher = pattern.matcher(field04);
		check = check && matcher.find();
		return check;
	}
	private static final String TRACE = "[0-9]{6}";
	public static boolean checkField11(String field11){
		boolean check = true;
		Pattern pattern = Pattern.compile(TRACE);
		Matcher matcher = pattern.matcher(field11);
		check = check && matcher.find();
		return check;
	}
	public static boolean checkField18(String field18){
		return field18.equals("6011");
	}
	public static boolean checkField19(String field19){
		return field19.equals("418");
	}
	public static boolean checkField22(String field22){
		return field22.equals("021");
	}
	public static boolean checkField25(String field25){
		return field25.equals("01");
	}
	private static final String FEE = "[D|C0-9]{9}";
	public static boolean checkField28(String field28){
		boolean check = true;
		Pattern pattern = Pattern.compile(FEE);
		Matcher matcher = pattern.matcher(field28);
		check = check && matcher.find();
		return check;
	}
	// 49 52 54 90
	private static String AUTH = "[0-9A-Z]{6}";
	public static boolean checkField38(String field38){
		boolean check = true;
		Pattern pattern = Pattern.compile(AUTH);
		Matcher matcher = pattern.matcher(field38);
		check = check && matcher.find();
		return check;
	}
	private static String RES = "[0-9]{2}";
	public static boolean checkField39(String field39){
		boolean check = true;
		Pattern pattern = Pattern.compile(RES);
		Matcher matcher = pattern.matcher(field39);
		check = check && matcher.find();
		return check;
	}
	private static String ATMID = "[0-9A-Za-z]{6,8}";
	public static boolean checkField41(String field41){
		boolean check = true;
		Pattern pattern = Pattern.compile(ATMID);
		Matcher matcher = pattern.matcher(field41);
		check = check && matcher.find();
		return check;
	}
	private static String ACQID = "[0-9A-Za-z\\s]{15}";
	public static boolean checkField42(String field42){
		boolean check = true;
		Pattern pattern = Pattern.compile(ACQID);
		Matcher matcher = pattern.matcher(field42);
		check = check && matcher.find();
		return check;
	}
	private static String ATMLOCATION = "[0-9A-Za-z\\s]{40}";
	public static boolean checkField43(String field43){
		boolean check = true;
		Pattern pattern = Pattern.compile(ATMLOCATION);
		Matcher matcher = pattern.matcher(field43);
		check = check && matcher.find();
		return check;
	}
	public static boolean checkField49(String field49){
		return field49.equals("418");
	}
	private static String PINBOCK = "[0-9A-F]{16}";
	public static boolean checkField52(String field52){
		boolean check = true;
		Pattern pattern = Pattern.compile(PINBOCK);
		Matcher matcher = pattern.matcher(field52);
		check = check && matcher.find();
		return check;
	}
	private static String ADDAMT = "[0-9C-D]{20,40}";
	public static boolean checkField54(String field54){
		boolean check = true;
		Pattern pattern = Pattern.compile(ADDAMT);
		Matcher matcher = pattern.matcher(field54);
		check = check && matcher.find();
		return check;
	}
	public static boolean checkField70(String field70){
		boolean check = field70.equals(NETECHO) || field70.equals(NETSIGON) || field70.equals(NETSIGOF);
		return check;
	}
	private static String ORGDE = "[0-9]{42}";
	public static boolean checkField90(String field90){
		boolean check = true;
		Pattern pattern = Pattern.compile(ORGDE);
		Matcher matcher = pattern.matcher(field90);
		check = check && matcher.find();
		return check;
	}
	
}
