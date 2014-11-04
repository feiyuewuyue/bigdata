package org.zkpk.groupb.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.IntervalXYDataset;

public class BarChart {
	private static IntervalXYDataset createDataset(){
		TimeSeriesCollection datase=new TimeSeriesCollection();
		TimeSeries series=new TimeSeries("融资量",Year.class);
		series.add(new Year(2000),5000);
		series.add(new Year(2001),5500);
		series.add(new Year(2002),6000);
		series.add(new Year(2003),8000);
		series.add(new Year(2004),7000);
		series.add(new Year(2005),7500);
		series.add(new Year(2006),6000);
		series.add(new Year(2007),12000);
		series.add(new Year(2008),8000);
		datase.addSeries(series);
		return datase;
	}
	
	private static JFreeChart createChart(IntervalXYDataset xyDataSet){
		JFreeChart jFreeChart=ChartFactory.createXYBarChart(
				"历年经融债的发债融资量",				//标题
				"年", 							//x轴标签
				true,							//是否显示日期
				"数额/亿元",						//y轴标签
				xyDataSet,						//数据集
				PlotOrientation.VERTICAL,		//显示方向
				true,							//是否显示图例
				false,							//是否有工具条
				false);							//是否有链接
		jFreeChart.setBackgroundPaint(Color.white);
		jFreeChart.addSubtitle(new TextTitle("金融行业报告",new Font("Dialog",Font.ITALIC,10)));
		XYPlot plot=(XYPlot) jFreeChart.getPlot();
		XYBarRenderer renderer=(XYBarRenderer) plot.getRenderer();
		renderer.setMargin(0.50);
		DateAxis axis=(DateAxis) plot.getDomainAxis();
		axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		axis.setLowerMargin(0.01);
		axis.setUpperMargin(0.01);
		return jFreeChart;
		
		
	}
	
	public static String generateBarChart(HttpSession session,PrintWriter pw){
		String filename=null;
		IntervalXYDataset xyDataset=createDataset();
		JFreeChart chart=createChart(xyDataset);
		ChartRenderingInfo info=new ChartRenderingInfo();
		try {
			filename=ServletUtilities.saveChartAsPNG(chart, 500,300, info, session);
			ChartUtilities.writeImageMap(pw, filename, info, false);
			pw.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filename;
	}
	
	
	
}
