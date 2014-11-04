package com.zmd.data;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProductData {

	/**
	 * 			----���漰����ȫ�ֱ���ģ��----
	 */
	public static long userTime;
	public static	String userAccount;
	public static	String[] userIP1={"223","175","202","110","221","103","118","125"};
	public static  String[] userIP2={"220","184","100","166","207","22","213","72"};
	public static  String[] userIP3={"0","128","255","191","135","100","63","103","144","159","136","143"};
	public static  String[] userIP4={"0","255"};
	public static	int qqid;
	public static	String natIP;
	public static	String cookieValue;
	public static	String[] devName1={"MacBook Pro mac","iphone 5s","Thinkpad"};
	public static	String[] osName1={"os x 10.9","ios","win 8"};
	public static  String str;
	public static  String data;
	public static  int qqid1;
	public static  String natIPTest;
/**
 * 		MD5��ȡ��ϣֵʵ�ֲ���
 */
	public String md5s(String plainText) {
		  try {
			  MessageDigest md = MessageDigest.getInstance("MD5");
			  md.update(plainText.getBytes());
			  byte b[] = md.digest();
			  int i;
			  StringBuffer buf = new StringBuffer("");
			  for (int offset = 0; offset < b.length; offset++) {
				  i = b[offset];
				  if (i < 0)
					  i += 256;
				  if (i < 16)
					  buf.append("0");
				  buf.append(Integer.toHexString(i));
			  }
			  str = buf.toString();
//		   System.out.println(buf.toString());// 32λ�ļ���
//		   System.out.println("result: " + buf.toString().substring(8, 24));// 16λ�ļ���
		  }catch (NoSuchAlgorithmException e) {
		   // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		  	return str;
		}
/**
 * 		----�������������ģ��----
 */
	public String getDev(){
			String random="";
			int index=(int)(Math.random()*devName1.length);
			random=devName1[index];
			return random;
		} 
	public String getOs(){
			String random="";
			int index=(int)(Math.random()*osName1.length);
			random=osName1[index];
			return random;
		}
	public  String getIP1(){
			String random="";
			int index=(int)(Math.random()*userIP1.length);
			random=userIP1[index];
			return random;
	}
	public  String getIP2(){
			String random="";
			int index=(int)(Math.random()*userIP2.length);
			random=userIP2[index];
			return random;
	}
	public  String getIP3(){
			String random="";
			int index=(int)(Math.random()*userIP3.length);
			random=userIP3[index];
			return random;
	}	
	public  String getIP4(){
			String random="";
			int index=(int)(Math.random()*userIP4.length);
			random=userIP4[index];
			return random;
	}
	public int getqqID(){
			Random rd=new Random();
			qqid1=rd.nextInt(999999999);
			return qqid1;
	}
	public String getNatIP(){
			java.util.Random rd=new java.util.Random();
			String natIPTest=rd.nextInt(255)+"."+rd.nextInt(255);
			return natIPTest;
	}
/**
 * 		----�����ݵĲ���----
 */
	public static List<String>  creatData(){
		ProductData pd=new ProductData();
		Random rd=new Random((long) (Math.random()*75));
		List<String> dd=new ArrayList<String>();
			userTime=System.currentTimeMillis();
			natIP="192"+"."+"168"+"."+pd.getNatIP();
			String userIP=pd.getIP1()+"."+pd.getIP2()+"."+pd.getIP3()+"."+pd.getIP4();
			String devName=pd.getDev();
			String osName=pd.getOs();
			userAccount="0229"+rd.nextInt(9999999);
			if(userAccount.length()<12){
				String userModel="111111";
				int a=11-userAccount.length();
				String userResult=userModel.substring((int)Math.random()*10,a);
			    userAccount=userAccount+userResult;
			}
			int times=rd.nextInt(25)+1;
			qqid=pd.getqqID();
			for(int i=0;i<times;i++){
				int s=rd.nextInt(3);
				if(s==2){
					 qqid=pd.getqqID();
					 natIP="192"+"."+"168"+"."+pd.getNatIP();
					 cookieValue=pd.md5s(userAccount);	 
				}
				String data=userTime+"\t"+userAccount+"\t"+userIP+"\t"+qqid+"\t"+natIP+"\t"+cookieValue+"\t"+devName+"\t"+osName+"\n";
				dd.add(data);
			}
			return dd;
		}
/**
* 		 ------socket(UDPЭ��)����ʵ�ֲ���-------		
*/
	
	public  void udpSendSocket(){
		DatagramSocket ds;
		try {
			ds = new DatagramSocket();
			ProductData pd=new ProductData();
			List<String> dataflumn=pd.creatData();
	    	System.out.println(dataflumn);
	    	for(String list:dataflumn){;
	    		byte[] by=list.getBytes();
	    		DatagramPacket dp=new DatagramPacket(by,by.length,InetAddress.getByName("192.168.2.53"),10006);
				ds.send(dp);
	    	}
		}catch(Exception e){
			e.printStackTrace();
	    	}
		}
/**
 * 		----socket(TCPЭ��)����ʵ�ֲ���----
 */
	public  void tcpSendSocket(){
		Socket s1=null;
		try {
			ProductData pd=new ProductData();
			List<String> dataflumn=pd.creatData();
	    	System.out.println(dataflumn);
	    	for(String list:dataflumn){
	    		s1=new Socket("192.168.2.101",10034);
	    		BufferedOutputStream bosout=new BufferedOutputStream(s1.getOutputStream());
	    		bosout.write(list.getBytes());
	    		bosout.flush();
	    	}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null!=s1){
				try{
					s1.close();
				}catch(IOException e){
					e.printStackTrace();
					}
				}	
			}
		}
/**
 * 		----�������������д������----
 */
	
	public static void  sendLocal(String result){
		FileOutputStream out=null;
		System.out.println();
			try{
				out=new FileOutputStream("D:\\IODataTest1.txt",true);
				out.write(result.getBytes());
				out.flush();
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}catch(IOException e){
				e.printStackTrace();
			}finally{
			    if(out!=null){
				   try {
					 out.close();
					}catch (IOException e) {
					 // TODO Auto-generated catch block
						e.printStackTrace();
						}
					}
				}				
			}
	public static void main(String[] args) throws UnknownHostException {
		// TODO Auto-generated method stub
		Thread thread=new Thread(new SleepRunner());
		thread.start();

	}
}
/**
 *		----���ô���Ƶ�ʵ�ģ�飨���ý������õģ�----
 */
class SleepRunner implements Runnable{
	
	private ProductData pd=new ProductData();
	@Override
	public void run() {
		while(true){
			try {
				pd.tcpSendSocket();
				Thread.currentThread().sleep((long) (Math.random()*1000));
			} catch (InterruptedException e) {	
				e.printStackTrace();
			}
		}
	}
}

