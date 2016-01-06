package com.iflytek.voicedemo;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceAssistanceActivity extends Activity{
	private final static int NOTICE_NUM = 6;
	private int[] mAppIcon;
	private String[] mAppName;
	private String[] mAppContent;
	
	private ListView mLvVoiceNotive;
	private VoiceListAdapter mVoiceListAdapter;
	
	private Toast mToast;
	
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	
	private EditText mEtResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.voice_assistant);
		init();
		mLvVoiceNotive = (ListView) findViewById(R.id.lv_notice);
		mVoiceListAdapter = new VoiceListAdapter();
		mLvVoiceNotive.setAdapter(mVoiceListAdapter);
		
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		
		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(this, mInitListener);
		
		mEtResult = (EditText) findViewById(R.id.tv_voice);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	@Override
	protected void onStop() {
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	private void init() {
		mAppIcon = new int[]{R.drawable.phone,R.drawable.fmradio,R.drawable.map,
				    R.drawable.music,R.drawable.clock,R.drawable.camera};;
		mAppName = new String[]{"电话","收音机","导航","音乐","闹钟","相机"};
		mAppContent = new String[]{"给小明打电话","打开收音机","打开导航","播放音乐","打开闹钟","打开相机"};
	}
	
	private class ViewHolder {
		ImageView iv_app_icon;
		TextView tv_app_name;
		TextView tv_app_content;
	}
	
	class VoiceListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return NOTICE_NUM;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(VoiceAssistanceActivity.this).inflate(R.layout.voice_item, null);
				holder.iv_app_icon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
				holder.tv_app_name = (TextView) convertView.findViewById(R.id.tv_app_name);
				holder.tv_app_content = (TextView) convertView.findViewById(R.id.tv_app_content);
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.iv_app_icon.setImageResource(mAppIcon[position]);
			holder.tv_app_name.setText(mAppName[position]);
			holder.tv_app_content.setText(mAppContent[position]);
			return convertView;
		}
	}
	
	/** 初始化监听器。 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code);
			}
		}
	};
	
	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

}
