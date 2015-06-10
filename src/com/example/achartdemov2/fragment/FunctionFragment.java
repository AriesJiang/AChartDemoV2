package com.example.achartdemov2.fragment;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.achartdemov2.R;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class FunctionFragment extends Fragment {
    private static final String TAG = "FunctionFragment";
    
    private RelativeLayout llBarChart;
    private View vChart1;
	private ImageView imge;
	private LinearLayout lin;
	private Context mcontext;
	DisplayMetrics metric;
	
	private String[][] Top10ErrCode={{"ADFU1","5"},{"MBPW2","8"},
			{"ABCDE","12"},{"BLFU1","16"},{"LCVD3","15"},{"ADDK1","11"},
			{"CMFU3","8"},{"LCCR2","7"},{"QBLE1","5"},{"SPNS1","2"}};

    public static FunctionFragment newInstance(String s) {
        FunctionFragment newFragment = new FunctionFragment();
        Log.d(TAG, "FunctionFragment-----" + s);
//        Bundle bundle = new Bundle();
//        bundle.putString("hello", s);
//        newFragment.setArguments(bundle);
        return newFragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "TestFragment-----onCreate");
//        Bundle args = getArguments();
//        hello = args != null ? args.getString("hello") : defaultHello;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d(TAG, "TestFragment-----onCreateView");
        mcontext = FunctionFragment.this.getActivity();
        View view = inflater.inflate(R.layout.fragment_chart, container, false);
        llBarChart = (RelativeLayout) view.findViewById(R.id.layout1);
        
        vChart1 = getLineDashedwChart("PQC Top 10 ErrCode", "ErrCode", "QTY", Top10ErrCode);
		llBarChart.removeAllViews();
		//llBarChart.addView(vChart);
		llBarChart.addView(vChart1, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
        return view;
    }
    
    @SuppressWarnings("deprecation")
	private View getLineDashedwChart(String chartTitle, String XTitle, String YTitle, String[][] xy){
		
		XYSeries Series1 = new XYSeries(YTitle);
		//line2
		XYSeries Series2 = new XYSeries(YTitle);
		
		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();             
		Dataset.addSeries(Series1);
		//line2
		Dataset.addSeries(Series2);
		
		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer(); 
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();        
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();        
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);
		
		Renderer.setFitLegend(true);                  //如果设置图例应大小以适合
		Renderer.setShowAxes(false);                  //如果设置的轴应该是可见的。
		Renderer.setShowCustomTextGrid(false);     //如果设置网格的自定义X或Y的标签应该是可见的。
		Renderer.setShowLegend(false);     //如果设置线的标识是否是可见的。
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(R.color.chartred_shadow));
		
		Renderer.setApplyBackgroundColor(true);			//設定背景顏色
		Renderer.setBackgroundColor(Color.WHITE);			//設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE);				//設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD);		//設定文字style
		
		Renderer.setShowGrid(true);							//設定網格
		Renderer.setGridColor(Color.GRAY);					//設定網格顏色
		
		Renderer.setChartTitle(chartTitle);					//設定標頭文字
		Renderer.setLabelsColor(Color.BLACK);				//設定標頭文字顏色
		Renderer.setChartTitleTextSize(20);					//設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK);					//設定雙軸顏色
		Renderer.setBarSpacing(1);						//設定bar間的距離
		
		Renderer.setXTitle(XTitle);						//設定X軸文字      
		Renderer.setYTitle(YTitle);						//設定Y軸文字 
		Renderer.setXLabelsColor(Color.BLACK);				//設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK);			//設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER);				//設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER);				//設定Y軸文字置中
		Renderer.setXLabelsAngle(0); 						//設定X軸文字傾斜度
		
		Renderer.setXLabels(0); 							//設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0);							//設定Y軸文最小值
		Renderer.setYAxisMax(30);							//設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动. 
		
		yRenderer1.setColor(getResources().getColor(R.color.chartred));              		//設定Series顏色
		yRenderer1.setStroke(BasicStroke.DASHED);//设置描边风格
		yRenderer2.setColor(getResources().getColor(R.color.chartred));              		//設定Series顏色
		//yRenderer.setDisplayChartValues(true);			//展現Series數值
		
//		Series.add(0, 0); 
//			Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		for(int r=0; r<xy.length; r++) {
			//Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
//			yRenderer1.setPointStyle(PointStyle.CIRCLE);//点的类型是圆形
//			Renderer.setPointSize(4);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
//			yRenderer1.setFillPoints(true);//设置点是否实心  
//			yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
//			Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
//			yRenderer.setFillPoints(true);//设置点是否实心  
			Renderer.addXTextLabel(r+1, xy[r][0]);
			Series1.add(r+1, Integer.parseInt(xy[r][1]));     
		}
		for(int r=2; r<xy.length-2; r++) {
			//Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			yRenderer2.setPointStyle(PointStyle.CIRCLE);//点的类型是圆形
			Renderer.setPointSize(4);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			yRenderer2.setFillPoints(true);//设置点是否实心  
//			yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
//			Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
//			yRenderer.setFillPoints(true);//设置点是否实心  
			Renderer.addXTextLabel(r+1, xy[r][0]);
			Series2.add(r+1, Integer.parseInt(xy[r][1]));     
		}
//		Series.add(11, 0); 
//			Renderer.addXTextLabel(xy.length+1, "");
		
//		View view = ChartFactory.getBarChartView(getBaseContext(), Dataset, Renderer, Type.DEFAULT);  
//		View view = ChartFactory.getLineChartView(getBaseContext(), Dataset, Renderer);
		//光滑的线段
		View view = ChartFactory.getCubeLineChartView(mcontext, Dataset, Renderer, 0.2f);
		return view;
	}
    

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "TestFragment-----onDestroy");
    }

}
