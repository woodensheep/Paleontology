package com.nandity.paleontology.hcvideo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.hikvision.netsdk.ExceptionCallBack;
import com.hikvision.netsdk.HCNetSDK;
import com.hikvision.netsdk.NET_DVR_DEVICEINFO_V30;
import com.hikvision.netsdk.NET_DVR_PLAYBACK_INFO;
import com.hikvision.netsdk.NET_DVR_PREVIEWINFO;
import com.hikvision.netsdk.NET_DVR_TIME;
import com.hikvision.netsdk.NET_DVR_VOD_PARA;
import com.hikvision.netsdk.PTZCommand;
import com.hikvision.netsdk.PlaybackControlCommand;
import com.nandity.paleontology.R;

public class HcActivity extends Activity implements OnClickListener {
	private Button btnPlay, btnPlayback, btnUp, btnLeft, btnRight, btnDown, btnBig, btnSmall;
	private TextView tvStartTime, tvStopTime;
	private static String TAG = "HcActivity";
	private NET_DVR_DEVICEINFO_V30 m_oNetDvrDeviceInfoV30 = null;
	private int m_iLogID = -1; // return by NET_DVR_Login_v30
	private int m_iPlayID = -1; // return by NET_DVR_RealPlay_V30
	private int m_iPlaybackID = -1; // return by NET_DVR_PlayBackByTime

	private int m_iStartChan = 0; // start channel no
	private int m_iChanNum = 0; // channel number
	private PlaySurfaceView[] playView = new PlaySurfaceView[4];
	private boolean m_bMultiPlay = false;

