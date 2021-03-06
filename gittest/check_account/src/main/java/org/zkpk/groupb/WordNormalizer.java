package org.zkpk.groupb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class WordNormalizer extends BaseBasicBolt {

	private Connection connection;
	private String url = "jdbc:mysql://192.168.29.32:3306/check_account";
	private String password = "123456";
	private String driver = "com.mysql.jdbc.Driver";
	private String username = "zkpk";
	private PreparedStatement st = null;
	private Map<String, Set<String>> map = new HashMap<String, Set<String>>();
	private Map<String, Set<String>> map1 = new HashMap<String, Set<String>>();
	private Map<String, Set<String>> map2 = new HashMap<String, Set<String>>();

	private boolean flag=true;
	private boolean flag1=true;
	private boolean flag2=true;
	private BasicOutputCollector collector;
	public void cleanup() {
	}

	public void execute(Tuple input, BasicOutputCollector collector) {
		this.collector = collector;
		String sentence = input.getString(0);
		if(sentence.equals("over")){
			map.clear();
			map1.clear();
			map2.clear();
		}
		String[] words = sentence.split("\t");
		if (words.length == 11) {
			String userId = words[1] + "\t" + words[8] + "\t" + words[9]+"\t"+words[10];
			String natIp = words[4];
			String qqId = words[3];
			String large = words[5] + words[6] + words[7];
			Set<String> s = null;
			Set<String> s1=null;
			Set<String> s2=null;
			if ((s = map.get(userId)) != null) {
				if(s.size()==3){
					flag=false;
				}else{
					flag=true;
				}
				s.add(natIp);
				map.put(userId, s);
				if (s.size() == 3&&flag) {
					flag=false;
					collector.emit(new Values(userId+"\t"+"natIp"));
				} 

			} else {
				s = new HashSet<String>();
				s.add(natIp);
				map.put(userId, s);
			}

			if ((s1 = map1.get(userId)) != null) {

				if(s.size()==2){
					flag1=false;
				}else{
					flag1=true;
				}
				s1.add(qqId);
				map1.put(userId, s1);
				if(s1.size()==2&&flag1){
					flag1=false;
					collector.emit(new Values(userId+"\t"+"qqId"));
				
				}

			} else {
				s1 = new HashSet<String>();
				s1.add(qqId);
				map1.put(userId, s1);
			}

			if ((s2 = map2.get(userId)) != null) {

				if(s2.size()==3){
					flag2=false;
				}else{
					flag2=true;
				}
				s2.add(large);
				map2.put(userId, s2);
				if(s2.size()==3&&flag2){
					flag2=false;
					collector.emit(new Values(userId+"\t"+"three"));
				}

			} else {
				s2 = new HashSet<String>();
				s2.add(large);
				map2.put(userId, s2);
			}

		}

	}


	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}