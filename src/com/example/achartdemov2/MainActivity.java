package com.example.achartdemov2;

import java.text.NumberFormat;
import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.BasicStroke;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.example.achartdemov2.fragment.FunctionFragment;
import com.example.achartdemov2.fragment.MyFragmentPagerAdapter;
import com.example.achartdemov2.fragment.TestFragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener {

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;
	private Fragment mainFragment, functionFragment, recentFragment;

	private RelativeLayout llBarChart, llBarChart2, llBarChart3, llBarChart4,
			llBarChart5;
	private View vChart1, vChart2, vChart3, vChart4, vChart5;
	/**
	 * 上一个被选中的fragment
	 */
	private int previousSelectPosition = 0;
	private LinearLayout llPoints;

	private String[][] Top10ErrCode = { { "ADFU1", "5" }, { "MBPW2", "8" },
			{ "ABCDE", "12" }, { "BLFU1", "16" }, { "LCVD3", "15" },
			{ "ADDK1", "11" }, { "CMFU3", "8" }, { "LCCR2", "7" },
			{ "QBLE1", "5" }, { "SPNS1", "2" }, { "ABCDE", "12" },
			{ "BLFU1", "16" }, { "LCVD3", "15" }, { "ADDK1", "11" },
			{ "CMFU3", "8" }, { "LCCR2", "7" }, { "QBLE1", "5" },
			{ "SPNS1", "2" } };
	int screenWidth;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViewPager();
	}

	private void initViewPager() {
		screenWidth = (getWindowManager().getDefaultDisplay().getWidth() - 50) / 7; // 屏幕宽（像素，如：480px）
		Log.e("123123", "**********" + screenWidth);
		mPager = (ViewPager) findViewById(R.id.vPager);
		fragmentsList = new ArrayList<Fragment>();
		// LayoutInflater mInflater = getLayoutInflater();
		// View activityView = mInflater.inflate(R.layout.lay1, null);

		// mainFragment = TestFragment.newInstance("Hello Activity.");
		mainFragment = TestFragment.newInstance("Hello Main.");
		recentFragment = TestFragment.newInstance("Hello Group.");
		// functionFragment=TestFragment.newInstance("Hello Friends.");
		functionFragment = FunctionFragment.newInstance("Hello Function");
		// setupFragment = TestFragment.newInstance("Hello Chat.");

		fragmentsList.add(mainFragment);
		fragmentsList.add(functionFragment);
		fragmentsList.add(recentFragment);
		// fragmentsList.add(setupFragment);

		mPager.setAdapter(new MyFragmentPagerAdapter(
				getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(this);

		llPoints = (LinearLayout) findViewById(R.id.ll_points);
		prepareData();
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);

		llBarChart = (RelativeLayout) findViewById(R.id.layout);
		llBarChart2 = (RelativeLayout) findViewById(R.id.layout2);
		llBarChart3 = (RelativeLayout) findViewById(R.id.layout3);
		llBarChart4 = (RelativeLayout) findViewById(R.id.layout4);
		llBarChart5 = (RelativeLayout) findViewById(R.id.layout5);

		vChart1 = getBarChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart.addView(vChart1, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));

		vChart2 = getLineChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart2.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart2.addView(vChart2, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart3 = getLineShadowChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		final GraphicalView graphicalView = (GraphicalView) vChart3;
		graphicalView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 这段代码处理点击一个点后,获得所点击的点在哪个序列中以及点的坐标.
				// -->start
				SeriesSelection seriesSelection = graphicalView
						.getCurrentSeriesAndPoint();
				double[] xy = graphicalView.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(MainActivity.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							MainActivity.this,
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked"
									+ " closest point value X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue()
									+ " clicked point value X=" + (float) xy[0]
									+ ", Y=" + (float) xy[1],
							Toast.LENGTH_SHORT).show();
				}
				// -->end

			}
		});
		llBarChart3.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart3.addView(vChart3, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart4 = getLine2ShadowChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart4.removeAllViews();
		// llBarChart.addView(vChart);
		final GraphicalView graphicalView2 = (GraphicalView) vChart4;
		graphicalView2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 这段代码处理点击一个点后,获得所点击的点在哪个序列中以及点的坐标.
				// -->start
				SeriesSelection seriesSelection = graphicalView2
						.getCurrentSeriesAndPoint();
				double[] xy = graphicalView2.toRealPoint(0);
				if (seriesSelection == null) {
					Toast.makeText(MainActivity.this,
							"No chart element was clicked", Toast.LENGTH_SHORT)
							.show();
				} else {
					Toast.makeText(
							MainActivity.this,
							"Chart element in series index "
									+ seriesSelection.getSeriesIndex()
									+ " data point index "
									+ seriesSelection.getPointIndex()
									+ " was clicked"
									+ " closest point value X="
									+ seriesSelection.getXValue() + ", Y="
									+ seriesSelection.getValue()
									+ " clicked point value X=" + (float) xy[0]
									+ ", Y=" + (float) xy[1],
							Toast.LENGTH_SHORT).show();
				}
				// -->end

			}
		});
		llBarChart4.addView(vChart4, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		vChart5 = getLineDashedwChart("PQC Top 10 ErrCode", "ErrCode", "QTY",
				Top10ErrCode);
		llBarChart5.removeAllViews();
		// llBarChart.addView(vChart);
		llBarChart5.addView(vChart5, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	/**
	 * viewpager圆点初始化
	 */
	@SuppressWarnings("deprecation")
	private void prepareData() {

		View view;
		for (int i = 0; i < fragmentsList.size(); i++) {
			// 添加点view对象
			view = new View(getBaseContext());
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.point_background));
			LayoutParams lp = new LayoutParams(5, 5);
			lp.leftMargin = 10;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}

	private View getBarChart(String chartTitle, String XTitle, String YTitle,
			String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setApplyBackgroundColor(true); // 設定背景顏色
		Renderer.setBackgroundColor(Color.TRANSPARENT); // 設定圖內圍背景顏色
		Renderer.setMarginsColor(getResources()
				.getColor(R.color.chartred_white)); // 設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD); // 設定文字style

		Renderer.setShowGrid(true); // 設定網格
		Renderer.setGridColor(Color.GRAY); // 設定網格顏色

		Renderer.setChartTitle(chartTitle); // 設定標頭文字
		Renderer.setLabelsColor(Color.BLACK); // 設定標頭文字顏色
		Renderer.setChartTitleTextSize(20); // 設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK); // 設定雙軸顏色
		Renderer.setBarSpacing(1); // 設定bar間的距離

		Renderer.setXTitle(XTitle); // 設定X軸文字
		Renderer.setYTitle(YTitle); // 設定Y軸文字
		Renderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		Renderer.setXLabelsAngle(0); // 設定X軸文字傾斜度

		Renderer.setXLabels(0); // 設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0); // 設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动.

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		yRenderer.setDisplayChartValues(true); // 展現Series數值

		Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
				Renderer, Type.DEFAULT);
		return view;
	}

	private View getLineChart(String chartTitle, String XTitle, String YTitle,
			String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setFitLegend(true); // 如果设置图例应大小以适合
		Renderer.setShowAxes(true); // 如果设置的轴应该是可见的。

		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setMaximumFractionDigits(2); // 保留到小数点后几位 显示：47.00
		nf.setMinimumFractionDigits(2);
		// setMaximumFractionDigits(int) 设置数值的小数部分允许的最大位数。
		// setMaximumIntegerDigits(int) 设置数值的整数部分允许的最大位数。
		// setMinimumFractionDigits(int) 设置数值的小数部分允许的最小位数。
		// setMinimumIntegerDigits(int) 设置数值的整数部分允许的最小位数.

		// NumberFormat nf = NumberFormat.getPercentInstance();
		// nf.setMinimumFractionDigits(2); // 保留到小数点后几位 显示：47.00%

		// Sets the number format for displaying labels.
		Renderer.setLabelFormat(nf);
		Renderer.setShowCustomTextGrid(false); // 如果设置网格的自定义X或Y的标签应该是可见的。
		Renderer.setShowLegend(false); // 如果设置线的标识是否是可见的。

		Renderer.setApplyBackgroundColor(true); // 設定 背景顏色
		Renderer.setBackgroundColor(Color.WHITE); // 設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE); // 設定圖外圍背景顏色
		// Renderer.setMargins(margins)
		Renderer.setTextTypeface(null, Typeface.BOLD); // 設定文字style

		Renderer.setShowGrid(true); // 設定網格
		Renderer.setGridColor(Color.GRAY); // 設定網格顏色

		Renderer.setChartTitle(chartTitle); // 設定標頭文字
		Renderer.setLabelsColor(Color.BLACK); // 設定標頭文字顏色
		Renderer.setChartTitleTextSize(20); // 設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK); // 設定雙軸顏色
		Renderer.setBarSpacing(1); // 設定bar間的距離

		Renderer.setXTitle(XTitle); // 設定X軸文字
		Renderer.setYTitle(YTitle); // 設定Y軸文字
		Renderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		Renderer.setXLabelsAngle(0); // 設定X軸文字傾斜度

		Renderer.setXLabels(0); // 設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0); // 設定Y軸文最小值
		Renderer.setYAxisMax(30); // 設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动.

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		yRenderer.setDisplayChartValues(true); // 展現Series數值
		
		yRenderer.setChartValuesTextSize(30);
		//设置这个的目的是，使得所有点的值都能显示，默认的距离为100，是个bug
		yRenderer.setDisplayChartValuesDistance(30);//设置两个折点间的距离，使得所有点的值都能显示
		yRenderer.setChartValuesSpacing(8);
				
		Renderer.setLabelsTextSize(20);
		Renderer.setLegendTextSize(0);
		Renderer.setAxisTitleTextSize(20);

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			if (r == 6) {
				yRenderer.setPointStyle(PointStyle.CIRCLE);// 点的类型是圆形
				Renderer.setPointSize(4);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
				yRenderer.setFillPoints(false);// 设置点是否实心
			} else {
				// yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
				// Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
				// yRenderer.setFillPoints(true);//设置点是否实心
			}
			// Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// 光滑的线段
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLineShadowChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer);

		Renderer.setFitLegend(true); // 如果设置图例应大小以适合
		Renderer.setShowAxes(false); // 如果设置的轴应该是可见的。
		Renderer.setShowCustomTextGrid(false); // 如果设置网格的自定义X或Y的标签应该是可见的。
		Renderer.setShowLegend(false); // 如果设置线的标识是否是可见的。
		yRenderer.setFillBelowLine(true);
		yRenderer.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // 設定背景顏色
		Renderer.setBackgroundColor(Color.WHITE); // 設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE); // 設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD); // 設定文字style

		Renderer.setShowGrid(true); // 設定網格
		Renderer.setGridColor(Color.GRAY); // 設定網格顏色

		Renderer.setChartTitle(chartTitle); // 設定標頭文字
		Renderer.setLabelsColor(Color.BLACK); // 設定標頭文字顏色
		Renderer.setChartTitleTextSize(20); // 設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK); // 設定雙軸顏色
		Renderer.setBarSpacing(1); // 設定bar間的距離

		Renderer.setXTitle(XTitle); // 設定X軸文字
		Renderer.setYTitle(YTitle); // 設定Y軸文字
		Renderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		Renderer.setXLabelsAngle(0); // 設定X軸文字傾斜度

		Renderer.setXLabels(0); // 設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0); // 設定Y軸文最小值
		Renderer.setYAxisMax(30); // 設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动.
		Renderer.setPointSize(5);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		Renderer.setClickEnabled(true);//
		Renderer.setSelectableBuffer(screenWidth);

		yRenderer.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		yRenderer.setPointStyle(PointStyle.CIRCLE);// 点的类型是圆形
		yRenderer.setFillPoints(true);// 设置点是否实心
		// yRenderer.setDisplayChartValues(true); //展現Series數值

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		int start = 0;
		int end = start + 6;
		for (; start < end; start++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			Renderer.addXTextLabel(start, xy[start][0]);
			Series.add(start, Integer.parseInt(xy[start][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// 光滑的线段
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.3f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLine2ShadowChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series1 = new XYSeries(YTitle);
		// line2
		XYSeries Series2 = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series1);
		// line2
		Dataset.addSeries(Series2);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);

		Renderer.setFitLegend(true); // 如果设置图例应大小以适合
		Renderer.setShowAxes(false); // 如果设置的轴应该是可见的。
		Renderer.setShowCustomTextGrid(false); // 如果设置网格的自定义X或Y的标签应该是可见的。
		Renderer.setShowLegend(false); // 如果设置线的标识是否是可见的。
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // 設定背景顏色
		Renderer.setBackgroundColor(Color.WHITE); // 設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE); // 設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD); // 設定文字style

		Renderer.setShowGrid(true); // 設定網格
		Renderer.setGridColor(Color.GRAY); // 設定網格顏色

		Renderer.setChartTitle(chartTitle); // 設定標頭文字
		Renderer.setLabelsColor(Color.BLACK); // 設定標頭文字顏色
		Renderer.setChartTitleTextSize(20); // 設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK); // 設定雙軸顏色
		Renderer.setBarSpacing(1); // 設定bar間的距離

		Renderer.setXTitle(XTitle); // 設定X軸文字
		Renderer.setYTitle(YTitle); // 設定Y軸文字
		Renderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		Renderer.setXLabelsAngle(0); // 設定X軸文字傾斜度

		Renderer.setXLabels(0); // 設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0); // 設定Y軸文最小值
		Renderer.setYAxisMax(30); // 設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动.
		Renderer.setClickEnabled(true);//
		Renderer.setSelectableBuffer(50);

		yRenderer1.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		yRenderer2.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		// yRenderer.setDisplayChartValues(true); //展現Series數值

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer1.setPointStyle(PointStyle.CIRCLE);//点的类型是圆形
			// Renderer.setPointSize(4);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer1.setFillPoints(true);//设置点是否实心
			// yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
			// Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer.setFillPoints(true);//设置点是否实心
			Renderer.addXTextLabel(r, xy[r][0]);
			// Renderer.addYTextLabel(r, xy[r][0]);
			Series1.add(r, Integer.parseInt(xy[r][1]));
		}
		yRenderer2.setPointStyle(PointStyle.CIRCLE);// 点的类型是圆形
		yRenderer2.setFillPoints(true);// 设置点是否实心
		for (int r = 2; r < xy.length - 2; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
			// Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer.setFillPoints(true);//设置点是否实心
			Renderer.addXTextLabel(r, xy[r][0]);// 为坐标为r的点命名xy[r][0]
			// Renderer.addYTextLabel(r, xy[r][0]);
			Series2.add(r, Integer.parseInt(xy[r][1]));// 添加坐标
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// 光滑的线段
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@SuppressWarnings("deprecation")
	private View getLineDashedwChart(String chartTitle, String XTitle,
			String YTitle, String[][] xy) {

		XYSeries Series1 = new XYSeries(YTitle);
		// line2
		XYSeries Series2 = new XYSeries(YTitle);

		XYMultipleSeriesDataset Dataset = new XYMultipleSeriesDataset();
		Dataset.addSeries(Series1);
		// line2
		Dataset.addSeries(Series2);

		XYMultipleSeriesRenderer Renderer = new XYMultipleSeriesRenderer();
		XYSeriesRenderer yRenderer1 = new XYSeriesRenderer();
		XYSeriesRenderer yRenderer2 = new XYSeriesRenderer();
		Renderer.addSeriesRenderer(yRenderer1);
		Renderer.addSeriesRenderer(yRenderer2);

		Renderer.setFitLegend(true); // 如果设置图例应大小以适合
		Renderer.setShowAxes(false); // 如果设置的轴应该是可见的。
		Renderer.setShowCustomTextGrid(false); // 如果设置网格的自定义X或Y的标签应该是可见的。
		Renderer.setShowLegend(false); // 如果设置线的标识是否是可见的。
		yRenderer1.setFillBelowLine(false);
		yRenderer1.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));
		yRenderer2.setFillBelowLine(true);
		yRenderer2.setFillBelowLineColor(getResources().getColor(
				R.color.chartred_shadow));

		Renderer.setApplyBackgroundColor(true); // 設定背景顏色
		Renderer.setBackgroundColor(Color.WHITE); // 設定圖內圍背景顏色
		Renderer.setMarginsColor(Color.WHITE); // 設定圖外圍背景顏色
		Renderer.setTextTypeface(null, Typeface.BOLD); // 設定文字style

		Renderer.setShowGrid(true); // 設定網格
		Renderer.setGridColor(Color.GRAY); // 設定網格顏色

		Renderer.setChartTitle(chartTitle); // 設定標頭文字
		Renderer.setLabelsColor(Color.BLACK); // 設定標頭文字顏色
		Renderer.setChartTitleTextSize(20); // 設定標頭文字大小
		Renderer.setAxesColor(Color.BLACK); // 設定雙軸顏色
		Renderer.setBarSpacing(1); // 設定bar間的距離

		Renderer.setXTitle(XTitle); // 設定X軸文字
		Renderer.setYTitle(YTitle); // 設定Y軸文字
		Renderer.setXLabelsColor(Color.BLACK); // 設定X軸文字顏色
		Renderer.setYLabelsColor(0, Color.BLACK); // 設定Y軸文字顏色
		Renderer.setXLabelsAlign(Align.CENTER); // 設定X軸文字置中
		Renderer.setYLabelsAlign(Align.CENTER); // 設定Y軸文字置中
		Renderer.setXLabelsAngle(0); // 設定X軸文字傾斜度

		Renderer.setXLabels(0); // 設定X軸不顯示數字, 改以程式設定文字
		Renderer.setYAxisMin(0); // 設定Y軸文最小值
		Renderer.setYAxisMax(30); // 設定Y軸文最小值
		Renderer.setZoomEnabled(false);
		Renderer.setZoomEnabled(false, false);
		Renderer.setPanEnabled(false, false);// 不允许左右拖动,不允许上下拖动.

		yRenderer1.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		yRenderer1.setStroke(BasicStroke.DASHED);// 设置描边风格
		yRenderer2.setColor(getResources().getColor(R.color.chartred)); // 設定Series顏色
		// yRenderer.setDisplayChartValues(true); //展現Series數值

		// Series.add(0, 0);
		// Renderer.addXTextLabel(0, "");
		Renderer.setPointSize(5);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
		for (int r = 0; r < xy.length; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			// yRenderer1.setPointStyle(PointStyle.CIRCLE);//点的类型是圆形
			// Renderer.setPointSize(4);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer1.setFillPoints(true);//设置点是否实心
			// yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
			// Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer.setFillPoints(true);//设置点是否实心
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series1.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		for (int r = 2; r < xy.length - 2; r++) {
			// Log.i("DEBUG", (r+1)+" "+xy[r][0]+"; "+xy[r][1]);
			yRenderer2.setPointStyle(PointStyle.CIRCLE);// 点的类型是圆形
			Renderer.setPointSize(4);// 设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			yRenderer2.setFillPoints(true);// 设置点是否实心
			// yRenderer.setPointStyle(PointStyle.X);//点的类型是圆形
			// Renderer.setPointSize(5);//设置点的大小(图上显示的点的大小和图例中点的大小都会被设置)
			// yRenderer.setFillPoints(true);//设置点是否实心
			Renderer.addXTextLabel(r + 1, xy[r][0]);
			Series2.add(r + 1, Integer.parseInt(xy[r][1]));
		}
		// Series.add(11, 0);
		// Renderer.addXTextLabel(xy.length+1, "");

		// View view = ChartFactory.getBarChartView(getBaseContext(), Dataset,
		// Renderer, Type.DEFAULT);
		// View view = ChartFactory.getLineChartView(getBaseContext(), Dataset,
		// Renderer);
		// 光滑的线段
		View view = ChartFactory.getCubeLineChartView(getBaseContext(),
				Dataset, Renderer, 0.2f);
		return view;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int position) {
		// 切换选中的点
		llPoints.getChildAt(previousSelectPosition).setEnabled(false); // 把前一个点置为normal状态
		llPoints.getChildAt(position).setEnabled(true); // 把当前选中的position对应的点置为enabled状态
		previousSelectPosition = position;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