	private int startYear, startMonth, startDay, startHour, startMinute, stopYear, stopMonth, stopDay, stopHour,
			stopMinute;
	private String ip;
	private int port;
	private String user;
	private String psd;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashUtil crashUtil = CrashUtil.getInstance();
		crashUtil.init(this);
		setContentView(R.layout.activity_hc);
		Log.d(TAG, "执行了onCreate()方法");
		intent=getIntent();
		ip=intent.getStringExtra("IP");
		port=intent.getIntExtra("PORT", 0);
		user=intent.getStringExtra("USER");
		psd=intent.getStringExtra("PASSWORD");
		if (!initSDK()) {
			finish();
			return;
		}
		login();
		initViews();
		setListener();
	}

	private void setListener() {
		btnUp.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan, PTZCommand.TILT_UP,
								0)) {
							Log.e(TAG, "start PAN_UP failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start PAN_UP succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan, PTZCommand.TILT_UP,
								1)) {
							Log.e(TAG, "start PAN_UP failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop PAN_UP succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
		btnLeft.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.PAN_LEFT, 0)) {
							Log.e(TAG, "start PAN_LEFT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start PAN_LEFT succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.PAN_LEFT, 1)) {
							Log.e(TAG, "start PAN_LEFT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop PAN_LEFT succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
		btnRight.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.PAN_RIGHT, 0)) {
							Log.e(TAG, "start PAN_RIGHT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start PAN_RIGHT succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.PAN_RIGHT, 1)) {
							Log.e(TAG, "start PAN_RIGHT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop PAN_RIGHT succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
		btnDown.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.TILT_DOWN, 0)) {
							Log.e(TAG, "start PAN_DOWN failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start PAN_DOWN succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.TILT_DOWN, 1)) {
							Log.e(TAG, "start PAN_DOWN failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop PAN_DOWN succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
		btnBig.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan, PTZCommand.ZOOM_IN,
								0)) {
							Log.e(TAG, "start ZOOM_IN failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start ZOOM_IN succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan, PTZCommand.ZOOM_IN,
								1)) {
							Log.e(TAG, "stop ZOOM_IN failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop ZOOM_IN succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
		btnSmall.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				try {
					if (m_iLogID < 0) {
						Log.e(TAG, "please login on a device first");
						return false;
					}
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.ZOOM_OUT, 0)) {
							Log.e(TAG, "start ZOOM_OUT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "start ZOOM_OUT succ");
						}
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						if (!HCNetSDK.getInstance().NET_DVR_PTZControl_Other(m_iLogID, m_iStartChan,
								PTZCommand.ZOOM_OUT, 1)) {
							Log.e(TAG, "stop ZOOM_OUT failed with error code: "
									+ HCNetSDK.getInstance().NET_DVR_GetLastError());
						} else {
							Log.i(TAG, "stop ZOOM_OUT succ");
						}
					}
					return true;
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					return false;
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Boolean i = HCNetSDK.getInstance().NET_DVR_Cleanup();
		Log.d(TAG, "释放资源是否成功：" + i);
	}

	private void initViews() {
		btnPlay = (Button) findViewById(R.id.btn_Preview);
		btnPlayback = (Button) findViewById(R.id.btn_Playback);
		btnUp = (Button) findViewById(R.id.btn_up);
		btnLeft = (Button) findViewById(R.id.btn_left);
		btnRight = (Button) findViewById(R.id.btn_right);
		btnDown = (Button) findViewById(R.id.btn_down);
		btnBig = (Button) findViewById(R.id.btn_big);
		btnSmall = (Button) findViewById(R.id.btn_small);
		tvStartTime = (TextView) findViewById(R.id.tv_startTime);
		tvStopTime = (TextView) findViewById(R.id.tv_stopTime);
		btnPlay.setOnClickListener(this);
		btnPlayback.setOnClickListener(this);
		tvStartTime.setOnClickListener(this);
		tvStopTime.setOnClickListener(this);
	}

	private boolean initSDK() {
		if (!HCNetSDK.getInstance().NET_DVR_Init()) {
			Log.e(TAG, "HCNetSDK init is failed!");
			return false;
		}
		HCNetSDK.getInstance().NET_DVR_SetLogToFile(3, "/mnt/sdcard/sdklog/", true);
		return true;
	}

	private void ChangeSingleSurFace(boolean bSingle) {
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);

		for (int i = 0; i < 4; i++) {
			if (playView[i] == null) {
				playView[i] = new PlaySurfaceView(this);
				playView[i].setParam(metric.widthPixels);
				FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
						FrameLayout.LayoutParams.WRAP_CONTENT);
				params.bottomMargin = playView[i].getM_iHeight() - (i / 2) * playView[i].getM_iHeight();
				params.leftMargin = (i % 2) * playView[i].getM_iWidth();
				params.gravity = Gravity.TOP | Gravity.LEFT;
				addContentView(playView[i], params);
				playView[i].setVisibility(View.INVISIBLE);

			}
		}

		if (bSingle) {
			for (int i = 0; i < 4; ++i) {
				playView[i].setVisibility(View.INVISIBLE);
			}
			playView[0].setParam(metric.widthPixels * 2);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			params.bottomMargin = playView[3].getM_iHeight() - (3 / 2) * playView[3].getM_iHeight();
			// params.bottomMargin = 0;
			params.leftMargin = 0;
			// params.
			params.gravity = Gravity.TOP | Gravity.LEFT;
			playView[0].setLayoutParams(params);
			playView[0].setVisibility(View.VISIBLE);
		} else {
			for (int i = 0; i < 4; ++i) {
				playView[i].setVisibility(View.VISIBLE);
			}

			playView[0].setParam(metric.widthPixels);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
					FrameLayout.LayoutParams.WRAP_CONTENT);
			params.bottomMargin = playView[0].getM_iHeight() - (0 / 2) * playView[0].getM_iHeight();
			params.leftMargin = (0 % 2) * playView[0].getM_iWidth();
			params.gravity = Gravity.TOP | Gravity.LEFT;
			playView[0].setLayoutParams(params);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_Preview:
			preview();
			break;
		case R.id.btn_Playback:
			playBack();
			break;
		case R.id.tv_startTime:
			setTime(tvStartTime, 1);
			break;
		case R.id.tv_stopTime:
			setTime(tvStopTime, 2);
			break;
		}
	}

	private void setTime(final TextView tv, final int type) {
		View view = LayoutInflater.from(HcActivity.this).inflate(R.layout.dialog_timepicker, null);
		final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker1);
		final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
		timePicker.setIs24HourView(true);
		new AlertDialog.Builder(HcActivity.this, AlertDialog.THEME_HOLO_LIGHT).setView(view).setTitle("选择时间")
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (type == 1) {
							startYear = datePicker.getYear();
							startMonth = datePicker.getMonth() + 1;
							startDay = datePicker.getDayOfMonth();
							startHour = timePicker.getCurrentHour();
							startMinute = timePicker.getCurrentMinute();
							String time = startYear + "/" + startMonth + "/" + startDay + " " + startHour + ":"
									+ startMinute;
							tv.setText(time);
						} else {
							stopYear = datePicker.getYear();
							stopMonth = datePicker.getMonth() + 1;
							stopDay = datePicker.getDayOfMonth();
							stopHour = timePicker.getCurrentHour();
							stopMinute = timePicker.getCurrentMinute();
							String time = stopYear + "/" + stopMonth + "/" + stopDay + " " + stopHour + ":"
									+ stopMinute;
							tv.setText(time);
						}
					}
				}).setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).create().show();
	}

	private void playBack() {
		try {
			if (m_iLogID < 0) {
				Log.e(TAG, "please login on a device first");
				Toast.makeText(HcActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (m_iPlayID >= 0) {
				Log.i(TAG, "Please stop preview first");
				Toast.makeText(HcActivity.this, "请先停止预览！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (startYear == 0 || stopYear == 0) {
				Toast.makeText(HcActivity.this, "请先设置好时间！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (m_iPlaybackID < 0) {
				ChangeSingleSurFace(true);
				NET_DVR_TIME start = new NET_DVR_TIME();
				NET_DVR_TIME end = new NET_DVR_TIME();
				start.dwYear = startYear;
				start.dwMonth = startMonth;
				start.dwDay = startDay;
				start.dwHour = startHour;
				start.dwMinute = startMinute;
				start.dwSecond = 0;
				end.dwYear = stopYear;
				end.dwMonth = stopMonth;
				end.dwDay = stopDay;
				end.dwHour = stopHour;
				end.dwMinute = stopMinute;
				end.dwSecond = 0;
				NET_DVR_VOD_PARA struVod = new NET_DVR_VOD_PARA();
				struVod.struBeginTime = start;
				struVod.struEndTime = end;
				struVod.byStreamType = 1;
				struVod.struIDInfo.dwChannel = m_iStartChan;
				struVod.hWnd = playView[0].getHolder().getSurface();
				m_iPlaybackID = HCNetSDK.getInstance().NET_DVR_PlayBackByTime_V40(m_iLogID, struVod);
				if (m_iPlaybackID >= 0) {
					NET_DVR_PLAYBACK_INFO struPlaybackInfo = null;
					if (!HCNetSDK.getInstance().NET_DVR_PlayBackControl_V40(m_iPlaybackID,
							PlaybackControlCommand.NET_DVR_PLAYSTART, null, 0, struPlaybackInfo)) {
						Log.e(TAG, "net sdk playback start failed!");
						return;
					}
					btnPlayback.setText("停止");
				} else {
					Log.i(TAG, "NET_DVR_PlayBackByTime failed, error code: "
							+ HCNetSDK.getInstance().NET_DVR_GetLastError());
					Toast.makeText(HcActivity.this, "回放失败，检查时间是否正确！", Toast.LENGTH_SHORT).show();
				}
			} else {
				if (!HCNetSDK.getInstance().NET_DVR_StopPlayBack(m_iPlaybackID)) {
					Log.e(TAG, "net sdk stop playback failed");

				}
				btnPlayback.setText("回放");
				m_iPlaybackID = -1;
				ChangeSingleSurFace(true);
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
	}

	private void preview() {
		try {
			// ((InputMethodManager)
			// getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
			// HcActivity.this.getCurrentFocus().getWindowToken(),
			// InputMethodManager.HIDE_NOT_ALWAYS);
			if (m_iLogID < 0) {
				Toast.makeText(HcActivity.this, "请先登录！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (m_iPlaybackID >= 0) {
				Toast.makeText(HcActivity.this, "请先停止回放！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (m_iChanNum > 8)// 多个通道
			{
				if (!m_bMultiPlay) {
					startMultiPreview();
					m_bMultiPlay = true;
					btnPlay.setText("停止");
				} else {
					stopMultiPreview();
					m_bMultiPlay = false;
					btnPlay.setText("预览");
				}
			} else {// 单个通道
				if (m_iPlayID < 0) {
					startSinglePreview();
				} else {
					stopSinglePreview();
					btnPlay.setText("预览");
				}
			}
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.toString());
		}
	}

	/**
	 * 停止单个通道播放
	 */
	private void stopSinglePreview() {
		if (m_iPlayID < 0) {
			Log.e(TAG, "m_iPlayID < 0");
			return;
		}

		// net sdk stop preview
		if (!HCNetSDK.getInstance().NET_DVR_StopRealPlay(m_iPlayID)) {
			Log.e(TAG, "StopRealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
		}
		ChangeSingleSurFace(true);
		m_iPlayID = -1;
	}

	/**
	 * 开始单个通道播放
	 */
	private void startSinglePreview() {
		ChangeSingleSurFace(true);
		NET_DVR_PREVIEWINFO previewInfo = new NET_DVR_PREVIEWINFO();
		previewInfo.lChannel = m_iStartChan;
		previewInfo.dwStreamType = 0; // substream
		previewInfo.bBlocked = 1;
		previewInfo.hHwnd = playView[0].getHolder();
		Log.d(TAG, "surface:" + playView[0].toString());
		m_iPlayID = HCNetSDK.getInstance().NET_DVR_RealPlay_V40(m_iLogID, previewInfo, null);
		if (m_iPlayID < 0) {
			Log.e(TAG, "NET_DVR_RealPlay is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			return;
		}
		Log.i(TAG, "NetSdk Play sucess ***********************3***************************");
		Toast.makeText(HcActivity.this, "播放成功！", Toast.LENGTH_SHORT).show();
		btnPlay.setText("停止");
	}

	/**
	 * 停止多个通道播放
	 */
	private void stopMultiPreview() {
		int i = 0;
		for (i = 0; i < 4; i++) {
			playView[i].stopPreview();
		}
		ChangeSingleSurFace(true);
		m_iPlayID = -1;
	}

	/**
	 * 开始多个通道播放
	 */
	private void startMultiPreview() {
		ChangeSingleSurFace(true);
		playView[0].startPreview(m_iLogID, m_iStartChan+1 );
		m_iPlayID = playView[0].m_iPreviewHandle;
		m_iStartChan=m_iStartChan+1;
		Log.d(TAG, "预览ID:"+m_iPlayID);
	}

	/**
	 * 设备登录
	 */
	private void login() {
		try {
			if (m_iLogID < 0) {
				m_iLogID = loginDevice();
				if (m_iLogID < 0) {
					Log.e(TAG, "This device logins failed!");
					return;
				} else {
					Log.d(TAG, "登录成功的ID:" + m_iLogID);
				}
				ExceptionCallBack oexceptionCbf = getExceptiongCbf();
				if (oexceptionCbf == null) {
					Log.e(TAG, "ExceptionCallBack object is failed!");
					return;
				}

				if (!HCNetSDK.getInstance().NET_DVR_SetExceptionCallBack(oexceptionCbf)) {
					Log.e(TAG, "NET_DVR_SetExceptionCallBack is failed!");
					return;
				}

				Log.i(TAG, "登录成功！");
			} else {
				if (!HCNetSDK.getInstance().NET_DVR_Logout_V30(m_iLogID)) {
					Log.e(TAG, " NET_DVR_Logout is failed!");
					return;
				}
				ChangeSingleSurFace(true);
				m_iLogID = -1;
			}
		} catch (Exception e) {
			Log.e(TAG, "error: " + e.toString());
		}
	}

	private ExceptionCallBack getExceptiongCbf() {
		ExceptionCallBack oExceptionCbf = new ExceptionCallBack() {
			public void fExceptionCallBack(int iType, int iUserID, int iHandle) {
				System.out.println("recv exception, type:" + iType);
			}
		};
		return oExceptionCbf;
	}

	private int loginDevice() {
		m_oNetDvrDeviceInfoV30 = new NET_DVR_DEVICEINFO_V30();
		if (null == m_oNetDvrDeviceInfoV30) {
			Log.e(TAG, "HKNetDvrDeviceInfoV30 new is failed!");
			return -1;
		}
		int loginId = HCNetSDK.getInstance().NET_DVR_Login_V30(ip, port, user, psd, m_oNetDvrDeviceInfoV30);
		if (loginId < 0) {
			int code = HCNetSDK.getInstance().NET_DVR_GetLastError();
			String msg;
			Log.e(TAG, "NET_DVR_Login is failed!Err:" + HCNetSDK.getInstance().NET_DVR_GetLastError());
			if (code == 1) {
				msg = "账号密码错误！";
			} else {
				msg = "从设备获取数据失败！";
			}
			Toast.makeText(HcActivity.this, msg, Toast.LENGTH_SHORT).show();
			return -1;
		}
		if (m_oNetDvrDeviceInfoV30.byChanNum > 0) {
			m_iStartChan = m_oNetDvrDeviceInfoV30.byStartChan;
			m_iChanNum = m_oNetDvrDeviceInfoV30.byChanNum;
		} else if (m_oNetDvrDeviceInfoV30.byIPChanNum > 0) {
			m_iStartChan = m_oNetDvrDeviceInfoV30.byStartDChan;
			m_iChanNum = m_oNetDvrDeviceInfoV30.byIPChanNum + m_oNetDvrDeviceInfoV30.byHighDChanNum * 256;
		}
		int byChanNum=m_oNetDvrDeviceInfoV30.byChanNum;
		int byStartChan=m_oNetDvrDeviceInfoV30.byStartChan;
		int byIpChanNum=m_oNetDvrDeviceInfoV30.byIPChanNum;
		int byZeroChanNum=m_oNetDvrDeviceInfoV30.byZeroChanNum;
		int byStartDChan=m_oNetDvrDeviceInfoV30.byStartDChan;
		int byHighDChanNum=m_oNetDvrDeviceInfoV30.byHighDChanNum;
		Log.d(TAG, "模拟通道个数：" + byChanNum);
		Log.d(TAG, "模拟起始通道号：" + byStartChan);
		Log.d(TAG, "最大数字通道个数：" + byIpChanNum);
		Log.d(TAG, "零通道个数：" + byZeroChanNum);
		Log.d(TAG, "数字起始通道号：" + byStartDChan);
		Log.d(TAG, "数字通道个数：" + byHighDChanNum);
		if (m_iChanNum > 8) {
			ChangeSingleSurFace(true);
		} else {
			ChangeSingleSurFace(true);
		}
		Log.i(TAG, "NET_DVR_Login is Successful!");

		return loginId;
	}

}
